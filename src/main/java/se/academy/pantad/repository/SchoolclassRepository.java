package se.academy.pantad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.academy.pantad.domain.Schoolclass;


public interface SchoolclassRepository extends JpaRepository<Schoolclass, Long> {
}
