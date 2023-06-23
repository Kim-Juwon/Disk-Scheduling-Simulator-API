package link.diskscheduler.domain.scheduler;

import link.diskscheduler.domain.cylinder.Cylinder;
import link.diskscheduler.domain.cylinder.Cylinders;
import link.diskscheduler.dto.request.AlgorithmDto;
import link.diskscheduler.dto.request.Request;
import link.diskscheduler.dto.response.Response;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SeekTimeScheduler {
    protected final Cylinders notArrivedCylinders;
    protected Cylinder targetCylinder;
    protected int currentCylinderNumber;
    protected int currentTime;

    public abstract Response schedule();

    public static SeekTimeScheduler from(Request request) {
        AlgorithmDto algorithm = request.getAlgorithm();

        if (algorithm.equals(AlgorithmDto.FCFS)) {
            return FCFSScheduler.from(request);
        }
        if (algorithm.equals(AlgorithmDto.SSTF)) {
            return SSTFScheduler.from(request);
        }
        if (algorithm.equals(AlgorithmDto.SCAN)) {
            return ScanScheduler.from(request);
        }
        return null;
    }
}
