package part1;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;


/**
*
* @author Alina
*/
public class Console {
	

//private String getKey, getValue;
	   
public String ConsoleString = "";
	   
	
boolean Single = false, Continue = true;
	   
Memory mainMemory;
CPU cpu;
GUI ui;
	   
private int PC;
	
	public static void main(String[] args) throws IOException {
	
        //GUI show  
		GUI ui = new GUI();
		Load l = new Load();
		l.readFile(0);
		Memory m = new Memory();
		m.readFile(0);
		
		
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                new Console();
            }
        });
        //to do: GUI and ROM Loader program
        
    }
	
    public void initial(){
        cpu = new CPU();
        mainMemory = new Memory();
        
        cpu.setPC(6);
        
        //put the instructions and required data into main memory
        cpu.setIndexRegister(2, 1000);//initialize index register
        cpu.setIndexRegister(1, 700);
        cpu.setIndexRegister(0, 500);
        cpu.setGeneralRegister(0, "0");
        cpu.setGeneralRegister(1, "0");
        cpu.setGeneralRegister(2, "0");
        cpu.setGeneralRegister(3, "0");
        cpu.setMBR("0");
        cpu.setIR("0");
        mainMemory.setValue(6, "0000011100011111");//store instruction LDR 3,0,31
        mainMemory.setValue(31, "1110011001101101");//store the required data to location 31
        mainMemory.setValue(7, "0000101101010101");//store instruction STR 3,0,21
        mainMemory.setValue(8, "0000111001010001");//store instruction LDA 2,0,17
        mainMemory.setValue(9, "1010010010001100");//store instruction LDX 2,12
        mainMemory.setValue(712, "0000001110011001");//store the required data to location 712
        mainMemory.setValue(10, "1010100001011001");//store instruction STX, 1, 25
    }
	
	 public void Run(int pc){
	        Continue = true;
	        Single = false;
	        PC = pc;
	        execute(PC, Continue, Single);
	    }
	 
	    public void execute(int memoryLocation, boolean Continue, boolean Single) {
	        if(Continue){
	            //initialize PC
	            cpu.setPC(memoryLocation);
	            System.out.println(cpu.getPC());
	            if(mainMemory.getValue(cpu.getPC()) == null){
	                System.out.println("4"); 
	                return;
	            }
	            //put location from PC to MAR ,it needs 1 clock
	            cpu.setMAR(cpu.getMAR());
	            cpu.clock++;
	            System.out.println("2");            
	            //MCU uses the address in the MAR to fetch a word from memory and place it in MBR
	            if(mainMemory.getValue(cpu.getMAR()) == null){
	                cpu.setMBR("");
	            }
	            else{
	                cpu.setMBR(mainMemory.getValue(cpu.getMAR()));
	            }
	            cpu.clock++;
	            System.out.println("3"); 
	            //The contents of MBR are moved to the IR. This takes 1 cycle.
	            cpu.setIR(cpu.getMBR());
	            cpu.clock++;
	            System.out.println(cpu.getPC());
	            System.out.println(cpu.getMAR());
	            System.out.println(cpu.getMBR());
	            System.out.println(cpu.getIR());
	            ShowNumberR(cpu.getR0(),cpu.getR1(), cpu.getR2(), cpu.getR3(),"0", "0", "0", "0", true);
	            ShowNumberX("", "", "", cpu.getX1(),cpu.getX2(), cpu.getX3(), true);
	            ShowNumberO( "", "", cpu.getMBR(), cpu.getIR(), cpu.getPC(), cpu.getMAR(), "0", "0", true);
	            ShowNumberZ(cpu.getMFR(), cpu.getCC());    
	            System.out.println(cpu.clock);
	            
	            //extract the opcode, R(registerSelect), IX, I, address from the IR.
	            //we transfer long to int for saving space of memory,
	            //instructions below show the transfer process: string of binary number -> long -> int
	            cpu.opcode = Integer.parseInt(String.valueOf(Long.valueOf(cpu.getIR())/100000/100000),2);
	            cpu.registerSelect = Integer.parseInt(String.valueOf(Long.valueOf(cpu.getIR())/100000000%100),2);
	            cpu.IX = Integer.parseInt(String.valueOf(Long.valueOf(cpu.getIR())/1000000%100),2);
	            cpu.I = Integer.parseInt(String.valueOf(Long.valueOf(cpu.getIR())/100000%10));
	            cpu.address = Integer.parseInt(String.valueOf(Long.valueOf(cpu.getIR())%100000),2);
	            cpu.clock++;
	            //determine the class of opcode
	            switch (cpu.opcode){
	                case 1: cpu.LDR(mainMemory, cpu.registerSelect, cpu.IX, cpu.I, cpu.address);
	                break;
	                case 2: cpu.STR(mainMemory, cpu.registerSelect, cpu.IX, cpu.I, cpu.address);
	                break;
	                case 3: cpu.LDA(mainMemory, cpu.registerSelect, cpu.IX, cpu.I, cpu.address);
	                break;
	                case 41: cpu.LDX(mainMemory, cpu.IX, cpu.I, cpu.address);
	                break;
	                case 42: cpu.STX(mainMemory, cpu.IX, cpu.I, cpu.address);
	                break;

	            }
	            cpu.setPC(cpu.getPC()+1);
	            if(Single){
	                Continue = false;
	                PC = cpu.getPC();               
	            }
	            execute(cpu.getPC(), Continue, Single);
	        }
	        else{
	            PC = cpu.getPC();
	        }
	    }
	    
	    public void ShowNumberR(String TextR0, String TextR1, String TextR2, String TextR3, String CPU_R0, String CPU_R1, String CPU_R2, String CPU_R3, boolean showR){
	        int NumR0, NumR1, NumR2, NumR3;
	        //show R0
	        if(!(TextR0.equals(""))){
	            NumR0 = Integer.parseInt(TextR0);
	            if(NumR0>0 && NumR0<65536){
	                NumR0 = NumR0;
	                cpu.setGeneralRegister(0, TextR0);
	                ConsoleString = ConsoleString + "\r\nchange R0 to "+TextR0;
	                ui.ta.setText(ConsoleString);
	            }
	            else{
	                NumR0 = Integer.parseInt(CPU_R0);
	            }
	        }
	        else{
	            NumR0 = Integer.parseInt(CPU_R0);
	        }
	        for(int i=15; i>=0; i--){
	            if(NumR0%2==1){
	                if(showR){ui.indicate[9][i].setText("1");}
	                else{ui.indicate[9][i].setText("X");}
	            }
	            else{
	            	ui.indicate[9][i].setText("0");
	            }
	            NumR0/=2;
	        }
	        
	        //show R1
	        if(!(TextR1.equals(""))){
	            NumR1 = Integer.parseInt(TextR1);
	            if(NumR1>0 && NumR1<65536){
	                NumR1 = NumR1;
	                cpu.setGeneralRegister(1, TextR1);
	                ConsoleString = ConsoleString + "\r\nchange R1 to "+TextR1;
	                ui.ta.setText(ConsoleString);
	            }
	            else{
	                NumR1 = Integer.parseInt(CPU_R1);
	            }
	        }
	        else{
	            NumR1 = Integer.parseInt(CPU_R1);
	        }
	        for(int i=15; i>=0; i--){
	            if(NumR1%2==1){
	                if(showR){ui.indicate[10][i].setText("1");
	                System.out.println("indicate£º"+ui.indicate[10][i]);
	                }
	                
	                else{ui.indicate[10][i].setText("X");}
	                System.out.println("indicate£º"+ui.indicate[10][i]);
	            }
	            else{
	            	ui.indicate[10][i].setText("0");
	            	System.out.println("indicate£º"+ui.indicate[10][i]);
	            }
	            NumR1/=2;
	        }
	        
	        //show R2
	        if(!(TextR2.equals(""))){
	            NumR2 = Integer.parseInt(TextR2);
	            if(NumR2>0 && NumR2<65536){
	                NumR2 = NumR2;
	                cpu.setGeneralRegister(1, TextR1);
	                ConsoleString = ConsoleString + "\r\nchange R2 to "+TextR2;
	                ui.ta.setText(ConsoleString);
	            }
	            else{
	                NumR2 = Integer.parseInt(CPU_R2);
	            }
	        }
	        else{
	            NumR2 = Integer.parseInt(CPU_R2);
	        }
	        for(int i=15; i>=0; i--){
	            if(NumR2%2==1){
	                if(showR){ui.indicate[11][i].setText("1");}
	                else{ui.indicate[11][i].setText("X");}
	            }
	            else{
	            	ui.indicate[11][i].setText("0");
	            }
	            NumR2/=2;
	        }
	        
	        //show R3
	        if(!(TextR3.equals(""))){
	            NumR3 = Integer.parseInt(TextR3);
	            if(NumR3>0 && NumR3<65536){
	                NumR3 = NumR3;
	                cpu.setGeneralRegister(3, TextR3);
	                ConsoleString = ConsoleString + "\r\nchange R3 to "+TextR3;
	                ui.ta.setText(ConsoleString);
	            }
	            else{
	                NumR3 = Integer.parseInt(CPU_R1);
	            }
	        }
	        else{
	            NumR3 = Integer.parseInt(CPU_R3);
	        }
	        for(int i=15; i>=0; i--){
	            if(NumR3%2==1){
	                if(showR){ui.indicate[12][i].setText("1");}
	                else{ui.indicate[12][i].setText("X");}
	            }
	            else{
	            	ui.indicate[11][i].setText("0");
	            }
	            NumR3/=2;
	        }
	        
	    }
	    
	    public void ShowNumberX(String TextX1, String TextX2, String TextX3, int CPU_X1, int CPU_X2, int CPU_X3, boolean showX){
	        int NumX1, NumX2, NumX3;
	        //show X1
	        if(!(TextX1.equals(""))){
	            NumX1 = Integer.parseInt(TextX1);
	            if(NumX1>0 && NumX1<65536){
	                NumX1 = NumX1;
	                cpu.setIndexRegister(0, NumX1);
	                ConsoleString = ConsoleString + "\r\nchange X1 to "+TextX1;
	                ui.ta.setText(ConsoleString);
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
	                if(showX){ui.indicate[13][i].setText("1");}
	                else{ui.indicate[13][i].setText("X");}
	            }
	            else{
	            	ui.indicate[13][i].setText("0");
	            }
	            NumX1/=2;
	        }
	        
	        //show X2
	        if(!(TextX2.equals(""))){
	            NumX2 = Integer.parseInt(TextX2);
	            if(NumX2>0 && NumX2<65536){
	                NumX2 = NumX2;
	                cpu.setIndexRegister(1, NumX2);
	                ConsoleString = ConsoleString + "\r\nchange X2 to "+TextX2;
	                ui.ta.setText(ConsoleString);
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
	                if(showX){ui.indicate[14][i].setText("1");}
	                
	                else{ui.indicate[14][i].setText("X");}
	            }
	            else{
	            	ui.indicate[14][i].setText("0");
	            }
	            NumX2/=2;
	        }

	        //show X3
	        if(!(TextX3.equals(""))){
	            NumX3 = Integer.parseInt(TextX3);
	            if(NumX3>0 && NumX3<65536){
	                NumX3 = NumX3;
	                cpu.setIndexRegister(2, NumX3);
	                ConsoleString = ConsoleString + "\r\nchange X3 to "+TextX3;
	                ui.ta.setText(ConsoleString);
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
	                if(showX){ui.indicate[15][i].setText("1");}
	                else{ui.indicate[15][i].setText("X");}
	            }
	            else{
	            	ui.indicate[15][i].setText("0");
	            }
	            NumX3/=2;
	        }
	        
	    }
	    
	    public void ShowNumberO(String TextPC, String TextMAR, String TextMBR, String TextIR, int CPU_PC, int CPU_MAR, String CPU_MBR, String CPU_IR, boolean showO){
	        int NumPC, NumMAR, NumMBR, NumIR;
	        //show PC
	        System.out.println("A");
	        if(!(TextPC.equals(""))){
	            NumPC = Integer.parseInt(TextPC);
	            if(NumPC>0 && NumPC<4096){
	                NumPC = NumPC;
	                cpu.setPC(NumPC);                
	                ConsoleString = ConsoleString + "\r\nchange PC to "+TextPC;
	                ui.ta.setText(ConsoleString);
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
	                if(showO){ui.indicate[0][i].setText("1");}
	                else{ui.indicate[0][i].setText("X");}
	            }
	            else{
	            	ui.indicate[0][i].setText("0");
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
	                ui.ta.setText(ConsoleString);
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
	                if(showO){ui.indicate[3][i].setText("1");}
	                else{ui.indicate[3][i].setText("X");}
	            }
	            else{
	            	ui.indicate[3][i].setText("0");
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
	                ui.ta.setText(ConsoleString);
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
	                if(showO){ui.indicate[4][i].setText("1");}
	                else{ui.indicate[4][i].setText("X");}
	            }
	            else{
	            	ui.indicate[4][i].setText("0");
	            }
	            NumMBR/=2;
	        }
	        
	        //show IR
	        if(!(TextIR.equals(""))){
	            NumIR = Integer.parseInt(TextIR);
	            if(NumIR>0 && NumIR<65536){
	                NumIR = NumIR;
	                cpu.setIR(TextIR);
	                ConsoleString = ConsoleString + "\r\nchange IR to "+TextIR;
	                ui.ta.setText(ConsoleString);
	            }
	            else{
	                NumIR = Integer.parseInt(CPU_IR);
	            }
	        }
	        else{
	            NumIR = Integer.parseInt(CPU_IR);
	        }
	        for(int i=15; i>=0; i--){
	            if(NumIR%2==1){
	                if(showO){ui.indicate[2][i].setText("1");}
	                else{ui.indicate[2][i].setText("X");}
	            }
	            else{
	            	ui.indicate[2][i].setText("0");
	            }
	            NumIR/=2;
	        }        

	    }
	    
	    public void ShowNumberZ(int CPU_MFR, String CPU_CC){
	        int NumMFR, NumCC;
	        //show MFR
	        NumMFR = CPU_MFR;
	        for(int i=3; i>=0; i--){
	            if(NumMFR%2==1){
	            	ui.indicate[6][i].setText("1");
	            }
	            else{
	            	ui.indicate[6][i].setText("0");                
	            }
	            NumMFR/=2;
	        }
	        
	        //show CC
	        NumCC = Integer.parseInt(CPU_CC);;
	        for(int i=3; i>=0; i--){
	            if(NumCC%2==1){
	            	ui.indicate[1][i].setText("1");
	            }
	            else{
	            	ui.indicate[1][i].setText("0");                
	            }
	            NumCC/=2;
	        }
	    }
	
	    public void Single(){
	        Single = true;
	        Continue = true;
	        execute(PC, Continue, Single);
	    }
}
