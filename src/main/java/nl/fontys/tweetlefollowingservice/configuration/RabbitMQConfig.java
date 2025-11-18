package nl.fontys.tweetlefollowingservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static nl.fontys.tweetlefollowingservice.domain.RabbitMQConstants.*;

@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange followExchange() {
        return new TopicExchange(FOLLOW_EXCHANGE);
    }

    @Bean
    public TopicExchange userAuthExchange() {
        return new TopicExchange(USER_AUTH_EXCHANGE);
    }

    @Bean
    public TopicExchange userGeneralExchange() {
        return new TopicExchange(USER_GENERAL_EXCHANGE);
    }

    @Bean
    public Queue followingUserRegisterQueue() {
        return new Queue(FOLLOWING_USER_REGISTER_QUEUE, true);
    }

    @Bean
    public Queue followingUserUpdateQueue() {
        return new Queue(FOLLOWING_USER_UPDATE_QUEUE, true);
    }

    @Bean
    public Queue followingUserDeleteQueue() {
        return new Queue(FOLLOWING_USER_DELETE_QUEUE, true);
    }

    @Bean
    public Queue followCreatedQueue() {
        return new Queue(FOLLOW_CREATED_QUEUE, true);
    }

    @Bean
    public Queue followDeletedQueue() {
        return new Queue(FOLLOW_DELETED_QUEUE, true);
    }

    @Bean
    public Binding bindFollowingUserRegisterQueue() {
        return BindingBuilder.bind(followingUserRegisterQueue())
                .to(userAuthExchange())
                .with(USER_AUTH_REGISTERED_KEY);
    }

    @Bean
    public Binding bindFollowingUserUpdateQueue() {
        return BindingBuilder.bind(followingUserUpdateQueue())
                .to(userGeneralExchange())
                .with(USER_UPDATED_KEY);
    }

    @Bean
    public Binding bindFollowingUserDeleteQueue() {
        return BindingBuilder.bind(followingUserDeleteQueue())
                .to(userGeneralExchange())
                .with(USER_DELETED_KEY);
    }

    @Bean
    public Binding bindFollowCreatedQueue() {
        return BindingBuilder.bind(followCreatedQueue())
                .to(followExchange())
                .with(FOLLOW_CREATED_KEY);
    }

    @Bean
    public Binding bindFollowDeletedQueue() {
        return BindingBuilder.bind(followDeletedQueue())
                .to(followExchange())
                .with(FOLLOW_DELETED_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setAlwaysConvertToInferredType(true);
        return converter;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
