package com.vicayala.demotravel.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Demo Travel",
                version="1.0",
                description = "OpenApi Documentation")
)
public class OpenApiConfig {


}
