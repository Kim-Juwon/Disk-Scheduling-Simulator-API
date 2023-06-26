package link.diskscheduler.domain.queue;

import link.diskscheduler.domain.cylinder.Cylinder;
import link.diskscheduler.domain.cylinder.Cylinders;
import link.diskscheduler.domain.direction.ScanDirection;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ScanQueue extends Queue {
    private static final int FRONT = 0;
    private final List<Cylinder> cylinders;

    public static ScanQueue create() {
        return new ScanQueue(new LinkedList<>());
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

    public Cylinder getNextCylinder(int currentCylinderNumber, ScanDirection currentScanDirection) {
        if (isEmpty()) {
            return null;
        }

        Cylinder nextCylinder = null;

        List<Cylinder> cylindersInRange = new ArrayList<>();
        List<Cylinder> cylindersOutRange = new ArrayList<>();

        cylinders.forEach(cylinder -> {
            if (isCylinderInRange(cylinder, currentCylinderNumber, currentScanDirection)) {
                cylindersInRange.add(cylinder);
            } else {
                cylindersOutRange.add(cylinder);
            }
        });

        // 현재 scan 방향에 있는 요청 실린더가 있다면, 현재 위치와 가장 가까운 요청 실린더를 추출
        if (!cylindersInRange.isEmpty()) {
            nextCylinder = cylindersInRange.get(0);
            for (int i = 1; i < cylindersInRange.size(); i++) {
                Cylinder cylinder = cylindersInRange.get(i);
                int distance = cylinder.getDistanceFrom(currentCylinderNumber);
                int minDistance = nextCylinder.getDistanceFrom(currentCylinderNumber);

                if (distance < minDistance) {
                    nextCylinder = cylinder;
                }
            }
        }

        // 현재 scan 방향에 요청 실린더가 없다면, 반대편 방향에서 가장 가까운 요청 실린더를 추출
        if (nextCylinder == null && !cylindersOutRange.isEmpty()) {
            nextCylinder = cylindersOutRange.get(0);
            for (int i = 1; i < cylindersOutRange.size(); i++) {
                Cylinder cylinder = cylindersOutRange.get(i);
                int distance = cylinder.getDistanceFrom(currentCylinderNumber);
                int minDistance = nextCylinder.getDistanceFrom(currentCylinderNumber);

                if (distance < minDistance) {
                    nextCylinder = cylinder;
                }
            }
        }

        cylinders.remove(nextCylinder);

        return nextCylinder;
    }

    private boolean isCylinderInRange(Cylinder cylinder, int currentCylinderNumber, ScanDirection currentScanDirection) {
        return (currentScanDirection.equals(ScanDirection.LEFT) && cylinder.isNumberLessOrEqualsFrom(currentCylinderNumber))
                || (currentScanDirection.equals(ScanDirection.RIGHT) && cylinder.isNumberGreaterOrEqualsFrom(currentCylinderNumber));
    }

    public Cylinders getSameCylinders(int cylinderNumber) {
        Cylinders sameCylinders = Cylinders.create();

        for (int i = 0; i < cylinders.size(); i++) {
            Cylinder cylinder = cylinders.get(i);
            if (cylinder.hasSameNumber(cylinderNumber)) {
                sameCylinders.addToBack(cylinder);
                cylinders.remove(i);
                i--;
            }
        }

        return sameCylinders;
    }

    @Override
    public void increaseWaitingTime() {
        cylinders.forEach(Cylinder::increaseWaitingTime);
    }
}
