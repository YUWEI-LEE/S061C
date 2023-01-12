package tw.com.firstbank.fcbcore.fir.service.example.spring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	private static final String API_PKG = "tw.com.firstbank.fcbcore.fir.service.example.adapter.in";
	private static final String API_TITLE = "Example API";
	private static final String API_DESC = "For example service";
	private static final String API_VERSION = "API 1.0.0";
	private static final String SERVER_URL = "http://localhost:8080";

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI().addServersItem(new Server().url(SERVER_URL))
			.info(new Info()
				.title(API_TITLE)
				.description(API_DESC)
				.version(API_VERSION));
	}
}

