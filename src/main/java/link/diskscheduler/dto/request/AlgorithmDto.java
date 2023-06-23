package link.diskscheduler.dto.request;

import lombok.Getter;

@Getter
public enum AlgorithmDto {
    FCFS,
    SSTF,
    SCAN,
    C_SCAN,
    LOOK,
    C_LOOK
}
