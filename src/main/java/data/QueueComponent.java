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
import javax.persistence.JoinColumn;
// import org.springframework.format.annotation.DateTimeFormat;
// import org.springframework.context.annotation.ComponentScan;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "\"QueueComponent\"")
public class QueueComponent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn
    private Queue parentQueue;

    @Column(name = "playlist")
    private String playlist;

    @Column(name = "genres")
    private String genres;

    @Column(name = "artists")
    private String artists;

    @Column(name = "albums")
    private String albums;

    @Column(name = "popularityMin")
    private Integer popularityMin;

    @Column(name = "popularityMax")
    private Integer popularityMax;

    public QueueComponent() {
    }

    public long getId() {
        return id;
    }

    public Queue getParentQueue() {
        return parentQueue;
    }

    public String getGenres() {
        return genres;
    }

    public String getArtists() {
        return artists;
    }

    public String getAlbums() {
        return albums;
    }

    public Integer getPopularityMin() {
        return popularityMin;
    }

    public Integer getPopularityMax() {
        return popularityMax;
    }

}
