package nl.fontys.tweetlefollowingservice.business.service;

import nl.fontys.tweetlefollowingservice.domain.FollowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static nl.fontys.tweetlefollowingservice.domain.RabbitMQConstants.*;

@Service
public class PublishService {

    private static final Logger log = LoggerFactory.getLogger(PublishService.class);

    private final RabbitTemplate rabbitTemplate;

    public PublishService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishFollowCreated(FollowEvent event) {
        rabbitTemplate.convertAndSend(FOLLOW_EXCHANGE, FOLLOW_CREATED_KEY, event);
        log.info("RabbitMQ follow.created event sent: {} -> {}", event.getFollowerId(), event.getFollowedId());
    }

    public void publishFollowDeleted(FollowEvent event) {
        rabbitTemplate.convertAndSend(FOLLOW_EXCHANGE, FOLLOW_DELETED_KEY, event);
        log.info("RabbitMQ follow.deleted event sent: {} -> {}", event.getFollowerId(), event.getFollowedId());
    }
}
