package controller;

import model.Server;
import model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    /*
    give the task to the queue with the fewest members
     */

    @Override
    public void addTask(List<Server> servers, Task t) {
        int min=Integer.MAX_VALUE;
        int index=0;

        //get server with the smallest size
        for(int i=0;i<servers.size();i++){
            Server s=servers.get(i);
            if(s.getTasks().size()<min){
                min = s.getTotalTime();
                index = i;
            }
        }

        //add current task to that server
        Server s=servers.get(index);
        s.addTask(t);
    }
}
