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
