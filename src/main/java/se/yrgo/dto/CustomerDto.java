package se.yrgo.dto;

import java.util.List;

public record CustomerDto(
    Long id, 
    String name, 
    int phoneNumber,
    List<VehicleDto> vehicles
) {}
