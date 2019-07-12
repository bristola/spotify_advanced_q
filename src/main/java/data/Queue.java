package data;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import java.util.Set;
// import org.springframework.format.annotation.DateTimeFormat;
// import org.springframework.context.annotation.ComponentScan;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "\"Queue\"")
public class Queue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "queuename")
    private String queueName;

    @ManyToOne
    @JoinColumn
    private User ownerUser;

    @OneToMany(mappedBy = "parentQueue", cascade = CascadeType.ALL)
    private Set<QueueComponent> queueComponents;

    public Queue() {
    }

    public Queue(String queueName, User ownerUser) {
        this.queueName = queueName;
        this.ownerUser = ownerUser;
    }

    public long getId() {
        return id;
    }

    public String getQueueName() {
        return queueName;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public Set<QueueComponent> getQueueComponents() {
        return queueComponents;
    }

    public void addQueueComponent(QueueComponent queueComponent) {
        queueComponents.add(queueComponent);
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

}
