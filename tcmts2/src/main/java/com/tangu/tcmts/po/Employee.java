package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
/**
 * @author:yinghuaxu
 * @Description:Employee
 * @Date: create in 14:33 2017/10/24
 */
@Data
public class Employee {
    private Integer id;

    private String employeeName;

    private String newEmployeePassword;

    private String initialCode;
    @ApiModelProperty(value = "0.在职,1.停职")
    private Integer useState;

    private String employeeCode;

    private String employeeTel;

    private Integer roleId;

    private String roleName;

    private String employeePassword;
    @ApiModelProperty(value = "是否为操作员 1.是 0.否")
    private Integer isOperator;

    private Date createTime;

    private Date modTime;

    private String jwtToken;
    @ApiModelProperty("权限名称")
    private String functionName;
    @ApiModelProperty("权限编码")
    private String functionCode;
    @ApiModelProperty("1 = 处方新增并审核请求的接口")
    private int examineType;
    private Integer recipeId;
    private Integer belong;
}