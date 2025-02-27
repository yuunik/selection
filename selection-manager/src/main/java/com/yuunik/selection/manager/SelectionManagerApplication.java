package com.yuunik.selection.manager;

import com.yuunik.selection.manager.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.yuunik.selection"})
@EnableConfigurationProperties(value = {UserAuthProperties.class})
public class SelectionManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SelectionManagerApplication.class, args);
    }
}
