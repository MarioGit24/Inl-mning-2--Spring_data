package se.yrgo.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import se.yrgo.entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT v FROM Vehicle v WHERE v.customer.id = :customerid")
    List<Vehicle> findByCustomerId(Long customerId);

    @Query("SELECT v FROM Vehicle v WHERE v.brand = :brand")
    List<Vehicle> findByBrand(String brand);



}
