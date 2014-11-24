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
@Table(name = "team")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t"),
    @NamedQuery(name = "Team.findByName", query = "SELECT t FROM Team t WHERE t.name = :name")})
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "awayTeam")
    private Collection<Game> gameCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "homeTeam")
    private Collection<Game> gameCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    private Collection<Boxscore> boxscoreCollection;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Game> getGameCollection() {
        return gameCollection;
    }

    public void setGameCollection(Collection<Game> gameCollection) {
        this.gameCollection = gameCollection;
    }

    @XmlTransient
    public Collection<Game> getGameCollection1() {
        return gameCollection1;
    }

    public void setGameCollection1(Collection<Game> gameCollection1) {
        this.gameCollection1 = gameCollection1;
    }

    @XmlTransient
    public Collection<Boxscore> getBoxscoreCollection() {
        return boxscoreCollection;
    }

    public void setBoxscoreCollection(Collection<Boxscore> boxscoreCollection) {
        this.boxscoreCollection = boxscoreCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Team[ name=" + name + " ]";
    }
    
}
