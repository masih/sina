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

import org.junit.Assert;
import org.junit.Test;

import static org.mashti.sina.util.Gamma.gamma;
import static org.mashti.sina.util.Gamma.logGamma;

/**
 * Tests {@link Gamma} function's implementation.
 *
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class GammaTest {

    public static final double DELTA = 0.0d;
    //@formatter:off
    private static final double[][] TEST_INPUT_OUTPUTS = {
            // in                           out logGamma            out gamma
            {Double.NaN, Double.NaN, Double.NaN}, {-1.0, Double.NaN, Double.NaN}, {0.0, Double.NaN, Double.NaN}, {Double.NEGATIVE_INFINITY, Double.NaN, Double.NaN}, {Double.POSITIVE_INFINITY, Double.NaN, Double.NaN}, {1.0, -4.440892098500626E-16, 0.9999999999999996}, {1.654546546, -0.1044578864710679, 0.9008127330111706}, {998877445566332211.987456321, 4.040000631630226E19, Double.POSITIVE_INFINITY}
    };
    //@formatter:on

    /** Tests {@link Gamma#logGamma(double)}. */
    @Test
    public void testLogGamma() {

        for (final double[] args : TEST_INPUT_OUTPUTS) {
            Assert.assertEquals(logGamma(args[0]), args[1], DELTA);
        }
    }

    /** Tests {@link Gamma#gamma(double)}. */
    @Test
    public void testGamma() {

        for (final double[] args : TEST_INPUT_OUTPUTS) {
            Assert.assertEquals(gamma(args[0]), args[2], DELTA);
        }
    }
}
