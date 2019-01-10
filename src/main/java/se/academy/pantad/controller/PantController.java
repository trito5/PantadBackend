package se.academy.pantad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.academy.pantad.domain.Pant;
import se.academy.pantad.domain.Schoolclass;
import se.academy.pantad.domain.User;
import se.academy.pantad.payload.CollectedClassPantRequest;
import se.academy.pantad.payload.NewPantRequest;
import se.academy.pantad.repository.PantRepository;
import se.academy.pantad.repository.SchoolclassRepository;
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

    @Autowired
    private SchoolclassRepository schoolclassRepository;


    //Registrera ny pant
    @PostMapping("/newPant")
    public ResponseEntity<?> registerPant(@Valid @RequestBody NewPantRequest newPantRequest, @CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByEmail(currentUser.getEmail()).get();
        String info = (newPantRequest.getCollectInfo().equals("") || newPantRequest.getCollectInfo() == null) ? "" : newPantRequest.getCollectInfo();
        Pant pant = new Pant(newPantRequest.getValue(), newPantRequest.getAddress(), newPantRequest.getLongitude(), newPantRequest.getLatitude(),
                newPantRequest.getPostalCode(), newPantRequest.getCity(), newPantRequest.getCollectTimeFrame(),false, false, info);
        pant.setUser(user);
        Pant result = pantRepository.save(pant);
        return ResponseEntity.ok().body(result);
    }

    //Gör så att pant bli collected
    @GetMapping("/collectedPant/{pantId}")
    public ResponseEntity<?> collectedPant(@PathVariable Long pantId, @CurrentUser UserPrincipal currentUser) {
        Optional<Pant> pant = pantRepository.findById(pantId);
        if (pant.isPresent()) {
            Pant pantFound = pant.get();
            pantFound.setCollected(true);

            Schoolclass schoolclass = schoolclassRepository.findByUserId(currentUser.getId()).get();
            pantFound.setCollectedClass(schoolclass);

            Pant result = pantRepository.save(pantFound);
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().body(pantId + ": is not present");
        }
    }

    //Användare deletar en pant
    @GetMapping("/deletePant/{pantId}")
    public ResponseEntity<?> deletePant(@PathVariable Long pantId){
        Optional<Pant> pant = pantRepository.findById(pantId);
        if(pant.isPresent()){
            Pant pantFound = pant.get();
            pantFound.setDeleted(true);
            Pant result = pantRepository.save(pantFound);
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().body(pantId + ": is not present");
        }
    }

    //Gör så att pant blir uncollected
    @GetMapping ("/unCollectPant/{pantId}")
    public ResponseEntity<?> unCollectPant (@PathVariable Long pantId){
        Optional<Pant> pant = pantRepository.findById(pantId);
        if (pant.isPresent()){
            Pant pantFound = pant.get();
            pantFound.setCollected(false);
            pantFound.setCollectedClass(null);

            Pant result = pantRepository.save(pantFound);
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().body(pantId + ": is not present");
        }
    }

    //Hämta all pant som är collectad av en viss klass
    @GetMapping("/collectedPant")
    public List<CollectedClassPantRequest> getCollectedPant(@CurrentUser UserPrincipal currentUser){

        Schoolclass schoolclass = schoolclassRepository.findByUserId(currentUser.getId()).get();

        List<Pant> pantList = pantRepository.findByCollectedClassClassId(schoolclass.getClassId());
        List<CollectedClassPantRequest> classPant = new ArrayList<>();

        pantList.forEach(p -> {
            classPant.add(new CollectedClassPantRequest(p.getPantId(), p.getValue(), p.getAddress(), p.getLongitude(), p.getLatitude()));
        });

        return classPant;
    }

    //Hämta all pant som är lämnad av en viss användare
    @GetMapping("/getUserPant")
    public List<Pant>  getUserPant(@CurrentUser UserPrincipal currentUser) {
        List<Pant> pantList = pantRepository.findByUserId(currentUser.getId());
        return pantList;
    }
    //lista med all pant som inte är collected
    @GetMapping("/allPant")
    public List<Pant> getAllPant() {
        List<Pant> pantList = pantRepository.findAll();
        List<Pant> availablePant = new ArrayList<>();

        pantList.forEach(p -> {
            if (!p.isCollected() && !p.isDeleted()) {
                availablePant.add(p);
            }
        });
        return availablePant;
    }

}

