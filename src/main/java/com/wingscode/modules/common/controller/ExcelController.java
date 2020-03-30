package com.wingscode.modules.common.controller;

import com.alibaba.druid.util.StringUtils;
import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.service.LeadsService;
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


    // /**
    //  * 导出excel
    //  * @return
    //  */
    // @PostMapping("/excelOut")
    // @ApiOperation("所有坐席的统计情况")
    // public void export(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params) throws Exception {
    //       Long parentId= Long.parseLong(StringUtils.isEmpty((String) params.get("parentId")) ? "0" : (String) params.get("parentId"));
    //
    //     //获取数据
    //     List<LeadsEntity> list = LeadsService.listByAdminExcel(params,parentId);
    //
    //          //excel标题
    //        String[] title = {"客户名称","leads名称","leads姓名","leads电话","leads归属地","leads微信","金额","是否意向","状态","备注","操作人","负责坐席"};
    //         //excel文件名
    //        String fileName = "leads管理"+System.currentTimeMillis()+".xls";
    //
    //         //sheet名
    //         String sheetName = "leads管理";
    //
    //     String[][]  content=new String[list.size()][12];
    //         for (int i = 0; i < list.size(); i++) {
    //                 LeadsEntity obj = list.get(i);
    //                 content[i][0] = obj.getParentName();
    //                 content[i][1] = obj.getName();
    //                 content[i][2] = obj.getName1();
    //                 content[i][3] = obj.getPhone();
    //                 content[i][4] = obj.getProvince() + obj.getCity();
    //                 content[i][5] = obj.getWebchat();
    //                 content[i][6] = obj.getAmount().toString();
    //                 content[i][7] = obj.getNeed().toString();
    //                 content[i][8] = obj.getStatus().toString();
    //                 content[i][9] = obj.getRemark();
    //                 content[i][10] = obj.getDisposeUser().toString();
    //                 content[i][11] = obj.getDisposeUser().toString();
    //         }
    //           //创建HSSFWorkbook
    //         HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
    //
    //            //响应到客户端
    //      try {
    //         this.setResponseHeader(response, fileName);
    //         OutputStream os = response.getOutputStream();
    //         wb.write(os);
    //         os.flush();
    //         os.close();
    //          } catch (Exception e) {
    //            e.printStackTrace();
    //       }
    //  }
    //
    // //发送响应流方法
    // public void setResponseHeader(HttpServletResponse response, String fileName) {
    //     try {
    //         try {
    //             fileName = new String(fileName.getBytes(),"ISO8859-1");
    //         } catch (UnsupportedEncodingException e) {
    //             // TODO Auto-generated catch block
    //             e.printStackTrace();
    //         }
    //         response.setContentType("application/octet-stream;charset=ISO8859-1");
    //         response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
    //         response.addHeader("Pargam", "no-cache");
    //         response.addHeader("Cache-Control", "no-cache");
    //     } catch (Exception ex) {
    //         ex.printStackTrace();
    //     }
    // }


    // /**
    //  * 导出excel
    //  * @return
    //  */
    //     HSSFWorkbook workbook = new HSSFWorkbook(); //创建一个Excel
    // @PostMapping("/excelOut")
    // @ApiOperation("所有坐席的统计情况")
    // public String exportExchangeSudent(@RequestParam Map<String, Object> params) throws Exception{
    //     Long parentId= Long.parseLong(StringUtils.isEmpty(String.valueOf(params.get("parentId"))) ? "0" : (String) params.get("parentId"));
    //     //获取数据
    //     List<LeadsEntity> list = LeadsService.listByAdminExcel(params,parentId);
    //
    //     String[] tableHeader = {"客户名称","leads名称","leads姓名","leads电话","leads归属地","leads微信","金额","是否意向","状态","备注","操作人","负责坐席"};
    //     short cellNumber=(short)tableHeader.length;//表的列数
    //     HSSFCellStyle style = workbook.createCellStyle(); //设置表头的类型
    //  //   style.setAlignment(HorizontalAlignment.CENTER);
    //      HSSFCellStyle style1 = workbook.createCellStyle(); //设置数据类型
    //   //  style1.setAlignment(HorizontalAlignment.CENTER);
    //     HSSFFont font = workbook.createFont(); //设置字体
    //     HSSFSheet sheet = workbook.createSheet("sheet1"); //创建一个sheet
    //     HSSFHeader header = sheet.getHeader();//设置sheet的头
    //     try {
    //         //根据是否取出数据，设置header信息
    //         if(list.size() < 1 ){
    //             header.setCenter("查无资料");
    //         }else{
    //             header.setCenter("交换生表");
    //           HSSFRow row = sheet.createRow(0);
    //             row.setHeight((short)400);
    //             //表头
    //             HSSFCell cell=null;
    //             for(int k = 0;k < cellNumber;k++){
    //                 cell = row.createCell((short) k);//创建第0行第k列
    //                 cell.setCellValue(tableHeader[k]);//设置第0行第k列的值
    //                 sheet.setColumnWidth((short)k,(short)8000);//设置列的宽度
    //                 font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
    //                 font.setFontHeight((short)350); //设置单元字体高度
    //                 style1.setFont(font);//设置字体风格
    //                 cell.setCellStyle(style1);
    //             }
    //             // 给Excel填充数据
    //             for(int i = 0 ;i < list.size() ;i++){
    //                 //获取InternationalStudent对象
    //                 LeadsEntity leads = (LeadsEntity)list.get(i);
    //                 row = sheet.createRow((short) (i + 1));//创建第i+1行
    //                 row.setHeight((short)400);//设置行高
    //
    //                 if(leads.getParentName() != null){
    //                     cell = row.createCell((short) 0);//创建第i+1行第0列
    //                     cell.setCellValue(leads.getParentName());//设置第i+1行第0列的值
    //                     cell.setCellStyle(style);//设置风格
    //                 }
    //                 if(leads.getName() != null){
    //                     cell = row.createCell((short) 1); //创建第i+1行第1列
    //                     cell.setCellValue(leads.getName());//设置第i+1行第1列的值
    //                     cell.setCellStyle(style); //设置风格
    //                 }
    //                 if(leads.getName1() != null){
    //                     cell = row.createCell((short) 2);
    //                     cell.setCellValue(leads.getName1());
    //                     cell.setCellStyle(style);
    //                 }
    //                 if(leads.getPhone()!= null){
    //                     cell = row.createCell((short) 3);
    //                     cell.setCellValue(leads.getPhone());
    //                     cell.setCellStyle(style);
    //                 }
    //                 if(leads.getProvince()+leads.getCity()!= null){
    //                     cell = row.createCell((short) 4);
    //                     cell.setCellValue(leads.getProvince()+leads.getCity());
    //                     cell.setCellStyle(style);
    //                 }
    //                 if(leads.getWebchat() != null){
    //                     cell = row.createCell((short) 5);
    //                     cell.setCellValue(leads.getWebchat());
    //                     cell.setCellStyle(style);
    //                 }
    //                 if(leads.getAmount() != null){
    //                     cell = row.createCell((short) 6);
    //                     cell.setCellValue(leads.getAmount().toString());
    //                     cell.setCellStyle(style);
    //                 }
    //                 if(leads.getNeed() != null){
    //                     cell = row.createCell((short) 7);
    //                     cell.setCellValue(leads.getNeed().toString());
    //                     cell.setCellStyle(style);
    //                 }
    //                 if(leads.getStatus() != null){
    //                     cell = row.createCell((short) 8);
    //                     cell.setCellValue(leads.getStatus().toString());
    //                     cell.setCellStyle(style);
    //                 }
    //                 if(leads.getRemark() != null){
    //                     cell = row.createCell((short) 9);
    //                     cell.setCellValue(leads.getRemark().toString());
    //                     cell.setCellStyle(style);
    //                 }
    //                 if(leads.getDisposeUser() != null){
    //                     cell = row.createCell((short) 10);
    //                     cell.setCellValue(leads.getDisposeUser().toString());
    //                     cell.setCellStyle(style);
    //                 }
    //                 if(leads.getDisposeUser() != null){
    //                     cell = row.createCell((short) 11);
    //                     cell.setCellValue(leads.getDisposeUser().toString());
    //                     cell.setCellStyle(style);
    //                 }
    //
    //
    //             }
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     outputSetting("leads表.xls");
    //     return null;
    // }
    //
    // //固定配置
    // public void outputSetting(String fileName) {
    //     HttpServletResponse response = null;//创建一个HttpServletResponse对象
    //      ServletOutputStream out = null;//创建一个输出流对象
    //     try {
    //         response = ServletActionContext.getResponse();//初始化HttpServletResponse对象
    //           out = response.getOutputStream();// 得到输出流
    //         response.setHeader("Content-disposition","attachment; filename="+new String(fileName.getBytes(),"ISO-8859-1"));//filename是下载的xls的名
    //         response.setContentType("application/msexcel;charset=UTF-8");//设置类型
    //         response.setHeader("Pragma","No-cache");//设置头
    //         response.setHeader("Cache-Control","no-cache");//设置头
    //         response.setDateHeader("Expires", 0);//设置日期头
    //         workbook.write(out);
    //         out.flush();
    //         workbook.write(out);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }finally{
    //         try{
    //             if(out!=null){
    //                 out.close();
    //             }
    //         }catch(IOException e){
    //             e.printStackTrace();
    //         }
    //     }


    /**
     * 导出数据为Excel文件
     * 下面注释的代码为直接响应到服务器的
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/excelOut")
    @ApiOperation("所有坐席的统计情况")
    public String exportExcel(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> params) {

        Long parentId= Long.parseLong(StringUtils.isEmpty(String.valueOf(params.get("parentId"))) ? "0" : (String) params.get("parentId"));
        //     //获取数据
             List<LeadsEntity> list = LeadsService.listByAdminExcel(params,parentId);
        String resultName ="";
        String[] title = {"客户名称","leads名称","leads姓名","leads电话","leads归属地","leads微信","金额","是否意向","状态","备注","操作人","负责坐席"};
//        String filename = "jobSet.xls";
        String sheetName = "leads统计表";
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
                content[i][7] = list.get(i).getNeed().toString();
                content[i][8] = list.get(i).getStatus().toString();
                content[i][9] = list.get(i).getRemark();
                content[i][10] = list.get(i).getDisposeUser().toString();
                content[i][11] = list.get(i).getDisposeUser().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HSSFWorkbook wb = ExcelOutUtil.getHSSFWorkbook(sheetName, title, content, null);
        try {
            String ctxPath = "/root";
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
