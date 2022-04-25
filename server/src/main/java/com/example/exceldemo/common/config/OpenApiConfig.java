package com.example.exceldemo.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String NAME = "Joan Roucoux";
    private static final String URL = "https://github.com/JoanRoucoux/react-spring-excel-app";
    private static final String EMAIL = "joan.roucoux@gmail.com";
    private static final String TITLE = "Excel Demo OpenAPI definition";
    private static final String VERSION = "v1.0";

    @Bean
    public OpenAPI openApi() {
        final Contact contact = new Contact()
                .name(NAME)
                .url(URL)
                .email(EMAIL);
        final Info info = new Info()
                .title(TITLE)
                .version(VERSION)
                .contact(contact);

        return new OpenAPI()
                .info(info);
    }
}
