package com.wxcz.carpenter.config.shiro;


import com.wxcz.carpenter.common.SecretKeyConstants;
import com.wxcz.carpenter.pojo.entity.EcmUser;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.EcmUserRolesVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import com.wxcz.carpenter.service.EcmUserService;
import com.wxcz.carpenter.util.EncryptUtil;
import com.wxcz.carpenter.util.MD5Utils;

import lombok.SneakyThrows;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * creator：cxd
 * date:2020/8/5
 * shiro认证和授权的 一个类（领域配置类）
 * @author cxd
 */

public class UserRealm extends AuthorizingRealm {
    Logger logger = LoggerFactory.getLogger(UserRealm.class);
    @Resource
    EcmUserService userService;


    /**
     * 认证身份用的
     * subject.login(token)调用之后，会进入到这个方法
     *
     * @param authenticationToken 携带了用户名和密码的：认证，判断用户名和密码跟数据库里边是否一致
     * @return
     */
    @SneakyThrows
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取身份（用户名 电话号码）
        Object principal = authenticationToken.getPrincipal();
        // 获取密码（密码）：是前端传递来的，不具备真实性
        Object credentials = authenticationToken.getCredentials();
        Session session = SecurityUtils.getSubject().getSession();
//        Object code = session.getAttribute("code");// 先把code取出来
        EcmUserQuery query = new EcmUserQuery();
        // 前端传递过来的// String.valueOf((char[]) credentials)
        String password = new String((char[]) credentials);
        query.setPhone(EncryptUtil.aesEncrypt((String) principal, SecretKeyConstants.secretKey));
        query.setPassWord(MD5Utils.encrypt(password));

        // 拿到了数据库的用户
        EcmUserVO ecmUserVO = userService.login(query);

        if (ecmUserVO == null){
            throw new CredentialsException("账户或密码错误");
        }

//        // 应该设置 session
        session.setAttribute("userId", ecmUserVO.getPkUserId());
        session.setAttribute("nickName", ecmUserVO.getUsername());
//        session.setAttribute("phone", ecmUserVO.getMobile());
        //更新的登录时间 和 登录次数
        userService.upDataUser(ecmUserVO);

        // 设置角色 权限

        List<EcmUserRolesVO> userRolesVOS = userService.selectUserRolesByUser(ecmUserVO);



        session.setAttribute("hisRoles", userRolesVOS);


        return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getCredentials(), "userRealm");
    }

    /**
     * 对身份进行授权用的
     *
     * @param principalCollection
     * @return
     * @throws AuthenticationException 根据用户名去数据库查询这个 用户的角色和权限设置给 这个SimpleAuthorizationInfo对象即可
     *                                 <p>
     *                                 只有第一次判断的时候，才能进入授权方法，如果当前用户没有这个权限或者角色，那么将会被缓存到内存之中，第二次之后，就不用查询了
     */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {// 登录成功之后，给用户授予角色和权限用的，或者说检测用户的角色和权限的

        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();

        Session session = SecurityUtils.getSubject().getSession();
        List<EcmUserRolesVO> userRolesVOS = (List<EcmUserRolesVO>) session.getAttribute("hisRoles");
        HashSet<String> roleSet = new HashSet<>();
        HashSet<String> acessSet = new HashSet<>();
        userRolesVOS.forEach( role -> {
             roleSet.add(role.getFlag());
             role.getAcessVOS().forEach( acess -> {
                 acessSet.add(acess.getFlag());
             });
        });


        auth.setRoles(roleSet);
        auth.setStringPermissions(acessSet);

        return auth;

    }

}
