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
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

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
    protected float angle;
    protected float x,y;

    protected float timeframe;

    protected Animation<TextureRegion> an;

    protected TextureRegion region;

    protected Body body;
    protected CircleShape shape;
    protected Sprite sprite;

    protected BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("Physicshit.json"));

    Enemy(World world, int type, float angle, float startX, float startY, Animation tx) {
        this.angle=angle;
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

    Enemy(World world, int type, float angle, float startX, float startY, Texture tx) {
        this.angle=angle;
        this.sprite=new Sprite(tx);
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
            bodyDef.position.set(ArcadeValues.pxToMeters(ArcadeValues.pelletOriginX+800), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY));
            body = world.createBody(bodyDef);
            fixturer(0f, 0f);
        }
        if(leftRight==0) {
            //bodyDef.position.set(ArcadeValues.pxToMeters(-120), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY));
            bodyDef.position.set(ArcadeValues.pxToMeters(ArcadeValues.pelletOriginX-800), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY));
            body = world.createBody(bodyDef);
            fixturer(0.1f, 0.7f);
        }

        //DONT FORGET TO body.setLinearVelocity(SPEED,0f); FOR EACH ENEMY
        body.setUserData(this);

    }

    Enemy(World world, int type,int arcadeOr3, int leftRight, Texture tx){    //FOR ARCADEARROWWWWWW
        this.sprite=new Sprite(tx);
        if(leftRight==0&&arcadeOr3==0) sprite.flip(false,true);
        this.color=type;
        BodyDef bodyDef= new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if(arcadeOr3==1) {  //if arcadeOr3 is 1, LEVEL 3
            int r=MathUtils.random(2);
            float rand;
            if(r==0) {
                rand = MathUtils.random(ArcadeValues.pelletOriginY+140,ArcadeValues.pelletOriginY+160); //START OF Y RANGE TO END
            }else if(r==1) {
                rand = MathUtils.random(ArcadeValues.pelletOriginY+40,ArcadeValues.pelletOriginY+100);
            }else{
                rand = MathUtils.random(ArcadeValues.pelletOriginY,ArcadeValues.pelletOriginY+20);
            }
            bodyDef.position.set(ArcadeValues.pxToMeters(3840),ArcadeValues.pxToMeters(rand));  //PUT THE END OF X TO X,PUT THE RANDOM FOR Y
        }else if(arcadeOr3==0){ //IF IT IS ARCADE
            if(leftRight==1) {
                switch (type) {
                    case 0:
                        bodyDef.position.set(ArcadeValues.pxToMeters(ArcadeValues.pelletOriginX + 800), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY));
                        break;
                    case 1:
                        bodyDef.position.set(ArcadeValues.pxToMeters(ArcadeValues.pelletOriginX + 800), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY+50));
                        break;
                    case 2:
                        bodyDef.position.set(ArcadeValues.pxToMeters(ArcadeValues.pelletOriginX + 800), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY+100));
                        break;
                }
            }else if(leftRight==0){
                switch (type) {
                    case 0:
                        bodyDef.position.set(ArcadeValues.pxToMeters(ArcadeValues.pelletOriginX + 800), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY));
                        break;
                    case 1:
                        bodyDef.position.set(ArcadeValues.pxToMeters(ArcadeValues.pelletOriginX + 800), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY+50));
                        break;
                    case 2:
                        bodyDef.position.set(ArcadeValues.pxToMeters(ArcadeValues.pelletOriginX + 800), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY+100));
                        break;
                }
            }
        }
        body=world.createBody(bodyDef);
        fixturer(0f,0f);
        body.setUserData(this);
    }

    Enemy(World world, float x, Texture tx) {
        this.sprite= new Sprite(tx);
        this.color = 2;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                ArcadeValues.pxToMeters(x),
                ArcadeValues.pxToMeters(ArcadeValues.meteorSpawn)
                );
        body = world.createBody(bodyDef);
        fixturer(0f, 0f);
        body.setLinearVelocity(0.0f, ArcadeValues.meteorVelocity);
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
        Gdx.app.log("ATTACK Attack color"+color,  "Enemy color"+this.color);
        //
        float prevhp = hp;
        //
        hp -= color != this.color ? damage : (damage * 2);
        Gdx.app.log("ATTACK -Damage done: "+(prevhp-hp), " remaining hp="+hp);
        return hp <= 0.0f;
    }

    abstract void draw(SpriteBatch batch);
}
