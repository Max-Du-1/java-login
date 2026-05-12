package com.example.login;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hanputaoya")  // 文档标题
                        .version("1.0.0")          // 版本号
                        // .description("用户登录、注册接口在线文档") // 描述
                );
    }
}