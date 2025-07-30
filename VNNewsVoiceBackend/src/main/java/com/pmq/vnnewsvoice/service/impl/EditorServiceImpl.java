package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.Editor;
import com.pmq.vnnewsvoice.repository.EditorRepository;
import com.pmq.vnnewsvoice.service.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class EditorServiceImpl implements EditorService {

    @Autowired
    private EditorRepository editorRepository;

    @Override
    public Editor addEditor(Editor editor) {
        if (editor == null || editor.getUserId() == null) {
            throw new IllegalArgumentException("Invalid editor data");
        }
        return editorRepository.addEditor(editor);
    }

    @Override
    public Optional<Editor> getEditorById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return editorRepository.getEditorById(id);
    }

    @Override
    public Optional<Editor> getEditorByUserId(Long userId) {
        if (userId == null) {
            return Optional.empty();
        }
        return editorRepository.getEditorByUserId(userId);
    }

    @Override
    public List<Editor> getEditors(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return editorRepository.getEditors(Map.of());
        }
        return editorRepository.getEditors(params);
    }

    @Override
    public Editor updateEditor(Editor editor) {
        if (editor == null || editor.getId() == null) {
            throw new IllegalArgumentException("Invalid editor data");
        }
        return editorRepository.updateEditor(editor);
    }

    @Override
    public boolean deleteEditor(Long id) {
        if (id == null) {
            return false;
        }
        return editorRepository.deleteEditor(id);
    }

    @Override
    public long countEditors() {
        return editorRepository.countEditors();
    }

    @Override
    public long countSearchEditors(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return editorRepository.countEditors();
        }
        return editorRepository.countSearchEditors(filters);
    }
}
