package by.krutikov.domain;

import by.krutikov.domain.attributeconverter.ExperienceAttributeConverter;
import by.krutikov.domain.attributeconverter.InstrumentAttributeConverter;
import by.krutikov.domain.enums.ExperienceLevel;
import by.krutikov.domain.enums.InstrumentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;


@Data
@EqualsAndHashCode(exclude = {"media", "account", "myReactions", "othersReactions"})
@ToString(exclude = {"media", "account", "myReactions", "othersReactions"})
@Entity
@Table(name = "user_profiles")
public class UserProfile {
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
    //@Getter(AccessLevel.NONE)
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


    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Media media;

    @Column
    private String description;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "date_modified")
    private Timestamp dateModified;

    @OneToMany(mappedBy = "fromProfile", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonManagedReference
    private Set<Reaction> myReactions;

    @OneToMany(mappedBy = "toProfile", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonManagedReference
    private Set<Reaction> othersReactions;

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

    @PrePersist
    protected void onCreate() {
        this.dateCreated = new Timestamp(new Date().getTime());
        this.dateModified = this.dateCreated;
        this.isVisible = Boolean.TRUE;
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModified = new Timestamp(new Date().getTime());
    }
}

