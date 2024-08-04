package com.community.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }


    private Info apiInfo() {
        return new Info()
                .title("Donkey Community API") // API 제목
                .description("오르미 커뮤니티 프로젝트 CRUD API") // API 설명
                .version("1.0.0"); // API 버전
    }
}


/**
 * 필요하다면 변경
 *
 *
 * import io.swagger.v3.oas.models.Components;
 * import io.swagger.v3.oas.models.OpenAPI;
 * import io.swagger.v3.oas.models.info.Info;
 * import io.swagger.v3.oas.models.security.SecurityRequirement;
 * import io.swagger.v3.oas.models.security.SecurityScheme;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 *
 * @Configuration
 * public class SwaggerConfig {
 *
 *     @Bean
 *     public OpenAPI openAPI() {
 *         return new OpenAPI()
 *             .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
 *             .components(new Components()
 *                 .addSecuritySchemes("bearerAuth", new SecurityScheme()
 *                     .name("bearerAuth")
 *                     .type(SecurityScheme.Type.HTTP)
 *                     .scheme("bearer")
 *                     .bearerFormat("JWT")))
 *             .info(apiInfo());
 *     }
 *
 *     private Info apiInfo() {
 *         return new Info()
 *             .title("Donkey Community API") // API 제목
 *             .description("오르미 커뮤니티 프로젝트 CRUD API") // API 설명
 *             .version("1.0.0"); // API 버전
 *     }
 * }
 */

/**
 *
 * @Configuration
 * public class SwaggerConfig {
 *
 *     @Bean
 *     public Docket api() {
 *         return new Docket(DocumentationType.OAS_30)
 *             .useDefaultResponseMessages(false)
 *             .select()
 *             .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
 *             .paths(PathSelectors.any())
 *             .build()
 *             .securitySchemes(List.of(apiKey()))
 *             .securityContexts(List.of(securityContext()))
 *             .apiInfo(apiInfo());
 *     }
 *
 *     private ApiInfo apiInfo() {
 *         return new ApiInfoBuilder()
 *             .title("Boot API 01 Project Swagger")
 *             .build();
 *     }
 *
 *     private ApiKey apiKey() {
 *         return new ApiKey("Authorization", "Bearer Token", "header");
 *     }
 *
 *     private SecurityContext securityContext() {
 *         return SecurityContext.builder()
 *             .securityReferences(defaultAuth())
 *             .operationSelector(selector -> selector.requestMappingPattern().startsWith("/api/"))
 *             .build();
 *     }
 *
 *     private List<SecurityReference> defaultAuth() {
 *         AuthorizationScope authorizationScope = new AuthorizationScope("global", "global access");
 *         return List.of(new SecurityReference("Authorization", new AuthorizationScope[]{authorizationScope}));
 *     }
 * }
 */

/**
 * Spring FOX 3.0 버전
 *
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 * import springfox.documentation.builders.PathSelectors;
 * import springfox.documentation.builders.RequestHandlerSelectors;
 * import springfox.documentation.service.ApiInfo;
 * import springfox.documentation.service.ApiKey;
 * import springfox.documentation.service.AuthorizationScope;
 * import springfox.documentation.service.SecurityReference;
 * import springfox.documentation.spi.DocumentationType;
 * import springfox.documentation.spi.service.contexts.SecurityContext;
 * import springfox.documentation.spring.web.plugins.Docket;
 * import springfox.documentation.swagger2.annotations.EnableSwagger2;
 *
 * import java.util.Collections;
 * import java.util.List;
 *
 * @Configuration
 * @EnableSwagger2
 * public class SwaggerConfig {
 *
 *     @Bean
 *     public Docket api() {
 *         return new Docket(DocumentationType.OAS_30) // OpenAPI Specification 3.0을 사용합니다.
 *             .useDefaultResponseMessages(false)
 *             .select()
 *             .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
 *             .paths(PathSelectors.any())
 *             .build()
 *             .securitySchemes(Collections.singletonList(apiKey()))
 *             .securityContexts(Collections.singletonList(securityContext()))
 *             .apiInfo(apiInfo());
 *     }
 *
 *     private ApiInfo apiInfo() {
 *         return new ApiInfo(
 *             "Boot API 01 Project Swagger", // 제목
 *             "API documentation for Boot API 01 Project", // 설명
 *             "1.0.0", // 버전
 *             "Terms of service URL", // 서비스 약관 URL
 *             new Contact("Your Name", "Your Website", "Your Email"), // 연락처 정보
 *             "License of API", "API license URL", Collections.emptyList() // 라이선스 정보
 *         );
 *     }
 *
 *     private ApiKey apiKey() {
 *         return new ApiKey("JWT", "Authorization", "header");
 *     }
 *
 *     private SecurityContext securityContext() {
 *         return SecurityContext.builder()
 *             .securityReferences(defaultAuth())
 *             .forPaths(PathSelectors.regex("/.*")) // 보안이 적용될 경로를 정규식으로 설정
 *             .build();
 *     }
 *
 *     private List<SecurityReference> defaultAuth() {
 *         AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
 *         AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
 *         authorizationScopes[0] = authorizationScope;
 *         return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
 *     }
 * }
 */