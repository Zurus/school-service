package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.Settings;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface SettingsRepository extends JpaRepository<Settings, Integer> {

    Optional<Settings> findByKey(String key);
}
