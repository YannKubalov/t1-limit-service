package t1.limit.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import t1.limit.service.UserLimitService;

@Component
@AllArgsConstructor
public class UserLimitResetScheduler {

    private final UserLimitService userLimitService;

    @Scheduled(cron = "${scheduler.daily_limit_reset}", zone = "Europe/Moscow")
    public void dailyReset() {
        userLimitService.resetAllLimits();
    }

}
