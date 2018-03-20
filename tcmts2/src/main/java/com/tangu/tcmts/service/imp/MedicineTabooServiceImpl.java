package com.tangu.tcmts.service.imp;

import com.tangu.tcmts.dao.MedicineTabooMapper;
import com.tangu.tcmts.po.MedicineTaboo;
import com.tangu.tcmts.service.MedicineTabooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MedicineTabooServiceImpl implements MedicineTabooService {
    @Autowired
    private MedicineTabooMapper medicineTabooMapper;

    @Override
    public List<MedicineTaboo> getMedicineTabooList(MedicineTaboo medicineTaboo) {
        return medicineTabooMapper.getMedicineTabooList(medicineTaboo);
    }

    @Override
    public MedicineTaboo getRepeatMedicineTaboo(MedicineTaboo medicineTaboo) {
        return medicineTabooMapper.getRepeatMedicineTaboo(medicineTaboo);
    }

    /* @Override
     public MedicineTaboo getParentRepeatMedicineTaboo(MedicineTaboo medicineTaboo) {
         return medicineTabooMapper.getParentRepeatMedicineTaboo(medicineTaboo);
     }
 */
    @Override
    public Integer deleteMedicineTaboo(Integer id) {
        return medicineTabooMapper.deleteMedicineTaboo(id);
    }

    @Override
    public Integer insertMedicineTaboo(MedicineTaboo medicineTaboo) {
        return medicineTabooMapper.insertMedicineTaboo(medicineTaboo);
    }

    @Override
    public Integer updateMedicineTaboo(MedicineTaboo medicineTaboo) {
        return medicineTabooMapper.updateMedicineTaboo(medicineTaboo);
    }

    @Override
    public Map<String, String> getMedicineTabooUnionMap() {
        Map<String, String> tabooUnionMap = medicineTabooMapper.getMedicineTabooUnionMap().stream()
                .collect(Collectors.toMap(MedicineTaboo::getSubMedicineName, MedicineTaboo::getTabooTypeName));
        return tabooUnionMap;
    }

}
