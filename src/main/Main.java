package main;

import binary.BinaryInt;
import binary.IntDivisionResult;
import exception.BinaryArrayException;
import binary.SumBitAux;
import binary.Util;
import java.util.Arrays;
import exception.BinaryArrayException;
import binary.BinaryFloat;
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

        Main.printHeader();

        Scanner input = new Scanner(System.in);

        BinaryInt bin1;
        BinaryInt bin2;

        Main.printMenu();
        int typeOption = -1;
        do {
            typeOption = Main.requestInt();

            switch (typeOption) {
                //Inteiros
                case 0:
                    int operationOption = -1;
                    do {

                        Main.printOperationsOption("Inteiro");

                        operationOption = Main.requestInt();

                        switch (operationOption) {
                            case 0:

                                bin1 = Main.requestBinaryNumber("primeiro");
                                bin2 = Main.requestBinaryNumber("segundo");
                                BinaryInt sum = bin1.sum(bin2);

                                Main.printBinaryIntResult(sum, "soma");
                                break;
                            case 1:

                                bin1 = Main.requestBinaryNumber("primeiro");
                                bin2 = Main.requestBinaryNumber("segundo");
                                BinaryInt sub = bin1.sub(bin2);

                                Main.printBinaryIntResult(sub, "subtração");
                                break;
                            case 2:

                                bin1 = Main.requestBinaryNumber("primeiro");
                                bin2 = Main.requestBinaryNumber("segundo");
                                BinaryInt mult = bin1.mult(bin2);

                                Main.printBinaryIntResult(mult, "multiplicação");
                                break;
                            case 3:

                                bin1 = Main.requestBinaryNumber("primeiro");
                                bin2 = Main.requestBinaryNumber("segundo");
                                BinaryInt div = bin1.mult(bin2);

                                Main.printBinaryIntResult(div, "divisão");
                                break;
                        }

                    } while (operationOption > 0);
                    break;

                //Floats
                case 1:

                    break;
            }

        } while (typeOption > 0);

        Main.sayGoodBye();

        try {

        } catch (Exception e) {

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

    private static void printOperationsOption(String type) {
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

    private static BinaryInt requestBinaryNumber(String order) {
        Scanner input = new Scanner(System.in);

        System.out.println("Digite o " + order + " número em decimal");
        System.out.print(">");
        int num1 = input.nextInt();

        System.out.println("Digite o tamanho em bits desse número");
        System.out.print(">");

        int bitSize1 = input.nextInt();

        return new BinaryInt(num1, bitSize1);
    }

    private static void printBinaryIntResult(BinaryInt bin, String operation) {
        System.out.println("O resuldado da " + operation + " foi: ");

        System.out.println(bin);
        System.out.println();

    }
}
