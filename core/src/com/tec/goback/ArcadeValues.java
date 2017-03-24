package com.tec.goback;

/**
 * Created by gerry on 3/22/17.
 */

class ArcadeValues {
    static final float initalFrequency = 2; //seconds between spawns
    static final float initialFactor = 0.02f; // decreas

    static final float pelletOriginX = 640;
    static final float pelletOriginY = 226;
    static final float meterspelletOriginX = pelletOriginX/100;
    static final float meterspelletOriginY = pelletOriginY/100;

    //lizard settings
    static final float rightLizardOriginX=0;
    static final float rightLizardOriginY=0;
    static final float meterRightLizardOriginX=0f;
    static final float meterRightLizardOriginY=0f;
    static final float leftLizardOriginX=0;
    static final float leftLizardOriginY=0;
    static final float meterLeftLizardOriginX=0f;
    static final float meterLeftLizardOriginY=0f;

    static final short wallCat = 1;
    static final short pelletCat = 2;
    static final short enemyCat = 4;
    static final short sophieCat = 8;

    static final short wallMask = pelletCat | enemyCat;
    static final short pelletMask = enemyCat | wallCat;
    static final short sophieMask = enemyCat;
    static final short enemyMask= pelletCat | sophieCat;

    static float pxToMeters(float n){
        return n/100;
    }

    static float metersToPx(float n){
        return n*100;
    }
}
