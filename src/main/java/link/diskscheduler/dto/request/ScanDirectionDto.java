package link.diskscheduler.dto.request;

import link.diskscheduler.domain.direction.ScanDirection;

public enum ScanDirectionDto {
    LEFT,
    RIGHT;

    public ScanDirection getScanDirection() {
        if (name().equals("LEFT")) {
            return ScanDirection.LEFT;
        }
        if (name().equals("RIGHT")) {
            return ScanDirection.RIGHT;
        }
        return null;
    }
}
