package part1;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.swing.*;


public class GUI extends JFrame{
	private Frame f;
	
	private JButton openfile;
	private MenuItem show;
	private BufferedReader br;
	private JTextArea ta;
	private JButton execute;
	private StringBuilder text;
	
	private JTextArea text_console;  //NEW BY Alina
	private JLabel pc,cc,IR,MAR,MBR,MSR,MFR,IAR,IRR,R0,R1,R2,R3,X1,X2,X3 = null;
	private JTextField t_pc,t_cc,t_IR,t_MAR,t_MBR,t_MSR,t_MFR,t_IAR,t_IRR,t_R0,t_R1,t_R2,t_R3,t_X1,t_X2,t_X3 = null;
	private JPanel jp;
	private JPanel jpProcessState;
	private JPanel textfeild;
	private String getTextPC, getTextR0, getTextR1, getTextR2, getTextR3, getTextX1, getTextX2, getTextX3, getTextMAR, getTextMBR, getTextIR; //NEW BY Alina
	boolean Single = false, Continue = true; //NEW BY Alina
	public String ConsoleString = ""; //NEW BY Alina
    private FileDialog openFile; //NEW BY Alina
    
    Memory mainMemoryStore = new Memory();
    CPU cpu = new CPU();
	
	 
    public GUI() {
    	 
        init();
        addEvents();
        
   
    }
	
    public void init() {
    	f = new JFrame("CS6461 Computer");
    	jp = new JPanel();
    	jpProcessState = new JPanel();
    	textfeild = new JPanel();
    	MenuBar menuBar = new MenuBar();
        // 鑿滃崟
    	Menu menu = new Menu("choose");
        // 鑿滃崟椤�
    	show=new MenuItem("project1");
        // 鑿滃崟娣诲姞鑿滃崟椤�
        menu.add(show);
        // 鑿滃崟鏍忔坊鍔犺彍鍗�
        menuBar.add(menu);
        f.setMenuBar(menuBar);
        //鏂囨湰鍩�
       
     

        
        openfile = new JButton("Open File");
        openfile.setBounds(30, 20, 100, 50);
        
        execute = new JButton("Execute");
        execute.setBounds(130, 20, 100, 50);
        
        ta=new JTextArea(20,20);
        ta.setBounds(30, 100, 200, 300);
        
        text_console=new JTextArea(30,30);
        text_console.setBounds(40,100,200,300);
 
        
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
        
        t_pc = new JTextField(30) ;
        t_pc.setBounds(0, 10, 100, 25);
        t_cc = new JTextField() ;
        t_cc.setBounds(0, 35, 100, 25);
        t_IR = new JTextField() ;
        t_IR.setBounds(0, 60, 100, 25);
        t_MAR = new JTextField() ;
        t_MAR.setBounds(0, 85, 100, 25);
        t_MBR = new JTextField() ;
        t_MBR.setBounds(0, 110, 100, 25);
        t_MSR = new JTextField() ;
        t_MSR.setBounds(0, 135, 100, 25);
        t_MFR = new JTextField() ;
        t_MFR.setBounds(0, 160, 100, 25);
        t_IAR = new JTextField() ;
        t_IAR.setBounds(0, 185, 100, 25);
        t_IRR = new JTextField() ;
        t_IRR.setBounds(0, 210, 100, 25);
        t_R0 = new JTextField() ;
        t_R0.setBounds(0, 235, 100, 25);
        t_R1 = new JTextField() ;
        t_R1.setBounds(0, 260, 100, 25);
        t_R2 = new JTextField() ;
        t_R2.setBounds(0, 285, 100, 25);
        t_R3 = new JTextField() ;
        t_R3.setBounds(0, 310, 100, 25);
        t_X1 = new JTextField() ;
        t_X1.setBounds(0, 335, 100, 25);
        t_X2 = new JTextField() ;
        t_X2.setBounds(0, 360, 100, 25);
        t_X3 = new JTextField() ;
        t_X3.setBounds(0, 385, 100, 25);
        
       
        f.add(jp);
        f.add(jpProcessState);
        f.add(textfeild);
       
        
        f.setBounds(500,300,700, 550);
        f.setLayout(null);
        f.setVisible(true);  

        jp.setLayout(null);
        jp.setVisible(false);
        jp.setBounds(0,0,400, 550);
        jp.add(openfile);
        jp.add(execute);
        jp.add(ta);
        
        
        jpProcessState.setLayout(null);
        jpProcessState.setVisible(false); 
        jpProcessState.setBounds(400,0,200, 550);
        
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
        
        textfeild.setVisible(false); 
        textfeild.setLayout(null); 
        textfeild.setBounds(450,0,350, 550);
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
        
    }
	
    public void addEvents(){
    	 f.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
                 System.exit(0);
             }
         });
    	 
    	 show.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent e) {
            	 jpProcessState.setVisible(true); 
            	 jp.setVisible(true);
            	 textfeild.setVisible(true);
             }
         });
    	 
    	 
    	 openfile.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent e) {
                 openFile=new FileDialog(f, "open file", FileDialog.LOAD);
                 openFile.setVisible(true);
                 String dirName=openFile.getDirectory();
                 String fileName=openFile.getFile();
                 System.out.println(dirName);
                 
                 //璇诲彇灞曠ず鏂囦欢
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
         });
    	 
    	 execute.addActionListener(new ActionListener() {
             
    		 public void actionPerformed(ActionEvent e) {
	                getTextPC = t_pc.getText();
	                getTextR0 = t_R0.getText();
	                getTextR1 = t_R1.getText();
	                getTextR2 = t_R2.getText();
	                getTextR3 = t_R3.getText();
	                getTextX1 = t_X1.getText();
	                getTextX2 = t_X2.getText();    
	                getTextX3 = t_X3.getText();
	                getTextMAR = t_MAR.getText();
	                getTextMBR = t_MBR.getText();
	                getTextIR = t_IR.getText();
	                
	              //NEW BY Alina
	                System.out.println(getTextPC);
	                System.out.println(getTextR0);
	                ShowNumberR(getTextR0, getTextR1, getTextR2, getTextR3, cpu.getR0(), cpu.getR1(), cpu.getR2(), cpu.getR3(), false);
	                ShowNumberX(getTextX1, getTextX2, getTextX3, cpu.getX1(), cpu.getX2(), cpu.getX3(), false);
	                ShowNumberO(getTextPC, getTextMAR, getTextMBR, getTextIR, cpu.getPC(), cpu.getMAR(), cpu.getMBR(), cpu.getIR(), false);
	                Continue = false;
	            }
         });
    	 
    }
    
  //NEW BY Alina
    private void ShowNumberO(String TextPC, String TextMAR, String TextMBR, String TextIR, int CPU_PC, int CPU_MAR, String CPU_MBR, String CPU_IR, boolean showO){
        int NumPC, NumMAR, NumMBR, NumIR;
        //show PC
        System.out.println("A");
        if(!(TextPC.equals(""))){
            NumPC = Integer.parseInt(TextPC);
            if(NumPC>0 && NumPC<4096){
                NumPC = NumPC;
                cpu.setPC(NumPC);                
                ConsoleString = ConsoleString + "\r\nchange PC to "+TextPC;
                this.text_console.setText(ConsoleString);
            }
            else{
                NumPC = CPU_PC;
            }
        }
        else{
            NumPC = CPU_PC;
        }
        System.out.println("B");
        
        //show MAR
        if(!(TextMAR.equals(""))){
            NumMAR = Integer.parseInt(TextMAR);
            if(NumMAR>0 && NumMAR<65536){
                NumMAR = NumMAR;
                cpu.setMAR(NumMAR);
                ConsoleString = ConsoleString + "\r\nchange MAR to "+TextMAR;
                this.text_console.setText(ConsoleString);
            }
            else{
                NumMAR = CPU_MAR;
            }
        }
        else{
            NumMAR = CPU_MAR;
        }
            
        //show MBR
        if(!(TextMBR.equals(""))){
            NumMBR = Integer.parseInt(TextMBR);
            if(NumMBR>0 && NumMBR<65536){
                NumMBR = NumMBR;
                cpu.setMBR(TextMBR);
                ConsoleString = ConsoleString + "\r\nchange MBR to "+TextMBR;
                this.text_console.setText(ConsoleString);
            }
            else{
                NumMBR = Integer.parseInt(CPU_MBR);
            }
        }
        else{
            NumMBR = Integer.parseInt(CPU_MBR);
        }
        
        //show IR
        if(!(TextIR.equals(""))){
            NumIR = Integer.parseInt(TextIR);
            if(NumIR>0 && NumIR<65536){
                NumIR = NumIR;
                cpu.setIR(TextIR);
                ConsoleString = ConsoleString + "\r\nchange IR to "+TextIR;
                this.text_console.setText(ConsoleString);
            }
            else{
                NumIR = Integer.parseInt(CPU_IR);
            }
        }
        else{
            NumIR = Integer.parseInt(CPU_IR);
        }     

    }
    
  //NEW BY Alina
    private void ShowNumberR(String TextR0, String TextR1, String TextR2, String TextR3, String CPU_R0, String CPU_R1, String CPU_R2, String CPU_R3, boolean showR){
        int NumR0, NumR1, NumR2, NumR3;
        //show R0
        if(!(TextR0.equals(""))){
            NumR0 = Integer.parseInt(TextR0);
            if(NumR0>0 && NumR0<65536){
                NumR0 = NumR0;
                cpu.setGeneralRegister(0, TextR0);
                ConsoleString = ConsoleString + "\r\nchange R0 to "+TextR0;
                this.text_console.setText(ConsoleString);
            }
            else{
                NumR0 = Integer.parseInt(CPU_R0);
            }
        }
        else{
            NumR0 = Integer.parseInt(CPU_R0);
        }
        
        //show R1
        if(!(TextR1.equals(""))){
            NumR1 = Integer.parseInt(TextR1);
            if(NumR1>0 && NumR1<65536){
                NumR1 = NumR1;
                cpu.setGeneralRegister(1, TextR1);
                ConsoleString = ConsoleString + "\r\nchange R1 to "+TextR1;
                this.text_console.setText(ConsoleString);
            }
            else{
                NumR1 = Integer.parseInt(CPU_R1);
            }
        }
        else{
            NumR1 = Integer.parseInt(CPU_R1);
        }
        
        //show R2
        if(!(TextR2.equals(""))){
            NumR2 = Integer.parseInt(TextR2);
            if(NumR2>0 && NumR2<65536){
                NumR2 = NumR2;
                cpu.setGeneralRegister(1, TextR1);
                ConsoleString = ConsoleString + "\r\nchange R2 to "+TextR2;
                this.text_console.setText(ConsoleString);
            }
            else{
                NumR2 = Integer.parseInt(CPU_R2);
            }
        }
        else{
            NumR2 = Integer.parseInt(CPU_R2);
        }
        
        //show R3
        if(!(TextR3.equals(""))){
            NumR3 = Integer.parseInt(TextR3);
            if(NumR3>0 && NumR3<65536){
                NumR3 = NumR3;
                cpu.setGeneralRegister(3, TextR3);
                ConsoleString = ConsoleString + "\r\nchange R3 to "+TextR3;
                this.text_console.setText(ConsoleString);
            }
            else{
                NumR3 = Integer.parseInt(CPU_R1);
            }
        }
        else{
            NumR3 = Integer.parseInt(CPU_R3);
        }
        
    }
    
  //NEW BY Alina
    private void ShowNumberX(String TextX1, String TextX2, String TextX3, int CPU_X1, int CPU_X2, int CPU_X3, boolean showX){
        int NumX1, NumX2, NumX3;
        //show X1
        if(!(TextX1.equals(""))){
            NumX1 = Integer.parseInt(TextX1);
            if(NumX1>0 && NumX1<65536){
                NumX1 = NumX1;
                cpu.setIndexRegister(0, NumX1);
                ConsoleString = ConsoleString + "\r\nchange X1 to "+TextX1;
                this.text_console.setText(ConsoleString);
            }
            else{
                NumX1 = CPU_X1;
            }
        }
        else{
            NumX1 = CPU_X1;
        }
        
        //show X2
        if(!(TextX2.equals(""))){
            NumX2 = Integer.parseInt(TextX2);
            if(NumX2>0 && NumX2<65536){
                NumX2 = NumX2;
                cpu.setIndexRegister(1, NumX2);
                ConsoleString = ConsoleString + "\r\nchange X2 to "+TextX2;
                this.text_console.setText(ConsoleString);
            }
            else{
                NumX2 = CPU_X2;
            }
        }
        else{
            NumX2 = CPU_X2;
        }

        //show X3
        if(!(TextX3.equals(""))){
            NumX3 = Integer.parseInt(TextX3);
            if(NumX3>0 && NumX3<65536){
                NumX3 = NumX3;
                cpu.setIndexRegister(2, NumX3);
                ConsoleString = ConsoleString + "\r\nchange X3 to "+TextX3;
                this.text_console.setText(ConsoleString);
            }
            else{
                NumX3 = CPU_X3;
            }
        }
        else{
            NumX3 = CPU_X3;
        }
        
    }
    
	 public static void main(String[] args) {
	        new GUI();
	  
	    }
}

