/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ozielcarneiro
 */
@Entity
@Table(name = "game")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g"),
    @NamedQuery(name = "Game.findByIdgame", query = "SELECT g FROM Game g WHERE g.idgame = :idgame"),
    @NamedQuery(name = "Game.findByGameDate", query = "SELECT g FROM Game g WHERE g.gameDate = :gameDate")})
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idgame")
    private Integer idgame;
    @Basic(optional = false)
    @Column(name = "gameDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gameDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Collection<Playerboxscore> playerboxscoreCollection;
    @JoinColumn(name = "awayTeam", referencedColumnName = "name")
    @ManyToOne(optional = false)
    private Team awayTeam;
    @JoinColumn(name = "homeTeam", referencedColumnName = "name")
    @ManyToOne(optional = false)
    private Team homeTeam;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Collection<Boxscore> boxscoreCollection;

    public Game() {
    }

    public Game(Integer idgame) {
        this.idgame = idgame;
    }

    public Game(Integer idgame, Date gameDate) {
        this.idgame = idgame;
        this.gameDate = gameDate;
    }

    public Integer getIdgame() {
        return idgame;
    }

    public void setIdgame(Integer idgame) {
        this.idgame = idgame;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    @XmlTransient
    public Collection<Playerboxscore> getPlayerboxscoreCollection() {
        return playerboxscoreCollection;
    }

    public void setPlayerboxscoreCollection(Collection<Playerboxscore> playerboxscoreCollection) {
        this.playerboxscoreCollection = playerboxscoreCollection;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
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
        hash += (idgame != null ? idgame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.idgame == null && other.idgame != null) || (this.idgame != null && !this.idgame.equals(other.idgame))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Game[ idgame=" + idgame + " ]";
    }
    
}
