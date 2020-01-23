package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import com.space.util.ShipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.space.util.ShipUtils.countRating;

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
        ship.setRating(countRating(ship));
        return repository.save(ship);
    }

    public Ship update(long id, Ship ship) {
        ship.setId(id);
        ship.setRating(countRating(ship));
        return repository.save(ship);
    }

    public Ship get(long id) {
        return repository.findById(id).orElse(null);
    }
}
