package binary;

/**
 *
 * @author Anderson Pessoa
 * @author Marcus Vinicius
 */
public class SumBitAux {

    //Attributes
    private boolean result;
    private boolean carry;

    public SumBitAux() {
    }

    //Setter
    private void setSumResult(boolean carry, boolean result) {
        this.result = result;
        this.carry = carry;
    }

    //Main constructor
    public SumBitAux(boolean bit1, boolean bit2, boolean carry) {

        if (bit1 == true) {
            if (bit2 == true) {
                if (carry == true) {
                    //Bit1 = 1
                    //Bit2 = 1
                    //Carry = 1
                    this.setSumResult(true, true);
                } else {
                    //Bit1 = 1
                    //Bit2 = 1
                    //Carry = 0
                    this.setSumResult(true, false);
                }
            } else if (carry == true) {
                //Bit1 = 1
                //Bit2 = 0
                //Carry = 1
                this.setSumResult(true, false);
            } else {
                //Bit1 = 1
                //Bit2 = 0
                //Carry = 0
                this.setSumResult(false, true);
            }
        } else if (bit2 == true) {
            if (carry == true) {
                //Bit1 = 0
                //Bit2 = 1
                //Carry = 1
                this.setSumResult(true, false);
            } else {
                //Bit1 = 0
                //Bit2 = 1
                //Carry = 0
                this.setSumResult(false, true);
            }
        } else if (carry == true) {
            //Bit1 = 0
            //Bit2 = 0
            //Carry = 1
            this.setSumResult(false, true);
        } else {
            //Bit1 = 0
            //Bit2 = 0
            //Carry = 0
            this.setSumResult(false, false);
        }
    }

    //Getters
    public boolean getResult() {
        return this.result;
    }

    public boolean getCarry() {
        return this.carry;
    }

    @Override
    public String toString() {
        return "Result: " + this.getResult() + "\n" + "Carry: " + this.getCarry();
    }

}
