package pe.edu.bikeswap.bike_service.application.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pe.edu.bikeswap.bike_service.domain.model.Bike;
import pe.edu.bikeswap.bike_service.domain.service.BikeService;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/bikes")
public class BicycleController {
    private final BikeService bikeService;

    public BicycleController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    // URL: http://localhost:8080/api/v1/bikes
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<Bike>> getAllBicycles() {
        return new ResponseEntity<List<Bike>>(bikeService.getAllBikes(), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/bikes/{bikeId}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{bikeId}")
    public ResponseEntity<Bike> getBicycleById(@PathVariable(name = "bikeId") Long bicycleId) {
        return new ResponseEntity<Bike>(bikeService.getBikeById(bicycleId), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/bikes/available
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/available")
    public ResponseEntity<List<Bike>> getAllAvailableBicycles(
            @RequestParam(name = "start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
            @RequestParam(name = "end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date
    ) {
        return new ResponseEntity<>(bikeService.getAllAvailableBikes(start_date, end_date), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/bikes/{bikeId}
    // Method: PUT
    @Transactional
    @PutMapping("/{bikeId}")
    public ResponseEntity<Bike> updateBicycleByBicycleId(@PathVariable(name = "bikeId") Long bicycleId, @RequestBody Bike bike) {
        return new ResponseEntity<Bike>(bikeService.updateBike(bicycleId, bike), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/bikes/{bikeId}
    // Method: DELETE
    @Transactional
    @DeleteMapping("/{bikeId}")
    public ResponseEntity<String> deleteBicycleByBicycleId(@PathVariable(name = "bikeId") Long bicycleId) {
        return new ResponseEntity<String>("Bicicleta eliminada correctamente", HttpStatus.OK);
    }
}
