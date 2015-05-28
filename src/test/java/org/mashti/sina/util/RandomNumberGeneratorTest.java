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
package org.mashti.sina.util;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import org.mashti.sina.distribution.ExponentialDistribution;
import org.mashti.sina.distribution.ProbabilityDistribution;
import org.mashti.sina.distribution.UniformDistribution;
import org.mashti.sina.distribution.WeibullDistribution;

/**
 * Tests {@link RandomNumberGenerator}.
 *
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class RandomNumberGeneratorTest {

    public static final double DELTA = 0.00001d;

    /** Tests {@link RandomNumberGenerator#RandomNumberGenerator(ProbabilityDistribution)}. */
    @Test
    public void testRandomNumberGeneratorIProbabilityDistribution() {

        final ProbabilityDistribution distribution = new ExponentialDistribution(1D);
        final RandomNumberGenerator rng = new RandomNumberGenerator(distribution);
        Assert.assertEquals(rng.getProbabilityDistribution(), distribution);
        Assert.assertTrue(rng.generate().doubleValue() > 0);
    }

    /** Tests random number generator with probability distribution and fixed seed, and  duration generation. */
    @Test
    public void testRandomNumberGeneratorIProbabilityDistributionLongAndRandomAndDurations() {

        //@formatter:off
        final Object[][] args = {
                {new ExponentialDistribution(1D), 1, 1.3125911792091947, 0.5277697200942817, 0.2328339031183774, 0.40454111905682183, 3.4344204904877125},
                {new ExponentialDistribution(0.5), 456, 2.8452293916157276, 0.8391831940243457, 2.438998315622579, 1.3042599600809817, 0.1926491876803895},
                {new WeibullDistribution(1.5, 180), 88, 213.79416676181947, 217.4846985290254, 161.958560863693, 104.65790054884297, 97.63648580955604},
                {new WeibullDistribution(0.7, 180), 9963, 477.19152415085273, 48.651446228092155, 27.094095227797993, 0.39397769296152974, 288.4787826842495},
                {new UniformDistribution(-88, -2), 222, -24.597490579933293, -55.97113365596071, -26.3725775342321, -19.988249300992976, -29.27905724349791},
                {new UniformDistribution(0.1, 0.2), 645, 0.1696120641901696, 0.11933065687438449, 0.1483114379405135, 0.18573720142363337, 0.16094777322155132}
        };
        //@formatter:on
        int i;
        for (final Object[] arg : args) {
            i = 0;
            final ProbabilityDistribution distribution = (ProbabilityDistribution) arg[i++];
            final int seed = (Integer) arg[i++];
            final RandomNumberGenerator rng_1 = new RandomNumberGenerator(distribution, seed);
            final Random rng_2_uniform_random = new Random(seed);
            final Random uniform_random = new Random(seed);
            final Random uniform_random_duration_nanos = new Random(seed);
            final Random uniform_random_duration = new Random(seed);
            final RandomNumberGenerator rng_2 = new RandomNumberGenerator(distribution, rng_2_uniform_random);
            Assert.assertEquals(rng_1.getProbabilityDistribution(), distribution);
            Assert.assertEquals(rng_2.getProbabilityDistribution(), distribution);

            //            for (int j = 2; j < arg.length; j++) {
            //                final Double outcome = (Double) arg[j];
            //                Assert.assertEquals(rng_1.generate().doubleValue(), outcome, DELTA);
            //
            //                Assert.assertEquals(rng_2.generate().doubleValue(), outcome, DELTA);
            //                Assert.assertEquals(RandomNumberGenerator.generate(distribution, uniform_random).doubleValue(), outcome, DELTA);
            //                final long outcome_as_long = outcome.longValue();
            //            }
        }
    }
}
