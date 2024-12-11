package t1.limit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import t1.limit.entity.UserLimit;
import t1.limit.repository.UserLimitRepository;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class UserLimitServiceImpl implements UserLimitService {

    private final UserLimitRepository repository;
    private final BigDecimal STANDARD_LIMIT = new BigDecimal("10000.00");

    @Override
    public UserLimit getOrCreateUserLimit(Long userId) {
        return repository.findByUserId(userId)
                .orElseGet(() -> {
                    UserLimit newUserLimit = new UserLimit();
                    newUserLimit.setUserId(userId);
                    newUserLimit.setDailyLimit(STANDARD_LIMIT);
                    return repository.save(newUserLimit);
                });
    }

    @Override
    public UserLimit getUserLimit(Long userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    @Override
    public void updateLimit(Long userId, BigDecimal amount) {
        UserLimit userLimit = getUserLimit(userId);
        userLimit.setDailyLimit(userLimit.getDailyLimit().subtract(amount));
        repository.save(userLimit);
    }

    @Override
    public void resetAllLimits() {
        repository.findAll().forEach(limit -> {
            limit.setDailyLimit(STANDARD_LIMIT);
            repository.save(limit);
        });
    }

    @Override
    public void restoreLimit(Long userId, BigDecimal amount) {
        UserLimit userLimit = getUserLimit(userId);
        userLimit.setDailyLimit(userLimit.getDailyLimit().add(amount));
        repository.save(userLimit);
    }
}