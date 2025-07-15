package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Generator;
import com.pmq.vnnewsvoice.repository.GeneratorRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class GeneratorRepositoryImpl implements GeneratorRepository {

    @Override
    public Generator addGenerator(Generator generator) {
        return null;
    }

    @Override
    public Optional<Generator> getGeneratorById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Generator> getGeneratorByName(String name) {
        return Optional.empty();
    }

    @Override
    public String getGeneratorURL(Long id) {
        return "";
    }

    @Override
    public Optional<Generator> updateGenerator(Generator generator) {
        return Optional.empty();
    }

    @Override
    public boolean deleteGenerator(Long id) {
        return false;
    }

    @Override
    public long countGenerators() {
        return 0;
    }

    @Override
    public long countSearchGenerators(Map<String, String> filters) {
        return 0;
    }

    @Override
    public long countActiveGenerators(Map<String, String> params) {
        return 0;
    }

    @Override
    public LocalDateTime getLastCrawlTimeOfGeneratorByGenerator(Generator generator) {
        return null;
    }

    @Override
    public LocalDateTime getLastCrawlTimeOfGeneratorById(Long id) {
        return null;
    }

    @Override
    public LocalDateTime getLastCrawlTimeOfGeneratorByName(String name) {
        return null;
    }
}
