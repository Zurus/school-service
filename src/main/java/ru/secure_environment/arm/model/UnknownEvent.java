package ru.secure_environment.arm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "unknown_events")
@Data
@Entity
@ToString
public class UnknownEvent extends AbstractEvent {

    @Column(name = "card", nullable = false)
    @NotBlank
    private String card;
}
