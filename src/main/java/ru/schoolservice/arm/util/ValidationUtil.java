package ru.schoolservice.arm.util;

import lombok.experimental.UtilityClass;
import ru.schoolservice.arm.error.IllegalRequestDataException;
import ru.schoolservice.arm.model.BaseEntity;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(BaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalRequestDataException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(BaseEntity entity, int id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.id() != id) {
            throw new IllegalRequestDataException(entity + " must has id=" + id);
        }
    }
}
