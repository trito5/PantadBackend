package se.academy.pantad.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter @Getter
public class UserSummary {

    private Long id;
    private String name;
    private boolean isSchoolclass;

}
