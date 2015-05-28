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

import org.mashti.sina.distribution.statistic.StatisticsStateless;
import org.mashti.sina.util.Gamma;
import org.mashti.sina.util.NumericalRangeValidator;

public class WeibullDistribution implements ProbabilityDistribution {

    static final double NATURAL_LOGARITHM_OF_TWO = Math.log(TWO);
    private final double shape;
    private final double scale;

    public WeibullDistribution(final Number shape, final Number scale) {

        NumericalRangeValidator.validateRangeLargerThanZeroExclusive(shape);
        NumericalRangeValidator.validateRangeLargerThanZeroExclusive(scale);
        this.shape = shape.doubleValue();
        this.scale = scale.doubleValue();
    }

    public static WeibullDistribution byMean(final Number shape, final Number mean) {

        NumericalRangeValidator.validateRangeLargerThanZeroExclusive(shape);
        NumericalRangeValidator.validateRangeLargerThanZeroExclusive(mean);
        final Number scale = calculateScale(shape, mean);
        return new WeibullDistribution(shape, scale);
    }

    public Number getShape() {

        return shape;
    }

    public Number getScale() {

        return scale;
    }

    @Override
    public Number density(final Number x) {

        NumericalRangeValidator.validateRangeLargerThanZeroInclusive(x);
        final double x_by_scale = x.doubleValue() / scale;
        return shape / scale * Math.pow(x_by_scale, shape - ONE) * Math.exp(-Math.pow(x_by_scale, shape));
    }

    @Override
    public Number cumulative(final Number x) {

        NumericalRangeValidator.validateRangeLargerThanZeroInclusive(x);
        final double x_by_scale = x.doubleValue() / scale;
        return 1 - Math.exp(-Math.pow(x_by_scale, shape));
    }

    @Override
    public Number quantile(final Number probability) {

        NumericalRangeValidator.validateRangeZeroToOneInclusive(probability);
        return scale * Math.pow(-Math.log(ONE - probability.doubleValue()), ONE / shape);
    }

    @Override
    public Number mean() {

        return scale * Gamma.gamma(ONE + ONE / shape);
    }

    @Override
    public Number median() {

        return scale * Math.pow(NATURAL_LOGARITHM_OF_TWO, ONE / shape);
    }

    @Override
    public Number mode() {

        return shape == ONE ? ZERO : scale * Math.pow((shape - ONE) / shape, ONE / shape);
    }

    @Override
    public Number variance() {

        return Math.pow(scale, TWO) * Gamma.gamma(ONE + TWO / shape) - Math.pow(mean().doubleValue(), TWO);
    }

    @Override
    public Number skewness() {

        final double mean = mean().doubleValue();
        final double sd = StatisticsStateless.standardDeviation(this).doubleValue();
        return (Gamma.gamma(ONE + TWO / shape) * Math.pow(scale, THREE) - THREE * mean * Math.pow(sd, TWO) - Math.pow(mean, THREE)) / Math.pow(sd, THREE);
    }

    @Override
    public String toString() {

        return "Weibull(shape: " + shape + ", scale: " + scale + ")";
    }

    private static double calculateScale(final Number shape, final Number mean) {

        return mean.doubleValue() / Gamma.gamma(ONE + ONE / shape.doubleValue());
    }
}
