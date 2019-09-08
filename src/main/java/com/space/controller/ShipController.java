package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ShipController {

    @Autowired
    private ShipService shipService;

    @GetMapping(value = "rest/ships")
    public ResponseEntity<List<Ship>> getShips(@RequestParam(value = "pageNumber") Optional<Integer> page,
                                               @RequestParam(value = "pageSize")   Optional<Integer> pageSize,
                                               @RequestParam(value = "order")      Optional<ShipOrder> shipOrder,
                                               @RequestParam(value = "name")       Optional<String> name,
                                               @RequestParam(value = "planet")     Optional<String> planet,
                                               @RequestParam(value = "shipType")   Optional<ShipType> shipType,
                                               @RequestParam(value = "after")      Optional<Long> after,
                                               @RequestParam(value = "before")     Optional<Long> before,
                                               @RequestParam(value = "minSpeed")   Optional<Double> minSpeed,
                                               @RequestParam(value = "maxSpeed")   Optional<Double> maxSpeed,
                                               @RequestParam(value = "minCrewSize")Optional<Integer> minCrewSize,
                                               @RequestParam(value = "maxCrewSize")Optional<Integer> maxCrewSize,
                                               @RequestParam(value = "isUsed")     Optional<Boolean> isUsed,
                                               @RequestParam(value = "minRating")  Optional<Double> minRating,
                                               @RequestParam(value = "maxRating")  Optional<Double> maxRating) {
        String order = shipOrder.isPresent() ? shipOrder.get().getFieldName() : "id";
        Pageable pageable = new PageRequest(page.orElse(0), pageSize.orElse(3), Sort.by(order));
        return new ResponseEntity<>(shipService.getListOfShips(name, planet, shipType, after,
                                                                before, minSpeed, maxSpeed, minCrewSize, maxCrewSize,
                                                                isUsed, minRating, maxRating, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "rest/ships/count")
    public ResponseEntity<Integer> getAmountOfShips(@RequestParam(value = "pageNumber") Optional<Integer> page,
                                                 @RequestParam(value = "pageSize")   Optional<Integer> pageSize,
                                                 @RequestParam(value = "order")      Optional<ShipOrder> shipOrder,
                                                 @RequestParam(value = "name")       Optional<String> name,
                                                 @RequestParam(value = "planet")     Optional<String> planet,
                                                 @RequestParam(value = "shipType")   Optional<ShipType> shipType,
                                                 @RequestParam(value = "after")      Optional<Long> after,
                                                 @RequestParam(value = "before")     Optional<Long> before,
                                                 @RequestParam(value = "minSpeed")   Optional<Double> minSpeed,
                                                 @RequestParam(value = "maxSpeed")   Optional<Double> maxSpeed,
                                                 @RequestParam(value = "minCrewSize")Optional<Integer> minCrewSize,
                                                 @RequestParam(value = "maxCrewSize")Optional<Integer> maxCrewSize,
                                                 @RequestParam(value = "isUsed")     Optional<Boolean> isUsed,
                                                 @RequestParam(value = "minRating")  Optional<Double> minRating,
                                                 @RequestParam(value = "maxRating")  Optional<Double> maxRating) {
        String order = shipOrder.isPresent() ? shipOrder.get().getFieldName() : "id";
        return new ResponseEntity<>(shipService.getListOfShips(name, planet, shipType, after,
                before, minSpeed, maxSpeed, minCrewSize, maxCrewSize,
                isUsed, minRating, maxRating, null).size(), HttpStatus.OK);
    }

    @GetMapping(value = "rest/ships/{id}")
    public ResponseEntity<Ship> getShipById(@PathVariable long id) {
        if (id == 0)                     return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        if (!shipService.existsById(id)) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(shipService.get(id), HttpStatus.OK);
    }

    @PostMapping(value = "rest/ships/"  , consumes = "application/json", produces = "application/json")
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship, @RequestParam(value = "isUsed") Optional<Boolean> isUsed) {
        if (!shipService.checkDataForNull(ship))  return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        if (!shipService.checkForValidData(ship)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        isUsed.ifPresent(ship::setUsed);
        return new ResponseEntity<>(shipService.save(ship), HttpStatus.OK);
    }

    @PostMapping(value = "rest/ships/{id}", produces = "application/json")
    public ResponseEntity<Ship> updateShip(@PathVariable long id, @RequestBody Ship ship) {
        if (ship.getName() == null && ship.getPlanet() == null) return new ResponseEntity<>(shipService.get(id), HttpStatus.OK);
        if (id == 0)                                            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        if (!shipService.existsById(id))                        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        if (!shipService.checkDataForNull(ship))                return new ResponseEntity<>(shipService.get(id), HttpStatus.BAD_REQUEST);
        Ship updatedShip = shipService.update(ship, id);
        if (!shipService.checkForValidData(updatedShip))        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(shipService.save(updatedShip), HttpStatus.OK);
    }

    @DeleteMapping("rest/ships/{id}")
    public ResponseEntity deleteShip(@PathVariable long id) {
        if (id > shipService.count()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        if (id == 0)                  return new ResponseEntity(HttpStatus.BAD_REQUEST);

        shipService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
