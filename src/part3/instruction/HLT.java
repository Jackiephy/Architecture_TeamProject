package instruction;

import memory.MCU;
import cpu.CPU;

/**
 *
 * @author Alina
 */
public class HLT extends Abstractinstruction {
	@Override
	public void execute(String instruction, CPU cpu, MCU mcu){
		// -----------------------------------
		// 000: HALT -> Stops the machine.
		// -----------------------------------
		// TODO Auto-generated method stub
		if(instruction.substring(8,16).equals("00000000")){
			//System.out.println("HALT!");
			//JOptionPane.showMessageDialog(null, "Program stop!");
		}
	}

	@Override
	public String getExecuteMessage() {

		// TODO Auto-generated method stub
		return "HALT";

	}    
}
