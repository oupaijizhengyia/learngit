package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
/**
 * @author:yinghuaxu
 * @Description:特殊扣率DO
 * @Date: create in 10:29 2017/10/23
 */
@Data
public class SpecialDiscount {
    private Integer id;

    private Integer settleCompanyId;
    @ApiModelProperty(value = "药品Id")
    private Integer medicineId;
    @ApiModelProperty(value = "特殊扣率",required = true)
    private BigDecimal discount;

    private BigDecimal taxRate;
    @ApiModelProperty(value = "药品名称",required = true)
    private String name_medicineId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpecialDiscount that = (SpecialDiscount) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (settleCompanyId != null ? !settleCompanyId.equals(that.settleCompanyId) : that.settleCompanyId != null) {
            return false;
        }
        if (medicineId != null ? !medicineId.equals(that.medicineId) : that.medicineId != null) {
            return false;
        }
        if (discount != null ? !discount.equals(that.discount) : that.discount != null) {
            return false;
        }
        return taxRate != null ? taxRate.equals(that.taxRate) : that.taxRate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (settleCompanyId != null ? settleCompanyId.hashCode() : 0);
        result = 31 * result + (medicineId != null ? medicineId.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (taxRate != null ? taxRate.hashCode() : 0);
        return result;
    }
}