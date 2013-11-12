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
package org.mashti.sina.util;

import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.sqrt;

/**
 * This is a utility class that provides computation methods related to the
 * error functions.
 *
 * @version $Id: Erf.java 1456905 2013-03-15 11:37:35Z luc $
 */
public class Erf {

    private static final double[] ERFCS = {0.0, -.49046121234691808039984544033376e-1, -.14226120510371364237824741899631e+0, +.10035582187599795575754676712933e-1, -.57687646997674847650827025509167e-3, +.27419931252196061034422160791471e-4, -.11043175507344507604135381295905e-5,
                    +.38488755420345036949961311498174e-7, -.11808582533875466969631751801581e-8, +.32334215826050909646402930953354e-10, -.79910159470045487581607374708595e-12, +.17990725113961455611967245486634e-13, -.37186354878186926382316828209493e-15, +.71035990037142529711689908394666e-17,
                    -.12612455119155225832495424853333e-18, +.20916406941769294369170500266666e-20, -.32539731029314072982364160000000e-22, +.47668672097976748332373333333333e-24, -.65980120782851343155199999999999e-26, +.86550114699637626197333333333333e-28, -.10788925177498064213333333333333e-29,
                    +.12811883993017002666666666666666e-31};
    private static final double[] ERC_2_CS = {0.0, -.6960134660230950112739150826197e-1, -.4110133936262089348982212084666e-1, +.3914495866689626881561143705244e-2, -.4906395650548979161280935450774e-3, +.7157479001377036380760894141825e-4, -.1153071634131232833808232847912e-4,
                    +.1994670590201997635052314867709e-5, -.3642666471599222873936118430711e-6, +.6944372610005012589931277214633e-7, -.1371220902104366019534605141210e-7, +.2788389661007137131963860348087e-8, -.5814164724331161551864791050316e-9, +.1238920491752753181180168817950e-9,
                    -.2690639145306743432390424937889e-10, +.5942614350847910982444709683840e-11, -.1332386735758119579287754420570e-11, +.3028046806177132017173697243304e-12, -.6966648814941032588795867588954e-13, +.1620854541053922969812893227628e-13, -.3809934465250491999876913057729e-14,
                    +.9040487815978831149368971012975e-15, -.2164006195089607347809812047003e-15, +.5222102233995854984607980244172e-16, -.1269729602364555336372415527780e-16, +.3109145504276197583836227412951e-17, -.7663762920320385524009566714811e-18, +.1900819251362745202536929733290e-18,
                    -.4742207279069039545225655999965e-19, +.1189649200076528382880683078451e-19, -.3000035590325780256845271313066e-20, +.7602993453043246173019385277098e-21, -.1935909447606872881569811049130e-21, +.4951399124773337881000042386773e-22, -.1271807481336371879608621989888e-22,
                    +.3280049600469513043315841652053e-23, -.8492320176822896568924792422399e-24, +.2206917892807560223519879987199e-24, -.5755617245696528498312819507199e-25, +.1506191533639234250354144051199e-25, -.3954502959018796953104285695999e-26, +.1041529704151500979984645051733e-26,
                    -.2751487795278765079450178901333e-27, +.7290058205497557408997703680000e-28, -.1936939645915947804077501098666e-28, +.5160357112051487298370054826666e-29, -.1378419322193094099389644800000e-29, +.3691326793107069042251093333333e-30, -.9909389590624365420653226666666e-31,
                    +.2666491705195388413323946666666e-31};
    private static final double[] ERFCCS = {0.0, +.715179310202924774503697709496e-1, -.265324343376067157558893386681e-1, +.171115397792085588332699194606e-2, -.163751663458517884163746404749e-3, +.198712935005520364995974806758e-4, -.284371241276655508750175183152e-5,
                    +.460616130896313036969379968464e-6, -.822775302587920842057766536366e-7, +.159214187277090112989358340826e-7, -.329507136225284321486631665072e-8, +.722343976040055546581261153890e-9, -.166485581339872959344695966886e-9, +.401039258823766482077671768814e-10,
                    -.100481621442573113272170176283e-10, +.260827591330033380859341009439e-11, -.699111056040402486557697812476e-12, +.192949233326170708624205749803e-12, -.547013118875433106490125085271e-13, +.158966330976269744839084032762e-13, -.472689398019755483920369584290e-14,
                    +.143587337678498478672873997840e-14, -.444951056181735839417250062829e-15, +.140481088476823343737305537466e-15, -.451381838776421089625963281623e-16, +.147452154104513307787018713262e-16, -.489262140694577615436841552532e-17, +.164761214141064673895301522827e-17,
                    -.562681717632940809299928521323e-18, +.194744338223207851429197867821e-18, -.682630564294842072956664144723e-19, +.242198888729864924018301125438e-19, -.869341413350307042563800861857e-20, +.315518034622808557122363401262e-20, -.115737232404960874261239486742e-20,
                    +.428894716160565394623737097442e-21, -.160503074205761685005737770964e-21, +.606329875745380264495069923027e-22, -.231140425169795849098840801367e-22, +.888877854066188552554702955697e-23, -.344726057665137652230718495566e-23, +.134786546020696506827582774181e-23,
                    -.531179407112502173645873201807e-24, +.210934105861978316828954734537e-24, -.843836558792378911598133256738e-25, +.339998252494520890627359576337e-25, -.137945238807324209002238377110e-25, +.563449031183325261513392634811e-26, -.231649043447706544823427752700e-26,
                    +.958446284460181015263158381226e-27, -.399072288033010972624224850193e-27, +.167212922594447736017228709669e-27, -.704599152276601385638803782587e-28, +.297976840286420635412357989444e-28, -.126252246646061929722422632994e-28, +.539543870454248793985299653154e-29,
                    -.238099288253145918675346190062e-29, +.109905283010276157359726683750e-29, -.486771374164496572732518677435e-30, +.152587726411035756763200828211e-30};
    private static final double SQRTPI = 1.77245385090551602729816748334115e0;

    /**
     * Default constructor.  Prohibit instantiation.
     */
    private Erf() {

    }

    /**
     *
     *This method evaluates the n-term Chebyshev series cs at x.
     *It is a Java translation of the FORTRAN
     *routine dcsevl written by W. Fullerton of LANL.  The FORTRAN
     *version is part of the SLATEC library of numerical analysis
     *routines.<p>
     *Steve Verrill translated the FORTRAN code (updated 5/1/92)
     *into Java.  This translation was performed on February 22, 2002.
     *
     *@param   x    Value at which the series is to be evaluated
     *@param   cs   Array of n terms of a Chebyshev series.  In evaluating
     *              cs, only half the first coefficient is summed.
     *@param   n    Number of terms in array cs (excludes the term
     *              in the 0th spot)
     *
     *@version .5 --- February 22, 2002
     *
     */

    public static double dcsevl(double x, double cs[], int n) {

        double b0, b1, b2, twox, ans, onepl;
        int i, ni;

        // SLATEC's d1mach(4) for IEEE 754 arithmetic should be
        // 2 x 2^{-53} = 2.22e-16.

        final double d1mach4 = 2.22e-16;

        onepl = 1.0 + d1mach4;

        if (n < 1) {

            System.out.print("\n\nERROR: The number of terms for dcsevl" + " was less than 1.\n\n");

            System.exit(0);

        }

        if (n > 1000) {

            System.out.print("\n\nERROR: The number of terms for dcsevl" + " was greater than 1000.\n\n");

            System.exit(0);

        }

        if (abs(x) > onepl) {

            System.out.print("\n\nERROR: The x for dcsevl" + " was outside the interval (-1,1).\n\n");

            System.exit(0);

        }

        // We must initialize b2.
        // Java doesn't know that the for loop will go
        // (it doesn't know that we have excluded [above]
        // the case in which n < 1)
        // so it complains that b2 might not have been initialized
        // when we use it below the for loop

        b2 = 0.0;

        b1 = 0.0;
        b0 = 0.0;
        twox = 2.0 * x;

        for (i = 1; i <= n; i++) {

            b2 = b1;
            b1 = b0;
            ni = n + 1 - i;
            b0 = twox * b1 - b2 + cs[ni];
        }

        ans = 0.5 * (b0 - b2);

        return ans;

    }

    /**
     *
     *This method calculates the double precision error function.
     *It is a Java translation of the FORTRAN
     *routine derf written by W. Fullerton of LANL.  The FORTRAN
     *version is part of the SLATEC library of numerical analysis
     *routines.<p>
     *Steve Verrill translated the FORTRAN code (updated 6/18/92)
     *into Java.  This translation was performed on February 21, 2002.
     *
     *@param   x
     *
     *@version .5 --- February 21, 2002
     *
     */

    public static double erf(double x) {

        // SLATEC's d1mach(3) for IEEE 754 arithmetic should be
        // 2^{-53} = 1.11e-16.

        final double d1mach3 = 1.11e-16;

        // eta0 = .1*d1mach(3)

        final double eta0 = 1.11e-17;

        double y, ans;

        double xbig, sqeps;

        int nterf;

        nterf = initds(ERFCS, 21, eta0);

        xbig = sqrt(-log(SQRTPI * d1mach3));
        sqeps = sqrt(2.0 * d1mach3);

        y = abs(x);

        if (y <= 1.0) {

            // ERF(X) = 1.0 - ERFC(X)  FOR  -1.0 .LE. X .LE. 1.0

            if (y <= sqeps) {

                ans = 2.0 * x * x / SQRTPI;

            }
            else {

                ans = x * (1.0 + dcsevl(2.0 * x * x - 1.0, ERFCS, nterf));

            }

            return ans;

        }

        if (y <= xbig) {
            ans = sign(1.0 - erfc(y), x);
        }
        else {
            ans = sign(1.0, x);
        }

        return ans;

    }

    /**
     *
     *This method calculates the double precision
     *complementary error function.
     *It is a Java translation of the FORTRAN
     *routine derfc written by W. Fullerton of LANL.  The FORTRAN
     *version is part of the SLATEC library of numerical analysis
     *routines.<p>
     *Steve Verrill translated the FORTRAN code (updated 6/18/92)
     *into Java.  This translation was performed on February 22, 2002.
     *
     *@param   x
     *
     *@version .5 --- February 22, 2002
     *
     */

    public static double erfc(double x) {

        // SLATEC's d1mach(3) for IEEE 754 arithmetic should be
        // 2^{-53} = 1.11e-16.

        final double d1mach3 = 1.11e-16;

        // eta0 = .1*d1mach(3)

        final double eta0 = 1.11e-17;

        double ans, y;

        double sqeps, xsml, txmax, xmax;

        int nterf, nterfc, nterc2;

        nterf = initds(ERFCS, 21, eta0);
        nterfc = initds(ERFCCS, 59, eta0);
        nterc2 = initds(ERC_2_CS, 49, eta0);

        sqeps = sqrt(2.0 * d1mach3);

        xsml = -sqrt(-log(SQRTPI * d1mach3));

        // For IEEE 754 arithmetic d1mach(1) = 2^{-1022}
        // log(d1mach(1)) = -1022

        txmax = sqrt(-(log(SQRTPI) - 1022));

        xmax = txmax - 0.5 * log(txmax) / txmax - 0.01;

        if (x <= xsml) {

            // ERFC(X) = 1.0 - ERF(X)  FOR  X .LT. XSML

            ans = 2.0;

            return ans;

        }

        if (x > xmax) {

            //         System.out.print("\n\nx is so big that derfc underflows" +
            //         "\n\n");

            ans = 0.0;

            return ans;

        }

        y = abs(x);

        if (y <= 1.0) {

            // ERFC(X) = 1.0 - ERF(X)  FOR ABS(X) .LE. 1.0

            if (y < sqeps) {

                ans = 1.0 - 2.0 * x / SQRTPI;

            }
            else {

                ans = 1.0 - x * (1.0 + dcsevl(2.0 * x * x - 1.0, ERFCS, nterf));

            }

            return ans;

        }

        // ERFC(X) = 1.0 - ERF(X)  FOR  1.0 .LT. ABS(X) .LE. XMAX

        y = y * y;

        if (y <= 4.0) {

            ans = exp(-y) / abs(x) * (0.5 + dcsevl((8.0 / y - 5.0) / 3.0, ERC_2_CS, nterc2));

        }
        else {

            ans = exp(-y) / abs(x) * (0.5 + dcsevl(8.0 / y - 1.0, ERFCCS, nterfc));

        }

        if (x < 0.0) ans = 2.0 - ans;

        return ans;

    }

    /**
     *
     *This method determines the number of terms needed
     *in an orthogonal polynomial series so that it meets a specified
     *accuracy.
     *It is a Java translation of the FORTRAN
     *routine initds written by W. Fullerton of LANL.  The FORTRAN
     *version is part of the SLATEC library of numerical analysis
     *routines.<p>
     *Steve Verrill translated the FORTRAN code (updated 3/15/90)
     *into Java.  This translation was performed on February 22, 2002.
     *
     *@param   os    Double precision array of nos coefficients in an
     *               orthogonal series
     *@param   nos   Number of coefficients in os
     *@param   eta   Scalar containing the
     *               requested accuracy of the series
     *
     *@version .5 --- February 22, 2002
     *
     */

    public static int initds(double os[], int nos, double eta) {

        double err;

        int ii, i;

        if (nos < 1) { throw new ArithmeticException("\n\nERROR: The number of coefficients for initds was less than 1."); }

        // We must initialize i.
        // Java doesn't know that the for loop will go
        // (it doesn't know that we have excluded [above]
        // the case in which nos < 1)
        // so it complains that i might not have been initialized
        // when we use it below the for loop

        i = nos;

        err = 0.0;

        for (ii = 1; ii <= nos; ii++) {

            i = nos + 1 - ii;

            err += abs(os[i]);

            if (err > eta) break;

        }

        if (i == nos) { throw new ArithmeticException("the Chebyshev series is too short for the specified accuracy"); }

        return i;

    }

    /**
     * Returns the inverse erf.
     * <p>
     * This implementation is described in the paper:
     * <a href="http://people.maths.ox.ac.uk/gilesm/files/gems_erfinv.pdf">Approximating
     * the erfinv function</a> by Mike Giles, Oxford-Man Institute of Quantitative Finance,
     * which was published in GPU Computing Gems, volume 2, 2010.
     * The source code is available <a href="http://gpucomputing.net/?q=node/1828">here</a>.
     * </p>
     * @param x the value
     * @return t such that x = erf(t)
     * @since 3.2
     */
    public static double erfInv(final double x) {

        // beware that the logarithm argument must be
        // commputed as (1.0 - x) * (1.0 + x),
        // it must NOT be simplified as 1.0 - x * x as this
        // would induce rounding errors near the boundaries +/-1
        double w = -log((1.0 - x) * (1.0 + x));
        double p;

        if (w < 6.25) {
            w = w - 3.125;
            p = -3.6444120640178196996e-21;
            p = -1.685059138182016589e-19 + p * w;
            p = 1.2858480715256400167e-18 + p * w;
            p = 1.115787767802518096e-17 + p * w;
            p = -1.333171662854620906e-16 + p * w;
            p = 2.0972767875968561637e-17 + p * w;
            p = 6.6376381343583238325e-15 + p * w;
            p = -4.0545662729752068639e-14 + p * w;
            p = -8.1519341976054721522e-14 + p * w;
            p = 2.6335093153082322977e-12 + p * w;
            p = -1.2975133253453532498e-11 + p * w;
            p = -5.4154120542946279317e-11 + p * w;
            p = 1.051212273321532285e-09 + p * w;
            p = -4.1126339803469836976e-09 + p * w;
            p = -2.9070369957882005086e-08 + p * w;
            p = 4.2347877827932403518e-07 + p * w;
            p = -1.3654692000834678645e-06 + p * w;
            p = -1.3882523362786468719e-05 + p * w;
            p = 0.0001867342080340571352 + p * w;
            p = -0.00074070253416626697512 + p * w;
            p = -0.0060336708714301490533 + p * w;
            p = 0.24015818242558961693 + p * w;
            p = 1.6536545626831027356 + p * w;
        }
        else if (w < 16.0) {
            w = sqrt(w) - 3.25;
            p = 2.2137376921775787049e-09;
            p = 9.0756561938885390979e-08 + p * w;
            p = -2.7517406297064545428e-07 + p * w;
            p = 1.8239629214389227755e-08 + p * w;
            p = 1.5027403968909827627e-06 + p * w;
            p = -4.013867526981545969e-06 + p * w;
            p = 2.9234449089955446044e-06 + p * w;
            p = 1.2475304481671778723e-05 + p * w;
            p = -4.7318229009055733981e-05 + p * w;
            p = 6.8284851459573175448e-05 + p * w;
            p = 2.4031110387097893999e-05 + p * w;
            p = -0.0003550375203628474796 + p * w;
            p = 0.00095328937973738049703 + p * w;
            p = -0.0016882755560235047313 + p * w;
            p = 0.0024914420961078508066 + p * w;
            p = -0.0037512085075692412107 + p * w;
            p = 0.005370914553590063617 + p * w;
            p = 1.0052589676941592334 + p * w;
            p = 3.0838856104922207635 + p * w;
        }
        else if (!Double.isInfinite(w)) {
            w = sqrt(w) - 5.0;
            p = -2.7109920616438573243e-11;
            p = -2.5556418169965252055e-10 + p * w;
            p = 1.5076572693500548083e-09 + p * w;
            p = -3.7894654401267369937e-09 + p * w;
            p = 7.6157012080783393804e-09 + p * w;
            p = -1.4960026627149240478e-08 + p * w;
            p = 2.9147953450901080826e-08 + p * w;
            p = -6.7711997758452339498e-08 + p * w;
            p = 2.2900482228026654717e-07 + p * w;
            p = -9.9298272942317002539e-07 + p * w;
            p = 4.5260625972231537039e-06 + p * w;
            p = -1.9681778105531670567e-05 + p * w;
            p = 7.5995277030017761139e-05 + p * w;
            p = -0.00021503011930044477347 + p * w;
            p = -0.00013871931833623122026 + p * w;
            p = 1.0103004648645343977 + p * w;
            p = 4.8499064014085844221 + p * w;
        }
        else {
            // this branch does not appears in the original code, it
            // was added because the previous branch does not handle
            // x = +/-1 correctly. In this case, w is positive infinity
            // and as the first coefficient (-2.71e-11) is negative.
            // Once the first multiplication is done, p becomes negative
            // infinity and remains so throughout the polynomial evaluation.
            // So the branch above incorrectly returns negative infinity
            // instead of the correct positive infinity.
            p = Double.POSITIVE_INFINITY;
        }

        return p * x;

    }

    /**
     *
     *<p>
     *This method implements the FORTRAN sign (not sin) function.
     *See the code for details.
     *
     *Created by Steve Verrill, March 1997.
     *
     *@param  a   a
     *@param  b   b
     *
     */

    private static double sign(double a, double b) {

        return b < 0.0 ? -abs(a) : abs(a);
    }
}
