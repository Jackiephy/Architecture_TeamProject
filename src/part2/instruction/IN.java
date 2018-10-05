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
public class IN extends Abstractinstruction {

    int num;
    int devId;

    @Override
    public void execute(String instruction, CPU cpu, MCU mcu) {
        this.num = StringUtil.binaryToDecimal(instruction.substring(6, 8));
        this.devId = StringUtil.binaryToDecimal(instruction.substring(11, 16));

        if (this.devId == Const.DevId.KEYBOARD.getValue()) {
            String buffer = mcu.getKeyboardBuffer();

            if (buffer != null && buffer.length() > 0) {

                int val = buffer.charAt(0);
                cpu.setRnByNum(this.num, val);
                mcu.setKeyboardBuffer(buffer.substring(1, buffer.length()));

            } else {
                cpu.setRnByNum(this.num, 0);
            }

        }
        if (this.devId == Const.DevId.CARD.getValue()) {
            String buffer = mcu.getCardBuffer();
            
            if (buffer != null && buffer.length() > 0) {
                
                int val = buffer.charAt(0);
                cpu.setRnByNum(this.num, val);
                mcu.setCardBuffer(buffer.substring(1, buffer.length()));
                
            } else {
                cpu.setRnByNum(this.num, 0);
            }
        }
        cpu.increasePC();
    }

    @Override
    public String getExecuteMessage() {
        return "IN " + this.num + ", " + this.devId;
    }

}
