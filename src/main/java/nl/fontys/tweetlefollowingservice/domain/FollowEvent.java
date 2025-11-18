package nl.fontys.tweetlefollowingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowEvent {
    private Long followerId;
    private Long followedId;
}
