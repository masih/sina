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

public final class NumericalRangeValidator {

    private NumericalRangeValidator() {

    }

    public static void validateRangeLargerThanZeroInclusive(final Number value) {

        validateRangeLargerThan(value, 0.0D, true);
    }

    public static void validateRangeLargerThanZeroExclusive(final Number value) {

        validateRangeLargerThan(value, 0.0D, false);
    }

    public static void validateRangeLargerThanOneInclusive(final Number value) {

        validateRangeLargerThan(value, 1.0D, true);
    }

    public static void validateRangeLargerThan(final Number value, final Number min, final boolean min_inclusive) {

        validateRange(value, min, Double.POSITIVE_INFINITY, min_inclusive, true);
    }

    public static void validateRange(final Number value, final Number min, final Number max, final boolean min_inclusive, final boolean max_inclusive) {

        final double value_d = value.doubleValue();
        final double min_d = min.doubleValue();
        final double max_d = max.doubleValue();

        validateNumber(value_d, min_d, max_d);
        if (min_d > max_d) { throw new IllegalArgumentException("invalid range"); }
        if ((min_inclusive ? value_d < min_d : value_d <= min_d) || (max_inclusive ? value_d > max_d : value_d >= max_d)) {
            final StringBuilder sb = new StringBuilder();
            sb.append("value ");
            sb.append(value);
            sb.append(" must be between ");
            sb.append(min);
            sb.append(min_inclusive ? " (inclusive) " : " (exclusive) ");
            sb.append("and ");
            sb.append(max);
            sb.append(max_inclusive ? " (inclusive)" : " (exclusive)");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static void validateRangeZeroToOneExclusive(final Number value) {

        validateRange(value, 0.0D, 1.0D, false, false);
    }

    public static void validateRangeZeroToOneInclusive(final Number value) {

        validateRange(value, 0.0D, 1.0D, true, true);
    }

    public static void validateNumber(final Number... values) {

        if (hasNaN(values)) { throw new IllegalArgumentException("NaN is not allowed"); }
    }

    public static boolean hasNaN(final Number... values) {

        for (final Number value : values) {
            if (Double.isNaN(value.doubleValue())) { return true; }
        }
        return false;
    }
}
