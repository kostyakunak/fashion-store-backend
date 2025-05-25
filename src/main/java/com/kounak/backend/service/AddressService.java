package com.kounak.backend.service;

import com.kounak.backend.model.Address;
import com.kounak.backend.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private final Logger logger = LoggerFactory.getLogger(AddressService.class);
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddresses() {
        logger.info("Получение всех адресов");
        return addressRepository.findAll();
    }

    public Address addAddress(Address address) {
        logger.info("Добавление нового адреса");
        if (address.isMain()) {
            setMainAddress(address);
            return addressRepository.save(address);
        }
        return addressRepository.save(address);
    }

    public List<Address> getAddressesByUser(Long userId) {
        logger.info("Получение адресов для пользователя с ID: {}", userId);
        return addressRepository.findByUserId(userId);
    }

    public Address updateAddress(Address address) {
        logger.info("Обновление адреса с ID: {}", address.getId());
        if (address.isMain()) {
            setMainAddress(address);
            return addressRepository.save(address);
        }
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        logger.info("Удаление адреса с ID: {}", id);
        addressRepository.deleteById(id);
    }

    public boolean addressExists(Long id) {
        return addressRepository.existsById(id);
    }

    public Optional<Address> getAddressById(Long id) {
        logger.info("Получение адреса по ID: {}", id);
        return addressRepository.findById(id);
    }

    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    /**
     * Сбрасывает isMain у всех других адресов пользователя
     */
    public void setMainAddress(Address address) {
        List<Address> addresses = addressRepository.findByUserId(address.getUser().getId());
        for (Address a : addresses) {
            if (!a.getId().equals(address.getId()) && a.isMain()) {
                a.setIsMain(false);
                addressRepository.save(a);
            }
        }
    }
}