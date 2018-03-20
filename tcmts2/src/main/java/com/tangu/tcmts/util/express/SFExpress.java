package com.tangu.tcmts.util.express;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.HttpRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SFExpress extends BaseExpress {

    //查找顺丰状态
    private static String searchSf(SysLookup sysLookup, String logisticCode) {
        if (sysLookup == null) {
            return null;
        }
        String name = sysLookup.getExt1();
        String password = sysLookup.getExt2();
        String string = sysLookup.getExt3() + "?code=" + logisticCode + "%2525" + name + "%2525"
                + password + "%2525" + System.currentTimeMillis();
        String result = HttpRequest.httpRequest(string, "", Constants.FORM_CONTENT_TYPE);
        return result;
    }

    @Override
    public List<Map<String, String>> searchExpress(SysLookup sysLookup, String logisticCode) {
        String result = searchSf(sysLookup,logisticCode);
        if (result == null) {
            return list;
        }
        JSONArray json = JSONArray.parseArray(result);
        map = new HashMap<String, String>(16);
        for (int i = json.size() - 1; i >= 0; i--) {
            map = new HashMap<String, String>(16);
            JSONObject ob = (JSONObject) json.get(i);
            String time = ob.getString("acceptTime");
            String opCode = ob.getString("opCode");
            String status = ob.getString("remark");
            if (Integer.parseInt(opCode) != 8000) {
                time = time.substring(0, time.length() - 3);
                map.put("time", time);
                map.put("state", status);
                list.add(map);
            } else {
                continue;
            }
        }
        return list;
    }
}
