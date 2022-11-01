package ru.secure_environment.arm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.secure_environment.arm.model.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Card extends BaseEntity implements Serializable {

    public Card(Integer id, String cardId) {
        super(id);
        this.cardId = cardId;
    }

    @Column(name = "card_hex_code", nullable = false)
    @NotBlank
    //@Pattern(regexp = "[0-9]{3},[0-9]{5}", message = "Wrong card id")
    private String cardId;
}
