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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.mashti.sina.util.AtomicDoubleArray;
import org.mashti.sina.util.ThreadLocalRandom;

/**
 * A random sampling reservoir of a stream of {@code long}s. Uses Vitter's Algorithm R to produce a
 * statistically representative sample.
 *
 * @see <a href="http://www.cs.umd.edu/~samir/498/vitter.pdf">Random Sampling with a Reservoir</a>
 */
public class UniformReservoir implements Serializable {

    private static final long serialVersionUID = -2046074870426588279L;
    private static final int DEFAULT_SIZE = 1028;
    private static final int BITS_PER_LONG = 63;
    private final AtomicLong count = new AtomicLong();
    private final AtomicDoubleArray values;

    /**
     * Creates a new {@link UniformReservoir} of 1028 elements, which offers a 99.9% confidence level
     * with a 5% margin of error assuming a normal distribution.
     */
    public UniformReservoir() {

        this(DEFAULT_SIZE);
    }

    /**
     * Creates a new {@link UniformReservoir}.
     *
     * @param size the number of samples to keep in the sampling reservoir
     */
    public UniformReservoir(int size) {

        values = new AtomicDoubleArray(size);
        reset();
    }

    public int size() {

        final long c = count.get();
        final int values_length = values.length();
        if (c > values_length) { return values_length; }
        return (int) c;
    }

    public void update(Number value) {

        update(value.doubleValue());
    }

    public void update(double value) {

        final long c = count.incrementAndGet();
        if (c <= values.length()) {
            values.set((int) c - 1, value);
        }
        else {
            final long r = nextLong(c);
            if (r < values.length()) {
                values.set((int) r, value);
            }
        }
    }

    public List<Double> getSnapshot() {

        final int s = size();
        final List<Double> copy = new ArrayList<Double>(s);
        for (int i = 0; i < s; i++) {
            copy.add(values.get(i));
        }
        return copy;
    }

    public void reset() {

        for (int i = 0; i < values.length(); i++) {
            values.set(i, 0);
        }
        count.set(0);
    }

    /**
     * Get a pseudo-random long uniformly between 0 and n-1. Stolen from
     * {@link java.util.Random#nextInt()}.
     *
     * @param n the bound
     * @return a value select randomly from the range {@code [0..n)}.
     */
    private static long nextLong(long n) {

        long bits, val;
        do {
            bits = ThreadLocalRandom.current().nextLong() & (~(1L << BITS_PER_LONG));
            val = bits % n;
        } while (bits - val + (n - 1) < 0L);
        return val;
    }
}
