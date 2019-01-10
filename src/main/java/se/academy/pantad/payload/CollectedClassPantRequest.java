package se.academy.pantad.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectedClassPantRequest {

    @NotBlank
    private Long id;
    @NotBlank
    private String value;
    @NotBlank
    private String address;
    @NotBlank
    private String longitude;
    @NotBlank
    private String latitude;


}