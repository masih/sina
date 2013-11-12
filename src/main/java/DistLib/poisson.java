package DistLib;

/* data translated from C using perl script translate.pl */
/* script version 0.00                               */

public class poisson {

    /* Factorial Table */
    static double fact[] = {1.0, 1.0, 2.0, 6.0, 24.0, 120.0, 720.0, 5040.0, 40320.0, 362880.0};

    public static double cumulative(double x, double lambda) {

        if (Double.isNaN(x) || Double.isNaN(lambda)) return x + lambda;
        x = java.lang.Math.floor(x + 0.5);
        if (lambda <= 0.0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        if (x < 0) return 0;
        if (Double.isInfinite(x)) return 1;
        return 1 - gamma.cumulative(lambda, x + 1, 1.0);
    }

    public static double density(double x, double lambda) {

        if (Double.isNaN(x) || Double.isNaN(lambda)) return x + lambda;
        x = java.lang.Math.floor(x + 0.5);
        if (lambda <= 0.0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        if (x < 0) return 0;
        if (Double.isInfinite(x)) return 0;
        return java.lang.Math.exp(x * java.lang.Math.log(lambda) - lambda - misc.lgammafn(x + 1));
    }

    public static double quantile(double x, double lambda) {

        double mu, sigma, gamma, z, y;
        if (Double.isNaN(x) || Double.isNaN(lambda)) return x + lambda;
        if (Double.isInfinite(lambda)) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        if (x < 0 || x > 1 || lambda <= 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        if (x == 0) return 0;
        if (x == 1) return Double.POSITIVE_INFINITY;
        mu = lambda;
        sigma = java.lang.Math.sqrt(lambda);
        gamma = sigma;
        z = normal.quantile(x, 0.0, 1.0);
        y = java.lang.Math.floor(mu + sigma * (z + gamma * (z * z - 1) / 6) + 0.5);
        z = cumulative(y, lambda);

        if (z >= x) {
            for (;;) {
                if ((z = poisson.cumulative(y - 1, lambda)) < x) return y;
                y = y - 1;
            }
        }
        else {

            for (;;) {
                if ((z = poisson.cumulative(y + 1, lambda)) >= x) return y + 1;
                y = y + 1;
            }
        }
    }

    public static double random(double mu, uniform PRNG) {

        throw new java.lang.ArithmeticException("FUNCTION NOT IMPLEMENTED");
    }
}
