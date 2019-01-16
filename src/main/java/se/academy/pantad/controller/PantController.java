package se.academy.pantad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.academy.pantad.domain.Pant;
import se.academy.pantad.domain.Schoolclass;
import se.academy.pantad.domain.User;
import se.academy.pantad.payload.CollectedClassPantRequest;
import se.academy.pantad.payload.NewPantRequest;
import se.academy.pantad.payload.ResponseStatus;
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


    //En användare registrerar ny pant
    @PostMapping("/newPant")
    public ResponseEntity<?> registerPant(@Valid @RequestBody NewPantRequest newPantRequest, @CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByEmail(currentUser.getEmail()).get();
        String info = (newPantRequest.getCollectInfo().equals("") || newPantRequest.getCollectInfo() == null) ? "" : newPantRequest.getCollectInfo();
        Pant pant = new Pant(newPantRequest.getValue(), newPantRequest.getAddress(), newPantRequest.getLongitude(), newPantRequest.getLatitude(),
                newPantRequest.getPostalCode(), newPantRequest.getCity(), newPantRequest.getCollectTimeFrame(),false, false, info);
        pant.setUser(user);
        pantRepository.save(pant);
        return ResponseEntity.ok().body(new ResponseStatus("Pant added"));
    }

    //När skolklass väljer att hämta pant, så binds panten till aktuell skolklass samt markeras som isCollected
    @GetMapping("/collectedPant/{pantId}")
    public ResponseEntity<?> collectedPant(@PathVariable Long pantId, @CurrentUser UserPrincipal currentUser) {
        Optional<Pant> pant = pantRepository.findById(pantId);
        if (pant.isPresent()) {
            Pant pantFound = pant.get();
            pantFound.setCollected(true);
            Schoolclass schoolclass = schoolclassRepository.findByUserId(currentUser.getId()).get();
            pantFound.setCollectedClass(schoolclass);
            pantRepository.save(pantFound);
            return ResponseEntity.ok().body(new ResponseStatus("Pant collected"));
        } else {
            return ResponseEntity.badRequest().body(new ResponseStatus( "PantId: " +pantId +" is not found"));
        }
    }

    //Användare tar bort en pant. Då markeras den som isDeleted i databasen.
    @GetMapping("/deletePant/{pantId}")
    public ResponseEntity<?> deletePant(@PathVariable Long pantId){
        Optional<Pant> pant = pantRepository.findById(pantId);
        if(pant.isPresent()){
            Pant pantFound = pant.get();
            pantFound.setDeleted(true);
            pantRepository.save(pantFound);
            return ResponseEntity.ok().body(new ResponseStatus("Pant deleted"));
        } else {
            return ResponseEntity.badRequest().body(new ResponseStatus( "PantId: " +pantId +" is not found"));
        }
    }

    @GetMapping("/doneCollecting/{pantId}")
    public ResponseEntity<?> doneCollecting(@PathVariable Long pantId, @CurrentUser UserPrincipal currentUser){
        Optional<Schoolclass> schoolclass = schoolclassRepository.findByUserId(currentUser.getId());

        if (schoolclass.isPresent()){
            Schoolclass schoolclassFound = schoolclass.get();
            Optional<Pant> pant = pantRepository.findByCollectedClassClassIdAndPantId(schoolclassFound.getClassId(), pantId);
            if (pant.isPresent()){
                Pant pantFound = pant.get();
                pantFound.setDeleted(true);
                return ResponseEntity.ok().body(new ResponseStatus("Pant done collecting"));
            }
        }
        return ResponseEntity.badRequest().body(new ResponseStatus("Pant not found"));
    }

    //Hämta all pant som är hämtad av en viss klass
    @GetMapping("/collectedPant")
    public List<CollectedClassPantRequest> getCollectedPant(@CurrentUser UserPrincipal currentUser){

        Schoolclass schoolclass = schoolclassRepository.findByUserId(currentUser.getId()).get();

        List<Pant> pantList = pantRepository.findByCollectedClassClassIdAndIsDeleted(schoolclass.getClassId(), false);
        List<CollectedClassPantRequest> classPant = new ArrayList<>();

        pantList.forEach(p -> {
            User user = p.getUser();
            classPant.add(new CollectedClassPantRequest(p.getPantId(), p.getValue(), p.getAddress(), p.getPostalCode(), p.getCity(), p.getLongitude(), p.getLatitude(), p.getCollectTimeFrame(), p.getCollectInfo(), user.getEmail(), user.getName()));
        });

        return classPant;
    }

    //När en skolklass ångrar en hämtning av pant.
    @GetMapping ("/unCollectPant/{pantId}")
    public ResponseEntity<?> unCollectPant (@PathVariable Long pantId){
        Optional<Pant> pant = pantRepository.findById(pantId);
        if (pant.isPresent()){
            Pant pantFound = pant.get();
            pantFound.setCollected(false);
            pantFound.setCollectedClass(null);
            pantRepository.save(pantFound);
            return ResponseEntity.ok().body(new ResponseStatus("Pant unCollected"));
        } else {
            return ResponseEntity.badRequest().body(new ResponseStatus("Pant is not present"));
        }
    }

    //Hämta all pant som är lämnad av en viss användare.
    @GetMapping("/getUserPant")
    public List<Pant>  getUserPant(@CurrentUser UserPrincipal currentUser) {
        List<Pant> pantList = pantRepository.findByUserIdAndIsDeleted(currentUser.getId(), false);
        return pantList;
    }

    //lista med all pant som inte är hämtad.
    @GetMapping("/allPant")
    public List<Pant> getAllPant() {
        List<Pant> pantList = pantRepository.findByIsDeletedAndIsCollected(false, false);
        return pantList;
    }

}

