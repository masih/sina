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

import java.util.concurrent.TimeUnit;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static org.mashti.sina.distribution.WeibullDistribution.NATURAL_LOGARITHM_OF_TWO;
import static org.mashti.sina.util.NumericalRangeValidator.validateRangeLargerThanZeroExclusive;
import static org.mashti.sina.util.NumericalRangeValidator.validateRangeLargerThanZeroInclusive;
import static org.mashti.sina.util.NumericalRangeValidator.validateRangeZeroToOneInclusive;

/**
 * Implements Exponential probability distribution.
 *
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class ExponentialDistribution implements ProbabilityDistribution {

    private static final long serialVersionUID = -8780132031221192523L;
    private final double scale;

    public ExponentialDistribution(final Number mean) {

        validateRangeLargerThanZeroExclusive(mean);
        scale = mean.doubleValue();
    }

    public static ExponentialDistribution byMean(final long duration, TimeUnit unit) {

        return new ExponentialDistribution(Double.valueOf(TimeUnit.NANOSECONDS.convert(duration, unit)));
    }

    @Override
    public Double density(final Number x) {

        validateRangeLargerThanZeroInclusive(x);
        return exp(-x.doubleValue() / scale) / scale;
    }

    @Override
    public Double cumulative(final Number x) {

        validateRangeLargerThanZeroInclusive(x);
        return ONE - exp(-x.doubleValue() / scale);
    }

    @Override
    public Double quantile(final Number probability) {

        validateRangeZeroToOneInclusive(probability);
        return -scale * log(ONE - probability.doubleValue());
    }

    @Override
    public Double mean() {

        return scale;
    }

    @Override
    public Double median() {

        return scale * NATURAL_LOGARITHM_OF_TWO;
    }

    @Override
    public Double mode() {

        return ZERO;
    }

    @Override
    public Double variance() {

        return pow(scale, TWO);
    }

    @Override
    public Double skewness() {

        return TWO;
    }

    @Override
    public String toString() {

        return new StringBuilder().append("Exponential(mean: ").append(scale).append(")").toString();
    }
}
