
package com.bs.hellooauth2jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class Controller {

    @GetMapping("/home")
    public String home(HttpServletRequest request){
        log.info("header={}",request.getHeader("Auth"));
       return request.getHeader("Auth");
    }
}
