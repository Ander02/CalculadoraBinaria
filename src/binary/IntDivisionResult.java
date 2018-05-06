package binary;

import java.util.Arrays;

/**
 *
 * @author Anderson
 */
public class IntDivisionResult {

    private BinaryInt quocient;
    private boolean[] rest;

    public IntDivisionResult() {
    }

    public IntDivisionResult(BinaryInt quocient, boolean[] rest) {
        this.quocient = quocient;
        this.rest = rest;
    }

    public BinaryInt getQuocient() {
        return quocient;
    }

    public void setQuocient(BinaryInt quocient) {
        this.quocient = quocient;
    }

    public boolean[] getRest() {
        return rest;
    }

    public void setRest(boolean[] rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Quocient: " + this.quocient + ": " + this.quocient.toInt() +
             "\nRest: " + Arrays.toString(this.rest);
    }
    
    
}