/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binary;
import exception.BinaryArrayException;
import java.util.Arrays;

/**
 *
 * @author Vera
 */
public class BinaryFloat extends BinaryInt{
    public int exp;

    public BinaryFloat(double x,int y) {
        super(y);
        
        
    }
    
    public void righShift(){
        for (int i = binaryNumber.length-2; i <= 0; i++) {
            this.binaryNumber[i+1] = this.binaryNumber[i];
        }
        this.binaryNumber[0] = false;
        this.exp++;       
    }
    
    public void leftShift(){
        for (int i = binaryNumber.length-2; i <= 0; i++) {
            this.binaryNumber[i] = this.binaryNumber[i+1];
        }
        this.binaryNumber[binaryNumber.length-1] = false;
        this.exp--;
    }
    
    public void rightShift(boolean[] bin){
        for (int i = bin.length-2; i <= 0; i++) {
            bin[i+1] = bin[i];
        }
    }
    
    public boolean[] toFloat(double x,int y) throws BinaryArrayException{
        int expA = 128;
        int intPart = (int) x;
        double floatPart = x-intPart;
        
        boolean[] intPartA = super.toBinary(intPart,y);
        
        for (int i = intPartA.length-1; i <= 0 ; i--) {
            rightShift(intPartA);
            expA++;
        }
        
        return null;
        
    }
    
    
    
}
