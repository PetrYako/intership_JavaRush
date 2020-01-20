package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ShipRepository extends PagingAndSortingRepository<Ship, Long> {

    @Query("SELECT s FROM Ship s")
    List<Ship> getAll(Pageable pageable);

    @Override
    @Transactional
    Ship save(Ship ship);
}
