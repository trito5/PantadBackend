/*
package se.academy.pantad.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.academy.pantad.domain.Pant;
import se.academy.pantad.domain.User;
import se.academy.pantad.repository.PantRepository;
import se.academy.pantad.repository.SchoolclassRepository;
import se.academy.pantad.repository.UserRepository;

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
        User user = new User("hej@hotmail.com", "password", "Anders", "pantad");
        userRepository.save(user);
        Pant pant = new Pant("130", "Helgalunden 13", "45", "65", user, "Massa pant!");
        pantRepository.save(pant);

    }
}
*/
