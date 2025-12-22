package com.qtify.queue.queueservice.repository;

import com.qtify.queue.queueservice.domain.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.*;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    @Query("""
        SELECT t
        FROM RestaurantTable t
        WHERE t.occupiedUntil IS NULL
           OR t.occupiedUntil <= :now
    """)
    List<RestaurantTable> findAvailableTables(@Param("now") LocalDateTime now);

}
