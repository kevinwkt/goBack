package com.tec.goback;

/**
 * Created by gerry on 3/22/17.
 */

class ArcadeValues {
    static final float pelletOriginX = 600;
    static final float pelletOriginY = 100;
    static final float meterspelletOriginX = 6f;
    static final float meterspelletOriginY = 1f;

    static final short wallCat = 1;
    static final short pelletCat = 2;
    static final short enemyCat = 4;
    static final short sophieCat = 8;

    static final short wallMask = pelletCat | enemyCat;
    static final short pelletMask = enemyCat | wallCat;
    static final short sophieMask = enemyCat;





    static float pxToMeters(float n){
        return n/100;
    }

    static float metersToPx(float n){
        return n*100;
    }
}
