/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityControllers;

import dataStoring.exceptions.IllegalOrphanException;
import dataStoring.exceptions.NonexistentEntityException;
import dataStoring.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Game;
import java.util.ArrayList;
import java.util.Collection;
import entity.Boxscore;
import entity.Team;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ozielcarneiro
 */
public class TeamJpaController implements Serializable {

    public TeamJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Team team) throws PreexistingEntityException, Exception {
        if (team.getGameCollection() == null) {
            team.setGameCollection(new ArrayList<Game>());
        }
        if (team.getGameCollection1() == null) {
            team.setGameCollection1(new ArrayList<Game>());
        }
        if (team.getBoxscoreCollection() == null) {
            team.setBoxscoreCollection(new ArrayList<Boxscore>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Game> attachedGameCollection = new ArrayList<Game>();
            for (Game gameCollectionGameToAttach : team.getGameCollection()) {
                gameCollectionGameToAttach = em.getReference(gameCollectionGameToAttach.getClass(), gameCollectionGameToAttach.getIdgame());
                attachedGameCollection.add(gameCollectionGameToAttach);
            }
            team.setGameCollection(attachedGameCollection);
            Collection<Game> attachedGameCollection1 = new ArrayList<Game>();
            for (Game gameCollection1GameToAttach : team.getGameCollection1()) {
                gameCollection1GameToAttach = em.getReference(gameCollection1GameToAttach.getClass(), gameCollection1GameToAttach.getIdgame());
                attachedGameCollection1.add(gameCollection1GameToAttach);
            }
            team.setGameCollection1(attachedGameCollection1);
            Collection<Boxscore> attachedBoxscoreCollection = new ArrayList<Boxscore>();
            for (Boxscore boxscoreCollectionBoxscoreToAttach : team.getBoxscoreCollection()) {
                boxscoreCollectionBoxscoreToAttach = em.getReference(boxscoreCollectionBoxscoreToAttach.getClass(), boxscoreCollectionBoxscoreToAttach.getBoxscorePK());
                attachedBoxscoreCollection.add(boxscoreCollectionBoxscoreToAttach);
            }
            team.setBoxscoreCollection(attachedBoxscoreCollection);
            em.persist(team);
            for (Game gameCollectionGame : team.getGameCollection()) {
                Team oldAwayTeamOfGameCollectionGame = gameCollectionGame.getAwayTeam();
                gameCollectionGame.setAwayTeam(team);
                gameCollectionGame = em.merge(gameCollectionGame);
                if (oldAwayTeamOfGameCollectionGame != null) {
                    oldAwayTeamOfGameCollectionGame.getGameCollection().remove(gameCollectionGame);
                    oldAwayTeamOfGameCollectionGame = em.merge(oldAwayTeamOfGameCollectionGame);
                }
            }
            for (Game gameCollection1Game : team.getGameCollection1()) {
                Team oldHomeTeamOfGameCollection1Game = gameCollection1Game.getHomeTeam();
                gameCollection1Game.setHomeTeam(team);
                gameCollection1Game = em.merge(gameCollection1Game);
                if (oldHomeTeamOfGameCollection1Game != null) {
                    oldHomeTeamOfGameCollection1Game.getGameCollection1().remove(gameCollection1Game);
                    oldHomeTeamOfGameCollection1Game = em.merge(oldHomeTeamOfGameCollection1Game);
                }
            }
            for (Boxscore boxscoreCollectionBoxscore : team.getBoxscoreCollection()) {
                Team oldTeamOfBoxscoreCollectionBoxscore = boxscoreCollectionBoxscore.getTeam();
                boxscoreCollectionBoxscore.setTeam(team);
                boxscoreCollectionBoxscore = em.merge(boxscoreCollectionBoxscore);
                if (oldTeamOfBoxscoreCollectionBoxscore != null) {
                    oldTeamOfBoxscoreCollectionBoxscore.getBoxscoreCollection().remove(boxscoreCollectionBoxscore);
                    oldTeamOfBoxscoreCollectionBoxscore = em.merge(oldTeamOfBoxscoreCollectionBoxscore);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTeam(team.getName()) != null) {
                throw new PreexistingEntityException("Team " + team + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Team team) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Team persistentTeam = em.find(Team.class, team.getName());
            Collection<Game> gameCollectionOld = persistentTeam.getGameCollection();
            Collection<Game> gameCollectionNew = team.getGameCollection();
            Collection<Game> gameCollection1Old = persistentTeam.getGameCollection1();
            Collection<Game> gameCollection1New = team.getGameCollection1();
            Collection<Boxscore> boxscoreCollectionOld = persistentTeam.getBoxscoreCollection();
            Collection<Boxscore> boxscoreCollectionNew = team.getBoxscoreCollection();
            List<String> illegalOrphanMessages = null;
            for (Game gameCollectionOldGame : gameCollectionOld) {
                if (!gameCollectionNew.contains(gameCollectionOldGame)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Game " + gameCollectionOldGame + " since its awayTeam field is not nullable.");
                }
            }
            for (Game gameCollection1OldGame : gameCollection1Old) {
                if (!gameCollection1New.contains(gameCollection1OldGame)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Game " + gameCollection1OldGame + " since its homeTeam field is not nullable.");
                }
            }
            for (Boxscore boxscoreCollectionOldBoxscore : boxscoreCollectionOld) {
                if (!boxscoreCollectionNew.contains(boxscoreCollectionOldBoxscore)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Boxscore " + boxscoreCollectionOldBoxscore + " since its team field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Game> attachedGameCollectionNew = new ArrayList<Game>();
            for (Game gameCollectionNewGameToAttach : gameCollectionNew) {
                gameCollectionNewGameToAttach = em.getReference(gameCollectionNewGameToAttach.getClass(), gameCollectionNewGameToAttach.getIdgame());
                attachedGameCollectionNew.add(gameCollectionNewGameToAttach);
            }
            gameCollectionNew = attachedGameCollectionNew;
            team.setGameCollection(gameCollectionNew);
            Collection<Game> attachedGameCollection1New = new ArrayList<Game>();
            for (Game gameCollection1NewGameToAttach : gameCollection1New) {
                gameCollection1NewGameToAttach = em.getReference(gameCollection1NewGameToAttach.getClass(), gameCollection1NewGameToAttach.getIdgame());
                attachedGameCollection1New.add(gameCollection1NewGameToAttach);
            }
            gameCollection1New = attachedGameCollection1New;
            team.setGameCollection1(gameCollection1New);
            Collection<Boxscore> attachedBoxscoreCollectionNew = new ArrayList<Boxscore>();
            for (Boxscore boxscoreCollectionNewBoxscoreToAttach : boxscoreCollectionNew) {
                boxscoreCollectionNewBoxscoreToAttach = em.getReference(boxscoreCollectionNewBoxscoreToAttach.getClass(), boxscoreCollectionNewBoxscoreToAttach.getBoxscorePK());
                attachedBoxscoreCollectionNew.add(boxscoreCollectionNewBoxscoreToAttach);
            }
            boxscoreCollectionNew = attachedBoxscoreCollectionNew;
            team.setBoxscoreCollection(boxscoreCollectionNew);
            team = em.merge(team);
            for (Game gameCollectionNewGame : gameCollectionNew) {
                if (!gameCollectionOld.contains(gameCollectionNewGame)) {
                    Team oldAwayTeamOfGameCollectionNewGame = gameCollectionNewGame.getAwayTeam();
                    gameCollectionNewGame.setAwayTeam(team);
                    gameCollectionNewGame = em.merge(gameCollectionNewGame);
                    if (oldAwayTeamOfGameCollectionNewGame != null && !oldAwayTeamOfGameCollectionNewGame.equals(team)) {
                        oldAwayTeamOfGameCollectionNewGame.getGameCollection().remove(gameCollectionNewGame);
                        oldAwayTeamOfGameCollectionNewGame = em.merge(oldAwayTeamOfGameCollectionNewGame);
                    }
                }
            }
            for (Game gameCollection1NewGame : gameCollection1New) {
                if (!gameCollection1Old.contains(gameCollection1NewGame)) {
                    Team oldHomeTeamOfGameCollection1NewGame = gameCollection1NewGame.getHomeTeam();
                    gameCollection1NewGame.setHomeTeam(team);
                    gameCollection1NewGame = em.merge(gameCollection1NewGame);
                    if (oldHomeTeamOfGameCollection1NewGame != null && !oldHomeTeamOfGameCollection1NewGame.equals(team)) {
                        oldHomeTeamOfGameCollection1NewGame.getGameCollection1().remove(gameCollection1NewGame);
                        oldHomeTeamOfGameCollection1NewGame = em.merge(oldHomeTeamOfGameCollection1NewGame);
                    }
                }
            }
            for (Boxscore boxscoreCollectionNewBoxscore : boxscoreCollectionNew) {
                if (!boxscoreCollectionOld.contains(boxscoreCollectionNewBoxscore)) {
                    Team oldTeamOfBoxscoreCollectionNewBoxscore = boxscoreCollectionNewBoxscore.getTeam();
                    boxscoreCollectionNewBoxscore.setTeam(team);
                    boxscoreCollectionNewBoxscore = em.merge(boxscoreCollectionNewBoxscore);
                    if (oldTeamOfBoxscoreCollectionNewBoxscore != null && !oldTeamOfBoxscoreCollectionNewBoxscore.equals(team)) {
                        oldTeamOfBoxscoreCollectionNewBoxscore.getBoxscoreCollection().remove(boxscoreCollectionNewBoxscore);
                        oldTeamOfBoxscoreCollectionNewBoxscore = em.merge(oldTeamOfBoxscoreCollectionNewBoxscore);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = team.getName();
                if (findTeam(id) == null) {
                    throw new NonexistentEntityException("The team with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Team team;
            try {
                team = em.getReference(Team.class, id);
                team.getName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The team with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Game> gameCollectionOrphanCheck = team.getGameCollection();
            for (Game gameCollectionOrphanCheckGame : gameCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Team (" + team + ") cannot be destroyed since the Game " + gameCollectionOrphanCheckGame + " in its gameCollection field has a non-nullable awayTeam field.");
            }
            Collection<Game> gameCollection1OrphanCheck = team.getGameCollection1();
            for (Game gameCollection1OrphanCheckGame : gameCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Team (" + team + ") cannot be destroyed since the Game " + gameCollection1OrphanCheckGame + " in its gameCollection1 field has a non-nullable homeTeam field.");
            }
            Collection<Boxscore> boxscoreCollectionOrphanCheck = team.getBoxscoreCollection();
            for (Boxscore boxscoreCollectionOrphanCheckBoxscore : boxscoreCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Team (" + team + ") cannot be destroyed since the Boxscore " + boxscoreCollectionOrphanCheckBoxscore + " in its boxscoreCollection field has a non-nullable team field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(team);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Team> findTeamEntities() {
        return findTeamEntities(true, -1, -1);
    }

    public List<Team> findTeamEntities(int maxResults, int firstResult) {
        return findTeamEntities(false, maxResults, firstResult);
    }

    private List<Team> findTeamEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Team.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Team findTeam(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Team.class, id);
        } finally {
            em.close();
        }
    }

    public int getTeamCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Team> rt = cq.from(Team.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
