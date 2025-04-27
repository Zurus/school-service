package ru.schoolservice.arm.cps.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.schoolservice.arm.cps.entity.InsuranceCreditRiskEntity;
import ru.schoolservice.arm.cps.entity.InsuranceProgramsRiskEntity;
import ru.schoolservice.arm.cps.model.InsuranceProgramRiskObject;
import ru.schoolservice.arm.cps.model.InsuranceRiskObjectType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface InsuranceProgramsRiskMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "activeFrom", source = "activeFrom")
    @Mapping(target = "activeTo", source = "activeTo")
    @Mapping(source = "entity", target = "insuranceCode", qualifiedByName = "mapProgramCode")
    @Mapping(source = "entity", target = "objectType", qualifiedByName = "mapRiskObjectType")
    @Mapping(source = "entity", target = "absRiskCode", qualifiedByName = "mapAbsRiskCode")
    @Mapping(source = "entity", target = "name", qualifiedByName = "mapRiskName")
    @Mapping(source = "entity", target = "riskCode", qualifiedByName = "mapRiskCode")
    @Mapping(target = "nonStandart", source = "nonStandart")
    @Mapping(target = "sure", source = "sure")
    @Mapping(target = "isCombined", source = "risk.combined")
    @Mapping(source = "entity", target = "combinedIds", qualifiedByName = "mapCombinedIds")
    InsuranceProgramRiskObject toDto(InsuranceProgramsRiskEntity entity);

    @Named("mapProgramCode")
    default String mapProgramCode(InsuranceProgramsRiskEntity entity) {
        return entity.getProgram() != null ? entity.getProgram().getCode() : null;
    }

    @Named("mapRiskObjectType")
    default InsuranceRiskObjectType mapRiskObjectType(InsuranceProgramsRiskEntity entity) {
        return entity.getRisk() != null ? entity.getRisk().getObjectType() : null;
    }

    @Named("mapRiskCode")
    default String mapRiskCode(InsuranceProgramsRiskEntity entity) {
        return entity.getRisk() != null ? entity.getRisk().getCode() : null;
    }

    @Named("mapAbsRiskCode")
    default String mapAbsRiskCode(InsuranceProgramsRiskEntity entity) {
        return entity.getRisk() != null ? entity.getRisk().getAbsRiskCode() : null;
    }

    @Named("mapRiskName")
    default String mapRiskName(InsuranceProgramsRiskEntity entity) {
        return entity.getRisk() != null ? entity.getRisk().getName() : null;
    }

    @Named("mapCombinedIds")
    default List<String> mapCombinedIds(InsuranceProgramsRiskEntity entity) {
        if (entity.getRisk() == null) return Collections.emptyList();

        InsuranceCreditRiskEntity risk = entity.getRisk();
        if (!Boolean.TRUE.equals(risk.getCombined())) return Collections.emptyList();

        return risk.getCombinedRisks().stream()
                .map(InsuranceCreditRiskEntity::getCode)
                .collect(Collectors.toList());
    }
}
