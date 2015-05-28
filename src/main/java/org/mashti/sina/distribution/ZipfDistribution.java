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

import org.mashti.sina.util.NumericalRangeValidator;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class ZipfDistribution implements ProbabilityDistribution {

    private static final long serialVersionUID = -2899296734993846546L;
    private final Integer elements_count;
    private final double exponent;
    private final double harmonic_n_exp;
    private final double harmonic_n_exp_minus_1;

    public ZipfDistribution(Integer elements_count, double exponent) {

        NumericalRangeValidator.validateRangeLargerThanOneInclusive(elements_count);
        this.elements_count = elements_count;
        this.exponent = exponent;
        harmonic_n_exp = generalizedHarmonic(elements_count, exponent);
        harmonic_n_exp_minus_1 = generalizedHarmonic(elements_count, exponent - ONE);
    }

    public static void main(String[] args) {

        System.out.println(Math.pow(8d, 1d / 3d));
    }

    @Override
    public Number density(final Number x) {

        NumericalRangeValidator.validateRange(x, 1, elements_count, true, true);
        return ONE / Math.pow(x.doubleValue(), exponent) / harmonic_n_exp;
    }

    @Override
    public Number cumulative(final Number x) {

        final long element = x.longValue();
        if (element < 1 || element > elements_count) { throw new IllegalArgumentException("out of range");}
        return generalizedHarmonic(element, exponent) / harmonic_n_exp;
    }

    @Override
    public Number quantile(final Number probability) {
        //TODO implement
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public Number mean() {

        return harmonic_n_exp_minus_1 / harmonic_n_exp;
    }

    @Override
    public Number median() {

        //TODO implement
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public Number mode() {

        return ONE;
    }

    @Override
    public Number variance() {

        final double Hs2 = generalizedHarmonic(elements_count, exponent - 2);
        final double Hs1 = generalizedHarmonic(elements_count, exponent - 1);
        final double Hs = generalizedHarmonic(elements_count, exponent);
        return Hs2 / Hs - Hs1 * Hs1 / (Hs * Hs);
    }

    @Override
    public Number skewness() {

        return exponent;
    }

    /**
     * Calculates the Nth generalized harmonic number. See
     * <a href="http://mathworld.wolfram.com/HarmonicSeries.html">Harmonic
     * Series</a>.
     *
     * @param n Term in the series to calculate (must be larger than 1)
     * @param m Exponent (special case {@code m = 1} is the harmonic series).
     * @return the n<sup>th</sup> generalized harmonic number.
     */
    static double generalizedHarmonic(final long n, final double m) {

        double value = 0;
        for (long k = n; k > 0; --k) {
            value += 1.0 / Math.pow(k, m);
        }
        return value;
    }
}
