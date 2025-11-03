package nl.fontys.tweetlefollowingservice.controller;

import nl.fontys.tweetlefollowingservice.business.service.FollowingService;
import nl.fontys.tweetlefollowingservice.persistence.entity.FollowingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowingController {

    @Autowired
    private FollowingService followingService;

    @PostMapping("/{followerId}/{followeeId}")
    public ResponseEntity<FollowingEntity> followUser(@PathVariable Long followerId, @PathVariable Long followeeId) {
        return ResponseEntity.ok(followingService.followUser(followerId, followeeId));
    }

    @DeleteMapping("/{followerId}/{followeeId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long followerId, @PathVariable Long followeeId) {
        followingService.unfollowUser(followerId, followeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/following/{followerId}")
    public ResponseEntity<List<FollowingEntity>> getFollowing(@PathVariable Long followerId) {
        return ResponseEntity.ok(followingService.getFollowing(followerId));
    }

    @GetMapping("/followers/{followeeId}")
    public ResponseEntity<List<FollowingEntity>> getFollowers(@PathVariable Long followeeId) {
        return ResponseEntity.ok(followingService.getFollowers(followeeId));
    }
}

