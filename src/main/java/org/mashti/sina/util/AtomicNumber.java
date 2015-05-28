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

import java.util.concurrent.atomic.AtomicLong;

public class AtomicNumber extends Number {

    private final AtomicLong long_bits;

    public AtomicNumber() {

        this(0.0D);
    }

    public AtomicNumber(final Number initial_value) {

        long_bits = new AtomicLong(toLongBits(initial_value));
    }

    @Override
    public int intValue() {

        return (int) doubleValue();
    }

    @Override
    public long longValue() {

        return (long) doubleValue();
    }

    @Override
    public float floatValue() {

        return (float) doubleValue();
    }

    @Override
    public double doubleValue() {

        return Double.longBitsToDouble(long_bits.get());
    }

    @Override
    public String toString() {

        return String.valueOf(get());
    }

    public Number get() {

        return fromLongBits(long_bits.get());
    }

    public void set(final Number value) {

        long_bits.set(toLongBits(value));
    }

    public Number getAndSet(final Number value) {

        return fromLongBits(long_bits.getAndSet(toLongBits(value)));
    }

    public boolean compareAndSet(final Number expect, final Number update) {

        return long_bits.compareAndSet(toLongBits(expect), toLongBits(update));
    }

    public boolean weakCompareAndSet(final Number expect, final Number update) {

        return long_bits.weakCompareAndSet(toLongBits(expect), toLongBits(update));
    }

    public Number addAndGet(final Number value) {

        long current;
        long next;
        do {
            current = long_bits.get();
            next = toLongBits(fromLongBits(current) + value.doubleValue());
        }
        while (!long_bits.compareAndSet(current, next));
        return fromLongBits(next);
    }

    public Number setIfGreaterAndGet(final Number value) {

        long current;
        long next;
        do {
            current = long_bits.get();
            next = toLongBits(Math.max(fromLongBits(current), value.doubleValue()));
        }
        while (!long_bits.compareAndSet(current, next));
        return fromLongBits(next);
    }

    public Number setIfSmallerAndGet(final Number value) {

        long current;
        long next;
        do {
            current = long_bits.get();
            next = toLongBits(Math.min(fromLongBits(current), value.doubleValue()));
        }
        while (!long_bits.compareAndSet(current, next));
        return fromLongBits(next);
    }

    private long toLongBits(final Number value) {

        return Double.doubleToRawLongBits(value.doubleValue());
    }

    private double fromLongBits(final long value) {

        return Double.longBitsToDouble(value);
    }
}
