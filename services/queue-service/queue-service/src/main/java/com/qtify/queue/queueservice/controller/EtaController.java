package com.qtify.queue.queueservice.controller;

import com.qtify.queue.queueservice.domain.QueueEntry;
import com.qtify.queue.queueservice.domain.RestaurantTable;
import com.qtify.queue.queueservice.dto.EtaResponse;
import com.qtify.queue.queueservice.repository.QueueEntryRepository;
import com.qtify.queue.queueservice.repository.RestaurantTableRepository;
import com.qtify.queue.queueservice.service.EtaSimulationService;
import com.qtify.queue.queueservice.service.QueueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eta")
public class EtaController {
    public final RestaurantTableRepository restaurantTableRepository;
    public final QueueEntryRepository queueEntryRepository;
    public final EtaSimulationService  etaService;

    public EtaController(RestaurantTableRepository restaurantTableRepository,
                         QueueEntryRepository queueEntryRepository,
                         EtaSimulationService etaService) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.queueEntryRepository = queueEntryRepository;
        this.etaService = etaService;

    }

    @GetMapping("/preview")
    public List<EtaResponse> previewEtas() {

        // 1. Fetch all tables
        List<RestaurantTable> tables =
                restaurantTableRepository.findAll();

        // 2. Fetch queue in FIFO order
        List<QueueEntry> queue =
                queueEntryRepository.findAllByOrderByCreatedAtAsc();

        // 3. Calculate ETA
        Map<Long, Integer> etaMap =
                etaService.calculateEta(tables, queue);

        // 4. Convert to response
        List<EtaResponse> response = new ArrayList<>();

        for (QueueEntry entry : queue) {
            int eta = etaMap.get(entry.getId());
            response.add(new EtaResponse(entry.getId(), eta));
        }

        return response;
    }

}
