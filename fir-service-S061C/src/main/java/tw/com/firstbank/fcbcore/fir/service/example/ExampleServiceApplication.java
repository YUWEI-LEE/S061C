package tw.com.firstbank.fcbcore.fir.service.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.retry.annotation.EnableRetry;
import tw.com.firstbank.fcbcore.fcbframework.adapters.rabbitmq.adapter.out.event.RabbitMQEventBusAdapter;
import tw.com.firstbank.fcbcore.fcbframework.adapters.rabbitmq.adapter.out.repository.eventlog.impl.EventLogRepositoryImpl;
import tw.com.firstbank.fcbcore.fcbframework.adapters.rabbitmq.spring.config.DataSourceJournalConfig;
import tw.com.firstbank.fcbcore.fcbframework.adapters.rabbitmq.spring.config.RabbitMQConfig;
import tw.com.firstbank.fcbcore.fir.service.example.spring.config.DataSourceMainConfig;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
	DataSourceAutoConfiguration.class,
	DataSourceTransactionManagerAutoConfiguration.class,
	HibernateJpaAutoConfiguration.class,
	RabbitAutoConfiguration.class
})
@ComponentScan(value = "tw.com.firstbank.fcbcore",
	excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
		classes = {DataSourceMainConfig.class, DataSourceJournalConfig.class,
			tw.com.firstbank.fcbcore.fir.service.example.spring.config.RabbitMQConfig.class,
			RabbitMQConfig.class, RabbitMQEventBusAdapter.class, EventLogRepositoryImpl.class,
			tw.com.firstbank.fcbcore.fcbframework.adapters.rabbitmq.spring.config.EventLogSaveAspect.class})
	})
@EnableFeignClients
@EnableRetry
public class ExampleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleServiceApplication.class, args);
	}
}
