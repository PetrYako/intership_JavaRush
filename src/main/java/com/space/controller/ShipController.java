package com.space.controller;

import com.space.model.Ship;
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
import java.util.Date;
import java.util.List;

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


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Ship> getAll() {
       return service.getAll(PageRequest.of(0, 3, Sort.by("id")));
    }

    @GetMapping(params = {"pageNumber", "pageSize", "order"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Ship> getAllPagination(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize,
                                       @RequestParam("order") ShipOrder order) {

        Sort.Direction sort = order.getFieldName().equals("id") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return service.getAll(PageRequest.of(pageNumber, pageSize, sort, order.getFieldName()));
    }

    @GetMapping(value = "/count")
    public long getCount() {
        return service.count();
    }

    @GetMapping(value = "/count", params = {"pageNumber", "pageSize", "order"})
    public long getCountPagination(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize,
                                   @RequestParam("order") String order) {

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
