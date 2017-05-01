package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;


/*
 * Created by gerry on 4/7/17.
 */

abstract class ArcadeOrb{

    private Body body;
    private Sprite sprite;
    private float life;
    private float ac;
    private boolean active;
    private float radius;
    private int place;
    private int color;
    private Preferences stats = Gdx.app.getPreferences("STATS");

    ArcadeOrb(World world, Texture tx, int place, boolean active, float life, float radius, int color){
        this.place = place;
        this.radius = radius;
        this.color = color;
        this.sprite = new Sprite(tx);
        this.active = active;
        this.life = life;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(ArcadeValues.places[place][0], ArcadeValues.places[place][1]);
        this.body = world.createBody(bodyDef);
        fixturer(0.1f, 0.7f);
        body.setLinearVelocity(0f, 0f);

        body.setUserData(this);
    }

    private void fixturer(float density, float restitution) {

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.friction = 0;

        CircleShape shape = new CircleShape();
        shape.setRadius(ArcadeValues.pxToMeters(radius));
        fixtureDef.shape = shape;


        fixtureDef.filter.categoryBits = ArcadeValues.orbCat;
        fixtureDef.filter.maskBits = active ? ArcadeValues.enemyCat : 0;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    boolean getHurtDie(int type, float damage){
        life -= color == type ? damage : (damage * 2);
        return life <= 0.0f;
    }

    void draw(SpriteBatch batch){
        //Offset depending on the orb
        sprite.setCenter(
                ArcadeValues.metersToPx(body.getPosition().x),
                ArcadeValues.metersToPx(body.getPosition().y)
        );

        switch(((int) (life*10/(color == 1 || color == 2 ? (color == 1 ? stats.getFloat("YellowLife") :  stats.getFloat("BlueLife") ) : stats.getFloat("RedLife") ) ) )){
            case 10:case 9:
                if(active){
                    sprite.setColor(1f, 1f, 1f, 1f);
                }else{
                    sprite.setColor(0.6f, 0.6f, 0.6f, 1f);
                }
                break;
            case 8:case 7:  //low opacity
                if(active){
                    sprite.setColor(1f, 1f, 1f, 0.8f);
                }else{
                    sprite.setColor(0.6f, 0.6f, 0.6f, 0.8f);
                }
                break;
            case 6:case 5:  //lower opacity
                if(active){
                    sprite.setColor(1f, 1f, 1f, 0.6f);
                }else{
                    sprite.setColor(0.6f, 0.6f, 0.6f, 0.6f);
                }
                break;
            case 4:case 3:  //low freq blink
                blink(0.5f);
                break;
            case 2:case 1:  //hi freq blink
                blink(0.18f);
                break;
        }

        sprite.draw(batch);
    }

    private void blink(float speed){
        ac = ac <= 1.0 ? ac + Gdx.graphics.getDeltaTime() : 0;
        if(active) {
            sprite.setColor(1f, 1f, 1f, (0.3f * MathUtils.sin(ac * (2*MathUtils.PI /speed)) + 0.5f));
        }else{
            sprite.setColor(0.6f, 0.6f, 0.6f, (0.3f * MathUtils.sin(ac * (2*MathUtils.PI/speed)) + 0.5f));
        }
    }

    int getPlace(){
        return place;
    }

    float getLife(){ return life;}

    int getColor(){ return color; }

    Body getBody(){
        return body;
    }
}
