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

import org.junit.Assert;
import org.junit.Test;

import static org.mashti.sina.distribution.ProbabilityDistribution.HALF;
import static org.mashti.sina.distribution.ProbabilityDistribution.ONE;

/**
 * Tests {@link ExponentialDistribution}.
 *
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class ExponentialDistributionTest {

    public static final double DELTA = 0.0d;
    //@formatter:off
    private static final double[][] TEST_INPUT_OUTPUT = {
            //rate, prob. & cumulative in, prob. out, cumulative out, quantile in, quantile out, median, mode, skewness, variance
            {0.0011261261261261261, 654.0, 5.391815429000562E-4, 0.5212067899047501, 0.654, 942.4490554848786, 615.5146963372314, 0.0, 2.0, 788544.0}
    };
    //@formatter:on

    /** Tests {@link ExponentialDistribution#ExponentialDistribution(Double)}. */
    @Test
    public void testExponentialDistribution() {

        final Double[] bad_args = {Double.NEGATIVE_INFINITY, -Double.MAX_VALUE, -1.0, -0.0, +0.0, Double.NaN};
        for (final Double bad_arg : bad_args) {
            try {
                new ExponentialDistribution(bad_arg);
                Assert.fail();
            }
            catch (final IllegalArgumentException e) {
                continue;
            }
        }
        new ExponentialDistribution(HALF);
        new ExponentialDistribution(ONE / HALF);
    }

    /** Tests {@link ExponentialDistribution#byMean(Double)}. */
    @Test
    public void testByMeanNumber() {

        final Double[] bad_args = {Double.NEGATIVE_INFINITY, -Double.MAX_VALUE, -1.0, -0.0, +0.0, Double.NaN};
        for (final Double bad_arg : bad_args) {
            try {
                ExponentialDistribution.byMean(bad_arg);
                Assert.fail();
            }
            catch (final IllegalArgumentException e) {
                continue;
            }
        }
        ExponentialDistribution.byMean(HALF);
        ExponentialDistribution.byMean(1.0 / HALF);
    }

    /** Tests rate, mean probability, cumulative, quantile, median,mode, skewness and variance. */
    @Test
    public void testCalculations() {

        for (final double[] args : TEST_INPUT_OUTPUT) {
            int i = 0;
            final double rate = args[i++];
            final ExponentialDistribution distribution = new ExponentialDistribution(rate);
            Assert.assertEquals(distribution.rate().doubleValue(), rate, DELTA);
            Assert.assertEquals(distribution.mean().doubleValue(), 1 / rate, DELTA);
            final double x = args[i++];
            Assert.assertEquals(distribution.probability(x).doubleValue(), args[i++], DELTA);
            Assert.assertEquals(distribution.cumulative(x).doubleValue(), args[i++], DELTA);
            Assert.assertEquals(distribution.quantile(args[i++]).doubleValue(), args[i++], DELTA);
            Assert.assertEquals(distribution.median().doubleValue(), args[i++], DELTA);
            Assert.assertEquals(distribution.mode().doubleValue(), args[i++], DELTA);
            Assert.assertEquals(distribution.skewness().doubleValue(), args[i++], DELTA);
            Assert.assertEquals(distribution.variance().doubleValue(), args[i++], DELTA);
        }
    }
}
