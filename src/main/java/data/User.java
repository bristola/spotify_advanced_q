package data;

import java.util.Date;
import java.util.Set;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "\"User\"")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", updatable = false)
    private Date created;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "ownerUser", cascade = CascadeType.ALL)
    private Set<Queue> queues;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Date getCreated() {
        return created;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
