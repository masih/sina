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

import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Tests {@link NumericalRangeValidator}.
 *
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class NumericalRangeValidatorTest {

    /** Tests {@link NumericalRangeValidator#validateRangeLargerThanZeroInclusive(Number)}. */
    @Test
    public void testValidateRangeLargerThanZeroInclusive() {

        final double[] bad_args = {Double.NEGATIVE_INFINITY, -Double.MAX_VALUE, -1.0, -100.0, Double.NaN};

        for (final double arg : bad_args) {
            try {
                NumericalRangeValidator.validateRangeLargerThanZeroInclusive(arg);
                fail();
            }
            catch (final IllegalArgumentException e) {
                continue;
            }
        }

        final double[] good_args = {Double.POSITIVE_INFINITY, Double.MAX_VALUE, 1.0, 100.0, -0.0, +0.0};
        for (final double arg : good_args) {
            NumericalRangeValidator.validateRangeLargerThanZeroInclusive(arg);
        }
    }

    /** Tests {@link NumericalRangeValidator#validateRangeLargerThanZeroExclusive(Number)}. */
    @Test
    public void testValidateRangeLargerThanZeroExclusive() {

        final double[] bad_args = {Double.NEGATIVE_INFINITY, -Double.MAX_VALUE, -1.0, -100.0, -0.0, +0.0, Double.NaN};

        for (final double arg : bad_args) {
            try {
                NumericalRangeValidator.validateRangeLargerThanZeroExclusive(arg);
                fail();
            }
            catch (final IllegalArgumentException e) {
                continue;
            }
        }

        final double[] good_args = {Double.POSITIVE_INFINITY, Double.MAX_VALUE, 1.0, 100.0};
        for (final double arg : good_args) {
            NumericalRangeValidator.validateRangeLargerThanZeroExclusive(arg);
        }
    }

    /** Tests {@link NumericalRangeValidator#validateRangeLargerThan(Number, Number, boolean)}. */
    @Test
    public void testValidateRangeLargerThan() {

        //@formatter:off
        final Object[][] bad_args = {
                {Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, false}, {Double.NEGATIVE_INFINITY, Double.NaN, false}, {0.0, Double.POSITIVE_INFINITY, false}, {-100.0, 5.0, true}
        }; //@formatter:on

        for (final Object[] arg : bad_args) {
            try {
                NumericalRangeValidator.validateRangeLargerThan((Number) arg[0], (Number) arg[1], (Boolean) arg[2]);
                fail();
            }
            catch (final IllegalArgumentException e) {
                continue;
            }
        }

        //@formatter:off
        final Object[][] good_args = {
                {Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, true}, {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, true}, {Double.POSITIVE_INFINITY, 0.0, false}, {-5.0, -500.0, true}
        }; //@formatter:on
        for (final Object[] arg : good_args) {
            NumericalRangeValidator.validateRangeLargerThan((Number) arg[0], (Number) arg[1], (Boolean) arg[2]);
        }
    }

    /** Tests {@link NumericalRangeValidator#validateRange(Number, Number, Number, boolean, boolean)}. */
    @Test
    public void testValidateRange() {

        //@formatter:off
        final Object[][] bad_args = {
                {Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, false, false},
                {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, false, false},
                {Double.NEGATIVE_INFINITY, 10, Double.POSITIVE_INFINITY, false, false},
                {Double.NaN, Double.NaN, Double.NaN, false, false},
                {Double.NaN, Double.NaN, Double.NaN, true, true},
                {Double.NaN, Double.NaN, Double.NaN, false, true},
                {Double.NaN, Double.NaN, Double.NaN, true, false},
                {0.0, 100, -100, true, false},
                {-100.0, 5.0, 10, true, true}
        }; //@formatter:on

        for (final Object[] arg : bad_args) {
            int i;
            try {
                i = 0;
                NumericalRangeValidator.validateRange((Number) arg[i++], (Number) arg[i++], (Number) arg[i++], (Boolean) arg[i++], (Boolean) arg[i++]);
                fail();
            }
            catch (final IllegalArgumentException e) {
                continue;
            }
        }

        //@formatter:off
        final Object[][] good_args = {
                {Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, true, true}, {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, true, true}, {10, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false, false}, {5, -9, 5, false, true}, {5, -9, 50, false, false}, {5, 5, 5, true, true}
        }; //@formatter:on
        int i;
        for (final Object[] arg : good_args) {
            i = 0;
            NumericalRangeValidator.validateRange((Number) arg[i++], (Number) arg[i++], (Number) arg[i++], (Boolean) arg[i++], (Boolean) arg[i++]);
        }
    }

    /** Tests {@link NumericalRangeValidator#validateRangeZeroToOneInclusive(Number)}. */
    @Test
    public void testValidateRangeZeroToOneInclusive() {

        final double[] bad_args = {Double.NEGATIVE_INFINITY, -Double.MAX_VALUE, Double.POSITIVE_INFINITY, Double.MAX_VALUE, -1.0, -100.0, 1.000001, Double.NaN};

        for (final double arg : bad_args) {
            try {
                NumericalRangeValidator.validateRangeZeroToOneInclusive(arg);
                fail();
            }
            catch (final IllegalArgumentException e) {
                continue;
            }
        }

        final double[] good_args = {+0.0, -0.0, 0.999999999999999999, 1.0};
        for (final double arg : good_args) {
            NumericalRangeValidator.validateRangeZeroToOneInclusive(arg);
        }
    }

    /** Tests {@link NumericalRangeValidator#validateNumber(Number...)}. */
    @Test
    public void testValidateNumber() {

        try {
            NumericalRangeValidator.validateNumber(Double.NaN, Double.NaN, Double.NaN);
            fail();
        }
        catch (final IllegalArgumentException e) {
            //ignore; expected
        }

        final Number[] good_args = {+0.0, -0.0, 0.999999999999999999, 1.0, Double.NEGATIVE_INFINITY, -Double.MAX_VALUE, Double.POSITIVE_INFINITY, Double.MAX_VALUE, -1.0, -100.0, 1.000001};
        for (final Number arg : good_args) {
            NumericalRangeValidator.validateNumber(arg);
        }
        NumericalRangeValidator.validateNumber(good_args);
    }

}
