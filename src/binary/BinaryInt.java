package binary;

/**
 *
 * @author Anderson
 */
public class BinaryInt {

    public boolean signed;
    public boolean[] binaryNumberWithoutSignal;

    public int length() {
        return binaryNumberWithoutSignal.length + 1;
    }

    public boolean[] fullBynaryNumber() {
        boolean[] fullBinaryNumber = new boolean[this.length()];

        fullBinaryNumber[0] = this.signed;
        for (int i = 1; i < fullBinaryNumber.length; i++) {
            fullBinaryNumber[i] = binaryNumberWithoutSignal[i - 1];
        }

        return fullBinaryNumber;
    }

}
