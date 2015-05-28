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
/*
 * Created on Apr 17, 2007
 */
package DistLib.rng;

import DistLib.StdUniformRng;

public class MarsagliaMulticarry implements StdUniformRng {

    int i1_seed;
    int[] i_seed;

    static private double i2_32m1 = 2.328306437080797e-10; /* = 1/(2^32 - 1) */

    static private int do32bits(int N) {

        return (N);
    }

    public MarsagliaMulticarry() {

        i1_seed = 123;
        i_seed = new int[1];
        fixupSeeds();
    }

    public void fixupSeeds() {

        if (i1_seed == 0) i1_seed++;
        for (int j = 0; j < i_seed.length; j++) {
            if (i_seed[j] == 0) i_seed[j]++;
        }
    }

    public double random() {

        i1_seed = 36969 * (i1_seed & 0177777) + (i1_seed >> 16);
        i_seed[0] = 18000 * (i_seed[0] & 0177777) + (i_seed[0] >> 16);
        return (do32bits(i1_seed << 16) ^ (i_seed[0] & 0177777)) * i2_32m1; /* in [0,1) */
    }

}
