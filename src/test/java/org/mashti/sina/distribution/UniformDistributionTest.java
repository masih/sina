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

import org.junit.Assert;
import org.junit.Test;

import static org.mashti.sina.distribution.ProbabilityDistribution.HALF;
import static org.mashti.sina.distribution.ProbabilityDistribution.ONE;

/**
 * Tests {@link UniformDistribution}.
 *
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class UniformDistributionTest {

    public static final double DELTA = 0.0d;
    //@formatter:off
    private static final double[][] TEST_INPUT_OUTPUT = {
            {0.8842, 852456, 426228.4421, 1.5, 1.1730822907450489E-6, 7.223840746408012E-7, 2.4778507908955887E-6, 2.9964565828880207, 426228.4421, 0.8842, 0.0, 6.055664370446595E10},
            {1.5, 50, 25.75, 21.0, 0.020618556701030927, 0.4020618556701031, 0.012691650910201193, 2.115545069144758, 25.75, 1.5, 0.0, 196.02083333333334},
            {-1.2, -1.0, -1.1, 0.30000000000000004, 0.0, 1.0, 0.012691650910201193, -1.1974616698179597, -1.1, -1.2, 0.0, 0.003333333333333332},
            {-1.0, -1.0, -1.0, 11.0, 0.0, 1.0, 0.012691650910201193, -1.0, -1.0, -1.0, 0.0, 0.0}
    };

    /** Tests {@link UniformDistribution#UniformDistribution(Number, Number)}. */
    @Test
    public void testUniformDistributionDurationDuration() {

        final Number[][] bad_args = {{Double.NaN, Double.NaN}, {Double.NaN, Double.NEGATIVE_INFINITY}, {Double.NEGATIVE_INFINITY, Double.NaN}, {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}, {5.7, 5.6}, {-55.1, -56.0}};
        for (final Number[] bad_arg : bad_args) {
            try {
                new UniformDistribution(bad_arg[0], bad_arg[1]);
                Assert.fail();
            }
            catch (final IllegalArgumentException e) {
                continue;
            }
        }

        final Number[][] good_args = {{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY}, {Double.MAX_VALUE, Double.MAX_VALUE}, {ONE, ONE}, {HALF, HALF}, {-HALF, -HALF}, {-HALF, ONE}};
        for (final Number[] good_arg : good_args) {
            final Number low = good_arg[0].doubleValue();
            final Number scale = good_arg[1];
            final UniformDistribution test_dist = new UniformDistribution(low, scale);
            Assert.assertEquals(test_dist.getLow().doubleValue(), low);
            Assert.assertEquals(test_dist.getHigh().doubleValue(), scale);
        }
    }
    //@formatter:on

    /** Tests rate, mean probability, cumulative, quantile, median,mode, skewness and variance. */
    @Test
    public void testCalculations() {

        for (final double[] args : TEST_INPUT_OUTPUT) {
            int i = 0;
            final double low = args[i++];
            final double high = args[i++];
            final UniformDistribution distribution = new UniformDistribution(low, high);
            Assert.assertEquals(distribution.getLow().doubleValue(), low, DELTA);
            Assert.assertEquals(distribution.getHigh().doubleValue(), high, DELTA);
            Assert.assertEquals(distribution.mean().doubleValue(), args[i++], DELTA);
            final double x = args[i++];
            Assert.assertEquals(distribution.density(x).doubleValue(), args[i++], DELTA);
            Assert.assertEquals(distribution.cumulative(x).doubleValue(), args[i++], DELTA);
            Assert.assertEquals(distribution.quantile(args[i++]).doubleValue(), args[i++], DELTA);
            Assert.assertEquals(distribution.median().doubleValue(), args[i++], DELTA);
            Assert.assertEquals(distribution.mode().doubleValue(), args[i++], DELTA);
            Assert.assertEquals(distribution.skewness().doubleValue(), args[i++], DELTA);
            Assert.assertEquals(distribution.variance().doubleValue(), args[i++], DELTA);
        }
    }

}
