package tw.com.firstbank.fcbcore.fir.service.example.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	private static final Logger log = LoggerFactory.getLogger(RabbitMQConfig.class);

//	public static final String MY_RPC_EXCHANGE = "tut.rpc";
//	public static final String MY_RPC_REQUEST = "tut.rpc.requests";
//	public static final String MY_RPC_ROUTEKEY = "rpc";

	public static final String MY_STARTUP_QUEUE = "my.startup.queue";

	/**
	 * The constant represents the rabbitMQ queue name.
	 */
	public static final String MY_QUEUE = "myqueue";

	public static final String DLQ_EXCHANGE = "my.dead-letter.exchange";
	public static final String DLQ_QUEUE = "my.dead-letter.queue";

//	@Bean(name = "rpcExchange")
//	public DirectExchange rpcExchange() {
//		return new DirectExchange(MY_RPC_EXCHANGE);
//	}

//	@Bean(name = "rpcQueue")
//	public Queue rpcQueue() {
//		return new Queue(MY_RPC_REQUEST);
//	}

//	@Bean(name = "rpcBinding")
//	public Binding rpcBinding(@Qualifier("rpcExchange") DirectExchange exchange, @Qualifier("rpcQueue") Queue queue) {
//		return BindingBuilder.bind(queue).to(exchange).with(MY_RPC_ROUTEKEY);
//	}

	@Bean(name = "myQueue")
	public Queue myQueue() {
		return QueueBuilder.durable(MY_QUEUE)
			.withArgument("x-dead-letter-exchange", DLQ_EXCHANGE)
			.withArgument("x-dead-letter-routing-key", "deadLetter").build();
	}

	@Bean(name = "myStartupQueue")
	public Queue myStartupQueue() {
		return QueueBuilder.durable(MY_STARTUP_QUEUE)
			.withArgument("x-dead-letter-exchange", DLQ_EXCHANGE)
			.withArgument("x-dead-letter-routing-key", "deadLetter").build();
	}

	@Bean
	DirectExchange dlqExchange() {
		return new DirectExchange(DLQ_EXCHANGE);
	}

	@Bean
	Queue dlq() {
		return QueueBuilder.durable(DLQ_QUEUE).build();
	}

	@Bean
	Binding dlqBinding() {
		return BindingBuilder.bind(dlq()).to(dlqExchange()).with("deadLetter");
	}

}
