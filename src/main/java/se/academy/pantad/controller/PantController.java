package se.academy.pantad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.academy.pantad.domain.Pant;
import se.academy.pantad.domain.User;
import se.academy.pantad.payload.NewPantRequest;
import se.academy.pantad.repository.PantRepository;
import se.academy.pantad.repository.UserRepository;
import se.academy.pantad.security.CurrentUser;
import se.academy.pantad.security.UserPrincipal;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequestMapping("/pant")
@RestController
public class PantController {

    @Autowired
    private PantRepository pantRepository;

    @Autowired
    private UserRepository userRepository;


    //Registrera ny pant
    @PostMapping("/newPant")
    public ResponseEntity<?> registerPant(@Valid @RequestBody NewPantRequest newPantRequest, @CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByEmail(currentUser.getEmail()).get();
        Pant pant = new Pant(newPantRequest.getValue(), newPantRequest.getAddress(), newPantRequest.getLongitude(), newPantRequest.getLatitude(), false);
        pant.setUser(user);
        pantRepository.save(pant);
        return ResponseEntity.ok().build();
    }

    //Gör så att pant bli collected
    @GetMapping("/collectedPant/{pantId}")
    public ResponseEntity<?> collectedPant(@PathVariable Long pantId) {
        Optional<Pant> pant = pantRepository.findById(pantId);
        if (pant.isPresent()) {
            Pant pantFound = pant.get();
            pantFound.setCollected(true);
            Pant result = pantRepository.save(pantFound);
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().body(pantId + ": is not present");
        }
    }

    //lista med all pant som inte är collected
    @GetMapping("/allPant")
    public List<Pant> getAllPant() {
        List<Pant> pantList = pantRepository.findAll();
        List<Pant> availablePant = new ArrayList<>();

        pantList.forEach(p -> {
            if (!p.isCollected()) {
                availablePant.add(p);
            }
        });
        return availablePant;
    }

}

