/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spareCode;

import entity.Game;
import entity.Player;
import entity.Playerboxscore;
import entity.PlayerboxscorePK;
import entity.Team;
import java.sql.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ozielcarneiro
 */
public class EntityTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NBADataPU");
        EntityManager em = emf.createEntityManager();
        Team t1 = new Team("Team1");
        Team t2 = new Team("Team2");
        Game g = new Game();
        g.setIdgame(1);
        g.setAwayTeam(t2);
        g.setHomeTeam(t1);
        g.setGameDate(Date.valueOf("2014-11-11"));
        Player p = new Player();
        p.setName("X");
        p.setPosition("PG");
        try{
        
        em.getTransaction().begin();
        em.persist(t1);
        em.persist(t2);
        em.persist(g);
        em.persist(p);
        em.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(p.getIdplayer());
        Playerboxscore pbs = new Playerboxscore();
        pbs.setAst(0);
        pbs.setBlk(0);
        pbs.setDreb(0);
        pbs.setFga(0);
        pbs.setFgm(0);
        pbs.setFta(0);
        pbs.setFtm(0);
        pbs.setGame(g);
        pbs.setPlayerboxscorePK(new PlayerboxscorePK(g.getIdgame(), p.getIdplayer()));
        pbs.setMinutes(0);
        pbs.setOreb(0);
        pbs.setPam(0);
        pbs.setPf(0);
        pbs.setPlayer(p);
        pbs.setPts(0);
        pbs.setReb(0);
        pbs.setStarter(false);
        pbs.setStl(0);
        pbs.setTov(0);
        pbs.setTpa(0);
        pbs.setTpm(0);
        pbs.setDnp(true);
        try{
        em.getTransaction().begin();
        em.persist(pbs);
        em.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(p.getIdplayer());
    }
}
