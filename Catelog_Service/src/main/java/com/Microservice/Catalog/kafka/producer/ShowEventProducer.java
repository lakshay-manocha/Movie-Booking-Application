package com.Microservice.Catalog.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.microservice.common.events.KafkaTopics;
import com.microservice.common.events.ShowCreatedEvent;

@Service
public class ShowEventProducer {

	private final KafkaTemplate<String, ShowCreatedEvent> kafkatemplate;
	
	public ShowEventProducer (KafkaTemplate<String, ShowCreatedEvent> kafkatemplate) {
		this.kafkatemplate=kafkatemplate;
	}
	
	public void publishShowCreatedEvent(
			ShowCreatedEvent event) {
		kafkatemplate.send(
				KafkaTopics.SHOW_CREATED,
				event.getShowId(),
				event);
	}
}
