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
public class JSR extends Abstractinstruction {

	int ix;
	int address;
	int i;

	@Override
	public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
		// 014: JSR -> Jump and Save Return Address
		ix = StringUtil.binaryToDecimal(instruction.substring(8, 10));
		i = StringUtil.binaryToDecimal(instruction.substring(10, 11));
		address = StringUtil.binaryToDecimal(instruction.substring(11, 16));

		int effectiveAddress = EffectiveAddress.computeEffectiveAddress(ix, address, i, mcu, cpu);

		cpu.setRnByNum(3, cpu.getPC() + 1);
		cpu.setPC(effectiveAddress);
	}

	@Override
	public String getExecuteMessage() {
		// TODO Auto-generated method stub
		return "JSR " + ix + ", " + address + ", " + i;
	}

}
