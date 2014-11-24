package entity;

import entity.Playerboxscore;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-11-20T18:36:50")
@StaticMetamodel(Player.class)
public class Player_ { 

    public static volatile SingularAttribute<Player, Integer> idplayer;
    public static volatile SingularAttribute<Player, String> name;
    public static volatile CollectionAttribute<Player, Playerboxscore> playerboxscoreCollection;
    public static volatile SingularAttribute<Player, String> position;

}