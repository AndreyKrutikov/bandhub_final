package by.krutikov.domain;

import by.krutikov.domain.converter.ExperienceAttributeConverter;
import by.krutikov.domain.converter.InstrumentAttributeConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;


@Data
@Entity
@Table(name = "user_profiles")
public class Profile {
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
    private double lon;

    @Transient
    private double lat;

    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Point location;// = geometryFactory.createPoint(new Coordinate(lon, lat));

    @Column(name = "cell_phone_number")
    private String phoneNumber;

    @Column(name = "instrument_id")
    @Convert(converter = InstrumentAttributeConverter.class)
    private InstrumentType instrument;

    @Column(name = "experience_id")
    @Convert(converter = ExperienceAttributeConverter.class)
    private ExperienceLevel experience;

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

    public double getLon() {
        return location.getCoordinate().getX();
    }

    public void setLon(double lon) {
        this.lon = lon;
        this.setLocation();
    }

    public double getLat() {
        return location.getCoordinate().getY();
    }

    public void setLat(double lat) {
        this.lat = lat;
        this.setLocation();
    }

    private void setLocation() {
        this.location = geometryFactory.createPoint(new Coordinate(lon, lat));
    }
}

