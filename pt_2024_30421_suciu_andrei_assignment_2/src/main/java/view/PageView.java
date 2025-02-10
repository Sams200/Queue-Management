package view;

import controller.PageController;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PageView extends JPanel{
    private PageController controller;

    //fonts
    private final Font fontLargeTitle=new Font("Bauhaus 93",Font.PLAIN,55);
    private final Font fontAuthor=new Font("Bauhaus 93",Font.PLAIN,30);
    private final Font fontMath=new Font("Cascadia Code",Font.PLAIN,20);
    ////////////////////////

    //dimensions
    private final Dimension pageDimension=new Dimension(1250,750);
    private final Dimension pageMin=new Dimension(1250,500);
    ///////////////////

    //borders
    private Border borderRaised=BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private Border borderLowered=BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    private Border borderRed=BorderFactory.createLineBorder(Color.RED,5,true);
    private Border borderBlue=BorderFactory.createLineBorder(Color.BLUE,5,true);
    private Border borderGreen=BorderFactory.createLineBorder(Color.GREEN,5,true);
    /////////////////////////////////

    //main page
    private JFrame frame = new JFrame("Queue Simulator");
    private JPanel page = new JPanel();
    private JScrollPane pane = new JScrollPane(page, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel top = new JPanel();
    private JPanel mid = new JPanel();
    private JPanel bottom = new JPanel();
    //////////////////////////////

    private JTextField threadNr=new JTextField(15);
    private JTextField clientNr=new JTextField(15);
    private JTextField simulationTime=new JTextField(15);
    private JTextField minArrivalTime=new JTextField(5);
    private JTextField maxArrivalTime=new JTextField(5);
    private JTextField minServiceTime=new JTextField(5);
    private JTextField maxServiceTime=new JTextField(5);
    private JTextArea simulationTxt=new JTextArea(10,60);


    public PageView(PageController pageController){
        //initialize everything
        this.controller = pageController;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(pageDimension);
        frame.setContentPane(pane);

        page.setLayout(new BorderLayout());
        page.add(top, BorderLayout.PAGE_START);
        page.add(mid, BorderLayout.CENTER);
        page.add(bottom, BorderLayout.PAGE_END);

        page.setMinimumSize(pageMin);
        pane.setMinimumSize(pageMin);
        //////////////////////////////////

        setTopLayout();
        setMidLayout();
    }

    private void setTopLayout(){
        JPanel middlePanel=new JPanel();

        top.setLayout(new BoxLayout(top,BoxLayout.LINE_AXIS));

        middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.PAGE_AXIS));
        JLabel titleLabel=new JLabel("Queue Simulator");
        titleLabel.setFont(fontLargeTitle);
        titleLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        middlePanel.add(titleLabel);

        JLabel authorLabel=new JLabel("made by Suciu Andrei");
        authorLabel.setFont(fontAuthor);
        authorLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        middlePanel.add(authorLabel);

        top.add(Box.createHorizontalGlue());
        top.add(middlePanel);
        top.add(Box.createHorizontalGlue());

        top.setBorder(borderRaised);
        top.addMouseListener(makeDynamicButton(top,1));
    }

    private void setMidLayout(){
        JPanel settingsPanel=new JPanel();
        JPanel simulationPanel=new JPanel();
        JPanel simBuf=new JPanel();
        JScrollPane simulationPane=new JScrollPane(simBuf,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel settingsBuf=new JPanel();
        settingsBuf.setBorder(borderRaised);
        //simulationPanel.setBorder(borderRaised);
        JPanel runSim=new JPanel();
        runSim.setBorder(borderRaised);
        runSim.setLayout(new BorderLayout());
        runSim.add(Box.createVerticalStrut(5),BorderLayout.PAGE_START);
        //runSim.add(Box.createVerticalStrut(5),BorderLayout.PAGE_END);
        runSim.add(Box.createVerticalStrut(5),BorderLayout.LINE_START);
        runSim.add(Box.createVerticalStrut(5),BorderLayout.LINE_END);

        JLabel runLabel=new JLabel("Run Simulation");
        runLabel.setFont(fontLargeTitle);
        runLabel.setHorizontalAlignment(SwingConstants.CENTER);
        runSim.add(runLabel,BorderLayout.CENTER);
        runSim.addMouseListener(makeDynamicButton(runSim,2));
        runSim.setMaximumSize(new Dimension(600,80));

        mid.setLayout(new BoxLayout(mid,BoxLayout.Y_AXIS));
        //mid.add(Box.createVerticalStrut(20));
        mid.add(settingsBuf);
        mid.add(runSim);
        makeSettings(settingsBuf,settingsPanel);
        //mid.add(Box.createVerticalStrut(50));


        simulationPanel.setLayout(new BorderLayout());
        simulationPanel.add(Box.createHorizontalStrut(80),BorderLayout.LINE_START);
        simulationPanel.add(Box.createHorizontalStrut(80),BorderLayout.LINE_END);

        mid.add(simulationPanel);
        simulationPanel.add(simulationPane,BorderLayout.CENTER);
        simulationPane.setBorder(borderRaised);
        mid.add(Box.createVerticalGlue());


        simBuf.setLayout(new BorderLayout());
        simBuf.add(Box.createHorizontalStrut(30),BorderLayout.LINE_START);
        simBuf.add(simulationTxt,BorderLayout.CENTER);
        simBuf.add(Box.createHorizontalStrut(30),BorderLayout.LINE_END);


        //simulationTxt.setBorder(borderRaised);
        simulationTxt.setFont(fontMath);
        simulationTxt.setText("Simulation:");
        simulationTxt.setForeground(Color.GRAY);
        makePlaceholderTxt(simulationTxt,"Simulation:");
        simulationTxt.setEditable(false);

    }

    private void makeSettings(JPanel settingsBuf,JPanel settingsPanel){
        settingsBuf.setLayout(new BorderLayout());
        settingsBuf.add(Box.createVerticalStrut(20),BorderLayout.PAGE_START);
        settingsBuf.add(settingsPanel,BorderLayout.CENTER);
        settingsBuf.add(Box.createVerticalStrut(20),BorderLayout.PAGE_END);
        settingsBuf.setMaximumSize(new Dimension(600,1000));

        settingsPanel.setMaximumSize(new Dimension(600,1000));



        JPanel fields=new JPanel();
        JPanel labels=new JPanel();


        boxLayout(settingsPanel,BoxLayout.LINE_AXIS);
        settingsPanel.add(Box.createHorizontalStrut(50));
        settingsPanel.add(labels);
        settingsPanel.add(Box.createHorizontalStrut(20));
        settingsPanel.add(fields);
        settingsPanel.add(Box.createHorizontalGlue());

//        fields.setBorder(borderRed);
//        labels.setBorder(borderBlue);
        labels.setMinimumSize(new Dimension(30,600));

        labels.add(Box.createVerticalStrut(20));
        boxLayout(labels,BoxLayout.PAGE_AXIS);
        JLabel labelClient=new JLabel("Client Nr.");
        labelClient.setFont(fontMath);
        labels.add(labelClient);
        labels.add(Box.createVerticalStrut(60));
        JLabel labelThread=new JLabel("Queue Nr.");
        labelThread.setFont(fontMath);
        labels.add(labelThread);
        labels.add(Box.createVerticalStrut(60));
        JLabel labelSim=new JLabel("Simulation Time");
        labelSim.setFont(fontMath);
        labels.add(labelSim);
        labels.add(Box.createVerticalStrut(60));
        JLabel labelArr=new JLabel("Arrival Time");
        labelArr.setFont(fontMath);
        labels.add(labelArr);
        labels.add(Box.createVerticalStrut(60));
        JLabel labelSer=new JLabel("Service Time");
        labelSer.setFont(fontMath);
        labels.add(labelSer);
        labels.add(Box.createVerticalGlue());

        boxLayout(fields,BoxLayout.PAGE_AXIS);
        JPanel cl=new JPanel();
        cl.add(clientNr);
        fields.add(cl);
        labels.add(Box.createVerticalStrut(10));

        JPanel thr=new JPanel();
        thr.add(threadNr);
        fields.add(thr);
        labels.add(Box.createVerticalStrut(10));

        JPanel sim=new JPanel();
        sim.add(simulationTime);
        fields.add(sim);
        labels.add(Box.createVerticalStrut(10));

        JPanel arr=new JPanel();
        arr.add(minArrivalTime);
        JLabel dash=new JLabel("  -  ");
        dash.setFont(fontMath);
        arr.add(dash);
        arr.add(maxArrivalTime);
        fields.add(arr);
        labels.add(Box.createVerticalStrut(10));

        JPanel ser=new JPanel();
        ser.add(minServiceTime);
        JLabel dash2=new JLabel("  -  ");
        dash2.setFont(fontMath);
        ser.add(dash2);
        ser.add(maxServiceTime);
        fields.add(ser);


    }


    public void setVisibility(boolean isVisible){
        frame.setVisible(isVisible);
    }

    private MouseAdapter makeDynamicButton(JPanel panel,int method){
        MouseAdapter mouseAdapter=new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panel.setBorder(borderLowered);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                switch (method){
                    case 1:
                        controller.openDocumentation();
                        break;
                    case 2:
                        controller.runSimulation();
                        break;
                    default:
                        break;
                }
                panel.setBorder(borderRaised);
            }
        };
        return mouseAdapter;
    }

    private void boxLayout(JPanel panel,int axis){
        panel.setLayout(new BoxLayout(panel,axis));
    }

    public String getThreadNr(){
        return threadNr.getText();
    }
    public String getClientNr(){
        return clientNr.getText();
    }
    public String getSimTime(){
        return simulationTime.getText();
    }
    public String getMinArr(){
        return minArrivalTime.getText();
    }
    public String getMaxArr(){
        return maxArrivalTime.getText();
    }
    public String getMinSer(){
        return minServiceTime.getText();
    }
    public String getMaxSer(){
        return maxServiceTime.getText();
    }


    public void setSimulation(ArrayList<String> lines){
        simulationTxt.setText("");
        for(String s:lines){
            simulationTxt.append(s);
        }
    }
    public String[] getSimulation(){
        return simulationTxt.getText().split("\n");
    }

    private static void makePlaceholderTxt(JTextArea field, String txt){
        FocusListener focusListener=new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(txt)) {
                    //field.setText("");
                    field.setForeground(Color.GRAY);
                }
                else
                    field.setForeground(Color.WHITE);
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().equals(txt)) {
                    field.setForeground(Color.GRAY);
                    field.setText(txt);
                }
            }
        };
        field.addFocusListener(focusListener);
    }
}
