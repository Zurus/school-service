package ru.schoolservice.arm.cps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Validated
public class InsuranceProgramRiskObject {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("insuranceCode")
    private String insuranceCode = null;

    @JsonProperty("objectType")
    private InsuranceRiskObjectType objectType = null;

    @JsonProperty("riskCode")
    private String riskCode = null;

    @JsonProperty("absRiskCode")
    private String absRiskCode = null;

    @JsonProperty("activeFrom")
    private LocalDate activeFrom = null;

    @JsonProperty("activeTo")
    private LocalDate activeTo = null;

    @JsonProperty("nonStandart")
    private Boolean nonStandart = null;

    @JsonProperty("sure")
    private Boolean sure = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("is_combined")
    private Boolean isCombined = null;

    @JsonProperty("combined_ids")
    @Valid
    private List<String> combinedIds = null;


    /**
     * Создает пустой экземпляр класса
     */
    public InsuranceProgramRiskObject() {}

    /**
     * Создает экземпляр класса
     *
     * @param id Идентификатор страхового риска
     * @param insuranceCode Код страховой программы
     * @param objectType Код страхового риска
     * @param riskCode Код страхового риска
     * @param absRiskCode Код страхового риска в АБС
     * @param activeFrom Дата начала действия записи
     * @param activeTo Дата окончания действия записи
     * @param nonStandart Признак нестандартности
     * @param sure Признак завереения
     * @param name Наименование страхового риска
     * @param isCombined Признак комбинированного риска
     * @param combinedIds
     */
    public InsuranceProgramRiskObject(Long id, String insuranceCode, InsuranceRiskObjectType objectType, String riskCode, String absRiskCode, LocalDate activeFrom, LocalDate activeTo, Boolean nonStandart, Boolean sure, String name, Boolean isCombined, List<String> combinedIds) {
        this.id = id;
        this.insuranceCode = insuranceCode;
        this.objectType = objectType;
        this.riskCode = riskCode;
        this.absRiskCode = absRiskCode;
        this.activeFrom = activeFrom;
        this.activeTo = activeTo;
        this.nonStandart = nonStandart;
        this.sure = sure;
        this.name = name;
        this.isCombined = isCombined;
        this.combinedIds = combinedIds;
    }

    /**
     * Идентификатор страхового риска
     *
     * @return Идентификатор страхового риска
     */



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Код страховой программы
     *
     * @return Код страховой программы
     */



    public String getInsuranceCode() {
        return insuranceCode;
    }

    public void setInsuranceCode(String insuranceCode) {
        this.insuranceCode = insuranceCode;
    }


    /**
     * Код страхового риска
     *
     * @return Код страхового риска
     */

    @Valid


    public InsuranceRiskObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(InsuranceRiskObjectType objectType) {
        this.objectType = objectType;
    }


    /**
     * Код страхового риска
     *
     * @return Код страхового риска
     */



    public String getRiskCode() {
        return riskCode;
    }

    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }


    /**
     * Код страхового риска в АБС
     *
     * @return Код страхового риска в АБС
     */



    public String getAbsRiskCode() {
        return absRiskCode;
    }

    public void setAbsRiskCode(String absRiskCode) {
        this.absRiskCode = absRiskCode;
    }


    /**
     * Дата начала действия записи
     *
     * @return Дата начала действия записи
     */

    @Valid


    public LocalDate getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(LocalDate activeFrom) {
        this.activeFrom = activeFrom;
    }


    /**
     * Дата окончания действия записи
     *
     * @return Дата окончания действия записи
     */

    @Valid


    public LocalDate getActiveTo() {
        return activeTo;
    }

    public void setActiveTo(LocalDate activeTo) {
        this.activeTo = activeTo;
    }


    /**
     * Признак нестандартности
     *
     * @return Признак нестандартности
     */



    public Boolean isNonStandart() {
        return nonStandart;
    }

    public void setNonStandart(Boolean nonStandart) {
        this.nonStandart = nonStandart;
    }


    /**
     * Признак завереения
     *
     * @return Признак завереения
     */



    public Boolean isSure() {
        return sure;
    }

    public void setSure(Boolean sure) {
        this.sure = sure;
    }


    /**
     * Наименование страхового риска
     *
     * @return Наименование страхового риска
     */



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * Признак комбинированного риска
     *
     * @return Признак комбинированного риска
     */



    public Boolean isIsCombined() {
        return isCombined;
    }

    public void setIsCombined(Boolean isCombined) {
        this.isCombined = isCombined;
    }


    public InsuranceProgramRiskObject addCombinedIdsItem(String combinedIdsItem) {
        if (this.combinedIds == null) {
            this.combinedIds = new ArrayList<>();
        }
        this.combinedIds.add(combinedIdsItem);
        return this;
    }

    /**
     * Get combinedIds
     *
     * @return
     */



    public List<String> getCombinedIds() {
        return combinedIds;
    }

    public void setCombinedIds(List<String> combinedIds) {
        this.combinedIds = combinedIds;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InsuranceProgramRiskObject insuranceProgramRiskObject = (InsuranceProgramRiskObject) o;
        return Objects.equals(this.id, insuranceProgramRiskObject.id) &&
                Objects.equals(this.insuranceCode, insuranceProgramRiskObject.insuranceCode) &&
                Objects.equals(this.objectType, insuranceProgramRiskObject.objectType) &&
                Objects.equals(this.riskCode, insuranceProgramRiskObject.riskCode) &&
                Objects.equals(this.absRiskCode, insuranceProgramRiskObject.absRiskCode) &&
                Objects.equals(this.activeFrom, insuranceProgramRiskObject.activeFrom) &&
                Objects.equals(this.activeTo, insuranceProgramRiskObject.activeTo) &&
                Objects.equals(this.nonStandart, insuranceProgramRiskObject.nonStandart) &&
                Objects.equals(this.sure, insuranceProgramRiskObject.sure) &&
                Objects.equals(this.name, insuranceProgramRiskObject.name) &&
                Objects.equals(this.isCombined, insuranceProgramRiskObject.isCombined) &&
                Objects.equals(this.combinedIds, insuranceProgramRiskObject.combinedIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, insuranceCode, objectType, riskCode, absRiskCode, activeFrom, activeTo, nonStandart, sure, name, isCombined, combinedIds);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class InsuranceProgramRiskObject {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    insuranceCode: ").append(toIndentedString(insuranceCode)).append("\n");
        sb.append("    objectType: ").append(toIndentedString(objectType)).append("\n");
        sb.append("    riskCode: ").append(toIndentedString(riskCode)).append("\n");
        sb.append("    absRiskCode: ").append(toIndentedString(absRiskCode)).append("\n");
        sb.append("    activeFrom: ").append(toIndentedString(activeFrom)).append("\n");
        sb.append("    activeTo: ").append(toIndentedString(activeTo)).append("\n");
        sb.append("    nonStandart: ").append(toIndentedString(nonStandart)).append("\n");
        sb.append("    sure: ").append(toIndentedString(sure)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    isCombined: ").append(toIndentedString(isCombined)).append("\n");
        sb.append("    combinedIds: ").append(toIndentedString(combinedIds)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
