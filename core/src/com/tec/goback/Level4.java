package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by sergiohernandezjr on 01/05/17.
 */

class Level4 extends Frame {

    private static final float SOPHIE_START_X = 10800;
    private static final float SOPHIE_START_Y = 220;
    private Sophie sophie;

    public static final float WIDTH_MAP = 11520;
    public static final float HEIGHT_MAP = 720;

    public static final float LEFT_LIMIT = 980;
    public static float RIGHT_LIMIT = 10900;


    private Texture sophieTexture;
    private Texture background01;
    private Texture background02;
    private Texture background03;

    // yellow orb
    private Sprite yellowOrb;
    private float yellowOrbXPosition = 10810;
    private float yellowOrbYPosition = 150;
    private OrbMovement currentYellowOrbState = OrbMovement.GOING_UP;
    private final float DISTANCE_YELLOW_ORB_SOPHIE = -160;

    // blue orb
    private Sprite blueOrb;
    private float blueOrbXPosition = 10800;
    private float blueOrbYPosition = 235;
    private OrbMovement currentBlueOrbState = OrbMovement.GOING_DOWN;
    private final float DISTANCE_BLUE_ORB_SOPHIE = 30;

    // red orb
    private Sprite redOrb;
    private float redOrbXPosition = 10820;
    private float redOrbYPosition = 320;
    private OrbMovement currentRedOrbState = OrbMovement.GOING_UP;
    private final float DISTANCE_RED_ORB_SOPHIE = 30;

    Level4(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);
    }

    @Override
    public void show() {
        super.show();
        textureInit();
        objectInit();


        sophie.setMovementState(Sophie.MovementState.WAKING_LEFT);
        sophie.sprite.setPosition(SOPHIE_START_X, SOPHIE_START_Y);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(new Input());
        //musicInit();

    }

    private void textureInit() {
        sophieTexture = aManager.get("Squirts/Sophie/SOPHIEWalk.png");
        background01 = aManager.get("WOODS/WOODSPanoramic1of2.png");
        background02 = aManager.get("MOUNTAINS/GoBackMOUNTAINSPanoramic.png");
        background03 = aManager.get("HARBOR/GoBackHARBORPanoramic.png");

        sophie = new Sophie(sophieTexture, 100,100);
        yellowOrb = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTYellowOrb.png"));
        blueOrb = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTBlueOrb.png"));
        redOrb = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTRedOrb.png"));

        yellowOrb.setColor(1.0f,1.0f,1.0f,0.8f);
        blueOrb.setColor(1.0f,1.0f,1.0f,0.4f);
        redOrb.setColor(1.0f,1.0f,1.0f,0.4f);
    }

    private void objectInit() {
        batch = new SpriteBatch();
        batch.begin();
        batch.draw(background01,7680,0);
        batch.draw(background02,3840,0);
        batch.draw(background03,0,0);
        camera.position.set(7680+background01.getWidth()-HALFW,camera.position.y, 0);
        camera.update();
        batch.end();
    }

    @Override
    public void render(float delta) {
        cls();

        batch.setProjectionMatrix(super.camera.combined);

        if(state == GameState.CLUE){
            clueStage.draw();
            Gdx.input.setInputProcessor(clueStage);
        }else if(state == GameState.STATS){
            statsStage.sophieCoins.setText(Integer.toString(statsStage.statsPrefs.getInteger("Coins")));
            statsStage.yellowXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.blueXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.redXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.draw();
            Gdx.input.setInputProcessor(inputMultiplexer);
        }else if (state==GameState.PAUSED) {
            pauseStage.draw();
            Gdx.input.setInputProcessor(pauseStage);
        }else if(state==GameState.PLAYING){
            batch.begin();
            Gdx.input.setInputProcessor(new Input());

            batch.draw(background01,7680,0);
            batch.draw(background02,3840,0);
            batch.draw(background03,0,0);
            batch.draw(pauseButton,camera.position.x+HALFW-pauseButton.getWidth(),camera.position.y-HALFH);

            sophie.draw(batch);
            sophie.update();

            yellowOrb.draw(batch);
            blueOrb.draw(batch);
            redOrb.draw(batch);

            moveYellowOrb(delta);
            moveBlueOrb(delta);
            moveRedOrb(delta);

            updateCamera();
            batch.end();
        }



    }

    private void moveRedOrb(float delta){
        if(redOrbYPosition >= 335){ // 155
            currentRedOrbState = OrbMovement.GOING_DOWN;
        }else if(redOrbYPosition <= 305){ //145
            currentRedOrbState = OrbMovement.GOING_UP;
        }
        redOrbXPosition = redOrb.getX();

        if(currentRedOrbState == OrbMovement.GOING_UP){
            redOrbYPosition += delta*30;
        }else if(currentRedOrbState == OrbMovement.GOING_DOWN){
            redOrbYPosition -= delta*30;
        }

        redOrb.setPosition(redOrbXPosition,redOrbYPosition);
        if(redOrb.getX()<(sophie.sprite.getX()-redOrb.getWidth()-DISTANCE_RED_ORB_SOPHIE)){
            redOrb.setPosition(sophie.sprite.getX()-redOrb.getWidth()-DISTANCE_RED_ORB_SOPHIE+1,redOrb.getY());
        }else if((sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_RED_ORB_SOPHIE)<redOrb.getX()){
            redOrb.setPosition(sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_RED_ORB_SOPHIE-1, redOrb.getY());
        }
    }

    private void moveBlueOrb(float delta){
        if(blueOrbYPosition >= 230){ // 155
            currentBlueOrbState = OrbMovement.GOING_DOWN;
        }else if(blueOrbYPosition <= 200){ //145
            currentBlueOrbState = OrbMovement.GOING_UP;
        }
        blueOrbXPosition = blueOrb.getX();

        if(currentBlueOrbState == OrbMovement.GOING_UP){
            blueOrbYPosition += delta*30;
        }else if(currentBlueOrbState == OrbMovement.GOING_DOWN){
            blueOrbYPosition -= delta*30;
        }

        blueOrb.setPosition(blueOrbXPosition,blueOrbYPosition);
        if(blueOrb.getX()<(sophie.sprite.getX()-blueOrb.getWidth()-DISTANCE_BLUE_ORB_SOPHIE)){
            blueOrb.setPosition(sophie.sprite.getX()-blueOrb.getWidth()-DISTANCE_BLUE_ORB_SOPHIE+1,blueOrb.getY());
        }else if((sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_BLUE_ORB_SOPHIE)<blueOrb.getX()){
            blueOrb.setPosition(sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_BLUE_ORB_SOPHIE-1, blueOrb.getY());
        }
    }

    private void moveYellowOrb(float delta){
        if(yellowOrbYPosition >= 155){ // 155
            currentYellowOrbState = OrbMovement.GOING_DOWN;
        }else if(yellowOrbYPosition <= 125){ //145
            currentYellowOrbState = OrbMovement.GOING_UP;
        }
        yellowOrbXPosition = yellowOrb.getX();

        if(currentYellowOrbState == OrbMovement.GOING_UP){
            yellowOrbYPosition += delta*30;
        }else if(currentYellowOrbState == OrbMovement.GOING_DOWN){
            yellowOrbYPosition -= delta*30;
        }

        yellowOrb.setPosition(yellowOrbXPosition,yellowOrbYPosition);
        if(yellowOrb.getX()<(sophie.sprite.getX()-yellowOrb.getWidth()-DISTANCE_YELLOW_ORB_SOPHIE)){
            yellowOrb.setPosition(sophie.sprite.getX()-yellowOrb.getWidth()-DISTANCE_YELLOW_ORB_SOPHIE+1,yellowOrb.getY());
        }else if((sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_YELLOW_ORB_SOPHIE)<yellowOrb.getX()){
            yellowOrb.setPosition(sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_YELLOW_ORB_SOPHIE-1, yellowOrb.getY());
        }
    }

    private void updateCamera() {
        float posX = sophie.sprite.getX()+sophie.sprite.getWidth()/2;
        // Si está en la parte 'media'
        if (posX>=HALFW && posX<=WIDTH_MAP-HALFW) {
            // El personaje define el centro de la cámara
            camera.position.set((int)posX, camera.position.y, 0);
        } else if (posX>WIDTH_MAP-HALFW) {    // Si está en la última mitad
            // La cámara se queda a media pantalla antes del fin del mundo  :)
            camera.position.set(WIDTH_MAP-HALFW, camera.position.y, 0);
        } else if ( posX<HALFH ) { // La primera mitad
            camera.position.set(HALFW, HALFH,0);
        }

        camera.update();
    }

    private class Input implements InputProcessor {
        private Vector3 v = new Vector3();
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            v.set(screenX,screenY,0);
            camera.unproject(v);

            Gdx.app.log(""+v.x, v.y+"");

            if(camera.position.x - v.x < -522 && v.y < 135){
                state = GameState.PAUSED;
            }

            if(sophie.getMovementState()==Sophie.MovementState.STILL_LEFT||sophie.getMovementState()==Sophie.MovementState.STILL_RIGHT) {
                if (v.x >= camera.position.x) {
                    sophie.setMovementState(Sophie.MovementState.MOVE_RIGHT);
                } else {
                    sophie.setMovementState(Sophie.MovementState.MOVE_LEFT);
                }
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if(sophie.getMovementState()==Sophie.MovementState.MOVE_LEFT)
                sophie.setMovementState(Sophie.MovementState.STILL_LEFT);
            else if(sophie.getMovementState() == Sophie.MovementState.MOVE_RIGHT)
                sophie.setMovementState(Sophie.MovementState.STILL_RIGHT);
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private enum OrbMovement{
        GOING_UP,
        GOING_DOWN
    }
}
