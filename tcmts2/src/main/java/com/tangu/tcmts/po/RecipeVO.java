package com.tangu.tcmts.po;

import lombok.Data;

/**
 * Created by Administrator on 2018/1/18.
 */
@Data
public class RecipeVO {

    private Integer recipeId;

    private String sysRecipeCode;

    private String shelvesCode;

    private String invoiceCode;

    private String recipientName;

    private Integer quantity;

    private Integer totalPackagePaste;
    
    private String inpatientArea;

    private Integer belong;

    private Integer ShippingState;
    
    private Integer carryId;

}
