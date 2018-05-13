package binary;

import exception.BinaryArrayException;
import java.util.Arrays;

/**
 *
 * @author Anderson Pessoa
 * @author Marcus Vinicius
 */
public class BinaryFloat extends BinaryInt {

    public int exp;
    public double original;
    public boolean[] expo;

    public BinaryFloat(double num, int bits) throws BinaryArrayException {
        //super(y);
        this.original = num;
        this.bitSize = bits;
        this.binaryNumber = toFloat(Math.abs(num), bits);
        if (num >= 0) {
            this.signed = false;
        }
        if(num < 0){
            this.signed = true;
        }

    }

    public BinaryFloat(boolean sign, boolean[] mant, int bitSize, boolean[] exp) {
        this.expo = exp;
        this.signed = sign;
        this.binaryNumber = mant;
        this.bitSize = bitSize;
    }

    public void rightShift() throws BinaryArrayException {
        BinaryInt one = new BinaryInt(1,8);
        BinaryInt aux = new BinaryInt(false,this.expo);
        for (int i = binaryNumber.length - 2; i >= 0; i--) {
            this.binaryNumber[i + 1] = this.binaryNumber[i];
        }
        this.binaryNumber[0] = false;
        this.expo = aux.sum(one).binaryNumber;
    }

    public void leftShift() throws BinaryArrayException {
        BinaryInt one = new BinaryInt(1,8);
        BinaryInt aux = new BinaryInt(false,this.expo);
        for (int i = 0; i <= binaryNumber.length - 2; i++) {
            this.binaryNumber[i] = this.binaryNumber[i + 1];
        }
        this.binaryNumber[binaryNumber.length - 1] = false;
        
        if(aux.sub(one).signed){
            throw new BinaryArrayException("Underflow no expoente");
        }
        
        this.expo = aux.sub(one).binaryNumber;
    }

    public static void leftShift(boolean[] bin) {
        //System.out.println("teste leftShift statico antes: "+Arrays.toString(bin));
        for (int i = 0; i <= bin.length - 2; i++) {
            bin[i] = bin[i + 1];
        }
        bin[bin.length - 1] = false;
        //System.out.println("teste leftShift statico depois: "+Arrays.toString(bin));
    }

    public static void rightShift(boolean[] bin) {
        for (int i = bin.length - 2; i >= 0; i--) {
            bin[i + 1] = bin[i];
        }
        bin[0] = false;
    }

    public boolean[] fullNumber() throws BinaryArrayException {
        boolean[] ret = new boolean[this.bitSize + 10];
        boolean[] aux = toBinary(this.exp, 8);
        ret[0] = this.signed;
        for (int i = 1; i <= 8; i++) {
            ret[i] = aux[i - 1];
        }
        for (int i = 9; i < this.bitSize + 8; i++) {
            ret[i] = this.binaryNumber[i - 9];
        }

        return ret;
        
    }
    
    public boolean[] numberSignal() {
        boolean[] ret = new boolean[this.bitSize + 1];
        ret[0] = this.signed;
        for (int i = 1; i < this.bitSize - 1; i++) {
            ret[i] = this.binaryNumber[i - 1];
        }

        return ret;
    }

    
    
    public boolean[] toFloat(double x, int y) throws BinaryArrayException {
        int contAux = 0;
        int expA = 128;
        int intPart = (int) x;
        double floatPart = x - intPart;
        boolean[] floatPartA = new boolean[y];

        boolean[] intPartA = super.toBinary(intPart, y); //Parte inteira do binário
        boolean[] intPartB = new boolean[y];
        int count = 0;

        
        //Contador para ver quantos right shifts serão necessários aplicar para retornar o expoente.
        if (intPart > 0) {
            while (!intPartA[count]) { 
                count++;
            }
        }

        //Se o número for maior que 0, calcula a quantidade de right shifts serão necessários.
        if (intPart > 0) {
            count = y - count - 1;
        }

        //Aplica os Right Shifts necessários para a normalização do binãrio em Floating Point
        for (int i = count; i > 0; i--) {
            rightShift(intPartB);
            intPartB[0] = intPartA[y - 1];
            rightShift(intPartA);
            expA++;
            contAux++;
        }

        //Faz a matriz da parte depois da virgula do número em Floating Point
        count = 0;
        while (floatPart != 0 && count <= floatPartA.length - 1) { 
            floatPart *= 2;
            if (floatPart >= 1) {
                floatPartA[count] = true;
                floatPart -= 1;
            } else {
                floatPartA[count] = false;
            }
            count++;
        }

        //Se o número não tiver uma parte inteira, normaliza usando Left Shifts.
        if (!intPartA[y - 1]) {
            count = 0;
            while (!intPartA[y - 1]) {
                intPartA[y - 1] = floatPartA[0];
                leftShift(floatPartA);
                expA--;
            }
        }

        //Aplica a quantidade de Right Shifts necessários de acordo quantos Right Shifts foram feitos para a parte inteira, se ela existir.
        for (int i = 0; i < expA - 128; i++) { 
            rightShift(floatPartA);
        }

        //Criação de 2 Binários inteiros para a soma e a criação da mantissa.
        BinaryInt sum1 = new BinaryInt(false, intPartB);
        BinaryInt sum2 = new BinaryInt(false, floatPartA);

        sum1 = sum1.sum(sum2); 
        
       
        this.expo = toBinary(expA,8);

        return sum1.binaryNumber;
    }

    public double floatToDouble() throws BinaryArrayException {
        boolean negativo = this.signed;
        int exp = toInt(false,this.expo);
        double result;
        double inteiro;
        double flutuante = 0;
        boolean[] intPart = new boolean[this.bitSize];
        intPart[this.bitSize - 1] = true;


        //Se o expoente for maior que 0, aplica os Left Shifts necessários para obter a parte inteira de um binário em Floating Point 
        for (int i = 0; i < exp - 128; i++) {
            leftShift(intPart);
            intPart[this.bitSize - 1] = this.binaryNumber[0];
            this.leftShift();
        }

        //Se o expoente for menor que 0, aplica Right Shifts necessários para obter a parte inteira de um binário em Floating Point.
        if (exp - 128 < 0) {
            for (int i = 0; i < 128 - exp; i++) {
                this.rightShift();
                this.binaryNumber[0] = intPart[this.bitSize - 1];
                rightShift(intPart);
            }
        }

        //Cria um binário inteiro para utilizar e retornar a parte inteira do Floating Point
        BinaryInt aux = new BinaryInt(false, intPart);

        inteiro = (double) aux.toInt();

        //Leitor para a parte fracionada do Floating Point para obter a parte fracionada em decimal.
        for (int i = 0; i < this.bitSize - 1; i++) {
            if (this.binaryNumber[i]) {
                flutuante += Math.pow(0.5, (i + 1));
            }
        }

        //soma da parte inteira e a parte fracionada em decimal.
        result = inteiro + flutuante;

       if(negativo){
           return -result;
       }

        return result;

    }

    public BinaryFloat floatSum(BinaryFloat bin) throws BinaryArrayException {
        //Checa qual dos expoentes é maior e normaliza a mantissa do Binário com o menor expoente
        boolean igual = (Arrays.equals(this.expo, bin.expo));
        
        if(!igual){
            BinaryInt m1 = new BinaryInt(false,this.expo);
            BinaryInt m2 = new BinaryInt(false,bin.expo);
            boolean menor = m1.sub(m2).signed;

            if (!menor) {
                bin.rightShift();
                bin.binaryNumber[0] = true;
                while (!Arrays.equals(this.expo, bin.expo)) {
                    bin.rightShift();
                }
            }
            else {
                this.rightShift();
                this.binaryNumber[0] = true;
                while (!Arrays.equals(this.expo,bin.expo)) {
                    this.rightShift();
                }
            }
        }

        
        boolean[] mant1 = this.binaryNumber; 
        boolean[] mant2 = bin.binaryNumber;

        //Condição de checagem de overflow da mantissa
        boolean overflowCheck = (mant1[0] || mant2[0]);

        //Soma das mantissas
        boolean[] res = sumIgnoringOverflow(mant1, mant2);

        //Checa se houve overflow ou se os expoentes forem iguais, aplica um Right Shift para normalizar a mantissa
        if ((overflowCheck && !res[0]) || igual) {
            BinaryInt one = new BinaryInt(1,8);
            this.expo = one.sum(new BinaryInt(false,this.expo)).binaryNumber;
            rightShift(res);
        }

        BinaryFloat ret = new BinaryFloat(false, res, res.length, this.expo); 

        return ret;
    }

    public BinaryFloat floatSub(BinaryFloat bin) throws BinaryArrayException{
        
        boolean normalizeCheck = false; 
        BinaryInt exp1 = new BinaryInt(false,this.expo);
        BinaryInt exp2 = new BinaryInt(false,bin.expo);
        
        boolean igual = (Arrays.equals(this.expo, bin.expo));
        boolean negativo = exp1.sub(exp2).signed;
        //normalizador das mantissas usando Right Shift na mantissa com o menor expoente.
        if(!igual){
            if(!negativo){
                //System.out.println("test");
                bin.rightShift();
                bin.binaryNumber[0] = true;
                while(!Arrays.equals(this.expo, bin.expo)){
                    bin.rightShift();
                }
            }
            else{
                this.rightShift();
                this.binaryNumber[0] = true;
                while(!Arrays.equals(this.expo,bin.expo)){
                    this.rightShift();
                }
            }
        }
        
        //checa qual dos dois numeros é menor que o outro e acerta a flag negativo se o segundo numero for maior que o primeiro
        int count = 0;
        int count2 = 0;
        while(!this.binaryNumber[count]&&count<this.bitSize-1){
            count++;
        }
        
        while(!bin.binaryNumber[count2]&&count2<bin.bitSize-1){
            count2++;
        }
        if(igual){
            if(count>count2)
                negativo=true;
        }
        
        
        //Soma com o complemento de 2 da mantissa e checa se necessita de normalização
        boolean[] mant1 = this.binaryNumber;
        boolean[] mant2 = bin.binaryNumber;
        
        
        //BinaryInt test1 = new BinaryInt(false,mant1);
        //BinaryInt test2 = new BinaryInt(false,mant2);
        //BinaryInt result = test1.sub(test2);
       
        
        
        System.out.println(Arrays.toString(mant2));
        
        
        mant2 = complementOfOne(mant2);
        boolean[] one = new boolean[this.bitSize]; one[this.bitSize-1] = true;
        mant2 = sumIgnoringOverflow(mant2,one);
        boolean[] res = sumIgnoringOverflow(mant1,mant2);
        
        //System.out.println(Arrays.toString(mant1));
        //System.out.println(Arrays.toString(mant2));
        
 
        
        System.out.println(Arrays.toString(res));
        
        //condição de normalização        
        if(negativo){
            System.out.println("test");
            res = complementOfOne(res);        
            res = sumIgnoringOverflow(one,res);
        }
        
        normalizeCheck = (mant1[0]||mant2[0]);
        
        //this.leftShift();
        //leftShift(res);
        //leftShift(res);
        //this.leftShift();
        
        //checa se precisa normalizar a mantissa e normaliza a mantissa quando preciso.
        System.out.println(Arrays.toString(res));
        BinaryInt um = new BinaryInt(1,8);
        if((normalizeCheck&&res[0])||!normalizeCheck){
            //System.out.println("test");
            boolean aux = mant2[0];
            //leftShift(res);
            while(!aux){
                aux = res[0]; 
                this.leftShift();
                leftShift(res);
            }
        }
        
          
        //System.out.println(Arrays.toString(this.expo));
        //System.out.println(Arrays.toString(bin.expo));
        //System.out.println(Arrays.toString(res));
        if(negativo){     
            return new BinaryFloat(true,res,res.length,this.expo);
        }
              
        return new BinaryFloat(false,res,res.length,this.expo);
    }
    
    public BinaryFloat floatMul(BinaryFloat bin) throws BinaryArrayException{ 
            //Checa se o resultado vai ser negativo, atribui as mantissas e soma os expoentes e subtrai o bias.
            boolean[] zero = new boolean[this.bitSize];            
            
            boolean negativo = (this.signed!=bin.signed);
            BinaryInt bias = new BinaryInt(128,8);
            BinaryInt exp1 = new BinaryInt(false,this.expo);
            BinaryInt exp2 = new BinaryInt(false,bin.expo);
            exp1 = exp1.sub(bias);
            exp1 = exp1.sum(exp2);
            if(exp1.signed){
                throw new BinaryArrayException("Underflow Detectado.");
            }
            if((Arrays.equals(this.binaryNumber, zero)&&Arrays.equals(this.expo, bias.binaryNumber))||(Arrays.equals(bin.binaryNumber, zero)&&Arrays.equals(bin.expo, bias.binaryNumber)))
                return new BinaryFloat(0,this.bitSize);
            
            boolean[] expRes = exp1.binaryNumber;
            boolean[] mant1 = this.binaryNumber;
            boolean[] mant2 = bin.binaryNumber;
            
            
            int count = 1;
            int count2 = 1;
            
            while(!mant1[count-1]&&count<this.bitSize-1){
                count++;
            }
            while(!mant2[count2-1]&&count<this.bitSize-1){
                count2++;
            }
            
            int check = Math.max(count,count2)*2;
            
            
            
            //Aplica um Right Shift nas mantissas e adiciona o significando para a multiplicação
            rightShift(mant1);
            rightShift(mant2);
            
            mant1[0] = true;
            mant2[0] = true;
            
            BinaryInt mu1 = new BinaryInt(false,mant1);
            BinaryInt mu2 = new BinaryInt(false,mant2);
            boolean[] res1 = mu2.mult(mu1).binaryNumber;
            boolean[] res2 = new boolean[this.bitSize];
                    
            //Checa a parte significante da multiplicação e atribui a um arranjo
            int i = 0;
            while(!res1[i]){
                i++;
            }
            
            for (int j = 0; j < this.bitSize; j++) {
                res2[j] = res1[i+j];
        }

            //Normalizador do resultado
            leftShift(res2);
            boolean normCheck = ((mant1[0]||mant2[0])&&!res2[0]);
            
            System.out.println(check);
            System.out.println(Arrays.toString(res2));
            
            BinaryInt exp = new BinaryInt(false,expRes);
            if (normCheck){
                BinaryInt one = new BinaryInt(1,8);
                exp = one.sum(new BinaryInt(false,expRes));
            }
            

            if(negativo){
                return new BinaryFloat(true,res2,this.bitSize,exp.binaryNumber);
            }
            
            return new BinaryFloat(false,res2,this.bitSize,exp.binaryNumber);
    }
    
       public BinaryFloat floatDiv(BinaryFloat bin) throws BinaryArrayException {
           //Checa se o resultado será negativo ou positivo, atribui as mantissas e subtrai os expoentes e adiciona o bias.
            boolean negativo = (this.signed!=bin.signed);
            boolean[] mant1 = this.binaryNumber;
            boolean[] mant2 = bin.binaryNumber;
            BinaryInt bias = new BinaryInt(128,8);
            BinaryInt exp1 = new BinaryInt(false,this.expo);
            BinaryInt exp2 = new BinaryInt(false,bin.expo);
            exp1 = exp1.sub(exp2);
            BinaryInt expR = exp1.sum(bias);
            boolean[] exp = expR.binaryNumber;
            
            if(expR.signed){
                throw new BinaryArrayException("Underflow detectado na divisão.");
            }
            
            
            //Transforma as mantissas em significandos para a divisão
            rightShift(mant1);
            rightShift(mant2);
            
            mant1[0] = true;
            mant2[0] = true;
            
            boolean[] dividendo = new boolean[mant1.length+1];
            boolean[] divisor = new boolean[mant2.length+1];
            
            for (int i = 0; i < this.bitSize-1; i++) {
               dividendo[i+1] = mant1[i];
               divisor[i+1] = mant2[i];
           }
            
            //Atribui o complemento de 2 no divisor
            boolean[] one = new boolean[divisor.length];
            one[one.length-1]=true;
            divisor = complementOfOne(divisor);
            divisor = sumIgnoringOverflow(divisor, one);
            
            //Atribuições para a divisão
            int count = 0;
            boolean isZero = false;
            boolean[] atual = dividendo;
            boolean[] parcial;
            boolean[] result = new boolean[this.bitSize];
            while(!isZero&&count<this.bitSize){
                //Soma o dividendo atual com o complemento de 2 do divisor e guarda no resultado parcial.
                isZero = true;
                parcial = sumIgnoringOverflow(atual,divisor);
                
                //Checa se o resultado parcial se tornou 0, satisfazendo a condição de parada
                for (int i = 0; i < this.bitSize+1; i++) {
                    if (parcial[i]) {
                        isZero = false;
                    }
                }
                
                //Checa se foi possível fazer a divisão parcial, se não for possível adiciona false no indice do resultado e aplica um Left Shift no dividendo atual
                if (parcial[0]){
                    leftShift(atual);
                    result[count] = false;
                }
                //Se foi possível fazer a divisão parcial, o dividendo atual se torna o resultado parcial, aplica um Left Shift no dividendo atual e marca true na respota correspondente ao indice.
                else{
                    atual = parcial;
                    leftShift(atual);
                    result[count] = true;
                }
                count++;
            }
            
            //Normaliza a resposta
            BinaryInt atualExp = new BinaryInt(false,exp);
            BinaryInt um = new BinaryInt(1,8);
            boolean shifts = result[0];                
            leftShift(result);
            while(!shifts){
                leftShift(result);
                shifts = result[0];
                exp = atualExp.sum(um).binaryNumber;
            }
            
            
            if(negativo){
                return new BinaryFloat(true,result,this.bitSize,exp);
            }
            
            return new BinaryFloat(false,result,this.bitSize,exp);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.binaryNumber);
    }
}
