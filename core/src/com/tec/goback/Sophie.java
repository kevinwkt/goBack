package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by kevin on 3/19/2017.
 */

class Sophie extends Squirt
{
    private final float vx = 4;      // Horizontal Velocity

    private Animation<TextureRegion> walking;         // ANIMATIONS
    private Animation<TextureRegion> standby;
    private Animation<TextureRegion> dying;
    private Animation<TextureRegion> waking;

    private float timerchangeframewalk;
    private float timerchangeframestandby;
    private float timerchangeframedie;
    private float timerchangeframewake;

    Preferences pref=Gdx.app.getPreferences("getLevel");
    // TIME TO CHANGE FRAMES

    private MovementState currentstate = MovementState.STILL_RIGHT;

    // Recibe una imagen con varios frames (ver marioSprite.png)
    public Sophie(Texture textura, float x, float y) {
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);
        // La divide en 4 frames de 32x64 (ver marioSprite.png)
        TextureRegion[][] texturaPersonaje = texturaCompleta.split(160,160);
        // Crea la animación con tiempo de 0.15 segundos entre frames.
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
        //waking.setPlayMode(Animation.PlayMode.LOOP);
        //dying.setPlayMode(Animation.PlayMode.LOOP);

        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerchangeframewalk = 0;
        timerchangeframestandby=0;
        timerchangeframedie=0;
        timerchangeframewake=0;


        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO
        sprite.setPosition(x,y);    // Posición inicial
    }

    // Dibuja el personaje
    public void draw(SpriteBatch batch) {
        // Dibuja el personaje dependiendo del estadoMovimiento
        TextureRegion region;
        switch (currentstate) {
            //case HIT:
            case WAKING_RIGHT:
                timerchangeframewake +=Gdx.graphics.getDeltaTime();
                region=waking.getKeyFrame(timerchangeframewake);
                batch.draw(region,sprite.getX(),sprite.getY());

                if(waking.isAnimationFinished(timerchangeframewake))
                    currentstate=MovementState.STILL_RIGHT;
                break;
            case WAKING_LEFT:
                timerchangeframewake +=Gdx.graphics.getDeltaTime();
                region=waking.getKeyFrame(timerchangeframewake);
                if (!region.isFlipX()) {
                    region.flip(true,false);
                }
                batch.draw(region,sprite.getX(),sprite.getY());
                if(waking.isAnimationFinished(timerchangeframewake))
                    currentstate=MovementState.STILL_LEFT;
                break;
            case DYING:
                timerchangeframedie +=Gdx.graphics.getDeltaTime();
                region=dying.getKeyFrame(timerchangeframedie);
                batch.draw(region,sprite.getX(),sprite.getY());
                break;
            case MOVE_RIGHT:
            case MOVE_LEFT:
                timerchangeframewalk += Gdx.graphics.getDeltaTime();
                // Frame que se dibujará
                region = walking.getKeyFrame(timerchangeframewalk);
                if (currentstate== MovementState.MOVE_LEFT) {
                    if (!region.isFlipX()) {
                        region.flip(true,false);
                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true,false);
                    }
                }
                batch.draw(region,sprite.getX(),sprite.getY());
                break;
            case SLEEPING:
                region = waking.getKeyFrame(0);
                batch.draw(region,sprite.getX(),sprite.getY());
                break;
            case CREATING:
            case STILL_LEFT:
            case STILL_RIGHT:
                timerchangeframestandby += Gdx.graphics.getDeltaTime();
                region = standby.getKeyFrame(timerchangeframestandby);
                if (currentstate== MovementState.STILL_LEFT) {
                    if (!region.isFlipX()) {
                        region.flip(true,false);
                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true,false);
                    }
                }
                batch.draw(region,sprite.getX(),sprite.getY()); // Dibuja el sprite estático
                break;
        }
    }

    // Actualiza el sprite, de acuerdo al estadoMovimiento y estadoSalto
    public void update() {
        switch (currentstate) {
            case HIT:
            case MOVE_RIGHT:
            case MOVE_LEFT:
                moveHorizontal();
                break;
        }
    }

    // GO LEFT OR RIGHT
    private void moveHorizontal() {
        // HORIZONTAL MOVEMENT
        float newX = sprite.getX();
        // GO RIGHT
        if ( currentstate== MovementState.MOVE_RIGHT) {
            if ( /*NOTHING TO THE RIGHT, NO COLLISIONS*/true) {
                // Ejecutar movimiento horizontal
                newX += vx;
                // Prueba que no salga del mundo por la derecha
                int d= pref.getInteger("level");
                switch (d){
                    case 1:
                        if (newX <= Level1.RIGHT_LIMIT){
                            sprite.setX(newX);
                        }
                        break;
                    case 2:
                        if (/*newX <= Level2.WIDTH - sprite.getWidth()*/true) sprite.setX(newX);
                        break;
                    case 3:
                        if (/*newX <= Level3.WIDTH - sprite.getWidth()*/true) sprite.setX(newX);
                        break;
                    case 4:
                        if(newX<=Level4.RIGHT_LIMIT)
                            sprite.setX(newX);
                        break;
                }
            }
        }
        // GO LEFT
        if ( currentstate== MovementState.MOVE_LEFT) {
            if (/*NOTHING TO THE LEFT, NO COLLISIONS*/true) {
                newX -= vx;
                int d= pref.getInteger("level");
                switch (d){
                    case 1:
                        if (newX >= Level1.LEFT_LIMIT){
                            sprite.setX(newX);
                        }
                        break;
                    case 2:
                        if (/*newX <= Level2.WIDTH - sprite.getWidth()*/true) sprite.setX(newX);
                        break;
                    case 3:
                        if (/*newX <= Level3.WIDTH - sprite.getWidth()*/true) sprite.setX(newX);
                        break;
                    case 4:
                        if(newX>=Level4.LEFT_LIMIT)
                            sprite.setX(newX);
                        break;
                }
            }
        }
//        if ( currentstate== MovementState.HIT) {
//            newX -= vx;
//            int d= pref.getInteger("level");
//            switch (d){
//                case 1:
//                    if (newX <= Level1.WIDTH - sprite.getWidth()) sprite.setX(newX);
//                case 2:
//                    if (newX <= Level2.WIDTH - sprite.getWidth()) sprite.setX(newX);
//                case 3:
//                    if (newX <= Level3.WIDTH - sprite.getWidth()) sprite.setX(newX);
//            }
//        }
    }

    // Revisa si toca una moneda
//    public boolean collectObject(TiledMap mapa) {
//        // Revisar si toca una moneda (pies)
//        TiledMapTileLayer capa = (TiledMapTileLayer)mapa.getLayers().get(0);
//        int x = (int)(sprite.getX()/32);
//        int y = (int)(sprite.getY()/32);
//        TiledMapTileLayer.Cell celda = capa.getCell(x,y);
//        if (celda!=null ) {
//            Object tipo = celda.getTile().getProperties().get("tipo");
//            if ( "moneda".equals(tipo) ) {
//                capa.setCell(x,y,null);    // Borra la moneda del mapa
//                capa.setCell(x,y,capa.getCell(0,4)); // Cuadro azul en lugar de la moneda
//                return true;
//            }
//        }
//        return false;
//    }

    // Accesor de estadoMovimiento
    public MovementState getMovementState() {
        return currentstate;
    }

    // Modificador de estadoMovimiento
    public void setMovementState(MovementState ms) {
        this.currentstate = ms;
    }

    public enum MovementState {
        CREATING,
        WAKING_RIGHT,
        WAKING_LEFT,
        STILL_RIGHT,
        STILL_LEFT,
        HIT,
        MOVE_LEFT,
        MOVE_RIGHT,
        DYING,
        SLEEPING
    }
}
