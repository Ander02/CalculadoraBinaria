package binary;

import exception.BinaryArrayException;
import java.util.Arrays;

/**
 *
 * @author Anderson
 */
public class BinaryInt {

    public boolean signed;
    public boolean[] binaryNumber;
    public int bitSize;
    public int original;

    public BinaryInt() {
    }

    public BinaryInt(BinaryInt bin) {
        this.signed = bin.signed;
        this.bitSize = bin.bitSize;
        this.original = bin.original;
        this.binaryNumber = bin.binaryNumber;
    }

    public BinaryInt(int num, int bitSize) {
        this.original = num;
        this.bitSize = bitSize;
        try {
            if (num >= 0) {
                this.signed = false;
                this.binaryNumber = BinaryInt.toBinary(Math.abs(num), bitSize);
            } else {
                this.signed = true;
                boolean[] aux = BinaryInt.complementOfTwo(BinaryInt.toBinary(Math.abs(num), bitSize + 1));
                this.binaryNumber = new boolean[bitSize];
                for (int i = 1; i < aux.length; i++) {
                    this.binaryNumber[i - 1] = aux[i];
                }
            }
        } catch (BinaryArrayException ex) {
            System.out.println(ex.message);
        }
    }

    public BinaryInt(int bitSize) {
        this.bitSize = bitSize;
        this.binaryNumber = new boolean[bitSize];
        this.original = 0;
        this.signed = false;
    }

    /*
    public BinaryInt(boolean[] binaryNumber) {
        this.signed = false;
        this.binaryNumber = binaryNumber;
        this.original = BinaryInt.toInt(binaryNumber);
        this.bitSize = binaryNumber.length;
    }*/
    public BinaryInt(boolean signed, boolean[] binaryNumber) {
        this.signed = signed;
        this.binaryNumber = binaryNumber;
        this.bitSize = binaryNumber.length;
        this.original = BinaryInt.toInt(this.signed, this.binaryNumber);
    }

    public int length() {
        return this.binaryNumber.length + 1;
    }

    public boolean[] fullBynaryNumber() {
        boolean[] fullBinaryNumber = new boolean[this.length()];

        fullBinaryNumber[0] = this.signed;
        for (int i = 1; i < fullBinaryNumber.length; i++) {
            fullBinaryNumber[i] = binaryNumber[i - 1];
        }

        return fullBinaryNumber;
    }

    public BinaryInt sum(BinaryInt bin) throws BinaryArrayException {

        boolean[] normalized;
        boolean[] other;
        boolean isPositiveSum = false;

        //Se ambos são positivos
        if (this.signed == false && bin.signed == false) {
            isPositiveSum = true;
        }
        /*
        BinaryInt normalizedObj;
        BinaryInt otherObj;
        if (this.bitSize > bin.bitSize) {
            normalizedObj = new BinaryInt(bin.original, this.bitSize);
            otherObj = new BinaryInt(this.original, this.bitSize);
        } else {
            normalizedObj = new BinaryInt(this.original, bin.bitSize);
            otherObj = new BinaryInt(bin.original, bin.bitSize);
        }*/

        if (this.binaryNumber.length > bin.binaryNumber.length) {
            normalized = bin.normalize(this.binaryNumber.length + 1);
            other = this.fullBynaryNumber();
            normalized[0] = bin.signed;
            //other[0] = this.signed;
        } else {
            normalized = this.normalize(bin.binaryNumber.length + 1);
            other = bin.fullBynaryNumber();
            normalized[0] = this.signed;
            //other[0] = bin.signed;
        }
        //normalized = normalizedObj.fullBynaryNumber();
        //other = otherObj.fullBynaryNumber();

        boolean[] resp = new boolean[normalized.length];
        boolean carry = false;
        for (int i = normalized.length - 1; i >= 0; i--) {
            SumBitAux aux = new SumBitAux(normalized[i], other[i], carry);

            carry = aux.getCarry();
            resp[i] = aux.getResult();
        }

        boolean[] binAux = new boolean[resp.length - 1];
        for (int i = 0; i < resp.length - 1; i++) {
            binAux[i] = resp[i + 1];
        }

        //Se deu um número negativo com soma positiva
        if (resp[0] == true && isPositiveSum == true) {
            throw new BinaryArrayException("Overflow Detectado na soma.");
        }

        BinaryInt ret = new BinaryInt(resp[0], binAux);

        return ret;
    }

    public static boolean[] sumIgnoringOverflow(boolean[] bin1, boolean[] bin2) {

        boolean[] normalized;
        boolean[] other;

        if (bin1.length > bin2.length) {
            normalized = BinaryInt.normalize(bin2, bin1.length);
            other = bin1;
            normalized[0] = bin2[0];
            //other[0] = bin1[0];
        } else {
            normalized = BinaryInt.normalize(bin1, bin2.length);
            other = bin2;
            normalized[0] = bin1[0];
            //other[0] = bin2[0];
        }

        boolean[] resp = new boolean[normalized.length];
        boolean carry = false;
        for (int i = normalized.length - 1; i >= 0; i--) {
            SumBitAux aux = new SumBitAux(normalized[i], other[i], carry);

            carry = aux.getCarry();
            resp[i] = aux.getResult();
        }

        return resp;
    }

    public BinaryInt sub(BinaryInt bin) throws BinaryArrayException {

        BinaryInt bin1 = new BinaryInt(this);
        BinaryInt bin2 = new BinaryInt(bin);

        if (this.binaryNumber == null || bin.binaryNumber == null) {
            throw new BinaryArrayException("O binário esperado não foi criado por algum erro.");
        }

        if (bin1.bitSize > bin2.bitSize) {
            bin2 = new BinaryInt(bin2.original, bin1.bitSize);
        } else {
            bin1 = new BinaryInt(bin1.original, bin2.bitSize);
        }

        bin2 = BinaryInt.complementOfTwo(bin2);
        bin1 = bin1.sum(bin2);

        return bin1;
    }

    public BinaryInt mult(BinaryInt bin) {

        boolean isNegative = this.signed != bin.signed;

        int multiplicandoBitSize = this.length();
        boolean[] multiplicandoBin = this.getModule().fullBynaryNumber();
        boolean[] negativeMultiplicandoBin = new boolean[multiplicandoBin.length];
        int multiplicatorBitSize = bin.length();
        boolean[] multiplicatorBin = bin.getModule().fullBynaryNumber();

        boolean[] addVector = new boolean[multiplicandoBitSize + multiplicatorBitSize + 1];
        boolean[] subVector = new boolean[multiplicandoBitSize + multiplicatorBitSize + 1];
        boolean[] productVector = new boolean[multiplicandoBitSize + multiplicatorBitSize + 1];

        for (int i = 0; i < multiplicandoBin.length; i++) {
            negativeMultiplicandoBin[i] = !multiplicandoBin[i];
        }

        negativeMultiplicandoBin = BinaryInt.sumIgnoringOverflow(negativeMultiplicandoBin, new boolean[]{true});
        negativeMultiplicandoBin[0] = true;

        //Inicializando linhas
        for (int i = 0; i < multiplicandoBitSize; i++) {
            addVector[i] = multiplicandoBin[i];
            subVector[i] = negativeMultiplicandoBin[i];
            productVector[i] = false;
        }
        for (int i = multiplicandoBitSize; i < multiplicatorBitSize + multiplicandoBitSize; i++) {
            addVector[i] = false;
            subVector[i] = false;
            productVector[i] = multiplicatorBin[i - multiplicandoBitSize];
        }
        addVector[addVector.length - 1] = false;
        subVector[subVector.length - 1] = false;
        productVector[productVector.length - 1] = false;

        for (int i = 0; i < multiplicatorBitSize; i++) {

            //Último e penúltimo índice do array produto
            boolean lastIndex = productVector[productVector.length - 1];
            boolean penultimateIndex = productVector[productVector.length - 2];

            if (penultimateIndex == false && lastIndex == true) {

                productVector = BinaryInt.sumIgnoringOverflow(productVector, addVector);

            } else if (penultimateIndex == true && lastIndex == false) {

                productVector = BinaryInt.sumIgnoringOverflow(productVector, subVector);
            }

            //Rigth Shift mantendo o bit de sinal
            productVector = BinaryInt.specialRigthShift(productVector);
        }

        //ùltimo rigth shift
        productVector = BinaryInt.specialRigthShift(productVector);

        productVector = BinaryInt.reduce(productVector);

        BinaryInt resp = new BinaryInt(false, productVector);

        if (isNegative) {
            resp = BinaryInt.complementOfTwo(resp);
        }

        return resp;
    }

    /**
     *
     * @param bin A binary number
     * @return The binary number shifted to rigth with more significative bit
     */
    public static boolean[] specialRigthShift(boolean[] bin) {
        boolean[] shiftedProductVector = new boolean[bin.length];

        shiftedProductVector[0] = bin[0];
        shiftedProductVector[1] = bin[0];

        for (int j = 1; j < bin.length - 1; j++) {
            shiftedProductVector[j + 1] = bin[j];
        }

        return shiftedProductVector;
    }

    public IntDivisionResult div(BinaryInt bin) throws BinaryArrayException {

        boolean isNegative = this.signed != bin.signed;

        BinaryInt one = new BinaryInt(1, 1);
        BinaryInt quocient = new BinaryInt(0, this.length());
        
        BinaryInt rest = this.getModule();
        BinaryInt div = bin.getModule();

        do {
            rest = rest.sub(div);

            quocient = quocient.sum(one);

        } while (rest.signed == false);

        rest = rest.sum(div);
        quocient = quocient.sub(one);

        //quocient.binaryNumber = BinaryInt.reduce(quocient.binaryNumber);
        //rest.binaryNumber = BinaryInt.reduce(rest.binaryNumber);

        if (isNegative) {
            quocient = BinaryInt.complementOfTwo(quocient);
            rest = BinaryInt.complementOfTwo(rest);
        }

        System.out.println("Quocient: " + quocient.toInt());
        System.out.println("Resto: " + rest.toInt());

        return new IntDivisionResult(quocient, rest);
    }

    /**
     *
     * @param length
     * @return Normalize the binary number for a length
     */
    public boolean[] normalize(int length) {

        int originalLength = this.binaryNumber.length;

        if (length == originalLength) {
            return this.binaryNumber;
        }

        boolean[] normalizedBin = new boolean[length];
        int i;
        for (i = 0; i < length - originalLength; i++) {
            normalizedBin[i] = false;
        }
        for (int j = i; j < length; j++) {
            normalizedBin[j] = this.binaryNumber[j - i];
        }

        return normalizedBin;
    }

    /**
     *
     * @param bin The binary number
     * @param length The length of normalization
     * @return The binary number normalized int the length
     */
    public static boolean[] normalize(boolean[] bin, int length) {

        int originalLength = bin.length;

        if (length == originalLength) {
            return bin;
        }

        boolean[] normalizedBin = new boolean[length];
        int i;
        for (i = 0; i < length - originalLength; i++) {
            normalizedBin[i] = false;
        }
        for (int j = i; j < length; j++) {
            normalizedBin[j] = bin[j - i];
        }

        return normalizedBin;
    }

    /**
     * @return The int value representation
     */
    public int toInt() {
        int toInt = 0;
        int length = this.binaryNumber.length - 1;
        BinaryInt aux;
        boolean[] result = this.binaryNumber;
        boolean negativo = false;

        if (this.signed) {
            aux = complementOfTwo(this);
            result = aux.binaryNumber;
            negativo = true;
        }

        for (int i = 0; i <= length; i++) {
            if (result[i]) {
                toInt += (int) Math.pow(2, (length - i));
            }
        }

        if (negativo) {
            return -toInt;
        }

        return toInt;
    }

    public static int toInt(boolean signal, boolean[] binary) {
        int value = 0;
        int length = binary.length - 1;

        boolean[] result = binary;
        if (signal) {
            result = BinaryInt.complementOfTwo(result);
        }

        for (int i = 0; i <= length; i++) {
            if (result[i]) {
                value += (int) Math.pow(2, (length - i));
            }
        }

        if (signal) {
            return -value;
        }

        return value;
    }

    /**
     * @param num A int number
     * @param length The length alocated for the number
     * @return Return the binary representation of a number
     * @throws BinaryArrayException
     */
    public static boolean[] toBinary(int num, int length) throws BinaryArrayException {
        boolean[] result = new boolean[length];
        int i = length - 1;

        do {
            //System.out.println(i);
            //System.out.println(x%2);

            if (i < 0) {
                throw new BinaryArrayException("O numero entrado em binário é maior que a quantidade de bits alocada.");
            }

            result[i] = (num % 2) == 1;

            num = num / 2;
            i--;
        } while (num != 0);

        return result;
    }

    /**
     *
     * @param binary The BinaryInt object
     * @return The One Complment
     */
    public static BinaryInt complementOfOne(BinaryInt binary) {
        boolean[] result = new boolean[binary.binaryNumber.length];
        BinaryInt aux;

        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = !binary.binaryNumber[i];
        }

        if (binary.signed) {
            aux = new BinaryInt(false, result);
        } else {
            aux = new BinaryInt(true, result);
        }

        return aux;
    }

    public static boolean[] complementOfOne(boolean[] binary) {
        boolean[] result = new boolean[binary.length];

        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = !binary[i];
        }
        return result;
    }

    public static BinaryInt complementOfTwo(BinaryInt binary) {
        BinaryInt one = new BinaryInt(1, binary.bitSize);
        BinaryInt aux = BinaryInt.complementOfOne(binary);
        try {
            BinaryInt result = aux.sum(one);
            return result;
        } catch (BinaryArrayException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public BinaryInt getModule() {
        if (this.signed) {
            return BinaryInt.complementOfTwo(this);
        } else {
            return this;
        }
    }

    public static boolean[] complementOfTwo(boolean[] binary) {

        boolean[] one = new boolean[]{true};
        return BinaryInt.sumIgnoringOverflow(BinaryInt.complementOfOne(binary), one);
    }

    public static boolean[] reduce(boolean[] binary) {

        int initialFalseNum = 0;
        for (int i = 0; i < binary.length; i++) {
            if (binary[i] == false) {
                initialFalseNum++;
            } else {
                break;
            }
        }

        boolean[] result = new boolean[binary.length - initialFalseNum];
        for (int i = 0; i < result.length; i++) {
            result[i] = binary[initialFalseNum + i];
        }

        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.fullBynaryNumber());
    }
}
