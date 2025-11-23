package se.yrgo.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import se.yrgo.data.CustomerRepository;
import se.yrgo.data.VehicleRepository;
import se.yrgo.dto.CustomerDto;
import se.yrgo.dto.VehicleDto;
import se.yrgo.entity.Customer;
import se.yrgo.entity.Vehicle;





@RestController
@RequestMapping("/")
public class VehicleCustomerController {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    public VehicleCustomerController(
        VehicleRepository vehicleRepository, 
        CustomerRepository customerRepository
    ) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
    }

    private VehicleDto mapToDto(Vehicle vehicle) {
        return new VehicleDto(
            vehicle.getVehicleId(),
            vehicle.getRegistrationNumber(),
            vehicle.getBrand(),
            vehicle.getModel(),
            vehicle.getProductionYear()
        );
    }

    private CustomerDto mapToDto(Customer customer) {
        List<VehicleDto> vehicleDtos = customer.getVehicles().stream()
            .map(this::mapToDto)
            .toList();

            return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getPhoneNumber(),
                vehicleDtos
            );
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerDto> createCustomer(
        @RequestParam String name, 
        @RequestParam int phoneNumber)
    {
        Customer newCustomer = new Customer(name, phoneNumber);
        Customer savedCustomer = customerRepository.save(newCustomer);
        
        return ResponseEntity.ok(mapToDto(savedCustomer));
    }

    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomersWithVehicles() {
        return customerRepository.findAllWithVehicles().stream()
            .map(this::mapToDto)
            .toList();
    }

    @GetMapping("/customers-id")
    public ResponseEntity<Long> getCustomerIdByName(
        @RequestParam String name)
        {
        return customerRepository.findByName(name)
            .map(customer -> ResponseEntity.ok(customer.getId()))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/vehicles")
    public List<VehicleDto> getAllVehicles() {
        return vehicleRepository.findAll().stream()
            .map(this::mapToDto)
            .toList();
    }

    @GetMapping("/vehicles-by-brand")
    public List<VehicleDto> getVehiclesByBrand(
        @RequestParam String brand)
        {
        return vehicleRepository.findByBrand(brand).stream()
            .map(this::mapToDto)
            .toList();

    }
    
    @PostMapping("/vehicles")
public ResponseEntity<VehicleDto> createVehicle(
    @RequestParam String registrationNumber,
    @RequestParam String brand,
    @RequestParam String model,
    @RequestParam int productionYear) 
{
    Vehicle newVehicle = new Vehicle(registrationNumber, brand, model, productionYear);
    
    Vehicle savedVehicle = vehicleRepository.save(newVehicle);
    
    return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(savedVehicle));
}



    @PostMapping("customers/{customerId}/vehicles/{vehicleId}")
    public ResponseEntity<Void> linkVehicleToCustomer(
        @PathVariable Long customerId, 
        @PathVariable Long vehicleId)
        {
           Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, 
            "Customer with ID " + customerId + " not found"
        ));
        
    Vehicle vehicle = vehicleRepository.findById(vehicleId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, 
            "Vehicle with ID " + vehicleId + " not found"
        ));
        
        vehicle.setCustomer(customer);
        vehicleRepository.save(vehicle);

        return ResponseEntity.ok().build();
    
    }
    




    



    



    


    
}
