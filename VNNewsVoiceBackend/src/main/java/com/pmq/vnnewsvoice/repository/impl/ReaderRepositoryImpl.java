package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Reader;
import com.pmq.vnnewsvoice.pojo.UserProvider;
import com.pmq.vnnewsvoice.repository.ReaderRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
@Transactional
public class ReaderRepositoryImpl implements ReaderRepository {

    @Override
    public Reader addReader(Reader reader) {
        return null;
    }

    @Override
    public Optional<Reader> getReaderById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Reader> getReaderByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<Reader> getReaderByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<Reader> getReaders(Map<String, String> params) {
        return List.of();
    }

    @Override
    public List<Reader> searchReaders(Map<String, String> filters, Map<String, String> params) {
        return List.of();
    }

    @Override
    public List<Reader> getActiveReaders(Map<String, String> params) {
        return List.of();
    }

    @Override
    public Reader updateReader(Reader reader) {
        return null;
    }

    @Override
    public boolean deleteReader(Long id) {
        return false;
    }

    @Override
    public long countReaders() {
        return 0;
    }

    @Override
    public long countSearchReaders(Map<String, String> filters) {
        return 0;
    }

    @Override
    public Reader getReaderByUserProvider(UserProvider userProvider) {
        return null;
    }
}
