package link.diskscheduler.dto.response;

import link.diskscheduler.domain.cylinder.Cylinder;
import link.diskscheduler.domain.cylinder.Cylinders;
import link.diskscheduler.domain.queue.Queue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Response {
    private final List<InformationPerTimeDto> informations;

    public static Response create() {
        return new Response(new ArrayList<>());
    }

    public void add(int currentTime, int currentCylinderNumber, Cylinder targetCylinder, Queue queue) {
        informations.add(InformationPerTimeDto.of(currentTime, currentCylinderNumber, targetCylinder, queue));
    }

    public void add(int currentTime, int currentCylinderNumber, Cylinder targetCylinder, Queue queue, Cylinder cylinder) {
        informations.add(InformationPerTimeDto.of(currentTime, currentCylinderNumber, targetCylinder, queue, cylinder));
    }

    public void add(int currentTime, int currentCylinderNumber, Cylinder targetCylinder, Queue queue, Cylinders cylinders) {
        informations.add(InformationPerTimeDto.of(currentTime, currentCylinderNumber, targetCylinder, queue, cylinders));
    }
}
