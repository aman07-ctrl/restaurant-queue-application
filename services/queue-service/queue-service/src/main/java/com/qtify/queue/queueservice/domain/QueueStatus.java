package com.qtify.queue.queueservice.domain;

public enum QueueStatus {
    WAITING,     // Party is waiting to be seated
    SEATED,      // Party has been assigned a table
    ASSIGNED,   // Party finished dining
    CANCELLED    // Party left or cancelled
}
