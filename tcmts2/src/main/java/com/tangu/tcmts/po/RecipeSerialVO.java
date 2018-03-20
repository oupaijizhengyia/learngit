package com.tangu.tcmts.po;

import lombok.Data;

/**
 * Created by Administrator on 2018/1/18.
 */
@Data
public class RecipeSerialVO {

    private Integer recipeId;

    private String recipeSerial;

    private String sysRecipeCode;

    private String shelvesCode;

    private String recipientName;

    private Integer quantity;

    private Integer totalPackagePaste;
    
    private Integer shippingState;

}
