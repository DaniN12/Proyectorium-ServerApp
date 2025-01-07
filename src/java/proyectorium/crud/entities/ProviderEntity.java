/*
Entity class for providers
 */
package proyectorium.crud.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dani
 */

@NamedQueries({
    
            @NamedQuery(
                        name="listByContractInit", query="SELECT * FROM provider ORDER BY 'contract init' ASC"
                ),
            
            @NamedQuery(
                        name="listByContractEnd", query="SELECT * FROM provider ORDER BY 'contract end' DESC"
                ),
            
            @NamedQuery(
                        name="listByPrice", query="SELECT * FROM provider ORDER BY 'price' ASC"
                ),
     
})

@Entity
@Table(name = "provider", schema = "proyectorium")
@XmlRootElement
public class ProviderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String name;

    private Integer phone;

    @Temporal(TemporalType.TIMESTAMP)
    private Date contactIn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date contactEnd;

    private Float price;
    
    @OneToMany(cascade = ALL)
    private List<MovieEntity> movies;

    public ProviderEntity() {

    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Date getContactIn() {
        return contactIn;
    }

    public void setContactIn(Date contactIn) {
        this.contactIn = contactIn;
    }

    public Date getContactEnd() {
        return contactEnd;
    }

    public void setContactEnd(Date contactEnd) {
        this.contactEnd = contactEnd;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MovieEntity> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
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
        if (!(object instanceof CategoryEntity)) {
            return false;
        }
        CategoryEntity other = (CategoryEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyectorium.crud.entities.ProviderEntity[ id=" + id + " ]";
    }

}
