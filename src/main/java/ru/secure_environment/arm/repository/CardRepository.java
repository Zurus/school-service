package ru.secure_environment.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.secure_environment.arm.model.Card;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {


    Optional<Card> findCardByCardId(String cardId);

    Boolean existsByCardId(String cardId);
}
