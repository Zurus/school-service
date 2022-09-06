package ru.secure_environment.arm.util.validation;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import ru.secure_environment.arm.error.IllegalRequestDataException;
import ru.secure_environment.arm.model.BaseEntity;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.model.common.HasId;

import java.util.Objects;

import static ru.secure_environment.arm.util.ExceptionTextUtil.idWasNotFound;

@UtilityClass
public class ValidationUtil {

    public static void userVerification(User user) {
        if (user.getIsEmployee() && (StringUtils.isEmpty(user.getJobTitle()) || Objects.nonNull(user.getJobTitle()))) {
            throw new IllegalRequestDataException("for employee '" + user + "' job title must not be null or empty ");
        }

        if (user.getIsEmployee() && (StringUtils.isEmpty(user.getClassNumber()) || Objects.nonNull(user.getClassNumber()))) {
            throw new IllegalRequestDataException("for student '" + user + "' classNumber must not be null or empty");
        }
    }

    public static void checkNew(BaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalRequestDataException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId entity, int id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.id() != id) {
            throw new IllegalRequestDataException(entity + " must has id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new IllegalRequestDataException(idWasNotFound(id));
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}
