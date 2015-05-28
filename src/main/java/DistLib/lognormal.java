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

/* data translated from C using perl script translate.pl */
/* script version 0.00                               */

public class lognormal {

    /*
     *  DistLib : A C Library of Special Functions
     *  Copyright (C) 1998 Ross Ihaka
     *
     *  This program is free software; you can redistribute it and/or modify
     *  it under the terms of the GNU General Public License as published by
     *  the Free Software Foundation; either version 2 of the License, or
     *  (at your option) any later version.
     *
     *  This program is distributed in the hope that it will be useful,
     *  but WITHOUT ANY WARRANTY; without even the implied warranty of
     *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     *  GNU General Public License for more details.
     *
     *  You should have received a copy of the GNU General Public License
     *  along with this program; if not, write to the Free Software
     *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
     *
     *  SYNOPSIS
     *
     *    double density(double x, double logmean, double logsd);
     *
     *  DESCRIPTION
     *
     *    The density of the lognormal distribution.
     *
     * 	M_1_SQRT_2PI = 1 / sqrt(2 * pi)
     */

    /*!* #include "DistLib.h" /*4!*/

    public static double cumulative(double x, double logmean, double logsd) {

        /*!* #ifdef IEEE_754 /*4!*/
        if (Double.isNaN(x) || Double.isNaN(logmean) || Double.isNaN(logsd)) return x + logmean + logsd;
        /*!* #endif /*4!*/
        if (logsd <= 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //            return Double.NaN;
        }
        if (x > 0)
        /*!* 	return normal.cumulative!!!COMMENT!!!(log(x), logmean, logsd); *!*/
        return normal.cumulative(java.lang.Math.log(x), logmean, logsd);
        return 0;
    }

    /*
     *  DistLib : A C Library of Special Functions
     *  Copyright (C) 1998 Ross Ihaka
     *
     *  This program is free software; you can redistribute it and/or modify
     *  it under the terms of the GNU General Public License as published by
     *  the Free Software Foundation; either version 2 of the License, or
     *  (at your option) any later version.
     *
     *  This program is distributed in the hope that it will be useful,
     *  but WITHOUT ANY WARRANTY; without even the implied warranty of
     *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     *  GNU General Public License for more details.
     *
     *  You should have received a copy of the GNU General Public License
     *  along with this program; if not, write to the Free Software
     *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
     *
     *  SYNOPSIS
     *
     *    #include "DistLib.h"
     *    double cumulative(double x, double logmean, double logsd);
     *
     *  DESCRIPTION
     *
     *    The lognormal distribution function.
     */

    /*!* #include "DistLib.h" /*4!*/

    public static double density(double x, double logmean, double logsd) {

        double y;

        /*!* #ifdef IEEE_754 /*4!*/
        if (Double.isNaN(x) || Double.isNaN(logmean) || Double.isNaN(logsd)) return x + logmean + logsd;
        /*!* #endif /*4!*/
        if (logsd <= 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //            return Double.NaN;
        }
        if (x == 0) return 0;
        /*!*     y = (log(x) - logmean) / logsd; *!*/
        y = (java.lang.Math.log(x) - logmean) / logsd;
        /*!*     return Constants.M_1_SQRT_2PI * exp(-0.5 * y * y) / (x * logsd); *!*/
        return Constants.SQRT_2_PI * java.lang.Math.exp(-0.5 * y * y) / (x * logsd);
    }

    /*
     *  DistLib : A C Library of Special Functions
     *  Copyright (C) 1998 Ross Ihaka
     *
     *  This program is free software; you can redistribute it and/or modify
     *  it under the terms of the GNU General Public License as published by
     *  the Free Software Foundation; either version 2 of the License, or
     *  (at your option) any later version.
     *
     *  This program is distributed in the hope that it will be useful,
     *  but WITHOUT ANY WARRANTY; without even the implied warranty of
     *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     *  GNU General Public License for more details.
     *
     *  You should have received a copy of the GNU General Public License
     *  along with this program; if not, write to the Free Software
     *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
     *
     *  SYNOPSIS
     *
     *    #include "DistLib.h"
     *    double quantile(double x, double logmean, double logsd);
     *
     *  DESCRIPTION
     *
     *    This the lognormal quantile function.
     */

    /*!* #include "DistLib.h" /*4!*/

    public static double quantile(double x, double logmean, double logsd) {

        /*!* #ifdef IEEE_754 /*4!*/
        if (Double.isNaN(x) || Double.isNaN(logmean) || Double.isNaN(logsd)) return x + logmean + logsd;
        /*!* #endif /*4!*/
        if (x < 0 || x > 1 || logsd <= 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //            return Double.NaN;
        }
        if (x == 1) return Double.POSITIVE_INFINITY;
        /*!*     if (x > 0) return exp(qnorm(x, logmean, logsd)); *!*/
        if (x > 0) return java.lang.Math.exp(normal.quantile(x, logmean, logsd));
        return 0;
    }

    /*
     *  DistLib : A C Library of Special Functions
     *  Copyright (C) 1998 Ross Ihaka
     *
     *  This program is free software; you can redistribute it and/or modify
     *  it under the terms of the GNU General Public License as published by
     *  the Free Software Foundation; either version 2 of the License, or
     *  (at your option) any later version.
     *
     *  This program is distributed in the hope that it will be useful,
     *  but WITHOUT ANY WARRANTY; without even the implied warranty of
     *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     *  GNU General Public License for more details.
     *
     *  You should have received a copy of the GNU General Public License
     *  along with this program; if not, write to the Free Software
     *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
     *
     *  SYNOPSIS
     *
     *    #include "DistLib.h"
     *    double random(double logmean, double logsd);
     *
     *  DESCRIPTION
     *
     *    Random variates from the lognormal distribution.
     */

    /*!* #include "DistLib.h" /*4!*/

    public static double random(double logmean, double logsd, uniform PRNG) {

        if (
        /*!* #ifdef IEEE_754 /*4!*/
        Double.isInfinite(logmean) || Double.isInfinite(logsd) ||
        /*!* #endif /*4!*/
        logsd <= 0.0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //            return Double.NaN;
        }
        /*!*     return exp(rnorm(logmean, logsd)); *!*/
        return java.lang.Math.exp(normal.random(logmean, logsd, PRNG));
    }
}
