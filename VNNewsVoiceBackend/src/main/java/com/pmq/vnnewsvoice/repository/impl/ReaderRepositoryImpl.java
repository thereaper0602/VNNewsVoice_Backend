package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Reader;
import com.pmq.vnnewsvoice.pojo.UserInfo;
import com.pmq.vnnewsvoice.pojo.UserProvider;
import com.pmq.vnnewsvoice.repository.ReaderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
@Transactional
public class ReaderRepositoryImpl implements ReaderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Reader addReader(Reader reader) {
        entityManager.persist(reader);
        return reader;
    }

    @Override
    public Optional<Reader> getReaderById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(entityManager.find(Reader.class, id));
    }

    @Override
    public Optional<Reader> getReaderByUsername(String username) {
        if (username == null || username.isEmpty()) {
            return Optional.empty();
        }
        String hql = "SELECT r FROM Reader r JOIN r.userId u WHERE u.username = :username";
        List<Reader> readers = entityManager.createQuery(hql, Reader.class)
                .setParameter("username", username)
                .getResultList();
        return readers.isEmpty() ? Optional.empty() : Optional.of(readers.get(0));
    }

    @Override
    public Optional<Reader> getReaderByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return Optional.empty();
        }
        String hql = "SELECT r FROM Reader r JOIN r.userId u WHERE u.email = :email";
        List<Reader> readers = entityManager.createQuery(hql, Reader.class)
                .setParameter("email", email)
                .getResultList();
        return readers.isEmpty() ? Optional.empty() : Optional.of(readers.get(0));
    }

    @Override
    public List<Reader> getReaders(Map<String, String> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reader> query = builder.createQuery(Reader.class);
        Root<Reader> root = query.from(Reader.class);
        query.select(root);

        TypedQuery<Reader> q = entityManager.createQuery(query);

        if (params != null) {
            int page = Integer.parseInt(params.getOrDefault("page", "1"));
            int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "10"));
            page = Math.max(1, page);
            int start = (page - 1) * pageSize;
            q.setFirstResult(start);
            q.setMaxResults(pageSize);
        }
        return q.getResultList();
    }

    @Override
    public List<Reader> searchReaders(Map<String, String> filters, Map<String, String> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reader> query = builder.createQuery(Reader.class);
        Root<Reader> root = query.from(Reader.class);
        query.select(root);

        List<Predicate> predicates = buildSearchPredicates(filters, builder, root);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }

        TypedQuery<Reader> q = entityManager.createQuery(query);

        if (params != null) {
            int page = Integer.parseInt(params.getOrDefault("page", "1"));
            int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "10"));
            page = Math.max(1, page);
            int start = (page - 1) * pageSize;
            q.setFirstResult(start);
            q.setMaxResults(pageSize);
        }

        return q.getResultList();
    }

    @Override
    public List<Reader> getActiveReaders(Map<String, String> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reader> query = builder.createQuery(Reader.class);
        Root<Reader> root = query.from(Reader.class);
        query.select(root);

        // Join với UserInfo để lấy trường isActive
        jakarta.persistence.criteria.Join<Reader, UserInfo> userInfoJoin = root.join("userId");
        query.where(builder.equal(userInfoJoin.get("isActive"), true));

        TypedQuery<Reader> q = entityManager.createQuery(query);

        if (params != null) {
            int page = Integer.parseInt(params.getOrDefault("page", "1"));
            int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "10"));
            page = Math.max(1, page);
            int start = (page - 1) * pageSize;
            q.setFirstResult(start);
            q.setMaxResults(pageSize);
        }

        return q.getResultList();
    }

    @Override
    public Reader updateReader(Reader reader) {
        if (getReaderById(reader.getId()).isPresent()) {
            return entityManager.merge(reader);
        }
        return null;
    }

    @Override
    public boolean deleteReader(Long id) {
        if (id == null) {
            return false;
        }
        Reader reader = entityManager.find(Reader.class, id);
        if (reader != null) {
            entityManager.remove(reader);
            return true;
        }
        return false;
    }

    @Override
    public long countReaders() {
        String hql = "SELECT COUNT(r) FROM Reader r";
        return entityManager.createQuery(hql, Long.class).getSingleResult();
    }

    @Override
    public long countSearchReaders(Map<String, String> filters) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Reader> root = query.from(Reader.class);
        query.select(builder.count(root));

        List<Predicate> predicates = buildSearchPredicates(filters, builder, root);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Reader getReaderByUserProvider(UserProvider userProvider) {
        if (userProvider == null || userProvider.getId() == null) {
            return null;
        }
        String hql = "SELECT r FROM Reader r JOIN r.userId u JOIN u.userProviderSet up WHERE up.id = :providerId";
        List<Reader> readers = entityManager.createQuery(hql, Reader.class)
                .setParameter("providerId", userProvider.getId())
                .getResultList();
        return readers.isEmpty() ? null : readers.get(0);
    }

    private List<Predicate> buildSearchPredicates(Map<String, String> filters, CriteriaBuilder builder, Root<Reader> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filters != null && !filters.isEmpty()) {
            jakarta.persistence.criteria.Join<Reader, UserInfo> userInfoJoin = root.join("userId");

            if (filters.containsKey("username")) {
                String keyword = "%" + filters.get("username").replace("_", "\\_").replace("%", "\\%") + "%";
                predicates.add(builder.like(userInfoJoin.get("username"), keyword, '\\'));
            }

            if (filters.containsKey("email")) {
                String keyword = "%" + filters.get("email").replace("_", "\\_").replace("%", "\\%") + "%";
                predicates.add(builder.like(userInfoJoin.get("email"), keyword, '\\'));
            }

            if (filters.containsKey("isActive")) {
                Boolean isActive = Boolean.parseBoolean(filters.get("isActive"));
                predicates.add(builder.equal(userInfoJoin.get("isActive"), isActive));
            }
        }

        return predicates;
    }
}
