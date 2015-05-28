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
import org.mashti.sina.distribution.ProbabilityDistribution;

/**
 * A Random Number Generator (RNG) where the generated numbers are derived from a given {@link ProbabilityDistribution}.
 * This RNG uses <a href="http://en.wikipedia.org/wiki/Inverse_transform_sampling">Inverse Transform Sampling</a> to generate random numbers:
 * <blockquote>
 * The problem that the inverse transform sampling method solves is as follows:<br/>
 * <ul>
 * <li>Let {@code X} be a random variable whose distribution can be described by the cumulative distribution function {@code F}
 * <li>We want to generate values of {@code X} which are distributed according to this distribution.</li>
 * </ul> The inverse transform sampling method works as follows: <br/>
 * <ol>
 * <li>Generate a random number {@code u} from the standard uniform distribution in the interval {@code [0, 1]}
 * <li>Compute the value {@code x} such that {@code F(x) = u}
 * <li>Take {@code x} to be the random number drawn from the distribution described by {@code F}
 * </ol>
 * </blockquote>
 *
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class RandomNumberGenerator {

    private final ProbabilityDistribution distribution;
    private final Random uniform_random;

    /**
     * Instantiates a non-deterministic random number generator where the generated numbers are distributed according to the given probability distribution.
     *
     * @param distribution the distribution
     */
    public RandomNumberGenerator(final ProbabilityDistribution distribution) {

        this(distribution, new Random());
    }

    /**
     * Instantiates a deterministic random number generator where the generated numbers are distributed according to the given probability distribution.
     *
     * @param distribution the probability distribution
     * @param seed the uniform random generator's seed
     */
    public RandomNumberGenerator(final ProbabilityDistribution distribution, final long seed) {

        this(distribution, new Random(seed));
    }

    /**
     * Instantiates a random number generator where the generated numbers are distributed according to the given probability distribution and the given {@link Random} is used to generate uniform random numbers.
     *
     * @param distribution the probability distribution
     * @param uniform_random a uniform random number generator
     */
    public RandomNumberGenerator(final ProbabilityDistribution distribution, final Random uniform_random) {

        this.distribution = distribution;
        this.uniform_random = uniform_random;
    }

    /**
     * Generates a random number that is distributed according to the given probability distribution.
     *
     * @param distribution the distribution
     * @param uniform_random a uniform random number generator
     * @return a random number derived from the given probability distribution
     */
    public static Number generate(final ProbabilityDistribution distribution, final Random uniform_random) {

        return distribution.quantile(uniform_random.nextDouble());
    }

    /**
     * Generates a non-deterministic random number that is distributed according to the given probability distribution.
     *
     * @param distribution the distribution
     * @return a random number derived from the given probability distribution
     */
    public static Number generate(final ProbabilityDistribution distribution) {

        return distribution.quantile(ThreadLocalRandom.current().nextDouble());
    }

    /**
     * Generates a random number that is distributed according to the given probability distribution.
     *
     * @return a random number derived from the given probability distribution
     */
    public Number generate() {

        return generate(distribution, uniform_random);
    }

    /**
     * Gets the probability distribution.
     *
     * @return the probability distribution
     */
    public ProbabilityDistribution getProbabilityDistribution() {

        return distribution;
    }
}
