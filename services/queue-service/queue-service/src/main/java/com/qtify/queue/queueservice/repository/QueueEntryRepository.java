package com.qtify.queue.queueservice.repository;

import com.qtify.queue.queueservice.domain.QueueEntry;
import com.qtify.queue.queueservice.domain.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueueEntryRepository extends JpaRepository<QueueEntry, Long> {
    List<QueueEntry> findByStatus(QueueStatus status);
    List<QueueEntry> findAllByOrderByCreatedAtAsc();
    List<QueueEntry> findByStatusOrderByCreatedAt(QueueStatus status);
}
