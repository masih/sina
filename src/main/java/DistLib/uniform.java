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

import DistLib.rng.WichmannHill;

/**
 * Uniform distribution over an interval.
 */

public class uniform {

    /**
     * density of the uniform distribution.
     */
    public static double density(double x, double a, double b) {

        if (Double.isNaN(x) || Double.isNaN(a) || Double.isNaN(b)) return x + a + b;
        if (b <= a) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        if (a <= x && x <= b) return 1.0 / (b - a);
        return 0.0;
    }

    /**
     * distribution function of the uniform distribution.
     */
    public static double cumulative(double x, double a, double b) {

        if (Double.isNaN(x) || Double.isNaN(a) || Double.isNaN(b)) return x + a + b;
        if (b <= a) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        if (x <= a) return 0.0;
        if (x >= b) return 1.0;
        return (x - a) / (b - a);
    }

    /**
     * quantile function of the uniform distribution.
     */
    public static double quantile(double x, double a, double b) {

        if (Double.isNaN(x) || Double.isNaN(a) || Double.isNaN(b)) return x + a + b;
        if (b <= a || x < 0 || x > 1) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        return a + x * (b - a);
    }

    /**
     *  Random variates from the uniform distribution.
     */
    public static double random(double a, double b) {

        if (Double.isInfinite(a) || Double.isInfinite(b) || b < a) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        if (a == b)
            return a;
        else return a + (b - a) * random();
    }

    /**
     * Generator used during random() call. Can be set.
     */
    public static StdUniformRng uniRng = new WichmannHill();

    /**
     * generate standard uniform random variate
     */
    public static double random() {

        return uniRng.random();
    }

}
