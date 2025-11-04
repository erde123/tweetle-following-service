package nl.fontys.tweetlefollowingservice.business.service;

import nl.fontys.tweetlefollowingservice.persistence.entity.FollowingEntity;
import nl.fontys.tweetlefollowingservice.persistence.repository.FollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FollowingService {

    @Autowired
    private FollowingRepository followingRepository;

    public FollowingEntity followUser(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            throw new IllegalArgumentException("You cannot follow yourself.");
        }

        if (followingRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)) {
            throw new IllegalStateException("Already following this user.");
        }

        FollowingEntity entity = FollowingEntity.builder()
                .followerId(followerId)
                .followeeId(followeeId)
                .createdAt(Instant.now().toEpochMilli())
                .build();

        return followingRepository.save(entity);
    }

    public void unfollowUser(Long followerId, Long followeeId) {
        followingRepository.deleteByFollowerIdAndFolloweeId(followerId, followeeId);
    }

    public List<FollowingEntity> getFollowing(Long followerId) {
        return followingRepository.findByFollowerId(followerId);
    }

    public List<FollowingEntity> getFollowers(Long followeeId) {
        return followingRepository.findByFolloweeId(followeeId);
    }
}

