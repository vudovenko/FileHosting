package ru.relex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.relex.entity.RawData;

public interface RawDataRepository extends JpaRepository<RawData, Long> {
}
