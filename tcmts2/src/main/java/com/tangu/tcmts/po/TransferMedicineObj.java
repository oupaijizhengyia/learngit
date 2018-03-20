package com.tangu.tcmts.po;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TransferMedicineObj extends RecipeMedicine implements  Cloneable{
	private String serialNumber;
	private Integer medicineType;
	private String mnemonicCode;
	private String unit;
	private BigDecimal weight;
	private BigDecimal moneyEvery;
	public TransferMedicineObj(){
		
	}
	public TransferMedicineObj(String serialNumber,String mnemonicCode,String medicineName,
			BigDecimal weight,String unit,Integer medicineType){
		this.setSerialNumber(serialNumber);
		this.setMnemonicCode(mnemonicCode);
		this.setMedicineName(medicineName);
		this.setWeight(weight);
		this.setUnit(unit);
		this.setMedicineType(medicineType);
	}
	
	@Override
    public Object clone(){
		TransferMedicineObj o = null;
        try{
            o = (TransferMedicineObj) super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return o;
    }

}
