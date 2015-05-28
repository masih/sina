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

public class cauchy {

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
     *    double density(double x, double location, double scale);
     *
     *  DESCRIPTION
     *
     *    The density of the Cauchy distribution.
     */

    /*!* #include "DistLib.h" /*4!*/

    public static double density(double x, double location, double scale) {

        double y;
        /*!* #ifdef IEEE_754 /*4!*/
        /* NaNs propagated correctly */
        if (Double.isNaN(x) || Double.isNaN(location) || Double.isNaN(scale)) return x + location + scale;
        /*!* #endif /*4!*/
        if (scale <= 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //    	return Double.NaN;
        }
        y = (x - location) / scale;
        return 1.0 / (Constants.M_PI * scale * (1.0 + y * y));
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
     *    double cumulative(double x, double location, double scale);
     *
     *  DESCRIPTION
     *
     *    The distribution function of the Cauchy distribution.
     */

    /*!* #include "DistLib.h" /*4!*/

    public static double cumulative(double x, double location, double scale) {

        /*!* #ifdef IEEE_754 /*4!*/
        if (Double.isNaN(x) || Double.isNaN(location) || Double.isNaN(scale)) return x + location + scale;
        /*!* #endif /*4!*/
        if (scale <= 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //    	    return Double.NaN;
        }
        x = (x - location) / scale;
        /*!* #ifdef IEEE_754 /*4!*/
        if (Double.isInfinite(x)) {
            if (x < 0)
                return 0;
            else return 1;
        }
        /*!* #endif /*4!*/
        /*!* 	return 0.5 + atan(x) / Constants.M_PI; *!*/
        return 0.5 + java.lang.Math.atan(x) / Constants.M_PI;
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
     *    double quantile(double x, double location, double scale);
     *
     *  DESCRIPTION
     *
     *    The quantile function of the Cauchy distribution.
     */

    /*!* #include "DistLib.h" /*4!*/

    public static double quantile(double x, double location, double scale) {

        /*!* #ifdef IEEE_754 /*4!*/
        if (Double.isNaN(x) || Double.isNaN(location) || Double.isNaN(scale)) return x + location + scale;
        if (Double.isInfinite(x) || Double.isInfinite(location) || Double.isInfinite(scale)) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //            return Double.NaN;
        }
        /*!* #endif /*4!*/

        if (scale <= 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //    	return Double.NaN;
        }
        /*!*     return location + scale * tan(Constants.M_PI * (x - 0.5)); *!*/
        return location + scale * java.lang.Math.tan(Constants.M_PI * (x - 0.5));
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
     *    double random(double location, double scale);
     *
     *  DESCRIPTION
     *
     *    Random variates from the normal distribution.
     */

    /*!* #include "DistLib.h" /*4!*/

    public static double random(double location, double scale, uniform PRNG) {

        if (
        /*!* #ifdef IEEE_754 /*4!*/
        Double.isInfinite(location) || Double.isInfinite(scale) ||
        /*!* #endif /*4!*/
        scale < 0) { throw new java.lang.ArithmeticException("Math Error: DOMAIN");
        //    	return Double.NaN;
        }
        /*!*     return location + scale * tan(Constants.M_PI * sunif()); *!*/
        return location + scale * java.lang.Math.tan(Constants.M_PI * PRNG.random());
    }
}
