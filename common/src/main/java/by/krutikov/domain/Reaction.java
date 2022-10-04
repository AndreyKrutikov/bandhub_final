package by.krutikov.domain;

import by.krutikov.domain.converter.ReactionAttributeConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "reactions")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_profile")
    private Long fromProfile;

    @Column(name = "to_profile")
    private Long toProfile;

    @Column(name = "reaction_type_id")
    @Convert(converter = ReactionAttributeConverter.class)
    private ReactionType reactionType;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "date_modified")
    private Timestamp dateModified;

}
