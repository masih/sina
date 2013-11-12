package DistLib;

/* data translated from C using perl script translate.pl */
/* script version 0.00                               */

public class beta {

    static double zero = 0.0;
    static double fpu = 3e-308;
    static double acu_min = 1e-300;
    static double lower = fpu;
    static double upper = 1 - 2.22e-16;
    static double const1 = 2.30753;
    static double const2 = 0.27061;
    static double const3 = 0.99229;
    static double const4 = 0.04481;
    static volatile double xtrunc;

    public static double cumulative(double x, double pin, double qin) {

        if (Double.isNaN(x) || Double.isNaN(pin) || Double.isNaN(qin)) return x + pin + qin;
        if (pin <= 0 || qin <= 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        if (x <= 0) return 0;
        if (x >= 1) return 1;
        return pbeta_raw(x, pin, qin);
    }

    public static double density(double x, double a, double b) {

        double y;
        if (Double.isNaN(x) || Double.isNaN(a) || Double.isNaN(b)) return x + a + b;
        if (a <= 0.0 || b <= 0.0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        if (x < 0) return 0;
        if (x > 1) return 0;
        y = misc.beta(a, b);
        a = java.lang.Math.pow(x, a - 1);
        b = java.lang.Math.pow(1.0 - x, b - 1.0);
        return a * b / y;
    }

    public static double quantile(double alpha, double p, double q) {

        int swap_tail, i_pb, i_inn;
        double a, adj, logbeta, g, h, pp, prev, qq, r, s, t, tx, w, y, yprev;
        double acu;
        double xinbta = alpha;

        if (Double.isNaN(p) || Double.isNaN(q) || Double.isNaN(alpha)) return p + q + alpha;
        if (p < zero || q < zero || alpha < zero || alpha > 1) { throw new java.lang.ArithmeticException("Math Error: DOMAIN"); }
        if (alpha == zero || alpha == 1) return alpha;

        logbeta = misc.lbeta(p, q);

        if (alpha <= 0.5) {
            a = alpha;
            pp = p;
            qq = q;
            swap_tail = 0;
        }
        else {
            a = 1 - alpha;
            pp = q;
            qq = p;
            swap_tail = 1;
        }

        r = java.lang.Math.sqrt(-java.lang.Math.log(a * a));
        y = r - (const1 + const2 * r) / (1 + (const3 + const4 * r) * r);
        if (pp > 1 && qq > 1) {
            r = (y * y - 3) / 6;
            s = 1 / (pp + pp - 1);
            t = 1 / (qq + qq - 1);
            h = 2 / (s + t);
            w = y * java.lang.Math.sqrt(h + r) / h - (t - s) * (r + 5 / 6 - 2 / (3 * h));
            xinbta = pp / (pp + qq * java.lang.Math.exp(w + w));
        }
        else {
            r = qq + qq;
            t = 1 / (9 * qq);
            t = r * java.lang.Math.pow(1 - t + y * java.lang.Math.sqrt(t), 3);
            if (t <= zero)
                xinbta = 1 - java.lang.Math.exp((java.lang.Math.log((1 - a) * qq) + logbeta) / qq);
            else {
                t = (4 * pp + r - 2) / t;
                if (t <= 1)
                    xinbta = java.lang.Math.exp((java.lang.Math.log(a * pp) + logbeta) / pp);
                else xinbta = 1 - 2 / (t + 1);
            }
        }

        r = 1 - pp;
        t = 1 - qq;
        yprev = zero;
        adj = 1;
        if (xinbta < lower)
            xinbta = lower;
        else if (xinbta > upper) xinbta = upper;

        acu = Math.max(acu_min, java.lang.Math.pow(10., -13 - 2.5 / (pp * pp) - 0.5 / (a * a)));
        tx = prev = zero; /* keep -Wall happy */

        L_converged: {
            for (i_pb = 0; i_pb < 1000; i_pb++) {
                y = pbeta_raw(xinbta, pp, qq);
                if (Double.isInfinite(y)) y = (y - a) * java.lang.Math.exp(logbeta + r * java.lang.Math.log(xinbta) + t * java.lang.Math.log(1 - xinbta));
                if (y * yprev <= zero) prev = Math.max(java.lang.Math.abs(adj), fpu);
                g = 1;
                for (i_inn = 0; i_inn < 1000; i_inn++) {
                    adj = g * y;
                    if (java.lang.Math.abs(adj) < prev) {
                        tx = xinbta - adj;
                        if (tx >= zero && tx <= 1) {
                            if (prev <= acu) break L_converged;
                            if (java.lang.Math.abs(y) <= acu) break L_converged;
                            if (tx != zero && tx != 1) break;
                        }
                    }
                    g /= 3;
                }
                xtrunc = tx;
                if (xtrunc == xinbta) break L_converged;
                xinbta = tx;
                yprev = y;
            }
            throw new java.lang.ArithmeticException("Math Error: PRECISION");
        }

        if (swap_tail == 1) xinbta = 1 - xinbta;
        return xinbta;
    }

    static double pbeta_raw(double x, double pin, double qin) {

        double ans, c, finsum, p, ps, p1, q, term, xb, xi, y;
        int n, i, ib;
        double eps = 0;
        double alneps = 0;
        double sml = 0;
        double alnsml = 0;

        if (eps == 0) {
            eps = misc.d1mach(3);
            /*!*         alneps = log(eps);  *!*/
            alneps = java.lang.Math.log(eps);
            sml = misc.d1mach(1);
            /*!*         alnsml = log(sml);  *!*/
            alnsml = java.lang.Math.log(sml);
        }

        y = x;
        p = pin;
        q = qin;

        /* swap tails if x is greater than the mean */

        if (p / (p + q) < x) {
            y = 1 - y;
            p = qin;
            q = pin;
        }

        if ((p + q) * y / (p + 1) < eps) {

            /* tail approximation */

            ans = 0;
            /*!*         xb = p * log(Math.max(y, sml)) - log(p) - misc.lbeta(p, q);  *!*/
            xb = p * java.lang.Math.log(Math.max(y, sml)) - java.lang.Math.log(p) - misc.lbeta(p, q);
            if (xb > alnsml && y != 0)
            /*!*             ans = exp(xb);  *!*/
            ans = java.lang.Math.exp(xb);
            if (y != x || p != pin) ans = 1 - ans;
        }
        else {

            /* evaluate the infinite sum first.  term will equal */
            /* y^p / beta(ps, p) * (1 - ps)-sub-i * y^i / fac(i) */

            /*!*         ps = q - floor(q);  *!*/
            ps = q - java.lang.Math.floor(q);
            if (ps == 0) ps = 1;
            /*!*         xb = p * log(y) - misc.lbeta(ps, p) - log(p);  *!*/
            xb = p * java.lang.Math.log(y) - misc.lbeta(ps, p) - java.lang.Math.log(p);
            ans = 0;
            if (xb >= alnsml) {
                /*!*             ans = exp(xb);  *!*/
                ans = java.lang.Math.exp(xb);
                term = ans * p;
                if (ps != 1) {
                    n = (int) Math.max(alneps / java.lang.Math.log(y), 4.0);
                    for (i = 1; i <= n; i++) {
                        xi = i;
                        term = term * (xi - ps) * y / xi;
                        ans = ans + term / (p + xi);
                    }
                }
            }

            /* now evaluate the finite sum, maybe. */

            if (q > 1) {
                /*!*             xb = p * log(y) + q * log(1 - y) - misc.lbeta(p, q) - log(q);  *!*/
                xb = p * java.lang.Math.log(y) + q * java.lang.Math.log(1 - y) - misc.lbeta(p, q) - java.lang.Math.log(q);
                ib = (int) Math.max(xb / alnsml, 0.0);
                /*!*             term = exp(xb - ib * alnsml);  *!*/
                term = java.lang.Math.exp(xb - ib * alnsml);
                c = 1 / (1 - y);
                p1 = q * c / (p + q - 1);

                finsum = 0;
                n = (int) q;
                if (q == n) n = n - 1;
                for (i = 1; i <= n; i++) {
                    if (p1 <= 1 && term / eps <= finsum) break;
                    xi = i;
                    term = (q - xi + 1) * c * term / (p + q - xi);
                    if (term > 1) {
                        ib = ib - 1;
                        term = term * sml;
                    }
                    if (ib == 0) finsum = finsum + term;
                }
                ans = ans + finsum;
            }
            if (y != x || p != pin) ans = 1 - ans;
            ans = Math.max(Math.min(ans, 1.0), 0.0);
        }
        return ans;
    }
}
