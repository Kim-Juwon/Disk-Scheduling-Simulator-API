package link.diskscheduler.dto.response;

import link.diskscheduler.domain.cylinder.Cylinder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessedCylinderDto {
    private Integer number;
    private Integer waitingTime;

    public static ProcessedCylinderDto from(Cylinder cylinder) {
        return new ProcessedCylinderDto(cylinder.getNumber(), cylinder.getWaitingTime());
    }
}
