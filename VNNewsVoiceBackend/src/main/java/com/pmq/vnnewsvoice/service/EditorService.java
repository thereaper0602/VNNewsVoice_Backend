package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.Editor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EditorService {
    Editor addEditor(Editor editor);

    Optional<Editor> getEditorById(Long id);
    Optional<Editor> getEditorByUserId(Long userId);

    List<Editor> getEditors(Map<String, String> params);

    Editor updateEditor(Editor editor);

    boolean deleteEditor(Long id);

    long countEditors();
    long countSearchEditors(Map<String, String> filters);
}
