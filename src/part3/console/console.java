package console;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;



import javax.swing.*;


import cpu.CPU;
import instruction.Abstractinstruction;
import memory.Cache;
import memory.MCU;
import memory.Memory;
import util.Const;
import util.MachineFaultException;
import util.Program1;
import util.Program2;
import util.StringUtil;
public class console extends JFrame{

	   private JFrame frame;
	   
	   private JPanel Global;
	   
	   private JPanel panel_console_print,panel_console_keyboard, panel_console_cache,panel_leftbot,panel_instruction, panel_memory,panel_mfranddeposit,panel_deposit,panel_consoletext,panel_clearbutton,panel_left,panel_Pc,panel_Mfr,panel_Ir,panel_lastline,Registerset0,Registerset1,Registerset2,Registerset3,Index_Reg1_set,Index_Reg2_set,Index_Reg3_set,Mar_set,Mbr_set;
	   
	   private JTextField[] Pc,Cc,Mfr,Ir,Register1,Register2,Register3,Register0,Index_Reg1,Index_Reg2,Index_Reg3,Mar,Mbr;
	   
	   private JLabel label_program1,label_program2, label_instruction, label_Pc,label_Cc,label_Mfr,label_Ir, label_R1,label_R0,label_R2,label_R3,label_IX_R1,label_IX_R2,label_IX_R3,label_Mar,label_Mbr,label_Address;
	   
	   private JLabel label_console_printer, label_console_keyboard,label_console_cache,label_Pc_val,label_Ir_val,label_R1_value,label_R0_value,label_R2_value,label_R3_value,label_CC_val, label_PC_val,label_opcode_val,
	                    label_IX_R1_val,label_IX_R2_val,label_IX_R3_val,label_Mar_val,label_Mbr_val,label_Value;
	   
	   private JTextField text_Pc,text_Ir,text_R1,text_R0,text_R2,text_R3,text_IX_R1,text_IX_R2,text_IX_R3,text_Mar,text_Mbr,text_Address,text_Val;
	   
	   private JTextArea text_console_print, text_console_keyboard;
	   private JTable text_console_cache;
	   private JScrollPane scrollPane_cache, scrollPane_global;
	   
	   private JButton button_load,button_find, button_compare,button_read20number,button_execute,button_p1,button_p2,button_enter,button_run,button_halt,button_deposit,button_singlestep,button_console,button_memory,button_IPL;
	   private JRadioButton[] instruction;
	   
	   private String getTextPC, getTextR0, getTextR1, getTextR2, getTextR3, getTextX1, getTextX2, getTextX3, getTextMAR, getTextMBR, getTextIR;
	   private String getKey, getValue;
	   
	   public String ConsoleString = "";
	   
	   
       public int PCstore;
	   Memory mainMemorystore;
	   
	   boolean Single = false, Continue = true;
	   
	   Memory mainMemory;
	   CPU cpu;
	   MCU mcu;
	   private int program1Step, program2Step;
	   
	   
	   // Save situation when halt
	   int tempPC;
	   String tempIR;

	   private JPanel panel_control;
	   private JPanel panel_console;
	   private JPanel panel_register;
	   

	   
	   private void initComponents()
	    {
		      frame = new JFrame("CS6461 Computer");
		 
		      panel_console = new JPanel();
	    	  panel_register = new JPanel();
	    	  panel_control = new JPanel();
	    	  
	    	  frame.setBounds(30,0,1000, 710);
	          frame.setLayout(null);
	          frame.setVisible(true);  
	          
	          frame.addWindowListener(new WindowAdapter() {
	              public void windowClosing(WindowEvent e) {
	                  System.exit(0);
	              }
	          }); 

	          
	    	  button_IPL = new JButton("IPL");
	    	  button_IPL.setBounds(30, 10, 80, 40);
	    	  button_IPL.setOpaque(true);
	    	  button_IPL.setBackground(new java.awt.Color(250,128,114));
	    	  button_IPL.setBorderPainted(false);
	    	  
	    	  button_IPL.addActionListener(new java.awt.event.ActionListener() {       	
	              //enable all button      	
	              @Override
	              public void actionPerformed(ActionEvent e) {
	            	  button_IPL.setBackground(new java.awt.Color(189,252,201));
	                  initialCPU();
	                  cpu.setPC(Const.PC_BASE);
	                  //mcu.loadProgram(Const.BASE_PROGRAM);
	                  program1Step = 0;
	                  enableButton();
	                  initialReg();
	                  printConsole("IPL finish!");
	                  
	              }
	          });
	    	  
	    	  label_console_printer = new JLabel("Console Printer");
	    	  label_console_printer.setBounds(30,45,100,25);
	    	  
	    	  
	    	  text_console_print=new JTextArea(20,20);
	    	  text_console_print.setBounds(30, 65, 350, 200);
	    	  text_console_print.setOpaque(true);
	    	  text_console_print.setBackground(Color.black);
	    	  text_console_print.setForeground(Color.white);
	    	  
	    	  label_console_keyboard = new JLabel("Console Keyboard");
	    	  label_console_keyboard.setBounds(30,263,120,25);
	    	  
	    	  text_console_keyboard=new JTextArea();
	          text_console_keyboard.setBounds(30, 285, 350, 150);
	          text_console_keyboard.setOpaque(true);
	          text_console_keyboard.setBackground(Color.white);
	          text_console_keyboard.setLineWrap(true);
	          
	          label_console_cache = new JLabel("Cache");
	          label_console_cache.setBounds(30,435,100,25);
	          
	          text_console_cache=new JTable(16,2);
	          text_console_cache.setEnabled(false);
	          text_console_cache.setShowGrid(false);
	          
	          scrollPane_cache = new JScrollPane();
	          scrollPane_cache.setViewportView(text_console_cache);
	          
	          text_console_cache.setModel(new DefaultTableModel(
	                  new Object[][] { { null, null }, { null, null }, { null, null }, { null, null }, { null, null },
	                          { null, null },  { null, null }, { null, null },{ null, null }, { null, null }, { null, null },{ null, null },
	                          { null, null }, { null, null },{ null, null },{ null, null }, { null, null },  },
	                  new String[] { "Tag", "Data" }));
	          
	          scrollPane_cache.setBounds(30,460,350,160);
	          
	          button_console=new JButton("CLEAR");
	          button_console.setBounds(50,630,100,35);
	          
	          button_enter=new JButton("ENTER");
	          button_enter.setBounds(250,630,100,35);
	          
	          this.button_console.addActionListener(new java.awt.event.ActionListener() {
	              @Override
	              public void actionPerformed(ActionEvent e) {
	                  System.out.println("Clear");
	                  ConsoleString = "";
	                  text_console_print.setText("");
	                  text_console_keyboard.setText("");
	              }
	          }); 
	          
	          panel_console.setLayout(null);
	          panel_console.setVisible(true);
	          panel_console.setBounds(0,0,400, 850);
	          panel_console.add(button_IPL);
	          panel_console.add(label_console_printer);
	          panel_console.add(text_console_print);
	          panel_console.add(label_console_keyboard);
	          panel_console.add(text_console_keyboard);
	          panel_console.add(label_console_cache);
	          panel_console.add(scrollPane_cache);
	          panel_console.add(button_console);
	          panel_console.add(button_enter);

	          
	    	  label_Pc = new JLabel("PC");
	    	  label_Pc.setBounds(0, 10, 50, 25);
	    	  label_Ir = new JLabel("IR");
	    	  label_Ir.setBounds(0, 35, 50, 25);
	    	  label_Mar = new JLabel("MAR");
	    	  label_Mar.setBounds(0, 60, 50, 25);
	    	  label_Mbr = new JLabel("MBR");
	    	  label_Mbr.setBounds(0, 85, 50, 25);
	          label_R0 = new JLabel("R0");
	          label_R0.setBounds(0, 110, 50, 25);
	          label_R1 = new JLabel("R1");
	          label_R1.setBounds(0, 135, 50, 25);
	          label_R2 = new JLabel("R2");
	          label_R2.setBounds(0, 160, 50, 25);
	          label_R3 = new JLabel("R3");
	          label_R3.setBounds(0, 185, 50, 25);
	          label_IX_R1 = new JLabel("X1");
	          label_IX_R1.setBounds(0, 210, 50, 25);
	          label_IX_R2 = new JLabel("X2");
	          label_IX_R2.setBounds(0, 235, 50, 25);
	          label_IX_R3 = new JLabel("X3");
	          label_IX_R3.setBounds(0, 260, 50, 25);
	          label_Mfr = new JLabel("MFR");
	          label_Mfr.setBounds(0, 285, 50, 25);
	          label_Cc = new JLabel("cc");
	    	  label_Cc.setBounds(0, 310, 50, 25);
	    	  
	    	  text_Pc = new JTextField() ;
	    	  text_Pc.setBounds(400, 10, 100, 25);
	          text_Ir = new JTextField() ;
	          text_Ir.setBounds(400, 35, 100, 25);
	          text_Mar = new JTextField() ;
	          text_Mar.setBounds(400, 60, 100, 25);
	          text_Mbr = new JTextField() ;
	          text_Mbr.setBounds(400, 85, 100, 25);
	          text_R0 = new JTextField() ;
	          text_R0.setBounds(400, 110, 100, 25);
	          text_R1 = new JTextField() ;
	          text_R1.setBounds(400, 135, 100, 25);
	          text_R2 = new JTextField() ;
	          text_R2.setBounds(400, 160, 100, 25);
	          text_R3 = new JTextField() ;
	          text_R3.setBounds(400, 185, 100, 25);
	          text_IX_R1 = new JTextField() ;
	          text_IX_R1.setBounds(400, 210, 100, 25);
	          text_IX_R2 = new JTextField() ;
	          text_IX_R2.setBounds(400, 235, 100, 25);
	          text_IX_R3 = new JTextField() ;
	          text_IX_R3.setBounds(400, 260, 100, 25);
	          
	          button_deposit = new JButton("Deposit Reg");
	          button_deposit.setBounds(390,295,120,40);
	          

	          
	          Pc = new JTextField[12] ;
              for(int j = 0;j<12;j++){
	         	   
	         		   Pc[j]= new JTextField(" ");
	         		   Pc[j].setBounds(50+20*j, 10, 20, 25);
	         		   
	         		   Pc[j].setOpaque(true);
	         		   Pc[j].setBackground(Color.white);
	         		   panel_register.add(Pc[j]);
	         	   
	            }
              
              Ir = new JTextField[16] ;
              for(int j = 0;j<16;j++){
	         	   
            	  Ir[j]= new JTextField(" ");
            	  Ir[j].setBounds(50+20*j, 35, 20, 25);
	         		   
            	  Ir[j].setOpaque(true);
            	  Ir[j].setBackground(Color.white);
	         	  panel_register.add(Ir[j]);
	            
	            }
              
              Mar = new JTextField[16] ;
              for(int j = 0;j<16;j++){
	         	   
            	  Mar[j]= new JTextField(" ");
            	  Mar[j].setBounds(50+20*j, 60, 20, 25);
	         		   
            	  Mar[j].setOpaque(true);
            	  Mar[j].setBackground(Color.white);
	         	  panel_register.add(Mar[j]);	   
	         	   
	            }
              Mbr = new JTextField[16] ;
              for(int j = 0;j<16;j++){
	         	   
            	  Mbr[j]= new JTextField(" ");
            	  Mbr[j].setBounds(50+20*j, 85, 20, 25);
	         		   
            	  Mbr[j].setOpaque(true);
            	  Mbr[j].setBackground(Color.white);
	         	  panel_register.add(Mbr[j]);
	         	  
	            }
              Register0 = new JTextField[16] ;
              for(int j = 0;j<16;j++){
	         	   
            	  Register0[j]= new JTextField(" ");
            	  Register0[j].setBounds(50+20*j, 110, 20, 25);
	         		   
            	  Register0[j].setOpaque(true);
            	  Register0[j].setBackground(Color.white);
	         	  panel_register.add(Register0[j]);
	         	 
	            }
              Register1 = new JTextField[16] ;
              for(int j = 0;j<16;j++){
	         	   
            	  Register1[j]= new JTextField(" ");
            	  Register1[j].setBounds(50+20*j, 135, 20, 25);
	         		   
            	  Register1[j].setOpaque(true);
            	  Register1[j].setBackground(Color.white);
	         	  panel_register.add(Register1[j]);
	         		 
	            }
              Register2 = new JTextField[16] ;
              for(int j = 0;j<16;j++){
	         	   
            	  Register2[j]= new JTextField(" ");
            	  Register2[j].setBounds(50+20*j, 160, 20, 25);
	         		   
            	  Register2[j].setOpaque(true);
            	  Register2[j].setBackground(Color.white);
	         	  panel_register.add(Register2[j]);
	         	 
	            }
              Register3 = new JTextField[16] ;
              for(int j = 0;j<16;j++){
	         	   
            	  Register3[j]= new JTextField(" ");
            	  Register3[j].setBounds(50+20*j, 185, 20, 25);
	         		   
            	  Register3[j].setOpaque(true);
            	  Register3[j].setBackground(Color.white);
	         	  panel_register.add(Register3[j]);
	         	  
	            }
              Index_Reg1 = new JTextField[16] ;
              for(int j = 0;j<16;j++){
	         	   
            	  Index_Reg1[j]= new JTextField(" ");
            	  Index_Reg1[j].setBounds(50+20*j, 210, 20, 25);
	         		   
            	  Index_Reg1[j].setOpaque(true);
            	  Index_Reg1[j].setBackground(Color.white);
	         	  panel_register.add( Index_Reg1[j]);
	         	  
	            }
              Index_Reg2 = new JTextField[16] ;
              for(int j = 0;j<16;j++){
	         	   
            	  Index_Reg2[j]= new JTextField(" ");
            	  Index_Reg2[j].setBounds(50+20*j, 235, 20, 25);
	         		   
            	  Index_Reg2[j].setOpaque(true);
            	  Index_Reg2[j].setBackground(Color.white);
	         	  panel_register.add( Index_Reg2[j]);
	         	  
	            }
              Index_Reg3 = new JTextField[16] ;
              for(int j = 0;j<16;j++){
	         	   
            	  Index_Reg3[j]= new JTextField(" ");
            	  Index_Reg3[j].setBounds(50+20*j, 260, 20, 25);
	         		   
            	  Index_Reg3[j].setOpaque(true);
            	  Index_Reg3[j].setBackground(Color.white);
	         	  panel_register.add( Index_Reg3[j]);
	         	  
	            }
              Mfr = new JTextField[4] ;
              for(int j = 0;j<4;j++){
	         	   
            	  Mfr[j]= new JTextField(" ");
            	  Mfr[j].setBounds(50+20*j, 285, 20, 25);
	         		   
            	  Mfr[j].setOpaque(true);
            	  Mfr[j].setBackground(Color.white);
	         	  panel_register.add(Mfr[j]);
	         	  
	            }
              Cc = new JTextField[4] ;
              for(int j = 0;j<4;j++){
	         	   
            	  Cc[j]= new JTextField(" ");
            	  Cc[j].setBounds(50+20*j, 310, 20, 25);
	         		   
            	  Cc[j].setOpaque(true);
            	  Cc[j].setBackground(Color.white);
	         	  panel_register.add(Cc[j]);
	         	  
	            }
	   	   
	          
	          this.button_deposit.addActionListener(new java.awt.event.ActionListener() {
	              @Override
	              public void actionPerformed(ActionEvent e) {
	                  getTextPC = text_Pc.getText();
	                  getTextR0 = text_R0.getText();
	                  getTextR1 = text_R1.getText();
	                  getTextR2 = text_R2.getText();
	                  getTextR3 = text_R3.getText();
	                  getTextX1 = text_IX_R1.getText();
	                  getTextX2 = text_IX_R2.getText();    
	                  getTextX3 = text_IX_R3.getText();
	                  getTextMAR = text_Mar.getText();
	                  getTextMBR = text_Mbr.getText();
	                  getTextIR = text_Ir.getText();
	                  
	                  ShowNumberR(getTextR0, getTextR1, getTextR2, getTextR3, cpu.getRnByNum(0), cpu.getRnByNum(1), cpu.getRnByNum(2), cpu.getRnByNum(3), false);
	                  ShowNumberX(getTextX1, getTextX2, getTextX3, cpu.getXnByNum(0), cpu.getXnByNum(1), cpu.getXnByNum(2), false);
	                  ShowNumberO(getTextPC, getTextMAR, getTextMBR, getTextIR, cpu.getPC(), cpu.getMAR(), cpu.getMBR(), cpu.getIR(), false);
	                  Continue = false;
	              }
	          });
	          
	          panel_register.add(label_Pc);
	          panel_register.add(label_Ir);
	          panel_register.add(label_Mar);
	          panel_register.add(label_Mbr);
	          panel_register.add(label_R0);
	          panel_register.add(label_R1);
	          panel_register.add(label_R2);
	          panel_register.add(label_R3);
	          panel_register.add(label_IX_R1);
	          panel_register.add(label_IX_R2);
	          panel_register.add(label_IX_R3);
	          panel_register.add(label_Mfr);
	          panel_register.add(label_Cc);
	          
	          
	          panel_register.add(text_Pc);
	          panel_register.add(text_Ir);
	          panel_register.add(text_Mar);
	          panel_register.add(text_Mbr);
	          panel_register.add(text_R0);
	          panel_register.add(text_R1);
	          panel_register.add(text_R2);
	          panel_register.add(text_R3);
	          panel_register.add(text_IX_R1);
	          panel_register.add(text_IX_R2);
	          panel_register.add(text_IX_R3);
	        
	          panel_register.add(button_deposit);
	          
	          panel_register.setVisible(true); 
	          panel_register.setLayout(null); 
	          panel_register.setBounds(450,0,600, 340);
	       
	         
	          instruction=new JRadioButton[16];
	          label_instruction=new JLabel("Instruction:");
	          label_instruction.setBounds(10, 40, 90, 25);
	          button_execute=new JButton("Execute");
	          button_execute.setBounds(445, 35, 100, 35);
	         
	          label_program1=new JLabel("Program 1:");
	          label_program1.setBounds(10, 110, 70, 25);
	          button_read20number=new JButton("Read");
	          button_read20number.setBounds(150, 100, 90, 40);
	          button_compare=new JButton("compare");
	          button_compare.setBounds(330, 100, 90, 40);
	          label_Address=new JLabel("Address:");
	          label_Address.setBounds(80, 160, 70, 30);    
	          text_Address=new JTextField();
	          text_Address.setBounds(150, 160, 90, 30);    
	          label_Value=new JLabel("Value:");
	          label_Value.setBounds(270, 160, 70, 30);    
	          text_Val=new JTextField();
	          text_Val.setBounds(330, 160, 90, 30);
	          button_memory=new JButton("Deposit/Search");
	          button_memory.setBounds(330, 210, 190, 30);
	          
	          label_program2=new JLabel("Program 2:");
	          label_program2.setBounds(10, 265, 70, 25);
	          button_load=new JButton("Load");
	          button_load.setBounds(100, 260, 90, 40);
	          button_find=new JButton("Find");
	          button_find.setBounds(190, 260, 90, 40);
	          button_singlestep=new JButton("Single");
	          button_singlestep.setBounds(280, 260, 90, 40);
	          button_run=new JButton("Run");        
	          button_run.setBounds(370, 260, 90, 40);
	          button_halt=new JButton("Halt");
	          button_halt.setBounds(460, 260, 90, 40);
	         
	          
	          
	          
	         panel_control.add(label_instruction);
	          for(int i=0; i<16;i++)
	          {
	              instruction[i]=new JRadioButton("");
	              instruction[i].setBounds(85+22*i,42,24,20);
	              panel_control.add(instruction[i]);
	          }
	          panel_control.add(button_execute);
	          panel_control.add(label_program1);
	          panel_control.add(button_read20number);
	          panel_control.add( button_compare);
	          panel_control.add(label_Address);
	          panel_control.add(text_Address);
	          panel_control.add(label_Value);
	          panel_control.add(text_Val);
	          panel_control.add(button_memory);
	          panel_control.add(label_program2);
	          panel_control.add(button_load);
	          panel_control.add(button_find);
	          panel_control.add(button_singlestep);
	          panel_control.add(button_run);
	          panel_control.add(button_halt);
	          
	          
	          
	          this.button_read20number.addActionListener(new java.awt.event.ActionListener(){
	        	  @Override
	              public void actionPerformed(ActionEvent e){
	                  if(program1Step==0){
	                      // read 20 number from console
	                      System.out.println("Start reading numbers...");
	                      mcu.setKeyboardBuffer(text_console_keyboard.getText());
	                      if (text_console_keyboard.getText()==null || text_console_keyboard.getText().length()==0){
	                          JOptionPane.showMessageDialog(null, "type 20 numbers in the console keyboard");
	                      }else{
	                          printConsole("Below are the 20 numbers: ");
	                          mcu.loadProgram(Program1.Pre);
	                          mcu.loadProgram(Program1.PG1_20);
	                          cpu.setPC(Const.PG1_BASE_1);
	                          
	                          do{
	                              cpu.setMAR(cpu.getPC());
	                              cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
	                              cpu.setIR(cpu.getIntMBR());
	                              runInstruction(cpu.getBinaryStringOfIR(), cpu, mcu);
	                          }while(cpu.getPC() <= Const.PG1_END_1 && cpu.getPC() >= Const.PG1_BASE_1);
	                          refreshPanel();
	                          program1Step = 1;
	                          printConsole("Please enter 1 number (end with ',') and press compare button");
	                      }
	                  }    
	              }
	          });
	          
	          this.button_memory.addActionListener(new java.awt.event.ActionListener() {
	              @Override
	              public void actionPerformed(ActionEvent e) {
	                  System.out.println("Mem");
	                  getKey = text_Address.getText();
	                  getValue = text_Val.getText();
	                  System.out.println(getValue);
	                  if(getValue.equals("")){
	                      SearchINAddress(getKey);
	                  }
	                  else{
	                      DepositINAddress(getKey, getValue);
	                  }
	              }
	          });
	          
	          this.button_singlestep.addActionListener(new java.awt.event.ActionListener() {
	              @Override
	              public void actionPerformed(ActionEvent e) {
	                  System.out.println("Sin");
	                  Single();
	              }
	          });
	          
	          this.button_run.addActionListener(new java.awt.event.ActionListener() {
	              @Override
	              public void actionPerformed(ActionEvent e) {
	                  System.out.println("Run");
	                  Run();
	              }
	          });
	          
	          this.button_halt.addActionListener(new java.awt.event.ActionListener() {
	              @Override
	              public void actionPerformed(ActionEvent e) {
	                  System.out.println("Halt");
	                  Halt();
	              }
	          });
	          this.button_compare.addActionListener(new java.awt.event.ActionListener(){
	              @Override
	              public void actionPerformed(ActionEvent e){
	                  if(program1Step==1){
	                      // read 1 number 
	                      System.out.println("read 1 number...");
	                      mcu.loadProgram(Program1.PG1_20);
	                      cpu.setPC(Const.PG1_BASE_1);
	                      mcu.setKeyboardBuffer(text_console_keyboard.getText());
	                      do{
	                          cpu.setMAR(cpu.getPC());
	                          cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
	                          cpu.setIR(cpu.getIntMBR());
	                          runInstruction(cpu.getBinaryStringOfIR(), cpu, mcu);
	                      }while (cpu.getPC() <= Const.PG1_END_1 && cpu.getPC() >= Const.PG1_BASE_1);
	                      
	                      System.out.println("start comparing...");
	                      printConsole("compare result is (the closeset number is");
	                      mcu.loadProgram(Program1.Compare);
	                      cpu.setPC(Const.PG1_BASE_2);
	                      
	                      do{
	                          cpu.setMAR(cpu.getPC());
	                          cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
	                          cpu.setIR(cpu.getIntMBR());
	                          runInstruction(cpu.getBinaryStringOfIR(), cpu, mcu);
	                          System.out.println(mcu.fetchFromMemory(27)+" ");
	                      }while(cpu.getPC() <= Const.PG1_END_2 && cpu.getPC() >= Const.PG1_BASE_2);
	                      
	                      System.out.println("print the result in address 30");
	                      mcu.loadProgram(Program1.PrintResult1);
	                      cpu.setPC(Const.PG1_BASE_3);
	                      do{
	                          cpu.setMAR(cpu.getPC());
	                          cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
	                          cpu.setIR(cpu.getIntMBR());
	                          runInstruction(cpu.getBinaryStringOfIR(), cpu, mcu);
	                          System.out.println(mcu.fetchFromMemory(30)+" ");
	                      } while(cpu.getPC() <= Const.PG1_END_3 && cpu.getPC() >= Const.PG1_BASE_3);
	                      
	                      refreshPanel();
	                      program1Step = 0;
	                          
	                      
	                  }
	              }
	          });
	          
	        //Load sentence
	          this.button_load.addActionListener(new java.awt.event.ActionListener(){
	              @Override
	              public void actionPerformed(ActionEvent e){
	                  if(program2Step==0){
	                      // read 6 sentences from file
	                      System.out.println("Start reading sentences...");
	                      
	                      //TODO: add readfiles here
	                      //String sentences = readfiles();
	                      String sentences = "Hello we are team 2.\n "
	                              +"Our team members are Luying Zhang, Mingqian Liu and Kai Wang.\n "
	                              +"We are good at different fields.\n "
	                              +"Luying is familiar to Java.\n "
	                              +"Mingqian can design beautiful UI interface.\n "
	                              +"Kai loves machine language.\n ";
	                      mcu.setCardBuffer(sentences);
	                      mcu.loadProgram(Program2.PRE);
	                      mcu.loadProgram(Program2.PROGRAM2_1);
	                      cpu.setPC(Const.PG2_BASE1);
	                      
	                      do{
	                          cpu.setMAR(cpu.getPC());
	                          cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
	                          cpu.setIR(cpu.getIntMBR());
	                          runInstruction(cpu.getBinaryStringOfIR(), cpu, mcu);
	                          System.out.println(mcu.fetchFromMemory(20)+" ");
	                      } while(cpu.getPC() <= Const.PG2_END1 && cpu.getPC() >= Const.PG2_BASE1);
	                      
	                      printConsole("Please enter a word need to be searched in console keyboard...");
	                      refreshPanel();
	                      program2Step = 1;
	                      
	                  }    
	              }
	          });
	          
	          //Find word
	          this.button_find.addActionListener(new java.awt.event.ActionListener(){
	              @Override
	              public void actionPerformed(ActionEvent e){
	                  if(program2Step==1){
	                      System.out.println("Start reading words...");
	                      if(text_console_keyboard.getText()==null|| text_console_keyboard.getText().length()==0){
	                          JOptionPane.showMessageDialog(null, "type a word in the console keyboard!");
	                      }else{
	                          //read word
	                          printConsole("search reault is ");
	                          mcu.loadProgram(Program2.PROGRAM2_2);
	                          cpu.setPC(Const.PG2_BASE2);
	                          mcu.setKeyboardBuffer(text_console_keyboard.getText());
	                          do{
	                              cpu.setMAR(cpu.getPC());
	                              cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
	                              cpu.setIR(cpu.getIntMBR());
	                              runInstruction(cpu.getBinaryStringOfIR(), cpu, mcu);
	                              System.out.println(mcu.fetchFromMemory(80)+" ");
	                              System.out.println(cpu.getRnByNum(1));
	                          }while(cpu.getPC()<=Const.PG2_END2 && cpu.getPC() > Const.PG2_BASE2);
	                          
	                          //search word
	                          printConsole("the word number is: ");
	                          mcu.loadProgram(Program2.PROGRAM2_3);
	                          cpu.setPC(Const.PG2_BASE3);
	                          do{
	                              cpu.setMAR(cpu.getPC());
	                              cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
	                              cpu.setIR(cpu.getIntMBR());
	                              runInstruction(cpu.getBinaryStringOfIR(), cpu, mcu);
	                              System.out.println(cpu.getRnByNum(1));
	                              System.out.println(cpu.getRnByNum(2));
	                          }while(cpu.getPC()<=Const.PG2_END3 && cpu.getPC() >= Const.PG2_BASE3);
	                          
	                          //print 1
	                          System.out.println("print result in address 28");                        
	                          mcu.loadProgram(Program2.PrintResult1);
	                          cpu.setPC(Const.PG2_BASE4);
	                          do{
	                              cpu.setMAR(cpu.getPC());
	                              cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
	                              cpu.setIR(cpu.getIntMBR());
	                              runInstruction(cpu.getBinaryStringOfIR(), cpu, mcu);
	                          } while(cpu.getPC() <= Const.PG2_END4 && cpu.getPC() >= Const.PG2_BASE4);
	                          
	                          //print 2
	                          System.out.println("print result in address 29");
	                          printConsole("the sentence number is: ");
	                          mcu.loadProgram(Program2.PrintResult2);
	                          cpu.setPC(Const.PG2_BASE5);
	                          do{
	                              cpu.setMAR(cpu.getPC());
	                              cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
	                              cpu.setIR(cpu.getIntMBR());
	                              runInstruction(cpu.getBinaryStringOfIR(), cpu, mcu);
	                          }while(cpu.getPC() <= Const.PG2_END5 && cpu.getPC() >= Const.PG2_BASE5);
	                          refreshPanel();                                
	                        
	                          program2Step = 0;
	                      }
	                      
	                  }    
	              }
	          });
	          
	          this.button_singlestep.addActionListener(new java.awt.event.ActionListener() {
	              @Override
	              public void actionPerformed(ActionEvent e) {
	                  System.out.println("Sin");
	                  Single();
	              }
	          });
	          
	          this.button_run.addActionListener(new java.awt.event.ActionListener() {
	              @Override
	              public void actionPerformed(ActionEvent e) {
	                  System.out.println("Run");
	                  Run();
	              }
	          });
	          
	          this.button_halt.addActionListener(new java.awt.event.ActionListener() {
	              @Override
	              public void actionPerformed(ActionEvent e) {
	                  System.out.println("Halt");
	                  Halt();
	              }
	          });
	          
	          panel_control.setVisible(true); 
	          panel_control.setLayout(null); 
	          panel_control.setBounds(420,350,560, 320);
	          panel_control.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Control"));
	          panel_control.setBackground(new java.awt.Color(176,224,230));
	         
	          frame.add(panel_console);
	          frame.add(panel_register);
	          frame.add(panel_control);
	         
	          button_execute.setEnabled(false);
	          button_compare.setEnabled(false);
	          button_read20number.setEnabled(false);
	          button_load.setEnabled(false);
	          button_find.setEnabled(false);
	          button_enter.setEnabled(false);
	          button_run.setEnabled(false);
	          button_halt.setEnabled(false);
	          button_deposit.setEnabled(false);
	          button_singlestep.setEnabled(false);
	          button_console.setEnabled(false);
	          button_memory.setEnabled(false);
	          
	         
	          
	          
	    }
	   
	   
	   public console()
	    {
	        initComponents();
	    }
	   
	   
	   
	   
	   private void enableButton(){
	        button_execute.setEnabled(true);
	        button_compare.setEnabled(true);
	        button_read20number.setEnabled(true);
	        button_load.setEnabled(true);
	        button_find.setEnabled(true);
	        button_enter.setEnabled(true);
	        button_run.setEnabled(true);
	        button_halt.setEnabled(true);
	        button_deposit.setEnabled(true);
	        button_singlestep.setEnabled(true);
	        button_console.setEnabled(true);
	        button_memory.setEnabled(true);
	    }
	   public void Halt(){
	        this.tempPC = cpu.getPC();
	        this.tempIR = cpu.getBinaryStringOfIR();
	        printConsole("Halt - PC: " + this.tempPC +", instruction: "+ tempIR);
	        
	        cpu.setMAR(cpu.getPC());
	        cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
	        cpu.setIR(cpu.getIntMBR());
	        String ins = cpu.getBinaryStringOfIR();
	        printConsole("PC: " + cpu.getPC() + ", instruction: " + ins);
	        runInstruction(ins, cpu, mcu);
	    }
	   public void Run(){
	        cpu.setMAR(this.tempPC);
	        cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
	        cpu.setIR(cpu.getIntMBR());
	        String ins = cpu.getBinaryStringOfIR();
	        printConsole("PC: " + cpu.getPC() + ", instruction: " + ins);
	        runInstruction(ins, cpu, mcu);
	        Run();
	    }
	    
	   private void refreshCacheTable() {
	        int row = 0;
	        for (Cache.CacheLine line : mcu.getCache().getCacheLines()) {
	            this.text_console_cache.setValueAt(line.getTag(), row, 0);
	            this.text_console_cache.setValueAt(line.getData(), row, 1);
	            row++;
	        }

	    }
	   private void refreshConsoleBuffer() {
	        if (mcu.getPrinterBuffer() != null) {
	            text_console_print.append(mcu.getPrinterBuffer());
	            mcu.setPrinterBuffer("");
	        }
	        if (mcu.getKeyboardBuffer() != null) {
	            text_console_keyboard.setText(mcu.getKeyboardBuffer());
	        }
	    }
	   public void initialCPU(){
	        this.cpu = new CPU();
	        this.mcu = new MCU();
	    }
	   public void refreshPanel(){
	        for (Component com : Global.getComponents()) {
	            if (com instanceof JPanel) {
	                JPanel panel = (JPanel) com;
	                int regVal = 0;
	                int bitLong = 0;
	                String bitString = "";
	                int i = 0;
	                for (Component comm : panel.getComponents()) {
	                    if (comm instanceof JLabel) {
	                        JLabel lbl = (JLabel) comm;
	                        regVal = cpu.getRegByName(lbl.getText());
	                        bitLong = cpu.getBitLengthByName(lbl.getText());
	                        bitString = StringUtil.decimalToBinary(regVal, bitLong);
	                        i = bitLong;
	                    }
	                    /*
	                    if (comm instanceof JRadioButton) {
	                        JRadioButton rdb = (JRadioButton) comm;
	                        if (bitString.charAt(bitLong - i) == '1') {
	                            rdb.setSelected(true);
	                        } else {
	                            rdb.setSelected(false);
	                        }
	                        i--;
	                    }
	                    */
	                    if (comm instanceof JTextField) {
	                        JTextField txt = (JTextField) comm;
	                        txt.setText(String.valueOf(regVal));
	                    }
	                }
	            }
	        }        
	    } 
	    private void initialReg(){
	        //put the instructions and required data into main memory
	        cpu.setXnByNum(2, 1000);//initialize index register
	        cpu.setXnByNum(1, 700);
	        cpu.setXnByNum(0, 500);
	        cpu.setRnByNum(0, 0);
	        cpu.setRnByNum(1, 0);
	        cpu.setRnByNum(2, 0);
	        cpu.setRnByNum(3, 0);
	        cpu.setMBR("0");
	        cpu.setIR(0);

	        ConsoleString = ConsoleString + "\r\nstart working, load instruction succeed!";
	        this.printConsole(ConsoleString);
	    }
	   
	    private void runInstruction(String instruction, CPU cpu, MCU mcu) {

	        // execute button event
	        String opCode = instruction.substring(0, 6);
	        try {
	            if (Const.OPCODE.containsKey(opCode)) {

	                Abstractinstruction instr = (Abstractinstruction) Class
	                        .forName("instruction." + Const.OPCODE.get(opCode)).newInstance();
	                instr.execute(instruction, cpu, mcu);
	                System.out.println("PC: " + cpu.getPC() + ", instruction: " + instruction);
	                // printConsole("instruction: " + instruction);
	                refreshCacheTable();
	                refreshConsoleBuffer();

	                String message = instr.getExecuteMessage();
	                System.out.println(message);
	                //
	                // TODO do something with this message
	                //

	            } else {
	                // we don't have that kind of instruction
	                throw new MachineFaultException(Const.FaultCode.ILL_OPRC.getValue(),
	                        Const.FaultCode.ILL_OPRC.getMessage());
	            }
	        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (MachineFaultException t) {
	            // handle the machine fault

	            t.printStackTrace();
	            //handleMachineFault(t.getFaultCode(), t.getMessage());
	        }
	    }
	    
	    
	    
	    public void printConsole(String information){
	        this.text_console_print.append(information + "\n");
	        
	    }
	    
	    public void Single(){
	        cpu.setMAR(cpu.getPC());
	        cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
	        cpu.setIR(cpu.getIntMBR());
	        String ins = cpu.getBinaryStringOfIR();
	        printConsole("PC: " + cpu.getPC() + ", instruction: " + ins);
	        runInstruction(ins, cpu, mcu);
	    }
	    
	    private void ShowNumberO(String TextPC, String TextMAR, String TextMBR, String TextIR, int CPU_PC, int CPU_MAR, String CPU_MBR, int CPU_IR, boolean showO){
	        int NumPC, NumMAR, NumMBR, NumIR;
	        //show PC
	        if(!(TextPC.equals(""))){
	            NumPC = Integer.parseInt(TextPC);
	            if(NumPC>0 && NumPC<4096){
	                NumPC = NumPC;
	                cpu.setPC(NumPC);                
	                ConsoleString = ConsoleString + "\r\nchange PC to "+TextPC;
	                printConsole(ConsoleString);
	            }
	            else{
	                NumPC = CPU_PC;
	            }
	        }
	        else{
	            NumPC = CPU_PC;
	        }
	        System.out.println("B");
	        for(int i=11; i>=0; i--){
	            if(NumPC%2==1){
	                if(showO){this.Pc[i].setBackground(Color.blue);}
	                else{this.Pc[i].setBackground(Color.red);}
	            }
	            else{
	                this.Pc[i].setBackground(Color.white);
	            }
	            NumPC/=2;
	        }
	        
	        //show MAR
	        if(!(TextMAR.equals(""))){
	            NumMAR = Integer.parseInt(TextMAR);
	            if(NumMAR>0 && NumMAR<65536){
	                NumMAR = NumMAR;
	                cpu.setMAR(NumMAR);
	                ConsoleString = ConsoleString + "\r\nchange MAR to "+TextMAR;
	                printConsole(ConsoleString);
	            }
	            else{
	                NumMAR = CPU_MAR;
	            }
	        }
	        else{
	            NumMAR = CPU_MAR;
	        }
	        for(int i=15; i>=0; i--){
	            if(NumMAR%2==1){
	                if(showO){this.Mar[i].setBackground(Color.blue);}
	                else{this.Mar[i].setBackground(Color.red);}
	            }
	            else{
	                this.Mar[i].setBackground(Color.white);
	            }
	            NumMAR/=2;
	        }
	            
	        //show MBR
	        if(!(TextMBR.equals(""))){
	            NumMBR = Integer.parseInt(TextMBR);
	            if(NumMBR>0 && NumMBR<65536){
	                NumMBR = NumMBR;
	                cpu.setMBR(TextMBR);
	                ConsoleString = ConsoleString + "\r\nchange MBR to "+TextMBR;
	                printConsole(ConsoleString);
	            }
	            else{
	                NumMBR = Integer.parseInt(CPU_MBR);
	            }
	        }
	        else{
	            NumMBR = Integer.parseInt(CPU_MBR);
	        }
	        for(int i=15; i>=0; i--){
	            if(NumMBR%2==1){
	                if(showO){this.Mbr[i].setBackground(Color.blue);}
	                else{this.Mbr[i].setBackground(Color.red);}
	            }
	            else{
	                this.Mbr[i].setBackground(Color.white);
	            }
	            NumMBR/=2;
	        }
	        
	        //show IR
	        if(!(TextIR.equals(""))){
	            NumIR = Integer.parseInt(TextIR);
	            if(NumIR>0 && NumIR<65536){
	                NumIR = NumIR;
	                cpu.setIR(Integer.parseInt(TextIR));
	                ConsoleString = ConsoleString + "\r\nchange IR to "+TextIR;
	                printConsole(ConsoleString);
	            }
	            else{
	                NumIR = CPU_IR;
	            }
	        }
	        else{
	            NumIR = CPU_IR;
	        }
	        for(int i=15; i>=0; i--){
	            if(NumIR%2==1){
	                if(showO){this.Ir[i].setBackground(Color.blue);}
	                else{this.Ir[i].setBackground(Color.red);}
	            }
	            else{
	                this.Ir[i].setBackground(Color.white);
	            }
	            NumIR/=2;
	        }        

	    }
	    
	    private void ShowNumberR(String TextR0, String TextR1, String TextR2, String TextR3, int CPU_R0, int CPU_R1, int CPU_R2, int CPU_R3, boolean showR){
	        int NumR0, NumR1, NumR2, NumR3;
	        //show R0
	        if(!(TextR0.equals(""))){
	            NumR0 = Integer.parseInt(TextR0);
	            if(NumR0>0 && NumR0<65536){
	                NumR0 = NumR0;
	                cpu.setRnByNum(0, Integer.parseInt(TextR0));
	                ConsoleString = ConsoleString + "\r\nchange R0 to "+TextR0;
	                printConsole(ConsoleString);
	            }
	            else{
	                NumR0 = CPU_R0;
	            }
	        }
	        else{
	            NumR0 = CPU_R0;
	        }
	        for(int i=15; i>=0; i--){
	            if(NumR0%2==1){
	                if(showR){this.Register0[i].setBackground(Color.blue);}
	                else{this.Register0[i].setBackground(Color.red);}
	            }
	            else{
	                this.Register0[i].setBackground(Color.white);
	            }
	            NumR0/=2;
	        }
	        
	        //show R1
	        if(!(TextR1.equals(""))){
	            NumR1 = Integer.parseInt(TextR1);
	            if(NumR1>0 && NumR1<65536){
	                NumR1 = NumR1;
	                cpu.setRnByNum(1, Integer.parseInt(TextR1));
	                ConsoleString = ConsoleString + "\r\nchange R1 to "+TextR1;
	                printConsole(ConsoleString);
	            }
	            else{
	                NumR1 = CPU_R1;
	            }
	        }
	        else{
	            NumR1 = CPU_R1;
	        }
	        for(int i=15; i>=0; i--){
	            if(NumR1%2==1){
	                if(showR){this.Register1[i].setBackground(Color.blue);}
	                else{this.Register1[i].setBackground(Color.red);}
	            }
	            else{
	                this.Register1[i].setBackground(Color.white);
	            }
	            NumR1/=2;
	        }
	        
	        //show R2
	        if(!(TextR2.equals(""))){
	            NumR2 = Integer.parseInt(TextR2);
	            if(NumR2>0 && NumR2<65536){
	                NumR2 = NumR2;
	                cpu.setRnByNum(1, Integer.parseInt(TextR2));
	                ConsoleString = ConsoleString + "\r\nchange R2 to "+TextR2;
	                printConsole(ConsoleString);
	            }
	            else{
	                NumR2 = CPU_R2;
	            }
	        }
	        else{
	            NumR2 = CPU_R2;
	        }
	        for(int i=15; i>=0; i--){
	            if(NumR2%2==1){
	                if(showR){this.Register2[i].setBackground(Color.blue);}
	                else{this.Register2[i].setBackground(Color.red);}
	            }
	            else{
	                this.Register2[i].setBackground(Color.white);
	            }
	            NumR2/=2;
	        }
	        
	        //show R3
	        if(!(TextR3.equals(""))){
	            NumR3 = Integer.parseInt(TextR3);
	            if(NumR3>0 && NumR3<65536){
	                NumR3 = NumR3;
	                cpu.setRnByNum(3, Integer.parseInt(TextR3));
	                ConsoleString = ConsoleString + "\r\nchange R3 to "+TextR3;
	                printConsole(ConsoleString);
	            }
	            else{
	                NumR3 = CPU_R1;
	            }
	        }
	        else{
	            NumR3 = CPU_R3;
	        }
	        for(int i=15; i>=0; i--){
	            if(NumR3%2==1){
	                if(showR){this.Register3[i].setBackground(Color.blue);}
	                else{this.Register3[i].setBackground(Color.red);}
	            }
	            else{
	                this.Register3[i].setBackground(Color.white);
	            }
	            NumR3/=2;
	        }
	        
	    }
	    
	    private void ShowNumberX(String TextX1, String TextX2, String TextX3, int CPU_X1, int CPU_X2, int CPU_X3, boolean showX){
	        int NumX1, NumX2, NumX3;
	        //show X1
	        if(!(TextX1.equals(""))){
	            NumX1 = Integer.parseInt(TextX1);
	            if(NumX1>0 && NumX1<65536){
	                NumX1 = NumX1;
	                cpu.setXnByNum(0, NumX1);
	                ConsoleString = ConsoleString + "\r\nchange X1 to "+TextX1;
	                printConsole(ConsoleString);
	            }
	            else{
	                NumX1 = CPU_X1;
	            }
	        }
	        else{
	            NumX1 = CPU_X1;
	        }
	        for(int i=15; i>=0; i--){
	            if(NumX1%2==1){
	                if(showX){this.Index_Reg1[i].setBackground(Color.blue);}
	                else{this.Index_Reg1[i].setBackground(Color.red);}
	            }
	            else{
	                this.Index_Reg1[i].setBackground(Color.white);
	            }
	            NumX1/=2;
	        }
	        
	        //show X2
	        if(!(TextX2.equals(""))){
	            NumX2 = Integer.parseInt(TextX2);
	            if(NumX2>0 && NumX2<65536){
	                NumX2 = NumX2;
	                cpu.setXnByNum(1, NumX2);
	                ConsoleString = ConsoleString + "\r\nchange X2 to "+TextX2;
	                printConsole(ConsoleString);
	            }
	            else{
	                NumX2 = CPU_X2;
	            }
	        }
	        else{
	            NumX2 = CPU_X2;
	        }
	        for(int i=15; i>=0; i--){
	            if(NumX2%2==1){
	                if(showX){this.Index_Reg2[i].setBackground(Color.blue);}
	                else{this.Index_Reg2[i].setBackground(Color.red);}
	            }
	            else{
	                this.Index_Reg2[i].setBackground(Color.white);
	            }
	            NumX2/=2;
	        }

	        //show X3
	        if(!(TextX3.equals(""))){
	            NumX3 = Integer.parseInt(TextX3);
	            if(NumX3>0 && NumX3<65536){
	                NumX3 = NumX3;
	                cpu.setXnByNum(2, NumX3);
	                ConsoleString = ConsoleString + "\r\nchange X3 to "+TextX3;
	                printConsole(ConsoleString);
	            }
	            else{
	                NumX3 = CPU_X3;
	            }
	        }
	        else{
	            NumX3 = CPU_X3;
	        }
	        for(int i=15; i>=0; i--){
	            if(NumX3%2==1){
	                if(showX){this.Index_Reg3[i].setBackground(Color.blue);}
	                else{this.Index_Reg3[i].setBackground(Color.red);}
	            }
	            else{
	                this.Index_Reg3[i].setBackground(Color.white);
	            }
	            NumX3/=2;
	        }
	        
	    }
	    
	    private void ShowNumberZ(int CPU_MFR, int CPU_CC){
	        int NumMFR, NumCC;
	        //show MFR
	        NumMFR = CPU_MFR;
	        for(int i=3; i>=0; i--){
	            if(NumMFR%2==1){
	                this.Mfr[i].setBackground(Color.blue);
	            }
	            else{
	                this.Mfr[i].setBackground(Color.white);                
	            }
	            NumMFR/=2;
	        }
	        
	        //show CC
	        NumCC = CPU_CC;;
	        for(int i=3; i>=0; i--){
	            if(NumCC%2==1){
	                this.Cc[i].setBackground(Color.blue);
	            }
	            else{
	                this.Cc[i].setBackground(Color.white);                
	            }
	            NumCC/=2;
	        }
	    }
	
	    private void SearchINAddress(String keyIN){
	        int address = Integer.parseInt(keyIN);
	        if (address > mcu.getCurrentMemorySize() - 1 || address < 0) {
	            JOptionPane.showMessageDialog(null,
	            "Memory between 0 and " + (mcu.getCurrentMemorySize() - 1) + "!");
	            } else {
	                int value = mcu.fetchFromMemory(address);
	                text_Val.setText(String.valueOf(value));
	            }
	    }
	    
	    private void DepositINAddress(String keyIN, String AddIN){       
	        
	        int address = Integer.parseInt(keyIN);
	        int value = Integer.parseInt(AddIN);
	        if (address > mcu.getCurrentMemorySize() - 1 || address < 0) {
	            JOptionPane.showMessageDialog(null,
	                "Memory between 0 and " + (mcu.getCurrentMemorySize() - 1) + "!");
	        } else if (value > 0xffff || value < 0) {
	                JOptionPane.showMessageDialog(null, "Value between 0 and 65535!");
	        } else {
	            mcu.storeInMemory(address, value);
	            text_Address.setText("0");
	            text_Val.setText("");
	        printConsole("store memory address " + address + " with value " + value);
	        }
	    }
	    
	   public static void main(String[] args) {      
	        EventQueue.invokeLater(new Runnable(){
	            public void run(){
	                new console();
	            }
	        });
	    }
}