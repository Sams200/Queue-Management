package controller;

import model.Task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {
    //orders the tasks by arrival time
    @Override
    public int compare(Task o1, Task o2) {
        return o1.getArrivalTime()- o2.getArrivalTime();

    }

    @Override
    public Comparator<Task> reversed() {
        return Comparator.super.reversed();
    }
}
