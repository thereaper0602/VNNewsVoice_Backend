package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.Generator;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public interface GeneratorRepository {
    Generator addGenerator(Generator generator);

    Optional<Generator> getGeneratorById(Long id);
    Optional<Generator> getGeneratorByName(String name);
    String getGeneratorURL(Long id);

    Optional<Generator> updateGenerator(Generator generator);

    boolean deleteGenerator(Long id);

    long countGenerators();
    long countSearchGenerators(Map<String, String> filters);
    long countActiveGenerators(Map<String, String> params);

    LocalDateTime getLastCrawlTimeOfGeneratorByGenerator(Generator generator);
    LocalDateTime getLastCrawlTimeOfGeneratorById(Long id);
    LocalDateTime getLastCrawlTimeOfGeneratorByName(String name);
}
