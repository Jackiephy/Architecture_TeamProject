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
public class RFS extends Abstractinstruction {

    int immed;

    @Override
    public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
        // 015: RFS -> Return From Subroutine w/ return code as Immed portion
        // (optional) stored in the instruction's address field
        // cpu.setR0(Immed));
        immed = StringUtil.binaryToDecimal(instruction.substring(11, 16));
        cpu.setRnByNum(0, immed);
        cpu.setPC(cpu.getRnByNum(3));
    }

    @Override
    public String getExecuteMessage() {
        // TODO Auto-generated method stub
        return "RFS " + immed;
    }

}
