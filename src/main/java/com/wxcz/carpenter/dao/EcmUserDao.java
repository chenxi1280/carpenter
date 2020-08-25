package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUser;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.EcmArtworkVO;
import com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cxd
 * @Date: 2020/8/14
 */
public interface EcmUserDao {
    int deleteByPrimaryKey(Integer pkUserId);

    int insert(EcmUser record);

    int insertSelective(EcmUser record);

    EcmUser selectByPrimaryKey(Integer pkUserId);

    int updateByPrimaryKeySelective(EcmUser record);

    int updateByPrimaryKey(EcmUser record);


    /**
     * @param: [query] 登录查询类 账户 密码
     * @return: com.wxcz.carpenter.pojo.vo.EcmUserVO
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 : 登录查询sql 
     */
    EcmUserVO login(EcmUserQuery query);

    /**
     * @param: [mobile] 电话号码
     * @return: com.wxcz.carpenter.pojo.vo.EcmUserVO
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 : 通过电话号码查询用户
     */
    EcmUserVO selectByPhone(String mobile);

    /**
     * @param: [ecmUserQuery]  查询条件（多条件 模糊查询）  自带分页 start 和 limit
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmUserVO>
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 :  按条件查询 用户集合  EcmUserVO 集合   （主要layui表格使用）
     */
    List<EcmUserVO> selectListByQuery(EcmUserQuery ecmUserQuery);

    /**
     * @param: [ecmUserQuery] 查询条件（多条件 模糊查询）
     * @return: java.lang.Integer
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 :查询表中用户行数 （主要layui表格使用）
     */
    Integer selectCountByQuery(EcmUserQuery ecmUserQuery);

    /**
     * @param: [list] 作品集合
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmUserVO> 用户集合
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 :   根据 EcmArtworkVO集合 查询 对应的 用户集合
     */
    List<EcmUserVO> selectByList(@Param("ids")  List<EcmArtworkVO> list);

    /**
     * @param: [list] 作品集合
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmUserVO> 审核人集合
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 : 根据 EcmArtworkVO集合 查询 对应的 审核人集合
     */
    List<EcmUserVO> selectUserNameByList(@Param("ids") List list);

    /**
     * @param: [list]
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmUserVO>
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 :  根据 投诉审核人id集合  查询 用户集合
     */
    List<EcmUserVO> selectByReportList(@Param("ids")List<EcmReportHistroyVO> list);

    /**
     * @param: []
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmUserVO>
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 查询所有用户 （有电话号码的）
     */
    List<EcmUserVO> selectAll();

    /**
     * @param: [asList] userId 集合
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmUserVO>
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 根据id 集合查询用户集合
     */
    List<EcmUserVO> selectIds(@Param("ids") List<Integer> asList);
}