package link.diskscheduler.domain.queue;

import link.diskscheduler.domain.cylinder.Cylinder;

import java.util.List;

public abstract class Queue {
    public abstract void add(Cylinder cylinder);
    public abstract boolean isEmpty();
    public abstract List<Cylinder> peekCurrentCylinders();
    public abstract void increaseWaitingTime();
}
