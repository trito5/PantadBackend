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
    private String postalCode;
    @NotBlank
    private String city;
    @NotBlank
    private String longitude;
    @NotBlank
    private String latitude;
    @NotBlank
    private String collectTimeFrame;
    @NotBlank
    private String collectInfo;
    @NotBlank
    private String userEmail;
    @NotBlank
    private String userName;


}