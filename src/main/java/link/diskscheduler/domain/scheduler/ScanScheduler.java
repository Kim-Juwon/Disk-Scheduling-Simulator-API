package link.diskscheduler.domain.scheduler;

import link.diskscheduler.domain.cylinder.Cylinder;
import link.diskscheduler.domain.cylinder.Cylinders;
import link.diskscheduler.domain.direction.ScanDirection;
import link.diskscheduler.domain.queue.ScanQueue;
import link.diskscheduler.dto.request.Request;
import link.diskscheduler.dto.response.Response;

import java.util.List;

public class ScanScheduler extends SeekTimeScheduler {
    private static final int ZERO = 0;
    private static final Cylinder EMPTY = null;
    private final ScanQueue queue;
    private ScanDirection scanDirection;
    private int firstCylinderNumber;
    private int lastCylinderNumber;

    private ScanScheduler(Cylinders notArrivedCylinders, Cylinder targetCylinder, int currentCylinderNumber, int currentTime, ScanQueue scanQueue, ScanDirection scanDirection, int firstCylinderNumber, int lastCylinderNumber) {
        super(notArrivedCylinders, targetCylinder, currentCylinderNumber, currentTime);
        this.queue = scanQueue;
        this.scanDirection = scanDirection;
        this.firstCylinderNumber = firstCylinderNumber;
        this.lastCylinderNumber = lastCylinderNumber;
    }

    public static ScanScheduler from(Request request) {
        return new ScanScheduler(Cylinders.from(request.getRequestedCylinders()), EMPTY, request.getStartCylinderNumber(), ZERO, ScanQueue.create(), request.getScanDirection().getScanDirection(), ZERO, request.getTotalCylinderCount() - 1);
    }

    @Override
    public Response schedule() {
        Response response = Response.create();

        while (isCylinderExistToSchedule()) {
            addArrivedCylindersToQueue();
            decideNextTargetCylinder();

            // 타겟 실린더가 없으면 위치는 그대로 유지하고 다음 시간으로 이동
            if (isTargetCylinderEmpty()) {
                response.add(currentTime, currentCylinderNumber, targetCylinder, queue);
                increaseWaitingTimeOfQueue();
                increaseCurrentTime();
                continue;
            }

            if (isReached()) {
                // 바로 다음 순서들에 같은 실린더 요청이 있는지 확인
                Cylinders sameCylinders = queue.getSameCylinders(targetCylinder.getNumber());

                if (sameCylinders.isEmpty()) {
                    response.add(currentTime, currentCylinderNumber, targetCylinder, queue, targetCylinder);
                } else {
                    sameCylinders.addToFront(targetCylinder);
                    response.add(currentTime, currentCylinderNumber, targetCylinder, queue, sameCylinders);
                }

                eliminateTargetCylinder();
                decideNextTargetCylinder();
            } else {
                response.add(currentTime, currentCylinderNumber, targetCylinder, queue);
            }

            if (!isTargetCylinderEmpty()) {
                targetCylinder.increaseWaitingTime();
            }

            moveTowardTargetCylinder();

            increaseWaitingTimeOfQueue();
            increaseCurrentTime();
        }

        return response;
    }

    private void addArrivedCylindersToQueue() {
        List<Cylinder> arrivedCylinders = notArrivedCylinders.getArrivedCylindersAt(currentTime);
        arrivedCylinders.forEach(queue::add);
    }

    private boolean isCylinderExistToSchedule() {
        return !notArrivedCylinders.isEmpty() || !queue.isEmpty() || targetCylinder != EMPTY;
    }

    private void decideNextTargetCylinder() {
        if (queue.isEmpty()) {
            return;
        }

        if (!isTargetCylinderEmpty()) {
            queue.addFront(targetCylinder);
        }
        targetCylinder = queue.getNextCylinder(currentCylinderNumber, scanDirection);
    }

    private boolean isTargetCylinderEmpty() {
        return targetCylinder == EMPTY;
    }

    private void eliminateTargetCylinder() {
        targetCylinder = EMPTY;
    }

    private boolean isReached() {
        return targetCylinder.hasSameNumber(currentCylinderNumber);
    }

    private void moveTowardTargetCylinder() {
        if (isTargetCylinderEmpty()) {
            return;
        }

        if (isFirstCylinder() || isLastCylinder()) {
            reverseDirection();
        }

        if (scanDirection.equals(ScanDirection.LEFT)) {
            moveToLeft();
        }
        if (scanDirection.equals(ScanDirection.RIGHT)) {
            moveToRight();
        }
    }

    private void moveToRight() {
        currentCylinderNumber++;
    }

    private void moveToLeft() {
        currentCylinderNumber--;
    }

    private void increaseCurrentTime() {
        currentTime++;
    }

    private void increaseWaitingTimeOfQueue() {
        queue.increaseWaitingTime();
    }

    private boolean isFirstCylinder() {
        return currentCylinderNumber == firstCylinderNumber;
    }

    private boolean isLastCylinder() {
        return currentCylinderNumber == lastCylinderNumber;
    }

    private void reverseDirection() {
        switch (scanDirection) {
            case LEFT:
                scanDirection = ScanDirection.RIGHT;
                break;
            case RIGHT:
                scanDirection = ScanDirection.LEFT;
                break;
        }
    }
}
