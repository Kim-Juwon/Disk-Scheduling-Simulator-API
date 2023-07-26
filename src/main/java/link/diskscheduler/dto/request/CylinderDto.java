package link.diskscheduler.dto.request;

import link.diskscheduler.constant.ProgramConstant;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class CylinderDto {
    @NotNull(message = "number의 값은 필수입니다.")
    @Min(value = ProgramConstant.MIN_CYLINDER_NUMBER, message = "number의 값이 범위를 벗어났습니다.")
    @Max(value = ProgramConstant.MAX_CYLINDER_NUMBER, message = "number의 값이 범위를 벗어났습니다.")
    private Integer number;

    @NotNull(message = "arrivalTime의 값은 필수입니다.")
    @Min(value = ProgramConstant.MIN_ARRIVAL_TIME, message = "arrivalTime의 값이 범위를 벗어났습니다.")
    @Max(value = ProgramConstant.MAX_ARRIVAL_TIME, message = "arrivalTime의 값이 범위를 벗어났습니다.")
    private Integer arrivalTime;
}
