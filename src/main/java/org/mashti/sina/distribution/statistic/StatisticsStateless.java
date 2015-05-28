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
package org.mashti.sina.distribution.statistic;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import org.mashti.sina.distribution.ProbabilityDistribution;
import org.mashti.sina.distribution.TDistribution;
import org.mashti.sina.util.AtomicNumber;
import org.mashti.sina.util.NumericalRangeValidator;

public class StatisticsStateless implements Serializable {

    public static final Double CONFIDENCE_LEVEL_95_PERCENT = 0.95D;
    private static final long serialVersionUID = -1031882992294342289L;
    private final AtomicLong sample_size;
    private final AtomicNumber min;
    private final AtomicNumber max;
    private final AtomicNumber sum;
    private final AtomicNumber sum_of_squares;
    private final boolean skip_nan;

    public StatisticsStateless() {

        this(false);
    }

    public StatisticsStateless(boolean skip_nan) {

        this.skip_nan = skip_nan;

        sample_size = new AtomicLong(0);
        min = new AtomicNumber(Double.NaN);
        max = new AtomicNumber(Double.NaN);
        sum = new AtomicNumber(Double.NaN);
        sum_of_squares = new AtomicNumber(Double.NaN);
    }

    public static Number arithmeticMean(final Number sum, final Number sample_size) {

        return sum.doubleValue() / sample_size.doubleValue();
    }

    public static final Number confidenceInterval(final Long sample_size, final Number standard_deviation, final Number conf_level) {

        NumericalRangeValidator.validateRangeLargerThanZeroExclusive(conf_level);
        NumericalRangeValidator.validateRangeLargerThanZeroInclusive(sample_size);

        final double confidence_interval;
        if (sample_size <= 1 || NumericalRangeValidator.hasNaN(standard_deviation)) {
            confidence_interval = Double.NaN;
        }
        else {
            final long df = sample_size - 1; // calculate degree of freedom
            final double probability = 1 - (1 - conf_level.doubleValue()) / 2; // calculate equivalent probability
            confidence_interval = new TDistribution(df).quantile(probability).doubleValue() * standard_deviation.doubleValue() / Math.sqrt(sample_size);
        }

        return confidence_interval;
    }

    public static Number standardDeviation(final Number sum, final Number sum_of_squares, final Long sample_size) {

        return Math.sqrt((sample_size * sum_of_squares.doubleValue() - squared(sum)) / (sample_size * (sample_size - 1)));
    }

    public static Number standardDeviation(final ProbabilityDistribution distribution) {

        return Math.sqrt(distribution.variance().doubleValue());
    }

    public boolean addSample(final Number value) {

        if (skip_nan && Double.isNaN(value.doubleValue())) {
            return false;
        }

        final long size = sample_size.incrementAndGet();
        if (size == 1) {
            min.set(value);
            max.set(value);
            sum.set(value);
            sum_of_squares.set(squared(value));
        }
        else {
            min.setIfSmallerAndGet(value);
            max.setIfGreaterAndGet(value);
            sum.addAndGet(value);
            sum_of_squares.addAndGet(squared(value));
        }
        return true;
    }

    public void addSamples(final Number[] samples) {

        for (final Number sample : samples) {
            addSample(sample);
        }
    }

    public synchronized void reset() {

        sample_size.set(0);
        min.set(Double.NaN);
        max.set(Double.NaN);
        sum.set(Double.NaN);
        sum_of_squares.set(Double.NaN);
    }

    public Number getMean() {

        return arithmeticMean(sum.get(), sample_size.get());
    }

    public Number getSum() {

        return sum.get();
    }

    public Number getSumOfSquares() {

        return sum_of_squares.get();
    }

    public Long getSampleSize() {

        return sample_size.get();
    }

    public Number getMax() {

        return max.get();
    }

    public Number getMin() {

        return min.get();
    }

    public Number getStandardDeviation() {

        return standardDeviation(sum.get(), sum_of_squares.get(), sample_size.get());
    }

    public Number getConfidenceInterval(final Number confidence_level) {

        return confidenceInterval(sample_size.get(), getStandardDeviation(), confidence_level);
    }

    public Number getConfidenceInterval95Percent() {

        return getConfidenceInterval(CONFIDENCE_LEVEL_95_PERCENT);
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder("StatisticsStateless{");
        sb.append("sample_size=").append(sample_size);
        sb.append(", min=").append(min);
        sb.append(", mean=").append(getMean());
        sb.append(", max=").append(max);
        sb.append(", sum=").append(sum);
        sb.append(", sum_of_squares=").append(sum_of_squares);
        sb.append('}');
        return sb.toString();
    }

    protected static double squared(final Number value) {

        return Math.pow(value.doubleValue(), ProbabilityDistribution.TWO);
    }
}
