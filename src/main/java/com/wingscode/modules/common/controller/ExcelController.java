package com.wingscode.modules.common.controller;

import com.alibaba.druid.util.StringUtils;
import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.service.LeadsService;
import com.wingscode.modules.common.service.StatisticalService;
import com.wingscode.modules.common.vo.AdminWorkerVo;
import com.wingscode.util.ExcelOutUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author：张延
 * Date：2020-03-28 11:15
 * Description：<描述>
 */
@RestController
@RequestMapping("/excel")
@Api("文件导出")
public class ExcelController  {

    @Autowired
    private  LeadsService LeadsService;
    @Autowired
    private StatisticalService statisticalService;

    /**
     * 导出数据为Excel文件
     * 下面注释的代码为直接响应到服务器的
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/excelOut")
    @ApiOperation("leads管理")
    public String exportExcel(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> params) {

        Long parentId= Long.parseLong(StringUtils.isEmpty(String.valueOf(params.get("parentId"))) ? "0" : (String) params.get("parentId"));
        //获取数据
        List<LeadsEntity> list = LeadsService.listByAdminExcel(params,parentId);
        String resultName ="";
        String[] title = {"客户名称","leads名称","leads姓名","leads电话","leads归属地","leads微信","金额","是否意向","状态","备注","操作人","负责坐席"};
//        String filename = "jobSet.xls";
        String sheetName = "leads管理";
        String[][] content = new String[list.size()][12];
        try {
            for (int i = 0; i < list.size(); i++) {
                content[i][0] = list.get(i).getParentName();
                content[i][1] = list.get(i).getName();
                content[i][2] = list.get(i).getName1();
                content[i][3] = list.get(i).getPhone();
                content[i][4] = list.get(i).getProvince() + list.get(i).getCity();
                content[i][5] = list.get(i).getWebchat();
                content[i][6] = list.get(i).getAmount().toString();
                if(list.get(i).getNeed()==0){
                    content[i][7] = "无意向";
                }
                if(list.get(i).getNeed()==1){
                    content[i][7] = "有意向";
                }
                if(list.get(i).getNeed()==2){
                    content[i][7] = "一般";
                }
                if(list.get(i).getStatus()==0){
                    content[i][8] ="已关闭";
                }
                if(list.get(i).getStatus()==1){
                    content[i][8] ="待分配";
                }
                if(list.get(i).getStatus()==2){
                    content[i][8] ="待处理";
                }
                if(list.get(i).getStatus()==3){
                    content[i][8] ="已加微";
                }
                if(list.get(i).getStatus()==4){
                    content[i][8] ="已响应";
                }
                if(list.get(i).getStatus()==5){
                    content[i][8] ="已成单";
                }
                if(list.get(i).getStatus()==6){
                    content[i][8] ="未成单";
                }
                content[i][9] = list.get(i).getRemark();
                content[i][10] = list.get(i).getDisposeUserName();
                content[i][11] = list.get(i).getWorkerName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HSSFWorkbook wb = ExcelOutUtil.getHSSFWorkbook(sheetName, title, content, null);
        try {
            String ctxPath = "/call/excel";
            String name = new SimpleDateFormat("ddHHmmss").format(new Date());
            String fileName=name+"leads.xlsx";
            String bizPath = "files";
            String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
            File file = new File(ctxPath + File.separator + bizPath + File.separator + nowday);
            if (!file.exists()) {
                file.mkdirs();// 创建文件根目录
            }
            String savePath = file.getPath() + File.separator + fileName;
            resultName = bizPath + File.separator + nowday+ File.separator + fileName;
            if (resultName.contains("\\")) {
                resultName = resultName.replace("\\", "/");
            System.out.print(resultName);
            System.out.print(savePath);
            }
            // 响应到客户端需要下面注释的代码
          //   this.setResponseHeader(response, filename);
         //  OutputStream os = response.getOutputStream(); //响应到服务器
           OutputStream os = new FileOutputStream(savePath); // 保存到当前路径savePath
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultName;
    }


    @PostMapping("/excelOutleads")
    @ApiOperation("所有坐席的统计情况")
    public String excelOutleads(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> params) {

        // Long parentId= Long.parseLong(StringUtils.isEmpty(String.valueOf(params.get("parentId"))) ? "0" : (String) params.get("parentId"));
        //获取数据
        List<AdminWorkerVo> list = statisticalService.allWorkerExcel(params);
        String resultName ="";
        String[] title = {"客户名称","坐席名称","leads数量","响应数量","响应率","新增","新增响应数量","新增响应率","平均每日新增"};
//        String filename = "jobSet.xls";
        String sheetName = "leads统计";
        String[][] content = new String[list.size()][9];
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            for (int i = 0; i < list.size(); i++) {
                content[i][0] = list.get(i).getUsername();
                content[i][1] = list.get(i).getLeadsname();
                content[i][2] = list.get(i).getSumLeads().toString();
                content[i][3] = list.get(i).getResponseRate().toString();
                content[i][4] = df.format(list.get(i).getSumAvg());
                content[i][5] = list.get(i).getAddTime().toString();
                content[i][6] = list.get(i).getAddTimeResponce().toString();
                content[i][7] = df.format(list.get(i).getAddTimeResponce()/list.get(i).getAddTime());
                content[i][8] = list.get(i).getAddVagDaliy().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HSSFWorkbook wb = ExcelOutUtil.getHSSFWorkbook(sheetName, title, content, null);
        try {
            String ctxPath = "/call/excel";
            String name = new SimpleDateFormat("ddHHmmss").format(new Date());
            String fileName=name+"StatisticalLeads.xlsx";
            String bizPath = "files";
            String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
            File file = new File(ctxPath + File.separator + bizPath + File.separator + nowday);
            if (!file.exists()) {
                file.mkdirs();// 创建文件根目录
            }
            String savePath = file.getPath() + File.separator + fileName;
            resultName = bizPath + File.separator + nowday+ File.separator + fileName;
            if (resultName.contains("\\")) {
                resultName = resultName.replace("\\", "/");
                System.out.print(resultName);
                System.out.print(savePath);
            }
            // 响应到客户端需要下面注释的代码
            //   this.setResponseHeader(response, filename);
            //  OutputStream os = response.getOutputStream(); //响应到服务器
            OutputStream os = new FileOutputStream(savePath); // 保存到当前路径savePath
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultName;
    }



    /**
     * 向客户端发送响应流方法
     *
     * @param response
     * @param fileName
     */
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
