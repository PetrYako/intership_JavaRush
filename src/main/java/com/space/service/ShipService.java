package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import com.space.util.ShipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.space.util.ShipUtils.countRating;

@Service
public class ShipService {

    private final ShipRepository repository;

    private long amountOfShips;

    @Autowired
    public ShipService(ShipRepository repository) {
        this.repository = repository;
    }

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

    public long count() { return amountOfShips; }

    public Ship get(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Ship> getAllWithCriteria(Pageable pageable,             Optional<String> name,      Optional<String> planet,    Optional<LocalDate> after,
                                         Optional<LocalDate> before,    Optional<Double> minSpeed,  Optional<Double> maxSpeed,  Optional<Integer> minCrewSize,
                                         Optional<Integer> maxCrewSize, Optional<Double> minRating, Optional<Double> maxRating, Optional<ShipType> shipType,
                                         Optional<Boolean> isUsed) {

        Specification<Ship> specification = (Specification<Ship>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            name.ifPresent(n ->         predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("name"), n))));
            planet.ifPresent(p ->       predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("planet"), p))));
            after.ifPresent(ad ->       predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"), ad))));
            before.ifPresent(bd ->      predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("prodDate"), bd))));
            minSpeed.ifPresent(ms ->    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("speed"), ms))));
            maxSpeed.ifPresent(ms ->    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("speed"), ms))));
            minCrewSize.ifPresent(mc -> predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"), mc))));
            maxCrewSize.ifPresent(mc -> predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"), mc))));
            minRating.ifPresent(mr ->   predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), mr))));
            maxRating.ifPresent(mr ->   predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("rating"), mr))));
            shipType.ifPresent(st ->    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("shipType"), st))));
            isUsed.ifPresent(i ->       predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("isUsed"), i))));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        setAmountOfShips(repository.count(specification));
        return repository.findAll(specification, pageable).getContent();
    }

    private void setAmountOfShips(long amount) { this.amountOfShips = amount; }

}
