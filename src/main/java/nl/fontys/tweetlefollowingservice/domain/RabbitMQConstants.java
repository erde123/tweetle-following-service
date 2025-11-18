package nl.fontys.tweetlefollowingservice.domain;

public final class RabbitMQConstants {

    private RabbitMQConstants() {
    }

    public static final String FOLLOW_EXCHANGE = "tweetle.exchange";
    public static final String FOLLOW_CREATED_KEY = "follow.created";
    public static final String FOLLOW_DELETED_KEY = "follow.deleted";

    public static final String FOLLOW_CREATED_QUEUE = "following.follow-created.queue";
    public static final String FOLLOW_DELETED_QUEUE = "following.follow-deleted.queue";

    public static final String FOLLOWING_USER_REGISTER_QUEUE = "following.user.register.queue";
    public static final String FOLLOWING_USER_UPDATE_QUEUE = "following.user.update.queue";
    public static final String FOLLOWING_USER_DELETE_QUEUE = "following.user.delete.queue";

    public static final String USER_AUTH_EXCHANGE = "user-auth-exchange";
    public static final String USER_GENERAL_EXCHANGE = "user-general-exchange";
    public static final String USER_AUTH_REGISTERED_KEY = "user.auth.registered";
    public static final String USER_UPDATED_KEY = "user.updated";
    public static final String USER_DELETED_KEY = "user.deleted";
}
