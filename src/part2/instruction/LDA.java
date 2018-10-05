package part2.instruction;

import part2.memory.MCU;
import part2.util.StringUtil;
import part2.cpu.CPU;
import part2.util.EffectiveAddress;
import part2.util.MachineFaultException;

/**
 *
 * @author Alina
 */
public class LDA extends Abstractinstruction {

	int r;
	int ix;
	int address;
	int i;

	@Override
	public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
		// -----------------------------------
		// 03:LDA -> Load Register From Memory
		// -----------------------------------
		r = StringUtil.binaryToDecimal(instruction.substring(6, 8));
		ix = StringUtil.binaryToDecimal(instruction.substring(8, 10));
		i = StringUtil.binaryToDecimal(instruction.substring(10, 11));
		address = StringUtil.binaryToDecimal(instruction.substring(11, 16));

		int effectiveAddress = EffectiveAddress.computeEffectiveAddress(ix, address, i, mcu, cpu);

		// reading the content of selected register using [R] in the
		// instruction
		cpu.setRnByNum(r, effectiveAddress);
		cpu.increasePC();

	}

	@Override
	public String getExecuteMessage() {
		return "LDA " + r + ", " + ix + ", " + address + ", " + i;
	}

}
