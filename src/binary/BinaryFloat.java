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
public class BinaryFloat extends BinaryInt {

    public int exp;
    public double original;

    public BinaryFloat(double x,int y) throws BinaryArrayException {        
        super(y);
        this.original = x;
        this.bitSize = y;
        this.binaryNumber = toFloat(x,y);
        if (x>0){
            this.signed = false;
        }
        
    }
    
    public BinaryFloat(boolean sign,boolean[] mant,int bitSize,int exp){
        this.exp = exp;
        this.signed = sign;
        this.binaryNumber = mant;
        this.bitSize = bitSize;
    }

    public void rightShift() {
        for (int i = binaryNumber.length - 2; i >= 0; i--) {
            this.binaryNumber[i + 1] = this.binaryNumber[i];
        }
        this.binaryNumber[0] = false;
        this.exp++;
    }

    public void leftShift() {
        for (int i = 0; i <= binaryNumber.length-2; i++) {
            this.binaryNumber[i] = this.binaryNumber[i + 1];
        }
        this.binaryNumber[binaryNumber.length - 1] = false;
        this.exp--;
    }
    
    public static void leftShift(boolean[] bin){
        //System.out.println("teste leftShift statico antes: "+Arrays.toString(bin));
        for (int i = 0; i <= bin.length-2; i++) {
            bin[i] = bin[i+1];
        }
        bin[bin.length-1] = false;
        //System.out.println("teste leftShift statico depois: "+Arrays.toString(bin));
    }

    public static void rightShift(boolean[] bin) {
        for (int i = bin.length - 2; i >= 0; i--) {
            bin[i + 1] = bin[i];
        }
        bin[0] = false;
    }
    
    public boolean[] fullNumber() throws BinaryArrayException{
        boolean[] ret = new boolean[this.bitSize+10];
        boolean[] aux = toBinary(this.exp,8);
        ret[0] = this.signed;
        for (int i = 1; i <= 8; i++) {
            ret[i] = aux[i-1];
        }
        for (int i = 9; i < this.bitSize+8; i++) {
            ret[i] = this.binaryNumber[i-9];
        }
        
        return ret;
    }
    
    public boolean[] numberSignal(){
        boolean[] ret = new boolean[this.bitSize+1];
        ret[0] = this.signed;
        for (int i = 1; i < this.bitSize-1; i++) {
            ret[i] = this.binaryNumber[i-1];
        }
        
        return ret;
    }

    public double floatPart(){
        return this.original-(int) this.original;
    }
    
    public boolean[] toFloat(double x, int y) throws BinaryArrayException {
        int contAux = 0;
        int expA = 128;
        int intPart = (int) x;
        double floatPart = x - intPart;
        boolean[] floatPartA = new boolean[y];
        
        boolean[] intPartA = super.toBinary(intPart,y); //Parte inteira do binário
        boolean[] intPartB = new boolean[y];
        int count = 0;
        
        if(intPart>0){
            while (!intPartA[count]) { //Contador para ver quantos right shifts serão necessários aplicar para retornar o expoente.
                count++;
            }
        }
        
        //System.out.println(count);
      
        //System.out.println("Antes: "+Arrays.toString(intPartA)+"   "+Arrays.toString(intPartB));
        
        if(intPart>0)
            count = y-count-1;
        
        
        
        for (int i = count; i > 0; i--) {
            rightShift(intPartB);
            intPartB[0] = intPartA[y-1];
            rightShift(intPartA);
            expA++;
            contAux++;
        }
        
        //System.out.println("Depois: "+Arrays.toString(intPartA)+"   "+Arrays.toString(intPartB));
        
        count = 0;
        while(floatPart!=0&&count<=floatPartA.length-1){ //Parte quebrada do binário
            //System.out.println(floatPart);
            floatPart *= 2;
                if(floatPart>=1){
                    floatPartA[count] = true;
                    floatPart -= 1;
                }
                else{
                    floatPartA[count] = false;
                }
                count++;
        }
        
        
        
        //System.out.println("Float Part Antes :"+Arrays.toString(floatPartA));
        
        if(!intPartA[y-1]){
            //System.out.println("test");
            count = 0;
            while(!intPartA[y-1]){
                intPartA[y-1] = floatPartA[0];
                leftShift(floatPartA);
                expA--;
            }
        }
        
        //System.out.println(expA);
        
        for (int i = 0; i < expA - 128; i++) { //aplicando right shift na parte float para a soma
            //System.out.println(expA);
            rightShift(floatPartA);
        }
        
        //System.out.println("Float Part Depois :"+Arrays.toString(floatPartA));
        

        BinaryInt sum1 = new BinaryInt(false, intPartB, y);
        BinaryInt sum2 = new BinaryInt(false, floatPartA, y);

        sum1 = sum1.sum(sum2); //Soma das 2 partes no mesmo nível de expoente.
        
        this.exp = expA;        
        
        return sum1.binaryNumber;        
    }
    
    public double floatToDouble(){
        int exp = this.exp;
        double result;
        double inteiro;
        double flutuante = 0;
        boolean[] intPart = new boolean[this.bitSize];
        intPart[this.bitSize-1] = true;
        
        //System.out.println("Array Part flutuante Antes: "+Arrays.toString(this.binaryNumber));
        
        for (int i = 0; i < exp-128; i++) {   
            //System.out.println(this.exp-128);
            leftShift(intPart);
            intPart[this.bitSize-1] = this.binaryNumber[0];
            this.leftShift();              
        }
        
        if(exp-128<0){
            for (int i = 0; i < 128-exp; i++) {
                this.rightShift();
                this.binaryNumber[0] = intPart[this.bitSize-1];
                rightShift(intPart);
            }
        }
        
        //System.out.println("Array Parte inteiro: "+Arrays.toString(intPart));
        //System.out.println("Array Parte flutuante Depois: "+Arrays.toString(this.binaryNumber));
        
        BinaryInt aux = new BinaryInt(this.signed,intPart,this.bitSize);
        
        inteiro = (double) aux.toInt();
        
        for (int i = 0; i < this.bitSize-1; i++) {
            if(this.binaryNumber[i]){
                flutuante += Math.pow(0.5, (i+1));
            }
        }
        
        result = inteiro+flutuante;
        
        //System.out.println("Inteiro: "+inteiro);
        //System.out.println("Flutuante: "+flutuante);
        
        return result;
        
    }
    
    public BinaryFloat floatSum(BinaryFloat bin) throws BinaryArrayException{
        System.out.println("Bin: "+Arrays.toString(bin.binaryNumber));
        System.out.println("This: "+Arrays.toString(this.binaryNumber));
        boolean igual = (this.exp==bin.exp);
        if(this.exp>bin.exp){
            bin.rightShift();
            bin.binaryNumber[0] = true;
            while(bin.exp!=this.exp){
                bin.rightShift();
            }
        }
        if(bin.exp>this.exp){
            this.rightShift();
            this.binaryNumber[0] = true;
            while(this.exp!=bin.exp){
                this.rightShift();
            }
        }

        boolean overflowCheck = false;
               
        
        boolean[] mant1 = this.binaryNumber; //precisa ajeitar para o número completo (com o sinal)
        boolean[] mant2 = bin.binaryNumber; 
        
        if(mant1[0]||mant2[0]){
            overflowCheck = true;
        }
        
        boolean[] res = sumIgnoringOverflow(mant1, mant2);
        
        if((overflowCheck&&!res[0])||igual){
            this.exp++;
            rightShift(res);
            return new BinaryFloat(false,res,res.length,this.exp); //return caso aconteça overflow e precise adicionar 1 ao expoente e um rightShift.
        }
        
        
        BinaryFloat ret = new BinaryFloat(false,res,res.length,this.exp); //apenas para teste, retornar com o sinal adequado. Atualemente retorna o valor certo em qualquer instância positiva.
       
        
        return  ret;
    }
    
    public BinaryFloat floatSub(BinaryFloat bin) throws BinaryArrayException{
        //System.out.println("Bin: "+Arrays.toString(bin.binaryNumber));
        //System.out.println("This: "+Arrays.toString(this.binaryNumber));
        
        boolean extraShift = (bin.floatPart()>this.floatPart());
        boolean overflowCheck = false;
        boolean[] implicitA = new boolean[this.bitSize];
        boolean[] implicitB = new boolean[this.bitSize];
  
        int count = 0;
        int count2 = 0;
        boolean igual = (this.exp==bin.exp);
        boolean negativo = (bin.exp>this.exp);
        if(this.exp>bin.exp){
            bin.rightShift();
            bin.binaryNumber[0] = true;
            implicitA[this.bitSize-1]=true;
            implicitB[this.bitSize-1]=false;
            while(bin.exp!=this.exp){
                bin.rightShift();
            }
        }
        if(bin.exp>this.exp){
            this.rightShift();
            this.binaryNumber[0] = true;
            implicitA[this.bitSize-1]=false;
            implicitB[this.bitSize-1]=true;
            while(this.exp!=bin.exp){
                this.rightShift();
            }
        }
        
        int exp = this.exp;
        boolean[] mant1 = this.binaryNumber;
        boolean[] mant2 = complementOfOne(bin.binaryNumber);
        boolean[] one = new boolean[this.bitSize]; one[this.bitSize-1] = true;
        mant2 = sumIgnoringOverflow(mant2,one);
        boolean[] res = sumIgnoringOverflow(mant1,mant2);
        
        
        implicitB = complementOfOne(implicitB);
        implicitB = sumIgnoringOverflow(implicitB,one);
        boolean[] implicitR = sumIgnoringOverflow(implicitA,implicitB);
        
         if(mant1[0]||mant2[0]){
            overflowCheck = true;
        }
        
        
        //System.out.println("float 1: "+Arrays.toString(implicitA)+Arrays.toString(mant1)+" 2^"+(this.exp-128));
        //System.out.println("float 2: "+Arrays.toString(implicitB)+Arrays.toString(mant2)+" 2^"+(bin.exp-128));
        //System.out.println("result :"+Arrays.toString(implicitR)+Arrays.toString(res));
        
        
        
        while(!mant1[count]&&count==this.bitSize-1){
            count++;
        }
        
        while(!bin.binaryNumber[count2]&&count2==bin.bitSize-1){
            count2++;
        }
        
        if(igual){
        if(count>count2)
            negativo=true;}
        
        
        
        
        if((overflowCheck&&res[0])||!overflowCheck){
        boolean aux = implicitB[this.bitSize-1];
        while(!aux){
            aux = res[0]; 
            exp--;
            leftShift(res);
        }
        }
        
        
        /*if(overflowCheck&&res[0]){
            System.out.println("test");
            boolean aux2 = false;
            while(!aux){
                aux = res[0];
                exp--;
                leftShift(res);
            }
        }*/
        
        
        /*if(!negativo){ //normalizar, incompleto. Para qualquer valor que não precise de normalização o valor é correto. Não achei um jeito de fazer detectar quando precisa de left shifts
            for (int i = 0; i < norm; i++) {
                System.out.println("test");
                this.exp--;
                leftShift(res);
            }
        }*/
        
        
        if(negativo){
            return new BinaryFloat(true,res,res.length,exp);
        }
        
        
        
        return new BinaryFloat(false,res,res.length,exp);
    }
    
    public BinaryFloat floatMul(BinaryFloat bin){  
            boolean negativo = (this.signed!=bin.signed);
            boolean[] mant1 = this.binaryNumber;
            boolean[] mant2 = bin.binaryNumber;
            int exp = (this.exp+bin.exp)-128;
            
            //System.out.println(this.exp);
            //System.out.println(bin.exp);
            
            
            boolean overflowCheck = (mant1[0]||mant2[0]);
            
            rightShift(mant1);
            rightShift(mant2);
            
            mant1[0] = true;
            mant2[0] = true;
            
            BinaryInt mu1 = new BinaryInt(false,mant1,this.bitSize);
            BinaryInt mu2 = new BinaryInt(false,mant2,bin.bitSize);
            boolean[] res1 = mu2.mult(mu1).binaryNumber;
            boolean[] res2 = new boolean[this.bitSize];
            
            
            
            System.out.println(Arrays.toString(mu1.binaryNumber));
            System.out.println(Arrays.toString(mu2.binaryNumber));
            //System.out.println(Arrays.toString(res1));          
            
            int i = 0;
            while(!res1[i]){
                i++;
            }
            
            for (int j = 0; j < this.bitSize; j++) {
                res2[j] = res1[i+j];
        }
            
            System.out.println(Arrays.toString(res2));
            
            leftShift(res2);
            
            if (overflowCheck&&!res2[0]){
                //rightShift(res2);
                exp++;
            }
            

            if(negativo){
                return new BinaryFloat(true,res2,this.bitSize,exp);
            }
            
            return new BinaryFloat(false,res2,this.bitSize,exp);
    }
    
    
    
}
