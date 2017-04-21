package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by sergiohernandezjr on 16/02/17.
 * Con ayuda de DON PABLO
 */

abstract class Enemy{

    protected float hp;
    protected float dmg;
    protected int color;
    protected float SPEED;
    protected int leftRight;
    protected int angle;
    protected float x,y;

    protected float timeframe;

    protected Animation<TextureRegion> an;

    protected TextureRegion region;

    protected Body body;
    protected CircleShape shape;
    protected Sprite sprite;

    Enemy(World world, int type, float startX, float startY, Animation tx) {
        this.an=tx;
        this.color = type;
        this.x=startX;
        this.y=startY;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if(x>0) {
            leftRight=1;
        }else leftRight=0;
        bodyDef.position.set(ArcadeValues.pxToMeters(x), ArcadeValues.pxToMeters(y));
        body = world.createBody(bodyDef);
        fixturer(0.1f, 0.7f);

        body.setUserData(this);
    }

    Enemy(World world, int type, int leftOrRight, Animation tx) {
        this.an=tx;
        this.color = type;
        this.leftRight=leftOrRight;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if(leftRight==1) {
            //bodyDef.position.set(ArcadeValues.pxToMeters(1280+120), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY));
            bodyDef.position.set(ArcadeValues.pxToMeters(ArcadeValues.pelletOriginX+800), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY+50));
            body = world.createBody(bodyDef);
            fixturer(0f, 0f);
        }
        if(leftRight==0) {
            //bodyDef.position.set(ArcadeValues.pxToMeters(-120), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY));
            bodyDef.position.set(ArcadeValues.pxToMeters(ArcadeValues.pelletOriginX-800), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY+50));
            body = world.createBody(bodyDef);
            fixturer(0.1f, 0.7f);
        }

        //DONT FORGET TO body.setLinearVelocity(SPEED,0f); FOR EACH ENEMY
        body.setUserData(this);

    }

    abstract void fixturer(float density, float restitution);

    float getDamage(){
        return dmg;
    }

    int getColor(){
        return color;
    }

    int getRightLeft(){
        return leftRight;
    }

    boolean getHurtDie(int color, float damage){
        Gdx.app.log("Attack color"+color,  "Enemy color"+this.color);
        //
        float prevhp = hp;
        //
        hp -= color != this.color ? damage : (damage * 2);
        Gdx.app.log("Damage done: "+(prevhp-hp), " remaining hp="+hp);
        return hp <= 0.0f;
    }

    abstract void draw(SpriteBatch batch);
}


