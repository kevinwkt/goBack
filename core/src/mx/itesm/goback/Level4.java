package mx.itesm.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Created by sergiohernandezjr on 01/05/17.
 */

class Level4 extends Frame {

    private static final float SOPHIE_START_X = 10800;
    private static final float SOPHIE_START_Y = 220;
    private Sophie sophie;

    public static final float WIDTH_MAP = 11520;
    public static final float HEIGHT_MAP = 720;

    public static final float LEFT_LIMIT = 1920;
    public static float RIGHT_LIMIT = 10900;


    private Texture sophieTexture;
    private Texture background01;
    private Texture background02;
    private Texture background03;
    private Texture handTexture;
    private Texture deathTexture;
    private Sprite deathSprite;
    private float deathScale = 0f;
    private TextureRegion oldManTexture;
    private TextureRegion oldManEndTexture;
    private TextureRegion oldManReg;

    private Animation<TextureRegion> oldManAnim;
    private float timerChangeOldMan = -1.5f;

    //LOST
    private float dialoguetime = 0;
    private Dialogue dialogue;
    private GlyphLayout glyph = new GlyphLayout();

    // yellow orb
    private Orb yellowOrb;
    private float yellowOrbXPosition = 10850;
    private float yellowOrbYPosition = 110;
    private OrbMovement currentYellowOrbState = OrbMovement.GOING_UP;
    private final float DISTANCE_YELLOW_ORB_SOPHIE = -100;

    // blue orb
    private Orb blueOrb;
    private float blueOrbXPosition = 10870;
    private float blueOrbYPosition = 165;
    private OrbMovement currentBlueOrbState = OrbMovement.GOING_DOWN;
    private final float DISTANCE_BLUE_ORB_SOPHIE = 60;

    // red orb
    private Orb redOrb;
    private float redOrbXPosition = 10900;
    private float redOrbYPosition = 240;
    private OrbMovement currentRedOrbState = OrbMovement.GOING_UP;
    private final float DISTANCE_RED_ORB_SOPHIE = -30;

    //Bottom hands
    private BottomHand [] arrHand = new BottomHand[11];
    private WallHand[] wallHand = new WallHand[40];
    private Input input;

    //Side hands
    private final float handStartX = WIDTH_MAP + 500;
    private final float firstHandStartY = 520;
    private final float secondHandStartY = 360;
    private final float thirdHandStartY = 200;

    private SideHand firstHand;
    private SideHand secondHand;
    private SideHand thirdHand;

    //Catched orbs
    private final float redOrbCatchX = 8500;
    private final float blueOrbCatchX = 6000;
    private final float yellowOrbCatchX = 3500;
    private final float finishX = LEFT_LIMIT;

    private boolean limitReached = false;

    Level4(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);
    }

    @Override
    public void show() {
        super.show();
        textureInit();
        objectInit();

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(new Input());
        //musicInit();

    }

    private void textureInit() {
        sophieTexture = aManager.get("Squirts/Sophie/SOPHIEWalk.png");
        background01 = aManager.get("WOODS/WOODSPanoramic1of2.png");
        background02 = aManager.get("MOUNTAINS/GoBackMOUNTAINSPanoramic.png");
        background03 = aManager.get("HARBOR/GoBackHARBORPanoramic.png");

        handTexture = aManager.get("MINIONS/HAND/MINIONSHand00.png");
        deathTexture = aManager.get("BOSS/DEATH/BOSSDeathFull.png");

        oldManTexture = new TextureRegion((Texture) aManager.get("OLDMAN/CRACK/OLDMANCrackFULL.png"));
        oldManEndTexture = new TextureRegion((Texture) aManager.get("OLDMAN/CRACK/OLDMANCrackFINAL.png"));

        deathSprite = new Sprite(deathTexture);
        deathSprite.setOrigin(200, 33);
        deathSprite.scale(0f);
        deathSprite.setPosition(LEFT_LIMIT-50-oldManEndTexture.getRegionWidth()/2-deathSprite.getWidth()/2, 220+oldManEndTexture.getRegionHeight()-50);


        sophie = new Sophie(sophieTexture, 100,100);
        yellowOrb = new Orb((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTYellowOrb.png"));
        blueOrb = new Orb((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTBlueOrb.png"));
        redOrb = new Orb((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTRedOrb.png"));

        yellowOrb.setColor(1.0f,1.0f,1.0f,0.6f);
        blueOrb.setColor(1.0f,1.0f,1.0f,0.6f);
        redOrb.setColor(1.0f,1.0f,1.0f,0.6f);
    }

    private void objectInit() {
        batch = new SpriteBatch();
        batch.begin();

        //BG
        batch.draw(background01,7680,0);
        batch.draw(background02,3840,0);
        batch.draw(background03,0,0);

        //SOPHIE
        sophie.setMovementState(Sophie.MovementState.WAKING_LEFT);
        sophie.sprite.setPosition(SOPHIE_START_X, SOPHIE_START_Y);

        //CAMERA
        camera.position.set(7680+background01.getWidth()-HALFW,camera.position.y, 0);
        camera.update();

        //HANDS
        arrHand[0]= new BottomHand(handTexture, camera.position.x-HALFW-(WIDTH/(arrHand.length-1)), 0);
        for (int i = 0; i<arrHand.length-1;i++){
            arrHand[i+1] = new BottomHand(handTexture, camera.position.x-HALFW+(WIDTH/(arrHand.length-1))*i, 0);
        }

        firstHand = new SideHand(handTexture, handStartX, firstHandStartY);
        secondHand = new SideHand(handTexture, handStartX, secondHandStartY);;
        thirdHand = new SideHand(handTexture, handStartX, thirdHandStartY);;

        Random ran = new Random();

        for (int i = 0; i<wallHand.length;i++){
            float off = (240-160)*ran.nextFloat()+160;
            wallHand[i]=new WallHand(handTexture, handStartX-200+off*(i/8), -60+((i%8)*(HEIGHT_MAP+100)/8));
        }

        //oldMan anim
        TextureRegion[][] texOldManArr = oldManTexture.split(145,116);
        oldManAnim=new Animation(0.7f, texOldManArr[0][0], texOldManArr[0][1], texOldManArr[0][2], texOldManArr[0][3],
                texOldManArr[0][4], texOldManArr[0][5], texOldManArr[0][6], texOldManArr[0][7], oldManEndTexture);

        //dialogue
        dialogue = new Dialogue(aManager);

        batch.end();

        input = new Input();
    }

    @Override
    public void render(float delta) {
        cls();

        batch.setProjectionMatrix(super.camera.combined);

        if(soundPreferences.getBoolean("soundOn"))
            bgMusic.play();
        else
            bgMusic.stop();

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
        }else if(state==GameState.LOST){
            batch.begin();

            batch.draw(background01,7680,0);
            batch.draw(background02,3840,0);
            batch.draw(background03,0,0);

            sophie.setMovementState(Sophie.MovementState.DYING);
            sophie.draw(batch);
            loose(delta);
            batch.end();
        }else if(state==GameState.PLAYING){
            batch.begin();
            Gdx.input.setInputProcessor(input);

            //Draw BG
            batch.draw(background01,7680,0);
            batch.draw(background02,3840,0);
            batch.draw(background03,0,0);

            //Draw bottom hands
            /*for (int i = 0; i<arrHand.length;i++){
                arrHand[i].update();
                checkHand(arrHand[i]);
            }*/

            //Draw wall hands
            for (int i = 0; i<wallHand.length;i++){
                wallHand[i].update();
            }

            //Draw Sophie and Orbs
            updateSophieOrbs(delta);


            //Draw Side Hands
            firstHand.update(redOrb, secondHand, delta);
            secondHand.update(blueOrb, thirdHand, delta);
            thirdHand.update(yellowOrb, firstHand, delta);

            if(sophie.sprite.getX()<=finishX+500){
                for (int i = 0; i<wallHand.length;i++){
                    wallHand[i].state=HandState.STOPPED;
                }
            }

            if(firstHand.getX()<=LEFT_LIMIT+HALFW-firstHand.sprite.getWidth()){
                //Stop hands
                firstHand.state=HandState.STOPPED;
                secondHand.state=HandState.STOPPED;
                thirdHand.state=HandState.STOPPED;
            }

            if(sophie.sprite.getX()<=finishX){
                limitReached = true;
                endReached(delta);
            }else{
                batch.draw(oldManAnim.getKeyFrame(0f), LEFT_LIMIT-200-oldManAnim.getKeyFrame(0f).getRegionWidth()/2, 220);
            }

            //Catch orbs
            catchOrbs();

            if(sophie.sprite.getX()+sophie.sprite.getWidth()>=wallHand[0].sprite.getX()){
                state=GameState.LOST;
            }

            //Draw pause button and update camera
            batch.draw(pauseButton,camera.position.x+HALFW-pauseButton.getWidth(),camera.position.y-HALFH);
            updateCamera();
            batch.end();
        }
    }

    private void endReached(float delta) {
        //Stop sophie
        sophie.setMovementState(Sophie.MovementState.STILL_LEFT);

        //Draw old man
        oldManReg = oldManAnim.getKeyFrame(timerChangeOldMan<0?0:timerChangeOldMan);
        batch.draw(oldManReg, LEFT_LIMIT-200-oldManReg.getRegionWidth()/2, 220);
        timerChangeOldMan+=delta;
        if(oldManAnim.isAnimationFinished(timerChangeOldMan)){
            appearMictlan();
        }
    }

    private void appearMictlan() {
        deathSprite.setScale(deathScale+0.001f);
        deathSprite.draw(batch);

        if(deathScale<1){
            deathScale+=0.01;
        }else{
            finishLevel();
        }
    }

    private void finishLevel() {
        app.setScreen(new Fade(app, LoaderState.LEVELEND));
        pref.putInteger("level", 5);
        pref.flush();
        this.dispose();
    }

    private void catchOrbs() {
        if (!redOrb.catched){
            if(sophie.sprite.getX()<=redOrbCatchX && firstHand.state==HandState.FORWARD){
                firstHand.state = HandState.FORWARD_CATCH;
                //catchedRedOrb = true;
            }
        }else{
            if (!blueOrb.catched){
                if(sophie.sprite.getX()<=blueOrbCatchX && secondHand.state==HandState.FORWARD){
                    //Catch blue Orb
                    secondHand.state = HandState.FORWARD_CATCH;
                    //catchedBlueOrb = true;
                }
            }else{
                if (!yellowOrb.catched){
                    if(sophie.sprite.getX()<=yellowOrbCatchX && thirdHand.state==HandState.FORWARD){
                        //Catch yellow Orb
                        thirdHand.state = HandState.FORWARD_CATCH;
                        //catchedYellowOrb = true;
                    }
                }
            }
        }
    }

    private void updateSophieOrbs(float delta) {
        //Draw Sophie
        sophie.draw(batch);
        sophie.update();

        //Draw orbs
        yellowOrb.draw(batch);
        blueOrb.draw(batch);
        redOrb.draw(batch);

        moveYellowOrb(delta);
        moveBlueOrb(delta);
        moveRedOrb(delta);}

    private void moveRedOrb(float delta){
        if(redOrbYPosition >= 260){ // 155
            currentRedOrbState = OrbMovement.GOING_DOWN;
        }else if(redOrbYPosition <= 220){ //145
            currentRedOrbState = OrbMovement.GOING_UP;
        }
        redOrbXPosition = redOrb.getX();

        if(currentRedOrbState == OrbMovement.GOING_UP){
            redOrbYPosition += delta*30;
        }else if(currentRedOrbState == OrbMovement.GOING_DOWN){
            redOrbYPosition -= delta*30;
        }

        redOrb.setPosition(redOrbXPosition,redOrbYPosition);

        if(!redOrb.catched) {
            if (redOrb.getX() < (sophie.sprite.getX() - redOrb.getWidth() - DISTANCE_RED_ORB_SOPHIE)) {
                redOrb.setPosition(sophie.sprite.getX() - redOrb.getWidth() - DISTANCE_RED_ORB_SOPHIE + 1, redOrb.getY());
            } else if ((sophie.sprite.getX() + sophie.sprite.getWidth() + DISTANCE_RED_ORB_SOPHIE) < redOrb.getX()) {
                redOrb.setPosition(sophie.sprite.getX() + sophie.sprite.getWidth() + DISTANCE_RED_ORB_SOPHIE - 1, redOrb.getY());
            }else if(redOrb.getX()+redOrb.getWidth()/2<sophie.sprite.getX()+sophie.sprite.getWidth()/2){
                if (redOrb.isFlipX())
                    redOrb.flip(true, false);
            }else if(redOrb.getX()+redOrb.getWidth()/2>sophie.sprite.getX()+sophie.sprite.getWidth()/2){
                if (!redOrb.isFlipX())
                    redOrb.flip(true, false);
            }
        }else{
            redOrb.setPosition(firstHand.getX(), firstHand.getY());
            redOrb.setPosition(firstHand.getX()-redOrb.getWidth()/2+95, firstHand.getY()-redOrb.getHeight()/2+54);
        }
    }

    private void moveBlueOrb(float delta){
        if(blueOrbYPosition >= 170){ // 155
            currentBlueOrbState = OrbMovement.GOING_DOWN;
        }else if(blueOrbYPosition <= 160){ //145
            currentBlueOrbState = OrbMovement.GOING_UP;
        }
        blueOrbXPosition = blueOrb.getX();

        if(currentBlueOrbState == OrbMovement.GOING_UP){
            blueOrbYPosition += delta*30;
        }else if(currentBlueOrbState == OrbMovement.GOING_DOWN){
            blueOrbYPosition -= delta*30;
        }

        blueOrb.setPosition(blueOrbXPosition,blueOrbYPosition);

        if(!blueOrb.catched) {
            if (blueOrb.getX() < (sophie.sprite.getX() - blueOrb.getWidth() - DISTANCE_BLUE_ORB_SOPHIE)) {
                blueOrb.setPosition(sophie.sprite.getX() - blueOrb.getWidth() - DISTANCE_BLUE_ORB_SOPHIE + 1, blueOrb.getY());
            } else if ((sophie.sprite.getX() + sophie.sprite.getWidth() + DISTANCE_BLUE_ORB_SOPHIE) < blueOrb.getX()) {
                blueOrb.setPosition(sophie.sprite.getX() + sophie.sprite.getWidth() + DISTANCE_BLUE_ORB_SOPHIE - 1, blueOrb.getY());
            }else if(blueOrb.getX()+blueOrb.getWidth()/2<sophie.sprite.getX()+sophie.sprite.getWidth()/2){
                if (blueOrb.isFlipX())
                    blueOrb.flip(true, false);
            }else if(blueOrb.getX()+blueOrb.getWidth()/2>sophie.sprite.getX()+sophie.sprite.getWidth()/2){
                if (!blueOrb.isFlipX())
                    blueOrb.flip(true, false);
            }
        }else{
            blueOrb.setPosition(secondHand.getX()-blueOrb.getWidth()/2+95, secondHand.getY()-blueOrb.getHeight()/2+54);
        }
    }

    private void moveYellowOrb(float delta){
        if(yellowOrbYPosition >= 120){
            currentYellowOrbState = OrbMovement.GOING_DOWN;
        }else if(yellowOrbYPosition <= 100){
            currentYellowOrbState = OrbMovement.GOING_UP;
        }
        yellowOrbXPosition = yellowOrb.getX();

        if(currentYellowOrbState == OrbMovement.GOING_UP){
            yellowOrbYPosition += delta*30;
        }else if(currentYellowOrbState == OrbMovement.GOING_DOWN){
            yellowOrbYPosition -= delta*30;
        }

        yellowOrb.setPosition(yellowOrbXPosition,yellowOrbYPosition);

        if(!yellowOrb.catched) {
            if (yellowOrb.getX() < (sophie.sprite.getX() - yellowOrb.getWidth() - DISTANCE_YELLOW_ORB_SOPHIE)) {
                yellowOrb.setPosition(sophie.sprite.getX() - yellowOrb.getWidth() - DISTANCE_YELLOW_ORB_SOPHIE + 1, yellowOrb.getY());
            } else if ((sophie.sprite.getX() + sophie.sprite.getWidth() + DISTANCE_YELLOW_ORB_SOPHIE) < yellowOrb.getX()) {
                yellowOrb.setPosition(sophie.sprite.getX() + sophie.sprite.getWidth() + DISTANCE_YELLOW_ORB_SOPHIE - 1, yellowOrb.getY());
            }else if(yellowOrb.getX()+yellowOrb.getWidth()/2<sophie.sprite.getX()+sophie.sprite.getWidth()/2){
                if (yellowOrb.isFlipX())
                    yellowOrb.flip(true, false);
            }else if(yellowOrb.getX()+yellowOrb.getWidth()/2>sophie.sprite.getX()+sophie.sprite.getWidth()/2){
                if (!yellowOrb.isFlipX())
                    yellowOrb.flip(true, false);
            }
        }else{
            yellowOrb.setPosition(thirdHand.getX()-yellowOrb.getWidth()/2+95, thirdHand.getY()-yellowOrb.getHeight()/2+54);
        }
    }

    private void checkHand(BottomHand hand) {
        if(hand.sprite.getX()-hand.sprite.getOriginX()>=camera.position.x+HALFW){
            hand.sprite.setX(camera.position.x-HALFW-WIDTH/arrHand.length);
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

    private void loose(float delta) {
        dialoguetime += delta;

        if(dialoguetime < 2.5f) {
            dialogue.makeText(glyph, batch, "This dream overwhelmed you", camera.position.x);
        }else{
            app.setScreen(new Fade(app, LoaderState.LEVEL4));
            dispose();
        }
    }

    private class BottomHand extends Squirt{
        private boolean rotate = true;
        private Random rand = new Random();

        public BottomHand(Texture textura, float x, float y){
            sprite = new Sprite(textura);
            sprite.rotate(90f);
            sprite.setPosition(x, y);
            sprite.setOrigin(60, 30);

            float randFl = (0.09f)*rand.nextFloat()+0.01f;
            //sprite.scale(randFl);

            if(rand.nextBoolean()){
                sprite.flip(false, true);
            }

            sprite.draw(batch);
        }

        public void update(){
            if (rotate) {
                sprite.rotate(0.5f*rand.nextFloat());
            } else {
                sprite.rotate(-0.5f*rand.nextFloat());
            }

            if (sprite.getRotation() > 95) {
                rotate = false;
            } else if (sprite.getRotation() < 85) {
                rotate = true;
            }
            sprite.draw(batch);
        }
    }

    private class SideHand extends Squirt{
        private final float dx = 4;
        private float vy=0;

        private boolean rotate = true;
        private Random rand = new Random();

        private float timer = 0;
        protected HandState state;
        private float timeX = 0;
        private final float originalY;

        public SideHand(Texture textura, float x, float y){
            sprite = new Sprite(textura);
            sprite.flip(true, false);
            originalY = y;
            sprite.setOrigin(243, 102);
            setPosition(x, y);
            state = HandState.FORWARD;
            sprite.draw(batch);
        }

        public void update(Orb orb, SideHand other , float delta){
            if(state==HandState.FORWARD){
                setPosition(sophie.sprite.getX()+HALFW+sophie.sprite.getWidth()/2-sprite.getWidth(), getY());
            }else if(state==HandState.FORWARD_CATCH){
                if(vy==0){
                    timeX = (getX()+95-(orb.getX()+orb.getWidth()/2))/(dx);
                    vy=(getY()+54-(orb.getY()+orb.getHeight()/2))/timeX;
                }
                setPosition(getX()-2*dx, getY()-vy);
                if(getX()<=(orb.getX()+(orb.getWidth()/2)-95)){
                    state = HandState.CATCHING;
                }
            }else if(state == HandState.BACKWARD_CATCH){
                if(vy==0){
                    timeX = ((other.getX()-getX())/2)/(dx);
                    vy=(originalY-getY())/timeX;
                }
                setPosition(getX()+dx, getY()+vy);
                if(getX()>=other.getX()){
                    state = HandState.FORWARD;
                }
            }else if(state==HandState.CATCHING){
                setPosition(getX()-dx, getY());
                catchOrb(delta, orb);
            }

            if(!orb.catched) {
                if (rotate) {
                    sprite.rotate(0.5f * rand.nextFloat());
                } else {
                    sprite.rotate(-0.5f * rand.nextFloat());
                }

                if (sprite.getRotation() > 5) {
                    rotate = false;
                } else if (sprite.getRotation() < -5) {
                    rotate = true;
                }
            }

            sprite.draw(batch);
        }

        private void catchOrb(float delta, Orb orb){
            if(timer>=0.5f){
                state = HandState.BACKWARD_CATCH;
                vy=0;
                orb.catched = true;
            }else{
                timer+=delta;
            }
        }
        public void setPosition(float x, float y){
            sprite.setPosition(x, y);
        }

        public float getX(){
            return sprite.getX();
        }

        public float getY(){
            return sprite.getY();
        }

    }

    private class WallHand extends Squirt{
        private final float dx = 4;

        private boolean rotate = true;
        private Random rand = new Random();

        protected HandState state;

        public WallHand(Texture textura, float x, float y){
            sprite = new Sprite(textura);
            sprite.setColor(1f, 1f, 1f, 0.7f);
            sprite.flip(true, false);
            if(rand.nextBoolean()){
                sprite.flip(false, true);
            }
            float randFl = (0.49f)*rand.nextFloat()+0.01f;
            sprite.scale(randFl);
            sprite.setOrigin(243, 102);
            sprite.setPosition(x, y);
            state = HandState.FORWARD;
            sprite.draw(batch);
        }

        public void update(){
            if(state==HandState.FORWARD){
                sprite.setPosition(sprite.getX()-dx,sprite.getY());
            }

            if (rotate) {
                sprite.rotate(0.5f * rand.nextFloat());
            } else {
                sprite.rotate(-0.5f * rand.nextFloat());
            }

            if (sprite.getRotation()>10) {
                rotate = false;
            }else if(sprite.getRotation()<-10) {
                rotate = true;
            }

            sprite.draw(batch);
        }

    }

    private class Orb extends Sprite{
        public boolean catched = false;

        public Orb(Texture tex){
            super(tex);
        }
    }

    private enum HandState{
        FORWARD,
        FORWARD_CATCH,
        BACKWARD_CATCH,
        CATCHING,
        STOPPED
    }

    private class Input implements InputProcessor {
        private Vector3 v = new Vector3();
        @Override
        public boolean keyDown(int keycode) {
            if (keycode == com.badlogic.gdx.Input.Keys.BACK){
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

            if(camera.position.x - v.x < -522 && v.y < 135){
                state = GameState.PAUSED;
                return true;
            }
            if (!limitReached){
                if (sophie.getMovementState() == Sophie.MovementState.STILL_LEFT || sophie.getMovementState() == Sophie.MovementState.STILL_RIGHT) {
                    if (v.x >= camera.position.x) {
                        sophie.setMovementState(Sophie.MovementState.MOVE_RIGHT);
                    } else {
                        sophie.setMovementState(Sophie.MovementState.MOVE_LEFT);
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
        aManager.unload("Squirts/Sophie/SOPHIEWalk.png");

        aManager.unload("OLDMAN/CRACK/OLDMANCrackFULL.png");
        aManager.unload("OLDMAN/CRACK/OLDMANCrackFINAL.png");

        aManager.unload("HARBOR/GoBackHARBORPanoramic.png");
        aManager.unload("MOUNTAINS/GoBackMOUNTAINSPanoramic.png");
        aManager.unload("WOODS/WOODSPanoramic1of2.png");

        aManager.unload("WOODS/WOODSEnding.png");
        aManager.unload("MINIONS/HAND/MINIONSHand00.png");
        aManager.unload("BOSS/DEATH/BOSSDeathFull.png");
        aManager.unload("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTYellowOrb.png");
        aManager.unload("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTBlueOrb.png");
        aManager.unload("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTRedOrb.png");

        if(bgMusic != null){
            if(bgMusic.isPlaying()){
                bgMusic.pause();
            }
        }

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
