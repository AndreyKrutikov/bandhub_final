package by.krutikov.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import static java.lang.Boolean.FALSE;

@Data
@EqualsAndHashCode(exclude = {"roles", "userProfile"})
@ToString(exclude = {"roles", "userProfile"})
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "date_modified")
    private Timestamp dateModified;

    @ManyToMany(mappedBy = "accounts", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JsonManagedReference
    @JsonIgnoreProperties("accounts")
    private Set<Role> roles;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private UserProfile userProfile;

    @PrePersist
    protected void onCreate() {
        this.isLocked = FALSE;
        this.dateCreated = new Timestamp(new Date().getTime());
        this.dateModified = dateCreated;
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModified = new Timestamp(new Date().getTime());
    }
}
