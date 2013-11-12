/**
 * This file is part of sina.
 *
 * sina is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sina is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with sina.  If not, see <http://www.gnu.org/licenses/>.
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
