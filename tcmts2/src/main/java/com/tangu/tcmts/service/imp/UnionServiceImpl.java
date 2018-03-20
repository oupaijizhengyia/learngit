/**
 * FileName: UnionServiceImpl
 * Author: yeyang
 * Date: 2018/1/30 15:15
 */
package com.tangu.tcmts.service.imp;

import com.tangu.tcmts.dao.UnionMapper;
import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.UnionDTO;
import com.tangu.tcmts.service.UnionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UnionServiceImpl implements UnionService {

    @Autowired
    private UnionMapper unionMapper;

    @Override
    public int createAndCancelUnion(List<UnionDTO> unionDTOList) {

        //统计相同状态数量
        int count = 0;
        //0操作失败,1操作成功
        int re = 0;
        /*
        查看关联状态的有多少个
        此时默认是true
         */
        //如果有,则获取当前的unionId
        Integer unionAll = unionDTOList.get(0).getRecipeId();
        int countUnion = 1;

        boolean state = false;
        //得出有多少个关联的处方,已经多少个不同的处方
        for (int i = 0; i <unionDTOList.size() ; i++) {
            if(unionDTOList.get(i).getUnionState() == true){
                count++;
            }
            if(unionDTOList.get(i).getRecipientName() == null){
                unionDTOList.get(i).setRecipientName("");
            }
            if (unionDTOList.get(i).getRecipientTel() == null){
                unionDTOList.get(i).setRecipientTel("");
            }
        }

        //判定是否能够关联
        for (int i = 0; i < unionDTOList.size()-1; i++) {
            if(!unionDTOList.get(i).getUnionId().equals(unionDTOList.get(i+1).getUnionId())){
                countUnion++;
            }
            if (unionDTOList.get(i).getRecipientName().equals(unionDTOList.get(i+1).getRecipientName())
                    && unionDTOList.get(i).getRecipientTel().equals(unionDTOList.get(i+1).getRecipientTel())){
                state = true;
            }
        }
        //判断是否只有表中只有一个
        if (unionDTOList.size() == 1){
            if (unionDTOList.get(0).getUnionState()){
                setUnionId(unionDTOList, 0);
                return unionMapper.updateUnion(unionDTOList);
            }
        }
        //根据选中的处方类型不同,判断动作
        if (count == 0 && state){
            //此时选中的全部是未关联的处方
            setUnionId(unionDTOList,unionDTOList.get(0).getRecipeId());
            re = unionMapper.updateUnion(unionDTOList);

        }else if (count < unionDTOList.size() && count != 0 && state){
            //此时选中的有未关联处方,有已关联处方
            setUnionId(unionDTOList, unionAll);
            re = unionMapper.updateUnion(unionDTOList);
        }else if(count == unionDTOList.size() && state){
            //此时选中的全是关联的处方
            if(countUnion == 1){
                //此时选中的是一种处方
                setUnionId(unionDTOList, 0);
            }else {
                //此时选中的是多种处方
                setUnionId(unionDTOList, unionAll);
            }
            re = unionMapper.updateUnion(unionDTOList);
        }
        return re;
    }

    /**
     * 设置unionId
     * @param unionDTOList
     * @param unionAll
     */
    private void setUnionId(List<UnionDTO> unionDTOList, Integer unionAll) {
        for (UnionDTO unionDTO:unionDTOList) {
            unionDTO.setUnionId(unionAll);
        }
    }


}
