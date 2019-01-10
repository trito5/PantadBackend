package se.academy.pantad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.academy.pantad.domain.Pant;

import java.util.List;


public interface PantRepository extends JpaRepository<Pant, Long> {
    List<Pant> findByCollectedClassClassId(Long collectedClassClassId);

    // Optional<Schoolclass> findByUserId(Long userId);
}
