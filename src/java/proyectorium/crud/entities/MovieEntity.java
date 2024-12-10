/*
 * This class represents all the movies that will be created
 */
package proyectorium.crud.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@XmlRootElement
public class MovieEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private Integer duration;

    private String sinapsis;

    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;

    @Enumerated(EnumType.STRING)
    private MovieHour movieHour;

    @Lob
    private byte[] movieImage;

    @ManyToOne
    @JoinColumn(name = "provider")
    private ProviderEntity provider;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_category", schema = "proyectorium",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private List<CategoryEntity> categories;

    public MovieEntity() {

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

    public String getSinapsis() {
        return sinapsis;
    }

    public void setSinapsis(String sinapsis) {
        this.sinapsis = sinapsis;
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

}
