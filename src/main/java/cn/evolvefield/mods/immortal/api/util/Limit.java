package cn.evolvefield.mods.immortal.api.util;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 9:29
 * Version: 1.0
 */
public class Limit {
    private final double increment, minValue, maxValue, weight;

    private Limit(final double increment, final double min, final double max, final double weights) {
        this.increment = (increment < max ? increment : 0D);
        this.minValue = (min < max ? min : 0D);
        this.maxValue = (max > min ? max : increment + min);
        this.weight = Math.min(Math.max(weights, 0D), 1D);
    }

    /**
     * @param increment 增量值（必须小于最大值）
     * @param min       最小值 (必须小于最大值).
     * @param max       最大值 (必须大于最小值).
     * @param weights   权重 - 随机几率；可以解释为该属性的稀有程度（必须介于 0 和 1 之间）。
     * @return 新的limit实例
     */
    public static Limit hold(final double increment, final double min, final double max, final double weights) {
        return new Limit(increment, min, max, weights);
    }

    /**
     * @return 一个空实例
     */
    public static Limit none() {
        return new Limit(0D, 0D, 0D, 0D);
    }

    public double increment() {
        return this.increment;
    }

    public double minValue() {
        return this.minValue;
    }

    public double maxValue() {
        return this.maxValue;
    }

    public double weight() {
        return this.weight;
    }

    @Override
    public boolean equals(Object par0) {
        if (par0 == null) return false;
        if (par0 == this) return true;
        if (!(par0 instanceof Limit)) return false;

        Limit var0 = (Limit) par0;

        return toString().equals(var0.toString());
    }

    @Override
    public String toString() {
        return "[incr=" + this.increment + ",min=" + this.minValue + ",max=" + this.maxValue + ",weight=" + this.weight + "]";
    }
}
