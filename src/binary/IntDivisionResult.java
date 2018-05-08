package binary;

import java.util.Arrays;

/**
 *
 * @author Anderson
 */
public class IntDivisionResult {

    private BinaryInt quocient;
    private BinaryInt rest;

    public IntDivisionResult() {
    }

    public IntDivisionResult(BinaryInt quocient, BinaryInt rest) {
        this.quocient = quocient;
        this.rest = rest;
    }

    public BinaryInt getQuocient() {
        return quocient;
    }

    public void setQuocient(BinaryInt quocient) {
        this.quocient = quocient;
    }

    public BinaryInt getRest() {
        return rest;
    }

    public void setRest(BinaryInt rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Quocient: " + this.quocient + ": " + this.quocient.toInt()
                + "\nRest: " + this.rest + ":" + this.rest.toInt();
    }

}
