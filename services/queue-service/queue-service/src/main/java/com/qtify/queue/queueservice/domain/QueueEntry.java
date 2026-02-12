package com.qtify.queue.queueservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="queue_entries")
@Entity
public class QueueEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private int partySize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QueueStatus status;
    private LocalDateTime createdAt;

    private Long assignedTableId;




}
