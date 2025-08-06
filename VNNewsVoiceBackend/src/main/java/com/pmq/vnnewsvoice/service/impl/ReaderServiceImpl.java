package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.Reader;
import com.pmq.vnnewsvoice.pojo.UserProvider;
import com.pmq.vnnewsvoice.repository.ReaderRepository;
import com.pmq.vnnewsvoice.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderRepository readerRepository;
    @Override
    public Reader addReader(Reader reader) {
        if (reader == null || reader.getUserId() == null) {
            throw new IllegalArgumentException("Invalid reader data");
        }
        return readerRepository.addReader(reader);
    }

    @Override
    public Optional<Reader> getReaderById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return readerRepository.getReaderById(id);
    }

    @Override
    public Optional<Reader> getReaderByUsername(String username) {
        if (username == null || username.isEmpty()) {
            return Optional.empty();
        }
        return readerRepository.getReaderByUsername(username);
    }

    @Override
    public Optional<Reader> getReaderByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return Optional.empty();
        }
        return readerRepository.getReaderByEmail(email);
    }

    @Override
    public List<Reader> getReaders(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return readerRepository.getReaders(Map.of());
        }
        return readerRepository.getReaders(params);
    }

    @Override
    public List<Reader> searchReaders(Map<String, String> filters, Map<String, String> params) {
        if (filters == null || filters.isEmpty()) {
            return readerRepository.getReaders(params);
        }
        return readerRepository.searchReaders(filters, params);
    }

    @Override
    public List<Reader> getActiveReaders(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return readerRepository.getActiveReaders(Map.of());
        }
        return readerRepository.getActiveReaders(params);
    }

    @Override
    public Reader updateReader(Reader reader) {
        if (reader == null || reader.getId() == null) {
            throw new IllegalArgumentException("Invalid reader data");
        }
        return readerRepository.updateReader(reader);
    }

    @Override
    public boolean deleteReader(Long id) {
        if (id == null) {
            return false;
        }
        return readerRepository.deleteReader(id);
    }

    @Override
    public long countReaders() {
        return readerRepository.countReaders();
    }

    @Override
    public long countSearchReaders(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return readerRepository.countReaders();
        }
        return readerRepository.countSearchReaders(filters);
    }

    @Override
    public Reader getReaderByUserProvider(UserProvider userProvider) {
        if (userProvider == null || userProvider.getId() == null) {
            return null;
        }
        return readerRepository.getReaderByUserProvider(userProvider);
    }
}
