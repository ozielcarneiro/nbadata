/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ozielcarneiro
 */
@Entity
@Table(name = "boxscore")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boxscore.findAll", query = "SELECT b FROM Boxscore b"),
    @NamedQuery(name = "Boxscore.findByGameid", query = "SELECT b FROM Boxscore b WHERE b.boxscorePK.gameid = :gameid"),
    @NamedQuery(name = "Boxscore.findByTeamname", query = "SELECT b FROM Boxscore b WHERE b.boxscorePK.teamname = :teamname"),
    @NamedQuery(name = "Boxscore.findByHome", query = "SELECT b FROM Boxscore b WHERE b.home = :home"),
    @NamedQuery(name = "Boxscore.findByWin", query = "SELECT b FROM Boxscore b WHERE b.win = :win"),
    @NamedQuery(name = "Boxscore.findByFgm", query = "SELECT b FROM Boxscore b WHERE b.fgm = :fgm"),
    @NamedQuery(name = "Boxscore.findByFga", query = "SELECT b FROM Boxscore b WHERE b.fga = :fga"),
    @NamedQuery(name = "Boxscore.findByTpm", query = "SELECT b FROM Boxscore b WHERE b.tpm = :tpm"),
    @NamedQuery(name = "Boxscore.findByTpa", query = "SELECT b FROM Boxscore b WHERE b.tpa = :tpa"),
    @NamedQuery(name = "Boxscore.findByFtm", query = "SELECT b FROM Boxscore b WHERE b.ftm = :ftm"),
    @NamedQuery(name = "Boxscore.findByFta", query = "SELECT b FROM Boxscore b WHERE b.fta = :fta"),
    @NamedQuery(name = "Boxscore.findByOreb", query = "SELECT b FROM Boxscore b WHERE b.oreb = :oreb"),
    @NamedQuery(name = "Boxscore.findByDreb", query = "SELECT b FROM Boxscore b WHERE b.dreb = :dreb"),
    @NamedQuery(name = "Boxscore.findByReb", query = "SELECT b FROM Boxscore b WHERE b.reb = :reb"),
    @NamedQuery(name = "Boxscore.findByAst", query = "SELECT b FROM Boxscore b WHERE b.ast = :ast"),
    @NamedQuery(name = "Boxscore.findByStl", query = "SELECT b FROM Boxscore b WHERE b.stl = :stl"),
    @NamedQuery(name = "Boxscore.findByBlk", query = "SELECT b FROM Boxscore b WHERE b.blk = :blk"),
    @NamedQuery(name = "Boxscore.findByTov", query = "SELECT b FROM Boxscore b WHERE b.tov = :tov"),
    @NamedQuery(name = "Boxscore.findByPf", query = "SELECT b FROM Boxscore b WHERE b.pf = :pf"),
    @NamedQuery(name = "Boxscore.findByPts", query = "SELECT b FROM Boxscore b WHERE b.pts = :pts")})
public class Boxscore implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BoxscorePK boxscorePK;
    @Basic(optional = false)
    @Column(name = "home")
    private boolean home;
    @Basic(optional = false)
    @Column(name = "win")
    private boolean win;
    @Column(name = "fgm")
    private Integer fgm;
    @Column(name = "fga")
    private Integer fga;
    @Column(name = "tpm")
    private Integer tpm;
    @Column(name = "tpa")
    private Integer tpa;
    @Column(name = "ftm")
    private Integer ftm;
    @Column(name = "fta")
    private Integer fta;
    @Column(name = "oreb")
    private Integer oreb;
    @Column(name = "dreb")
    private Integer dreb;
    @Column(name = "reb")
    private Integer reb;
    @Column(name = "ast")
    private Integer ast;
    @Column(name = "stl")
    private Integer stl;
    @Column(name = "blk")
    private Integer blk;
    @Column(name = "tov")
    private Integer tov;
    @Column(name = "pf")
    private Integer pf;
    @Column(name = "pts")
    private Integer pts;
    @JoinColumn(name = "gameid", referencedColumnName = "idgame", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Game game;
    @JoinColumn(name = "teamname", referencedColumnName = "name", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Team team;

    public Boxscore() {
    }

    public Boxscore(BoxscorePK boxscorePK) {
        this.boxscorePK = boxscorePK;
    }

    public Boxscore(BoxscorePK boxscorePK, boolean home, boolean win) {
        this.boxscorePK = boxscorePK;
        this.home = home;
        this.win = win;
    }

    public Boxscore(int gameid, String teamname) {
        this.boxscorePK = new BoxscorePK(gameid, teamname);
    }

    public BoxscorePK getBoxscorePK() {
        return boxscorePK;
    }

    public void setBoxscorePK(BoxscorePK boxscorePK) {
        this.boxscorePK = boxscorePK;
    }

    public boolean getHome() {
        return home;
    }

    public void setHome(boolean home) {
        this.home = home;
    }

    public boolean getWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public Integer getFgm() {
        return fgm;
    }

    public void setFgm(Integer fgm) {
        this.fgm = fgm;
    }

    public Integer getFga() {
        return fga;
    }

    public void setFga(Integer fga) {
        this.fga = fga;
    }

    public Integer getTpm() {
        return tpm;
    }

    public void setTpm(Integer tpm) {
        this.tpm = tpm;
    }

    public Integer getTpa() {
        return tpa;
    }

    public void setTpa(Integer tpa) {
        this.tpa = tpa;
    }

    public Integer getFtm() {
        return ftm;
    }

    public void setFtm(Integer ftm) {
        this.ftm = ftm;
    }

    public Integer getFta() {
        return fta;
    }

    public void setFta(Integer fta) {
        this.fta = fta;
    }

    public Integer getOreb() {
        return oreb;
    }

    public void setOreb(Integer oreb) {
        this.oreb = oreb;
    }

    public Integer getDreb() {
        return dreb;
    }

    public void setDreb(Integer dreb) {
        this.dreb = dreb;
    }

    public Integer getReb() {
        return reb;
    }

    public void setReb(Integer reb) {
        this.reb = reb;
    }

    public Integer getAst() {
        return ast;
    }

    public void setAst(Integer ast) {
        this.ast = ast;
    }

    public Integer getStl() {
        return stl;
    }

    public void setStl(Integer stl) {
        this.stl = stl;
    }

    public Integer getBlk() {
        return blk;
    }

    public void setBlk(Integer blk) {
        this.blk = blk;
    }

    public Integer getTov() {
        return tov;
    }

    public void setTov(Integer tov) {
        this.tov = tov;
    }

    public Integer getPf() {
        return pf;
    }

    public void setPf(Integer pf) {
        this.pf = pf;
    }

    public Integer getPts() {
        return pts;
    }

    public void setPts(Integer pts) {
        this.pts = pts;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (boxscorePK != null ? boxscorePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Boxscore)) {
            return false;
        }
        Boxscore other = (Boxscore) object;
        if ((this.boxscorePK == null && other.boxscorePK != null) || (this.boxscorePK != null && !this.boxscorePK.equals(other.boxscorePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Boxscore[ boxscorePK=" + boxscorePK + " ]";
    }
    
}
