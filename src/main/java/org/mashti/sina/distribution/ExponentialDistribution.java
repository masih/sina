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
