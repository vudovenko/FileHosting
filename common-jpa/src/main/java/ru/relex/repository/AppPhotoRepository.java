package ru.relex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.relex.entity.AppPhoto;

public interface AppPhotoRepository extends JpaRepository<AppPhoto, Long> {
}
