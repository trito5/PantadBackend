package se.academy.pantad.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Pant {

    @Id
    @GeneratedValue
    private Long pantId;
    @NonNull
    private String value;
    private String address;
    private String longitude;
    private String latitude;
    @ManyToOne
    private User user;
    @ManyToOne
    private Schoolclass collectedByClassId;
    private boolean isCollected;
    private String collectInfo;

    public Pant(@NonNull String value, String address, String longitude, String latitude,
                User user, String collectInfo) {
        this.value = value;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.user = user;
        this.isCollected = false;
        this.collectInfo = collectInfo;

    }
}
