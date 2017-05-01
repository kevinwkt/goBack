package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by gerry on 4/30/17.
 */

class ArcadeEyes implements IArcadeBoss{
    private World world;

    private Eyes[] eyes = new Eyes[3];

    ArcadeEyes(World world, Texture yellowTx, Texture blueTx, Texture redTx) {
        eyes[0] = new Eyes(world, 1, new Sprite(yellowTx));
        eyes[1] = new Eyes(world, 2, new Sprite(blueTx));
        eyes[2] = new Eyes(world, 3, new Sprite(redTx));
    }

    public void move(){
        //opacity them and set active
    }

    public boolean getHurtDie(int color, float damage){
        for(Eyes e: eyes){
            if(!e.getHurtDie(1,0)) return false;
        }
        return true;
    }

    public void draw(SpriteBatch batch) {
        for(Eyes e: eyes){
            e.draw(batch);
        }
    }


    private class Eyes implements IArcadeBoss{
        private int color;
        private World world;
        private Sprite sprite;

        private float life = ArcadeValues.redBossLife;
        public Body body;


        private BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("Physicshit.json"));
        Eyes(World world, int color, Sprite sprite){
            this.world = world;
            this.color = color;
            this.sprite = sprite;

            BodyDef bodyDef = new BodyDef();  bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(
                (color == 1 || color == 2 ? (color == 1 ? ArcadeValues.meterspelletOriginX - 2 :  ArcadeValues.meterspelletOriginX + 1 ) : ArcadeValues.meterspelletOriginX + 4),
                    ArcadeValues.meterspelletOriginY + 2
            );

            body = world.createBody(bodyDef);
            body.setLinearVelocity(1f, 0f);
            body.setUserData(this);


            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.density = 0f;
            fixtureDef.restitution = 0f;
            fixtureDef.friction = 0f;
            fixtureDef.filter.categoryBits = ArcadeValues.enemyCat;
            fixtureDef.filter.maskBits = ArcadeValues.enemyMask;

            loader.attachFixture(body, "bossEyes", fixtureDef, 3.4f);
        }

        public boolean getHurtDie(int color, float damage) {
            life -= color != this.color ? damage : (damage * 2);
            return life <= 0.0f;
        }

        public void move() {
            if(body.getPosition().x > ArcadeValues.pxToMeters(1280) || body.getPosition().x < 0.0f) body.setLinearVelocity(-1, 0);

        }

        public void draw(SpriteBatch batch) {
            if(color == 1)Gdx.app.log("LAMEGACONCHA", "at: "+body.getPosition().x);
            move();
            sprite.setPosition(
                    ArcadeValues.metersToPx(body.getPosition().x),
                    ArcadeValues.metersToPx(body.getPosition().y)
            );
            sprite.draw(batch);
        }
    }
}
