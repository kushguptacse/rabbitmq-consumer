package com.sample.subscriber;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.sample.subscriber.model.GenericModel;

@Component
public class Receiver {
	@RabbitListener(queues = {"global-queue-1"})
	public void handleMessage(GenericModel message) {
		System.out.println("Received <" + message + ">");
	}

}