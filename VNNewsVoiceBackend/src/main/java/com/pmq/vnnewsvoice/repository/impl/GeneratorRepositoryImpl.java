package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Generator;
import com.pmq.vnnewsvoice.repository.GeneratorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class GeneratorRepositoryImpl implements GeneratorRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Generator addGenerator(Generator generator) {
        entityManager.persist(generator);
        return generator;
    }

    @Override
    public Optional<Generator> getGeneratorById(Long id) {
        return Optional.ofNullable(entityManager.find(Generator.class, id));
    }

    @Override
    public Optional<Generator> getGeneratorByName(String name) {
        String hql = "FROM Generator g WHERE g.name like :name";
        return entityManager.createQuery(hql, Generator.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }

    @Override
    public String getGeneratorURL(Long id) {
        String hql = "SELECT g.url FROM Generator g WHERE g.id = :id";
        return entityManager.createQuery(hql, String.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<Generator> updateGenerator(Generator generator) {
        Generator existingGenerator = entityManager.find(Generator.class, generator.getId());
        if (existingGenerator != null) {
            existingGenerator.setName(generator.getName());
            existingGenerator.setUrl(generator.getUrl());
            existingGenerator.setIsActive(generator.getIsActive());
            entityManager.merge(existingGenerator);
            return Optional.of(existingGenerator);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteGenerator(Long id) {
        Generator generator = entityManager.find(Generator.class, id);
        if (generator != null) {
            entityManager.remove(generator);
            return true;
        }
        return false;
    }

    @Override
    public long countGenerators() {
        String hql = "SELECT COUNT(g) FROM Generator g";
        return entityManager.createQuery(hql, Long.class)
                .getSingleResult();
    }

    @Override
    public long countSearchGenerators(Map<String, String> filters) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Generator> root = query.from(Generator.class);
        query.select(builder.count(root));

        // Build predicates
        List<Predicate> predicates = buildSearchPredicates(filters, builder, root);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public long countActiveGenerators(Map<String, String> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Generator> root = query.from(Generator.class);
        query.select(builder.count(root));

        // Build predicates for active generators
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("isActive"), true));

        if (params != null && params.containsKey("name")) {
            String name = params.get("name");
            predicates.add(builder.like(root.get("name"), "%" + name + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public LocalDateTime getLastCrawlTimeOfGeneratorByGenerator(Generator generator) {
        String hql = "SELECT g.lastTimeCrawled FROM Generator g WHERE g.id = :id";
        return entityManager.createQuery(hql, LocalDateTime.class)
                .setParameter("id", generator.getId())
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public LocalDateTime getLastCrawlTimeOfGeneratorById(Long id) {
        String hql = "SELECT g.lastTimeCrawled FROM Generator g WHERE g.id = :id";
        return entityManager.createQuery(hql, LocalDateTime.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public LocalDateTime getLastCrawlTimeOfGeneratorByName(String name) {
        return null;
    }

    @Override
    public List<Predicate> buildSearchPredicates(Map<String, String> filters, CriteriaBuilder builder, Root<Generator> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (filters != null) {
            if (filters.containsKey("name")) {
                String name = filters.get("name");
                predicates.add(builder.like(root.get("name"), "%" + name + "%"));
            }
            if (filters.containsKey("isActive")) {
                Boolean isActive = Boolean.parseBoolean(filters.get("isActive"));
                predicates.add(builder.equal(root.get("isActive"), isActive));
            }
        }
        return predicates;
    }
}
