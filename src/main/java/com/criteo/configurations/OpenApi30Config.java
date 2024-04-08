package com.criteo.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Sponsored Ads API", version = "v1.0"),
        servers = {@Server(url = "http://localhost:8080/criteo/api/v1", description = "Local"),
                @Server(url = "https://staging-server.com/criteo/api/v1", description = "Staging"),
                @Server(url = "https://production-server.com/criteo/api/v1", description = "Production")})
@SecurityScheme(
        type = SecuritySchemeType.HTTP
)
public class OpenApi30Config {

}

