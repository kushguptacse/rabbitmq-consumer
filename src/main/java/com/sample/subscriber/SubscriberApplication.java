package com.sample.subscriber;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sample.subscriber.config.ApplicationConfigReader;

@SpringBootApplication
public class SubscriberApplication {

	@Autowired
	private ApplicationConfigReader applicationConfig;

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Queue queue() {
		return new Queue(applicationConfig.getApp1Queue(), true);
	}

	@Bean
	public DirectExchange exchange() {
		return new DirectExchange(applicationConfig.getApp1Exchange());
	}

	@Bean
	public Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(applicationConfig.getApp1RoutingKey());
	}

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
	    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	    rabbitTemplate.setMessageConverter(jsonMessageConverter());
	    return rabbitTemplate;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SubscriberApplication.class, args);
	}

}
