package nl.fontys.tweetlefollowingservice.controller;

import nl.fontys.tweetlefollowingservice.business.service.FollowingService;
import nl.fontys.tweetlefollowingservice.persistence.entity.FollowingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowingControllerTest {

    @Mock
    private FollowingService followingService;

    @InjectMocks
    private FollowingController followingController;

    private FollowingEntity testFollowing;

    @BeforeEach
    void setUp() {
        testFollowing = FollowingEntity.builder()
                .id(1L)
                .followerId(10L)
                .followeeId(20L)
                .createdAt(System.currentTimeMillis())
                .build();
    }

    @Test
    void followUser_WhenSuccessful_ShouldReturnFollowingEntity() {
        when(followingService.followUser(10L, 20L)).thenReturn(testFollowing);

        ResponseEntity<FollowingEntity> response = followingController.followUser(10L, 20L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testFollowing, response.getBody());
        verify(followingService, times(1)).followUser(10L, 20L);
    }

    @Test
    void followUser_WhenAlreadyFollowing_ShouldThrowException() {
        when(followingService.followUser(10L, 20L))
                .thenThrow(new IllegalStateException("Already following this user."));

        Exception exception = assertThrows(IllegalStateException.class, () ->
                followingController.followUser(10L, 20L));

        assertEquals("Already following this user.", exception.getMessage());
        verify(followingService, times(1)).followUser(10L, 20L);
    }

    @Test
    void unfollowUser_WhenSuccessful_ShouldReturnNoContent() {
        doNothing().when(followingService).unfollowUser(10L, 20L);

        ResponseEntity<Void> response = followingController.unfollowUser(10L, 20L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(followingService, times(1)).unfollowUser(10L, 20L);
    }

    @Test
    void getFollowing_WhenSuccessful_ShouldReturnList() {
        when(followingService.getFollowing(10L)).thenReturn(Arrays.asList(testFollowing));

        ResponseEntity<List<FollowingEntity>> response = followingController.getFollowing(10L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(20L, response.getBody().get(0).getFolloweeId());
        verify(followingService, times(1)).getFollowing(10L);
    }

    @Test
    void getFollowers_WhenSuccessful_ShouldReturnList() {
        when(followingService.getFollowers(20L)).thenReturn(Arrays.asList(testFollowing));

        ResponseEntity<List<FollowingEntity>> response = followingController.getFollowers(20L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(10L, response.getBody().get(0).getFollowerId());
        verify(followingService, times(1)).getFollowers(20L);
    }

    @Test
    void getFollowing_WhenEmpty_ShouldReturnEmptyList() {
        when(followingService.getFollowing(10L)).thenReturn(List.of());

        ResponseEntity<List<FollowingEntity>> response = followingController.getFollowing(10L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(followingService, times(1)).getFollowing(10L);
    }

    @Test
    void getFollowers_WhenEmpty_ShouldReturnEmptyList() {
        when(followingService.getFollowers(20L)).thenReturn(List.of());

        ResponseEntity<List<FollowingEntity>> response = followingController.getFollowers(20L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(followingService, times(1)).getFollowers(20L);
    }
}
