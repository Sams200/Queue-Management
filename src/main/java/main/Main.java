package main;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import controller.PageController;
import controller.SimulationManager;
import model.Task;
import view.PageView;

import java.util.HashMap;

public class Main{
    public static void main(String[] args){
        //testing();
        setupApp();
    }

    public static void setupApp(){
        FlatMacDarkLaf.setup();

        PageController pageController=new PageController();
        PageView pageView=new PageView(pageController);
        pageView.setVisibility(true);
        pageController.setView(pageView);
    }

    public static void testing(){
        SimulationManager gen=new SimulationManager(20,6,4,2,15,4,20);
        Thread t=new Thread(gen);
        t.start();

        HashMap<Integer,Integer> hashMap=new HashMap<>();
        //hashMap.
    }
}