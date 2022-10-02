package by.krutikov.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.sql.Timestamp;


@Data
@Entity
@Table(name = "user_profiles")
public class Profile implements Serializable {
    private static final int SRID = 4326;
    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "displayed_name")
    private String displayedName;

    @OneToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private Account account;

    @Transient
    private Double longitude;
    @Transient
    private Double latitude;
    @JsonIgnore
    private Geometry location;

    @Column(name = "cell_phone_number")
    private String phoneNumber;

//    //@Column
//    private InstrumentType instrument;
//
//    //@Column
//    private Experience experience;

    @OneToOne
    @JoinColumn(name = "media_id")
    @JsonBackReference
    private Media media;

    @Column
    private String description;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "date_modified")
    private Timestamp dateModified;

    public Double getLongitude() {
        return location.getCoordinate().getX();
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return location.getCoordinate().getY();
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}

