/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binary;

/**
 *
 * @author Vera
 */
public class BinaryFloat extends BinaryInt{
    public int exp;

    public BinaryFloat(double x,int y) {
        super();
        
    }
    
    public void righShift(){
        for (int i = binaryNumber.length-2; i <= 0; i++) {
            this.binaryNumber[i+1] = this.binaryNumber[i];
        }
        this.exp++;       
    } 
    
    
    
}
