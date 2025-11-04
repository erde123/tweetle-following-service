package nl.fontys.tweetlefollowingservice.business.service;

import static org.junit.jupiter.api.Assertions.*;

import nl.fontys.tweetlefollowingservice.persistence.entity.FollowingEntity;
import nl.fontys.tweetlefollowingservice.persistence.repository.FollowingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class FollowingServiceTest {

    @Mock
    private FollowingRepository followingRepository;

    @InjectMocks
    private FollowingService followingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void followUser_ShouldSaveEntity_WhenNotAlreadyFollowing() {
        Long followerId = 1L;
        Long followeeId = 2L;

        when(followingRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId))
                .thenReturn(false);

        FollowingEntity expectedEntity = FollowingEntity.builder()
                .followerId(followerId)
                .followeeId(followeeId)
                .build();

        when(followingRepository.save(any(FollowingEntity.class)))
                .thenReturn(expectedEntity);

        FollowingEntity result = followingService.followUser(followerId, followeeId);

        assertNotNull(result);
        assertEquals(followerId, result.getFollowerId());
        assertEquals(followeeId, result.getFolloweeId());
        verify(followingRepository, times(1)).save(any(FollowingEntity.class));
    }

    @Test
    void followUser_ShouldThrow_WhenFollowingSelf() {
        Long userId = 1L;
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                followingService.followUser(userId, userId)
        );
        assertEquals("You cannot follow yourself.", exception.getMessage());
    }

    @Test
    void followUser_ShouldThrow_WhenAlreadyFollowing() {
        Long followerId = 1L;
        Long followeeId = 2L;

        when(followingRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId))
                .thenReturn(true);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                followingService.followUser(followerId, followeeId)
        );
        assertEquals("Already following this user.", exception.getMessage());
    }

    @Test
    void unfollowUser_ShouldCallDelete() {
        Long followerId = 1L;
        Long followeeId = 2L;

        followingService.unfollowUser(followerId, followeeId);

        verify(followingRepository, times(1))
                .deleteByFollowerIdAndFolloweeId(followerId, followeeId);
    }

    @Test
    void getFollowing_ShouldReturnList() {
        Long followerId = 1L;
        FollowingEntity entity = FollowingEntity.builder()
                .followerId(followerId)
                .followeeId(2L)
                .build();

        when(followingRepository.findByFollowerId(followerId))
                .thenReturn(Collections.singletonList(entity));

        List<FollowingEntity> result = followingService.getFollowing(followerId);
        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getFolloweeId());
    }

    @Test
    void getFollowers_ShouldReturnList() {
        Long followeeId = 2L;
        FollowingEntity entity = FollowingEntity.builder()
                .followerId(1L)
                .followeeId(followeeId)
                .build();

        when(followingRepository.findByFolloweeId(followeeId))
                .thenReturn(Collections.singletonList(entity));

        List<FollowingEntity> result = followingService.getFollowers(followeeId);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getFollowerId());
    }
}
