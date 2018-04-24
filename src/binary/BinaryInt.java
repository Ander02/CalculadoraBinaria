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
    

    public BinaryInt() {

    }

    public BinaryInt(int num, int bitSize) {
        this.bitSize = bitSize;
        if (num > 0) {
            this.signed = false;
            try {
                this.binaryNumber = this.toBinary(Math.abs(num), bitSize);
            } catch (BinaryArrayException ex) {
                System.out.println(ex.message);
            }
        }
    }

    public BinaryInt(int bitSize) {
        this.bitSize = bitSize;
        this.binaryNumber = new boolean[bitSize];
    }

    public BinaryInt(boolean[] binaryNumber) {
        this.signed = false;
        this.binaryNumber = binaryNumber;
    }

    public BinaryInt(boolean signed, boolean[] binaryNumber, int bitSize) {
        this.bitSize = bitSize;
        this.signed = signed;
        this.binaryNumber = binaryNumber;
    }

    public int length() {
        return binaryNumber.length + 1;
    }

    public boolean[] fullBynaryNumber() {
        boolean[] fullBinaryNumber = new boolean[this.length()];
        boolean[] aux = new boolean[fullBinaryNumber.length - 1];

        fullBinaryNumber[0] = this.signed;
        for (int i = 1; i < fullBinaryNumber.length; i++) {
            fullBinaryNumber[i] = binaryNumber[i - 1];
        }

        return fullBinaryNumber;
    }

    public BinaryInt sum(BinaryInt bin) throws BinaryArrayException {

        boolean[] normalized;
        boolean[] other;
        boolean overflowTest = false;

        if (this.signed == false && bin.signed == false) {
            overflowTest = true;
        }

        if (this.binaryNumber.length > bin.binaryNumber.length) {
            normalized = bin.normalize(this.binaryNumber.length + 1);
            other = this.fullBynaryNumber();
            normalized[0] = bin.signed;
            other[0] = this.signed;
        } else {
            normalized = this.normalize(bin.binaryNumber.length + 1);
            other = bin.fullBynaryNumber();
            normalized[0] = this.signed;
            other[0] = bin.signed;
        }

        System.out.println("O mesmo:    " + Arrays.toString(other));
        System.out.println("Normalizado " + Arrays.toString(normalized));

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

        if (resp[0] == true && overflowTest == true) {
            throw new BinaryArrayException("Overflow Detectado na soma.");
        }

        bin.signed = resp[0];
        bin.binaryNumber = binAux;

        /*
        if (carry == true) {
          resp[0] = true;
            boolean[] resp2 = new boolean[resp.length + 1];
            resp2[0] = carry;

            for (int i = 1; i < resp.length; i++) {
                resp2[i] = resp[i - 1];
            }

            resp = resp2;
        }*/
        System.out.println("resposta  : " + Arrays.toString(resp));
        System.out.println("");
        return bin;
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

        System.out.println("O mesmo:    " + Arrays.toString(other));
        System.out.println("Normalizado " + Arrays.toString(normalized));

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
        boolean[] normalized;
        boolean[] other;

        BinaryInt aux = new BinaryInt(this.signed, this.binaryNumber, this.bitSize);

        //System.out.println("Aux da subtração: "+Arrays.toString(aux.fullBynaryNumber()));
        if (this.binaryNumber == null || bin.binaryNumber == null) {
            throw new BinaryArrayException("O binário esperado não foi criado por algum erro.");
        }

        if (this.binaryNumber.length > bin.binaryNumber.length) {
            normalized = bin.normalize(this.binaryNumber.length + 1);
            other = this.fullBynaryNumber();
            normalized[0] = bin.signed;
            other[0] = this.signed;
        } else {
            normalized = this.normalize(bin.binaryNumber.length + 1);
            other = bin.fullBynaryNumber();
            normalized[0] = this.signed;
            other[0] = bin.signed;
        }

        //if (Arrays.toString(normalized).equals(Arrays.toString(other)))
        //return new BinaryInt(0);
        bin = complementOfTwo(bin);
        bin = bin.sum(aux);

        return bin;
    }

    public BinaryInt mult(BinaryInt bin) {

        int multiplicandoBitSize = this.length();
        boolean[] multiplicandoBin = this.fullBynaryNumber();
        boolean[] negativeMultiplicandoBin = new boolean[multiplicandoBin.length];
        int multiplicatorBitSize = bin.length();
        boolean[] multiplicatorBin = bin.fullBynaryNumber();

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
        return new BinaryInt(productVector);
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
        
        if(this.signed){
            aux = complementOfTwo(this);
            result = aux.binaryNumber;
            negativo = true;
        }

        for (int i = 0; i <= length; i++) {
            if (result[i]) {
                toInt += (int) Math.pow(2, (length - i));
            }
        }
        
        if(negativo)
            return -toInt;
        
        return toInt;
    }

    /**
     * @param num A int number
     * @param length The length alocated for the number
     * @return Return the binary representation of a number
     * @throws BinaryArrayException
     */
    public boolean[] toBinary(int num, int length) throws BinaryArrayException {
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
    public BinaryInt complementOfOne(BinaryInt binary) {
        boolean[] result = new boolean[binary.binaryNumber.length];
        BinaryInt aux;

        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = !binary.binaryNumber[i];
            //System.out.println(result[i]);
        }

        //System.out.println("Array entrado no complemento de 1: "+Arrays.toString(binary.binaryNumber));
        //System.out.println("Resultado do complemento de 1: "+Arrays.toString(result));
        if (binary.signed) {
            aux = new BinaryInt(false, result, this.bitSize);
        } else {
            aux = new BinaryInt(true, result, this.bitSize);
        }

        return aux;
    }

    public BinaryInt complementOfTwo(BinaryInt binary) {
        BinaryInt one = new BinaryInt(1, binary.bitSize);
        BinaryInt aux = complementOfOne(binary);
        try {
            BinaryInt result = aux.sum(one);
            return result;
        } catch (BinaryArrayException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.fullBynaryNumber());
    }

}
