package com.boe.admin.uiadmin.utils;


import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletResponse;

import com.alibaba.fastjson.JSON;
import com.boe.admin.uiadmin.common.Result;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ResultUtil {

    /**
     * 私有化构造器
     */
    private ResultUtil(){}
    
    public static void responseJson(ServletResponse response, Result<?> result){
   	 PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(result));
        } catch (Exception e) {
            log.error("【JSON输出异常】"+e);
        }finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
       
   }


    public static void responseJson(ServletResponse response, Map<String, Object> map){
    	 PrintWriter out = null;
         try {
             response.setCharacterEncoding("UTF-8");
             response.setContentType("application/json");
             out = response.getWriter();
             out.println(JSON.toJSONString(map));
         } catch (Exception e) {
             log.error("【JSON输出异常】"+e);
         }finally{
             if(out!=null){
                 out.flush();
                 out.close();
             }
         }
        
    }
   

}