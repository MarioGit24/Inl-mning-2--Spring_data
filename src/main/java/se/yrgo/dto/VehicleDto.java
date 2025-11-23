package se.yrgo.dto;

public record VehicleDto(
    Long VehicleId, 
    String registrationNumber, 
    String brand, String model, 
    int productionYear
) {}
