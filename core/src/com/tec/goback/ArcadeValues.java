package com.tec.goback;

/*
 * Created by gerry on 3/22/17.
 */

class ArcadeValues {
    static final float initalFrequency = 2; //seconds between spawns
    static final float initialFactor = 0.02f; // decreas

    //NATURAL ORDER OF THINGS
    /*
        Yellow  1
        Blue    2
        Red     3
    */

    //
    static final float attackDamage = 20f;

    //positioning
    static float highOnPot = (float)java.lang.Math.sqrt( 640F*640F + 448F*448F);

    static final float pelletOriginX = 640;
    static final float pelletOriginY = 272;
    static final float meterspelletOriginX = pelletOriginX/100;
    static final float meterspelletOriginY = pelletOriginY/100;
    static final float places[] = {meterspelletOriginX, meterspelletOriginX -1, meterspelletOriginX + 1};
    static final float radius[] = {83f/200f, 48f/200f, 57f/200f};

    static final float meteorSpawn = 800f;
    static final float meteorVelocity = -0.5f;
    static final float meteorRadius = 40f;




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
    static final short orbCat = 16;

    static final short wallMask = pelletCat;
    static final short pelletMask = enemyCat | wallCat ;
    static final short sophieMask = 0;
    static final short enemyMask = pelletCat | orbCat;


    static float pxToMeters(float n){
        return n/100;
    }

    static float metersToPx(float n){
        return n*100;
    }
}
