package se.academy.pantad.payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtAuthenticationResponse {

    @NonNull
    private String accessToken;
    private String tokenType = "Bearer";

}
