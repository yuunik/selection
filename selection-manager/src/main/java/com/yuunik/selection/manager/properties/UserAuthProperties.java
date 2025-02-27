package com.yuunik.selection.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "selection.auth")
public class UserAuthProperties {
    private List<String> noAuthUrls;
}
