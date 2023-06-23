package link.diskscheduler.constant;

public class ProgramConstant {
    public static final long MIN_CYLINDER_COUNT = 1L;
    public static final long MAX_CYLINDER_COUNT = 1000L;
    public static final long MIN_CYLINDER_NUMBER = MIN_CYLINDER_COUNT - 1;
    public static final long MAX_CYLINDER_NUMBER = MAX_CYLINDER_COUNT - 1;
    public static final int MIN_REQUESTED_CYLINDER_COUNT = 1;
    public static final int MAX_REQUESTED_CYLINDER_COUNT = 1000;
    public static final long MIN_ARRIVAL_TIME = 0L;
    public static final long MAX_ARRIVAL_TIME = 1000L;
}
