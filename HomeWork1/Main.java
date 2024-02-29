import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        int[] numberArray = new int[]{1,2,3,4,5,6,7,8,9,10};

/*  Нахождение среднего значения четных чисел обычным способом */
        int countOfEven = 0;
        int sumOfEven = 0;
        for (int i = 0; i < numberArray.length; i++) {
            if (numberArray[i] % 2 == 0) {
                sumOfEven = sumOfEven + numberArray[i];
                countOfEven = countOfEven + 1;
            }
        }
        System.out.println(sumOfEven/countOfEven);


/*  Нахождение среднего значения четных чисел через stream для массива чисел */
        System.out.println(Arrays.stream(numberArray)
                .filter(n -> n % 2 == 0)
                .average().orElseThrow());



/*  Нахождение среднего значения четных чисел через stream для списка чисел */
        List<Integer> evenNumbers = numbers.stream().filter(n -> n % 2 == 0).toList();
        System.out.println((double) evenNumbers.stream().reduce(0, Integer::sum)/evenNumbers.size());
    }
}
