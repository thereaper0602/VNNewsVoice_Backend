package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.Reader;
import com.pmq.vnnewsvoice.pojo.UserProvider;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReaderService {
    Reader addReader(Reader reader);

    Optional<Reader> getReaderById(Long id);
    Optional<Reader> getReaderByUsername(String username);
    Optional<Reader> getReaderByEmail(String email);

    List<Reader> getReaders(Map<String, String> params);
    List<Reader> searchReaders(Map<String, String> filters, Map<String, String> params);
    List<Reader> getActiveReaders(Map<String, String> params);

    Reader updateReader(Reader reader);

    boolean deleteReader(Long id);

    long countReaders();
    long countSearchReaders(Map<String, String> filters);

    Reader getReaderByUserProvider(UserProvider userProvider);
}
