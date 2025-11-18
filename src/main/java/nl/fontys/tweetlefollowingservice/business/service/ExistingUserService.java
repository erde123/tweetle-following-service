package nl.fontys.tweetlefollowingservice.business.service;

import nl.fontys.tweetlefollowingservice.domain.UserEvent;
import nl.fontys.tweetlefollowingservice.persistence.entity.ExistingUserEntity;
import nl.fontys.tweetlefollowingservice.persistence.repository.ExistingUserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ExistingUserService {

    private final ExistingUserRepository repository;

    public ExistingUserService(ExistingUserRepository repository) {
        this.repository = repository;
    }

    public ExistingUserEntity saveNewUserCredential(UserEvent event) {
        return upsertUser(event);
    }

    public ExistingUserEntity updateUser(UserEvent event) {
        return upsertUser(event);
    }

    public void deleteUser(Long userId) {
        if (repository.existsById(userId)) {
            repository.deleteById(userId);
        }
    }

    private ExistingUserEntity upsertUser(UserEvent event) {
        ExistingUserEntity entity = repository.findById(event.getUserId())
                .orElse(ExistingUserEntity.builder().userId(event.getUserId()).build());

        entity.setUsername(event.getUsername());
        entity.setAvatarUrl(event.getAvatarUrl());
        entity.setUpdatedAt(Instant.now().toEpochMilli());

        return repository.save(entity);
    }
}
