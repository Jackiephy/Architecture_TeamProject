package part2.util;

import part2.memory.Memory;
import part2.cpu.CPU;
import part2.memory.MCU;
import part2.util.MachineFaultException;

/**
 *
 * @author Alina
 */
public class EffectiveAddress {
    public static int computeEffectiveAddress(int IX, int address, int i, MCU mcu, CPU cpu) throws MachineFaultException{
        //move the operand address from the IR to IAR
        if (i == 0) {
	// not indirect addressing
            if (IX == 0) {// NO indexing
		if (checkMachineFault(address, mcu) == 1) {
			return address;
		}
            } else {
		if (checkMachineFault(address + cpu.getXnByNum(IX), mcu) == 1) {
                    return address + cpu.getXnByNum(IX);
		}
		}

	} else if (i == 1) {
	// indirect addressing
            if (IX == 0) {// NO indexing
		if (checkMachineFault(address, mcu) == 1) {
                    cpu.setMAR(address);
                    cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
		}
            } else {
		if (checkMachineFault(address + cpu.getXnByNum(IX), mcu) == 1) {
                    cpu.setMAR(address + cpu.getXnByNum(IX));
                    cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
		}
            }
            return cpu.getIntMBR();
            }
	return 0;
    }    
    
    	public static int checkMachineFault(int address, MCU mcu) throws MachineFaultException {
		// in here we check if we have a machine fault or not

		// first, we should check if it is a reserved location in memory or not
		if (address < 6) {
			throw new MachineFaultException(Const.Fault.ILL_MEM_RSV.getValue(),
					Const.Fault.ILL_MEM_RSV.getMessage());
			// now we should check if address is beyond the size of memory or
			// not
		} else if (address > mcu.getCurrentMemorySize() - 1) {
			throw new MachineFaultException(Const.Fault.ILL_MEM_BYD.getValue(),
					Const.Fault.ILL_MEM_BYD.getMessage());
			// if there is no machine fault we can safely return 1
		} else {
			return 1;
		}
	}
}
