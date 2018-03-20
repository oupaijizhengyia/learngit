package com.tangu.tcmts.service.imp;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.dao.TransferMedicineMapper;
import com.tangu.tcmts.dao.TransferRecipeMapper;
import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.RecipeMedicine;
import com.tangu.tcmts.po.TransferMedicine;
import com.tangu.tcmts.po.TransferRecipeDO;
import com.tangu.tcmts.po.TransferRecipeDTO;
import com.tangu.tcmts.service.TransferRecipeService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TransferRecipeServiceImpl implements TransferRecipeService {
    @Autowired
    private TransferRecipeMapper transferRecipeMapper;
    @Autowired
    private TransferMedicineMapper transferMedicineMapper;

    @Override
    public TransferRecipeDO getTransferRecipeContent(Integer id) {
        return transferRecipeMapper.getTransferRecipeContent(id);
    }

	@Override
    public List<TransferRecipeDO> listTransferRecipes(TransferRecipeDTO transferRecipeDTO) {
        return transferRecipeMapper.listTransferRecipes(transferRecipeDTO);
    }

    @Override
    public List<TransferMedicine> getMessageByTransferRecipe(TransferMedicine transferMedicine) {
        return transferMedicineMapper.getMessageByTransferRecipe(transferMedicine);
    }

    @Override
    public Integer updateUseState(TransferRecipeDO recipeDO) {
        return transferRecipeMapper.updateUseState(recipeDO);
    }

	@Override
	public Integer updateTransferRecipePrintState(List<Integer> list) {
		return transferRecipeMapper.updateTransferRecipePrintState(list);
	}

	@Override
	public RecipeDO checkTransferRecipe(Integer id) {
		return transferRecipeMapper.checkTransferRecipe(id);
	}
	
	@Override
	public List<RecipeMedicine> findMedicineListByBillIds(List billIdlist){
		return transferMedicineMapper.findMedicineListByBillIds(billIdlist);
	}

	@Override
	public List<RecipeDO> listReceiveTransferRecipe(List billIdList) {
		return transferRecipeMapper.listReceiveTransferRecipe(billIdList);
	}

	@Override
	public Integer insertTransferRecipe(List<TransferRecipeDO> list) {
		try {
			List<TransferMedicine> medicineList = new ArrayList<>();
			List<TransferRecipeDO> tempList = new ArrayList<>();
			for (TransferRecipeDO t : list) {
				t.setMedicineQuantity(t.getTransferMedicineList().size());//草药位数
				medicineList.addAll(t.getTransferMedicineList());
				tempList.add(t);
				if (tempList.size() >= 1000) {
					transferRecipeMapper.insertTranferRecipe(tempList);
					tempList.clear();
				}
			}
			if (tempList.size() > 0) {
				transferRecipeMapper.insertTranferRecipe(tempList);
			}
			List<TransferMedicine> mTempList = new ArrayList<>();
			for (TransferMedicine t : medicineList) {
				mTempList.add(t);
				if (mTempList.size() >= 1000) {
					transferMedicineMapper.insertTransferMedicine(mTempList);
					mTempList.clear();
				}
			}
			if (mTempList.size() > 0) {
				transferMedicineMapper.insertTransferMedicine(mTempList);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return 0;
		}
	}

	@Override
	public List<TransferRecipeDO> listTransferRecipe(TransferRecipeDTO transferRecipeDTO) {
		return transferRecipeMapper.listTransferRecipe(transferRecipeDTO);
	}

	@Override
	public List<RecipeMedicine> listTransferMedicineById(List<TransferRecipeDTO> transferRecipeDTO) {
		return transferMedicineMapper.listTransferMedicineById(transferRecipeDTO);
	}

	@Override
	public boolean updateMedicineMatch(List<TransferRecipeDTO> transferRecipeDTO) {
		return transferRecipeMapper.updateMedicineMatch(transferRecipeDTO) > 0 ? true :false;
	}
	
	@Override
	public List<TransferRecipeDO> listTransferRecipeByInvoiceCode(String invoiceCode) {
		return transferRecipeMapper.listTransferRecipeByInvoiceCode(invoiceCode);
	}

	@Override
	public List<RecipeMedicine> listTrMedicineToRpMedicine(TransferMedicine record) {
		return transferMedicineMapper.listTrMedicineToRpMedicine(record);
	}	
	
	@Override
	public void replaceReceiveInfo(TransferRecipeDO transferRecipeDO) {
		transferRecipeMapper.replaceReceiveInfo(transferRecipeDO);
	}
	
	@Override
	public Integer updateByPrimaryKeySelective(TransferRecipeDO transferRecipeDO){
		return transferRecipeMapper.updateByPrimaryKeySelective(transferRecipeDO);
	}

	@Override
	public List<RecipeDO> listReceiveTransferRecipes(List billIdList) {
		// TODO Auto-generated method stub
		return transferRecipeMapper.listReceiveTransferRecipes(billIdList);
	}

  @Override
  public Object listReciveRecipeList(TransferRecipeDTO record) {
    // TODO Auto-generated method stub
    record.setBelong(1);
    return new ResponseModel(transferRecipeMapper.listReciveRecipeList(record));
  }

@Override
public Integer insertTransferRecipeTmp(List<TransferRecipeDTO> list) {
	return transferRecipeMapper.insertTransferRecipeTmp(list);
}

@Override
public Integer updateMedicineMatchById(List<TransferRecipeDTO> list) {
	// TODO Auto-generated method stub
	return transferRecipeMapper.updateMedicineMatchById(list);
}
}
