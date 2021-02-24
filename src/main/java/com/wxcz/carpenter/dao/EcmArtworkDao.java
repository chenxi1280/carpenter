package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.vo.EcmArtworkVO;
import com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cxd
 * @data 2020/8/14
 */
@Repository
public interface EcmArtworkDao {

    int deleteByPrimaryKey(Integer pkArtworkId);

    int insert(EcmArtwork record);

    int insertSelective(EcmArtwork record);

    EcmArtwork selectByPrimaryKey(Integer pkArtworkId);

    int updateByPrimaryKeySelective(EcmArtwork record);

    int updateByPrimaryKey(EcmArtwork record);

    /**
     * @param: [ecmArtworkQuery] 查询条件（多条件 模糊查询）  自带分页 start 和 limit
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmArtworkVO>
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 :  按条件查询 作品集合  EcmArtworkVO 集合  （遗弃） （主要layui表格使用）
     *       连表查询 ，在 EcmArtworkVO 中的 作品所属的用户名会在 Use表查询
     *       连表user表是索引丢失，查询比较慢，
     */
    List<EcmArtworkVO> selectajaxList(EcmArtworkQuery ecmArtworkQuery);

    /**
     * @param: [ecmArtworkQuery]  查询条件（多条件 模糊查询）
     * @return: java.lang.Integer
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 :查询表中作品集合行数 （主要layui表格使用）
     */
    Integer selectCountByQuery(EcmArtworkQuery ecmArtworkQuery);

    /**
     * @param: [ecmArtworkQuery]  查询条件（多条件 模糊查询）  自带分页 start 和 limit
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmArtworkVO>
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 :  查询待审核的所有作品  EcmArtworkVO 集合  （主要layui表格使用）
     *       连表查询 ，在 EcmArtworkVO 中的 作品所属的用户名会在 Use表查询
     *       连表user表是索引丢失，查询比较慢，
     */
    List<EcmArtworkVO> selectajaxCheckList(EcmArtworkQuery ecmArtworkQuery);

    /**
     * @param: [ecmArtworkQuery]   查询条件（多条件 模糊查询）
     * @return: java.lang.Integer
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 : 查询表查询待审核的所有作品行数 （主要layui表格使用）
     */
    Integer selectCountByCheckList(EcmArtworkQuery ecmArtworkQuery);

    /**
     * @param: [ecmArtworkQuery] 查询条件（多条件 模糊查询）  自带分页 start 和 limit
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmArtworkVO>
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 : 按条件查询 作品集合  EcmArtworkVO 集合   （主要layui表格使用）
     */
    List<EcmArtworkVO> selectajaxListByQuery(EcmArtworkQuery ecmArtworkQuery);

    /**
     * @param: [pkUserId] 用户id
     * @return: java.lang.Integer
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 :  用户封禁下架所有作品
     */
    Integer downArtWorkByUserId(Integer pkUserId);

    /**
     * @param: [pkUserId]
     * @return: java.lang.Integer
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 根据用户id 查询 作品
     */
    Integer selectByUserId(Integer pkUserId);

    /**
     * @param: [list]
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmArtworkVO>
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 根据 投诉集合查询 作品信息
     */
    List<EcmArtworkVO> selectByReportList(@Param(("ids")) List<EcmReportHistroyVO> list);

    /**
     * @param: [ecmArtwork]
     * @return: java.lang.Integer
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 更新 作品 审核失败  审核人清空
     */
    Integer updateByPrimaryKeyFail(EcmArtwork ecmArtwork);

    int updateArtWorkPlayClient(EcmArtworkVO ecmArtworkVO);
}
