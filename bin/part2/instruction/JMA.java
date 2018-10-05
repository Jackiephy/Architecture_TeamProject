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
public class JMA extends Abstractinstruction {

	int ix;
	int address;
	int i;

	@Override
	public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
		// 013: JMA -> Unconditional Jump To Address
		ix = StringUtil.binaryToDecimal(instruction.substring(8, 10));
		address = StringUtil.binaryToDecimal(instruction.substring(11, 16));
		i = StringUtil.binaryToDecimal(instruction.substring(10, 11));

		int effectiveAddress = EffectiveAddress.computeEffectiveAddress(ix, address, i, mcu, cpu);

		cpu.setPC(effectiveAddress);
	}

	@Override
	public String getExecuteMessage() {
		// TODO Auto-generated method stub
		return "JMA " + ix + ", " + address + ", " + i;
	}

}
