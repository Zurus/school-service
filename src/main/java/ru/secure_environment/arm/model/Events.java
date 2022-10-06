package ru.secure_environment.arm.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import ru.secure_environment.arm.model.enums.EventEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Table(name = "events")
@Data
@Entity
public class Events extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "event_time")
    private LocalDateTime eventTime;

    @Column(name = "event_type")
    @Pattern(regexp = "[-]{0,1}[0-1]{1}", message = "Wrong event")
    private int eventType;

    private String userKeyCard;

    public EventEnum getEvent() {
        return EventEnum.parse(eventType);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
