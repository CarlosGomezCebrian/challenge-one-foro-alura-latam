package edu.foro.api.infra.springDoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {


    @Bean
    public OpenAPI custumOpenAPI(){
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("API forum")
                        .description("Rest API of the forum application, which contains the CRUD functionalities of users, courses and topics")
                        .contact(new Contact()
                                .name("Backend Team")
                                .email("backend@forum.api"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://forum/api/licencia")));
    }

    @Bean
    public void message(){
        System.out.println("bearer is working");
    }
}
//http://localhost:8080/swagger-ui/index.html#/
