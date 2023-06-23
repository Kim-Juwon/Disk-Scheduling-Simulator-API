package link.diskscheduler.dto.request;

import link.diskscheduler.constant.ProgramConstant;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class CylinderDto {
    @NotNull
    @Min(ProgramConstant.MIN_CYLINDER_NUMBER)
    @Max(ProgramConstant.MAX_CYLINDER_NUMBER)
    private Integer number;

    @NotNull
    @Min(ProgramConstant.MIN_ARRIVAL_TIME)
    @Max(ProgramConstant.MAX_ARRIVAL_TIME)
    private Integer arrivalTime;
}
