package edu.as.sys.demo;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{server.context-path}")
public class DemoController {
    @RequestMapping("/demo")
    String home(HttpServletRequest request) {
        System.out.println("=======GET Process=======");

        Map<String,String[]> requestMsg = request.getParameterMap();
        Enumeration<String> requestHeader = request.getHeaderNames();

        System.out.println("------- header -------");
        while(requestHeader.hasMoreElements()){
            String headerKey=requestHeader.nextElement().toString();
            //打印所有Header值

            System.out.println("headerKey="+headerKey+";value="+request.getHeader(headerKey));
        }

        System.out.println("------- parameter -------");
        for(String key :requestMsg.keySet())
        {
            for(int i=0;i<requestMsg.get(key).length;i++)
            {
                //打印所有请求参数值

                System.out.println("key="+key+";value="+requestMsg.get(key)[i].toString());
            }
        }
        return "Hello World!";
    }
}