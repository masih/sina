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

public class UniformDistribution implements ProbabilityDistribution {

    public static final UniformDistribution ZERO_UNIFORM_DISTRIBUTION = new UniformDistribution(0, 0);
    private final double low;
    private final double high;

    public UniformDistribution(final Number low, final Number high) {

        NumericalRangeValidator.validateNumber(low, high);
        this.low = low.doubleValue();
        this.high = high.doubleValue();
        // Allow high = low
        if (this.high < this.low) { throw new IllegalArgumentException("lower bound must be less than higher bound"); }
    }

    public Number getLow() {

        return low;
    }

    public Number getHigh() {

        return high;
    }

    @Override
    public Number density(final Number x) {

        final double x_d = x.doubleValue();
        return x_d < low || x_d > high ? ZERO : ONE / (high - low);
    }

    @Override
    public Number cumulative(final Number x) {

        final double x_d = x.doubleValue();
        return x_d < low ? ZERO : x_d >= high ? ONE : (x_d - low) / (high - low);
    }

    @Override
    public Number quantile(final Number probability) {

        NumericalRangeValidator.validateRangeZeroToOneInclusive(probability);
        return low + probability.doubleValue() * (high - low);
    }

    @Override
    public Number mean() {

        return (low + high) / TWO;
    }

    @Override
    public Number median() {

        return mean();
    }

    @Override
    public Number mode() {

        return low; // Any value between low to high (inclusive) may be mode
    }

    @Override
    public Number variance() {

        return Math.pow(high - low, TWO) / TWELVE;
    }

    @Override
    public Number skewness() {

        return ZERO;
    }

    @Override
    public String toString() {

        return "Uniform, low: " + low + ", high: " + high;
    }
}
