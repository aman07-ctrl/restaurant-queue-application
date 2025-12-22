package com.qtify.queue.queueservice.config;

import com.qtify.queue.queueservice.domain.QueueEntry;
import com.qtify.queue.queueservice.domain.RestaurantTable;
import com.qtify.queue.queueservice.repository.QueueEntryRepository;
import com.qtify.queue.queueservice.repository.RestaurantTableRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    private final RestaurantTableRepository tableRepository;
    private final QueueEntryRepository queueRepository;

    public DataLoader(RestaurantTableRepository tableRepository,
                      QueueEntryRepository queueRepository) {
        this.tableRepository = tableRepository;
        this.queueRepository = queueRepository;
    }

    @Override
    public void run(String... args) {


        if (tableRepository.count() > 0) {
            return;
        }

        loadTables();
        loadQueue();
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

    private void loadQueue() {

        QueueEntry p1 = new QueueEntry();
        p1.setPartySize(2);
        p1.setCreatedAt(LocalDateTime.now());

        QueueEntry p2 = new QueueEntry();
        p2.setPartySize(4);
        p2.setCreatedAt(LocalDateTime.now().plusSeconds(5));

        QueueEntry p3 = new QueueEntry();
        p3.setPartySize(6);
        p3.setCreatedAt(LocalDateTime.now().plusSeconds(10));

        queueRepository.saveAll(List.of(p1, p2, p3));
    }
}

