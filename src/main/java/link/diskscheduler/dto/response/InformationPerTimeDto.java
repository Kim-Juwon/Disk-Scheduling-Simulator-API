package link.diskscheduler.dto.response;

import link.diskscheduler.domain.cylinder.Cylinder;
import link.diskscheduler.domain.cylinder.Cylinders;
import link.diskscheduler.domain.queue.Queue;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class InformationPerTimeDto {
    private Integer currentTime;
    private Integer currentCylinderNumber;
    private Integer targetCylinderNumber;
    private List<Integer> queue;
    private List<ProcessedCylinderDto> processedCylinders;

    private InformationPerTimeDto(Integer currentTime, Integer currentCylinderNumber, Integer targetCylinderNumber, List<Integer> queue) {
        this.currentTime = currentTime;
        this.currentCylinderNumber = currentCylinderNumber;
        this.targetCylinderNumber = targetCylinderNumber;
        this.queue = queue;
    }

    private InformationPerTimeDto(Integer currentTime, Integer currentCylinderNumber, Integer targetCylinderNumber, List<Integer> queue, List<ProcessedCylinderDto> processedCylinders) {
        this(currentTime, currentCylinderNumber, targetCylinderNumber, queue);
        this.processedCylinders = processedCylinders;
    }

    public static InformationPerTimeDto of(Integer currentTime, Integer currentCylinderNumber, Cylinder targetCylinder, Queue queue) {
        Integer targetCylinderNumber;
        if (targetCylinder == null) {
            targetCylinderNumber = null;
        } else {
            targetCylinderNumber = targetCylinder.getNumber();
        }

        return new InformationPerTimeDto(
                currentTime,
                currentCylinderNumber,
                targetCylinderNumber,
                queue.peekCurrentCylinders().stream()
                        .map(Cylinder::getNumber)
                        .collect(Collectors.toList())
        );
    }

    public static InformationPerTimeDto of(Integer currentTime, Integer currentCylinderNumber, Cylinder targetCylinder, Queue queue, Cylinder cylinder) {
        return new InformationPerTimeDto(
                currentTime,
                currentCylinderNumber,
                targetCylinder.getNumber(),
                queue.peekCurrentCylinders().stream()
                        .map(Cylinder::getNumber)
                        .collect(Collectors.toList()),
                new ArrayList<>() {{
                    add(ProcessedCylinderDto.from(cylinder));
                }}
        );
    }

    public static InformationPerTimeDto of(Integer currentTime, Integer currentCylinderNumber, Cylinder targetCylinder, Queue queue, Cylinders cylinders) {
        return new InformationPerTimeDto(
                currentTime,
                currentCylinderNumber,
                targetCylinder.getNumber(),
                queue.peekCurrentCylinders().stream()
                        .map(Cylinder::getNumber)
                        .collect(Collectors.toList()),
                cylinders.getCylinders().stream()
                        .map(ProcessedCylinderDto::from)
                        .collect(Collectors.toList())
        );
    }
}
