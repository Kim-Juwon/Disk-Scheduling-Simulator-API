package link.diskscheduler.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class InformationPerTimeDto {
    private Integer currentTime;
    private Integer currentCylinder;
    private List<ProcessedCylinderDto> processedCylinders;
}
