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

    public int subtractNumberFrom(int cylinderNumber) {
        return number - cylinderNumber;
    }

    public boolean hasSameNumberAs(int cylinderNumber) {
        return number == cylinderNumber;
    }

    public boolean hasSameArrivalTimeAs(int arrivalTime) {
        return this.arrivalTime == arrivalTime;
    }

    public void increaseWaitingTime() {
        waitingTime++;
    }

    public int compareByArrivalTimeAscending(Cylinder cylinder) {
        return this.arrivalTime - cylinder.arrivalTime;
    }

    public int getDistanceFrom(int cylinderNumber) {
        return Math.abs(number - cylinderNumber);
    }

    public boolean isNumberGreaterThan(Cylinder cylinder) {
        return number > cylinder.getNumber();
    }

    public boolean isNumberGreaterThan(int cylinderNumber) {
        return number > cylinderNumber;
    }

    public boolean isNumberGreaterOrEqualsFrom(int cylinderNumber) {
        return number >= cylinderNumber;
    }

    public boolean isNumberLessThan(Cylinder cylinder) {
        return number < cylinder.getNumber();
    }

    public boolean isNumberLessThan(int cylinderNumber) {
        return number < cylinderNumber;
    }

    public boolean isNumberLessOrEqualsFrom(int cylinderNumber) {
        return number <= cylinderNumber;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
