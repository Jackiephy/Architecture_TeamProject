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
public class STFR extends Abstractinstruction {

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

		
		int cfr=cpu.getFRByNum(fr);
		String buffer="0000000000000000";
		String frs=Integer.toBinaryString(cfr);
		if(frs.length()<16)
		frs=buffer.substring(0, 16-frs.length())+frs;
		
		int man=Integer.parseInt(frs.substring(8, 16),2);
		int exp=Integer.parseInt(frs.substring(0, 8),2);
		


		


		cpu.setMAR(effectiveAddress);
		cpu.setMBR(exp);
		mcu.storeIntoCache(cpu.getMAR(), cpu.getIntMBR());

		cpu.setMAR(effectiveAddress + 1);
		cpu.setMBR(man);
		mcu.storeIntoCache(cpu.getMAR(), cpu.getIntMBR());

		cpu.increasePC();
	}

	@Override
	public String getExecuteMessage() {
		// TODO Auto-generated method stub
		return "STFR" + fr + ", " + ix + ", " + address + "," + i;
	}

}
