import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedBallStatistics {
    public static void main(String[] args) {
        // 假设历史期数的红色双色球号码分别为：
        List<List<Integer>> historyNumbers = Arrays.asList(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7),  // 第1期
                Arrays.asList(2, 3, 4, 5, 6, 7, 8),  // 第2期
                Arrays.asList(3, 4, 5, 6, 7, 8, 9),  // 第3期
                Arrays.asList(4, 5, 6, 7, 8, 9, 10), // 第4期
                Arrays.asList(5, 6, 7, 8, 9, 10, 11) // 第5期
        );

        List<Integer> currentNumbers = Arrays.asList(6, 7, 8, 9, 10, 11, 12); // 当前期的红色双色球号码

        int historyCount5 = countRepeatedNumbers(historyNumbers, currentNumbers, 5);
        int historyCount7 = countRepeatedNumbers(historyNumbers, currentNumbers, 7);

        double historyProbability5 = calculateRepeatedNumbersProbability(historyNumbers, currentNumbers, 5);
        double historyProbability7 = calculateRepeatedNumbersProbability(historyNumbers, currentNumbers, 7);

        System.out.println("在以往5期中的重号个数为：" + historyCount5);
        System.out.println("在以往7期中的重号个数为：" + historyCount7);
        System.out.println("在以往5期中的重号概率为：" + historyProbability5);
        System.out.println("在以往7期中的重号概率为：" + historyProbability7);
    }

    // 统计重号个数
    private static int countRepeatedNumbers(List<List<Integer>> historyNumbers, List<Integer> currentNumbers, int historyPeriods) {
        int count = 0;
        List<Integer> lastNumbers = historyNumbers.get(historyNumbers.size() - 1);
        for (int i = 0; i < currentNumbers.size(); i++) {
            if (lastNumbers.contains(currentNumbers.get(i))) {
                count++;
            }
        }
        return count;
    }

    // 计算重号概率
    private static double calculateRepeatedNumbersProbability(List<List<Integer>> historyNumbers, List<Integer> currentNumbers, int historyPeriods) {
        int count = countRepeatedNumbers(historyNumbers, currentNumbers, historyPeriods);
        return (double) count / currentNumbers.size();
    }
}