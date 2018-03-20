package com.tangu.tcmts.util.express;

import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.util.HttpRequest;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamatoExpress extends BaseExpress {
    private static final String STRO3 = "03";
    private static final String STR11 = "11";
    private static final String STR26 = "26";
    private static final String STR34 = "34";
    private static final String STR35 = "35";
    private static final String STR90 = "90";

    private static String getYamatoLogisticsInfo(String[] str) {
        String code = str[5].replaceAll("\"", "").trim();
        if (STRO3.equals(code)) {
            return "宅急便已收取快件";
        } else if (STR11.equals(code)) {
            return "快件已出发";
        } else if (STR26.equals(code)) {
            return "快件暂存【" + str[11].replaceAll("\"", "") + "】";
        } else if (STR34.equals(code)) {
            return "快件在【" + str[11].replaceAll("\"", "") + "】装车,已发往下一站";
        } else if (STR35.equals(code)) {
            return "快件到达【" + str[11].replaceAll("\"", "") + "】";
        } else if (STR90.equals(code)) {
            return "快件已配送";
        } else {
            return str[6].replaceAll("\"", "");
        }
    }

    //宅急便查单
    private static String searchYamato(SysLookup sysLookup, String logisticCode) {
        String url = sysLookup.getExt3() + "?cid=" + sysLookup.getExt1() + "&noFlag=0&historyFlag=2&NO=" + logisticCode;
        String resultXml = HttpRequest.get(url, null, "GBK");
        logger.info("rsesultXml:" + resultXml);
        return resultXml;
    }

    @Override
    public List<Map<String, String>> searchExpress(SysLookup sysLookup, String logisticCode) {
        String result = searchYamato(sysLookup, logisticCode);
        if (result == null) {
            return list;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hhmm");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Document doc;
        try {
            doc = DocumentHelper.parseText(result);
            Element root = doc.getRootElement();
            String resultCode = root.getText();
            logger.info("宅急便查单resultCode:" + resultCode);
            if (resultCode == null || StringUtils.isBlank(resultCode)) {
                return list;
            }
            String[] strs = null;
            StringBuffer sb;
            if (resultCode.contains("||")) {
                strs = resultCode.split("\\|\\|");
                String[] str = null;
                for (String string : strs) {
                    map = new HashMap<String, String>(16);
                    sb = new StringBuffer();
                    str = string.split(",");
                    sb.append("状态：" + getYamatoLogisticsInfo(str));
                    sb.append(",配送人员：" + str[14].replaceAll("\"", ""));
                    sb.append(",电话：[" + str[15].replaceAll("\"", "") + "]");
                    map.put("state", sb.toString());
                    map.put("time", sdf1.format(sdf.parse(str[7].replaceAll("\"", "") + " " + str[8].replaceAll("\"", ""))));
                    list.add(map);
                }
            } else {
                map = new HashMap<String, String>(16);
                strs = resultCode.split(",");
                sb = new StringBuffer();
                sb.append("状态：" + getYamatoLogisticsInfo(strs));
                sb.append(",配送人员：" + strs[14].replaceAll("\"", ""));
                sb.append(",电话：[" + strs[15].replaceAll("\"", "") + "]");
                map.put("state", sb.toString());
                map.put("time", sdf1.format(sdf.parse(strs[7].replaceAll("\"", "") + " " + strs[8].replaceAll("\"", ""))));
                list.add(map);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("宅急便对接。。。：", e.toString());
        }
        return null;
    }
}
