/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectorium.crud.entities;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author kbilb
 */
@Entity
@DiscriminatorValue("Admin")
public class AdminEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Long nSS;

    public Long getnSS() {
        return nSS;
    }

    public void setnSS(Long nSS) {
        this.nSS = nSS;
    }
    
}
