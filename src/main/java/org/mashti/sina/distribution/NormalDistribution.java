package org.mashti.sina.distribution;

import org.mashti.sina.util.Erf;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.mashti.sina.util.NumericalRangeValidator.validateRangeLargerThanZeroExclusive;
import static org.mashti.sina.util.NumericalRangeValidator.validateRangeZeroToOneInclusive;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class NormalDistribution implements ProbabilityDistribution {

    private static final long serialVersionUID = 3877572847526702145L;
    private static final double SQRT_2 = sqrt(2.0);
    private static final double SQRT_2_PI = sqrt(2 * PI);
    private final double mean;
    private final double sd;

    public NormalDistribution(Number mean, Number standard_deviation) {

        validateRangeLargerThanZeroExclusive(standard_deviation);
        this.mean = mean.doubleValue();
        sd = standard_deviation.doubleValue();
    }

    @Override
    public Number density(final Number x_) {

        double x = x_.doubleValue();
        x = (x - mean) / sd;
        return SQRT_2_PI * exp(-HALF * x * x) / sd;
    }

    @Override
    public Number cumulative(final Number x) {

        final double dev = x.doubleValue() - mean;
        if (abs(dev) > 40 * sd) { return dev < 0 ? 0.0d : 1.0d; }
        return 0.5 * (1 + Erf.erf(dev / (sd * SQRT_2)));
    }

    @Override
    public Number quantile(final Number probability) {

        validateRangeZeroToOneInclusive(probability);
        return mean + sd * SQRT_2 * Erf.erfInv(2 * probability.doubleValue() - 1);
    }

    @Override
    public Number mean() {

        return mean;
    }

    @Override
    public Number median() {

        return mean;
    }

    @Override
    public Number mode() {

        return mean;
    }

    @Override
    public Number variance() {

        return pow(sd, TWO);
    }

    @Override
    public Number skewness() {

        return ZERO;
    }
}
