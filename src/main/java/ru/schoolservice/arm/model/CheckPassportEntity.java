package ru.schoolservice.arm.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "check_passport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckPassportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fio")
    private String fio;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "doc_number")
    private String docNumber;

    @Column(name = "doc_series")
    private String docSeries;

    @Column(name = "check_status")
    private String checkStatus;

    @Column(name = "actualization_date")
    private LocalDateTime actualizationDate;

    @Column(name = "load_date")
    private LocalDateTime loadDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "checkPassport", orphanRemoval = true)
    private List<CheckPassportProcessesEntity> checkPassportProcessesEntities = new ArrayList<>();


    @Override
    public String toString() {
        return "CheckPassportEntity{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", birthDate=" + birthDate +
                ", docNumber='" + docNumber + '\'' +
                ", docSeries='" + docSeries + '\'' +
                ", checkStatus='" + checkStatus + '\'' +
                ", actualizationDate=" + actualizationDate +
                ", loadDate=" + loadDate +
                '}';
    }
}
