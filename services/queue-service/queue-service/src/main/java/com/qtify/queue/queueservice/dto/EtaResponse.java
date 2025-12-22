package com.qtify.queue.queueservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EtaResponse {
    private Long partyId;
    private int etaMinutes;
}
