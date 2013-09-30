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
package org.mashti.sina.distribution;

import org.apache.commons.math3.util.FastMath;
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

    @Override
    public Number probability(final Number x) {

        NumericalRangeValidator.validateRange(x, 1, elements_count, true, true);
        return ONE / FastMath.pow(x.doubleValue(), exponent) / harmonic_n_exp;
    }

    @Override
    public Number cumulative(final Number x) {

        NumericalRangeValidator.validateRange(x, ONE, elements_count, true, true);
        return generalizedHarmonic(x.intValue(), exponent) / harmonic_n_exp;
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

        //TODO implement according to : http://mathworld.wolfram.com/ZipfDistribution.html
        throw new UnsupportedOperationException("not implemented");
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
    static double generalizedHarmonic(final int n, final double m) {

        double value = 0;
        for (int k = n; k > 0; --k) {
            value += 1.0 / Math.pow(k, m);
        }
        return value;
    }
}
