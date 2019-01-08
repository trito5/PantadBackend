package se.academy.pantad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.academy.pantad.domain.Pant;


public interface PantRepository extends JpaRepository<Pant, Long> {
}
