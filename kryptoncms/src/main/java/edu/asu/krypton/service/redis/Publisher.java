package edu.asu.krypton.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
	@Autowired(required=true)
	private RedisTemplate<String, Object> template;

	public void publish(String topic,String message) {
		template.convertAndSend(topic, message);
	}
}
