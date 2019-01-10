package se.academy.pantad.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPantRequest {

    @NotBlank
    private String value;
    @NotBlank
    private String address;
    @NotBlank
    private String longitude;
    @NotBlank
    private String latitude;
    @NotBlank
    private String postalCode;
    @NotBlank
    private String city;
    @NotBlank
    private String collectTimeFrame;
    private String collectInfo;

}
