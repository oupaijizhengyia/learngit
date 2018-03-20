package com.tangu.tcmts.util.express;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tangu.common.util.MD5;
import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.HttpRequest;

import java.util.*;

public class SZExpress extends BaseExpress {

    private static String searchSz(SysLookup sysLookup, String logisticCode) {
        JSONObject json1 = new JSONObject();
        json1.put("expressNumber", logisticCode);

        JSONObject json = new JSONObject();
        json.put("custCode", sysLookup.getExt1());//帐号
        json.put("custPassword", MD5.encrypt(sysLookup.getExt2()));//密码
        json.put("orderRoute", json1);//快递单号

        String result = HttpRequest.httpRequest(sysLookup.getExt3(), json.toString(), Constants.JSON_CONTENT_TYPE);
        return result;
    }

    @Override
    public List<Map<String, String>> searchExpress(SysLookup sysLookup, String logisticCode) {
        String result = searchSz(sysLookup, logisticCode);
        if (result == null) {
            return list;
        }
        JSONObject response = JSONObject.parseObject(result);
        if ("00000".equals(response.getString("resultCode"))) {// 查询成功
            list = new ArrayList<Map<String, String>>();
            JSONArray array = response.getJSONArray("route");// 返回的信息
            if (array == null) {
                return null;
            }
            Iterator<Object> it = array.iterator();
            while (it.hasNext()) {
                map = new HashMap<String, String>();
                JSONObject ob = (JSONObject) it.next();
                if (ob.getString("remark") != null && !"".equals(ob.getString("remark"))) {
                    map.put("state", ob.getString("remark"));
                }
                String time = ob.getString("acceptTime");
                if (time != null && !"".equals(time)) {
                    map.put("time", time.substring(0, time.length() - 3));
                }
                list.add(map);
            }
            return list;
        } else if ("10002".equals(response.getString("resultCode"))) {
            logger.error("顺真查物流信息-登录帐号密码错误！");
        } else if ("10001".equals(response.getString("resultCode"))) {
            logger.error("顺真内部错误！");
        }
        return null;
    }
}
