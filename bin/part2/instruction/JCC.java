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
public class JCC extends Abstractinstruction {

	int cc;
	int ix;
	int address;
	int i;

	@Override
	public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
		// 012: JCC -> Jump If Condition Code cc replaces r for this instruction
		cc = StringUtil.binaryToDecimal(instruction.substring(6, 8));
		ix = Integer.valueOf(instruction.substring(8, 10));
		address = StringUtil.binaryToDecimal(instruction.substring(11, 16));
		i = Integer.valueOf(instruction.substring(10, 11));

		int effectiveAddress = EffectiveAddress.computeEffectiveAddress(ix, address, i, mcu, cpu);

		if (cpu.getCCElementByBit(cc)) {
			cpu.setPC(effectiveAddress);
		} else {
			cpu.increasePC();
		}
	}

	@Override
	public String getExecuteMessage() {
		// TODO Auto-generated method stub
		return "JCC " + cc + ", " + ix + ", " + address + ", " + i;
	}
}
