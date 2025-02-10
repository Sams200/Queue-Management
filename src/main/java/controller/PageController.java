package controller;

import view.PageView;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PageController {
    private PageView view;
    private boolean simRunning;
    private File file;
    private FileWriter writer;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");


    public PageController(){
        this.simRunning=false;
    }

    public void setView(PageView view){
        this.view=view;
    }

    public void openDocumentation(){
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File("./DOCUMENTATION.docx"));
            }
        } catch (Exception e) {
            //either file doesn't exist or we can't open for some reason
            //lets pretend nothing happened
            System.out.println("Error\nDocumentation not found");
        }
    }

    public void runSimulation() {
        if(simRunning)
            return;

        //clear the simulation
        view.setSimulation("");
        simRunning=true;

        int clientNr=0;
        int queueNr=0;
        int simTime=0;

        int minArr=0;
        int maxArr=0;
        int minSer=0;
        int maxSer=0;
        try{
            clientNr=Integer.parseUnsignedInt(view.getClientNr());
            queueNr=Integer.parseUnsignedInt(view.getThreadNr());
            simTime=Integer.parseUnsignedInt(view.getSimTime());

            minArr=Integer.parseUnsignedInt(view.getMinArr());
            maxArr=Integer.parseUnsignedInt(view.getMaxArr());
            minSer=Integer.parseUnsignedInt(view.getMinSer());
            maxSer=Integer.parseUnsignedInt(view.getMaxSer());
        }
        catch (NumberFormatException e){
            view.setSimulation("Invalid Input! Only Positive Integers are Allowed");
            simRunning=false;
            return;
        }

        if(minArr>maxArr || minSer>maxSer){
            view.setSimulation("Invalid Configuration");
            simRunning=false;
            return;
        }

        //if everything is valid then we can start the simulation
        simRunning=true;
        SimulationManager gen=new SimulationManager(simTime,maxSer,minSer,minArr,maxArr,queueNr,clientNr);
        gen.setController(this);
        Thread t=new Thread(gen);
        t.start();

        //make the file with the output
        try {
            LocalDateTime now = LocalDateTime.now();
            file = new File(".\\simulation\\Sim"+dtf.format(now)+".txt");
            if (!file.createNewFile()) {
                throw new IOException();
            }

        } catch (IOException ignored) {
            //System.out.println("Couldn't make file");
        }

    }

    public void setSimRunning(boolean simRunning){
        this.simRunning=simRunning;
    }

    public void setSimulation(String txt){
        view.setSimulation(txt);
        try {
            if(file == null)
                throw new IOException();
            writer=new FileWriter(file);
            writer.write(txt);
            writer.close();
        } catch (IOException ignored) {
            //System.out.println("Couldn't write");
        }
    }

    public void appendSimulation(String txt){
        view.appendSimulation(txt);
        try {
            if(file == null)
                throw new IOException();
            writer=new FileWriter(file,true);
            writer.append(txt);
            writer.close();
        } catch (IOException ignored) {
            //System.out.println("Couldn't write");
        }
    }
}
