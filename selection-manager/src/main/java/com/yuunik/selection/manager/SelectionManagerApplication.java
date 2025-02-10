package com.yuunik.selection.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.yuunik.selection"})
public class SelectionManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SelectionManagerApplication.class, args);
    }
}
