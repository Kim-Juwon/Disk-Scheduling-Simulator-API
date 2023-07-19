package link.diskscheduler.domain.queue;

import link.diskscheduler.domain.cylinder.Cylinder;
import link.diskscheduler.domain.cylinder.Cylinders;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SSTFQueue extends Queue {
    private static final int FRONT = 0;
    private final List<Cylinder> cylinders;

    public static SSTFQueue create() {
        return new SSTFQueue(new LinkedList<>());
    }

    @Override
    public void add(Cylinder cylinder) {
        cylinders.add(cylinder);
    }

    public void addFront(Cylinder cylinder) {
        cylinders.add(FRONT, cylinder);
    }

    @Override
    public boolean isEmpty() {
        return cylinders.isEmpty();
    }

    @Override
    public List<Cylinder> peekCurrentCylinders() {
        return cylinders;
    }

    public Cylinder getNextCylinderFrom(int currentHeadLocation) {
        if (isEmpty()) {
            return null;
        }

        Cylinder shortestSeekTimeCylinder = cylinders.get(FRONT);

        for (int i = 1; i < cylinders.size(); i++) {
            Cylinder cylinder = cylinders.get(i);

            int distance = cylinder.getDistanceFrom(currentHeadLocation);
            int shortestDistance = shortestSeekTimeCylinder.getDistanceFrom(currentHeadLocation);
            if (distance < shortestDistance) {
                shortestSeekTimeCylinder = cylinder;
            }
        }

        cylinders.remove(shortestSeekTimeCylinder);
        return shortestSeekTimeCylinder;
    }

    public Cylinders getSameCylindersFrom(int cylinderNumber) {
        Cylinders sameCylinders = Cylinders.create();

        for (int i = 0; i < cylinders.size(); i++) {
            Cylinder cylinder = cylinders.get(i);
            if (cylinder.hasSameNumberAs(cylinderNumber)) {
                sameCylinders.addToBack(cylinder);
                cylinders.remove(i);
                i--;
            }
        }

        return sameCylinders;
    }

    public void increaseWaitingTime() {
        cylinders.forEach(Cylinder::increaseWaitingTime);
    }
}
