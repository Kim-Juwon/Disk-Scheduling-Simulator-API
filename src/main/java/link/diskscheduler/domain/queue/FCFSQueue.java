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
    private final List<Cylinder> cylinders;

    public static FCFSQueue create() {
        return new FCFSQueue(new LinkedList<>());
    }

    @Override
    public void add(Cylinder cylinder) {
        cylinders.add(cylinder);
    }

    @Override
    public boolean isEmpty() {
        return cylinders.isEmpty();
    }

    public Cylinder getNextCylinder() {
        if (isEmpty()) {
            return null;
        }

        return cylinders.remove(EXIT);
    }

    @Override
    public List<Cylinder> peekCurrentCylinders() {
        return cylinders;
    }

    public Cylinders getImmediatelyNextSameCylindersFrom(int cylinderNumber) {
        Cylinders immediatelyNextSameCylinders = Cylinders.create();

        for (int i = 0; i < cylinders.size(); i++) {
            Cylinder cylinder = cylinders.get(i);
            if (!cylinder.hasSameNumberAs(cylinderNumber)) {
                break;
            }

            immediatelyNextSameCylinders.addToBack(cylinder);
            cylinders.remove(i);
            i--;
        }

        return immediatelyNextSameCylinders;
    }

    @Override
    public void increaseWaitingTime() {
        cylinders.forEach(Cylinder::increaseWaitingTime);
    }
}
