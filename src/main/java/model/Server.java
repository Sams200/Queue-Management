package model;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    /*
    each server represents a different queue that is shown
     */
    private BlockingQueue<Task> tasks;
    private AtomicInteger totalTime; //the sum of all the times left of all the tasks currently in the queue
    private AtomicInteger waitingTime; //how much waiting has been done to get into the first position
    private int time;
    private int maxTime;
    private int id;

    private int timeServiced;
    private int tasksServiced;

    public Server(int maxTime,int id){
        this.tasks=new LinkedBlockingQueue<>();
        this.totalTime=new AtomicInteger(0);
        this.waitingTime=new AtomicInteger(0);
        this.time=0;
        this.maxTime=maxTime;
        this.id=id;

        this.timeServiced=0;
        this.tasksServiced=0;
    }

    public void addTask(Task t){
        tasks.add(t);
        totalTime.addAndGet(t.getServiceTime());
    }
    @Override
    public void run() {
        while(time<maxTime){
            //look if theres something in the queue, otherwise wait until something appears
            try {
                Task t=tasks.peek();
                if(t==null){
                    //time++;
                    //Thread.sleep(25);
                    continue;
                }

                time+=t.getServiceTime();
                tasksServiced++;
                while(t.getServiceTime()>0){

                    Thread.sleep(1000);
                    timeServiced++;
                    t.setServiceTime(t.getServiceTime()-1);
                    //sleep for task's seconds, and decrement its service time by 1 each second


                    //also dont forget to decrement the total time every second
                    if(totalTime.get()>0)
                        totalTime.decrementAndGet();

                    //add 1 for each task that isnt in the front of the queue
                    if(tasks.size()>1)
                        waitingTime.addAndGet(tasks.size()-1);
                }

                tasks.remove();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public List<Task> getTasks() {
        return tasks.stream().toList();
    }

    public int getTotalTime() {
        return totalTime.get();
    }

    public int getWaitingTime(){
        return waitingTime.get();
    }

    public int getTimeServiced() {
        return timeServiced;
    }

    public int getTasksServiced() {
        return tasksServiced;
    }
}
