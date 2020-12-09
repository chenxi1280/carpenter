package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmArtworkEndings;
import lombok.Data;

import java.util.List;

/**
 * @author by cxd
 * @Classname EcmArtworkEndingsVO
 * @Description TODO
 * @Date 2020/12/2 14:22
 */
@Data
public class EcmArtworkEndingsVO extends EcmArtworkEndings {
    private int fkUserId;
    private List<Integer> selectTreeList;
}
