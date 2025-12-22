package com.qtify.queue.queueservice.service;

import com.qtify.queue.queueservice.domain.QueueEntry;
import com.qtify.queue.queueservice.domain.QueueStatus;
import com.qtify.queue.queueservice.repository.QueueEntryRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueueService {
    private final QueueEntryRepository queueEntryRepository;
    public QueueService(QueueEntryRepository queueEntryRepository) {
        this.queueEntryRepository = queueEntryRepository;
    }
    public QueueEntry addToQueue(String customerName, int partySize) {
        QueueEntry entry = QueueEntry.builder()
                .customerName(customerName)
                .partySize(partySize)
                .status(QueueStatus.WAITING)
                .createdAt(LocalDateTime.now())
                .build();

        return queueEntryRepository.save(entry);
    }
    public List<QueueEntry> getWaitingQueue() {
        return queueEntryRepository.findByStatus(QueueStatus.WAITING);
    }
}
