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
