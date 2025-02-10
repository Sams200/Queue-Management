package main;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import controller.PageController;
import view.PageView;

import java.awt.*;

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
}