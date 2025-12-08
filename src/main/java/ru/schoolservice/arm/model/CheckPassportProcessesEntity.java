package ru.schoolservice.arm.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "check_passport_processes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckPassportProcessesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "check_passport_id")
    private CheckPassportEntity checkPassport;

    @Column(name = "process_id")
    private String processId;


    @Override
    public String toString() {
        return "CheckPassportProcessesEntity{" +
                "id=" + id +
                ", processId='" + processId + '\'' +
                '}';
    }
}
