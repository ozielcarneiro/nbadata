/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
@Table(name = "playerboxscore")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Playerboxscore.findAll", query = "SELECT p FROM Playerboxscore p"),
    @NamedQuery(name = "Playerboxscore.findByGameid", query = "SELECT p FROM Playerboxscore p WHERE p.playerboxscorePK.gameid = :gameid"),
    @NamedQuery(name = "Playerboxscore.findByIdplayer", query = "SELECT p FROM Playerboxscore p WHERE p.playerboxscorePK.idplayer = :idplayer"),
    @NamedQuery(name = "Playerboxscore.findByStarter", query = "SELECT p FROM Playerboxscore p WHERE p.starter = :starter"),
    @NamedQuery(name = "Playerboxscore.findByMinutes", query = "SELECT p FROM Playerboxscore p WHERE p.minutes = :minutes"),
    @NamedQuery(name = "Playerboxscore.findByFgm", query = "SELECT p FROM Playerboxscore p WHERE p.fgm = :fgm"),
    @NamedQuery(name = "Playerboxscore.findByFga", query = "SELECT p FROM Playerboxscore p WHERE p.fga = :fga"),
    @NamedQuery(name = "Playerboxscore.findByTpm", query = "SELECT p FROM Playerboxscore p WHERE p.tpm = :tpm"),
    @NamedQuery(name = "Playerboxscore.findByTpa", query = "SELECT p FROM Playerboxscore p WHERE p.tpa = :tpa"),
    @NamedQuery(name = "Playerboxscore.findByFtm", query = "SELECT p FROM Playerboxscore p WHERE p.ftm = :ftm"),
    @NamedQuery(name = "Playerboxscore.findByFta", query = "SELECT p FROM Playerboxscore p WHERE p.fta = :fta"),
    @NamedQuery(name = "Playerboxscore.findByOreb", query = "SELECT p FROM Playerboxscore p WHERE p.oreb = :oreb"),
    @NamedQuery(name = "Playerboxscore.findByDreb", query = "SELECT p FROM Playerboxscore p WHERE p.dreb = :dreb"),
    @NamedQuery(name = "Playerboxscore.findByReb", query = "SELECT p FROM Playerboxscore p WHERE p.reb = :reb"),
    @NamedQuery(name = "Playerboxscore.findByAst", query = "SELECT p FROM Playerboxscore p WHERE p.ast = :ast"),
    @NamedQuery(name = "Playerboxscore.findByStl", query = "SELECT p FROM Playerboxscore p WHERE p.stl = :stl"),
    @NamedQuery(name = "Playerboxscore.findByBlk", query = "SELECT p FROM Playerboxscore p WHERE p.blk = :blk"),
    @NamedQuery(name = "Playerboxscore.findByTov", query = "SELECT p FROM Playerboxscore p WHERE p.tov = :tov"),
    @NamedQuery(name = "Playerboxscore.findByPf", query = "SELECT p FROM Playerboxscore p WHERE p.pf = :pf"),
    @NamedQuery(name = "Playerboxscore.findByPam", query = "SELECT p FROM Playerboxscore p WHERE p.pam = :pam"),
    @NamedQuery(name = "Playerboxscore.findByPts", query = "SELECT p FROM Playerboxscore p WHERE p.pts = :pts"),
    @NamedQuery(name = "Playerboxscore.findByDnp", query = "SELECT p FROM Playerboxscore p WHERE p.dnp = :dnp")})
public class Playerboxscore implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlayerboxscorePK playerboxscorePK;
    @Column(name = "starter")
    private Boolean starter;
    @Column(name = "minutes")
    private Integer minutes;
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
    @Column(name = "pam")
    private Integer pam;
    @Column(name = "pts")
    private Integer pts;
    @Column(name = "dnp")
    private Boolean dnp;
    @JoinColumn(name = "gameid", referencedColumnName = "idgame", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Game game;
    @JoinColumn(name = "idplayer", referencedColumnName = "idplayer", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Player player;

    public Playerboxscore() {
    }

    public Playerboxscore(PlayerboxscorePK playerboxscorePK) {
        this.playerboxscorePK = playerboxscorePK;
    }

    public Playerboxscore(int gameid, int idplayer) {
        this.playerboxscorePK = new PlayerboxscorePK(gameid, idplayer);
    }

    public PlayerboxscorePK getPlayerboxscorePK() {
        return playerboxscorePK;
    }

    public void setPlayerboxscorePK(PlayerboxscorePK playerboxscorePK) {
        this.playerboxscorePK = playerboxscorePK;
    }

    public Boolean getStarter() {
        return starter;
    }

    public void setStarter(Boolean starter) {
        this.starter = starter;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
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

    public Integer getPam() {
        return pam;
    }

    public void setPam(Integer pam) {
        this.pam = pam;
    }

    public Integer getPts() {
        return pts;
    }

    public void setPts(Integer pts) {
        this.pts = pts;
    }

    public Boolean getDnp() {
        return dnp;
    }

    public void setDnp(Boolean dnp) {
        this.dnp = dnp;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playerboxscorePK != null ? playerboxscorePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playerboxscore)) {
            return false;
        }
        Playerboxscore other = (Playerboxscore) object;
        if ((this.playerboxscorePK == null && other.playerboxscorePK != null) || (this.playerboxscorePK != null && !this.playerboxscorePK.equals(other.playerboxscorePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Playerboxscore[ playerboxscorePK=" + playerboxscorePK + " ]";
    }
    
    public String toCsv(){
        return ""+starter+","+minutes+","+fgm+","+fga+","+tpm+","+tpa+","+ftm+","+fta+""
                + ","+oreb+","+dreb+","+reb+","+ast+","+stl+","+blk+","+tov+","+pf+""
                + ","+pam+","+pts+","+dnp+"";
    }
    
}
