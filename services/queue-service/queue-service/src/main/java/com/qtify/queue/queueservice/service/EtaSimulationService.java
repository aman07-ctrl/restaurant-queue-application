package com.qtify.queue.queueservice.service;

import com.qtify.queue.queueservice.domain.QueueEntry;
import com.qtify.queue.queueservice.domain.RestaurantTable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EtaSimulationService {

    private static final int AVG_DINING_MINUTES = 45;

    public Map<Long, Integer> calculateEta(List<RestaurantTable> table, List<QueueEntry> queue) {

        Map<Long, Integer> etaResult = new HashMap<>();
        List<RestaurantTable> tableAvailabilityTimeline = new ArrayList<>();

        for (RestaurantTable restaurantTable : table) {
            RestaurantTable copy = new RestaurantTable();
            copy.setCapacity(restaurantTable.getCapacity());

            if (restaurantTable.getOccupiedUntil() != null) {
                copy.setOccupiedUntil(restaurantTable.getOccupiedUntil());
            } else {
                copy.setOccupiedUntil(LocalDateTime.now());
            }
            tableAvailabilityTimeline.add(copy);
        }
        for (QueueEntry party : queue) {

            RestaurantTable chosenTable = null;
            LocalDateTime earliestTime = null;

            // STEP 3: Find earliest suitable table
            for (RestaurantTable restaurantTable : tableAvailabilityTimeline) {

                if (restaurantTable.getCapacity() >= party.getPartySize()) {

                    if (earliestTime == null ||
                            restaurantTable.getOccupiedUntil().isBefore(earliestTime)) {

                        earliestTime = restaurantTable.getOccupiedUntil();
                        chosenTable = restaurantTable;
                    }
                }
            }
            if (chosenTable == null) {
                etaResult.put(party.getId(), -1);
                continue;
            }
            long minutes =
                    Duration.between(
                            LocalDateTime.now(),
                            earliestTime
                    ).toMinutes();

            int etaMinutes = (int) Math.max(minutes, 0);
            etaResult.put(party.getId(), etaMinutes);

            // STEP 6: Block table for next dining duration
            chosenTable.setOccupiedUntil(
                    earliestTime.plusMinutes(AVG_DINING_MINUTES)
            );

        }
        return etaResult;
    }
}
