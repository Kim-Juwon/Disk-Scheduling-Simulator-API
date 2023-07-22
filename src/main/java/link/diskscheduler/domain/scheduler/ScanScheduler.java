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
    private final ScanQueue queue;
    private final int firstCylinderNumber;
    private final int lastCylinderNumber;
    private ScanDirection currentScanDirection;

    private ScanScheduler(
            Cylinders notArrivedCylinders,
            Cylinder currentTargetCylinder,
            int currentHeadLocation,
            int currentTime,
            ScanQueue scanQueue,
            int firstCylinderNumber,
            int lastCylinderNumber,
            ScanDirection currentScanDirection
    ) {
        super(notArrivedCylinders, currentTargetCylinder, currentHeadLocation, currentTime);
        this.queue = scanQueue;
        this.firstCylinderNumber = firstCylinderNumber;
        this.lastCylinderNumber = lastCylinderNumber;
        this.currentScanDirection = currentScanDirection;
    }

    public static ScanScheduler from(Request request) {
        return new ScanScheduler(
                Cylinders.from(request.getRequestedCylinders()),
                null,
                request.getHeadLocation(),
                ZERO,
                ScanQueue.create(),
                ZERO,
                request.getCylinderCount() - 1,
                request.getScanDirection().getScanDirection()
        );
    }

    @Override
    public Response schedule() {
        Response response = Response.create();

        while (isCylinderExistToSchedule()) {
            addArrivedCylindersToQueue();
            decideNextTargetCylinder();

            // 현재 타겟 실린더가 없으면, 위치를 유지하며 다음 시간으로 이동
            if (isCurrentTargetCylinderEmpty()) {
                response.add(currentTime, currentHeadLocation, currentTargetCylinder, queue);
                increaseWaitingTimeOfQueue();
                increaseCurrentTime();
                continue;
            }

            // 헤드가 현재 타겟 실린더에 도착하였을 경우
            if (didHeadReachTargetCylinder()) {
                // queue에 있는 동일 번호 실린더들을 가져온다.
                Cylinders sameCylinders = queue.getSameCylindersFrom(currentTargetCylinder.getNumber());

                /*
                     queue에 동일 번호 실린더가 없다면 현재 타겟 실린더만 현재 시간 상태 결과에 저장한다.
                     queue에 동일 번호 실린더가 1개 이상 존재한다면 해당 실린더들을 추가해 현재 시간 상태 결과에 저장한다.
                 */
                if (sameCylinders.isEmpty()) {
                    response.add(currentTime, currentHeadLocation, currentTargetCylinder, queue, currentTargetCylinder);
                }
                else {
                    sameCylinders.addToFront(currentTargetCylinder);
                    response.add(currentTime, currentHeadLocation, currentTargetCylinder, queue, sameCylinders);
                }

                eliminateCurrentTargetCylinder();
                decideNextTargetCylinder();
            } else {
                response.add(currentTime, currentHeadLocation, currentTargetCylinder, queue);
            }

            increaseWaitingTimeOfCurrentTargetCylinder();

            moveHead();

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
        return !notArrivedCylinders.isEmpty() || !queue.isEmpty() || currentTargetCylinder != null;
    }

    private void decideNextTargetCylinder() {
        if (queue.isEmpty()) {
            return;
        }

        if (!isCurrentTargetCylinderEmpty()) {
            queue.addFront(currentTargetCylinder);
        }
        currentTargetCylinder = queue.getNextCylinderFrom(currentHeadLocation, currentScanDirection);
    }

    private boolean isCurrentTargetCylinderEmpty() {
        return currentTargetCylinder == null;
    }

    private void eliminateCurrentTargetCylinder() {
        currentTargetCylinder = null;
    }

    private boolean didHeadReachTargetCylinder() {
        return currentTargetCylinder.hasSameNumberAs(currentHeadLocation);
    }

    private void increaseWaitingTimeOfCurrentTargetCylinder() {
        if (isCurrentTargetCylinderEmpty()) {
            return;
        }

        currentTargetCylinder.increaseWaitingTime();
    }

    private void moveHead() {
        if (isCurrentTargetCylinderEmpty()) {
            return;
        }

        if (isHeadLocationEndpointCylinder()) {
            reverseScanDirection();
        }
        moveHeadToScanDirection();
    }

    private void moveHeadToScanDirection() {
        if (isCurrentScanDirectionLeft()) {
            moveToLeft();
        }
        if (isCurrentScanDirectionRight()) {
            moveToRight();
        }
    }

    private void moveToRight() {
        currentHeadLocation++;
    }

    private void moveToLeft() {
        currentHeadLocation--;
    }

    private void increaseCurrentTime() {
        currentTime++;
    }

    private void increaseWaitingTimeOfQueue() {
        queue.increaseWaitingTime();
    }

    private boolean isHeadLocationEndpointCylinder() {
        return isHeadLocationFirstCylinder() || isHeadLocationLastCylinder();
    }

    private boolean isHeadLocationFirstCylinder() {
        return currentHeadLocation == firstCylinderNumber;
    }

    private boolean isHeadLocationLastCylinder() {
        return currentHeadLocation == lastCylinderNumber;
    }

    private boolean isCurrentScanDirectionLeft() {
        return currentScanDirection.equals(ScanDirection.LEFT);
    }

    private boolean isCurrentScanDirectionRight() {
        return currentScanDirection.equals(ScanDirection.RIGHT);
    }

    private void reverseScanDirection() {
        switch (currentScanDirection) {
            case LEFT:
                currentScanDirection = ScanDirection.RIGHT;
                break;
            case RIGHT:
                currentScanDirection = ScanDirection.LEFT;
                break;
        }
    }
}
