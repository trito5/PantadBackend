package se.academy.pantad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.academy.pantad.domain.Pant;

import java.util.List;
import java.util.Optional;


public interface PantRepository extends JpaRepository<Pant, Long> {
    List<Pant> findByCollectedClassClassIdAndIsDeleted(Long collectedClassClassId, boolean isDeleted);
    Optional<Pant> findByCollectedClassClassIdAndPantId(Long collectedClassClassId, Long pantId);
    List<Pant> findByUserId(Long userId);
    List<Pant> findByUserIdAndIsDeleted(Long userId, boolean isDeleted);
    List<Pant> findByIsDeletedAndIsCollected(boolean isDeleted, boolean isCollected);

    // Optional<Schoolclass> findByUserId(Long userId);
}
