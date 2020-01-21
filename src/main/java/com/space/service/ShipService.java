package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ShipService {

    private final ShipRepository repository;

    @Autowired
    public ShipService(ShipRepository repository) {
        this.repository = repository;
    }

    public List<Ship> getAll(Pageable pageable) {
        return repository.getAll(pageable);
    }

    public long count() { return repository.count(); }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public Ship create(Ship ship) {
        return repository.save(ship);
    }

    public Ship update(Ship ship) {
        return repository.save(ship);
    }

    public Ship get(long id) {
        return repository.findById(id).orElse(null);
    }
}
