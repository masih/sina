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

import java.util.Random;
import org.junit.Test;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class ZipfDistributionTest {

    @Test
    public void testProbability() throws Exception {

    }

    @Test
    public void testCumulative() throws Exception {

    }

    @Test
    public void testQuantile() throws Exception {

        ProbabilityDistribution distribution = new ZipfDistribution(10, 1);
        Random random = new Random(5421);
        for (int i = 0; i < 100; i++) {

            System.out.println(distribution.quantile(random.nextDouble()));
        }

    }

    @Test
    public void testMean() throws Exception {

    }

    @Test
    public void testMode() throws Exception {

    }

    @Test
    public void testSkewness() throws Exception {

    }

    @Test
    public void testGeneralizedHarmonic() throws Exception {

    }
}
