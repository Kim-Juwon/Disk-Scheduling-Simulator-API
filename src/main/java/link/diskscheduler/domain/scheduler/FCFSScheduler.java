package link.diskscheduler.domain.scheduler;

import link.diskscheduler.domain.cylinder.Cylinder;
import link.diskscheduler.domain.cylinder.Cylinders;
import link.diskscheduler.domain.queue.FCFSQueue;
import link.diskscheduler.dto.request.Request;
import link.diskscheduler.dto.response.Response;

import java.util.List;

public class FCFSScheduler extends SeekTimeScheduler {
    private static final int ZERO = 0;
    private static final Cylinder EMPTY = null;
    private final FCFSQueue queue;

    private FCFSScheduler(Cylinders notArrivedCylinders, Cylinder targetCylinder, int currentCylinderNumber, int currentTime, FCFSQueue fcfsQueue) {
        super(notArrivedCylinders, targetCylinder, currentCylinderNumber, currentTime);
        this.queue = fcfsQueue;
    }

    public static FCFSScheduler from(Request request) {
        return new FCFSScheduler(Cylinders.from(request.getRequestedCylinders()), EMPTY, request.getStartCylinderNumber(), ZERO, FCFSQueue.create());
    }

    @Override
    public Response schedule() {
        Response response = Response.create();

        while (isCylinderExistToSchedule()) {
            addArrivedCylindersToQueue();
            decideNextTargetCylinder();

            // 요청 후 대기중인 실린더가 없으면 위치는 그대로 유지하고 다음 시간으로 이동
            if (isTargetCylinderEmpty()) {
                response.add(currentTime, currentCylinderNumber, targetCylinder, queue);
                increaseWaitingTimeOfQueue();
                increaseCurrentTime();
                continue;
            }

            if (isReached()) {
                // 바로 다음 순서들에 같은 실린더 요청이 있는지 확인
                Cylinders nextSameCylinders = queue.getNextSameCylinders(targetCylinder.getNumber());

                if (nextSameCylinders.isEmpty()) {
                    response.add(currentTime, currentCylinderNumber, targetCylinder, queue, targetCylinder);
                } else {
                    nextSameCylinders.addToFront(targetCylinder);
                    response.add(currentTime, currentCylinderNumber, targetCylinder, queue, nextSameCylinders);
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
        if (isTargetCylinderEmpty() && !queue.isEmpty()) {
            targetCylinder = queue.getNextCylinder();
        }
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

        int subtractedDistance = targetCylinder.subtractNumber(currentCylinderNumber);

        if (isPositive(subtractedDistance)) {
            moveToRight();
        }
        if (isNegative(subtractedDistance)) {
            moveToLeft();
        }
    }

    private boolean isPositive(int value) {
        return value > ZERO;
    }

    private boolean isNegative(int value) {
        return value < ZERO;
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
}
