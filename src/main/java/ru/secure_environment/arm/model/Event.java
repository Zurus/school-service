package ru.secure_environment.arm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.secure_environment.arm.model.common.BaseEntity;
import ru.secure_environment.arm.model.enums.EventEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
@Data
@Entity
@ToString
public class Event extends AbstractEvent {

    private String keyHex;
}
