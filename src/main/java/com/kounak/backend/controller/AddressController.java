package com.kounak.backend.controller;

import com.kounak.backend.model.Address;
import com.kounak.backend.model.User;
import com.kounak.backend.service.AddressService;
import com.kounak.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/addresses")
public class AddressController {
    private final Logger logger = LoggerFactory.getLogger(AddressController.class);
    private final AddressService addressService;
    private final UserService userService;

    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        try {
            List<Address> addresses = addressService.getAllAddresses();
            logger.info("Получено {} адресов", addresses.size());
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            logger.error("Ошибка при получении адресов: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUser(@PathVariable Long userId) {
        try {
            List<Address> addresses = addressService.getAddressesByUser(userId);
            logger.info("Получено {} адресов для пользователя с ID {}", addresses.size(), userId);
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            logger.error("Ошибка при получении адресов для пользователя {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        try {
            if (address.getUser() == null || address.getUser().getId() == null) {
                logger.error("Отсутствует ID пользователя в запросе");
                return ResponseEntity.badRequest().build();
            }

            // Получаем пользователя из базы
            User user = userService.getUserById(address.getUser().getId());
            if (user == null) {
                logger.error("Пользователь с ID {} не найден", address.getUser().getId());
                return ResponseEntity.badRequest().build();
            }
            
            // Устанавливаем пользователя в адрес
            address.setUser(user);
            
            // Важно: сбрасываем ID, чтобы работала автогенерация
            address.setId(null);
            
            Address savedAddress = addressService.addAddress(address);
            logger.info("Добавлен новый адрес с ID {}", savedAddress.getId());
            return ResponseEntity.ok(savedAddress);
        } catch (Exception e) {
            logger.error("Ошибка при добавлении адреса: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        try {
            if (address.getUser() == null || address.getUser().getId() == null) {
                logger.error("Отсутствует ID пользователя в запросе");
                return ResponseEntity.badRequest().build();
            }

            // Получаем пользователя из базы
            User user = userService.getUserById(address.getUser().getId());
            if (user == null) {
                logger.error("Пользователь с ID {} не найден", address.getUser().getId());
                return ResponseEntity.badRequest().build();
            }
            
            // Убеждаемся, что адрес существует
            if (!addressService.addressExists(id)) {
                logger.error("Адрес с ID {} не найден", id);
                return ResponseEntity.notFound().build();
            }
            
            // Устанавливаем ID и пользователя
            address.setId(id);
            address.setUser(user);
            
            Address updatedAddress = addressService.updateAddress(address);
            logger.info("Обновлен адрес с ID {}", updatedAddress.getId());
            return ResponseEntity.ok(updatedAddress);
        } catch (Exception e) {
            logger.error("Ошибка при обновлении адреса {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        try {
            if (!addressService.addressExists(id)) {
                logger.error("Адрес с ID {} не найден", id);
                return ResponseEntity.notFound().build();
            }
            
            addressService.deleteAddress(id);
            logger.info("Удален адрес с ID {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Ошибка при удалении адреса {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}