package se.academy.pantad.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.academy.pantad.domain.User;
import se.academy.pantad.payload.UserIdentityAvailability;
import se.academy.pantad.payload.UserSummary;
import se.academy.pantad.repository.UserRepository;
import se.academy.pantad.security.CurrentUser;
import se.academy.pantad.security.UserPrincipal;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //Kvar eller inte kvar?
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByEmail(currentUser.getEmail()).get();
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getName(), user.isSchoolclass());
        return userSummary;
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

}
