package com.boe.admin.uiadmin.config;



import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * ShutdownApp
 */
@Configuration
@Slf4j
public class ShutdownApp implements ApplicationListener<ContextClosedEvent>{

    private static volatile Connector connector;

    private final int waitTime = 30;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("..........................................................");
        log.info("...................SHUTDOWN NOW...........................");
        log.info("..........................................................");


       long startTime =  System.currentTimeMillis();
        //tomcat暂停对外服务
        connector.pause();
        //获取tomcat线程池
        Executor executor = connector.getProtocolHandler().getExecutor();
        if(executor instanceof ThreadPoolExecutor){
            try {
                 ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                 //线程池优雅停止，不接收新请求，等待任务完成后，关闭线程池
                 threadPoolExecutor.shutdown();
                 //阻塞等待一定的时间
                 if (threadPoolExecutor.awaitTermination(waitTime, TimeUnit.SECONDS)) {
                     log.info(String.format("Tomcat thread pool closed, time : %s ms", System.currentTimeMillis() - startTime));
                 }else {
                     log.warn("web 应用关闭时长超过最大时长：{} 秒，将进行强制关闭", waitTime);
                     threadPoolExecutor.shutdownNow();
                     if (!threadPoolExecutor.awaitTermination(waitTime, TimeUnit.SECONDS)) {
                        log.error("web应用关闭失败");
                     } 
                 }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }


    }



    @Bean
    public ServletWebServerFactory servletContainer() {

       TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
       tomcat.addConnectorCustomizers(connect -> {
           connector = connect;
        });
       return tomcat;
    }


}