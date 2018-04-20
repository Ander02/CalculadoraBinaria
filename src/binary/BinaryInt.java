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
    
    public BinaryInt(){
        
    }

    public BinaryInt(int x, int y) {
        this.bitSize = y;
        if (x>0){
            this.signed = false;           
            try {
                this.binaryNumber = this.toBinary(Math.abs(x),y);
            } 
            catch (BinaryArrayException ex) {
                System.out.println(ex.message);
            }
        }                             
    }
    
    public BinaryInt(int x){
        this.binaryNumber = new boolean[x];
    }
    
    public BinaryInt (boolean[] binaryNumber){
        this.signed = false;
        this.binaryNumber = binaryNumber;
    }

    public BinaryInt(boolean signed, boolean[] binaryNumber,int bitSize) {
        this.bitSize = bitSize;
        this.signed = signed;
        this.binaryNumber = binaryNumber;
    }

    public int length() {
        return binaryNumber.length + 1;
    }

    public boolean[] fullBynaryNumber() {
        boolean[] fullBinaryNumber = new boolean[this.length()];
        boolean[] aux = new boolean[fullBinaryNumber.length-1];

        fullBinaryNumber[0] = this.signed;
        for (int i = 1; i < fullBinaryNumber.length; i++) {
            fullBinaryNumber[i] = binaryNumber[i - 1];
        }

        return fullBinaryNumber;
    }

    public BinaryInt sum(BinaryInt bin) throws BinaryArrayException{

        boolean[] normalized;
        boolean[] other;
        boolean overflowTest = false;
        
        if (this.signed==false&&bin.signed==false)
            overflowTest = true;

        if (this.binaryNumber.length > bin.binaryNumber.length) {
            normalized = bin.normalize(this.binaryNumber.length+1);
            other = this.fullBynaryNumber();
            normalized[0] = bin.signed; other[0] = this.signed;
        } else {
            normalized = this.normalize(bin.binaryNumber.length+1);
            other = bin.fullBynaryNumber();
            normalized[0] = this.signed; other[0] = bin.signed;
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

        
        boolean[] binAux = new boolean[resp.length-1];
        for (int i = 0; i < resp.length-1; i++) {
            binAux[i] = resp[i+1];
        }
        
        bin.signed = resp[0];
        bin.binaryNumber = binAux;
        
        if (resp[0]==true&&overflowTest==true)
            throw new BinaryArrayException("Overflow Detectado na soma.");
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
    
    public BinaryInt sub(BinaryInt bin) throws BinaryArrayException{
        boolean[] normalized;
        boolean[] other;
        
        BinaryInt aux = new BinaryInt (this.signed,this.binaryNumber,this.bitSize);
        
        //System.out.println("Aux da subtração: "+Arrays.toString(aux.fullBynaryNumber()));
        
        if(this.binaryNumber==null||bin.binaryNumber==null)
            throw new BinaryArrayException("O binário esperado não foi criado por algum erro.");

        if (this.binaryNumber.length > bin.binaryNumber.length) {
            normalized = bin.normalize(this.binaryNumber.length+1);
            other = this.fullBynaryNumber();
            normalized[0] = bin.signed; other[0] = this.signed;
        } else {
            normalized = this.normalize(bin.binaryNumber.length+1);
            other = bin.fullBynaryNumber();
            normalized[0] = this.signed; other[0] = bin.signed;
        }
        
        //if (Arrays.toString(normalized).equals(Arrays.toString(other)))
            //return new BinaryInt(0);
        
        bin = complementoDeDois(bin);
        bin = bin.sum(aux);
        
        return bin;        
    }

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

    public static BinaryInt sum(BinaryInt bin1, BinaryInt bin2) {
        return null;
    }

    /**
     * @return The int value representation
     */
    public int toInt() {
        int toInt = 0;
        int length = this.binaryNumber.length-1;
        
        for (int i = 0; i <= length ; i++) {
            if(this.binaryNumber[i]){
                toInt += (int) Math.pow(2,(length-i));
            }
        }
        return toInt;
    }

    public boolean[] toBinary(int x,int y) throws BinaryArrayException{
        boolean[] result = new boolean[y];
        int i = y-1;
        
        do {
            //System.out.println(i);
            //System.out.println(x%2);
            
            if (i<0)
                throw new BinaryArrayException("O numero entrado em binário é maior que a quantidade de bits alocada.");
            
            result[i] = x%2==1;
                        
            x = x/2;           
            i--;
        }while(x!=0);
        
        return result;
    }
    
    public BinaryInt complementoDeUm(BinaryInt binary) {
        boolean[] result = new boolean[binary.binaryNumber.length];
        BinaryInt aux;

        for (int i = result.length-1; i >= 0; i--) {
            result[i] = !binary.binaryNumber[i];   
            //System.out.println(result[i]);
        }
        
        //System.out.println("Array entrado no complemento de 1: "+Arrays.toString(binary.binaryNumber));
        //System.out.println("Resultado do complemento de 1: "+Arrays.toString(result));
        
        
        if (binary.signed)
            aux = new BinaryInt(false,result,this.bitSize);
        
        else
            aux = new BinaryInt(true,result,this.bitSize);
        
        
        
        
        return aux;
    }

    public BinaryInt complementoDeDois(BinaryInt binary) {
        BinaryInt one = new BinaryInt(1,binary.bitSize);
        BinaryInt aux = complementoDeUm(binary);
        try {
            BinaryInt result = aux.sum(one);
            return result;
        }
        catch(BinaryArrayException ex){
            System.out.println(ex.message);            
            }
        
        return null;
            

        
        
    }
    
    @Override
    public String toString() {
        return Arrays.toString(this.fullBynaryNumber());
    }

}
