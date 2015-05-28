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

import DistLib.t;
import java.util.Random;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class TDistributionTest {

    @Test
    public void testProbability() throws Exception {

        Random random = new Random(654654);
        for (int i = 0; i < 10000; i++) {
            final int df = Math.abs(random.nextInt());
            final TDistribution tt = new TDistribution(df);
            final int i1 = random.nextInt();

            final org.apache.commons.math3.distribution.TDistribution tDistribution = new org.apache.commons.math3.distribution.TDistribution(df);
            final double actual = tDistribution.density(i1);
            final double probability = tt.density(i1).doubleValue();
            final double density = t.density(i1, df);

            assertEquals(actual, probability, 20);
            assertEquals(actual, density, 20);

            final float v = random.nextFloat();
            assertEquals(tDistribution.inverseCumulativeProbability(v), t.quantile(v, df), 20);
        }
    }
}
