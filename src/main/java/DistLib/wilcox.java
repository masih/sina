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
package DistLib;

import java.util.logging.Logger;

/**
 * http://stat.ethz.ch/R-manual/R-patched/library/stats/html/Wilcoxon.html
 * Wrapper of functions for Wilcoxon distribution.
 * <p>
 * This actually the Mann-Whitney Ux statistic.
 */

public class wilcox {

    public static final int WILCOX_MMAX = 50;
    public static final int WILCOX_NMAX = 50;
    private static final Logger log = Logger.getLogger(wilcox.class.getName());
    // table of exact cumulative probabilities
    static private double w[][][] = new double[WILCOX_MMAX][WILCOX_NMAX][];

    /**
     * Cumulative distribution function of the Wilcoxon distribution.
     */
    public static double cumulative(double x, double m, double n) {

        /*!* #ifdef IEEE_754 /*4!*/
        if (Double.isNaN(x) || Double.isNaN(m) || Double.isNaN(n)) return x + m + n;
        if (Double.isInfinite(m) || Double.isInfinite(n)) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //        return Double.NaN;
        }
        /*!* #endif /*4!*/
        roundSizes(m, n);
        if (m <= 0 || n <= 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //        return Double.NaN;
        }
        if (!checkSizesLarge(m, n)) return Double.NaN;
        /*!*   x = floor(x + 0.5); *!*/
        x = java.lang.Math.floor(x + 0.5);
        if (x < 0.0) return 0;
        if (x >= m * n) return 1;
        double p = 0.0;
        for (int i = 0; i <= x; i++)
            p += density(i, m, n);
        return (p);
    }

    /**
     * density function
     * @param x
     * @param m
     * @param n
     * @return density
     */
    public static double density(double x, double m, double n) {

        /*!* #ifdef IEEE_754 /*4!*/
        /* NaNs propagated correctly */
        if (Double.isNaN(x) || Double.isNaN(m) || Double.isNaN(n)) return x + m + n;
        /*!* #endif /*4!*/
        roundSizes(m, n);
        if (m <= 0 || n <= 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //        return Double.NaN;
        }
        if (!checkSizesLarge(m, n)) return Double.NaN;

        /*!*   x = floor(x + 0.5); *!*/
        x = java.lang.Math.floor(x + 0.5);
        if ((x < 0) || (x > m * n)) return 0;
        /*!*   return(cwilcox(x, m, n) / choose(m + n, n)); *!*/
        return (cwilcox((int) x, (int) m, (int) n) / misc.choose(m + n, n));
    }

    /**
     * The quantile function of the Wilcoxon distribution.
     */
    public static double quantile(double x, double m, double n) {

        /*!* #ifdef IEEE_754 /*4!*/
        if (Double.isNaN(x) || Double.isNaN(m) || Double.isNaN(n)) return x + m + n;
        if (Double.isInfinite(x) || Double.isInfinite(m) || Double.isInfinite(n)) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //    	return Double.NaN;
        }
        /*!* #endif /*4!*/

        roundSizes(m, n);
        if (x < 0 || x > 1 || m <= 0 || n <= 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //    	return Double.NaN;
        };
        if (!checkSizesLarge(m, n)) return Double.NaN;

        if (x == 0) return (0.0);
        if (x == 1) return (m * n);
        double p = 0.0;
        double q = 0.0;
        for (;;) {
            /* Don't call cumulative() for efficiency */
            p += density(q, m, n);
            if (p >= x) return (q);
            q++;
        }
    }

    /**
     *    Random variates from the Wilcoxon distribution.
     */
    public static double random(double m, double n) {

        /*!* #ifdef IEEE_754 /*4!*/
        /* NaNs propagated correctly */
        if (Double.isNaN(m) || Double.isNaN(n)) return (m + n);
        /*!* #endif /*4!*/
        roundSizes(m, n);
        if ((m < 0) || (n < 0)) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //        return Double.NaN;
        }
        if ((m == 0) || (n == 0)) return (0);
        double r = 0.0;
        int k = (int) (m + n);
        int[] x = new int[k];
        for (int i = 0; i < k; i++)
            x[i] = i;
        for (int i = 0; i < n; i++) {
            /*!*     j = floor(k * sunif()); *!*/
            int j = (int) java.lang.Math.floor(k * uniform.random());
            r += x[j];
            x[j] = x[--k];
        }
        return (r - n * (n - 1) / 2);
    }

    /**
     * check values for too large and log complaint
     */
    private static boolean checkSizesLarge(final double m, final double n) {

        if (m >= WILCOX_MMAX) {
            log.info("m should be less than %d\n" + WILCOX_MMAX);
            return false;
        }
        if (n >= WILCOX_NMAX) {
            log.info("n should be less than %d\n" + WILCOX_NMAX);
            return false;
        }
        return true;
    }

    /**
     * round sizes to integer
     */
    private static void roundSizes(double m, double n) {

        m = Math.floor(m + 0.5);
        n = Math.floor(n + 0.5);
    }

    /**
     *    The density of the Wilcoxon distribution.
     */
    static private double cwilcox(int k, int m, int n) {

        int u = m * n;
        int c = (int) (u / 2);

        if ((k < 0) || (k > u)) return (0);
        if (k > c) k = u - k;
        int i = m;
        int j = n;
        if (m >= n) {
            i = n;
            j = m;
        }
        if (w[i][j] == null) {
            w[i][j] = new double[c + 1];
            for (int l = 0; l <= c; l++)
                w[i][j][l] = -1;
        }
        if (w[i][j][k] < 0) {
            if ((i == 0) || (j == 0))
                w[i][j][k] = (k == 0) ? 1.0 : 0.0;
            else w[i][j][k] = cwilcox(k - n, m - 1, n) + cwilcox(k, m, n - 1);
        }
        return (w[i][j][k]);
    }
}
