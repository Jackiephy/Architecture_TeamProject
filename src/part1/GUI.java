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
	
	private JLabel pc,cc,IR,MAR,MBR,MSR,MFR,IAR,IRR,R0,R1,R2,R3,X1,X2,X3 = null;
	private JTextField t_pc,t_cc,t_IR,t_MAR,t_MBR,t_MSR,t_MFR,t_IAR,t_IRR,t_R0,t_R1,t_R2,t_R3,t_X1,t_X2,t_X3 = null;
	private JPanel jp;
	private JPanel jpProcessState;
	private JPanel textfeild;
	
    private FileDialog openFile;
    
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
        // 菜单
    	Menu menu = new Menu("choose");
        // 菜单项
    	show=new MenuItem("project1");
        // 菜单添加菜单项
        menu.add(show);
        // 菜单栏添加菜单
        menuBar.add(menu);
        f.setMenuBar(menuBar);
        //文本域
       
     

        
        openfile = new JButton("Open File");
        openfile.setBounds(30, 20, 100, 50);
        
        execute = new JButton("Execute");
        execute.setBounds(130, 20, 100, 50);
        
        ta=new JTextArea(20,20);
        ta.setBounds(30, 100, 200, 300);
 
        
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
                 
                 //读取展示文件
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
            	 t_pc.setText(Integer.toString( cpu.getPC()));
            	 t_cc.setText(cpu.getCC());
            	 t_IR.setText(cpu.getIR());
            	 t_MAR.setText(Integer.toString( cpu.getMAR()));
            	 t_MBR.setText(cpu.getMBR());
            	 t_MSR.setText(cpu.getMSR());
            	 t_MFR.setText(Integer.toString( cpu.getMFR()));
            	 t_IAR.setText(Integer.toString( cpu.getIAR()));
            	 t_IRR.setText(cpu.getIRR());
            	 t_R0.setText(cpu.getR0());
            	 t_R1.setText(cpu.getR1());
            	 t_R2.setText(cpu.getR2());
            	 t_R3.setText(cpu.getR3());
            	 t_X1.setText(Integer.toString( cpu.getX1()));
            	 t_X2.setText(Integer.toString( cpu.getX2()));
            	 t_X3.setText(Integer.toString( cpu.getX3()));
            	 
            	
             }
         });
    	 
    }
    
    
    
//	 public static void main(String[] args) {
//	        new GUI();
//	  
//	    }
}

