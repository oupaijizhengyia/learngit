package com.tangu.tcmts.po;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BoilProject {
    private Integer id;

    @ApiModelProperty(value = "煎煮方案名称")
    private String projectName;

    @ApiModelProperty(value = "浸泡时间")
    private Integer soakTime;

    @ApiModelProperty(value = "头煎时间")
    private Integer firstBoilTime;

    @ApiModelProperty(value = "二煎时间")
    private Integer secondBoilTime;

    @ApiModelProperty(value = "药液量")
    private Long volume;

    private Date createTime;

    private Integer modUser;

    private Date modTime;
    
    private Integer value;
    
    private String label;

}