package entity;

import entity.Game;
import entity.Player;
import entity.PlayerboxscorePK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-11-20T18:36:50")
@StaticMetamodel(Playerboxscore.class)
public class Playerboxscore_ { 

    public static volatile SingularAttribute<Playerboxscore, Integer> blk;
    public static volatile SingularAttribute<Playerboxscore, Integer> fga;
    public static volatile SingularAttribute<Playerboxscore, Game> game;
    public static volatile SingularAttribute<Playerboxscore, Integer> ast;
    public static volatile SingularAttribute<Playerboxscore, Boolean> starter;
    public static volatile SingularAttribute<Playerboxscore, Integer> tpa;
    public static volatile SingularAttribute<Playerboxscore, Integer> dreb;
    public static volatile SingularAttribute<Playerboxscore, Integer> minutes;
    public static volatile SingularAttribute<Playerboxscore, Boolean> dnp;
    public static volatile SingularAttribute<Playerboxscore, PlayerboxscorePK> playerboxscorePK;
    public static volatile SingularAttribute<Playerboxscore, Integer> stl;
    public static volatile SingularAttribute<Playerboxscore, Integer> fgm;
    public static volatile SingularAttribute<Playerboxscore, Integer> reb;
    public static volatile SingularAttribute<Playerboxscore, Integer> pts;
    public static volatile SingularAttribute<Playerboxscore, Integer> tpm;
    public static volatile SingularAttribute<Playerboxscore, Integer> fta;
    public static volatile SingularAttribute<Playerboxscore, Integer> oreb;
    public static volatile SingularAttribute<Playerboxscore, Integer> pf;
    public static volatile SingularAttribute<Playerboxscore, Integer> tov;
    public static volatile SingularAttribute<Playerboxscore, Integer> pam;
    public static volatile SingularAttribute<Playerboxscore, Integer> ftm;
    public static volatile SingularAttribute<Playerboxscore, Player> player;

}