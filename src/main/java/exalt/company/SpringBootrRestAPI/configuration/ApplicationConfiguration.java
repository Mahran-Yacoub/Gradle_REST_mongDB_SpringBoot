package exalt.company.SpringBootrRestAPI.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/** This Class is used to set configuration for the application*/
@Configuration
public class ApplicationConfiguration {


    @Bean
    public Docket swaggerDocumentation() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    /**
     * This method is used to set metadata about project when we use swagger ui
     * to document it
     *
     * @return ApiInfo that has metaData about the app
     *
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Mahran Test ")
                .description("This is a SpringBoot Application such that it represents backend " +
                        "side with some of APIs to Create , delete , update , read resources " +
                        "(CRUD) using http protocol and localhost as server and 8080 as port " +
                        "\"http://localhost:8080/\" " +
                        "and endpoint part like http://localhost:8080/customer/all.")

                .version("1.1").build();
    }
}
