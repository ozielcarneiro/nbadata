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
public class BoxscorePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "gameid")
    private int gameid;
    @Basic(optional = false)
    @Column(name = "teamname")
    private String teamname;

    public BoxscorePK() {
    }

    public BoxscorePK(int gameid, String teamname) {
        this.gameid = gameid;
        this.teamname = teamname;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) gameid;
        hash += (teamname != null ? teamname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BoxscorePK)) {
            return false;
        }
        BoxscorePK other = (BoxscorePK) object;
        if (this.gameid != other.gameid) {
            return false;
        }
        if ((this.teamname == null && other.teamname != null) || (this.teamname != null && !this.teamname.equals(other.teamname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BoxscorePK[ gameid=" + gameid + ", teamname=" + teamname + " ]";
    }
    
}
