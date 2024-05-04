import java.util.HashMap;
import java.util.Map;

interface DistributionStrategy {
    Map<String, Integer> analyzeDistribution(int[][] numbers);
}

class TwelveRemainderDistribution implements DistributionStrategy {
    @Override
    public Map<String, Integer> analyzeDistribution(int[][] numbers) {
        Map<String, Integer> distribution = new HashMap<>();
        for (int[] balls : numbers) {
            for (int num : balls) {
                int remainder = num % 12;
                String interval = getInterval(remainder);
                distribution.put(interval, distribution.getOrDefault(interval, 0) + 1);
            }
        }
        return distribution;
    }

    private String getInterval(int remainder) {
        // 实现具体的区间分布规则
        switch (remainder) {
            case 0:
            case 1:
                return "0-1区间";
            case 2:
            case 3:
                return "2-3区间";
            case 4:
            case 5:
                return "4-5区间";
            case 6:
            case 7:
                return "6-7区间";
            case 8:
            case 9:
                return "8-9区间";
            case 10:
            case 11:
                return "10-11区间";
            default:
                return "";
        }
    }
}

class DistributionAnalyzer {
    private DistributionStrategy strategy;

    public DistributionAnalyzer(DistributionStrategy strategy) {
        this.strategy = strategy;
    }

    public void analyze(int[][] numbers) {
        Map<String, Integer> distribution = strategy.analyzeDistribution(numbers);
        printDistribution(distribution);
    }

    private void printDistribution(Map<String, Integer> distribution) {
        for (Map.Entry<String, Integer> entry : distribution.entrySet()) {
            String interval = entry.getKey();
            int count = entry.getValue();
            System.out.println(interval + ": " + count + "次");
        }
    }
}

public class TwelveRemainderAnalysis {
    public static void main(String[] args) {
        // 示例近30期数据，每期6个红球和一个蓝球
        int[][] redBalls = {
                {1, 2, 3, 4, 5, 6},    // 第1期
                {7, 8, 9, 10, 11, 12}, // 第2期
                // ... 添加更多期数的红球号码
                {25, 26, 27, 28, 29, 30} // 第30期
        };
        int[] blueBalls = {1, 2, 3, 4, 5, 6}; // 近30期的蓝球号码

        DistributionStrategy redDistributionStrategy = new TwelveRemainderDistribution();
        DistributionStrategy blueDistributionStrategy = new TwelveRemainderDistribution();

        DistributionAnalyzer redAnalyzer = new DistributionAnalyzer(redDistributionStrategy);
        DistributionAnalyzer blueAnalyzer = new DistributionAnalyzer(blueDistributionStrategy);

        System.out.println("近30期红球的12余数区间分布：");
        redAnalyzer.analyze(redBalls);

        System.out.println("近30期蓝球的12余数区间分布：");
        blueAnalyzer.analyze(new int[][]{blueBalls});
    }
}