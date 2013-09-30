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
    private final double rate;

    public ExponentialDistribution(final Double rate) {

        validateRangeLargerThanZeroExclusive(rate);
        this.rate = rate.doubleValue();
    }

    public static ExponentialDistribution byMean(final long duration, TimeUnit unit) {

        return byMean(Double.valueOf(TimeUnit.NANOSECONDS.convert(duration, unit)));
    }

    public static ExponentialDistribution byMean(final Double mean) {

        validateRangeLargerThanZeroExclusive(mean);
        return new ExponentialDistribution(meanToRate(mean));
    }

    public Double rate() {

        return rate;
    }

    @Override
    public Double probability(final Number x) {

        validateRangeLargerThanZeroInclusive(x);
        return rate * Math.exp(-rate * x.doubleValue());
    }

    @Override
    public Double cumulative(final Number x) {

        validateRangeLargerThanZeroInclusive(x);
        return ONE - Math.exp(-rate * x.doubleValue());
    }

    @Override
    public Double quantile(final Number probability) {

        validateRangeZeroToOneInclusive(probability);
        return -Math.log(ONE - probability.doubleValue()) / rate;
    }

    @Override
    public Double mean() {

        return meanToRate(rate);
    }

    @Override
    public Double median() {

        return mean() * WeibullDistribution.NATURAL_LOGARITHM_OF_TWO;
    }

    @Override
    public Double mode() {

        return ZERO;
    }

    @Override
    public Double variance() {

        return Math.pow(rate, -TWO);
    }

    @Override
    public Double skewness() {

        return TWO;
    }

    @Override
    public String toString() {

        return new StringBuilder().append("Exponential(mean: ").append(mean()).append(")").toString();
    }

    private static double meanToRate(final Double mean) {

        return Math.pow(mean, -ONE);
    }
}
