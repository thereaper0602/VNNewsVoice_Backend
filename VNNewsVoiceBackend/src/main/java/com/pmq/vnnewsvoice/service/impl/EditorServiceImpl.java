package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.Editor;
import com.pmq.vnnewsvoice.service.EditorService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class EditorServiceImpl implements EditorService {
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
