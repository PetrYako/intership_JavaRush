package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rest/ships")
public class ShipController {


    private final ShipService service;

    @Autowired
    public ShipController(ShipService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Ship get(@PathVariable long id) {
        return service.get(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize", "order"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Ship> getAllWithCriteria(@RequestParam("pageNumber")  Optional<Integer> pageNumber,  @RequestParam("pageSize")     Optional<Integer> pageSize,
                                         @RequestParam("order")       Optional<ShipOrder> order,     @RequestParam("name")         Optional<String> name,
                                         @RequestParam("planet")      Optional<String> planet,       @RequestParam("after")        Optional<LocalDate> after,
                                         @RequestParam("before")      Optional<LocalDate> before,    @RequestParam("minSpeed")     Optional<Double> minSpeed,
                                         @RequestParam("maxSpeed")    Optional<Double> maxSpeed,     @RequestParam("minCrewSize")  Optional<Integer> minCrewSize,
                                         @RequestParam("maxCrewSize") Optional<Integer> maxCrewSize, @RequestParam("minRating")    Optional<Double> minRating,
                                         @RequestParam("maxRating")   Optional<Double> maxRating,    @RequestParam("shipType")     Optional<ShipType> shipType,
                                         @RequestParam("isUsed")      Optional<Boolean> isUsed) {

        Pageable pageable;
        if (!pageNumber.isPresent() && !order.isPresent() && !pageSize.isPresent()) {
            pageable = PageRequest.of(0, 3, Sort.Direction.ASC, "id");
            return service.getAllWithCriteria(pageable, name, planet, after, before, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, shipType, isUsed);
        }
        Sort.Direction sort = order.get().getFieldName().equals("id") ? Sort.Direction.ASC : Sort.Direction.DESC;
        pageable = PageRequest.of(pageNumber.get(), pageSize.get(), sort, order.get().getFieldName());
        return service.getAllWithCriteria(pageable, name, planet, after, before, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, shipType, isUsed);
    }

    @GetMapping(value = "/count")
    public long getCountWithCriteria(@RequestParam Map<String, String> parameters) {
        return service.count();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@Validated @RequestBody Ship ship) {
        service.create(ship);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable long id, @Validated @RequestBody Ship ship) {
        service.update(id, ship);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
