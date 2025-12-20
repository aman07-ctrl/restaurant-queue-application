package com.qtify.queue.queueservice.controller;

import com.qtify.queue.queueservice.domain.QueueEntry;
import com.qtify.queue.queueservice.service.QueueService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/queue")
public class QueueController {
    private QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }
    @PostMapping
    public QueueEntry joinQueue(@RequestParam String name, @RequestParam int partySize) {
        return queueService.addToQueue(name, partySize);

    }
    @GetMapping
    public List<QueueEntry> getQueue() {
        return queueService.getWaitingQueue();
    }
}
