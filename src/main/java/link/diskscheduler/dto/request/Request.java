package link.diskscheduler.dto.request;

import link.diskscheduler.constant.ProgramConstant;
import link.diskscheduler.exception.UnprocessableEntityException;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class Request {
    @NotNull(message = "cylinderCount의 값은 필수입니다.")
    @Min(value = ProgramConstant.MIN_CYLINDER_COUNT, message = "cylinderCount의 값이 범위를 벗어났습니다.")
    @Max(value = ProgramConstant.MAX_CYLINDER_COUNT, message = "cylinderCount의 값이 범위를 벗어났습니다.")
    private Integer cylinderCount;

    @NotNull(message = "headLocation의 값은 필수입니다.")
    @Min(value = ProgramConstant.MIN_CYLINDER_NUMBER, message = "headLocation의 값이 범위를 벗어났습니다.")
    @Max(value = ProgramConstant.MAX_CYLINDER_NUMBER, message = "headLocation의 값이 범위를 벗어났습니다.")
    private Integer headLocation;

    @Valid
    @NotNull(message = "requestedCylinders의 값은 필수입니다.")
    @Size(
            min = ProgramConstant.MIN_REQUESTED_CYLINDER_COUNT,
            max = ProgramConstant.MAX_REQUESTED_CYLINDER_COUNT,
            message = "requestedCylinders의 size 범위를 벗어났습니다."
    )
    private List<CylinderDto> requestedCylinders;

    @NotNull(message = "algorithm은 필수입니다.")
    private AlgorithmDto algorithm;

    private ScanDirectionDto scanDirection;

    /*
         - algorithm이 null이 아니면 scanDirection이 필수
         - algorithm이 null이면 scanDirection은 선택
     */
    public void validateConstraints() {
        if ((algorithm.equals(AlgorithmDto.SCAN)
                || algorithm.equals(AlgorithmDto.C_SCAN)
                || algorithm.equals(AlgorithmDto.LOOK)
                || algorithm.equals(AlgorithmDto.C_LOOK))
                && scanDirection == null) {
            throw new UnprocessableEntityException("algorithm의 값이 null이 아니면 scanDirection은 필수입니다.");
        }
    }
}
