/*
 * This class represents all the movies that will be created
 */
package proyectorium.crud.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author enzo
 */
@Entity
@Table(schema = "proyectorium", name = "movie")
@NamedQueries({
    @NamedQuery(
            name = "listByReleaseDate",
            query = "SELECT m FROM MovieEntity m ORDER BY m.releaseDate ASC"
    )
    ,
    @NamedQuery(
            name = "listByProvider",
            query = "SELECT m FROM MovieEntity m WHERE m.provider.name = :provider"
    )
    ,
    @NamedQuery(
            name = "listByMovieHour",
            query = "SELECT m FROM MovieEntity m WHERE m.movieHour = :movieHour"
    )
})

@XmlRootElement
public class MovieEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private Integer duration;

    private String sinopsis;

    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;

    @Enumerated(EnumType.STRING)
    private MovieHour movieHour;

    @Lob
    private byte[] movieImage;

    @OneToMany(cascade = ALL, fetch = FetchType.EAGER)
    private List<TicketEntity> tickets;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ProviderEntity provider;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_category", schema = "proyectorium",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private List<CategoryEntity> categories;

    public MovieEntity() {
        this.provider = new ProviderEntity();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public MovieHour getMovieHour() {
        return movieHour;
    }

    public void setMovieHour(MovieHour movieHour) {
        this.movieHour = movieHour;
    }

    public byte[] getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(byte[] movieImage) {
        this.movieImage = movieImage;
    }

    public List<TicketEntity> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }

    public ProviderEntity getProvider() {
        return provider;
    }

    public void setProvider(ProviderEntity provider) {
        this.provider = provider;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovieEntity)) {
            return false;
        }
        MovieEntity other = (MovieEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyectorium.crud.entities.MovieEntity[ id=" + id + " ]";
    }

}
