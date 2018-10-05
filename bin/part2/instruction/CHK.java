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
public class CHK extends Abstractinstruction {

	int num;
	int devId;

	@Override
	public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
		this.num = StringUtil.binaryToDecimal(instruction.substring(6, 8));
		this.devId = StringUtil.binaryToDecimal(instruction.substring(11, 16));

		if (this.devId == Const.DevId.KEYBOARD.getValue()) {
			cpu.setRnByNum(this.num, 1);
		}

		if (this.devId == Const.DevId.CARD.getValue()) {
			cpu.setRnByNum(this.num, 1);
		}

		if (this.devId == Const.DevId.PRINTER.getValue()) {
			cpu.setRnByNum(this.num, 1);
		}
	}

	@Override
	public String getExecuteMessage() {
		// TODO Auto-generated method stub
		return "CHK "+ num +", "+ devId;
	}

}