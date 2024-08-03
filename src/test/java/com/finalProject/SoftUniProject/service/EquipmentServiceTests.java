package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.dto.EquipmentAddBindingModel;
import com.finalProject.SoftUniProject.model.entity.Equipment;
import com.finalProject.SoftUniProject.repository.EquipmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EquipmentServiceTests {

    private EquipmentService toTest;

    @Mock
    private EquipmentRepository mockEquipmentRepository;

    @Captor
    private ArgumentCaptor<Equipment> equipmentArgumentCaptor;

    @BeforeEach
    void setUp(){
        toTest = new EquipmentService(mockEquipmentRepository);
    }

    @Test
    void testAddEquipmentSuccessful(){
        EquipmentAddBindingModel equipmentAddBindingModel = new EquipmentAddBindingModel();
        equipmentAddBindingModel.setName("test Equipment");
        equipmentAddBindingModel.setWeight(10);
        equipmentAddBindingModel.setPhotoUrl("https://picsum.photos/200");

        toTest.addEquipment(equipmentAddBindingModel);
        verify(mockEquipmentRepository).save(equipmentArgumentCaptor.capture());
        Equipment equipment = equipmentArgumentCaptor.getValue();

        Assertions.assertNotNull(equipment);
        Assertions.assertEquals(equipmentAddBindingModel.getName(), equipment.getName());
        Assertions.assertEquals(equipmentAddBindingModel.getWeight(), equipment.getWeight());
        Assertions.assertEquals(equipmentAddBindingModel.getPhotoUrl(), equipment.getPhotoUrl());

    }

    @Test
    void testAddEquipmentAlreadyExists(){
        EquipmentAddBindingModel equipmentAddBindingModel = new EquipmentAddBindingModel();
        equipmentAddBindingModel.setName("test Equipment");
        equipmentAddBindingModel.setWeight(10);
        equipmentAddBindingModel.setPhotoUrl("https://picsum.photos/200");

        when(mockEquipmentRepository.existsByName("test Equipment")).thenReturn(true);

        boolean isExisting = toTest.addEquipment(equipmentAddBindingModel);

       Assertions.assertFalse(isExisting);

    }

    @Test
    void testGetAllEquipment(){
        Equipment equipmentOne = new Equipment();
        Equipment equipmentTwo = new Equipment();
        List<Equipment> equipment = Arrays.asList(equipmentOne, equipmentTwo);

        when(mockEquipmentRepository.findAll()).thenReturn(equipment);
        List<Equipment> allEquipment = toTest.getAllEquipment();
        Assertions.assertEquals(equipment, allEquipment);
    }
}
