package model;

import java.util.Comparator;

public class Task {
    /*
    a simple task
    it has an id, an arrival time, and a service time
     */
    private int ID;
    private int arrivalTime;
    private int serviceTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }


    public boolean equals(Object o){
        if(! (o instanceof Task))
            return false;

        if(o==this)
            return true;

        Task t= (Task) o;

        return true;
    }

    public int hashCode(){
        return 5;
    }

    public int getID() {
        return ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
    public void setID(int id){
        this.ID=id;
    }

    public String toString(){
        return "( " + this.ID + ", " + (this.arrivalTime) + ", " + this.serviceTime + ")";
    }

}
