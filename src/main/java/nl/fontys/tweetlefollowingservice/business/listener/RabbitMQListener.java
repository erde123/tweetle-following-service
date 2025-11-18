package nl.fontys.tweetlefollowingservice.business.listener;

import nl.fontys.tweetlefollowingservice.business.service.ExistingUserService;
import nl.fontys.tweetlefollowingservice.domain.UserEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static nl.fontys.tweetlefollowingservice.domain.RabbitMQConstants.*;

@Component
public class RabbitMQListener {

    private final ExistingUserService existingUserService;

    public RabbitMQListener(ExistingUserService existingUserService) {
        this.existingUserService = existingUserService;
    }

    @RabbitListener(queues = FOLLOWING_USER_REGISTER_QUEUE)
    public void handleUserRegistered(UserEvent event) {
        System.out.println("📩 Following service received user registration for: " + event.getUsername());
        existingUserService.saveNewUserCredential(event);
    }

    @RabbitListener(queues = FOLLOWING_USER_UPDATE_QUEUE)
    public void handleUserUpdated(UserEvent event) {
        System.out.println("📩 Following service received user update for: " + event.getUsername());
        existingUserService.updateUser(event);
    }

    @RabbitListener(queues = FOLLOWING_USER_DELETE_QUEUE)
    public void handleUserDeleted(UserEvent event) {
        System.out.println("📩 Following service received user deletion for id: " + event.getUserId());
        existingUserService.deleteUser(event.getUserId());
    }
}
