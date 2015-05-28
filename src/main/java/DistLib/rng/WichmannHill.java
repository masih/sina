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
/**
 * Wichmann-Hill algorithm for random variates from the 
 * standard uniform distribution, U(0,1).
 * <p>
 *  Wichmann, B. A. and I. D. Hill (1982).
 *  Algorithm AS 183: An efficient and portable
 *  pseudo-random number generator,
 *  Applied Statistics, 31, 188.
 *
 * Created on Apr 16, 2007
 */
package DistLib.rng;

import DistLib.StdUniformRng;

public class WichmannHill implements StdUniformRng {

    int i1_seed;
    int[] i_seed;
    static final int c0 = 30269;
    static final int c1 = 30307;
    static final int c2 = 30323;

    public WichmannHill() {

        i1_seed = 123;
        i_seed = new int[2];
        fixupSeeds();
    }

    public void fixupSeeds() {

        // exclude 0 as seed
        if (i1_seed == 0) i1_seed++;
        for (int j = 0; j < i_seed.length; j++) {
            if (i_seed[j] == 0) i_seed[j]++;
        }
        if (i1_seed >= c0 || i_seed[0] >= c1 || i_seed[1] >= c2) {
            random();
        }
    }

    public double random() {

        i1_seed = i1_seed * 171 % c0;
        i_seed[0] = i_seed[0] * 172 % c1;
        i_seed[1] = i_seed[1] * 170 % c2;
        double value = (double) i1_seed / c0 + (double) i_seed[0] / c1 + (double) i_seed[1] / c2;
        return value - (int) value; // ensure in range [0,1)
    }

}
