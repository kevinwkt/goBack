package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by kevin on 3/20/2017.
 */

class ArcadeArrow extends Enemy {

    private int walkCounter;
    private int walkLimit;
    private boolean walkCond=true;

    ArcadeArrow(World world, int type,int arcadeOr3, int leftOrRight,Texture tx){
        super(world,type,arcadeOr3,leftOrRight,tx); //0 if its only for arcade, 1 if its for level 3
        if(leftRight==1) sprite.rotate(90);
        else sprite.rotate(-90);
        if(arcadeOr3==1) {
            switch (type) {
                case 0:
                    SPEED = 1f;
                    if (leftOrRight == 1) body.setLinearVelocity(-SPEED, 0f);
                    if (leftOrRight == 0) body.setLinearVelocity(SPEED, 0f);
                    break;
                case 1:
                    SPEED = 2f;
                    if (leftOrRight == 1) body.setLinearVelocity(-SPEED, 0f);
                    if (leftOrRight == 0) body.setLinearVelocity(SPEED, 0f);
                    break;
                case 2:
                    SPEED = 3f;
                    if (leftOrRight == 1) body.setLinearVelocity(-SPEED, 0f);
                    if (leftOrRight == 0) body.setLinearVelocity(SPEED, 0f);
                    break;
            }
        }else if(arcadeOr3==0){     //From arcade
            SPEED=2f;
            if(leftOrRight==0) //from left
            body.setLinearVelocity(SPEED,0);
            else if(leftOrRight==1) //from right
            body.setLinearVelocity(-SPEED,0);
        }
    }

    void fixturer(float density, float restitution) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(ArcadeValues.pxToMeters(100f), ArcadeValues.pxToMeters(35f));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;

        fixtureDef.filter.categoryBits = ArcadeValues.enemyCat; //its category
        fixtureDef.filter.maskBits = ArcadeValues.enemyMask; //or of its category with colliding categories

        loader.attachFixture(body,"arrowRight",fixtureDef,0.25f);
        if(leftRight==1) {
            body.setTransform(body.getPosition() ,90*MathUtils.degreesToRadians);
        }
        if(leftRight==0) {
            body.setTransform(body.getPosition(),-90*MathUtils.degreesToRadians);
        }
    }

    void draw(SpriteBatch batch) {
        timeframe +=Gdx.graphics.getDeltaTime();
        sprite.setPosition(ArcadeValues.metersToPx(body.getPosition().x+0.54f), ArcadeValues.metersToPx(body.getPosition().y-0.81f));
        sprite.draw(batch);
    }
}

