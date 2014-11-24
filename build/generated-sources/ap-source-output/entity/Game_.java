package entity;

import entity.Boxscore;
import entity.Playerboxscore;
import entity.Team;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-11-20T18:36:50")
@StaticMetamodel(Game.class)
public class Game_ { 

    public static volatile SingularAttribute<Game, Integer> idgame;
    public static volatile SingularAttribute<Game, Date> gameDate;
    public static volatile SingularAttribute<Game, Team> awayTeam;
    public static volatile CollectionAttribute<Game, Boxscore> boxscoreCollection;
    public static volatile CollectionAttribute<Game, Playerboxscore> playerboxscoreCollection;
    public static volatile SingularAttribute<Game, Team> homeTeam;

}