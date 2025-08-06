package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Notification;
import com.pmq.vnnewsvoice.repository.NotificationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class NotificationRepositoryImpl implements NotificationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Notification addNotification(Notification notification) {
        entityManager.persist(notification);
        return notification;
    }

    @Override
    public Notification getNotificationById(Long id) {
        return entityManager.find(Notification.class, id);
    }

    @Override
    public Notification updateNotification(Notification notification) {
        return entityManager.merge(notification);
    }

    @Override
    public boolean deleteNotification(Long id) {
        Notification notification = entityManager.find(Notification.class, id);
        if (notification != null) {
            entityManager.remove(notification);
            return true;
        }
        return false;
    }

    @Override
    public long countNotifications() {
        Query query = entityManager.createQuery("SELECT COUNT(n) FROM Notification n");
        return (long) query.getSingleResult();
    }

    @Override
    public long countUnreadNotifications() {
        Query query = entityManager.createQuery("SELECT COUNT(n) FROM Notification n WHERE n.isRead = false");
        return (long) query.getSingleResult();
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        Query query = entityManager.createQuery("SELECT n FROM Notification n WHERE n.userId.id = :userId ORDER BY n.createdAt DESC");
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public List<Notification> getNotificationsByUserIdPaginated(Long userId, int page, int size) {
        TypedQuery<Notification> query = entityManager.createQuery(
                "SELECT n FROM Notification n WHERE n.userId.id = :userId ORDER BY n.createdAt DESC", 
                Notification.class);
        query.setParameter("userId", userId);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    }
    
    @Override
    public long countNotificationsByUserId(Long userId) {
        Query query = entityManager.createQuery("SELECT COUNT(n) FROM Notification n WHERE n.userId.id = :userId");
        query.setParameter("userId", userId);
        return (long) query.getSingleResult();
    }
}