package ru.relex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.relex.entity.AppDocument;

public interface AppDocumentRepository extends JpaRepository<AppDocument, Long> {
}
