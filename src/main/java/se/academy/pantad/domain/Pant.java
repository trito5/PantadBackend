package se.academy.pantad.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString

public class Pant {

    @Id
    @GeneratedValue
    private Long pantId;
    @NonNull
    private String value;
    @NonNull
    private String address;
    @NonNull
    private String longitude;
    @NonNull
    private String latitude;
    @NonNull
    private String postalCode;
    @NonNull
    private String city;
    @NonNull
    private String collectTimeFrame;
    @ManyToOne
    private User user;
    @ManyToOne
    private Schoolclass collectedClass;
    @NonNull
    private boolean isCollected;
    @NonNull
    private boolean isDeleted;
    @NonNull
    private String collectInfo;

/*    public Pant(@NonNull String value, String address, String longitude, String latitude,
                User user, String collectInfo) {
        this.value = value;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.user = user;
        this.isCollected = false;
        this.collectInfo = collectInfo;

    }*/
}
