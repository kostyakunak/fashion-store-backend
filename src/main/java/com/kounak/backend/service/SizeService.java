package com.kounak.backend.service;

import com.kounak.backend.model.Size;
import com.kounak.backend.repository.SizeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeService {

    private final SizeRepository sizeRepository;

    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public Size addSize(Size size) {
        return sizeRepository.save(size);
    }

    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    public void deleteSize(Long id) {
        sizeRepository.deleteById(id);
    }

    // ✅ Добавляем метод для получения размера по ID
    public Size getSizeById(Long id) {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Size not found"));
    }
}