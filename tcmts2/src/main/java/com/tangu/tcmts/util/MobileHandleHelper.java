package com.tangu.tcmts.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.tangu.tcmts.controller.MobileHandleController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MobileHandleHelper {
    public static JSONObject parseRequestToObj(HttpServletRequest request) {  
        try {  
            String encoding = request.getCharacterEncoding();  
            if (encoding == null) {  
                encoding = "UTF-8";  
            }  
            InputStream ins = request.getInputStream();  
            InputStreamReader insr = new InputStreamReader(ins, encoding);  
  
            int readed = 0;
            int size = request.getContentLength();
            log.info("size="+size);
            char[] buf = new char[size];  
  
            while (readed < size) {  
                int nextSize = size - readed;  
                int nextReaded = insr.read(buf, readed, nextSize);  
                if (nextReaded != -1) {  
                    readed += nextReaded;  
                } else {  
                    break;  
                }  
            }  
            log.info("Request JSON Value ="+ String.valueOf(buf));  
            JSONObject jsonReq = JSONObject.parseObject(String.valueOf(buf));  
            return jsonReq;  
        } catch (Exception e) {  
        	e.printStackTrace();
        }  
        return null;  
    }
    @SuppressWarnings("unchecked")
	public static void setPrivilegJSONObject(JSONObject tempObj,Map privilegList) throws JSONException{
		if(privilegList.get("ACCEPT_SCAN")!=null) {
            tempObj.put("ACCEPT_SCAN", "1");
        } else {
            tempObj.put("ACCEPT_SCAN", "0");
        }
		if(privilegList.get("DISPENSE_SCAN")!=null) {
            tempObj.put("DISPENSE_SCAN", "1");
        } else {
            tempObj.put("DISPENSE_SCAN", "0");
        }
		if(privilegList.get("RE_CHECK_SCAN")!=null) {
            tempObj.put("RE_CHECK_SCAN", "1");
        } else {
            tempObj.put("RE_CHECK_SCAN", "0");
        }
		if(privilegList.get("SOAK_SCAN")!=null) {
            tempObj.put("SOAK_SCAN", "1");
        } else {
            tempObj.put("SOAK_SCAN", "0");
        }
		if(privilegList.get("START_DECOCT_SCAN")!=null) {
            tempObj.put("START_DECOCT_SCAN", "1");
        } else {
            tempObj.put("START_DECOCT_SCAN", "0");
        }
		if(privilegList.get("END_DECOCT_SCAN")!=null) {
            tempObj.put("END_DECOCT_SCAN", "1");
        } else {
            tempObj.put("END_DECOCT_SCAN", "0");
        }
		if(privilegList.get("BOIL_SCAN")!=null) {
            tempObj.put("BOIL_SCAN", "1");
        } else {
            tempObj.put("BOIL_SCAN", "0");
        }
		if(privilegList.get("PATROL_SCAN")!=null) {
            tempObj.put("PATROL_SCAN", "1");
        } else {
            tempObj.put("PATROL_SCAN", "0");
        }
		if(privilegList.get("RECIPE_HISTORY")!=null) {
            tempObj.put("RECIPE_HISTORY", "1");
        } else {
            tempObj.put("RECIPE_HISTORY", "0");
        }
		if(privilegList.get("DISPENSE_PATROL")!=null) {
            tempObj.put("DISPENSE_PATROL", "1");
        } else {
            tempObj.put("DISPENSE_PATROL", "0");
        }
		if(privilegList.get("DECOCT_PATROL")!=null) {
            tempObj.put("DECOCT_PATROL", "1");
        } else {
            tempObj.put("DECOCT_PATROL", "0");
        }
		if(privilegList.get("ALLOCATE_REQUEST")!=null) {
            tempObj.put("ALLOCATE_REQUEST", "1");
        } else {
            tempObj.put("ALLOCATE_REQUEST", "0");
        }
		if(privilegList.get("ALLOCATE_RESPONSE")!=null) {
            tempObj.put("ALLOCATE_RESPONSE", "1");
        } else {
            tempObj.put("ALLOCATE_RESPONSE", "0");
        }
		if(privilegList.get("ZN_ALLOT")!=null) {
            tempObj.put("ZN_ALLOT", "1");
        } else {
            tempObj.put("ZN_ALLOT", "0");
        }
		
		//判断是否有打包权限
		if(privilegList.get("PACK_SCAN")!=null){
			tempObj.put("PACK_SCAN", "1");
		}else{
			tempObj.put("PACK_SCAN", "0");
		}
		//判断是否有浓缩权限
		if(privilegList.get("CONCENTRATE_SCAN")!=null){
			tempObj.put("CONCENTRATE_SCAN", "1");
		}else{
			tempObj.put("CONCENTRATE_SCAN", "0");
		}
		//判断是否有先煎权限
		if(privilegList.get("FIRST_MEDICINE")!=null){
			tempObj.put("FIRST_MEDICINE", "1");
		}else{
			tempObj.put("FIRST_MEDICINE", "0");
		}
		//判断是否有审方权限
		if(privilegList.get("CHECK_BILL")!=null){
			tempObj.put("CHECK_BILL", "1");
		}else{
			tempObj.put("CHECK_BILL", "0");
		}
		//判断是否有后下权限
		if(privilegList.get("AFTER_MEDICINE")!=null){
			tempObj.put("AFTER_MEDICINE", "1");
		}else{
			tempObj.put("AFTER_MEDICINE", "0");
		}
		//判断是否有发货权限
		if(privilegList.get("IS_DELIVERY_BILL")!=null){
			tempObj.put("IS_DELIVERY_BILL", "1");
		}else{
			tempObj.put("IS_DELIVERY_BILL", "0");
		}
		//判断是否有领料权限
		if(privilegList.get("IS_PICKING_BILL")!=null){
			tempObj.put("IS_PICKING_BILL", "1");
		}else{
			tempObj.put("IS_PICKING_BILL", "0");
		}
		//判断是否有取药权限
		if(privilegList.get("MEDICINE_TAKE")!=null){
			tempObj.put("MEDICINE_TAKE", "1");
		}else{
			tempObj.put("MEDICINE_TAKE", "0");
		}
		//判断是否有上架权限
		if(privilegList.get("MEDICINE_SHELVES")!=null){
			tempObj.put("MEDICINE_SHELVES", "1");
		}else{
			tempObj.put("MEDICINE_SHELVES", "0");
		}
	}
  }
