package data;

import java.util.Calendar;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
// import org.springframework.context.annotation.ComponentScan;

@Entity
@Table(name = "\"User\"")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "created", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    private Calendar created;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }
}
