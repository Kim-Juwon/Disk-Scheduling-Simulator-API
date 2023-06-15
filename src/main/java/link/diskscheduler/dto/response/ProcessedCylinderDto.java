package link.diskscheduler.dto.response;

import lombok.Getter;

@Getter
public class ProcessedCylinderDto {
    private Integer number;
    private Integer totalDistance;
    private Integer waitingTime;
}
