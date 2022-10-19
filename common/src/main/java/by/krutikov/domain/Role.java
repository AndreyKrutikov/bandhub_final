package by.krutikov.domain;

import by.krutikov.domain.enums.SystemRoles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
//@EqualsAndHashCode(exclude = "accounts")
//@ToString(exclude = "accounts")
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private SystemRoles roleName;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "date_modified")
    private Timestamp dateModified;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "l_account_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    @JsonIgnoreProperties("roles")
    private Set<Account> accounts;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = new Timestamp(new Date().getTime());
        this.dateModified = this.dateCreated;
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModified = new Timestamp(new Date().getTime());
    }
}
