package ru.secure_environment.arm.model;

import lombok.Data;
import ru.secure_environment.arm.model.common.BaseEntity;
import ru.secure_environment.arm.model.enums.EventEnum;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class AbstractEvent extends BaseEntity {

    @Column(name = "event_time")
    private Date eventTime;

    @Column(name = "log_id")
    private int logId;

    @Column(name = "event_type")
    @Enumerated(EnumType.ORDINAL)
    private EventEnum direction;
}
