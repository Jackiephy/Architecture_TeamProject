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
public class TRR extends Abstractinstruction {

    int rx;
    int ry;

    @Override
    public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
        // ------------------------------------------------------
        // 022: TRR -> Test the equality of Register and Register
        // if c(rx) = c(ry), set cc(4) <- 1; else, cc(4) <- 0
        // ------------------------------------------------------

        rx = StringUtil.binaryToDecimal(instruction.substring(6, 8));
        ry = StringUtil.binaryToDecimal(instruction.substring(8, 10));

        if (cpu.getRnByNum(rx) == cpu.getRnByNum(ry)) {
            cpu.setCCElementByBit(Const.ConditionCode.EQUALORNOT.getValue(), true);
        } else {
            cpu.setCCElementByBit(Const.ConditionCode.EQUALORNOT.getValue(), false);
        }

        cpu.increasePC();
    }

    @Override
    public String getExecuteMessage() {
        return "TRR " + rx + ", " + ry;
    }
}
