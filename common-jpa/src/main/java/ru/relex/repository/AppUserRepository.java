package ru.relex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.relex.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findAppUserByTelegramUserId(Long id);
}
