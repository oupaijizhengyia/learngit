package com.tangu.tcmts.service;

import com.alibaba.fastjson.JSONObject;
import com.tangu.tcmts.po.ShelvesMedicineDTO;
import com.tangu.tcmts.po.ShelvesMedicineManage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */
public interface ShelvesMedicineManageService {

    List<ShelvesMedicineDTO> listShelvesMedicineDTOByCondition(ShelvesMedicineManage shelveMedicineManage);

    void export(Object headList , List<?> list, HttpServletRequest req, HttpServletResponse resp, String xml) throws UnsupportedEncodingException, ServletException, IOException;

    JSONObject medicineChangeState(JSONObject object, JSONObject obj,
        int statePackScan) throws Exception;

    JSONObject confirmRecipe(JSONObject object, JSONObject obj,
                             int statePackScan) throws Exception;

}
