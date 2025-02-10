package controller;

import model.Server;
import model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scheduler {
    /*
    the scheduler is responsible for giving the tasks to the appropriate
    queue according to the chosen strategy
     */
    private List<Server> servers;
    private Strategy strategy;

    public Scheduler(int maxNoServers,SelectionPolicy policy,int maxTime){
        this.servers= Collections.synchronizedList(new ArrayList<Server>());
        changeStrategy(policy);

        //initialize all the servers
        for(int i=0;i<maxNoServers;i++){
            Server s=new Server(maxTime,i+1);
            servers.add(s);
            Thread t=new Thread(s);
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        switch (policy){
            case SHORTEST_TIME -> {
                strategy=new ConcreteStrategyTime();
                break;
            }
            case SHORTEST_QUEUE ->{
                strategy=new ConcreteStrategyQueue();
                break;
            }
            default ->{
                break;
            }
        }
    }

    public void dispatchTask(Task t){
        strategy.addTask(servers,t);
    }

    public List<Server> getServers(){
        return this.servers;
    }

}
