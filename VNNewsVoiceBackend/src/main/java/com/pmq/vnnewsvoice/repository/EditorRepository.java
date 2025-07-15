package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.Editor;

import java.util.Map;
import java.util.Optional;

public interface EditorRepository {
    Editor addEditor(Editor editor);

    Optional<Editor> getEditorById(Long id);
    Optional<Editor> getEditorByUsername(String username);
    Optional<Editor> getEditorByEmail(String email);

    Editor updateEditor(Editor editor);

    boolean deleteEditor(Long id);

    long countEditors();
    long countSearchEditors(Map<String, String> filters);
}
