package mx.itesm.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by pablo on 7/05/17.
 */

class LevelFINAL extends Frame {

    //sophie
    private Sophie sophie;
    private Texture sophieTexture;
    private Sprite sophieSpr;
    private final float SOPHIE_START_X=2400; // 1850
    private final float SOPHIE_START_Y=220;
    public static final float LEFT_LIMIT = 900;
    public static float RIGHT_LIMIT = 2500;

    // map
    public static final float WIDTH_MAP = 3840;
    public static final float HEIGHT_MAP = 720;

    // batch
    private SpriteBatch batch;

    //dialogue
    private boolean dialogueOn = false;

    // input processor
    private Input levelInput = new Input();

    // ian
    private Sprite ian;


    // blue orb
    private Sprite blueOrb;
    private Sprite blueOrbAnimal;
    private boolean foundOrb = false;
    private float blueOrbXPosition = 2500;
    private float blueOrbYPosition = 190;
    private LevelFINAL.OrbMovement currentBlueOrbState = OrbMovement.GOING_UP;
    private final float DISTANCE_BLUE_ORB_SOPHIE = -50;

    // yellow orb
    private Sprite yellowOrb;
    private Sprite yellowOrbAnimal;
    private float yellowOrbXPosition = 2570;
    private float yellowOrbYPosition = 140;
    private LevelFINAL.OrbMovement currentYellowOrbState = OrbMovement.GOING_DOWN;
    private final float DISTANCE_YELLOW_ORB_SOPHIE = 80;

    // red orb
    private Sprite redOrb;
    private Sprite redOrbAnimal;
    private float redOrbXPosition = 2520;
    private float redOrbYPosition = 280;
    private LevelFINAL.OrbMovement currentRedOrbState = LevelFINAL.OrbMovement.GOING_UP;
    private final float DISTANCE_RED_ORB_SOPHIE = 0;

    private float opacityOrbs = 1;
    private float opacityAnimals = 0;
    private boolean drawOrbs = true;
    private boolean changeAnimal = true;
    private float timeAcumulator = 0;
    private boolean orbTransformation = true;
    private float dialogueTime = 0;
    private int dialogueSprite = 1;
    private Dialogue dialogue;
    private GlyphLayout glyph = new GlyphLayout();
    private Sprite ianDialogue;
    private boolean moveForward = false;
    private boolean moveBoat = false;
    private Sprite boatSpr;

    LevelFINAL(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);
    }

    @Override
    public void show(){
        super.show();
        textureInit();
        objectInit();

        sophie.setMovementState(Sophie.MovementState.STILL_LEFT);
        sophie.sprite.setPosition(SOPHIE_START_X, SOPHIE_START_Y);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(levelInput);
    }

    private void textureInit() {
        //Background
        background = aManager.get("HARBOR/GoBackHARBORPanoramic.png");
        //sophie
        sophieTexture = aManager.get("Squirts/Sophie/SOPHIEComplete.png");
        sophieSpr = new Sprite(aManager.get("SOPHIE/DIALOGUESophieNormal.png",Texture.class));
        // ian
        ian = new Sprite(aManager.get("IAN/IanStill.png",Texture.class));
        ianDialogue = new Sprite(aManager.get("IAN/DIALOGUEIanConcern.png",Texture.class));
        // boat
        boatSpr = new Sprite(aManager.get("INTRO/concha.png",Texture.class));


        // blue orb
        blueOrb = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTBlueOrb.png"));
        blueOrb.setPosition(blueOrbXPosition,blueOrbYPosition);
        blueOrb.setColor(1.0f,1.0f,1.0f,1.0f);
        blueOrbAnimal = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/ARCADEBlueOrbBUNNY.png"));
        blueOrbAnimal.setPosition(blueOrbXPosition,blueOrbYPosition);
        blueOrbAnimal.setColor(1.0f,1.0f,1.0f,0.0f);

        // yellow orb
        yellowOrb = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTYellowOrb.png"));
        yellowOrb.setPosition(yellowOrbXPosition,yellowOrbYPosition);
        yellowOrb.setColor(1.0f,1.0f,1.0f,1.0f);
        yellowOrbAnimal = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/ARCADEYellowOrbDOG.png"));
        yellowOrbAnimal.setPosition(yellowOrbXPosition,yellowOrbYPosition);
        yellowOrbAnimal.setColor(1.0f,1.0f,1.0f,0.0f);


        // red orb
        redOrb = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTRedOrb.png"));
        redOrb.setPosition(redOrbXPosition,redOrbYPosition);
        redOrb.setColor(1.0f,1.0f,1.0f,1.0f);
        redOrbAnimal = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/ARCADERedOrbBIRD.png"));
        redOrbAnimal.setPosition(redOrbXPosition,redOrbYPosition);
        redOrbAnimal.setColor(1.0f,1.0f,1.0f,0.0f);
    }

    private void objectInit() {
        batch = new SpriteBatch();
        sophie = new Sophie(sophieTexture, 100,100);
        ian.setPosition(900,220);
        boatSpr.setPosition(400,180);
        boatSpr.flip(true,false);
        dialogue = new Dialogue(aManager);

    }

    @Override
    public void render(float delta) {

        cls();
        batch.setProjectionMatrix(super.camera.combined);
        batch.begin();

        if(soundPreferences.getBoolean("soundOn"))
            bgMusic.play();
        else
            bgMusic.stop();


        if(state == GameState.PLAYING){
            Gdx.input.setInputProcessor(levelInput);
            batch.draw(background,0,0);
            batch.draw(pauseButton,camera.position.x+HALFW-pauseButton.getWidth(),camera.position.y-HALFH);

            sophie.draw(batch);
            sophie.update();
            ian.draw(batch);
            boatSpr.draw(batch);

            blueOrbAnimal.draw(batch);
            yellowOrbAnimal.draw(batch);
            redOrbAnimal.draw(batch);

            timeAcumulator += delta;

            if(timeAcumulator > 1){
                opacityOrbs -= delta;

            }
            if(timeAcumulator > 1.5){
                opacityAnimals += delta;
            }
            if(opacityAnimals >= 1){
                changeAnimal = false;
                orbTransformation = false;
            }
            if(opacityOrbs <= 0.0f){
                drawOrbs = false;
            }
            if(changeAnimal){
                blueOrbAnimal.setColor(1f, 1f, 1f, opacityAnimals);
                redOrbAnimal.setColor(1f, 1f, 1f, opacityAnimals);
                yellowOrbAnimal.setColor(1f, 1f, 1f, opacityAnimals);
            }
            if(drawOrbs){
                blueOrb.draw(batch);
                yellowOrb.draw(batch);
                redOrb.draw(batch);
                blueOrb.setColor(1f, 1f, 1f, opacityOrbs);
                redOrb.setColor(1f, 1f, 1f, opacityOrbs);
                yellowOrb.setColor(1f, 1f, 1f, opacityOrbs);
            }
            moveYellowOrb(delta);
            moveRedOrb(delta);
            moveBlueOrb(delta);
            checkIanPosition(delta);
            if(dialogueOn && dialogueSprite > 9){
                endGame();
            }
            boatMovement();



            updateCamera();
        }else if(state == GameState.CLUE){
            clueStage.draw();
            Gdx.input.setInputProcessor(clueStage);
        }else if(state == GameState.STATS){
            statsStage.sophieCoins.setText(Integer.toString(statsStage.statsPrefs.getInteger("Coins")));
            statsStage.yellowXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.blueXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.redXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.draw();
            Gdx.input.setInputProcessor(inputMultiplexer);
        }else if(state==GameState.PAUSED){
            pauseStage.draw();
            Gdx.input.setInputProcessor(pauseStage);
        }

        batch.end();
    }

    private void boatMovement() {
        if(moveBoat){
            boatSpr.setPosition(boatSpr.getX()-2,boatSpr.getY());
            ian.setPosition(ian.getX()-2,ian.getY());
            Gdx.app.log("ian",ian.getX()+"");
            if(ian.getX()<= 15){
                changeScreen();
            }
        }
    }

    private void changeScreen() {
        app.setScreen(new Fade(app, LoaderState.MAINMENU));
        this.dispose();
    }

    private void endGame() {
        if(sophie.sprite.getX() <= 980){
            if(!moveForward && !moveBoat){
                sophie.sprite.setPosition(sophie.sprite.getX()+10,sophie.sprite.getY());
            }
        }else{
            moveForward = true;
        }
        if(moveForward){
            if (sophie.sprite.getX() >= 850 ) {
                sophie.sprite.setPosition(sophie.sprite.getX()-10,sophie.sprite.getY());
                ian.setPosition(ian.getX()-15,ian.getY());
            }else{
                moveForward = false;
                moveBoat = true;
            }


        }

    }

    private void checkIanPosition(float delta) {
        if (sophie.sprite.getX() <= LEFT_LIMIT) {
            dialogueOn = true;
            dialogueTime += delta;

            switch (dialogueSprite){

                case 1:
                    dialogue.makeText(glyph, batch, "No, no, this can’t be.", ianDialogue, "Ian", sophieSpr, "Sophie", true, camera.position.x);
                    break;
                case 2:
                    dialogue.makeText(glyph, batch, "SOPHIE? Is that you? I’ve missed you so much, I lost you, and then after they found Ana… my poor baby, I couldn't bare living. ", ianDialogue, "Ian", sophieSpr, "Sophie",  false, camera.position.x);
                    break;
                case 3:
                    dialogue.makeText(glyph, batch, "What are you talking about?", ianDialogue, "Ian", sophieSpr, "Sophie",  true, camera.position.x);
                    break;
                case 4:
                    dialogue.makeText(glyph, batch, "Our daughter’s gone...", ianDialogue, "Ian", sophieSpr, "Sophie",  false, camera.position.x);
                    break;
                case 5:
                    dialogue.makeText(glyph, batch, "No. No… She’s not gone… she’s here, that’s what that skull was referring to. She’s here. I can get er back.", ianDialogue, "Ian", sophieSpr, "Sophie (Crying)",  true, camera.position.x);
                    break;
                case 6:
                    dialogue.makeText(glyph, batch, "No, SOPHIE, you can’t.", ianDialogue, "Ian", sophieSpr, "Sophie",  false, camera.position.x);
                    break;
                case 7:
                    dialogue.makeText(glyph, batch, "Yes, I can get her back.", ianDialogue, "Ian", sophieSpr, "Sophie",  true, camera.position.x);
                    break;
                case 8:
                    dialogue.makeText(glyph, batch, "SOPHIE! NO! SOPHIE!", ianDialogue, "Ian", sophieSpr, "Sophie",  false, camera.position.x);
                    break;
                case 9:
                    dialogue.makeText(glyph, batch, "She’s here. I can feel it. We can go back. ", ianDialogue, "Ian", sophieSpr, "Sophie",  true, camera.position.x);
                    break;
                default:
                    break;
            }

            if(dialogueTime > 6){

                dialogueSprite += 1;
                dialogueTime = 0;
                if(dialogueSprite >= 9){
                    dialogueOn = false;

                }

            }

        }
    }

    private void moveRedOrb(float delta) {
        if(redOrbYPosition >= 155){ // 155
            currentRedOrbState = OrbMovement.GOING_DOWN;
        }else if(redOrbYPosition <= 125){ //145
            currentRedOrbState = OrbMovement.GOING_UP;
        }
        redOrbXPosition = redOrbAnimal.getX();

        if(currentRedOrbState == OrbMovement.GOING_UP){
            //redOrbYPosition += delta*30;
        }else if(currentRedOrbState == OrbMovement.GOING_DOWN){
            //redOrbYPosition -= delta*30;
        }

        redOrbAnimal.setPosition(redOrbXPosition,redOrbYPosition);
        if(redOrbAnimal.getX()<(sophie.sprite.getX()-redOrbAnimal.getWidth()-DISTANCE_RED_ORB_SOPHIE)){
            redOrbAnimal.setPosition(sophie.sprite.getX()-redOrbAnimal.getWidth()-DISTANCE_RED_ORB_SOPHIE+1,redOrbAnimal.getY());
        }else if((sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_RED_ORB_SOPHIE)<redOrbAnimal.getX()){
            redOrbAnimal.setPosition(sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_RED_ORB_SOPHIE-1, redOrbAnimal.getY());
        }

    }

    private void moveYellowOrb(float delta) {
        if(yellowOrbYPosition >= 155){ // 155
            currentYellowOrbState = OrbMovement.GOING_DOWN;
        }else if(yellowOrbYPosition <= 125){ //145
            currentYellowOrbState = OrbMovement.GOING_UP;
        }
        yellowOrbXPosition = yellowOrbAnimal.getX();

        if(currentYellowOrbState == OrbMovement.GOING_UP){
            //yellowOrbYPosition += delta*30;
        }else if(currentYellowOrbState == OrbMovement.GOING_DOWN){
            //yellowOrbYPosition -= delta*30;
        }

        yellowOrbAnimal.setPosition(yellowOrbXPosition,yellowOrbYPosition);

        if(yellowOrbAnimal.getX()<(sophie.sprite.getX()-yellowOrbAnimal.getWidth()-DISTANCE_YELLOW_ORB_SOPHIE)){
            yellowOrbAnimal.setPosition(sophie.sprite.getX()-yellowOrbAnimal.getWidth()-DISTANCE_YELLOW_ORB_SOPHIE+1,yellowOrbAnimal.getY());
        }else if((sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_YELLOW_ORB_SOPHIE)<yellowOrbAnimal.getX()){
            yellowOrbAnimal.setPosition(sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_YELLOW_ORB_SOPHIE-1, yellowOrbAnimal.getY());
        }

    }

    private void moveBlueOrb(float delta) {
        if(blueOrbYPosition >= 155){ // 155
            currentBlueOrbState = OrbMovement.GOING_DOWN;
        }else if(blueOrbYPosition <= 125){ //145
            currentBlueOrbState = OrbMovement.GOING_UP;
        }
        blueOrbXPosition = blueOrbAnimal.getX();

        if(currentBlueOrbState == OrbMovement.GOING_UP){
            //blueOrbYPosition += delta*30;
        }else if(currentBlueOrbState == OrbMovement.GOING_DOWN){
            //blueOrbYPosition -= delta*30;
        }

        blueOrbAnimal.setPosition(blueOrbXPosition,blueOrbYPosition);
        if(blueOrbAnimal.getX()<(sophie.sprite.getX()-blueOrbAnimal.getWidth()-DISTANCE_BLUE_ORB_SOPHIE)){
            blueOrbAnimal.setPosition(sophie.sprite.getX()-blueOrbAnimal.getWidth()-DISTANCE_BLUE_ORB_SOPHIE+1,blueOrbAnimal.getY());
        }else if((sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_BLUE_ORB_SOPHIE)<blueOrbAnimal.getX()){
            blueOrbAnimal.setPosition(sophie.sprite.getX()+sophie.sprite.getWidth()+DISTANCE_BLUE_ORB_SOPHIE-1, blueOrbAnimal.getY());
        }

    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        if(bgMusic != null){
            if(bgMusic.isPlaying()){
                bgMusic.pause();
            }
        }
        aManager.unload("HARBOR/GoBackHARBORPanoramic.png");
        aManager.unload("Squirts/Sophie/SOPHIEComplete.png");
        aManager.unload("IAN/IanStill.png");
        aManager.unload("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTBlueOrb.png");
        aManager.unload("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTRedOrb.png");
        aManager.unload("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTYellowOrb.png");

        aManager.unload("Interfaces/GAMEPLAY/CONSTANT/ARCADEBlueOrbBUNNY.png");
        aManager.unload("Interfaces/GAMEPLAY/CONSTANT/ARCADERedOrbBIRD.png");
        aManager.unload("Interfaces/GAMEPLAY/CONSTANT/ARCADEYellowOrbDOG.png");
        aManager.unload("INTRO/concha.png");
        aManager.unload("SOPHIE/DIALOGUESophieNormal.png");
        aManager.unload("IAN/DIALOGUEIanConcern.png");
    }

    private class Input implements InputProcessor{
        private Vector3 v = new Vector3();
        @Override
        public boolean keyDown(int keycode) {
            if (keycode == com.badlogic.gdx.Input.Keys.BACK) {
                dispose();
                app.setScreen(new Fade(app, LoaderState.MAINMENU));
                return true;
            }
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
            if(!(camera.position.x - v.x < -522 && v.y < 135)){
                if(sophie.getMovementState()==Sophie.MovementState.STILL_LEFT||sophie.getMovementState()==Sophie.MovementState.STILL_RIGHT) {
                    if(!dialogueOn && !orbTransformation) {
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
            v.set(screenX,screenY,0);
            camera.unproject(v);
            if(sophie.getMovementState()==Sophie.MovementState.MOVE_LEFT)
                sophie.setMovementState(Sophie.MovementState.STILL_LEFT);
            else if(sophie.getMovementState() == Sophie.MovementState.MOVE_RIGHT)
                sophie.setMovementState(Sophie.MovementState.STILL_RIGHT);
            if(camera.position.x - v.x < -522 && v.y < 135){
                state = GameState.PAUSED;
            }
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

    private enum OrbMovement{
        GOING_UP,
        GOING_DOWN
    }
}
