package com.qtify.queue.queueservice.service;

import com.qtify.queue.queueservice.domain.QueueEntry;
import com.qtify.queue.queueservice.domain.RestaurantTable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Calculates estimated wait time (ETA) for queued parties.
 *
 * Algorithm:
 * 1. Clone table availability timeline
 * 2. Assign parties in FIFO order
 * 3. Match smallest suitable table
 * 4. Block table for average dining duration
 *
 * Returns:
 *  - ETA in minutes
 *  - -1 if no table can ever accommodate the party
 */

@Service
public class EtaSimulationService {

    private static final int AVG_DINING_MINUTES = 45;

    private final Clock clock;

    public EtaSimulationService(Clock clock) {
        this.clock = clock;
    }

    public Map<Long, Integer> calculateEta(List<RestaurantTable> table, List<QueueEntry> queue) {

        Map<Long, Integer> etaResult = new HashMap<>();
        LocalDateTime now = LocalDateTime.now(clock);

        List<RestaurantTable> tableAvailabilityTimeline = new ArrayList<>();

        for (RestaurantTable restaurantTable : table) {
            RestaurantTable copy = new RestaurantTable();
            copy.setCapacity(restaurantTable.getCapacity());

            if (restaurantTable.getOccupiedUntil() != null) {
                copy.setOccupiedUntil(restaurantTable.getOccupiedUntil());
            } else {
                copy.setOccupiedUntil(now);
            }
            tableAvailabilityTimeline.add(copy);
        }
        for (QueueEntry party : queue) {

            RestaurantTable chosenTable = null;
            LocalDateTime earliestTime = null;


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
                            now,
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
