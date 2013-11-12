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
