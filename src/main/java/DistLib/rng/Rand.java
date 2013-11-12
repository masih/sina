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
/*
 * Created on Apr 17, 2007
 */
package DistLib.rng;

import DistLib.StdUniformRng;
import java.util.Random;

public class Rand implements StdUniformRng {

    Random random;

    public Rand() {

        random = new Random();
    }

    public void fixupSeeds() {

        ; // do nothing since seeds are managed
    }

    public double random() {

        return random.nextDouble();
    }

}
