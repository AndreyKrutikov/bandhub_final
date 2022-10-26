package by.krutikov.domain;

import by.krutikov.domain.attributeconverter.ReactionAttributeConverter;
import by.krutikov.domain.enums.ReactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;


@Data
@Entity
@Table(name = "reactions")
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_profile")
    @JsonBackReference
    private UserProfile fromProfile;

    @ManyToOne
    @JoinColumn(name = "to_profile")
    @JsonBackReference
    private UserProfile toProfile;

    @Column(name = "reaction_type_id")
    @Convert(converter = ReactionAttributeConverter.class)
    private ReactionType reactionType;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = new Timestamp(new Date().getTime());
    }
}
