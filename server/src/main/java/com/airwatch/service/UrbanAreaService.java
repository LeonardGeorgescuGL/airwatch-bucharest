package com.airwatch.service;

import com.airwatch.model.UrbanArea;
import com.airwatch.repository.UrbanAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UrbanAreaService {

    @Autowired
    private UrbanAreaRepository urbanAreaRepository;

    public List<UrbanArea> getAllUrbanAreas() {
        return urbanAreaRepository.findAll();
    }

    public UrbanArea getUrbanAreaById(Integer id) {
        return urbanAreaRepository.findById(id).orElse(null);
    }

    public UrbanArea saveUrbanArea(UrbanArea urbanArea) {
        return urbanAreaRepository.save(urbanArea);
    }

    public UrbanArea updateUrbanArea(Integer id, UrbanArea updated) {
        UrbanArea existing = urbanAreaRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setName(updated.getName());
        existing.setLat(updated.getLat());
        existing.setLng(updated.getLng());
        existing.setSensorsCount(updated.getSensorsCount());
        return urbanAreaRepository.save(existing);
    }

    public void deleteUrbanArea(Integer id) {
        urbanAreaRepository.deleteById(id);
    }
}
