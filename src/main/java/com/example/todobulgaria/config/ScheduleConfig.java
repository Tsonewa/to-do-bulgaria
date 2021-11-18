package com.example.todobulgaria.config;

import com.example.todobulgaria.services.TripEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class ScheduleConfig {

    Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);
    private final TripEntityService tripEntityService;

    public ScheduleConfig(TripEntityService tripEntityService) {
        this.tripEntityService = tripEntityService;
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void tripsCountSchedule(){

        Integer tripsCount = tripEntityService.tripsCount();

        logger.info("Current trips count is: {}" , tripsCount);

    }
}
