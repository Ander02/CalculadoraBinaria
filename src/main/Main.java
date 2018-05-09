package main;

import binary.BinaryInt;
import binary.IntDivisionResult;
import exception.BinaryArrayException;
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

            Main.printMenu();
            int typeOption;
            do {
                typeOption = Main.requestInt();

                switch (typeOption) {
                    //Inteiros
                    case 0:
                        int operationOption;
                        do {
                            Main.printOperationsOptions("Inteiro");

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
                                    IntDivisionResult divRes = bin1.div(bin2);

                                    Main.printBinaryIntDivResult(divRes, "divisão");
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

    private static BinaryInt requestBinaryNumber(String order) {
        //Scanner input = new Scanner(System.in);
        boolean ok = false;

        while (!ok) {
            System.out.println("Digite o " + order + " número em decimal");
            int num1 = Main.requestInt();

            System.out.println("Digite o tamanho em bits desse número");
            int bitSize1 = Main.requestInt();
            try {

                return new BinaryInt(num1, bitSize1);

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

    private static void printBinaryIntDivResult(IntDivisionResult divRes, String operation) {
        System.out.println("O resuldado da " + operation + " foi: ");

        System.out.println(divRes);
        System.out.println();

    }
}
