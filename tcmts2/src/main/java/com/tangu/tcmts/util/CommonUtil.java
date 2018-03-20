package com.tangu.tcmts.util;

import com.tangu.common.util.MD5;
import com.tangu.security.JwtUserTool;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tangu.tcmts.po.CountReport;
import com.tangu.tcmts.po.Report;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;


/**
 * Created by djl on 2017/11/17.
 */
public class CommonUtil {
    /* 深复制 */
    public static Object deepClone(Object source) {

        /* 写入当前对象的二进制流 */
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos=null;
		try {
			oos = new ObjectOutputStream(bos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			oos.writeObject(source);
		} catch (IOException e) {
			e.printStackTrace();
		}

        /* 读出二进制流产生的新对象 */
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois=null;
		try {
			ois = new ObjectInputStream(bis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ois!=null){
			try {
				return ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
    }
	//设置双重MD5
	public static String DoubleMD5(String password){
    	password = MD5.encrypt(MD5.encrypt(password));
    	return password;
	}
	
	public static boolean isTrue(Object config){
		if(config==null){
			return false;
		}
		if(StringUtils.isBlank(config.toString())){
			return false;
		}
		if("Y".equals(config)){
			return true;
		}
		return false;
	}

	public static Map<String,Object> beanToMap(Object bean){
		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(bean);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					params.put(name, propertyUtilsBean.getNestedProperty(bean, name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

    /**
     * 输出统计结果
     * @param reportList
     * @return
     */
	public static CountReport setCountValue(List<Report> reportList){
        CountReport countReport = new CountReport();
        BigDecimal sumNum = new BigDecimal(0);
        BigDecimal sumTradeFreight = new BigDecimal(0);
        BigDecimal sumRetailFreight = new BigDecimal(0);

        BigDecimal extra = new BigDecimal(0);



        for (Report report: reportList) {
			if (report.getNum() == null){
				sumNum = sumNum.add(extra);
			}else {
				sumNum = sumNum.add(report.getNum());
			}
            if (report.getRetailFreight() == null){
                sumRetailFreight = sumRetailFreight.add(extra);
            }else {
                sumRetailFreight = sumRetailFreight.add(report.getRetailFreight());
            }
            if (report.getTradeFreight() == null){
                sumTradeFreight = sumTradeFreight.add(extra);
            }else {
                sumTradeFreight = sumTradeFreight.add(report.getTradeFreight());
            }
        }
        countReport.setCountNum(sumNum);
        countReport.setCountRetailFreight(sumRetailFreight);
        countReport.setCountTradeFreight(sumTradeFreight);
    	return  countReport;
	}
	
	//base64字符串转化成图片
  public static boolean encodeStr(String plainText, String fileName){  
    byte[] b= Base64.decodeBase64(plainText.replace("data:image/png;base64,",""));
    return saveFile(b, fileName);
  }
  
  public static boolean saveFile(byte[] b ,String fileName){
    File file = new File(fileName);
    try {
      FileOutputStream write = new FileOutputStream(file);
      // Base64解码
      for (int i = 0; i < b.length; ++i) {
        if (b[i] < 0) {// 调整异常数据
          b[i] += 256;
        }
      }
      write.write(b);
      write.flush();
      write.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }
  
  public static boolean deleteFile(String fileName) {
    File file = new File(fileName);
    // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
    if (file.exists() && file.isFile()) {
        if (file.delete()) {
            System.out.println("删除单个文件" + fileName + "成功！");
            return true;
        } else {
            System.out.println("删除单个文件" + fileName + "失败！");
            return false;
        }
    } else {
        System.out.println("删除单个文件失败：" + fileName + "不存在！");
        return false;
    }
  }
  
  public static String getFileName(String imgPath){
    StringBuffer filePath = new StringBuffer();
    filePath.append(File.separator)
      .append(JwtUserTool.getTenant())
      .append(File.separator)
      .append(imgPath)
      .append(File.separator)
      .append(JwtUserTool.getRoleId())
      .append(UUID.randomUUID().toString())
      .append(".png");
    return filePath.toString();
  }
}

