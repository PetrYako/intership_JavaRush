package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShipRepository extends PagingAndSortingRepository<Ship, Long> {

    Page<Ship> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM ship", nativeQuery = true)
    List<Ship> getAll();

    @Query("SELECT s FROM Ship s WHERE s.name LIKE %:name%")
    List<Ship> getAllWithFiltersNamePageNumber(@Param("name") String name, Pageable pageable);

    @Query("SELECT s FROM Ship s WHERE s.planet LIKE %:planet%")
    List<Ship> getAllWithFiltersPlanetPageSize(@Param("planet") String planet, Pageable pageable);

    @Query("SELECT s FROM Ship s WHERE s.shipType LIKE :shipType AND s.prodDate >= :after AND s.prodDate <= :before")
    List<Ship> getAllWithFiltersShipTypeAfterBefore(@Param("shipType") ShipType shipType, @Param("after") Date after, @Param("before") Date before, Pageable pageable);

    @Query("SELECT s FROM Ship s WHERE s.shipType LIKE :shipType AND s.speed >= :minSpeed AND s.speed <= :maxSpeed")
    List<Ship> getAllWithFiltersShipTypeMinSpeedMaxSpeed(@Param("shipType") ShipType shipType, @Param("minSpeed") double minSpeed, @Param("maxSpeed") double maxSpeed, Pageable pageable);

    @Query("SELECT s FROM Ship s WHERE s.shipType LIKE :shipType AND s.crewSize >= :minCrewSize AND s.crewSize <= :maxCrewSize")
    List<Ship> getAllWithFiltersShipTypeMinCrewSizeMaxCrewSize(@Param("shipType") ShipType shipType, @Param("minCrewSize") int minCrewSize, @Param("maxCrewSize") int maxCrewSize, Pageable pageable);

    @Query("SELECT s FROM Ship s WHERE s.isUsed = :isUsed AND s.rating >= :minRating AND s.rating <= :maxRating")
    List<Ship> getAllWithFiltersIsUsedMinMaxRating(@Param("isUsed") boolean isUsed, @Param("minRating") double minRating, @Param("maxRating") double maxRating, Pageable pageable);

    @Query("SELECT s FROM Ship s WHERE s.isUsed = :isUsed AND s.speed <= :maxSpeed AND s.rating <= :maxRating")
    List<Ship> getAllWithFiltersIsUsedMaxSpeedMaxRating(@Param("isUsed") boolean isUsed, @Param("maxSpeed") double maxSpeed, @Param("maxRating") double maxRating, Pageable pageable);

    @Query("SELECT s FROM Ship s WHERE s.prodDate >= :after AND s.prodDate <= :before AND s.crewSize >= :minCrewSize AND s.crewSize <= :maxCrewSize")
    List<Ship> getAllWithFiltersAfterBeforeMinCrewMaxCrew(@Param("after") Date after, @Param("before") Date before, @Param("minCrewSize") int minCrewSize, @Param("maxCrewSize") int maxCrewSize, Pageable pageable);

    @Query("SELECT s FROM Ship s WHERE s.rating >= :minRating AND s.crewSize >= :minCrewSize AND s.speed >= :minSpeed")
    List<Ship> getAllWithFiltersMinRatingMinCrewSizeMinSpeed(@Param("minRating") double minRating, @Param("minCrewSize") int minCrewSize, @Param("minSpeed") double minSpeed, Pageable pageable);

    @Query("SELECT s FROM Ship s WHERE s.name LIKE %:name% AND s.prodDate >= :after AND s.rating <= :maxRating")
    List<Ship> getAllWithFiltersNameAfterMaxRating(@Param("name") String name, @Param("after") Date after, @Param("maxRating") double maxRating, Pageable pageable);

    @Query("SELECT s FROM Ship s WHERE s.shipType LIKE :shipType AND s.isUsed = :isUsed")
    List<Ship> getAllWithFiltersShipTypeIsUsed(@Param("shipType") ShipType shipType, @Param("isUsed") boolean isUsed, Pageable pageable);

    @Query("SELECT s FROM Ship s WHERE s.shipType LIKE :shipType AND s.crewSize <= :maxCrewSize")
    List<Ship> getAllWithFiltersShipTypeMaxCrewSize(@Param("shipType") ShipType shipType, @Param("maxCrewSize") int maxCrewSize, Pageable pageable);

    @Query("SELECT s FROM Ship s WHERE s.shipType LIKE :shipType AND s.prodDate <= :before AND s.speed <= :maxSpeed")
    List<Ship> getAllWithFiltersShipTypeBeforeMaxSpeed(@Param("shipType") ShipType shipType, @Param("before") Date before, @Param("maxSpeed") double maxSpeed, Pageable pageable);

    @Query("SELECT s FROM Ship s WHERE s.isUsed = :isUsed AND s.speed >= :minSpeed AND s.speed <= :maxSpeed")
    List<Ship> getAllWithFiltersIsUsedMinMaxSpeed(@Param("isUsed") boolean isUsed, @Param("minSpeed") double minSpeed, @Param("maxSpeed") double maxSpeed, Pageable pageable);
}
