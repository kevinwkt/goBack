package com.tec.goback;

/*
 * Created by gerry on 3/22/17.
 */

class ArcadeValues {
    static boolean bossFightFlag = false;

    static final float WIDTH = 1280;
    static final float HEIGHT = 720;
    static final float HALFW = WIDTH/2;
    static final float HALFH = HEIGHT/2;

    static final float stepTimes[] = {
            10, 30, 60, 80, 110, 130, 160
    };

    //seconds between spawns
    static final float initalFrequency = 2;
    static final float arcadeMultiplier = 2;

    /*
        Yellow  1
        Blue    2
        Red     3
    */
    static final float attackDamage = 20f;

    //positioning
    static float highOnPot = (float) java.lang.Math.sqrt(640F * 640F + 448F * 448F);
    static final float bossX = 1000;
    static final float yellowBossLife = 9000;
    static final float blueBossLife = 9000;
    static final float redBossLife = 9000;

    static final float pelletOriginX = 640;
    static final float pelletOriginY = 272;
    static final float meterspelletOriginX = pelletOriginX / 100;
    static final float meterspelletOriginY = pelletOriginY / 100;
    static final float places[][] = {
            {meterspelletOriginX, meterspelletOriginY},
            {meterspelletOriginX - 1.5f, meterspelletOriginY},
            {meterspelletOriginX - 1.5f, meterspelletOriginY + 0.8f}
    };
    static final float radius[] = {36.5f, 20f, 26.5f};

    static final float meteorSpawn = 800f;
    static final float meteorVelocity = -0.5f;
    static final float meteorRadius = 40f;


    //lizard settings
    static final float rightLizardOriginX = 0;
    static final float rightLizardOriginY = 0;
    static final float meterRightLizardOriginX = 0f;
    static final float meterRightLizardOriginY = 0f;
    static final float leftLizardOriginX = 0;
    static final float leftLizardOriginY = 0;
    static final float meterLeftLizardOriginX = 0f;
    static final float meterLeftLizardOriginY = 0f;

    static final short wallCat = 1;
    static final short pelletCat = 2;
    static final short enemyCat = 4;
    static final short sophieCat = 8;
    static final short orbCat = 16;

    static final short wallMask = pelletCat;
    static final short pelletMask = enemyCat | wallCat;
    static final short sophieMask = enemyCat;
    static final short enemyMask = pelletCat | orbCat | sophieCat;


    static float pxToMeters(float n) {
        return n / 100;
    }

    static float metersToPx(float n) {
        return n * 100;
    }
}
