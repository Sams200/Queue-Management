package controller;

import view.PageView;

import java.awt.*;
import java.io.File;

public class PageController {
    private PageView view;

    public PageController(){

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
        System.out.println("Running Simulation");
    }
}
