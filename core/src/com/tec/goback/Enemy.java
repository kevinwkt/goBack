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

    protected static float hp;
    protected static float dmg;
    protected static int color;
    protected static float SPEED;
    protected static int leftright;

    private Body body;
    private CircleShape shape;
    private Sprite sprite;

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
        //this.sprite = new Sprite(tx);
        this.color = type;
        this.leftright=leftOrRight;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if(leftright==1) bodyDef.position.set(ArcadeValues.pxToMeters(1280+120), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY));
        if(leftright==0) bodyDef.position.set(ArcadeValues.pxToMeters(-120), ArcadeValues.pxToMeters(ArcadeValues.pelletOriginY));

        body = world.createBody(bodyDef);
        fixturer(0.1f, 0.7f);
        body.setBullet(true);

        body.setLinearVelocity(SPEED, 0);

        body.setUserData(this);
    }

    private void fixturer(float density, float restitution) {
        //neumann preventive shit
        for (Fixture fix : body.getFixtureList()) {
            body.destroyFixture(fix);
        }

        //shape of pellet
        shape = new CircleShape();

        shape.setRadius(
                ArcadeValues.pxToMeters((sprite.getWidth() * sprite.getScaleX()/2f))
        );//sprite translated to meters

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;

        fixtureDef.filter.categoryBits = ArcadeValues.pelletCat; //its category
        fixtureDef.filter.maskBits = ArcadeValues.pelletMask; //or of its category with colliding categories

        body.createFixture(fixtureDef);
    }

    private float getDamage(){
        return dmg;
    }

    private int getColor(){
        return color;
    }

    private boolean getHurtDie(float damage){
        hp-=damage;
        if(hp<=0) return true;
        else return false;
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(
                ArcadeValues.metersToPx(body.getPosition().x),
                ArcadeValues.metersToPx(body.getPosition().y)
        );
        sprite.draw(batch);
    }
}


