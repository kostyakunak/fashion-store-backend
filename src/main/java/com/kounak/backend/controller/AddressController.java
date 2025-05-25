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

    @PostMapping
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        try {
            logger.info("[addAddress] Получен Address: {}", address);
            logger.info("[addAddress] userId: {}, isMain: {}, recipientFirstName: {}, recipientLastName: {}", 
                address.getUser() != null ? address.getUser().getId() : null, address.isMain(), address.getRecipientFirstName(), address.getRecipientLastName());
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
            address.setUser(user);
            address.setId(null);
            // isMain уже попадёт из запроса, если фронт отправляет
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
            logger.info("[updateAddress] Получен Address: {}", address);
            logger.info("[updateAddress] userId: {}, isMain: {}, recipientFirstName: {}, recipientLastName: {}", 
                address.getUser() != null ? address.getUser().getId() : null, address.isMain(), address.getRecipientFirstName(), address.getRecipientLastName());
            if (address.getUser() == null || address.getUser().getId() == null) {
                logger.error("Отсутствует ID пользователя в запросе");
                return ResponseEntity.badRequest().build();
            }
            User user = userService.getUserById(address.getUser().getId());
            if (user == null) {
                logger.error("Пользователь с ID {} не найден", address.getUser().getId());
                return ResponseEntity.badRequest().build();
            }
            if (!addressService.addressExists(id)) {
                logger.error("Адрес с ID {} не найден", id);
                return ResponseEntity.notFound().build();
            }
            address.setId(id);
            address.setUser(user);
            // isMain уже попадёт из запроса, если фронт отправляет
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

    @GetMapping("/user/{userId}")
    public List<Address> getAddressesByUser(@PathVariable Long userId) {
        return addressService.getAddressesByUserId(userId);
    }
}