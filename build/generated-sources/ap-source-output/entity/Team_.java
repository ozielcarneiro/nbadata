package entity;

import entity.Boxscore;
import entity.Game;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-11-20T18:36:50")
@StaticMetamodel(Team.class)
public class Team_ { 

    public static volatile CollectionAttribute<Team, Boxscore> boxscoreCollection;
    public static volatile CollectionAttribute<Team, Game> gameCollection;
    public static volatile SingularAttribute<Team, String> name;
    public static volatile CollectionAttribute<Team, Game> gameCollection1;

}