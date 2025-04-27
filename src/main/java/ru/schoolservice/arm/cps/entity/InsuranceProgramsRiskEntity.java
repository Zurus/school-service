package ru.schoolservice.arm.cps.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "insurance_programs_risk")
public class InsuranceProgramsRiskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "insurance_program_id", referencedColumnName = "id")
    private InsuranceProgramEntity program;

    @ManyToOne
    @JoinColumn(name = "risk_id", referencedColumnName = "id")
    private InsuranceCreditRiskEntity risk;

    @Column(name = "active_from")
    private LocalDate activeFrom;

    @Column(name = "active_to")
    private LocalDate activeTo;

    @Column(name = "non_standart")
    private Boolean nonStandart;

    @Column(name = "sure")
    private Boolean sure;

    /**
     * Конструктор
     */
    public InsuranceProgramsRiskEntity() {
    }

    /**
     * Конструктор
     *
     * @param program    страховая программа
     * @param risk       страховой риск
     * @param activeFrom действует с
     * @param activeTo   действует по
     * @param nonStandart нестандартная
     * @param sure       признак заверения
     */
    public InsuranceProgramsRiskEntity(InsuranceProgramEntity program, InsuranceCreditRiskEntity risk, LocalDate activeFrom, LocalDate activeTo, Boolean nonStandart, Boolean sure) {
        this.program = program;
        this.risk = risk;
        this.activeFrom = activeFrom;
        this.activeTo = activeTo;
        this.nonStandart = nonStandart;
        this.sure = sure;
    }

    /**
     * Получить идентификатор
     *
     * @return идентификатор
     */
    public Long getId() {
        return id;
    }

    /**
     * Установить идентификатор
     *
     * @param id идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Получить страховую программу
     *
     * @return страховая программа
     */
    public InsuranceProgramEntity getProgram() {
        return program;
    }

    /**
     * Установить страховую программу
     *
     * @param program страховая программа
     */
    public void setProgram(InsuranceProgramEntity program) {
        this.program = program;
    }

    /**
     * Получить риск
     *
     * @return риск
     */
    public InsuranceCreditRiskEntity getRisk() {
        return risk;
    }

    /**
     * Установить риск
     *
     * @param risk риск
     */
    public void setRisk(InsuranceCreditRiskEntity risk) {
        this.risk = risk;
    }

    /**
     * Получить дату начала ЖЦ
     *
     * @return дата начала ЖЦ
     */
    public LocalDate getActiveFrom() {
        return activeFrom;
    }

    /**
     * Установить дату начала ЖЦ
     *
     * @param activeFrom дата начала ЖЦ
     */
    public void setActiveFrom(LocalDate activeFrom) {
        this.activeFrom = activeFrom;
    }

    /**
     * Получить дату окончания ЖЦ
     *
     * @return дата окончания ЖЦ
     */
    public LocalDate getActiveTo() {
        return activeTo;
    }

    /**
     * Установить дату окончания ЖЦ
     *
     * @param activeTo дата окончания ЖЦ
     */
    public void setActiveTo(LocalDate activeTo) {
        this.activeTo = activeTo;
    }

    /**
     * Получить признак нестандартного условия
     *
     * @return признак нестандартного условия
     */
    public Boolean isNonStandart() {
        return nonStandart;
    }

    /**
     * Установить признак нестандартного условия
     *
     * @param nonStandart признак нестандартного условия
     */
    public void setNonStandart(Boolean nonStandart) {
        this.nonStandart = nonStandart;
    }

    /**
     * Получить признак заверения
     *
     * @return признак заверения
     */
    public Boolean isSure() {
        return sure;
    }

    /**
     * Установить признак заверения
     *
     * @param sure признак заверения
     */
    public void setSure(Boolean sure) {
        this.sure = sure;
    }
}
