package ru.schoolservice.arm.cps.entity;

import javax.persistence.Column;
import java.util.Objects;

public class InsuranceProgramEntity extends DictionaryEntity {
    @Column(name = "REPORT_FORM_CODE")
    private String reportFormCode;

    @Column(name = "DBO_RISK_CODE")
    private String dboRiskCode;

    @Column(name = "ABS_PROGRAM_CODE")
    private String absProgramCode;

    /**
     * Конструктор
     */
    public InsuranceProgramEntity() {
    }

    /**
     * Конструктор
     *
     * @param code           код
     * @param name           наименование
     * @param reportFormCode код печатной формы
     * @param dboRiskCode    код риска в ДБО
     * @param absProgramCode код программы в АБС
     */
    public InsuranceProgramEntity(String code, String name, String reportFormCode, String dboRiskCode,
                                  String absProgramCode) {
        super(code, name);
        this.reportFormCode = reportFormCode;
        this.dboRiskCode = dboRiskCode;
        this.absProgramCode = absProgramCode;
    }

    /**
     * Вернуть код печатной формы
     *
     * @return код печатной формы
     */
    public String getReportFormCode() {
        return reportFormCode;
    }

    /**
     * Установить код печатной формы
     *
     * @param reportFormCode код печатной формы
     */
    public void setReportFormCode(String reportFormCode) {
        this.reportFormCode = reportFormCode;
    }

    /**
     * Получить код риска в ДБО
     *
     * @return код риска в ДБО
     */
    public String getDboRiskCode() {
        return dboRiskCode;
    }

    /**
     * Установить код риска в ДБО
     *
     * @param dboRiskCode код риска в ДБО
     */
    public void setDboRiskCode(String dboRiskCode) {
        this.dboRiskCode = dboRiskCode;
    }

    /**
     * Получить код программы в АБС
     *
     * @return код программы
     */
    public String getAbsProgramCode() {
        return absProgramCode;
    }

    /**
     * Установить код программы в АБС
     *
     * @param absProgramCode код программы
     */
    public void setAbsProgramCode(String absProgramCode) {
        this.absProgramCode = absProgramCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InsuranceProgramEntity that = (InsuranceProgramEntity) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getCode(), that.getCode()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(reportFormCode, that.reportFormCode) &&
                Objects.equals(dboRiskCode, that.dboRiskCode) &&
                Objects.equals(absProgramCode, that.absProgramCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCode(), getName(), reportFormCode, absProgramCode);
    }

    @Override
    public String toString() {
        return "InsuranceProgramEntity{" +
                "id=" + getId() +
                ", code='" + getCode() + '\'' +
                ", name='" + getName() + '\'' +
                ", reportFormCode='" + reportFormCode + '\'' +
                ", dboRiskCode=" + dboRiskCode +
                ", absProgramCode=" + absProgramCode +
                '}';
    }
}
