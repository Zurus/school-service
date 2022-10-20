package ru.secure_environment.arm.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.secure_environment.arm.model.enums.EventEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Table(name = "events")
@Data
@Entity
@ToString
public class Events extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "event_time")
    private Date eventTime;

    @Column(name = "log_id")
    private int logId;

    @Column(name = "event_type")
    private int direction;

    private String userKeyCard;

    public EventEnum getEvent() {
        return EventEnum.parse(direction);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
