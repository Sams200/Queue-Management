package controller;

import model.Server;
import model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SimulationManager implements Runnable{
    /*
    the simulation manager deals with the simulation itself
    its like an extension of the controller but only for the simulation stuff
    there exists one simulation manager for each simulation
     */
    private final int timeLimit;
    private final int maxProcessingTime;
    private final int minProcessingTime;
    private final int minArrTime;
    private final int maxArrTime;
    private final int numberOfServers;
    private final int numberOfClients;
    private final Scheduler scheduler; //class responsible for queue management and distribution
    private final List<Task> generatedTasks; //the clients that are to be distributed
    private final List<Task> waitingTasks;

    private PageController controller;

    private int idCount;

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int minArrTime, int maxArrTime, int numberOfServers, int numberOfClients) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.minArrTime = minArrTime;
        this.maxArrTime = maxArrTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        this.generatedTasks=Collections.synchronizedList(new ArrayList<Task>());
        this.waitingTasks=Collections.synchronizedList(new ArrayList<Task>());
        this.idCount=0;

        scheduler =new Scheduler(this.numberOfServers,SelectionPolicy.SHORTEST_TIME,timeLimit);

        generateNRandomTasks();
    }

    private void generateNRandomTasks(){
        //generate random tasks then sort them by their arrival time
        //and set the ids in order
        for(int i=0;i<numberOfClients;i++){
            int arr= (int) Math.floor(Math.random()*(maxArrTime-minArrTime)+minArrTime);
            int proc=(int) Math.floor(Math.random()*(maxProcessingTime-minProcessingTime)+minProcessingTime);;

            Task t=new Task(this.idCount,arr,proc);
            generatedTasks.add(t);
            waitingTasks.add(t);
            this.idCount++;
        }
        generatedTasks.sort(new TaskComparator());
        waitingTasks.sort(new TaskComparator());

        for(int i=0;i<generatedTasks.size();i++){
            Task t=generatedTasks.get(i);
            t.setID(i);
        }
    }

    @Override
    public void run() {
        controller.setSimRunning(true);
        int time=0;
        int peakHour=0;
        int maxClients=0;
        int clientsCount;
        List<Server> servers=scheduler.getServers();


        controller.appendSimulation("Task: ([id], [arrival time], [service time])\n\n");
        controller.appendSimulation("Client Nr: "+numberOfClients+"\n");
        controller.appendSimulation("Queue Nr: "+numberOfServers+"\n");
        controller.appendSimulation("Simulation Time: "+timeLimit+"\n");
        controller.appendSimulation("Arrival Time: "+minArrTime+" - "+maxArrTime+"\n");
        controller.appendSimulation("Service Time: "+minProcessingTime+" - "+maxProcessingTime+"\n\n");

        while(time<timeLimit){

            //if there are no tasks waiting and all queues are empty then end the simulation
            if(waitingTasks.isEmpty()){
                boolean end=true;
                for(Server s:servers){
                    if(!s.getTasks().isEmpty()){
                        end=false;
                        break;
                    }
                }
                if(end)
                    break;
            }

            //go through the tasks and if they must arrive at this time, give them to the dispatcher
            for(Task t:generatedTasks){
                if(t.getArrivalTime()!=time)
                    continue;

                scheduler.dispatchTask(t);
                waitingTasks.remove(t);
            }
            controller.appendSimulation("Time "+time+": \n");

            controller.appendSimulation("Waiting: ");
            int c=0;
            for(Task t:waitingTasks){ //print waiting tasks
                if(c%5==0)
                    controller.appendSimulation("\n");
                c++;
                controller.appendSimulation(t+", ");
            }
            controller.appendSimulation("\n");


            //print each server and its tasks
            clientsCount=0;
            for(int i=0;i<servers.size();i++){
                boolean printed=false;

                Server s=servers.get(i);
                clientsCount+=s.getTasks().size();
                if(clientsCount>maxClients){
                    maxClients=clientsCount;
                    peakHour=time;
                }

                controller.appendSimulation("Queue "+(i)+": ");
                List<Task> tasks=s.getTasks();
                for(Task t:tasks){
                    if(printed)
                        controller.appendSimulation(", ");
                    printed=true;
                    controller.appendSimulation(t.toString());
                }
                controller.appendSimulation("\n");
            }
            controller.appendSimulation("==============================\n");

            time++;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //calculate average waiting time
        double waitingTime=0;
        for(Server s:servers){
            waitingTime+=s.getWaitingTime();
        }
        waitingTime/=generatedTasks.size();

        //calculate average service time
        double serviceTime=0;
        int count=0;
        for(Server s:servers){
            serviceTime+=s.getTimeServiced();
            count+=s.getTasksServiced();
        }

        if(count>0)
            serviceTime/=count;


        controller.appendSimulation("Average Waiting Time: " + String.format( "%.2f\n", waitingTime ) );
        controller.appendSimulation("Average Service Time: " + String.format( "%.2f\n", serviceTime ) );
        controller.appendSimulation("Peak Hour: " + peakHour);

        controller.appendSimulation("\n\nEnd of Simulation\n");
        controller.setSimRunning(false);
    }

    public void setController(PageController controller){
        this.controller=controller;
    }
}
