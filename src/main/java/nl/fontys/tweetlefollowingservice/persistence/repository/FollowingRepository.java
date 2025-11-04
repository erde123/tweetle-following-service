package nl.fontys.tweetlefollowingservice.persistence.repository;

import nl.fontys.tweetlefollowingservice.persistence.entity.FollowingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowingRepository extends JpaRepository<FollowingEntity, Long> {
    List<FollowingEntity> findByFollowerId(Long followerId);
    List<FollowingEntity> findByFolloweeId(Long followeeId);
    void deleteByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}
