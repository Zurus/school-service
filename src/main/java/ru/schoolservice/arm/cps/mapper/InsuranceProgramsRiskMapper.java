package ru.schoolservice.arm.cps.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.schoolservice.arm.cps.entity.InsuranceProgramsRiskEntity;
import ru.schoolservice.arm.cps.model.InsuranceProgramRiskObject;
import ru.schoolservice.arm.cps.model.InsuranceRiskObjectType;

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
}
