package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.entity.EcmArtworkNodes;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.vo.EcmArtworkVO;

/**
 * @author by cxd
 * @Classname EcmArtworkService
 * @Description TODO
 * @Date 2020/8/7 10:53
 */
public interface EcmArtworkService {


    /**
     * @param: [ecmArtworkQuery] 查询条件类
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 按条件查询 作品
     * 保存成功: status 0  msg "success” data数据
     * 保存失败: status 500  msg "error“
     */
    PageDTO ajaxList(EcmArtworkQuery ecmArtworkQuery);

    /**
     * @param: [ecmArtworkVO] 需要修改的作品
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 :
     * 验证权限并 对作品 进行修改
     * 保存成功: status 200  msg "success”
     * 保存失败: status 500  msg "error“
     */
    ResponseDTO chengArtWork(EcmArtworkVO ecmArtworkVO);


    ResponseDTO updateArtWorkPlayClient(EcmArtworkVO ecmArtworkVO);

    /**
     * @param: [ecmArtworkQuery] 待审核 作品的 查询条件
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 :  查询 待审核视频的集合
     * 保存成功: status 0    msg "success”  data 数据
     * 保存失败: status 500  msg "error“    data 无
     */
    PageDTO ajaxCheckList(EcmArtworkQuery ecmArtworkQuery);

    /**
     * @param: [ecmArtworkVO] 作品id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 : 根据作品id 查询 所以的 作品节点
     * 保存成功: status 200  msg "success” data 作品节点集合
     * 保存失败: status 500  msg "error“  data 无
     */
    ResponseDTO getArtWorkNoteS(EcmArtworkQuery ecmArtworkVO);

    /**
     * @param: [ecmArtworkNodes] 更新后的及节点，（必须包含节点id）
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 : 通过传入的 节点进行更新操作
     * 保存成功: status 200  msg "success”
     * 保存失败: status 500  msg "error“
     */
    ResponseDTO upDataNode(EcmArtworkNodes ecmArtworkNodes);

    /**
     * @param: [ecmArtworkQuery] 需要审核检查作品的id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 检查 作品子节点 接口，并根据节点状态 决定 作品状态
     * 保存成功: status 200  msg “作品未通过审核” “作通过审核”
     * 保存失败: status 500  msg “作品有节点未审核”  “无权限”
     */
    ResponseDTO checkArtWork(EcmArtworkQuery ecmArtworkQuery);

    /**
     * @param: [ecmArtwork] 更新后的及作品，（必须包含作品id）
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 : 通过传入的 作品进行更新操作
     * 保存成功: status 200  msg "success”
     * 保存失败: status 500  msg "error“
     */
    ResponseDTO upDataArtWork(EcmArtwork ecmArtwork);

    /**
     * @param: [ecmArtwork] 查询的 作品id 和 当前 操作的用户id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 :   判断 作品审核人 和 当前操作的用户 是否匹配
     * 是: status 200  msg "success”
     * 不是: status 500  msg "error“
     */
    ResponseDTO artWorkAudit(EcmArtwork ecmArtwork);

    /**
     * @param: [ecmArtworkQuery]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 :
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    ResponseDTO reCheckArtWork(EcmArtworkQuery ecmArtworkQuery);

    /**
     * @param: [ecmArtworkQuery] 需要审核检查作品的id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 检查 投诉作品子节点 接口，并根据节点状态 决定 作品状态
     * 保存成功: status 200  msg “作品未通过审核” “作通过审核”
     * 保存失败: status 500  msg “作品有节点未审核”  “无权限”
     */
    ResponseDTO chArtWorkImg(EcmArtworkVO ecmArtworkQuery);

    ResponseDTO chengArtWorkReport(EcmArtworkVO ecmArtworkVO);


    ResponseDTO addEcmArtworkHot();

}
