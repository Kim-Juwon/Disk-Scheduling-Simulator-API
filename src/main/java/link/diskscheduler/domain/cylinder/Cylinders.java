package link.diskscheduler.domain.cylinder;

import link.diskscheduler.dto.request.CylinderDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Cylinders {
    private static final int FRONT = 0;
    private final List<Cylinder> cylinders;

    public static Cylinders from(List<CylinderDto> cylinderDtos) {
        List<Cylinder> cylinders = cylinderDtos.stream()
                .map(Cylinder::from)
                .sorted(Cylinder::compareByArrivalTimeAscending)
                .collect(Collectors.toCollection(LinkedList::new));

        return new Cylinders(cylinders);
    }

    public static Cylinders create() {
        return new Cylinders(new LinkedList<>());
    }

    public boolean isEmpty() {
        return cylinders.isEmpty();
    }

    public void addToFront(Cylinder cylinder) {
        cylinders.add(FRONT, cylinder);
    }

    public void addToBack(Cylinder cylinder) {
        cylinders.add(cylinder);
    }

    public List<Cylinder> getArrivedCylindersAt(int time) {
        List<Cylinder> arrivedCylinders = new ArrayList<>();

        for (int i = 0; i < cylinders.size(); i++) {
            Cylinder cylinder = cylinders.get(i);

            if (cylinder.hasSameArrivalTimeAs(time)) {
                arrivedCylinders.add(cylinder);
                cylinders.remove(i);
                i--;
            }
        }

        return arrivedCylinders;
    }

    public void increaseWaitingTime() {
        cylinders.forEach(Cylinder::increaseWaitingTime);
    }
}
