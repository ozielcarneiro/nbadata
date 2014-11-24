/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityControllers;

import dataStoring.exceptions.NonexistentEntityException;
import dataStoring.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Game;
import entity.Player;
import entity.Playerboxscore;
import entity.PlayerboxscorePK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ozielcarneiro
 */
public class PlayerboxscoreJpaController implements Serializable {

    public PlayerboxscoreJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Playerboxscore playerboxscore) throws PreexistingEntityException, Exception {
        if (playerboxscore.getPlayerboxscorePK() == null) {
            playerboxscore.setPlayerboxscorePK(new PlayerboxscorePK());
        }
        playerboxscore.getPlayerboxscorePK().setGameid(playerboxscore.getGame().getIdgame());
        playerboxscore.getPlayerboxscorePK().setIdplayer(playerboxscore.getPlayer().getIdplayer());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Game game = playerboxscore.getGame();
            if (game != null) {
                game = em.getReference(game.getClass(), game.getIdgame());
                playerboxscore.setGame(game);
            }
            Player player = playerboxscore.getPlayer();
            if (player != null) {
                player = em.getReference(player.getClass(), player.getIdplayer());
                playerboxscore.setPlayer(player);
            }
            em.persist(playerboxscore);
            if (game != null) {
                game.getPlayerboxscoreCollection().add(playerboxscore);
                game = em.merge(game);
            }
            if (player != null) {
                player.getPlayerboxscoreCollection().add(playerboxscore);
                player = em.merge(player);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlayerboxscore(playerboxscore.getPlayerboxscorePK()) != null) {
                throw new PreexistingEntityException("Playerboxscore " + playerboxscore + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Playerboxscore playerboxscore) throws NonexistentEntityException, Exception {
        playerboxscore.getPlayerboxscorePK().setGameid(playerboxscore.getGame().getIdgame());
        playerboxscore.getPlayerboxscorePK().setIdplayer(playerboxscore.getPlayer().getIdplayer());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Playerboxscore persistentPlayerboxscore = em.find(Playerboxscore.class, playerboxscore.getPlayerboxscorePK());
            Game gameOld = persistentPlayerboxscore.getGame();
            Game gameNew = playerboxscore.getGame();
            Player playerOld = persistentPlayerboxscore.getPlayer();
            Player playerNew = playerboxscore.getPlayer();
            if (gameNew != null) {
                gameNew = em.getReference(gameNew.getClass(), gameNew.getIdgame());
                playerboxscore.setGame(gameNew);
            }
            if (playerNew != null) {
                playerNew = em.getReference(playerNew.getClass(), playerNew.getIdplayer());
                playerboxscore.setPlayer(playerNew);
            }
            playerboxscore = em.merge(playerboxscore);
            if (gameOld != null && !gameOld.equals(gameNew)) {
                gameOld.getPlayerboxscoreCollection().remove(playerboxscore);
                gameOld = em.merge(gameOld);
            }
            if (gameNew != null && !gameNew.equals(gameOld)) {
                gameNew.getPlayerboxscoreCollection().add(playerboxscore);
                gameNew = em.merge(gameNew);
            }
            if (playerOld != null && !playerOld.equals(playerNew)) {
                playerOld.getPlayerboxscoreCollection().remove(playerboxscore);
                playerOld = em.merge(playerOld);
            }
            if (playerNew != null && !playerNew.equals(playerOld)) {
                playerNew.getPlayerboxscoreCollection().add(playerboxscore);
                playerNew = em.merge(playerNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PlayerboxscorePK id = playerboxscore.getPlayerboxscorePK();
                if (findPlayerboxscore(id) == null) {
                    throw new NonexistentEntityException("The playerboxscore with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PlayerboxscorePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Playerboxscore playerboxscore;
            try {
                playerboxscore = em.getReference(Playerboxscore.class, id);
                playerboxscore.getPlayerboxscorePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The playerboxscore with id " + id + " no longer exists.", enfe);
            }
            Game game = playerboxscore.getGame();
            if (game != null) {
                game.getPlayerboxscoreCollection().remove(playerboxscore);
                game = em.merge(game);
            }
            Player player = playerboxscore.getPlayer();
            if (player != null) {
                player.getPlayerboxscoreCollection().remove(playerboxscore);
                player = em.merge(player);
            }
            em.remove(playerboxscore);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Playerboxscore> findPlayerboxscoreEntities() {
        return findPlayerboxscoreEntities(true, -1, -1);
    }

    public List<Playerboxscore> findPlayerboxscoreEntities(int maxResults, int firstResult) {
        return findPlayerboxscoreEntities(false, maxResults, firstResult);
    }

    private List<Playerboxscore> findPlayerboxscoreEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Playerboxscore.class));
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

    public Playerboxscore findPlayerboxscore(PlayerboxscorePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Playerboxscore.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlayerboxscoreCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Playerboxscore> rt = cq.from(Playerboxscore.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
