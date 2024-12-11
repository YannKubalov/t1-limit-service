package t1.limit.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import t1.limit.entity.UserLimit;
import t1.limit.service.UserLimitService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/limits")
public class UserLimitController {

    private final UserLimitService service;

    @Operation(summary = "Получение текущего лимита для пользователя", description = "Возвращает текущий лимит пользователя")
    @GetMapping("/{userId}")
    public ResponseEntity<UserLimit> getUserLimit(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getOrCreateUserLimit(userId));
    }

    @Operation(summary = "Уменьшение лимита пользователя")
    @PostMapping("/{userId}/reduce")
    public ResponseEntity<String> reduceLimit(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        try{
            service.updateLimit(userId, amount);
        }
        catch (RuntimeException ex){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Лимит уменьшен успешно");
    }

    @Operation(summary = "Восстановление лимита пользователя", description = "Возвращает текущий лимит пользователя")
    @PostMapping("/{userId}/restore")
    public ResponseEntity<String> restoreLimit(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        service.restoreLimit(userId, amount);
        return ResponseEntity.ok("Лимит восстановлен");
    }
}
