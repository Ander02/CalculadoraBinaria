package binary;

/**
 *
 * @author Anderson
 */
public class BinaryInt {

    public boolean signed;
    public boolean[] binaryNumberWithoutSignal;
    
    public BinaryInt(int x){
        if (x>=0){
            this.signed = false;
            this.binaryNumberWithoutSignal = Util.toBinaryIntArray(x);
        }
        else
            this.signed = true;
            this.binaryNumberWithoutSignal = Util.complementoDeDois(Util.toBinaryIntArray(Math.abs(x)));
    }

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
