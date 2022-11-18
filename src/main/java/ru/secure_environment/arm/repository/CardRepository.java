package ru.secure_environment.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.secure_environment.arm.model.Card;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    Optional<Card> findCardByCardId(String cardId);

    Boolean existsByCardId(String cardId);

    @Query("SELECT c FROM Card c WHERE c.cardId in :cardsHex")
    List<Card> findAllCardByCardHex(List<String> cardsHex);
}
