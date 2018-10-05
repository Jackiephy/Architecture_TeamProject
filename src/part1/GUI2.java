
package part1;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.swing.*;


public class GUI extends JFrame{
	public Frame f;
	
	private JButton openfile;
	private BufferedReader br;
	public JTextArea ta;
	private JButton execute;
	private JButton IPL;
	private JButton saveNum;
	private JLabel console;
	public StringBuilder text;
	private JButton stepByStep;
	private JButton clear;
	
	
	private JLabel pc,cc,IR,MAR,MBR,MSR,MFR,IAR,IRR,R0,R1,R2,R3,X1,X2,X3 = null;
	public JTextField t_pc,t_cc,t_IR,t_MAR,t_MBR,t_MSR,t_MFR,t_IAR,t_IRR,t_R0,t_R1,t_R2,t_R3,t_X1,t_X2,t_X3 = null;
	public JTextField [][] indicate;
	private JPanel jp;
	private JPanel jpProcessState;
	private JPanel textfeild;
	private JPanel binaryIndicate;
	
    private FileDialog openFile;
    
    private boolean state = true;
    
    private String getTextPC, getTextR0, getTextR1, getTextR2, getTextR3, getTextX1, getTextX2, getTextX3, getTextMAR, getTextMBR, getTextIR;
  //private String getKey, getValue;
    
  //  Memory mainMemoryStore = new Memory();
   
	
	 
    public GUI() {
    	 
        init();
        addEvents();
        
   
    }
	
    public void init() {
    	  f = new JFrame("CS6461 Computer");
    	  jp = new JPanel();
    	  jpProcessState = new JPanel();
    	  textfeild = new JPanel();
    	  binaryIndicate = new JPanel();
   
        openfile = new JButton("Open File");
        openfile.setBounds(30, 80, 100, 30);
        
        execute = new JButton("Execute");
        execute.setBounds(130, 80, 100, 30);
        
        stepByStep = new JButton("StepByStep");
        stepByStep.setBounds(230, 80, 100, 30);
        
        clear = new JButton("clear");
        clear.setBounds(280, 460, 80, 30);

        
        IPL = new JButton("IPL");
        IPL.setBounds(30, 20, 60, 50);
        IPL.setBackground(Color.red);
        
        console = new JLabel("Console");
        console.setBounds(30,130,100,25);
        
        ta=new JTextArea(20,20);
        ta.setBounds(30, 150, 330, 300);
        ta.setBackground(Color.black);
        ta.setForeground(Color.white);
 
        
        pc = new JLabel("PC");
        pc.setBounds(0, 0, 100, 50);
        cc = new JLabel("cc");
        cc.setBounds(0, 25, 100, 50);
        IR = new JLabel("IR");
        IR.setBounds(0, 50, 100, 50);
        MAR = new JLabel("MAR");
        MAR.setBounds(0, 75, 100, 50);
        MBR = new JLabel("MBR");
        MBR.setBounds(0, 100, 100, 50);
        MSR = new JLabel("MSR");
        MSR.setBounds(0, 125, 100, 50);
        MFR = new JLabel("MFR");
        MFR.setBounds(0, 150, 100, 50);
        IAR = new JLabel("IAR");
        IAR.setBounds(0, 175, 100, 50);
        IRR = new JLabel("IRR");
        IRR.setBounds(0, 200, 100, 50);
        R0 = new JLabel("R0");
        R0.setBounds(0, 225, 100, 50);
        R1 = new JLabel("R1");
        R1.setBounds(0, 250, 100, 50);
        R2 = new JLabel("R2");
        R2.setBounds(0, 275, 100, 50);
        R3 = new JLabel("R3");
        R3.setBounds(0, 300, 100, 50);
        X1 = new JLabel("X1");
        X1.setBounds(0, 325, 100, 50);
        X2 = new JLabel("X2");
        X2.setBounds(0, 350, 100, 50);
        X3 = new JLabel("X3");
        X3.setBounds(0, 375, 100, 50);
        
        t_pc = new JTextField() ;
        t_pc.setBounds(10, 10, 100, 25);
        t_cc = new JTextField() ;
        t_cc.setBounds(10, 35, 100, 25);
        t_IR = new JTextField() ;
        t_IR.setBounds(10, 60, 100, 25);
        t_MAR = new JTextField() ;
        t_MAR.setBounds(10, 85, 100, 25);
        t_MBR = new JTextField() ;
        t_MBR.setBounds(10, 110, 100, 25);
        t_MSR = new JTextField() ;
        t_MSR.setBounds(10, 135, 100, 25);
        t_MFR = new JTextField() ;
        t_MFR.setBounds(10, 160, 100, 25);
        t_IAR = new JTextField() ;
        t_IAR.setBounds(10, 185, 100, 25);
        t_IRR = new JTextField() ;
        t_IRR.setBounds(10, 210, 100, 25);
        t_R0 = new JTextField() ;
        t_R0.setBounds(10, 235, 100, 25);
        t_R1 = new JTextField() ;
        t_R1.setBounds(10, 260, 100, 25);
        t_R2 = new JTextField() ;
        t_R2.setBounds(10, 285, 100, 25);
        t_R3 = new JTextField() ;
        t_R3.setBounds(10, 310, 100, 25);
        t_X1 = new JTextField() ;
        t_X1.setBounds(10, 335, 100, 25);
        t_X2 = new JTextField() ;
        t_X2.setBounds(10, 360, 100, 25);
        t_X3 = new JTextField() ;
        t_X3.setBounds(10, 385, 100, 25);
        
        saveNum = new JButton("SaveNumbers");
        saveNum.setBounds(0, 430, 125, 25);
        
        indicate = new JTextField[16][16] ;
      
//     	for(int i=0;i<10;i++){
//     		   indicate[i][0]= new JTextField(" ");
//     		   indicate[i][0].setBounds(15*i, 10, 13, 25);
//     		   binaryIndicate.add(indicate[i][0]);
//     	   }
//     	for(int i=0;i<4;i++){
//  		   indicate[i][1]= new JTextField(" ");
//  		   indicate[i][1].setBounds(15*i, 10+25*1, 13, 25);
//  		   binaryIndicate.add(indicate[i][1]);
//  		   indicate[i][6]= new JTextField(" ");
//		   indicate[i][6].setBounds(15*i, 10+25*6, 13, 25);
//		   binaryIndicate.add(indicate[i][6]);
//  	   }
//     	
//        for(int j = 2;j<16;j++){
//    	   for(int i=0;i<16;i++){
//    		   if(j!=6){
//    		   indicate[i][j]= new JTextField(" ");
//    		   indicate[i][j].setBounds(15*i, 10+25*j, 13, 25);
//    		   binaryIndicate.add(indicate[i][j]);
//    		   }
//    	   }
//       }
      for(int j = 0;j<16;j++){
  	   for(int i=0;i<16;i++){
  		 
  		   indicate[i][j]= new JTextField(" ");
  		   indicate[i][j].setBounds(20*i, 10+25*j, 20, 25);
  		   binaryIndicate.add(indicate[i][j]);
  		   
  	   }
     }
        
        f.setBounds(200,100,1000, 550);
        f.setLayout(null);
        f.setVisible(true);  

        jp.setLayout(null);
        jp.setVisible(true);
        jp.setBounds(0,0,400, 550);
        jp.add(openfile);
        jp.add(execute);
        jp.add(stepByStep);
        jp.add(clear);
        jp.add(IPL);
        jp.add(ta);
        jp.add(console);
        
        
       
        
        jpProcessState.add(pc);
        jpProcessState.add(cc);
        jpProcessState.add(IR);
        jpProcessState.add(MAR);
        jpProcessState.add(MBR);
        jpProcessState.add(MSR);
        jpProcessState.add(MFR);
        jpProcessState.add(IAR);
        jpProcessState.add(IRR);
        jpProcessState.add(R0);
        jpProcessState.add(R1);
        jpProcessState.add(R2);
        jpProcessState.add(R3);
        jpProcessState.add(X1);
        jpProcessState.add(X2);
        jpProcessState.add(X3);
        
        jpProcessState.setLayout(null);
        jpProcessState.setVisible(true); 
        jpProcessState.setBounds(400,0,50, 550);
        
       
        textfeild.add(t_pc);
        textfeild.add(t_cc);
        textfeild.add(t_IR);
        textfeild.add(t_MAR);
        textfeild.add(t_MBR);
        textfeild.add(t_MSR);
        textfeild.add(t_MFR);
        textfeild.add(t_IAR);
        textfeild.add(t_IRR);
        textfeild.add(t_R0);
        textfeild.add(t_R1);
        textfeild.add(t_R2);
        textfeild.add(t_R3);
        textfeild.add(t_X1);
        textfeild.add(t_X2);
        textfeild.add(t_X3);
        textfeild.add(saveNum);
        
        textfeild.setVisible(true); 
        textfeild.setLayout(null); 
        textfeild.setBounds(450,0,150, 550);
        
        
        
        
        
        
        binaryIndicate.setVisible(true); 
        binaryIndicate.setLayout(null); 
        binaryIndicate.setBounds(600,0,400, 550);
        
        f.add(jp);
        f.add(jpProcessState);
        f.add(textfeild);
        f.add(binaryIndicate);
       
    }
	
    public void addEvents(){
    	 CPU cpu = new CPU();
    	    Console con = new Console();
    	 f.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
                 System.exit(0);
             }
         });
 
    	 
    	 openfile.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent e) {
            	 if(state==true){
                 openFile=new FileDialog(f, "open file", FileDialog.LOAD);
                 openFile.setVisible(true);
                 String dirName=openFile.getDirectory();
                 String fileName=openFile.getFile();
                 System.out.println(dirName);
                 
                 if(dirName==null || fileName==null){
                     return;
                 }
                 File file=new File(dirName,fileName);
                 try {
                     br=new BufferedReader(new FileReader(file));
                     String line;
                     text = new StringBuilder();
                     while((line=br.readLine()) != null){
                         text.append(line);
                         text.append("\r\n");
            	 
                     }
                     ta.setText(text.toString());
                     
                 } catch (Exception e1) {
                     e1.printStackTrace();
                 }
                 
             }
            }	 
         });
    	 
    	 execute.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent e) {
            	 if(state==true){
//            	 t_pc.setText(Integer.toString( cpu.getPC()));
//            	 t_cc.setText(cpu.getCC());
//            	 t_IR.setText(cpu.getIR());
//            	 t_MAR.setText(Integer.toString( cpu.getMAR()));
//            	 t_MBR.setText(cpu.getMBR());
//            	 t_MSR.setText(cpu.getMSR());
//            	 t_MFR.setText(Integer.toString( cpu.getMFR()));
//            	 t_IAR.setText(Integer.toString( cpu.getIAR()));
//            	 t_IRR.setText(cpu.getIRR());
//            	 t_R0.setText(cpu.getR0());
//            	 t_R1.setText(cpu.getR1());
//            	 t_R2.setText(cpu.getR2());
//            	 t_R3.setText(cpu.getR3());
//            	 t_X1.setText(Integer.toString( cpu.getX1()));
//            	 t_X2.setText(Integer.toString( cpu.getX2()));
//            	 t_X3.setText(Integer.toString( cpu.getX3()));
            	 
            	 System.out.println("Run");
	                con.Run(6);
            	 }
            	
             }
         });
    	 
    	 IPL.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent e) {
            	 state = true;
            	 IPL.setBackground(Color.green);
            	con.initial();
             }
         });
    	 
    	 stepByStep.addActionListener(new ActionListener() {
	           
	            public void actionPerformed(ActionEvent e) {
	                System.out.println("Sin");
	                con.Single();
	            }
    	 });
    	 
    	 clear.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent e) {
            	 System.out.println("Clear");
            	 con.ConsoleString = "";
            	 ta.setText("");
             }
    	 });
    	 
    	 saveNum.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent e) {
    	 getTextPC = t_pc.getText();
         getTextR0 = t_R0.getText();
         getTextR1 = t_R1.getText();
         getTextR2 = t_R2.getText();
         getTextR3 = t_R3.getText();
         getTextX1 = t_R1.getText();
         getTextX2 = t_R2.getText();    
         getTextX3 = t_R3.getText();
         getTextMAR = t_MAR.getText();
         getTextMBR = t_MBR.getText();
         getTextIR = t_IR.getText();
         
         System.out.println(getTextPC);
         System.out.println(getTextR0);
         con.ShowNumberR(getTextR0, getTextR1, getTextR2, getTextR3, cpu.getR0(), cpu.getR1(), cpu.getR2(), cpu.getR3(), false);
         con.ShowNumberX(getTextX1, getTextX2, getTextX3, cpu.getX1(), cpu.getX2(), cpu.getX3(), false);
         con.ShowNumberO(getTextPC, getTextMAR, getTextMBR, getTextIR, cpu.getPC(), cpu.getMAR(), cpu.getMBR(), cpu.getIR(), false);
         con.Continue = false;
             }
    	 });
    	 
    }
    

}
