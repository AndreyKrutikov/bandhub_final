package by.krutikov.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "demo_url")
    private String demoUrl;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "date_modified")
    private Timestamp dateModified;

    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    @JsonBackReference
    private UserProfile userProfile;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = new Timestamp(new Date().getTime());
        this.dateModified = dateCreated;
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModified = new Timestamp(new Date().getTime());
    }
}
