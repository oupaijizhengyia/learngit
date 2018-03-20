package com.tangu.tcmts.controller;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.dao.PrintItemMapper;
import com.tangu.tcmts.po.*;
import com.tangu.tcmts.service.PrintService;
import com.tangu.tcmts.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author:djl
 * @Description:打印模块MODEL
 * @Date: create in 14:33 2017/10/26
 */
@Api(value = "/print", description = "打印模块")
@RestController
@RequestMapping("/print")
@Slf4j
public class PirntController {
    @Autowired
    private PrintService printService;
    @Autowired
    private PrintItemMapper printItemMapper;
    @Value("${tangu.file.uploadPath}")
    private String path;

    @ApiOperation(value = "获取打印字段列表", notes = "无参数",
            response = PrintField.class, responseContainer = "List")
    @RequestMapping(value = "/findPrintFieldList", method = RequestMethod.GET)
    public Object listPrintField() {
        List<PrintField> printFieldList = printService.listPrintField();
        return new ResponseModel(printFieldList);
    }

    @ApiOperation(value = "获取打印模板列表", notes = "无参数",
            response = PrintPage.class, responseContainer = "List")
    @RequestMapping(value = "/findPrintPageList", method = RequestMethod.POST)
    public Object listPrintPage(@RequestBody PrintPage printPage) {
        return new ResponseModel(printService.listPrintPage(printPage));
    }

    @ApiOperation(value = "保存打印模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "savePrintItem", value = "保存打印模板", dataType = "PrintPage")
    })
    @RequestMapping(value = "/savePrintItem", method = RequestMethod.POST)
    public Object savePrintItem(@RequestBody PrintPage printPage) {
        printPage.setModUser(JwtUserTool.getId());
        for (PrintItem item : printPage.getPrintItemList()) {
            item.setFontFamily(item.getFontFamily() == null ? "宋体" : item.getFontFamily());
            item.setFontWeight(item.getFontWeight() == null ? false : item.getFontWeight());
            item.setFontStyle(item.getFontStyle() == null ? false : item.getFontStyle());
            item.setFontDecoration(item.getFontDecoration() == null ? false : item.getFontDecoration());
            item.setTextAlign(item.getTextAlign() == null ? "left" : item.getTextAlign());
            item.setAutoSize(item.getAutoSize() == null ? false : item.getAutoSize());
        }
        Integer resultRow = printService.savePrintItem(printPage);
        if (resultRow > 0) {
            return new ResponseModel(Constant.OPRATION_SUCCESS);
        }
        if (resultRow == -1) {
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, "面单名称不允许重复");
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(value = "查看打印模板", response = PrintPage.class)
    @RequestMapping(value = "/getPrintPage", method = RequestMethod.POST)
    public Object getPrintPage(@RequestBody PrintPage printPage) {
        return new ResponseModel(printService.getPrintPageById(printPage.getId()));
    }

    @ApiOperation(value = "打印模板下拉", notes = "recipeDetail.处方单 recipeTag.标签 employeeCode.员工条码")
    @RequestMapping(value = "/getTagPrintPage/{billType}", method = RequestMethod.GET)
    public Object getTagPrintPage(@PathVariable String billType) {
    	if(billType.equals("recipe")){
    		billType=Constants.PrintType.RECIPE;
    	}
        return new ResponseModel(printService.listPrintDropDown(billType));
    }

    @ApiOperation(value = "获取打印机列表")
    @RequestMapping(value = "getPrinterName", method = RequestMethod.GET)
    public Object getPrinterName() {
        return new ResponseModel(printService.listDropDownPrinter());
    }

    @ApiOperation(value = "删除面单", notes = "删除面单，传入id")
    @ApiImplicitParam(name = "printPage", value = "删除面单", dataType = "PrintPage")
    @RequestMapping(value = "/deletePrintPage", method = RequestMethod.POST)
    public Object deletePrintPage(@RequestBody PrintPage printPage) {
        Integer result = printService.deletePrintPage(printPage.getId());
        if (result.equals(1)) {
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(value = "新增修改面单", notes = "新增修改面单")
    @ApiImplicitParam(name = "printPage", value = "新增修改面单", dataType = "PrintPage")
    @RequestMapping(value = "/createAndSavePrintPage", method = RequestMethod.POST)
    public Object createAndSavePrintPage(@RequestBody PrintPage printPage) {
        Integer result = 0;
        printPage.setModUser(JwtUserTool.getId());
        if (printService.getRepeatPageName(printPage).size() > 0) {
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.PAGE_EXIST);
        }
        if (printPage.getId() != null) {
            printPage.setModTime(new Date(System.currentTimeMillis()));
            result = printService.updatePrintPage(printPage);
        } else {
            result = printService.insertPrintPage(printPage);
        }
        PrintPage printPageId = new PrintPage();
        printPageId.setId(printPage.getId());
        if (result.equals(1)) {
            return new ResponseModel(printService.getPrintPageById(printPageId.getId())).attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(value = "复制面单", notes = "复制面单,传入id")
    @ApiImplicitParam(name = "printPage", value = "复制面单", dataType = "PrintPage")
    @RequestMapping(value = "/copyPrintPage", method = RequestMethod.POST)
    public Object copyPrintPage(@RequestBody PrintPage printPage) {
        Integer result = 0;
        PrintPage newPrintPage = printService.getPrintPageById(printPage.getId());
        PrintPage checkPage = new PrintPage();
        checkPage.setPageName(printPage.getPageName());
        if (printService.getRepeatPageName(checkPage).size() > 0) {
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.PAGE_EXIST);
        }
        newPrintPage.setPageName(printPage.getPageName());
        newPrintPage.setModUser(JwtUserTool.getId());
        result = printService.insertPrintPage(newPrintPage);
        if (CollectionUtils.isNotEmpty(newPrintPage.getPrintItemList())) {
            printItemMapper.insertBatch(newPrintPage);
        }
        PrintPage printPageId = new PrintPage();
        printPageId.setId(printPage.getId());
        if (result.equals(1)) {
            return new ResponseModel(printService.getPrintPageById(printPageId.getId())).attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(value = "上传背景图片", notes = "上传背景图片,传入id")
    @ApiImplicitParam(name = "printPage", value = "上传背景图片", dataType = "PrintPage")
    @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
    public Object uploadBackgroundImg(HttpServletRequest request, @RequestParam MultipartFile file)
            throws IOException {
        Integer result = 0;
        InputStream fileInputStream = file.getInputStream();
        String fileName = JwtUserTool.getTenant() + JwtUserTool.getRoleId() + UUID.randomUUID().toString();
        String filePath = path + File.separator + JwtUserTool.getTenant() + File.separator + "printImg";
        String storePath = "/" + JwtUserTool.getTenant() +
                "/" + "printImg" + "/" + fileName + ".jpg";
        //设置printPage值
        PrintPage printPage = new PrintPage();
        printPage.setId(Integer.parseInt(request.getParameter("id")));
        printPage.setImageWidth(Integer.parseInt(request.getParameter("imageWidth")));
        printPage.setImageHeight(Integer.parseInt(request.getParameter("imageHeight")));
        printPage.setPrintBackgroundImage(Boolean.parseBoolean(request.getParameter("printBackgroundImage")));
        printPage.setModUser(Integer.parseInt(JwtUserTool.getRoleId()));
        printPage.setModTime(new Date(System.currentTimeMillis()));

        if (printPage.getPrintBackgroundImage()) {
            printPage.setBackgroundImage(storePath);
            storePath =  "/" + JwtUserTool.getTenant() +
                    "/" + "printImg" + "/" + fileName +"_img.jpg";
            printPage.setImage(storePath);
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath, fileName + ".jpg"));
            BufferedImage buffer = ImageIO.read(fileInputStream);
            //判断图片宽高大小是否与输入的宽高乘四倍后的值一致
            log.info("buffer.getWidth()="+buffer.getWidth());
            log.info("buffer.getHeight()="+buffer.getHeight());
            log.info("printPage.getImageWidth()="+printPage.getImageWidth() * 4);
            log.info("printPage.getImageHeight()="+printPage.getImageHeight() * 4);
            boolean requirement = buffer.getWidth() <= printPage.getImageWidth() * 4 ||
                     buffer.getHeight() <= printPage.getImageHeight() * 4;
            //一致原图插入
            if (requirement) {
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath, fileName + "_img.jpg"));
            } else {
                try {
                    //不一致，扩大四倍宽高
                    printPage.setImageWidth(printPage.getImageWidth() * 4);
                    printPage.setImageHeight(printPage.getImageHeight() * 4);
                    double wRatio = (printPage.getImageWidth()).doubleValue() / buffer.getWidth(); //宽度的比例
                    double hRatio = (printPage.getImageHeight()).doubleValue() / buffer.getHeight(); //高度的比例
                    Image image = buffer.getScaledInstance(printPage.getImageWidth(), printPage.getImageHeight(), Image.SCALE_SMOOTH);
                    AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(wRatio, hRatio), null);
                    image = op.filter(buffer, null);
                    ImageIO.write((BufferedImage) image, "jpg", new File(filePath, fileName + "_img.jpg"));
                }catch (Exception e){
                	log.error("upload print img exception", e);
                    return new ResponseModel().attr(ResponseModel.KEY_ERROR, "上传失败");
                }
            }
        } else {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath, fileName + ".jpg"));
            BufferedImage buffer = ImageIO.read(fileInputStream);
            printPage.setImage(storePath);
            printPage.setImageWidth(buffer.getWidth());
            printPage.setImageHeight(buffer.getHeight());
        }
        result = printService.updatePrintPage(printPage);
        if (result.equals(1)) {
            return new ResponseModel(printPage).attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }
}
