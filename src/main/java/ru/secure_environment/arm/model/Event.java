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
@Table(name = "events", uniqueConstraints = {@UniqueConstraint(name = "event_card_id_log_id_uk", columnNames = {"card_id", "log_id"})})
@Data
@Entity
@ToString
public class Event extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id", nullable = false, foreignKey = @ForeignKey(name = "events_card_id_fk"))
    private Card card;

    @Column(name = "event_time")
    private Date eventTime;

    @Column(name = "log_id")
    private int logId;

    @Column(name = "event_type")
    @Enumerated(EnumType.ORDINAL)
    private EventEnum direction;
}
