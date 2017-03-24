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

public abstract class Enemy{

    protected float hp;
    protected float dmg;
    protected int color;
    protected float SPEED;
    protected int leftRight;

    protected Animation<TextureRegion> an;

    protected TextureRegion region;

    protected Body body;
    protected CircleShape shape;
    protected Sprite sprite;

    public Enemy(World world, int type, float angle, Texture tx, float startX, float startY) {
        this.sprite = new Sprite(tx);
        this.color = type;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                ArcadeValues.pxToMeters(startX),
                ArcadeValues.pxToMeters(startY)
        );

        body = world.createBody(bodyDef);
        fixturer(0.1f, 0.7f);
        body.setBullet(true);

        body.setLinearVelocity(MathUtils.cos(angle) * SPEED, MathUtils.sin(angle) * SPEED);

        body.setUserData(this);
    }

    public Enemy(World world, int type, int leftOrRight, Animation tx) {
        this.an=tx;
        this.color = type;
        this.leftRight=leftOrRight;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if(leftRight==1) {
            bodyDef.position.set(ArcadeValues.pxToMeters(1280+120), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY));
            body = world.createBody(bodyDef);
            fixturer(0.1f, 0.7f);
            body.setBullet(true);
            body.setLinearVelocity(-SPEED, 0);
        }
        if(leftRight==0) {
            bodyDef.position.set(ArcadeValues.pxToMeters(-120), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY));
            body = world.createBody(bodyDef);
            fixturer(0.1f, 0.7f);
            body.setBullet(true);
            body.setLinearVelocity(SPEED, 0);
        }

        body.setUserData(this);
    }

    abstract void fixturer(float density, float restitution);

    public float getDamage(){
        return dmg;
    }

    public int getColor(){
        return color;
    }

    public int getRightLeft(){
        return leftRight;
    }

    public boolean getHurtDie(float damage){
        hp-=damage;
        if(hp<=0) return true;
        else return false;
    }

    abstract void draw(SpriteBatch batch,float delta);
}


