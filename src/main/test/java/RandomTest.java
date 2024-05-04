
import cn.lottery.lottery.entity.Ssq;

public class RandomTest {

    public static void main(String[] args) {

//        Integer number = RandomUtil.randomInt(0, 6);
//        while (true){
//            if (number ==6)
//                break;
//            number = RandomUtil.randomInt(0, 6);
//        }
        RandomTest test = new RandomTest();
        Ssq ssq = new Ssq();
        ssq.setRed1("sss");
        test.testForObj(ssq);
        System.out.println(ssq);
    }

    public void testForObj(Ssq ssq){
        ssq.setRed1("wwqw");
    }
}
