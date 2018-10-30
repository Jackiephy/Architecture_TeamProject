package instruction;

import cpu.CPU;
import memory.MCU;
import util.MachineFaultException;


/**
 *
 * @author Alina
 */
public abstract class Abstractinstruction {
    
    public abstract void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException;

    public abstract String getExecuteMessage();    
}
