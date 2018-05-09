package main;

import binary.BinaryFloat;
import binary.BinaryInt;
import binary.IntDivisionResult;
import exception.BinaryArrayException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Anderson
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws exception.BinaryArrayException
     */
    public static void main(String[] args) throws BinaryArrayException {
        
        
        try {
            Main.printHeader();

            BinaryInt bin1;
            BinaryInt bin2;

            BinaryFloat float1;
            BinaryFloat float2;

            Main.printMenu();
            int typeOption;
            do {
                
                typeOption = Main.requestInt();

                switch (typeOption) {
                    //Inteiros
                    case 0:
                        int intOperationOption;
                        do {
                            Main.printOperationsOptions("Inteiro");

                            intOperationOption = Main.requestInt();

                            switch (intOperationOption) {
                                case 0:

                                    bin1 = Main.requestBinaryIntNumber("primeiro");
                                    bin2 = Main.requestBinaryIntNumber("segundo");
                                    BinaryInt sum = bin1.sum(bin2);

                                    Main.printBinaryIntResult(sum, "soma");
                                    break;
                                case 1:

                                    bin1 = Main.requestBinaryIntNumber("primeiro");
                                    bin2 = Main.requestBinaryIntNumber("segundo");
                                    BinaryInt sub = bin1.sub(bin2);

                                    Main.printBinaryIntResult(sub, "subtração");
                                    break;
                                case 2:

                                    bin1 = Main.requestBinaryIntNumber("primeiro");
                                    bin2 = Main.requestBinaryIntNumber("segundo");
                                    BinaryInt mult = bin1.mult(bin2);

                                    Main.printBinaryIntResult(mult, "multiplicação");
                                    break;
                                case 3:

                                    bin1 = Main.requestBinaryIntNumber("primeiro");
                                    bin2 = Main.requestBinaryIntNumber("segundo");
                                    IntDivisionResult divRes = bin1.div(bin2);

                                    Main.printBinaryIntDivResult(divRes, "divisão");
                                    break;
                            }

                        } while (intOperationOption >= 0);
                        break;

                    //Floats
                    case 1:
                        int floatOperationOption;
                        do {
                            Main.printOperationsOptions("Float");

                            floatOperationOption = Main.requestInt();

                            switch (floatOperationOption) {
                                case 0:

                                    float1 = Main.requestBinaryFloatNumber("primeiro");
                                    float2 = Main.requestBinaryFloatNumber("segundo");
                                    BinaryFloat sum = float1.floatSum(float2);

                                    Main.printBinaryFloatResult(sum, "soma");
                                    break;
                                case 1:

                                    float1 = Main.requestBinaryFloatNumber("primeiro");
                                    float2 = Main.requestBinaryFloatNumber("segundo");
                                    BinaryFloat sub = float1.floatSub(float2);

                                    Main.printBinaryFloatResult(sub, "subtração");
                                    break;
                                case 2:

                                    float1 = Main.requestBinaryFloatNumber("primeiro");
                                    float2 = Main.requestBinaryFloatNumber("segundo");
                                    BinaryFloat mult = float1.floatMul(float2);

                                    Main.printBinaryFloatResult(mult, "multiplicação");
                                    break;
                                case 3:

                                    float1 = Main.requestBinaryFloatNumber("primeiro");
                                    float2 = Main.requestBinaryFloatNumber("segundo");
                                    BinaryFloat div = float1.floatDiv(float2);

                                    Main.printBinaryFloatResult(div, "divisão");
                                    break;
                            }

                        } while (floatOperationOption >= 0);
                        break;
                }

            } while (typeOption >= 0);

            Main.sayGoodBye();

        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }
    }

    private static void printHeader() {
        System.out.println("Bem vindo à Calculadora Binária");
        System.out.println("Anderson Pessoa & Marcus Vinicius");
        System.out.println();
    }

    private static void printMenu() {
        System.out.println("Que tipo de operações você gostaria de fazer?");
        System.out.println("0 - Com Inteiros");
        System.out.println("1 - Com Floats");
        System.out.println("-1 - Sair");
    }

    private static void sayGoodBye() {
        System.out.println("Good bye!");
    }

    private static void printOperationsOptions(String type) {
        System.out.println("Você escolheu: " + type);
        System.out.println("O que você gostaria de fazer?");
        System.out.println("0 - Somar");
        System.out.println("1 - Subtrair");
        System.out.println("2 - Multiplicar");
        System.out.println("3 - Dividir");
        System.out.println("-1 - Sair");
    }

    private static int requestInt() {

        try {
            Scanner input = new Scanner(System.in);
            System.out.print(">");
            int num = input.nextInt();
            System.out.println();
            return num;
        } catch (Exception e) {
            return -1;
        }
    }

    private static double requestFloat() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print(">");
            double num = input.nextDouble();
            System.out.println();
            return num;
        } catch (Exception e) {
            return -1;
        }
    }

    private static BinaryInt requestBinaryIntNumber(String order) {
        boolean ok = false;

        while (!ok) {
            System.out.println("Digite o " + order + " número em decimal");
            int number = Main.requestInt();

            System.out.println("Digite o tamanho em bits desse número");
            int bitSize = Main.requestInt();
            try {

                return new BinaryInt(number, bitSize);

            } catch (Exception e) {
                System.out.println("Ocorreu um problema, tente novamente");
                ok = false;
            }
        }
        return null;
    }

    private static BinaryFloat requestBinaryFloatNumber(String order) {
        boolean ok = false;

        while (!ok) {
            System.out.println("Digite o " + order + " número em decimal");
            double number = Main.requestFloat();

            System.out.println("Digite a precisão desse número");
            int precision = Main.requestInt();
            try {
                return new BinaryFloat(number, precision);

            } catch (Exception e) {
                System.out.println("Ocorreu um problema, tente novamente");
                ok = false;
            }
        }
        return null;
    }

    private static void printBinaryIntResult(BinaryInt bin, String operation) {
        System.out.println("O resuldado da " + operation + " foi: ");

        System.out.println(bin);
        System.out.println();

    }

    private static void printBinaryFloatResult(BinaryFloat bin, String operation) throws BinaryArrayException {
        System.out.println("O resuldado da " + operation + " foi: ");

        System.out.println(Arrays.toString(bin.binaryNumber)+"* 2^"+(bin.exp-128)+" = "+bin.floatToDouble());
        System.out.println();

    }

    private static void printBinaryIntDivResult(IntDivisionResult divRes, String operation) {
        System.out.println("O resuldado da " + operation + " foi: ");

        System.out.println(divRes);
        System.out.println();

    }
}
