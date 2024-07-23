package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.dto.EquipmentAddBindingModel;
import com.finalProject.SoftUniProject.model.entity.Equipment;
import com.finalProject.SoftUniProject.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }


    public boolean addEquipment(EquipmentAddBindingModel equipmentAddBindingModel) {

        boolean existsByName = equipmentRepository.existsByName(equipmentAddBindingModel.getName());
        if (existsByName){
            return false;
        }

        Equipment equipment = new Equipment();

        equipment.setName(equipmentAddBindingModel.getName());
        equipment.setURL(equipmentAddBindingModel.getUrl());
        equipment.setWeight(equipmentAddBindingModel.getWeight());

        equipmentRepository.save(equipment);
        return true;
    }
}
