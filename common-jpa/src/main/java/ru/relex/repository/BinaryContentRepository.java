package ru.relex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.relex.entity.BinaryContent;

public interface BinaryContentRepository extends JpaRepository<BinaryContent, Long> {
}
