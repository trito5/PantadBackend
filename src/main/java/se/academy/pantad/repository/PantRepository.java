package se.academy.pantad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.academy.pantad.domain.Pant;

import java.util.List;


public interface PantRepository extends JpaRepository<Pant, Long> {
    List<Pant> findByCollectedClassClassId(Long collectedClassClassId);
    List<Pant> findByUserId(Long userId);
    List<Pant> findByUserIdAndIsDeleted(Long userId, boolean isDeleted);
    List<Pant> findByIsDeletedAndIsCollected(boolean isDeleted, boolean isCollected);

    // Optional<Schoolclass> findByUserId(Long userId);
}
