package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by sergiohernandezjr on 18/03/17.
 * Con ayuda de DON PABLO
 */
public class Level1 extends Frame {

    private Sophie sophie;
    private Texture sophieTexture;

    private Boolean bossMode = false;

    // lizard
    private Sprite lizardSpr;
    private Sprite lizardArmSpr;
    private Sprite lizardLegSpr;
    private float lizardYPosition = 850; // 850
    private float lizardAcceleration = 5;
    private boolean rotateLeftArm = true;
    private float lizardXPosition = 3200;

    // newspaper
    private Sprite newspaperSpr;

    // orb
    private Sprite yellowOrb;
    private boolean foundOrb = false;
    private float orbXPosition = 1350;
    private float orbYPosition = 150;
    private OrbMovement currentOrbState = OrbMovement.GOING_DOWN;

    // Música
    private Music bgMusic;
    private Sound fx;

    //BG
    Texture background;


    // batch
    private SpriteBatch batch;

    //for dialogue
    private Dialogue dialogue;
    private boolean dialogueOn = false;
    private float dialogueTime = 0;
    private int dialogueSprite = 1; // 1

    private Texture oldmanEyesClosed;
    private Texture oldmanStand;
    private Texture oldmanNod;
    private Texture oldmanEyesOpened;

    private Texture sophieBlink;
    private Texture sophieConcerned;
    private Texture sophieNormal;
    private Texture sophieSurprised;


    private Sprite oldmanEyesClosedSpr;
    private Sprite oldmanStandSpr;
    private Sprite oldmanNodSpr;
    private Sprite oldmanEyesOpenedSpr;
    private Sprite sophieBlinkSpr;
    private Sprite sophieConcernedSpr;
    private Sprite sophieNormalSpr;
    private Sprite sophieSurprisedSpr;

    public static final float LEFT_LIMIT = 980;
    public static float RIGHT_LIMIT = 3700;

    private final float SOPHIE_START_X=1850;
    private final float SOPHIE_START_Y=220;

    public static final float WIDTH_MAP = 3840;
    public static final float HEIGHT_MAP = 720;

    private final float DISTANCE_ORB_SOPHIE = 40;

    Preferences pref = Gdx.app.getPreferences("getLevel");

    public Level1(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);
    }

    @Override
    public void show() {
        super.show();
        textureInit();
        objectInit();


        sophie.setMovementState(Sophie.MovementState.SLEEPING);
        sophie.sprite.setPosition(SOPHIE_START_X, SOPHIE_START_Y);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(new Input());
    }

    private void textureInit() {
        sophieTexture = new Texture("Squirts/Sophie/SOPHIEWalk.png");
        sophie = new Sophie(sophieTexture, 100,100);

        sophieBlink = aManager.get("SOPHIE/DIALOGUESophieBlink.png");
        sophieConcerned = aManager.get("SOPHIE/DIALOGUESophieConcern.png");
        sophieNormal = aManager.get("SOPHIE/DIALOGUESophieNormal.png");
        sophieSurprised = aManager.get("SOPHIE/DIALOGUESophieSurprise.png");
        oldmanEyesClosed = aManager.get("OLDMAN/STILL/OLDMANStill00.png");
        oldmanStand = aManager.get("OLDMAN/STILL/OLDMANStill01.png");
        oldmanNod = aManager.get("OLDMAN/STILL/OLDMANStill02.png");
        oldmanEyesOpened = aManager.get("OLDMAN/STILL/OLDMANStill03.png");

        oldmanEyesClosedSpr = new Sprite(oldmanEyesClosed);
        oldmanStandSpr = new Sprite(oldmanStand);
        oldmanNodSpr = new Sprite(oldmanNod);
        oldmanEyesOpenedSpr = new Sprite(oldmanEyesOpened);
        sophieBlinkSpr = new Sprite(sophieBlink);
        sophieConcernedSpr = new Sprite(sophieConcerned);
        sophieNormalSpr = new Sprite(sophieNormal);
        sophieSurprisedSpr = new Sprite(sophieSurprised);

        yellowOrb = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTYellowOrb.png"));
        newspaperSpr = new Sprite((Texture)aManager.get("CLUES/Newspaper/CLUESNewspaper.png"));
        lizardSpr = new Sprite((Texture)aManager.get("BOSS/IGUANA/BOSSIguanaBody.png"));
        lizardArmSpr = new Sprite((Texture)aManager.get("BOSS/IGUANA/BOSSIguanaFrontLeg.png"));
        lizardLegSpr = new Sprite((Texture)aManager.get("BOSS/IGUANA/BOSSIguanaBackLeg.png"));

        //Background
        background = new Texture("HARBOR/GoBackHARBORPanoramic.png");
    }

    private void objectInit() {
        batch = new SpriteBatch();

        yellowOrb.setPosition(orbXPosition,orbYPosition);
        dialogue = new Dialogue(aManager);
        oldmanEyesOpenedSpr.setPosition(900,220);
    }

    @Override
    public void render(float delta) {
        cls();
        batch.setProjectionMatrix(super.camera.combined);
        batch.begin();

        batch.draw(background,0,0);
        drawNewspaper(batch);
        sophie.draw(batch);

        batch.draw(pauseButton,camera.position.x+HALFW-pauseButton.getWidth(),camera.position.y-HALFH);
        sophie.update();

        yellowOrb.draw(batch);

        if (state==GameState.PAUSED) {
            pauseStage.draw();
            Gdx.input.setInputProcessor(pauseStage);
        }else if(state==GameState.PLAYING){
            Gdx.input.setInputProcessor(new Input());
        }
        moveOrb(delta);
        checkOrbCollision();
        ckeckOldManCollision(delta);
        checkNewsPaperCollision();
        if(bossMode){
            drawLizard(delta);
        }

        updateCamera();

        batch.end();

    }

    private void changeScreen() {
        pref.putInteger("level",2);
        pref.flush();
        app.setScreen(new Fade(app, LoaderState.ARCADE));
        //bgMusic.stop();
        this.dispose();
    }

    private void drawLizard(float delta) {
        if(lizardYPosition > 255){
            lizardYPosition -= delta * lizardAcceleration;
            lizardAcceleration += 9.5;
        }else{
            lizardAcceleration += .5;
            if(lizardAcceleration > 870){
                lizardXPosition += delta * 100;

                if(lizardXPosition > 3790){
                    changeScreen();
                }
            }
        }

        lizardSpr.setPosition(lizardXPosition,lizardYPosition);
        lizardSpr.draw(batch);
        lizardLegSpr.setPosition(lizardXPosition+500,lizardYPosition-250);
        lizardLegSpr.draw(batch);
        lizardArmSpr.setPosition(lizardXPosition + 170,lizardYPosition-110);
        lizardArmSpr.draw(batch);
        lizardArmSpr.setOrigin(209,238);
        if (rotateLeftArm) {
            lizardArmSpr.rotate((float) 0.5);
        } else {
            lizardArmSpr.rotate((float) -0.5);
        }
        if (lizardArmSpr.getRotation() > 5) {
            rotateLeftArm = false;
        } else if (lizardArmSpr.getRotation() < -20) {
            rotateLeftArm = true;
        }
    }

    private void checkNewsPaperCollision() {
        if(sophie.sprite.getBoundingRectangle().contains(newspaperSpr.getX(),newspaperSpr.getY())){
            RIGHT_LIMIT = 3025;
            bossMode = true;
            dialogueOn = true;
        }
    }

    private void drawNewspaper(Batch batch) {
        if(dialogueSprite > 4){
            newspaperSpr.setPosition(3090,220);
            newspaperSpr.draw(batch);
        }
    }

    private void ckeckOldManCollision(float delta) {

        if(sophie.sprite.getBoundingRectangle().contains(oldmanEyesOpenedSpr.getX()+(sophie.sprite.getWidth()/2 + 7), oldmanEyesOpenedSpr.getY()) && dialogueSprite <= 4){
            dialogueTime += delta;
            dialogueOn = true;
            switch (dialogueSprite){
                case 1:
                    dialogue.makeText(batch, "He’s taking me back…. He’s taking me back….. He surely is taking me back You! It’s been a long long time. The boat will be coming back soon,  I hope what I have is enough. Will you be going back, too?", oldmanStandSpr, sophieNormalSpr, true, camera.position.x/2-oldmanEyesOpenedSpr.getWidth());
                    break;
                case 2:
                    dialogue.makeText(batch, "I don’t know… Where am I…?", oldmanStandSpr, sophieNormalSpr, true, camera.position.x/2-oldmanEyesOpenedSpr.getWidth());
                    break;
                case 3:
                    dialogue.makeText(batch, "You sweet girl, it really is a shame. I’ve done some terrible things, but I guess I can help somebody for a change. Take this, it will help you on your journey.", oldmanStandSpr, sophieNormalSpr, false,camera.position.x/2-oldmanEyesOpenedSpr.getWidth());
                    break;
                case 4:
                    dialogue.makeText(batch, "You need to pay to ride the boat, to go back. \n I used one too, but I’m going back and they can’t come on board.", oldmanNodSpr, sophieNormalSpr, false,camera.position.x/2-oldmanEyesOpenedSpr.getWidth());
                    break;
                default:
                    break;
            }

            if(dialogueTime > 2.5){ // 2.5
                dialogueSprite += 1;
                dialogueTime = 0;
                if(dialogueSprite >= 4){
                    dialogueOn = false;
                }

            }
        }

    }

    private void checkOrbCollision() {

        if(!foundOrb){
            if(yellowOrb.getBoundingRectangle().contains(sophie.sprite.getX()+yellowOrb.getBoundingRectangle().getWidth()/2,sophie.sprite.getY())){
                foundOrb = true;
                sophie.setMovementState(Sophie.MovementState.WAKING);
            }
        }else{
            if(yellowOrb.getX()<(sophie.sprite.getX()-yellowOrb.getWidth()-DISTANCE_ORB_SOPHIE)){
                yellowOrb.setPosition(sophie.sprite.getX()-yellowOrb.getWidth()-DISTANCE_ORB_SOPHIE+1,yellowOrb.getY());
            }else if((sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_ORB_SOPHIE)<yellowOrb.getX()){
                yellowOrb.setPosition(sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_ORB_SOPHIE-1, yellowOrb.getY());
            }

            oldmanEyesOpenedSpr.draw(batch);

        }
    }

    private void moveOrb(float delta) {

        if(orbYPosition >= 155){ // 155
            currentOrbState = OrbMovement.GOING_DOWN;
        }else if(orbYPosition <= 145){ //145
            currentOrbState = OrbMovement.GOING_UP;
        }

        if(!foundOrb){
            orbXPosition += delta * 100; // 100

        }else{
            orbXPosition = yellowOrb.getX();
        }

        if(currentOrbState == OrbMovement.GOING_UP){
            orbYPosition += delta*30;
        }else if(currentOrbState == OrbMovement.GOING_DOWN){
            orbYPosition -= delta*30;
        }

        yellowOrb.setPosition(orbXPosition,orbYPosition);


    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
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
        aManager.unload("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTYellowOrb.png");
        aManager.unload("OLDMAN/STILL/OLDMANStill00.png");
        aManager.unload("OLDMAN/STILL/OLDMANStill01.png");
        aManager.unload("OLDMAN/STILL/OLDMANStill02.png");
        aManager.unload("OLDMAN/STILL/OLDMANStill03.png");
        aManager.unload("SOPHIE/DIALOGUESophieBlink.png");
        aManager.unload("SOPHIE/DIALOGUESophieConcern.png");
        aManager.unload("SOPHIE/DIALOGUESophieNormal.png");
        aManager.unload("SOPHIE/DIALOGUESophieSurprise.png");
        aManager.unload("CLUES/Newspaper/CLUESNewspaper.png");
        aManager.unload("BOSS/IGUANA/BOSSIguanaBody.png");
        aManager.unload("BOSS/IGUANA/BOSSIguanaBackLeg.png");
        aManager.unload("BOSS/IGUANA/BOSSIguanaFrontLeg.png");
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

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


    private enum OrbMovement{
        GOING_UP,
        GOING_DOWN
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

            if(sophie.getMovementState()==Sophie.MovementState.STILL_LEFT||sophie.getMovementState()==Sophie.MovementState.STILL_RIGHT) {
                if(sophie.sprite.getX() - v.x < -522 && v.y < 135){
                    state = GameState.PAUSED;
                }else{
                    if(!dialogueOn){
                        if (v.x >= camera.position.x) {
                            sophie.setMovementState(Sophie.MovementState.MOVE_RIGHT);
                        } else {
                            sophie.setMovementState(Sophie.MovementState.MOVE_LEFT);
                        }
                    }

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
}
