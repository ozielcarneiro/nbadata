/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ozielcarneiro
 */
@Entity
@Table(name = "player")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
    @NamedQuery(name = "Player.findByIdplayer", query = "SELECT p FROM Player p WHERE p.idplayer = :idplayer"),
    @NamedQuery(name = "Player.findByName", query = "SELECT p FROM Player p WHERE p.name = :name"),
    @NamedQuery(name = "Player.findByPosition", query = "SELECT p FROM Player p WHERE p.position = :position")})
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idplayer")
    private Integer idplayer;
    @Column(name = "name")
    private String name;
    @Column(name = "position")
    private String position;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private Collection<Playerboxscore> playerboxscoreCollection;

    public Player() {
    }

    public Player(Integer idplayer) {
        this.idplayer = idplayer;
    }

    public Integer getIdplayer() {
        return idplayer;
    }

    public void setIdplayer(Integer idplayer) {
        this.idplayer = idplayer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @XmlTransient
    public Collection<Playerboxscore> getPlayerboxscoreCollection() {
        return playerboxscoreCollection;
    }

    public void setPlayerboxscoreCollection(Collection<Playerboxscore> playerboxscoreCollection) {
        this.playerboxscoreCollection = playerboxscoreCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idplayer != null ? idplayer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.idplayer == null && other.idplayer != null) || (this.idplayer != null && !this.idplayer.equals(other.idplayer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Player[ idplayer=" + idplayer + "; name=" + name + " ]";
    }
    
}
