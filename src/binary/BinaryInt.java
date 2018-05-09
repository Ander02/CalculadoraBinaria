package binary;

import exception.BinaryArrayException;
import java.util.Arrays;

/**
 *
 * @author Anderson Pessoa 
 * @author Marcus Vinicius
 */
public class BinaryInt {

    //Attributes
    public boolean signed;
    public boolean[] binaryNumber;
    public int bitSize;
    public int original;

    //Constructors
    public BinaryInt() {
    }

    public BinaryInt(BinaryInt bin) {
        this.signed = bin.signed;
        this.bitSize = bin.bitSize;
        this.original = bin.original;
        this.binaryNumber = bin.binaryNumber;
    }

    public BinaryInt(int num, int bitSize) throws BinaryArrayException {
        this.original = num;
        this.bitSize = bitSize;
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
    }

    public BinaryInt(int bitSize) {
        this.bitSize = bitSize;
        this.binaryNumber = new boolean[bitSize];
        this.original = 0;
        this.signed = false;
    }

    public BinaryInt(boolean signed, boolean[] binaryNumber) {
        this.signed = signed;
        this.binaryNumber = binaryNumber;
        this.bitSize = binaryNumber.length;
        this.original = BinaryInt.toInt(this.signed, this.binaryNumber);
    }

    /**
     *
     * @return The total length of binary number
     */
    public int length() {
        return this.binaryNumber.length + 1;
    }

    /**
     *
     * @return The full binary number representation
     */
    public boolean[] fullBynaryNumber() {
        boolean[] fullBinaryNumber = new boolean[this.length()];

        fullBinaryNumber[0] = this.signed;
        for (int i = 1; i < fullBinaryNumber.length; i++) {
            fullBinaryNumber[i] = binaryNumber[i - 1];
        }

        return fullBinaryNumber;
    }

    /**
     *
     * @param bin The binary number that will be added
     * @return A new binary number containing the sum of this with the binary
     * numeral passed as an argument
     * @throws BinaryArrayException
     */
    public BinaryInt sum(BinaryInt bin) throws BinaryArrayException {

        boolean[] normalized;
        boolean[] other;
        boolean isPositiveSum = false;

        //Se ambos são positivos
        if (this.signed == false && bin.signed == false) {
            isPositiveSum = true;
        }

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

        //Realize the sum
        boolean[] resp = new boolean[normalized.length];
        boolean carry = false;
        for (int i = normalized.length - 1; i >= 0; i--) {
            SumBitAux aux = new SumBitAux(normalized[i], other[i], carry);

            carry = aux.getCarry();
            resp[i] = aux.getResult();
        }

        //if overflow
        if (resp[0] == true && isPositiveSum == true) {
            throw new BinaryArrayException("Overflow Detectado na soma.");
        }

        //Adjust
        boolean[] binAux = new boolean[resp.length - 1];
        for (int i = 0; i < resp.length - 1; i++) {
            binAux[i] = resp[i + 1];
        }

        //return
        return new BinaryInt(resp[0], binAux);
    }

    /**
     *
     * @param bin1 The first boolean array binary number
     * @param bin2 The second boolean array binary number
     * @return The sum ignoring overflow exception between bin1 and bin2
     */
    public static boolean[] sumIgnoringOverflow(boolean[] bin1, boolean[] bin2) {

        boolean[] normalized;
        boolean[] other;

        //normalize
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

        //Realize sum
        boolean[] resp = new boolean[normalized.length];
        boolean carry = false;
        for (int i = normalized.length - 1; i >= 0; i--) {
            SumBitAux aux = new SumBitAux(normalized[i], other[i], carry);

            carry = aux.getCarry();
            resp[i] = aux.getResult();
        }

        return resp;
    }

    /**
     *
     * @param bin The binary number that will be subtracted
     * @return A new binary number containing the subtraction of this with the
     * binary numeral passed as an argument
     * @throws BinaryArrayException
     */
    public BinaryInt sub(BinaryInt bin) throws BinaryArrayException {

        BinaryInt bin1 = new BinaryInt(this);
        BinaryInt bin2 = new BinaryInt(bin);

        //Validate
        if (this.binaryNumber == null || bin.binaryNumber == null) {
            throw new BinaryArrayException("O binário esperado não foi criado por algum erro.");
        }

        //Normalize if necessary
        if (bin1.bitSize > bin2.bitSize) {
            bin2 = new BinaryInt(bin2.original, bin1.bitSize);
        } else {
            bin1 = new BinaryInt(bin1.original, bin2.bitSize);
        }

        //Subtraction and return value
        return bin1.sum(BinaryInt.complementOfTwo(bin2));
    }

    /**
     *
     * @param bin The binary number that will be multiplicated
     * @return A new binary number containing the multiplication of this with
     * the binary numeral passed as an argument
     */
    public BinaryInt mult(BinaryInt bin) throws BinaryArrayException {
        
        if(this.original==0||bin.original==0){
            return new BinaryInt(0,this.bitSize);
        }
        
        //Verifiy if result is negative
        boolean isNegative = this.signed != bin.signed;

        int multiplicandoBitSize = this.length();
        boolean[] multiplicandoBin = this.getModule().fullBynaryNumber();
        boolean[] negativeMultiplicandoBin = new boolean[multiplicandoBin.length];
        int multiplicatorBitSize = bin.length();
        boolean[] multiplicatorBin = bin.getModule().fullBynaryNumber();

        //Booth vectors
        boolean[] addVector = new boolean[multiplicandoBitSize + multiplicatorBitSize + 1];
        boolean[] subVector = new boolean[multiplicandoBitSize + multiplicatorBitSize + 1];
        boolean[] productVector = new boolean[multiplicandoBitSize + multiplicatorBitSize + 1];

        negativeMultiplicandoBin = BinaryInt.complementOfTwo(multiplicandoBin);
        negativeMultiplicandoBin[0] = true;

        //Initialize lines
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

            //last and penultimate index of product vector 
            boolean lastIndex = productVector[productVector.length - 1];
            boolean penultimateIndex = productVector[productVector.length - 2];

            if (penultimateIndex == false && lastIndex == true) {
                //Make sum with add vector
                productVector = BinaryInt.sumIgnoringOverflow(productVector, addVector);

            } else if (penultimateIndex == true && lastIndex == false) {
                //Make sum with sub vector
                productVector = BinaryInt.sumIgnoringOverflow(productVector, subVector);
            }

            //Special Rigth Shift maintaining the signal bit
            productVector = BinaryInt.specialRigthShift(productVector);
        }

        //Last rigth shift
        productVector = BinaryInt.specialRigthShift(productVector);

        //Reduce vector
        productVector = BinaryInt.reduce(productVector);

        //Create binary int object
        BinaryInt resp = new BinaryInt(false, productVector);

        //if is negative, make complement of two
        if (isNegative) {
            return BinaryInt.complementOfTwo(resp);
        }

        return resp;
    }

    /**
     *
     * @param bin A binary number
     * @return The binary number shifted to rigth with more significative bit
     */
    private static boolean[] specialRigthShift(boolean[] bin) {
        boolean[] shiftedProductVector = new boolean[bin.length];

        shiftedProductVector[0] = bin[0];
        shiftedProductVector[1] = bin[0];

        for (int j = 1; j < bin.length - 1; j++) {
            shiftedProductVector[j + 1] = bin[j];
        }

        return shiftedProductVector;
    }

    /**
     *
     * @param bin The binary number that will be multiplicated
     * @return A new binary number containing the multiplication of this with
     * the binary numeral passed as an argument
     * @throws BinaryArrayException
     */
    public IntDivisionResult div(BinaryInt bin) throws BinaryArrayException {

        //Verify if is negative
        boolean isNegative = this.signed != bin.signed;

        //One binary number
        BinaryInt one = new BinaryInt(1, 1);

        //Quocient of division
        BinaryInt quocient = new BinaryInt(0, this.length());

        //Divident(rest) and divisor(div) of division
        BinaryInt rest = this.getModule();
        BinaryInt div = bin.getModule();

        //makes division through multiple subtractions
        do {
            rest = rest.sub(div);

            quocient = quocient.sum(one);

        } while (rest.signed == false);

        //Back to the last state 
        rest = rest.sum(div);
        quocient = quocient.sub(one);

        //Verify if is negative
        if (isNegative) {
            quocient = BinaryInt.complementOfTwo(quocient);
            rest = BinaryInt.complementOfTwo(rest);
        }

        //return
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

        //Normalize
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

    /**
     *
     * @param signal The binary number signal bit
     * @param binary The binary number booleand array representation
     * @return The int value representation
     */
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
     * @return The One Complement
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

    /**
     *
     * @param binary The boolean array binary representation
     * @return The one complement of binary number
     */
    public static boolean[] complementOfOne(boolean[] binary) {
        boolean[] result = new boolean[binary.length];

        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = !binary[i];
        }
        return result;
    }

    /**
     *
     * @param binary The binary int object
     * @return The complement of two of binary int object
     */
    public static BinaryInt complementOfTwo(BinaryInt binary) {
        try {
            BinaryInt one = new BinaryInt(1, binary.bitSize);
            BinaryInt aux = BinaryInt.complementOfOne(binary);
            BinaryInt result = aux.sum(one);

            return result;
        } catch (BinaryArrayException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @param binary The binary boolean array representation
     * @return The complement of two of binary int object
     */
    public static boolean[] complementOfTwo(boolean[] binary) {

        boolean[] one = new boolean[]{true};
        return BinaryInt.sumIgnoringOverflow(BinaryInt.complementOfOne(binary), one);
    }

    /**
     *
     * @return The Binary int module
     */
    public BinaryInt getModule() {
        if (this.signed) {
            return BinaryInt.complementOfTwo(this);
        } else {
            return this;
        }
    }

    /**
     *
     * @param binary A positive binary array representation
     * @return The reduced binary boolean array
     */
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
        return Arrays.toString(this.fullBynaryNumber()) + ": " + this.toInt();
    }
}
