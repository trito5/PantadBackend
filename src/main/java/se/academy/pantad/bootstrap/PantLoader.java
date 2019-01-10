package se.academy.pantad.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.academy.pantad.domain.Pant;
import se.academy.pantad.repository.PantRepository;
import se.academy.pantad.repository.SchoolclassRepository;
import se.academy.pantad.repository.UserRepository;

import java.util.List;

@Component
public class PantLoader implements CommandLineRunner {

    @Autowired
    private PantRepository pantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SchoolclassRepository schoolclassRepository;


    @Override
    public void run(String... args) throws Exception {

        List<Pant> allPant = pantRepository.findAll();

        allPant.forEach(p -> {
            p.setCollected(false);
            p.setDeleted(false);
            pantRepository.save(p);
        });




    }
}

