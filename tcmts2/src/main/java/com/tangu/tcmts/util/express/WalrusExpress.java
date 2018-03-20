package com.tangu.tcmts.util.express;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tangu.common.util.MD5;
import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.util.HttpRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalrusExpress extends BaseExpress {
    private static String searchHx(SysLookup sysLookup, String logisticCode) {
        if (sysLookup == null) {
            return null;
        }
        JSONObject json = new JSONObject();
        String md5 = "";
        String para = "";
        json.put("appkey", sysLookup.getExt1());
        json.put("data", "{\"waybill\":\"" + logisticCode + "\"" + "}");
        md5 = MD5.MD516(sysLookup.getExt1() + "{\"waybill\":\"" + logisticCode + "\"" + "}", true);
        json.put("sign", md5);
        para = "appkey=" + json.getString("appkey") + "&data=" + json.getString("data") + "&sign=" + json.getString("sign");
        String result = HttpRequest.sendGet(sysLookup.getExt3(), para);
        JSONObject response = JSONObject.parseObject(result);
        logger.info(logisticCode + "----查单参数：" + para + "\n" + ("0".equals(response.getString("response_error")) ? "查询成功" : "查询失败") + "----查询返回结果：all==" + response.toString());
        if (!"0".equals(response.getString("response_error"))) {
            return null;
        }
        return response.getJSONArray("response_rows").toString();
    }

    @Override
    public List<Map<String, String>> searchExpress(SysLookup sysLookup, String logisticCode) {
        String result = searchHx(sysLookup,logisticCode);
        if (result == null) {
            return list;
        }
        JSONArray rows = JSONArray.parseArray(result);
        if (rows == null || rows.size() == 0) {
            return list;
        }
        JSONObject route = (JSONObject) rows.get(0);
        JSONObject routes = JSONObject.parseObject(route.toJSONString());
        JSONArray routesArr = routes.getJSONArray("act");
        if (routesArr != null && routesArr.size() > 0) {
            JSONObject jsonObject = null;
            String dsjf = null;
            String value = null;
            for (int i = routesArr.size() - 1; i >= 0; i--) {
                jsonObject = (JSONObject) routesArr.get(i);
                if (!jsonObject.containsKey("message")) {
                    continue;
                }
                map = new HashMap<String, String>(16);
                dsjf = jsonObject.getString("created_at");
                value = jsonObject.getString("message");
                map.put("time", dsjf.substring(0, dsjf.length() - 3));
                map.put("state", value);
                list.add(map);
            }
        }
        return list;
    }
}
