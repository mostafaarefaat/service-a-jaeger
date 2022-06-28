package com.example.servicea.rest;

import com.example.servicea.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a")
public class HelloRestController {

    @Autowired
    HelloService helloService;

    @GetMapping("/init")
    public String init(){
        return "service a start";
    }

    @GetMapping("/call")
    public String hello(){
        return helloService.hello();
    }

    @GetMapping("/rcall")
    public String rHello(){
        return helloService.helloRemote();
    }
}
