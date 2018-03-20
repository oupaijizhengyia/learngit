package com.tangu.tcmts.util.express;

import com.tangu.common.util.MD5;
import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.util.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.*;

public class ChinaPostExpress extends BaseExpress {
    private static String seachYz(SysLookup sysLookup, String recipeCode, String logisticCode, String hospitalCode) {
        if (sysLookup == null) {
            return null;
        }
        String name = sysLookup.getExt1();
        String password = sysLookup.getExt2();
        Date date = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formaterL = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String md5 = MD5.MD516(name + password + formater.format(date), true);
        String Xmlresult = "<?xml  version=\"1.0\"  encoding=\"UTF-8\" ?>" +
                "<Manifest>" +
                "<Head>" +
                "<MessageType>000002</MessageType>" +
                "<USERNAME>" + name + "</USERNAME>" +
                "<Signature>" + md5 + "</Signature>" +
                "<SendTime>" + formaterL.format(date) + "</SendTime>" +
                "<Version>1.0</Version>" +
                "</Head>" +
                "<Declaration>" +
                "<EWay>";
        if (logisticCode != null) {
            Xmlresult += "<Preexpressid>" + logisticCode + "</Preexpressid>";
        } else {
            Xmlresult += "<Preexpressid></Preexpressid>";
        }
        Xmlresult += "<HospitalCode>" + hospitalCode + "</HospitalCode>";
        if (recipeCode != null) {
            Xmlresult += "<RecipeNo>" + recipeCode + "</RecipeNo>";
        } else {
            Xmlresult += "<RecipeNo></RecipeNo>";
        }
        Xmlresult += "</EWay>" +
                "</Declaration>" +
                "</Manifest>";
        System.out.println("Xmlresult=" + Xmlresult);
        Object[] args = {Xmlresult};
        String result = HttpRequest.WebserviceRequest(sysLookup.getExt3(), "http://service.cm.com", "CallServiceRoute", args);
        System.out.println("result=" + result);
        return result;
    }

    @Override
    public List<Map<String, String>> searchExpress(SysLookup sysLookup, String recipeCode, String logisticCode, String hospitalCode) {
        String xmlResult = seachYz(sysLookup, recipeCode, logisticCode, hospitalCode);
        if (xmlResult == null) {
            return list;
        }
        String[] ss = xmlResult.split("&lt;expressinfo>");
        for (int i = ss.length - 1; i >= 1; i--) {
            ss[i] = ss[i].replaceAll("&gt;", ">");
            map = new HashMap<String, String>(16);
            String string = ss[i].substring(0, ss[i].indexOf("&lt;/expressinfo>"));
            if (string.indexOf("-") > -1) {
                map.put("time", string.substring(0, 16).trim());
                map.put("state", string.substring(20).trim());
            }
            list.add(map);
        }
        if (list.size() > 20) {
            list = new ArrayList<Map<String, String>>();
        }
        return list;
    }
}
