package t1.limit.service;

import t1.limit.entity.UserLimit;

import java.math.BigDecimal;

public interface UserLimitService {

     UserLimit getUserLimit(Long userId);

     UserLimit getOrCreateUserLimit(Long userId);

     void updateLimit(Long userId, BigDecimal amount);

     void resetAllLimits();

     void restoreLimit(Long userId, BigDecimal amount);
}
