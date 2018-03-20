package com.tangu.tcmts.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by djl on 2017/11/8.
 */
public class TransformUtil {
    private static String NUM = "零壹贰叁肆伍陆柒捌玖";
    private static String UNIT = "仟佰拾个";
    private static String GRADEUNIT = "仟万亿兆";
    private static String DOTUNIT = "角分厘";
    private static int GRADE = 4;
    private static final String ZH_CN_ZERO = "零";
    private static DecimalFormat decimalFormat = new DecimalFormat("######.00");

    public static String dateToBig(String intPart) {
        //得到转换后的日期
        int grade; //级长
        String result = "";
        //得到当级长
        grade = intPart.length();
        if (grade == 4) {
            //对每级数字处理
            for (int i = 0; i <= grade - 1; i++) {
                result += getSubUnit(intPart.substring(i, 1));//转换大写
            }
        }
        if (grade == 1) {
            result = ZH_CN_ZERO + getSubUnit(intPart);
        }
        if (grade == 2) {
            result = intToBig(intPart);
        }
        return result;
    }

    private static String getSubUnit(String strVal) {
        //数值转换
        String rst = "";
        for (int i = 0; i < strVal.length(); i++) {
            String s = strVal.substring(i, i + 1);
            int num = Integer.valueOf(s);
            if (num == 0) {
                //“零”作特殊处理
                if (i != strVal.length()){
                    rst += ZH_CN_ZERO;//转换后数末位不能为零
                }
            } else {
                //If IntKey = 1 And i = 2 Then
                //“壹拾”作特殊处理
                //“壹拾”合理
                //Else
                rst += NUM.substring(num, num + 1);
                //End If
                //追加单位 |个位不加单位
                if (i != strVal.length() - 1) {
                    rst += UNIT.substring(i + 4 - strVal.length(), i + 4 - strVal.length() + 1);
                }

            }
        }
        return rst;
    }

    public static String intToBig(String intPart) {
        //得到转换后的大写（整数部分）
        int grade; //级长
        String result = "";
        String strTmp = "";
        //得到当级长
        grade = intPart.length() / GRADE;
        //调整级次长度
        if (intPart.length() % GRADE != 0) {
            grade += 1;
        }
        //对每级数字处理
        for (int i = grade; i >= 1; i--) {
            strTmp = getNowGradeVal(intPart, i);//取得当前级次数字
            result += getSubUnit(strTmp);//转换大写
            result = dropZero(result);//除零
            //加级次单位
            if (i > 1) {
                //单位不能相连续
                if ("零零零零".equals(getSubUnit(strTmp))) {
                    result += ZH_CN_ZERO + GRADEUNIT.substring(i - 1, i);
                } else {
                    result += GRADEUNIT.substring(i - 1, i);
                }
            }
        }
        return result;
    }

    private static String dropZero(String strVal) {
        //去除连继的“零”
        String strRst;
        String strBefore; //前一位置字符
        String strNow;    //现在位置字符
        strBefore = strVal.substring(0, 1);
        strRst = strBefore;

        for (int i = 1; i < strVal.length(); i++) {
            strNow = strVal.substring(i, i + 1);
            if (isZhCNZero(strNow) && isZhCNZero(strBefore)) {

            } else {
                strRst += strNow;
            }
            strBefore = strNow;
        }
        //末位去零
        if (isZhCNZero(strRst.substring(strRst.length() - 1, strRst.length()))) {
            strRst = strRst.substring(0, strRst.length() - 1);
        }
        return strRst;
    }

    private static String getNowGradeVal(String strVal, int grade) {
        //得到当前级次的串
        String rst = "";
        if (strVal.length() <= grade * GRADE) {
            rst = strVal.substring(0, strVal.length() - (grade - 1) * GRADE);
        } else {
            rst = strVal.substring(strVal.length() - grade * GRADE, strVal.length() - (grade - 1) * GRADE);
        }
        return rst;
    }

    public static String toBigAmt(BigDecimal amount) {
        String amt = decimalFormat.format(amount);//此处可能有问题
        String dotPart = ""; //取小数位
        String intPart = ""; //取整数位
        int dotPos;
        if ((dotPos = amt.indexOf('.')) != -1) {
            intPart = amt.substring(0, dotPos);
            dotPart = amt.substring(dotPos + 1);
        } else {
            intPart = amt;
        }
        String intBig = intToBig(intPart);
        String dotBig = dotToBig(dotPart);
        if ((dotBig.length() == 0) && (intBig.length() != 0)) {
            return intBig + "元整";
        } else if ((dotBig.length() == 0) && (intBig.length() == 0)) {
            return intBig + "零元";
        } else if ((dotBig.length() != 0) && (intBig.length() != 0)) {
            return intBig + "元" + dotBig;
        } else {
            return dotBig;
        }
    }

    public static String dotToBig(String dotPart) {
        //得到转换后的大写（小数部分）
        String strRet = "";
        for (int i = 0; i < dotPart.length() && i < 3; i++) {
            int num;
            if ((num = Integer.valueOf(dotPart.substring(i, i + 1))) != 0) {
                strRet += NUM.substring(num, num + 1) + DOTUNIT.substring(i, i + 1);
            }
        }
        return strRet;
    }

    public static String formatCurrency(BigDecimal num) {
        DecimalFormat decimalFormat = new DecimalFormat("######");
        return decimalFormat.format(num);
    }

    public static String formatCurrencyPoint(BigDecimal num, Boolean hasSeparator, int fractionNum) {
        String formatedNum = "";
        NumberFormat numFormat = NumberFormat.getInstance();
        numFormat.setMaximumFractionDigits(fractionNum);
        formatedNum = numFormat.format(num);
        if (isZero(formatedNum, fractionNum)) {
            return formatedNum.substring(0, formatedNum.indexOf('.'));
        }
        return formatedNum;
    }
    
    public static Boolean isZero(Object value, int num) {
        String temp = value.toString();
        String temps = temp.substring(temp.indexOf('.') + 1, temp.length());
        int length;
        if (temps.length() >= num) {
            temp = temp.substring(temp.indexOf('.') + 1, temp.indexOf('.') + 1 + num);
            length = num;
        } else {
            temp = temps;
            length = temps.length();
        }
        Boolean isZero = true;
        int i = 0;
        while (i < length) {
            if (temp.toString().charAt(i) != '0') {
                isZero = false;
            }
            i++;
        }
        return isZero;
    }

    public static Boolean isZhCNZero(String str) {
        if (ZH_CN_ZERO.equals(str)) {
            return true;
        }
        return false;
    }
}
