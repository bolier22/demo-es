package com.wanma.demoes.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: zhugf
 * @Description:
 * @Date created in 2018/5/14
 */
@Component
@ConfigurationProperties(prefix = "system-params")
@Getter
@Setter
public class SystemParams {
 
    private String imageUri;

    private String imageName;

    private long costMaxTime;
}
