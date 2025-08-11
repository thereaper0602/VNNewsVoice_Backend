package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.Generator;
import com.pmq.vnnewsvoice.repository.GeneratorRepository;
import com.pmq.vnnewsvoice.service.GeneratorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    private GeneratorRepository generatorRepository;

    @Override
    public Generator addGenerator(Generator generator) {
        if (isValidGenerator(generator)) {
            return generatorRepository.addGenerator(generator);
        }
        return null;
    }

    @Override
    public Optional<Generator> getGeneratorById(Long id) {
        return generatorRepository.getGeneratorById(id);
    }

    @Override
    public Optional<Generator> getGeneratorByName(String name) {
        return generatorRepository.getGeneratorByName(name);
    }

    @Override
    public String getGeneratorURL(Long id) {
        Optional<Generator> generatorOptional = generatorRepository.getGeneratorById(id);
        if (generatorOptional.isEmpty()) {
            return "";
        }
        return generatorOptional.get().getUrl();
    }

    @Override
    public List<Generator> getGenerators(Map<String, String> params) {
        return generatorRepository.getGenerators(params);
    }

    @Override
    public Optional<Generator> updateGenerator(Generator generator) {
        if (isValidGenerator(generator)) {
            return generatorRepository.updateGenerator(generator);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteGenerator(Long id) {
        return generatorRepository.deleteGenerator(id);
    }

    @Override
    public long countGenerators() {
        return generatorRepository.countGenerators();
    }

    @Override
    public long countSearchGenerators(Map<String, String> filters) {
        return generatorRepository.countSearchGenerators(filters);
    }

    @Override
    public long countActiveGenerators(Map<String, String> params) {
        return generatorRepository.countActiveGenerators(params);
    }

    @Override
    public LocalDateTime getLastCrawlTimeOfGeneratorByGenerator(Generator generator) {
        if (isValidGenerator(generator)) {
            return generatorRepository.getLastCrawlTimeOfGeneratorByGenerator(generator);
        }
        return null;
    }

    @Override
    public LocalDateTime getLastCrawlTimeOfGeneratorById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return generatorRepository.getLastCrawlTimeOfGeneratorById(id);
    }

    @Override
    public LocalDateTime getLastCrawlTimeOfGeneratorByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return generatorRepository.getLastCrawlTimeOfGeneratorByName(name);
    }

    @Override
    public boolean isValidGenerator(Generator generator) {
        if (generator == null) {
            return false;
        }
        Optional<Generator> generatorOptional = generatorRepository.getGeneratorById(generator.getId());
        if (generatorOptional.isEmpty()) {
            return false;
        }
        if (generator.getName().isEmpty() || generator.getUrl().isEmpty()) {
            return false;
        }
        return true;
    }

    
}
