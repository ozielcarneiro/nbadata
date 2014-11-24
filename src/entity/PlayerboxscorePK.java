/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ozielcarneiro
 */
@Embeddable
public class PlayerboxscorePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "gameid")
    private int gameid;
    @Basic(optional = false)
    @Column(name = "idplayer")
    private int idplayer;

    public PlayerboxscorePK() {
    }

    public PlayerboxscorePK(int gameid, int idplayer) {
        this.gameid = gameid;
        this.idplayer = idplayer;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public int getIdplayer() {
        return idplayer;
    }

    public void setIdplayer(int idplayer) {
        this.idplayer = idplayer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) gameid;
        hash += (int) idplayer;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlayerboxscorePK)) {
            return false;
        }
        PlayerboxscorePK other = (PlayerboxscorePK) object;
        if (this.gameid != other.gameid) {
            return false;
        }
        if (this.idplayer != other.idplayer) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PlayerboxscorePK[ gameid=" + gameid + ", idplayer=" + idplayer + " ]";
    }
    
}
