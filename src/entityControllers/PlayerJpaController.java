/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityControllers;

import dataStoring.exceptions.IllegalOrphanException;
import dataStoring.exceptions.NonexistentEntityException;
import entity.Player;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Playerboxscore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;

/**
 *
 * @author ozielcarneiro
 */
public class PlayerJpaController implements Serializable {

    public PlayerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Player player) {
        if (player.getPlayerboxscoreCollection() == null) {
            player.setPlayerboxscoreCollection(new ArrayList<Playerboxscore>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Playerboxscore> attachedPlayerboxscoreCollection = new ArrayList<Playerboxscore>();
            for (Playerboxscore playerboxscoreCollectionPlayerboxscoreToAttach : player.getPlayerboxscoreCollection()) {
                playerboxscoreCollectionPlayerboxscoreToAttach = em.getReference(playerboxscoreCollectionPlayerboxscoreToAttach.getClass(), playerboxscoreCollectionPlayerboxscoreToAttach.getPlayerboxscorePK());
                attachedPlayerboxscoreCollection.add(playerboxscoreCollectionPlayerboxscoreToAttach);
            }
            player.setPlayerboxscoreCollection(attachedPlayerboxscoreCollection);
            em.persist(player);
            for (Playerboxscore playerboxscoreCollectionPlayerboxscore : player.getPlayerboxscoreCollection()) {
                Player oldPlayerOfPlayerboxscoreCollectionPlayerboxscore = playerboxscoreCollectionPlayerboxscore.getPlayer();
                playerboxscoreCollectionPlayerboxscore.setPlayer(player);
                playerboxscoreCollectionPlayerboxscore = em.merge(playerboxscoreCollectionPlayerboxscore);
                if (oldPlayerOfPlayerboxscoreCollectionPlayerboxscore != null) {
                    oldPlayerOfPlayerboxscoreCollectionPlayerboxscore.getPlayerboxscoreCollection().remove(playerboxscoreCollectionPlayerboxscore);
                    oldPlayerOfPlayerboxscoreCollectionPlayerboxscore = em.merge(oldPlayerOfPlayerboxscoreCollectionPlayerboxscore);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Player player) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Player persistentPlayer = em.find(Player.class, player.getIdplayer());
            Collection<Playerboxscore> playerboxscoreCollectionOld = persistentPlayer.getPlayerboxscoreCollection();
            Collection<Playerboxscore> playerboxscoreCollectionNew = player.getPlayerboxscoreCollection();
            List<String> illegalOrphanMessages = null;
            for (Playerboxscore playerboxscoreCollectionOldPlayerboxscore : playerboxscoreCollectionOld) {
                if (!playerboxscoreCollectionNew.contains(playerboxscoreCollectionOldPlayerboxscore)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Playerboxscore " + playerboxscoreCollectionOldPlayerboxscore + " since its player field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Playerboxscore> attachedPlayerboxscoreCollectionNew = new ArrayList<Playerboxscore>();
            for (Playerboxscore playerboxscoreCollectionNewPlayerboxscoreToAttach : playerboxscoreCollectionNew) {
                playerboxscoreCollectionNewPlayerboxscoreToAttach = em.getReference(playerboxscoreCollectionNewPlayerboxscoreToAttach.getClass(), playerboxscoreCollectionNewPlayerboxscoreToAttach.getPlayerboxscorePK());
                attachedPlayerboxscoreCollectionNew.add(playerboxscoreCollectionNewPlayerboxscoreToAttach);
            }
            playerboxscoreCollectionNew = attachedPlayerboxscoreCollectionNew;
            player.setPlayerboxscoreCollection(playerboxscoreCollectionNew);
            player = em.merge(player);
            for (Playerboxscore playerboxscoreCollectionNewPlayerboxscore : playerboxscoreCollectionNew) {
                if (!playerboxscoreCollectionOld.contains(playerboxscoreCollectionNewPlayerboxscore)) {
                    Player oldPlayerOfPlayerboxscoreCollectionNewPlayerboxscore = playerboxscoreCollectionNewPlayerboxscore.getPlayer();
                    playerboxscoreCollectionNewPlayerboxscore.setPlayer(player);
                    playerboxscoreCollectionNewPlayerboxscore = em.merge(playerboxscoreCollectionNewPlayerboxscore);
                    if (oldPlayerOfPlayerboxscoreCollectionNewPlayerboxscore != null && !oldPlayerOfPlayerboxscoreCollectionNewPlayerboxscore.equals(player)) {
                        oldPlayerOfPlayerboxscoreCollectionNewPlayerboxscore.getPlayerboxscoreCollection().remove(playerboxscoreCollectionNewPlayerboxscore);
                        oldPlayerOfPlayerboxscoreCollectionNewPlayerboxscore = em.merge(oldPlayerOfPlayerboxscoreCollectionNewPlayerboxscore);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = player.getIdplayer();
                if (findPlayer(id) == null) {
                    throw new NonexistentEntityException("The player with id " + id + " no longer exists.");
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
            Player player;
            try {
                player = em.getReference(Player.class, id);
                player.getIdplayer();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The player with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Playerboxscore> playerboxscoreCollectionOrphanCheck = player.getPlayerboxscoreCollection();
            for (Playerboxscore playerboxscoreCollectionOrphanCheckPlayerboxscore : playerboxscoreCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Player (" + player + ") cannot be destroyed since the Playerboxscore " + playerboxscoreCollectionOrphanCheckPlayerboxscore + " in its playerboxscoreCollection field has a non-nullable player field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(player);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Player> findPlayerEntities() {
        return findPlayerEntities(true, -1, -1);
    }

    public List<Player> findPlayerEntities(int maxResults, int firstResult) {
        return findPlayerEntities(false, maxResults, firstResult);
    }

    private List<Player> findPlayerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Player.class));
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

    public Player findPlayer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Player.class, id);
        } finally {
            em.close();
        }
    }

    public Player findPlayer(Player play) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Player> q = cb.createQuery(Player.class);
            Root<Player> p = q.from(Player.class);
            q.select(p);
            CriteriaQuery<Player> where = q.where(cb.equal(p.get("name"), play.getName()),
                    cb.equal(p.get("position"), play.getPosition()));

            return em.createQuery(where).getSingleResult();

        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {
            return null;
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        } finally {
            em.close();
        }
    }

    public int getPlayerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Player> rt = cq.from(Player.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
