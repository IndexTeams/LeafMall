package cn.kiroe.mall.mq.util;

public class DelayTimeUtil {
    private static final int[] DELAY_TIME_TABLE = new int[]{
            0, 1000, 5000, 10000, 30000, 60000, 120000, 180000, 240000, 300000,
            360000, 420000, 480000, 540000, 600000, 1200000, 1800000, 3600000, 7200000
    };

    public static int getDelayTimeByLevel(int delayLevel) {
        if (delayLevel < 0 || delayLevel >= DELAY_TIME_TABLE.length) {
            throw new IllegalArgumentException("Invalid delay level");
        }
        return DELAY_TIME_TABLE[delayLevel];
    }
}