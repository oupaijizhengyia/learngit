package com.tangu.tcmts.util.express;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tangu.common.util.MD5;
import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.HttpRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SPDExpress extends BaseExpress {
    //查找SPD状态
    private static String searchSpd(SysLookup sysLookup, String logisticCode) {
        if (sysLookup == null) {
            return null;
        }
        String name = sysLookup.getExt1();
        String password = sysLookup.getExt1();
        Date date = new Date();
        String md5 = "";
        md5 = MD5.MD516(password + "&appkey=" + name + "&number_id=" + logisticCode + "&timestamp="
                + Math.round(date.getTime() / 1000) + "&" + password, false);
        String url = sysLookup.getExt3() + "?&appkey=" + name + "&number_id=" + logisticCode + "&sign=" + md5
                + "&timestamp=" + date.getTime() / 1000;
        String result = HttpRequest.httpRequest(url, "", Constants.FORM_CONTENT_TYPE);
        return result;
    }

    @Override
    public List<Map<String, String>> searchExpress(SysLookup sysLookup, String logisticCode) {
        String result = searchSpd(sysLookup,logisticCode);
        if (result == null) {
            return list;
        }
        JSONObject response = JSONObject.parseObject(result);
        if ("0".equals(response.getString("code"))) {
            JSONArray rows = response.getJSONArray("rows");
            if (rows.size() > 0) {
                JSONObject route = (JSONObject) rows.get(0);
                JSONObject routes = JSONObject.parseObject(route.toJSONString());
                JSONArray routesArr = routes.getJSONArray("routes");
                if (routesArr != null && routesArr.size() > 0) {

                    for (int i = routesArr.size() - 1; i >= 0; i--) {
                        map = new HashMap<String, String>(16);
                        JSONObject jsonObject = (JSONObject) routesArr.get(i);
                        String dsjf = jsonObject.getString("updateAt");
                        if (dsjf == null || dsjf.length() < 6) {
                            continue;
                        }

                        String local = jsonObject.getString("actNode");
                        String mobile = jsonObject.getString("mobile");
                        String name = jsonObject.getString("updateByName");
                        String state = jsonObject.getString("state");
                        String value = local + " " + state + " " + name + " " + mobile;
                        map.put("time", dsjf.substring(0, dsjf.length() - 3));
                        map.put("state", value);
                        list.add(map);
                    }
                }
            }
        }
        return list;
    }
}
