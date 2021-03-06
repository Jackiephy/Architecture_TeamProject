package part2.instruction;

import part2.cpu.CPU;
import part2.memory.MCU;
import part2.util.Const;
import part2.util.EffectiveAddress;
import part2.util.MachineFaultException;
import part2.util.StringUtil;

/**
 *
 * @author Alina
 */
public class LDFR extends Abstractinstruction{

	int fr;
	int ix;
	int address;
	int i;

	@Override
	public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
		// TODO Auto-generated method stub
		fr = StringUtil.binaryToDecimal(instruction.substring(6, 8));
		ix = StringUtil.binaryToDecimal(instruction.substring(9, 11));
		i = StringUtil.binaryToDecimal(instruction.substring(8, 9));
		address = StringUtil.binaryToDecimal(instruction.substring(11, 16));
		int effectiveAddress = EffectiveAddress.computeEffectiveAddress(ix, address, i, mcu, cpu);
		
		String exp="0000000";
                String man="00000000";
	    
		cpu.setMAR(effectiveAddress);
		cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
		int expI=cpu.getIntMBR();
		cpu.setMAR(effectiveAddress+1);
		cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
		int manI=cpu.getIntMBR();

		

		String temp=Integer.toString(expI);
		exp=exp.substring(0,7-temp.length())+temp;
		String temp1=Integer.toString(manI);
		man=temp1+man.substring(temp1.length());

		

		
		String frs=exp+man;
		
		cpu.setFRByNum(fr, Integer.parseInt(frs,2));
		
		cpu.increasePC();
	}

	@Override
	public String getExecuteMessage() {
		// TODO Auto-generated method stub
		return "LDFR"+ fr + ", " + ix + ", " + address + "," +i;
	}

}
