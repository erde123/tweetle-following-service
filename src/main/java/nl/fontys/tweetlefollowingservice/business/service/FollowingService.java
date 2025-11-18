package nl.fontys.tweetlefollowingservice.business.service;

import jakarta.transaction.Transactional;
import nl.fontys.tweetlefollowingservice.domain.FollowEvent;
import nl.fontys.tweetlefollowingservice.persistence.entity.FollowingEntity;
import nl.fontys.tweetlefollowingservice.persistence.repository.FollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FollowingService {

    private static final Logger log = LoggerFactory.getLogger(FollowingService.class);

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private PublishService publishService;

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

        FollowingEntity saved = followingRepository.save(entity);

        publishService.publishFollowCreated(new FollowEvent(followerId, followeeId));
        log.info("User {} followed {}", followerId, followeeId);

        return saved;
    }

    @Transactional
    public void unfollowUser(Long followerId, Long followeeId) {
        followingRepository.deleteByFollowerIdAndFolloweeId(followerId, followeeId);

        publishService.publishFollowDeleted(new FollowEvent(followerId, followeeId));
        log.info("User {} unfollowed {}", followerId, followeeId);
    }

    public List<FollowingEntity> getFollowing(Long followerId) {
        return followingRepository.findByFollowerId(followerId);
    }

    public List<FollowingEntity> getFollowers(Long followeeId) {
        return followingRepository.findByFolloweeId(followeeId);
    }
}
