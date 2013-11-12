package DistLib;

/* data translated from C using perl script translate.pl */
/* script version 0.00                               */

public class weibull {

    public static double cumulative(double x, double shape, double scale) {

        if (Double.isNaN(x) || Double.isNaN(shape) || Double.isNaN(scale)) return x + shape + scale;
        if (shape <= 0 || scale <= 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        if (x <= 0) return 0;
        return 1.0 - java.lang.Math.exp(-java.lang.Math.pow(x / scale, shape));
    }

    public static double density(double x, double shape, double scale) {

        double tmp1, tmp2;
        if (Double.isNaN(x) || Double.isNaN(shape) || Double.isNaN(scale)) return x + shape + scale;
        if (shape <= 0 || scale <= 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        if (x <= 0) return 0;
        if (Double.isInfinite(x)) return 0;
        tmp1 = java.lang.Math.pow(x / scale, shape - 1);
        tmp2 = tmp1 * (x / scale);
        return shape * tmp1 * java.lang.Math.exp(-tmp2) / scale;
    }

    public static double quantile(double x, double shape, double scale) {

        if (Double.isNaN(x) || Double.isNaN(shape) || Double.isNaN(scale)) return x + shape + scale;
        if (shape <= 0 || scale <= 0 || x < 0 || x > 1) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        if (x == 0) return 0;
        if (x == 1) return Double.POSITIVE_INFINITY;
        return scale * java.lang.Math.pow(-java.lang.Math.log(1.0 - x), 1.0 / shape);
    }

    public static double random(double shape, double scale, uniform PRNG) {

        if (Double.isInfinite(shape) || Double.isInfinite(scale) || shape <= 0.0 || scale <= 0.0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        return scale * java.lang.Math.pow(-java.lang.Math.log(PRNG.random()), 1.0 / shape);
    }
}
