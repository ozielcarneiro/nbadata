/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityControllers;

import dataStoring.exceptions.NonexistentEntityException;
import dataStoring.exceptions.PreexistingEntityException;
import entity.Boxscore;
import entity.BoxscorePK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Game;
import entity.Team;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ozielcarneiro
 */
public class BoxscoreJpaController implements Serializable {

    public BoxscoreJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Boxscore boxscore) throws PreexistingEntityException, Exception {
        if (boxscore.getBoxscorePK() == null) {
            boxscore.setBoxscorePK(new BoxscorePK());
        }
        boxscore.getBoxscorePK().setGameid(boxscore.getGame().getIdgame());
        boxscore.getBoxscorePK().setTeamname(boxscore.getTeam().getName());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Game game = boxscore.getGame();
            if (game != null) {
                game = em.getReference(game.getClass(), game.getIdgame());
                boxscore.setGame(game);
            }
            Team team = boxscore.getTeam();
            if (team != null) {
                team = em.getReference(team.getClass(), team.getName());
                boxscore.setTeam(team);
            }
            em.persist(boxscore);
            if (game != null) {
                game.getBoxscoreCollection().add(boxscore);
                game = em.merge(game);
            }
            if (team != null) {
                team.getBoxscoreCollection().add(boxscore);
                team = em.merge(team);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBoxscore(boxscore.getBoxscorePK()) != null) {
                throw new PreexistingEntityException("Boxscore " + boxscore + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Boxscore boxscore) throws NonexistentEntityException, Exception {
        boxscore.getBoxscorePK().setGameid(boxscore.getGame().getIdgame());
        boxscore.getBoxscorePK().setTeamname(boxscore.getTeam().getName());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Boxscore persistentBoxscore = em.find(Boxscore.class, boxscore.getBoxscorePK());
            Game gameOld = persistentBoxscore.getGame();
            Game gameNew = boxscore.getGame();
            Team teamOld = persistentBoxscore.getTeam();
            Team teamNew = boxscore.getTeam();
            if (gameNew != null) {
                gameNew = em.getReference(gameNew.getClass(), gameNew.getIdgame());
                boxscore.setGame(gameNew);
            }
            if (teamNew != null) {
                teamNew = em.getReference(teamNew.getClass(), teamNew.getName());
                boxscore.setTeam(teamNew);
            }
            boxscore = em.merge(boxscore);
            if (gameOld != null && !gameOld.equals(gameNew)) {
                gameOld.getBoxscoreCollection().remove(boxscore);
                gameOld = em.merge(gameOld);
            }
            if (gameNew != null && !gameNew.equals(gameOld)) {
                gameNew.getBoxscoreCollection().add(boxscore);
                gameNew = em.merge(gameNew);
            }
            if (teamOld != null && !teamOld.equals(teamNew)) {
                teamOld.getBoxscoreCollection().remove(boxscore);
                teamOld = em.merge(teamOld);
            }
            if (teamNew != null && !teamNew.equals(teamOld)) {
                teamNew.getBoxscoreCollection().add(boxscore);
                teamNew = em.merge(teamNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BoxscorePK id = boxscore.getBoxscorePK();
                if (findBoxscore(id) == null) {
                    throw new NonexistentEntityException("The boxscore with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BoxscorePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Boxscore boxscore;
            try {
                boxscore = em.getReference(Boxscore.class, id);
                boxscore.getBoxscorePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The boxscore with id " + id + " no longer exists.", enfe);
            }
            Game game = boxscore.getGame();
            if (game != null) {
                game.getBoxscoreCollection().remove(boxscore);
                game = em.merge(game);
            }
            Team team = boxscore.getTeam();
            if (team != null) {
                team.getBoxscoreCollection().remove(boxscore);
                team = em.merge(team);
            }
            em.remove(boxscore);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Boxscore> findBoxscoreEntities() {
        return findBoxscoreEntities(true, -1, -1);
    }

    public List<Boxscore> findBoxscoreEntities(int maxResults, int firstResult) {
        return findBoxscoreEntities(false, maxResults, firstResult);
    }

    private List<Boxscore> findBoxscoreEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Boxscore.class));
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

    public Boxscore findBoxscore(BoxscorePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Boxscore.class, id);
        } finally {
            em.close();
        }
    }

    public int getBoxscoreCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Boxscore> rt = cq.from(Boxscore.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
