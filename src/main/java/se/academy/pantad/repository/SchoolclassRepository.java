package se.academy.pantad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.academy.pantad.domain.Schoolclass;

import java.util.Optional;


public interface SchoolclassRepository extends JpaRepository<Schoolclass, Long> {

    Optional<Schoolclass> findByUserId(Long userId);

}
