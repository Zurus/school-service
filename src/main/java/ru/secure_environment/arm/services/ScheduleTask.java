package ru.secure_environment.arm.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@Slf4j
@AllArgsConstructor
public class ScheduleTask {

    private final EventService eventService;
    public final static long TESTNG_TEST_TIMEOUT = 2678400000L;
    //TimeUnit.HOURS.toHours(24);
    //Duration.ofDays(31).toMillis();

    @Scheduled(fixedRate = TESTNG_TEST_TIMEOUT)
    public void clearEventTable() {
        log.info("automatic clearing table <events>");
        //https://stackoverflow.com/questions/16392892/how-to-reduce-one-month-from-current-date-and-stored-in-date-variable-using-java
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        eventService.clearEvents(cal.getTime());
    }
}
