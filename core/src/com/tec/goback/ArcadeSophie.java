package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/*
 * Created by gerry on 3/22/17.
 */
class ArcadeSophie {
    private Body body;
    private Sprite sprite;
    private float life = 100;
    private int color = 1;
    private Animation<TextureRegion> standby;
    private Animation<TextureRegion> walking;
    private Animation<TextureRegion> waking;
    private Animation<TextureRegion> dying;

    private float timerchangeframestandby;
    private float timerchangeframewalk;
    private float timerchangeframedie;
    private float timerchangeframewake;
    private Sophie.MovementState currentstate = Sophie.MovementState.STILL_RIGHT; //STILL_RIGHT


    ArcadeSophie(World world, Texture tx){
        TextureRegion texturaCompleta = new TextureRegion(tx);

        TextureRegion[][] texturaPersonaje = texturaCompleta.split(160,160);

        standby = new Animation(0.18f, texturaPersonaje[0][0], texturaPersonaje[0][1],
                texturaPersonaje[0][2], texturaPersonaje[0][3],texturaPersonaje[0][4],
                texturaPersonaje[0][5], texturaPersonaje[0][6]);
        walking = new Animation(0.06f, texturaPersonaje[0][7], texturaPersonaje[0][8],
                texturaPersonaje[0][9], texturaPersonaje[0][10],
                texturaPersonaje[0][11], texturaPersonaje[0][12],texturaPersonaje[0][13],
                texturaPersonaje[0][14], texturaPersonaje[0][15], texturaPersonaje[0][16],
                texturaPersonaje[0][17], texturaPersonaje[0][18], texturaPersonaje[0][19],
                texturaPersonaje[0][20],texturaPersonaje[0][21], texturaPersonaje[0][22],
                texturaPersonaje[0][23], texturaPersonaje[0][24],texturaPersonaje[0][25]);
        waking= new Animation(0.15f, texturaPersonaje[0][42], texturaPersonaje[0][41],
                texturaPersonaje[0][40], texturaPersonaje[0][39],
                texturaPersonaje[0][38], texturaPersonaje[0][37],texturaPersonaje[0][36],
                texturaPersonaje[0][35], texturaPersonaje[0][34], texturaPersonaje[0][33],
                texturaPersonaje[0][32], texturaPersonaje[0][31], texturaPersonaje[0][30],
                texturaPersonaje[0][29],texturaPersonaje[0][28], texturaPersonaje[0][27],
                texturaPersonaje[0][26]);
        dying= new Animation(0.1f, texturaPersonaje[0][26], texturaPersonaje[0][27],
                texturaPersonaje[0][28], texturaPersonaje[0][29],
                texturaPersonaje[0][30], texturaPersonaje[0][31],texturaPersonaje[0][32],
                texturaPersonaje[0][33], texturaPersonaje[0][34], texturaPersonaje[0][35],
                texturaPersonaje[0][36], texturaPersonaje[0][37], texturaPersonaje[0][38],
                texturaPersonaje[0][39],texturaPersonaje[0][40], texturaPersonaje[0][41],
                texturaPersonaje[0][42]);
        // Animación infinita
        standby.setPlayMode(Animation.PlayMode.LOOP);
        walking.setPlayMode(Animation.PlayMode.LOOP);

        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerchangeframewalk = 0;
        timerchangeframestandby=0;
        timerchangeframedie=0;
        timerchangeframewake=0;

        sprite = new Sprite(texturaPersonaje[0][0]);

        sprite.setCenter(ArcadeValues.pelletOriginX-100,ArcadeValues.pelletOriginY);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                ArcadeValues.meterspelletOriginX-7,
                ArcadeValues.meterspelletOriginY+(ArcadeValues.pxToMeters(tx.getHeight()/4))
        );

        body = world.createBody(bodyDef);
        fixturer(0.1f, 0.7f);
        body.setLinearVelocity(0f, 0f);
        body.setUserData(this);
    }

    private void fixturer(float density, float restitution) {

        //shape of girl
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                ArcadeValues.pxToMeters(sprite.getWidth()),
                ArcadeValues.pxToMeters(sprite.getHeight())
        );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;

        fixtureDef.filter.categoryBits = ArcadeValues.sophieCat; //its category
        fixtureDef.filter.maskBits = ArcadeValues.sophieMask; //or of its category with colliding categories

        body.createFixture(fixtureDef);
    }

    boolean getHurtDie(int type, float damage){
        if(color == type){
            life -= damage;
        }else{
            life -= damage * 2;
        }
        return life <= 0.0f;
    }

    void draw(SpriteBatch batch) {
        TextureRegion region;
        switch (currentstate) {
            case MOVE_RIGHT:
            case MOVE_LEFT:
                timerchangeframewalk += Gdx.graphics.getDeltaTime();
                region = walking.getKeyFrame(timerchangeframewalk);
                /*if (currentstate== Sophie.MovementState.MOVE_LEFT) {
                    if (!region.isFlipX()) {
                        region.flip(true,false);
                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true,false);
                    }
                }*/
                body.setLinearVelocity(1.5f, 0f);
                sprite.setCenter(
                        ArcadeValues.metersToPx(body.getPosition().x),
                        ArcadeValues.metersToPx(body.getPosition().y)
                );
                batch.draw(region,sprite.getX(),sprite.getY());
                break;
            case STILL_LEFT:
            case STILL_RIGHT:
                body.setLinearVelocity(0f, 0f);
                timerchangeframestandby += Gdx.graphics.getDeltaTime();
                region = standby.getKeyFrame(timerchangeframestandby);
                sprite.setCenter(
                        ArcadeValues.metersToPx(body.getPosition().x),
                        ArcadeValues.metersToPx(body.getPosition().y)
                );
                batch.draw(region,sprite.getX(),sprite.getY()); // Dibuja el sprite estático
                break;
        }



    }

    void setColor(int color){
        this.color = color;
    }

    public Sophie.MovementState getMovementState() {
        return currentstate;
    }

    // Modificador de estadoMovimiento
    public void setMovementState(Sophie.MovementState ms) {
        this.currentstate = ms;
    }

    public float getX(){
        return sprite.getX();
    }

    public float getY(){
        return sprite.getY();
    }

    public Sprite getSprite(){
        return sprite;
    }

    public enum MovementState {
        CREATING,
        WAKING,
        STILL_RIGHT,
        STILL_LEFT,
        HIT,
        MOVE_LEFT,
        MOVE_RIGHT,
        DYING,
        SLEEPING
    }
}
