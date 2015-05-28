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
package org.mashti.sina.distribution;

import DistLib.Constants;
import DistLib.beta;
import DistLib.misc;
import DistLib.normal;
import org.mashti.sina.util.NumericalRangeValidator;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.mashti.sina.util.NumericalRangeValidator.validateRangeZeroToOneInclusive;

public class TDistribution implements ProbabilityDistribution {

    private static final double EPS = 1.e-12;
    private final double df;

    public TDistribution(final Number degrees_of_freedom) {

        NumericalRangeValidator.validateRangeLargerThanZeroExclusive(degrees_of_freedom);
        df = degrees_of_freedom.doubleValue();
    }

    @Override
    public Number density(final Number x_d) {

        final double x = x_d.doubleValue();
        if (Double.isNaN(x)) { return Double.NaN; }
        if (Double.isInfinite(x)) { return ZERO; }
        if (Double.isInfinite(df)) { return normal.density(x, ZERO, ONE); }
        return pow(1.0 + x * x / df, -0.5 * (df + 1.0)) / (sqrt(df) * misc.beta(0.5, 0.5 * df));
    }

    @Override
    public Number cumulative(final Number x_) {

        final double x = x_.doubleValue();
        if (Double.isNaN(x)) { return Double.NaN; }
        if (Double.isInfinite(x)) { return x < ZERO ? ZERO : ONE; }
        if (Double.isInfinite(df)) { return normal.cumulative(x, 0.0, 1.0); }

        final double val = 0.5 * beta.cumulative(df / (df + x * x), df / 2.0, 0.5);
        return x > ZERO ? 1 - val : val;
    }

    @Override
    public Number quantile(final Number probability) {

        validateRangeZeroToOneInclusive(probability);
        final double p = probability.doubleValue();
        double a, b, c, d, prob, P, q, x, y;
        boolean neg;

        if (Double.isNaN(p)) { return Double.NaN; }
        if (p == ZERO) { return Double.NEGATIVE_INFINITY; }
        if (p == ONE) { return Double.POSITIVE_INFINITY; }
        if (df > 1e20) { return normal.quantile(p, 0.0, 1.0); }

        if (p > HALF) {
            neg = false;
            P = 2 * (1 - p);
        }
        else {
            neg = true;
            P = 2 * p;
        }

        if (abs(df - 2) < EPS) {
            q = sqrt(2 / (P * (2 - P)) - 2);
        }
        else if (df < 1 + EPS) {
            prob = P * Constants.M_PI_half;
            q = Math.cos(prob) / Math.sin(prob);
        }
        else {
            a = 1 / (df - 0.5);
            b = 48 / (a * a);
            c = ((20700 * a / b - 98) * a - 16) * a + 96.36;
            d = ((94.5 / (b + c) - 3) / b + 1) * sqrt(a * Constants.M_PI_half) * df;
            y = pow(d * P, 2 / df);

            if (y > 0.05 + a) {
                x = normal.quantile(0.5 * P, 0.0, 1.0);
                y = x * x;
                if (df < 5) {
                    c += 0.3 * (df - 4.5) * (x + 0.6);
                }
                c = (((0.05 * d * x - 5) * x - 7) * x - 2) * x + b + c;
                y = (((((0.4 * y + 6.3) * y + 36) * y + 94.5) / c - y - 3) / b + 1) * x;
                y = a * y * y;
                if (y > 0.002) {
                    y = Math.exp(y) - 1;
                }
                else {
                    y = 0.5 * y * y + y;
                }
            }
            else {
                y = ((1 / (((df + 6) / (df * y) - 0.089 * d - 0.822) * (df + 2) * 3) + 0.5 / (df + 4)) * y - 1) * (df + 1) / (df + 2) + 1 / y;
            }
            q = sqrt(df * y);
        }

        return neg ? -q : q;
    }

    @Override
    public Number mean() {

        return df > 1 ? ZERO : Double.NaN;
    }

    @Override
    public Number median() {

        return ZERO;
    }

    @Override
    public Number mode() {

        return ZERO;
    }

    @Override
    public Number variance() {

        return df > 2 ? ZERO : 1 < df && df <= 2 ? Double.POSITIVE_INFINITY : Double.NaN;
    }

    @Override
    public Number skewness() {

        return df > 3 ? ZERO : Double.NaN;
    }
}
