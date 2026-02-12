package com.qtify.queue.queueservice.config;

import com.qtify.queue.queueservice.domain.QueueEntry;
import com.qtify.queue.queueservice.domain.RestaurantTable;
import com.qtify.queue.queueservice.repository.QueueEntryRepository;
import com.qtify.queue.queueservice.repository.RestaurantTableRepository;
import com.qtify.queue.queueservice.service.QueueService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final RestaurantTableRepository tableRepository;
    private final QueueService queueService;

    public DataLoader(RestaurantTableRepository tableRepository,
                      QueueService queueService) {
        this.tableRepository = tableRepository;
        this.queueService = queueService;
    }

    @Override
    public void run(String... args) {


        if (tableRepository.count() == 0) {
            loadTables();
        }

        // Load queue ONLY via service

    }

    private void loadTables() {

        RestaurantTable t1 = new RestaurantTable();
        t1.setCapacity(2);
        t1.setOccupiedUntil(null);

        RestaurantTable t2 = new RestaurantTable();
        t2.setCapacity(4);
        t2.setOccupiedUntil(LocalDateTime.now().plusMinutes(10));

        RestaurantTable t3 = new RestaurantTable();
        t3.setCapacity(6);
        t3.setOccupiedUntil(LocalDateTime.now().plusMinutes(30));

        RestaurantTable t4 = new RestaurantTable();
        t4.setCapacity(4);
        t4.setOccupiedUntil(LocalDateTime.now().plusMinutes(20));

        tableRepository.saveAll(List.of(t1, t2, t3,t4));
    }

}

