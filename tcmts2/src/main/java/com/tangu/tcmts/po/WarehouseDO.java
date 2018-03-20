package com.tangu.tcmts.po;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author:huangyifan
 * @Description:仓库、药房DO
 * @Date: create in 17:00 2017/10/24
 */
public class WarehouseDO {
	@ApiModelProperty(value = "id", required = true)
    private Integer id;
	@ApiModelProperty(value = "仓库/药房名称", required = true)
    private String warehouseName;
	@ApiModelProperty(value = "数据类型：1.仓库,3.饮片药房,4.小包装药房")
    private Integer warehouseType;
	@ApiModelProperty(value = "备注")
    private String remark;
	@ApiModelProperty(value = "0.在用,1.停用")
    private Integer useState;
	@ApiModelProperty(value = "创建时间")
    private Date createTime;
	@ApiModelProperty(value = "修改人ID")
    private Integer modUser;
	
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public Integer getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(Integer warehouseType) {
        this.warehouseType = warehouseType;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getUseState() {
        return useState;
    }

    public void setUseState(Integer useState) {
        this.useState = useState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getModUser() {
        return modUser;
    }

    public void setModUser(Integer modUser) {
        this.modUser = modUser;
    }

}