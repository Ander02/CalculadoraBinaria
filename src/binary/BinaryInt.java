package binary;

import exception.BinaryOverflowException;
import java.util.Arrays;

/**
 *
 * @author Anderson
 */
public class BinaryInt {

    private boolean signed;
    private boolean[] binaryNumberWithoutSignal;

    public BinaryInt(int x) {
        this.signed = false;
        this.binaryNumberWithoutSignal = Util.toBinaryIntArray(Math.abs(x));
    }

    public BinaryInt(boolean[] binaryNumberWithoutSignal) {
        this.signed = false;
        this.binaryNumberWithoutSignal = binaryNumberWithoutSignal;
    }

    public BinaryInt(boolean signed, boolean[] binaryNumberWithoutSignal) {
        this.signed = signed;
        this.binaryNumberWithoutSignal = binaryNumberWithoutSignal;
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

    public BinaryInt sum(BinaryInt bin) throws BinaryOverflowException {

        boolean[] normalized;
        boolean[] other;

        //Normalize the binarys
        if (this.binaryNumberWithoutSignal.length > bin.binaryNumberWithoutSignal.length) {
            normalized = bin.normalize(this.binaryNumberWithoutSignal.length + 1);
            other = this.fullBynaryNumber();
            normalized[0] = bin.signed;
            other[0] = this.signed;
        } else {
            normalized = this.normalize(bin.binaryNumberWithoutSignal.length + 1);
            other = bin.fullBynaryNumber();
            normalized[0] = this.signed;
            other[0] = bin.signed;
        }

        System.out.println("O mesmo:    " + Arrays.toString(other) + ": " + toInt(other));
        System.out.println("Normalizado " + Arrays.toString(normalized) + ": " + toInt(normalized));

        //Realiza a soma e armazena em resp
        boolean[] resp = new boolean[normalized.length];
        boolean carry = false;
        for (int i = normalized.length - 1; i >= 1; i--) {
            SumBitAux aux = new SumBitAux(normalized[i], other[i], carry);

            carry = aux.getCarry();
            resp[i] = aux.getResult();
        }

        if (carry == true) {
            throw new BinaryOverflowException("The sum is not possible because have an overflow");
        }

        boolean[] binAux = new boolean[resp.length - 1];
        for (int i = 1; i < resp.length; i++) {
            binAux[i - 1] = resp[i];
        }

        bin.signed = resp[0];
        bin.binaryNumberWithoutSignal = binAux;

        //System.out.println("resposta  : " + Arrays.toString(resp) + ": " + toInt(resp));
        //System.out.println("");
        return bin;
    }

    public BinaryInt sub(BinaryInt bin) {

        try {
            bin = BinaryInt.complementoDeDois(bin);
            System.out.println(bin);
            System.out.println(this);
            BinaryInt resp = bin.sum(this);

            return resp;

        } catch (BinaryOverflowException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public boolean[] normalize(int length) {

        int originalLength = this.length();

        boolean[] fullBin = this.fullBynaryNumber();

        if (length == originalLength) {
            return fullBin;
        }

        boolean[] normalizedBin = new boolean[length];
        int i;
        normalizedBin[0] = fullBin[0];
        for (i = 1; i < length - originalLength; i++) {
            normalizedBin[i] = false;
        }
        for (int j = i; j < length; j++) {
            normalizedBin[j] = fullBin[j - i];
        }

        return normalizedBin;
    }

    public static BinaryInt complementoDeUm(BinaryInt binary) {
        boolean[] result = new boolean[binary.binaryNumberWithoutSignal.length];

        BinaryInt aux;

        for (int i = result.length - 1; i > 0; i--) {
            result[i] = !binary.binaryNumberWithoutSignal[i];
        }
        if (binary.signed == true) {
            aux = new BinaryInt(false, result);
        } else {
            aux = new BinaryInt(true, result);
        }
        return aux;
    }

    public BinaryInt complementoDeUm() {
        boolean[] result = new boolean[this.length() - 1];

        BinaryInt aux;

        for (int i = result.length - 1; i > 0; i--) {
            result[i] = !this.binaryNumberWithoutSignal[i];
        }
        if (this.signed == true) {
            aux = new BinaryInt(false, result);
        } else {
            aux = new BinaryInt(true, result);
        }
        return aux;
    }

    public static BinaryInt complementoDeDois(BinaryInt binary) {

        try {

            //Resolver exception no caso de overflow na soma com 1 para truncar o número
            BinaryInt one = new BinaryInt(1);
            BinaryInt aux = complementoDeUm(binary);
            BinaryInt result = aux.sum(one);

            return result;
        } catch (BinaryOverflowException ex) {
            System.out.println(ex);
            return null;
        }
    }

    /**
     * @return The int value representation
     */
    public int toInt() {
        int toInt = 0;
        int length = this.binaryNumberWithoutSignal.length - 1;

        for (int i = 0; i <= length; i++) {
            if (this.binaryNumberWithoutSignal[i]) {
                toInt += (int) Math.pow(2, (length - i));
            }
        }
        return toInt;
    }

    public static int toInt(boolean[] binNum) {
        int toInt = 0;
        int length = binNum.length - 1;

        for (int i = 0; i <= length; i++) {
            if (binNum[i]) {
                toInt += (int) Math.pow(2, (length - i));
            }
        }
        return toInt;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.fullBynaryNumber());
    }

}
