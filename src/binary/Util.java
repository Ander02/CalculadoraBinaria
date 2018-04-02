package binary;

/**
 *
 * @author Anderson
 */
public class Util {

    /**
     *
     * @param num1
     * @param num2
     * @return
     */
    public static int max(int num1, int num2) {
        if (num1 >= num2) {
            return num1;
        } else {
            return num2;
        }
    }

    /**
     *
     * @param num One int numbem
     * @return A char array with the binary representation
     */
    public static char[] toBinaryCharArray(int num) {
        return Integer.toBinaryString(num).toCharArray();
    }

    public static int[] toBinaryIntArray(char[] binaryCharArray) {
        int[] binaryArray = new int[binaryCharArray.length];

        for (int i = 0; i < binaryCharArray.length; i++) {
            switch (binaryCharArray[i]) {
                case '1':
                    binaryArray[i] = 1;
                    break;
                case '0':
                    binaryArray[i] = 0;
                    break;
                default:
                    System.out.println("Deu merda");
                    break;
            }
        }

        return binaryArray;
    }

    public static int[] toBinaryIntArray(int num) {
        char[] binaryCharArray = toBinaryCharArray(num);
        return toBinaryIntArray(binaryCharArray);
    }

    public static int[] complementoDeUm(int[] binaryCharArray) {
        int[] result = new int[binaryCharArray.length];

        for (int i = 0; i < binaryCharArray.length; i++) {
            if (binaryCharArray[i] == 0) {
                result[i] = 1;
            } else {
                result[i] = 0;
            }
        }

        return result;

    }

    public static int[] complementoDeDois(int[] binary) {
        int[] aux = new int[binary.length];
        int[] one = new int[binary.length];

        for (int i = 0; i < (one.length - 1); i++) {
            one[i] = 0;
        }
        one[(one.length - 1)] = 1;

        aux = complementoDeUm(binary);
        aux = Operations.sumBin(aux, one);

        return aux;
    }    
}
