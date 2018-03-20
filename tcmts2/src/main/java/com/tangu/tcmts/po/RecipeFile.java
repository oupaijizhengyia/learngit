/**
 * FileName: RecipeFile
 * Author: yeyang
 * Date: 2018/1/31 10:46
 * 处方文件
 */

package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RecipeFile {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("处方id")
    private Integer recipeId;

    @ApiModelProperty("处方code")
    private String recipeCode;
    
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("配方图")
    private Integer fileType;

    @ApiModelProperty("base64")
    private String fileBase64;

}
