package se.academy.pantad.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Schoolclass {


    @Id
    @GeneratedValue
    private Long classId;
    @NonNull
    private String school;
    @NonNull
    private String className;
    @NonNull
    @OneToOne
    private User user;

}
