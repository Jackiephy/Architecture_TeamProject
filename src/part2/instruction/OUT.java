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
public class OUT extends Abstractinstruction {

	int num;
	int devId;

	@Override
	public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
		this.num = StringUtil.binaryToDecimal(instruction.substring(6, 8));
		this.devId = StringUtil.binaryToDecimal(instruction.substring(11, 16));
		if (this.devId == Const.DevId.PRINTER.getValue()) {
			int val = cpu.getRnByNum(this.num);
			char c = (char) val;
			
			mcu.setPrinterBuffer(String.valueOf(c));
			
			cpu.increasePC();
		}
	}

	@Override
	public String getExecuteMessage() {
		return "OUT " + this.num + ", " + this.devId;
	}

}