package link.diskscheduler.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class Request {
    private Integer totalNumberOfCylinders;
    private Integer startCylinderNumber;
    private List<CylinderDto> requestedCylinders;
}
