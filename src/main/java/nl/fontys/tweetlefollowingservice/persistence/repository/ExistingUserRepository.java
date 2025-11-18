package nl.fontys.tweetlefollowingservice.persistence.repository;

import nl.fontys.tweetlefollowingservice.persistence.entity.ExistingUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExistingUserRepository extends JpaRepository<ExistingUserEntity, Long> {
}
