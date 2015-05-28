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

import java.io.Serializable;

/**
 * The Interface ProbabilityDistribution.
 *
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public interface ProbabilityDistribution extends Serializable {

    /** The zero. */
    double ZERO = 0.0D;
    /** The half. */
    double HALF = 0.5D;
    /** The one. */
    double ONE = HALF + HALF;
    /** The two. */
    double TWO = ONE + ONE;
    /** The three. */
    double THREE = TWO + ONE;
    /** The ten. */
    double TEN = THREE + THREE + THREE + ONE;
    /** The twelve. */
    double TWELVE = TEN + TWO;
    /** The one hundred. */
    double ONE_HUNDRED = TEN * TEN;

    /**
     * Probability Density Function (PDF).
     *
     * @param x the random variable
     * @return the relative likelihood of the occurrence of the random variable <code>x</code>
     */
    Number density(Number x);

    /**
     * Cumulative Distribution Function (CDF).
     *
     * @param x the random variable
     * @return the likelihood of the occurrence of the random variables less than or equal to <code>x</code>
     */
    Number cumulative(Number x);

    /**
     * Given a probability, calculates the maximum random variable Inverse of {@link #cumulative(Number)}.
     *
     * @param probability the probability
     * @return the number
     */
    Number quantile(Number probability);

    /**
     * Mean.
     *
     * @return the number
     */
    Number mean();

    /**
     * Median.
     *
     * @return the number
     */
    Number median();

    /**
     * Mode.
     *
     * @return the number
     */
    Number mode();

    /**
     * Variance.
     *
     * @return the number
     */
    Number variance();

    /**
     * Skewness.
     *
     * @return the number
     */
    Number skewness();
}
