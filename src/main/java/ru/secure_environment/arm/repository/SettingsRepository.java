package ru.secure_environment.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.model.Settings;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface SettingsRepository extends JpaRepository<Settings, Integer> {

    Optional<Settings> getSettingsByKey(String key);
}
