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
import entity.Team;
import entity.Playerboxscore;
import java.util.ArrayList;
import java.util.Collection;
import entity.Boxscore;
import entity.Game;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author ozielcarneiro
 */
public class GameJpaController implements Serializable {

    public GameJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Game game) throws PreexistingEntityException, Exception {
        if (game.getPlayerboxscoreCollection() == null) {
            game.setPlayerboxscoreCollection(new ArrayList<Playerboxscore>());
        }
        if (game.getBoxscoreCollection() == null) {
            game.setBoxscoreCollection(new ArrayList<Boxscore>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Team awayTeam = game.getAwayTeam();
            if (awayTeam != null) {
                awayTeam = em.getReference(awayTeam.getClass(), awayTeam.getName());
                game.setAwayTeam(awayTeam);
            }
            Team homeTeam = game.getHomeTeam();
            if (homeTeam != null) {
                homeTeam = em.getReference(homeTeam.getClass(), homeTeam.getName());
                game.setHomeTeam(homeTeam);
            }
            Collection<Playerboxscore> attachedPlayerboxscoreCollection = new ArrayList<Playerboxscore>();
            for (Playerboxscore playerboxscoreCollectionPlayerboxscoreToAttach : game.getPlayerboxscoreCollection()) {
                playerboxscoreCollectionPlayerboxscoreToAttach = em.getReference(playerboxscoreCollectionPlayerboxscoreToAttach.getClass(), playerboxscoreCollectionPlayerboxscoreToAttach.getPlayerboxscorePK());
                attachedPlayerboxscoreCollection.add(playerboxscoreCollectionPlayerboxscoreToAttach);
            }
            game.setPlayerboxscoreCollection(attachedPlayerboxscoreCollection);
            Collection<Boxscore> attachedBoxscoreCollection = new ArrayList<Boxscore>();
            for (Boxscore boxscoreCollectionBoxscoreToAttach : game.getBoxscoreCollection()) {
                boxscoreCollectionBoxscoreToAttach = em.getReference(boxscoreCollectionBoxscoreToAttach.getClass(), boxscoreCollectionBoxscoreToAttach.getBoxscorePK());
                attachedBoxscoreCollection.add(boxscoreCollectionBoxscoreToAttach);
            }
            game.setBoxscoreCollection(attachedBoxscoreCollection);
            em.persist(game);
            if (awayTeam != null) {
                awayTeam.getGameCollection().add(game);
                awayTeam = em.merge(awayTeam);
            }
            if (homeTeam != null) {
                homeTeam.getGameCollection().add(game);
                homeTeam = em.merge(homeTeam);
            }
            for (Playerboxscore playerboxscoreCollectionPlayerboxscore : game.getPlayerboxscoreCollection()) {
                Game oldGameOfPlayerboxscoreCollectionPlayerboxscore = playerboxscoreCollectionPlayerboxscore.getGame();
                playerboxscoreCollectionPlayerboxscore.setGame(game);
                playerboxscoreCollectionPlayerboxscore = em.merge(playerboxscoreCollectionPlayerboxscore);
                if (oldGameOfPlayerboxscoreCollectionPlayerboxscore != null) {
                    oldGameOfPlayerboxscoreCollectionPlayerboxscore.getPlayerboxscoreCollection().remove(playerboxscoreCollectionPlayerboxscore);
                    oldGameOfPlayerboxscoreCollectionPlayerboxscore = em.merge(oldGameOfPlayerboxscoreCollectionPlayerboxscore);
                }
            }
            for (Boxscore boxscoreCollectionBoxscore : game.getBoxscoreCollection()) {
                Game oldGameOfBoxscoreCollectionBoxscore = boxscoreCollectionBoxscore.getGame();
                boxscoreCollectionBoxscore.setGame(game);
                boxscoreCollectionBoxscore = em.merge(boxscoreCollectionBoxscore);
                if (oldGameOfBoxscoreCollectionBoxscore != null) {
                    oldGameOfBoxscoreCollectionBoxscore.getBoxscoreCollection().remove(boxscoreCollectionBoxscore);
                    oldGameOfBoxscoreCollectionBoxscore = em.merge(oldGameOfBoxscoreCollectionBoxscore);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGame(game.getIdgame()) != null) {
                throw new PreexistingEntityException("Game " + game + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Game game) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Game persistentGame = em.find(Game.class, game.getIdgame());
            Team awayTeamOld = persistentGame.getAwayTeam();
            Team awayTeamNew = game.getAwayTeam();
            Team homeTeamOld = persistentGame.getHomeTeam();
            Team homeTeamNew = game.getHomeTeam();
            Collection<Playerboxscore> playerboxscoreCollectionOld = persistentGame.getPlayerboxscoreCollection();
            Collection<Playerboxscore> playerboxscoreCollectionNew = game.getPlayerboxscoreCollection();
            Collection<Boxscore> boxscoreCollectionOld = persistentGame.getBoxscoreCollection();
            Collection<Boxscore> boxscoreCollectionNew = game.getBoxscoreCollection();
            List<String> illegalOrphanMessages = null;
            for (Playerboxscore playerboxscoreCollectionOldPlayerboxscore : playerboxscoreCollectionOld) {
                if (!playerboxscoreCollectionNew.contains(playerboxscoreCollectionOldPlayerboxscore)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Playerboxscore " + playerboxscoreCollectionOldPlayerboxscore + " since its game field is not nullable.");
                }
            }
            for (Boxscore boxscoreCollectionOldBoxscore : boxscoreCollectionOld) {
                if (!boxscoreCollectionNew.contains(boxscoreCollectionOldBoxscore)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Boxscore " + boxscoreCollectionOldBoxscore + " since its game field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (awayTeamNew != null) {
                awayTeamNew = em.getReference(awayTeamNew.getClass(), awayTeamNew.getName());
                game.setAwayTeam(awayTeamNew);
            }
            if (homeTeamNew != null) {
                homeTeamNew = em.getReference(homeTeamNew.getClass(), homeTeamNew.getName());
                game.setHomeTeam(homeTeamNew);
            }
            Collection<Playerboxscore> attachedPlayerboxscoreCollectionNew = new ArrayList<Playerboxscore>();
            for (Playerboxscore playerboxscoreCollectionNewPlayerboxscoreToAttach : playerboxscoreCollectionNew) {
                playerboxscoreCollectionNewPlayerboxscoreToAttach = em.getReference(playerboxscoreCollectionNewPlayerboxscoreToAttach.getClass(), playerboxscoreCollectionNewPlayerboxscoreToAttach.getPlayerboxscorePK());
                attachedPlayerboxscoreCollectionNew.add(playerboxscoreCollectionNewPlayerboxscoreToAttach);
            }
            playerboxscoreCollectionNew = attachedPlayerboxscoreCollectionNew;
            game.setPlayerboxscoreCollection(playerboxscoreCollectionNew);
            Collection<Boxscore> attachedBoxscoreCollectionNew = new ArrayList<Boxscore>();
            for (Boxscore boxscoreCollectionNewBoxscoreToAttach : boxscoreCollectionNew) {
                boxscoreCollectionNewBoxscoreToAttach = em.getReference(boxscoreCollectionNewBoxscoreToAttach.getClass(), boxscoreCollectionNewBoxscoreToAttach.getBoxscorePK());
                attachedBoxscoreCollectionNew.add(boxscoreCollectionNewBoxscoreToAttach);
            }
            boxscoreCollectionNew = attachedBoxscoreCollectionNew;
            game.setBoxscoreCollection(boxscoreCollectionNew);
            game = em.merge(game);
            if (awayTeamOld != null && !awayTeamOld.equals(awayTeamNew)) {
                awayTeamOld.getGameCollection().remove(game);
                awayTeamOld = em.merge(awayTeamOld);
            }
            if (awayTeamNew != null && !awayTeamNew.equals(awayTeamOld)) {
                awayTeamNew.getGameCollection().add(game);
                awayTeamNew = em.merge(awayTeamNew);
            }
            if (homeTeamOld != null && !homeTeamOld.equals(homeTeamNew)) {
                homeTeamOld.getGameCollection().remove(game);
                homeTeamOld = em.merge(homeTeamOld);
            }
            if (homeTeamNew != null && !homeTeamNew.equals(homeTeamOld)) {
                homeTeamNew.getGameCollection().add(game);
                homeTeamNew = em.merge(homeTeamNew);
            }
            for (Playerboxscore playerboxscoreCollectionNewPlayerboxscore : playerboxscoreCollectionNew) {
                if (!playerboxscoreCollectionOld.contains(playerboxscoreCollectionNewPlayerboxscore)) {
                    Game oldGameOfPlayerboxscoreCollectionNewPlayerboxscore = playerboxscoreCollectionNewPlayerboxscore.getGame();
                    playerboxscoreCollectionNewPlayerboxscore.setGame(game);
                    playerboxscoreCollectionNewPlayerboxscore = em.merge(playerboxscoreCollectionNewPlayerboxscore);
                    if (oldGameOfPlayerboxscoreCollectionNewPlayerboxscore != null && !oldGameOfPlayerboxscoreCollectionNewPlayerboxscore.equals(game)) {
                        oldGameOfPlayerboxscoreCollectionNewPlayerboxscore.getPlayerboxscoreCollection().remove(playerboxscoreCollectionNewPlayerboxscore);
                        oldGameOfPlayerboxscoreCollectionNewPlayerboxscore = em.merge(oldGameOfPlayerboxscoreCollectionNewPlayerboxscore);
                    }
                }
            }
            for (Boxscore boxscoreCollectionNewBoxscore : boxscoreCollectionNew) {
                if (!boxscoreCollectionOld.contains(boxscoreCollectionNewBoxscore)) {
                    Game oldGameOfBoxscoreCollectionNewBoxscore = boxscoreCollectionNewBoxscore.getGame();
                    boxscoreCollectionNewBoxscore.setGame(game);
                    boxscoreCollectionNewBoxscore = em.merge(boxscoreCollectionNewBoxscore);
                    if (oldGameOfBoxscoreCollectionNewBoxscore != null && !oldGameOfBoxscoreCollectionNewBoxscore.equals(game)) {
                        oldGameOfBoxscoreCollectionNewBoxscore.getBoxscoreCollection().remove(boxscoreCollectionNewBoxscore);
                        oldGameOfBoxscoreCollectionNewBoxscore = em.merge(oldGameOfBoxscoreCollectionNewBoxscore);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = game.getIdgame();
                if (findGame(id) == null) {
                    throw new NonexistentEntityException("The game with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Game game;
            try {
                game = em.getReference(Game.class, id);
                game.getIdgame();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The game with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Playerboxscore> playerboxscoreCollectionOrphanCheck = game.getPlayerboxscoreCollection();
            for (Playerboxscore playerboxscoreCollectionOrphanCheckPlayerboxscore : playerboxscoreCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Game (" + game + ") cannot be destroyed since the Playerboxscore " + playerboxscoreCollectionOrphanCheckPlayerboxscore + " in its playerboxscoreCollection field has a non-nullable game field.");
            }
            Collection<Boxscore> boxscoreCollectionOrphanCheck = game.getBoxscoreCollection();
            for (Boxscore boxscoreCollectionOrphanCheckBoxscore : boxscoreCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Game (" + game + ") cannot be destroyed since the Boxscore " + boxscoreCollectionOrphanCheckBoxscore + " in its boxscoreCollection field has a non-nullable game field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Team awayTeam = game.getAwayTeam();
            if (awayTeam != null) {
                awayTeam.getGameCollection().remove(game);
                awayTeam = em.merge(awayTeam);
            }
            Team homeTeam = game.getHomeTeam();
            if (homeTeam != null) {
                homeTeam.getGameCollection().remove(game);
                homeTeam = em.merge(homeTeam);
            }
            em.remove(game);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Game> findGameEntities() {
        return findGameEntities(true, -1, -1);
    }

    public List<Game> findGameEntities(int maxResults, int firstResult) {
        return findGameEntities(false, maxResults, firstResult);
    }

    private List<Game> findGameEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Game.class));
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

    public Game findGame(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Game.class, id);
        } finally {
            em.close();
        }
    }

    public int getGameCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Game> rt = cq.from(Game.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
