/**
 * Copyright © 2013, Masih H. Derkani
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
