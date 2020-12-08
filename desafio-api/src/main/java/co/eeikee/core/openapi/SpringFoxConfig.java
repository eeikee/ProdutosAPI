package co.eeikee.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer{
	
	@Bean
	public Docket apiDocket() {
	 return new Docket(DocumentationType.SWAGGER_2).select()
			 .apis(RequestHandlerSelectors.any())
			 .build().apiInfo(ApiInfo()).tags(new Tag("Clientes", "Clientes do sistema de venda"),
					 new Tag("Fornecedores", "Fornecedores dos produtos"),
					 new Tag("Produtos", "Produtos disponíveis no sistema"),
					 new Tag("Vendas", "Venda de um produto ao cliente"));
	}
	
	public ApiInfo ApiInfo() {
		return new ApiInfoBuilder()
				.title("API Produtos")
				.description("Projeto: API de produtos (REST)")
				.version("1")
				.contact(new Contact("Eike", "https://www.linkedin.com/in/eeikee/","konumaeike5618@hotmail.com"))
				.build();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		
	registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
