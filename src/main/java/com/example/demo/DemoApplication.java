package com.example.demo;

import com.example.demo.servlet.SimpleStatusServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean<SimpleStatusServlet> statusServlet() {
        ServletRegistrationBean<SimpleStatusServlet> registration = new ServletRegistrationBean<>(new SimpleStatusServlet());
        registration.setUrlMappings(List.of("/status"));
        return registration;
    }
}
