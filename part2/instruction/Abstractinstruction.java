package part2.instruction;

import part2.cpu.CPU;
import part2.memory.MCU;
import part2.util.MachineFaultException;


/**
 *
 * @author Alina
 */
public abstract class Abstractinstruction {
    
    public abstract void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException;

    public abstract String getExecuteMessage();    
}
