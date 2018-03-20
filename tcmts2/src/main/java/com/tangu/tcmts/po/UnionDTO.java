/**
 * FileName: UnionDTO
 * Author: yeyang
 * Date: 2018/1/30 15:29
 */
package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UnionDTO {
    @ApiModelProperty("处方Id")
    private Integer recipeId;
    @ApiModelProperty("关联Id")
    private Integer unionId;
    @ApiModelProperty("关联状态")
    private Boolean unionState;
    @ApiModelProperty("病人姓名")
    private String recipientName;
    @ApiModelProperty("病人电话")
    private String recipientTel;

    public void setUnionId(Integer unionId) {
        this.unionId = unionId;
        if(unionId == null){
            setUnionId(0);
        }
        this.unionState = unionId.equals(0) ? false : true;
    }

}
