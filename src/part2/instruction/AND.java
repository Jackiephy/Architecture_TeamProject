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
public class AND extends Abstractinstruction {
    int rx;
    int ry;


    @Override
    public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
        // -----------------------------------
        // 023: AND-> Logical And of Register and Register
        // c(rx) <- c(rx) AND c(ry)
        // -----------------------------------
        // TODO Auto-generated method stub

        this.rx = StringUtil.binaryToDecimal(instruction.substring(6, 8));
        this.ry = StringUtil.binaryToDecimal(instruction.substring(8, 10));
        int x = cpu.getRnByNum(rx);
        int y = cpu.getRnByNum(ry);
        cpu.setRnByNum(rx, x & y);

        cpu.increasePC();
    }

    @Override
    public String getExecuteMessage() {
        // TODO Auto-generated method stub
        return "AND " + rx + ", " + ry;
    }


}
