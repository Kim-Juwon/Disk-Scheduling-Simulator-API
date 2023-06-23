package link.diskscheduler.domain.queue;

import link.diskscheduler.domain.cylinder.Cylinder;
import link.diskscheduler.domain.cylinder.Cylinders;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FCFSQueue extends Queue {
    private static final int EXIT = 0;
    private final List<Cylinder> requestedCylinderQueue;

    public static FCFSQueue create() {
        return new FCFSQueue(new LinkedList<>());
    }

    @Override
    public void add(Cylinder cylinder) {
        requestedCylinderQueue.add(cylinder);
    }

    @Override
    public boolean isEmpty() {
        return requestedCylinderQueue.isEmpty();
    }

    public Cylinder getNextCylinder() {
        if (isEmpty()) {
            return null;
        }

        return requestedCylinderQueue.remove(EXIT);
    }

    @Override
    public List<Cylinder> peekCurrentCylinders() {
        return requestedCylinderQueue;
    }

    public Cylinders getNextSameCylinders(int cylinderNumber) {
        Cylinders cylinders = Cylinders.create();

        for (int i = 0; i < requestedCylinderQueue.size(); i++) {
            Cylinder cylinder = requestedCylinderQueue.get(i);
            if (!cylinder.hasSameNumber(cylinderNumber)) {
                break;
            }

            cylinders.addToBack(cylinder);
            requestedCylinderQueue.remove(i);
            i--;
        }

        return cylinders;
    }

    @Override
    public void increaseWaitingTime() {
        requestedCylinderQueue.forEach(Cylinder::increaseWaitingTime);
    }
}
