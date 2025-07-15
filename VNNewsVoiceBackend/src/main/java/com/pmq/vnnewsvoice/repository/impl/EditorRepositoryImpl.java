package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Editor;
import com.pmq.vnnewsvoice.repository.EditorRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class EditorRepositoryImpl implements EditorRepository {
    @Override
    public Editor addEditor(Editor editor) {
        return null;
    }

    @Override
    public Optional<Editor> getEditorById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Editor> getEditorByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<Editor> getEditorByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Editor updateEditor(Editor editor) {
        return null;
    }

    @Override
    public boolean deleteEditor(Long id) {
        return false;
    }

    @Override
    public long countEditors() {
        return 0;
    }

    @Override
    public long countSearchEditors(Map<String, String> filters) {
        return 0;
    }
}
