package com.hengtiansoft.common.poi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hengtiansoft.common.poi.bean.SimpleExcelBean;
import com.hengtiansoft.common.poi.bean.SimpleMulityExcelBean;

public class ExcelUtilDemo {

    private static final Logger log= LoggerFactory.getLogger(ExcelUtilDemo.class);
    private List<List<String>> content = new ArrayList<List<String>>();
    {

        // 数据从数据库取
        List<String> row1 = new ArrayList<String>();
        row1.add("11");
        row1.add("12");
        List<String> row2 = new ArrayList<String>();
        row2.add("21");
        row2.add("22");
        List<String> row3 = new ArrayList<String>();
        row3.add("31");
        row3.add("32");
        List<String> row4 = new ArrayList<String>();
        row4.add("41");
        row4.add("42");
        content.add(row1);
        content.add(row2);
        content.add(row3);
        content.add(row4);

    }

    /**
    * Description: 导出excel
    *
    */
    @RequestMapping("/toExcel")
    public void toExcel(HttpServletRequest request, HttpServletResponse response) {
        SimpleExcelBean bean = new SimpleExcelBean();
        bean.setTitle("单Sheet");
        bean.setColNames(new String[] { "第一列", "第二列" });
        bean.setContent(content);
        try {
            PoiUtil.toExcel(bean, response, request);
        } catch (IOException e) {
            log.error("msg",e);
        }
    }

    /**
     * Description: 导出excel
     *
     */
    @RequestMapping("/toExcelMulity")
    public void toExcelMulity(HttpServletRequest request, HttpServletResponse response) {
        SimpleMulityExcelBean mulityExcelBean = new SimpleMulityExcelBean();
        mulityExcelBean.setTitle("多Sheet");
        // 第一页
        SimpleExcelBean bean1 = new SimpleExcelBean();
        bean1.setTitle("第一页");
        bean1.setColNames(new String[] { "第一列", "第二列" });
        bean1.setContent(content);

        // 第二页
        SimpleExcelBean bean2 = new SimpleExcelBean();
        bean2.setTitle("第二页");
        bean2.setColNames(new String[] { "第一列", "第二列" });
        bean2.setContent(content);
        mulityExcelBean.getBeans().add(bean1);
        mulityExcelBean.getBeans().add(bean2);
        try {
            PoiUtil.toExcelMulity(mulityExcelBean, response, request);
        } catch (IOException e) {
            log.error("msg",e);
        }

    }

    @RequestMapping("/getExcel")
    @ResponseBody
    public void getExcel(@RequestParam MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            String[][] msg = PoiUtil.getFromExcel(2, in);
            for (String[] strings : msg) {
                for (String string : strings) {
                    System.out.println(string);
                }
            }
        } catch (IOException e) {
            log.error("msg",e);
        }
    }
}
