package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Transactional
public class ShipService {

    @Autowired
    private ShipRepository repository;

    public List<Ship> filterByIsUsedMinMaxSpeed(boolean isUsed, double minSpeed, double maxSpeed, Pageable pageable) {
        return repository.getAllWithFiltersIsUsedMinMaxSpeed(isUsed, minSpeed, maxSpeed, pageable);
    }

    public List<Ship> filterByShipTypeBeforeMaxSpeed(ShipType shipType, Date before, double maxSpeed, Pageable pageable) {
        return repository.getAllWithFiltersShipTypeBeforeMaxSpeed(shipType, before, maxSpeed, pageable);
    }

    public List<Ship> filterByShipTypeMaxCrewSize(ShipType shipType, int maxCrewSize, Pageable pageable) {
        return repository.getAllWithFiltersShipTypeMaxCrewSize(shipType, maxCrewSize, pageable);
    }

    public List<Ship> filterByShipTypeIsUsed(ShipType shipType, boolean isUsed, Pageable pageable) {
        return repository.getAllWithFiltersShipTypeIsUsed(shipType, isUsed, pageable);
    }

    public List<Ship> filterByNameAfterMaxRating(String name, Date after, double maxRating, Pageable pageable) {
        return repository.getAllWithFiltersNameAfterMaxRating(name, after, maxRating, pageable);
    }

    public List<Ship> filterByMinRatingMinCrewSizeMinSpeed(double minRating, int minCrewSize, double minSpeed, Pageable pageable) {
        return repository.getAllWithFiltersMinRatingMinCrewSizeMinSpeed(minRating, minCrewSize, minSpeed, pageable);
    }

    public List<Ship> filterByAfterBeforeMinCrewMaxCrew(Date after, Date before, int minCrewSize, int maxCrewSize, Pageable pageable) {
        return repository.getAllWithFiltersAfterBeforeMinCrewMaxCrew(after, before, minCrewSize, maxCrewSize, pageable);
    }

    public List<Ship> filterByIsUsedMaxSpeedMaxRating(boolean isUsed, double maxSpeed, double maxRating, Pageable pageable) {
        return repository.getAllWithFiltersIsUsedMaxSpeedMaxRating(isUsed, maxSpeed, maxRating, pageable);
    }

    public List<Ship> filterByIsUsedMinMaxRating(boolean isUsed, double minRating, double maxRating, Pageable pageable) {
        return repository.getAllWithFiltersIsUsedMinMaxRating(isUsed, minRating, maxRating, pageable);
    }

    public List<Ship> filterByShipTypeMinCrewSizeMaxCrewSize(ShipType shipType, int minCrewSize, int maxCrewSize, Pageable pageable) {
        return repository.getAllWithFiltersShipTypeMinCrewSizeMaxCrewSize(shipType, minCrewSize, maxCrewSize, pageable);
    }

    public List<Ship> filterByShipTypeMinSpeedMaxSpeed(ShipType shipType, double minSpeed, double maxSpeed, Pageable pageable) {
        return repository.getAllWithFiltersShipTypeMinSpeedMaxSpeed(shipType, minSpeed, maxSpeed, pageable);
    }

    public List<Ship> filterByShipTypeAfterBefore(ShipType shipType, Date after, Date before, Pageable pageable) {
        return repository.getAllWithFiltersShipTypeAfterBefore(shipType, after, before, pageable);
    }

    public List<Ship> filterByNameAndPage(String name, Pageable pageable) {
        return repository.getAllWithFiltersNamePageNumber(name, pageable);
    }

    public List<Ship> filterByPlanetAndPage(String planet, Pageable pageable) {
        return repository.getAllWithFiltersPlanetPageSize(planet, pageable);
    }

    public List<Ship> list(Pageable pageable) {
        if (pageable == null)
            return repository.getAll();
       return repository.findAll(pageable).getContent();
    }

    public Ship save(Ship ship) {
        ship.setRating(countRating(ship));
        repository.save(ship);
        return ship;
    }

    public Ship update(Ship ship, long id) {
        Ship oldShip = get(id);
        ship.setId(id);
        if (ship.getName() == null)
            ship.setName(oldShip.getName());
        if (ship.getPlanet() == null)
            ship.setPlanet(oldShip.getPlanet());
        if (ship.getSpeed() == 0.0)
            ship.setSpeed(oldShip.getSpeed());
        if (ship.getProdDate() == null)
            ship.setProdDate(oldShip.getProdDate());
        if (ship.getCrewSize() == 0)
            ship.setCrewSize(oldShip.getCrewSize());
        if (ship.getShipType() == null)
            ship.setShipType(oldShip.getShipType());
        return ship;
    }

    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    public Ship get(long id) {
     return repository.findById(id).get();
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }

    private double countRating(Ship ship) {
        Date date;
        if (ship.getProdDate() == null)
            date = get(ship.getId()).getProdDate();
        else
            date = ship.getProdDate();

        double speed;
        if (ship.getSpeed() == 0.0)
            speed = get(ship.getId()).getSpeed();
        else
            speed = ship.getSpeed();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

        double n1 = 80 * speed * (ship.isUsed() ? 0.5 : 1);
        double n2 = 3019 - year + 1;

        BigDecimal bd = new BigDecimal(Double.toString(n1/n2));
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    public boolean checkForValidData(Ship ship) {
        if (ship.getName().isEmpty() || ship.getSpeed() == 0 || ship.getYear() < 0 || ship.getPlanet().isEmpty() ||
                ship.getCrewSize() == 0 || ship.getShipType().name().isEmpty())
            return false;

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(ship.getProdDate());
        int year = calendar.get(Calendar.YEAR);

        if (ship.getName().length() > 50 || ship.getPlanet().length() > 50) return false;
        if (year < 2800 || year > 3019)                                     return false;
        if (ship.getSpeed() < 0.01 || ship.getSpeed() > 0.99)               return false;
        if (ship.getCrewSize() < 1 || ship.getCrewSize() > 9999)            return false;

        return true;
    }

    public boolean checkDataForNull(Ship ship) {
        return ship.getName() != null || ship.getPlanet() != null || ship.getShipType() != null || ship.getProdDate() != null ||
                ship.getSpeed() != 0 || ship.getCrewSize() != 0;
    }

    public List<Ship> getListOfShips(Optional<String> name, Optional<String> planet, Optional<ShipType> shipType, Optional<Long> after, Optional<Long> before,
                                     Optional<Double> minSpeed, Optional<Double> maxSpeed, Optional<Integer> minCrewSize, Optional<Integer> maxCrewSize,
                                     Optional<Boolean> isUsed, Optional<Double> minRating, Optional<Double> maxRating, Pageable pageable) {

        List<Ship> shipList;

        if (after.isPresent() && before.isPresent() && minCrewSize.isPresent() && maxCrewSize.isPresent())
            shipList = filterByAfterBeforeMinCrewMaxCrew(new Date(after.get()), new Date(before.get()), minCrewSize.get(), maxCrewSize.get(), pageable);
        else if (name.isPresent() && after.isPresent() && maxRating.isPresent())
            shipList = filterByNameAfterMaxRating(name.get(), new Date(after.get()), maxRating.get(), pageable);
        else if (shipType.isPresent() && after.isPresent() && before.isPresent())
            shipList = filterByShipTypeAfterBefore(shipType.get(), new Date(after.get()), new Date(before.get()), pageable);
        else if (shipType.isPresent() && minSpeed.isPresent() && maxSpeed.isPresent())
            shipList = filterByShipTypeMinSpeedMaxSpeed(shipType.get(), minSpeed.get(), maxSpeed.get(), pageable);
        else if (shipType.isPresent() && minCrewSize.isPresent() && maxCrewSize.isPresent())
            shipList = filterByShipTypeMinCrewSizeMaxCrewSize(shipType.get(), minCrewSize.get(), maxCrewSize.get(), pageable);
        else if (isUsed.isPresent() && minRating.isPresent() && maxRating.isPresent())
            shipList = filterByIsUsedMinMaxRating(isUsed.get(), minRating.get(), maxRating.get(), pageable);
        else if (isUsed.isPresent() && maxSpeed.isPresent() && maxRating.isPresent())
            shipList = filterByIsUsedMaxSpeedMaxRating(isUsed.get(), maxSpeed.get(), maxRating.get(), pageable);
        else if (minRating.isPresent() && minCrewSize.isPresent() && minSpeed.isPresent())
            shipList = filterByMinRatingMinCrewSizeMinSpeed(minRating.get(), minCrewSize.get(), minSpeed.get(), pageable);
        else if (shipType.isPresent() && before.isPresent() && maxSpeed.isPresent())
            shipList = filterByShipTypeBeforeMaxSpeed(shipType.get(), new Date(before.get()), maxSpeed.get(), pageable);
        else if (isUsed.isPresent() && minSpeed.isPresent() && maxSpeed.isPresent())
            shipList = filterByIsUsedMinMaxSpeed(isUsed.get(), minSpeed.get(), maxSpeed.get(), pageable);
        else if (shipType.isPresent() && isUsed.isPresent())
            shipList = filterByShipTypeIsUsed(shipType.get(), isUsed.get(), pageable);
        else if (shipType.isPresent() && maxCrewSize.isPresent())
            shipList = filterByShipTypeMaxCrewSize(shipType.get(), maxCrewSize.get(), pageable);
        else if (name.isPresent())
            shipList = filterByNameAndPage(name.get(), pageable);
        else if (planet.isPresent())
            shipList = filterByPlanetAndPage(planet.get(), pageable);
        else
            shipList = list(pageable);

        return shipList;
    }

}
