package link.diskscheduler.dto.request;

import link.diskscheduler.constant.ProgramConstant;
import link.diskscheduler.exception.UnprocessableEntityException;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class Request {
    @NotNull
    @Min(ProgramConstant.MIN_CYLINDER_COUNT)
    @Max(ProgramConstant.MAX_CYLINDER_COUNT)
    private Integer cylinderCount;

    @NotNull
    @Min(ProgramConstant.MIN_CYLINDER_NUMBER)
    @Max(ProgramConstant.MAX_CYLINDER_NUMBER)
    private Integer headLocation;

    @NotNull
    @Size(min = ProgramConstant.MIN_REQUESTED_CYLINDER_COUNT, max = ProgramConstant.MAX_REQUESTED_CYLINDER_COUNT)
    private List<CylinderDto> requestedCylinders;

    @NotNull
    private AlgorithmDto algorithm;

    private ScanDirectionDto scanDirection;

    /**
     *   - algorithm이 있을땐 scanDirection이 not null
     */
    public void validateConstraints() {
        if ((algorithm.equals(AlgorithmDto.SCAN) || algorithm.equals(AlgorithmDto.C_SCAN) || algorithm.equals(AlgorithmDto.LOOK) || algorithm.equals(AlgorithmDto.C_LOOK)) && scanDirection == null) {
            throw new UnprocessableEntityException("scan direction is not null");
        }


    }
}
