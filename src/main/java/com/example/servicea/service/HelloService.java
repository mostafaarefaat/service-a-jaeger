package com.example.servicea.service;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class HelloService {

    @Autowired
    private Tracer tracer;

    @Autowired
    RestTemplate restTemplate;

    public String hello() {
        Span span = tracer.buildSpan("a-hello-span").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            Map<String, String> fields = new LinkedHashMap<>();
            fields.put("message", "this is a log message for service a");
            span.log(fields);
            span.setTag("Custom Tag", "My custome service a tag");
            span.setBaggageItem("bag-a", "rest call from service a");
            return "Hello service a";
        } finally {
            span.finish();
        }
    }

    public String helloRemote() {
        Span span = tracer.buildSpan("a-hello-remote-span").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            Map<String, String> fields = new LinkedHashMap<>();
            fields.put("message", "this is a log message for service a");
            span.log(fields);
            span.setTag("Custom Tag", "My custome service a tag");
            span.setBaggageItem("bag-key", "rest call from service a");
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:9992/b/rcall", String.class);
            return response.getBody();
        } finally {
            span.finish();
        }
    }

}
