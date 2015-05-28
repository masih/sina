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

import org.mashti.sina.util.Erf;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.mashti.sina.util.NumericalRangeValidator.validateRangeLargerThanZeroExclusive;
import static org.mashti.sina.util.NumericalRangeValidator.validateRangeZeroToOneInclusive;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class NormalDistribution implements ProbabilityDistribution {

    private static final long serialVersionUID = 3877572847526702145L;
    private static final double SQRT_2 = sqrt(2.0);
    private static final double SQRT_2_PI = sqrt(2 * PI);
    private final double mean;
    private final double sd;

    public NormalDistribution(Number mean, Number standard_deviation) {

        validateRangeLargerThanZeroExclusive(standard_deviation);
        this.mean = mean.doubleValue();
        sd = standard_deviation.doubleValue();
    }

    @Override
    public Number density(final Number x_) {

        double x = x_.doubleValue();
        x = (x - mean) / sd;
        return SQRT_2_PI * exp(-HALF * x * x) / sd;
    }

    @Override
    public Number cumulative(final Number x) {

        final double dev = x.doubleValue() - mean;
        if (abs(dev) > 40 * sd) { return dev < 0 ? 0.0d : 1.0d; }
        return 0.5 * (1 + Erf.erf(dev / (sd * SQRT_2)));
    }

    @Override
    public Number quantile(final Number probability) {

        validateRangeZeroToOneInclusive(probability);
        return mean + sd * SQRT_2 * Erf.erfInv(2 * probability.doubleValue() - 1);
    }

    @Override
    public Number mean() {

        return mean;
    }

    @Override
    public Number median() {

        return mean;
    }

    @Override
    public Number mode() {

        return mean;
    }

    @Override
    public Number variance() {

        return pow(sd, TWO);
    }

    @Override
    public Number skewness() {

        return ZERO;
    }
}
