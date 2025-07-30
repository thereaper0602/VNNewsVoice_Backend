package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.Editor;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EditorRepository {
    Editor addEditor(Editor editor);

    Optional<Editor> getEditorById(Long id);
    Optional<Editor> getEditorByUserId(Long userId);

    List<Editor> getEditors(Map<String, String> params);

    Editor updateEditor(Editor editor);

    boolean deleteEditor(Long id);

    long countEditors();
    long countSearchEditors(Map<String, String> filters);
    List<Predicate> buildSearchPredicates(Map<String, String> filters, CriteriaBuilder builder, Root<Editor> root);
}
