package link.diskscheduler.domain.cylinder;

import link.diskscheduler.dto.request.CylinderDto;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class Cylinder {
    private static final int ZERO = 0;
    private int number;
    private int arrivalTime;
    private int waitingTime;

    public static Cylinder from(CylinderDto cylinderDto) {
        return Cylinder.builder()
                .number(cylinderDto.getNumber())
                .arrivalTime(cylinderDto.getArrivalTime())
                .waitingTime(ZERO)
                .build();
    }

    public int subtractNumber(int cylinderNumber) {
        return number - cylinderNumber;
    }

    public boolean hasSameNumber(int cylinderNumber) {
        return number == cylinderNumber;
    }

    public boolean hasSameArrivalTime(int arrivalTime) {
        return this.arrivalTime == arrivalTime;
    }

    public void increaseWaitingTime() {
        waitingTime++;
    }

    public int compareByArrivalTimeAscending(Cylinder cylinder) {
        return this.arrivalTime - cylinder.arrivalTime;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
