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

import org.mashti.sina.util.Gamma;
import org.mashti.sina.util.NumericalRangeValidator;

public class TDistribution implements ProbabilityDistribution {

    private final double df;
    private final double df_plus_1_over_2;

    public TDistribution(final Number degrees_of_freedom) {

        NumericalRangeValidator.validateRangeLargerThanZeroExclusive(degrees_of_freedom);
        df = degrees_of_freedom.doubleValue();
        df_plus_1_over_2 = (df + ONE) / TWO;
    }

    @Override
    public Number probability(final Number x) {

        final double x_d = x.doubleValue();
        return Gamma.gamma(df_plus_1_over_2) / (Math.sqrt(df * Math.PI) * Gamma.gamma(df / TWO)) * Math.pow(1 + Math.pow(x_d, TWO) / df, -df_plus_1_over_2);
    }

    @Override
    public Number cumulative(final Number x) {

        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public Number quantile(final Number probability) {

        throw new UnsupportedOperationException("not implemented");
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
