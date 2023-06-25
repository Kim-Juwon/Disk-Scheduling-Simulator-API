package link.diskscheduler.domain.direction;

public enum ScanDirection {
    LEFT, RIGHT;

    public ScanDirection reverse() {
        if (equals(LEFT)) {
            return RIGHT;
        } else {
            return LEFT;
        }
    }
}
