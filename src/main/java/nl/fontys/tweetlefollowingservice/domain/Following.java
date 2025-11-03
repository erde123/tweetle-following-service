package nl.fontys.tweetlefollowingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Following {
    private Long followingId;
    private Long followeeId;
    private Long createdAt;
}
