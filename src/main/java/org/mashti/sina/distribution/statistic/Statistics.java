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

import java.util.Collections;
import java.util.List;
import org.mashti.sina.util.NumericalRangeValidator;

import static org.mashti.sina.distribution.ProbabilityDistribution.ONE_HUNDRED;
import static org.mashti.sina.distribution.ProbabilityDistribution.ZERO;

public class Statistics extends StatisticsStateless {

    private static final long serialVersionUID = -1822453429009595112L;
    private final UniformReservoir reservoir = new UniformReservoir();

    public Statistics() {

    }

    public Statistics(boolean skip_nan) {

        super(skip_nan);
    }

    @Override
    public boolean addSample(final Number value) {

        if (super.addSample(value)) {
            reservoir.update(value);
            return true;
        }
        return false;
    }

    @Override
    public synchronized void reset() {

        super.reset();
        reservoir.reset();
    }

    @Override
    public String toString() {

        return "Statistics [getMax()=" + getMax() + ", getMin()=" + getMin() + ", getMean()=" + getMean() + ", getSampleSize()=" + getSampleSize() + ']';
    }

    public Number getPercentile(final double quantile) {

        NumericalRangeValidator.validateRange(quantile, ZERO, ONE_HUNDRED, true, true);
        final Number percentile;

        final List<Double> snapshot = reservoir.getSnapshot();
        if (snapshot.isEmpty()) {
            percentile = Double.NaN;
        }
        else {
            //TODO inefficient?
            Collections.sort(snapshot);
            final int sample_size = snapshot.size();
            final double position = quantile / 100 * (sample_size + 1);

            if (position < 1) {
                percentile = snapshot.get(0);
            }
            else if (position >= sample_size) {
                percentile = snapshot.get(sample_size - 1);
            }
            else {

                final double lower = snapshot.get((int) position - 1);
                final double upper = snapshot.get((int) position);
                percentile = lower + (position - Math.floor(position)) * (upper - lower);
            }
        }

        return percentile;
    }
}
