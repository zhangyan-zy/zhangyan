package com.wingscode.modules.common.controller;

import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.service.LeadsService;
import com.wingscode.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Author：张延
 * Date：2020-03-28 11:15
 * Description：<描述>
 */
@RestController
@RequestMapping("/excel")
public class ExcelController  {

     @Autowired
    private  LeadsService LeadsService;


    /**
     * 导出excel
     * @return
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {

               Long  parentId= Long.valueOf(request.getParameter("parentId"));
                Map params= request.getParameterMap();

        //获取数据
        List<LeadsEntity> list = LeadsService.listByAdminExcel(params,parentId);

             //excel标题
           String[] title = {"客户名称","leads名称","leads姓名","leads电话","leads归属地","leads微信","金额","是否意向","状态","备注","操作人","负责坐席"};
            //excel文件名
           String fileName = "leads管理"+System.currentTimeMillis()+".xls";

            //sheet名
            String sheetName = "leads管理";

           String[][] content=null;
            for (int i = 0; i < list.size(); i++) {
            content= new String[title.length][];
            LeadsEntity obj = list.get(i);
            content[i][0] = obj.getName();
            content[i][1] = obj.getName1();
            content[i][2] = obj.getParentName();
            content[i][3] = obj.getPhone();
            content[i][4] = obj.getProvince()+obj.getCity();
            content[i][5] = obj.getWebchat();
            content[i][6] = obj.getAmount().toString();
            content[i][7] = obj.getNeed().toString();
            content[i][8] = obj.getStatus().toString();
            content[i][9] = obj.getRemark();
            content[i][10] = obj.getDisposeUser().toString();
            content[i][11] = obj.getDisposeUser().toString();
               }

              //创建HSSFWorkbook
            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

               //响应到客户端
         try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
             } catch (Exception e) {
               e.printStackTrace();
          }
     }

    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



}
