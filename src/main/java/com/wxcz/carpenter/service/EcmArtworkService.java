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
     *        按条件查询 作品
     *       保存成功: status 0  msg "success” data数据
     *       保存失败: status 500  msg "error“
     */
    PageDTO ajaxList(EcmArtworkQuery ecmArtworkQuery);

    /**
     * @param: [ecmArtworkVO] 需要修改的作品
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 :
     *        验证权限并 对作品 进行修改
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    ResponseDTO chengArtWork(EcmArtworkVO ecmArtworkVO);

    PageDTO ajaxCheckList(EcmArtworkQuery ecmArtworkQuery);

    ResponseDTO getArtWorkNoteS(EcmArtworkQuery ecmArtworkVO);

    ResponseDTO upDataNode(EcmArtworkNodes ecmArtworkNodes);

    ResponseDTO checkArtWork(EcmArtworkQuery ecmArtworkQuery);

    ResponseDTO upDataArtWork(EcmArtwork ecmArtwork);

    ResponseDTO artWorkAudit(EcmArtwork ecmArtwork);
}
