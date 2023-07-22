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
    private Integer time;
    private Integer headLocation;
    private Integer targetCylinder;
    private List<Integer> queue;
    private List<ProcessedCylinderDto> processedCylinders;

    private InformationPerTimeDto(Integer time, Integer headLocation, Integer targetCylinder, List<Integer> queue) {
        this.time = time;
        this.headLocation = headLocation;
        this.targetCylinder = targetCylinder;
        this.queue = queue;
    }

    private InformationPerTimeDto(Integer time, Integer headLocation, Integer targetCylinder, List<Integer> queue, List<ProcessedCylinderDto> processedCylinders) {
        this(time, headLocation, targetCylinder, queue);
        this.processedCylinders = processedCylinders;
    }

    public static InformationPerTimeDto of(Integer time, Integer headLocation, Cylinder targetCylinder, Queue queue) {
        return new InformationPerTimeDto(
                time,
                headLocation,
                targetCylinder == null ? null : targetCylinder.getNumber(),
                queue.peekCurrentCylinders().stream()
                        .map(Cylinder::getNumber)
                        .collect(Collectors.toList())
        );
    }

    public static InformationPerTimeDto of(Integer time, Integer headLocation, Cylinder targetCylinder, Queue queue, Cylinder processedCylinder) {
        return new InformationPerTimeDto(
                time,
                headLocation,
                targetCylinder == null ? null : targetCylinder.getNumber(),
                queue.peekCurrentCylinders().stream()
                        .map(Cylinder::getNumber)
                        .collect(Collectors.toList()),
                new ArrayList<>() {{
                    add(ProcessedCylinderDto.from(processedCylinder));
                }}
        );
    }

    public static InformationPerTimeDto of(Integer time, Integer headLocation, Cylinder targetCylinder, Queue queue, Cylinders processedCylinders) {
        return new InformationPerTimeDto(
                time,
                headLocation,
                targetCylinder == null ? null : targetCylinder.getNumber(),
                queue.peekCurrentCylinders().stream()
                        .map(Cylinder::getNumber)
                        .collect(Collectors.toList()),
                processedCylinders.getCylinders().stream()
                        .map(ProcessedCylinderDto::from)
                        .collect(Collectors.toList())
        );
    }
}
