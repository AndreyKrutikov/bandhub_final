package by.krutikov.domain;

import by.krutikov.domain.attributeconverter.ExperienceAttributeConverter;
import by.krutikov.domain.attributeconverter.InstrumentAttributeConverter;
import by.krutikov.domain.enums.ExperienceLevel;
import by.krutikov.domain.enums.InstrumentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


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
    //@Setter(AccessLevel.NONE)
    //point is not serializable by json
    private Point location;

    @Column(name = "cell_phone_number")
    private String phoneNumber;

    @Column(name = "instrument_id")
    @Convert(converter = InstrumentAttributeConverter.class)
    private InstrumentType instrument;

    @Column(name = "experience_id")
    @Convert(converter = ExperienceAttributeConverter.class)
    private ExperienceLevel experience;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<Media> media;

    @Column
    private String description;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "date_modified")
    private Timestamp dateModified;

    @OneToMany(mappedBy = "fromProfile", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
//    @Fetch(value = FetchMode.SELECT)
    @JsonManagedReference
    private Set<Reaction> myReactions;

    @OneToMany(mappedBy = "toProfile", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
//    @Fetch(value = FetchMode.SELECT)
    @JsonManagedReference
    private Set<Reaction> othersReactions;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = new Timestamp(new Date().getTime());
        this.dateModified = dateCreated;
        this.isVisible = FALSE;
        this.location = geometryFactory.createPoint(new Coordinate(lon, lat));
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModified = new Timestamp(new Date().getTime());
        this.isVisible = media.isEmpty() ? FALSE : TRUE;
        this.location = geometryFactory.createPoint(new Coordinate(lon, lat));
    }

    @PostLoad
    protected void onRead() {
        this.isVisible = media.isEmpty() ? FALSE : TRUE;
        this.lon = location.getCoordinate().getX();
        this.lat = location.getCoordinate().getY();
    }
}

