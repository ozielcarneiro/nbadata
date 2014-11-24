package entity;

import entity.BoxscorePK;
import entity.Game;
import entity.Team;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-11-20T18:36:50")
@StaticMetamodel(Boxscore.class)
public class Boxscore_ { 

    public static volatile SingularAttribute<Boxscore, Integer> blk;
    public static volatile SingularAttribute<Boxscore, Integer> fga;
    public static volatile SingularAttribute<Boxscore, Game> game;
    public static volatile SingularAttribute<Boxscore, Integer> ast;
    public static volatile SingularAttribute<Boxscore, Integer> tpa;
    public static volatile SingularAttribute<Boxscore, Integer> dreb;
    public static volatile SingularAttribute<Boxscore, BoxscorePK> boxscorePK;
    public static volatile SingularAttribute<Boxscore, Integer> stl;
    public static volatile SingularAttribute<Boxscore, Team> team;
    public static volatile SingularAttribute<Boxscore, Integer> fgm;
    public static volatile SingularAttribute<Boxscore, Integer> reb;
    public static volatile SingularAttribute<Boxscore, Integer> pts;
    public static volatile SingularAttribute<Boxscore, Boolean> home;
    public static volatile SingularAttribute<Boxscore, Integer> tpm;
    public static volatile SingularAttribute<Boxscore, Integer> fta;
    public static volatile SingularAttribute<Boxscore, Integer> oreb;
    public static volatile SingularAttribute<Boxscore, Integer> pf;
    public static volatile SingularAttribute<Boxscore, Integer> tov;
    public static volatile SingularAttribute<Boxscore, Boolean> win;
    public static volatile SingularAttribute<Boxscore, Integer> ftm;

}