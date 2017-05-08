package mx.itesm.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by sergiohernandezjr on 18/03/17.
 */
class Level2 extends Frame {
    //box2d shit
    private World world;
    private HashSet<Body> meteorsOutOfBounds;
    private HashSet<Body> deadMeteors;
    private Array<Body> squirts = new Array<Body>();
    private Object meteorObj;


    // Sophie
    //private Sophie sophie;
    private Texture sophieTexture;
    boolean sophieInitFlag = true;

    private ArcadeSophie sophie;

    // textures
    Texture background;
    Texture meteor;

    //map
    public static final float WIDTH_MAP = 3840;
    public static final float HEIGHT_MAP = 720;

    protected static final float LEFT_LIMIT = 20;
    protected static float RIGHT_LIMIT = 3650;

    private Input level2Input = new Input();

    // preferences
    Preferences pref = Gdx.app.getPreferences("getLevel");
    Preferences soundPreferences = Gdx.app.getPreferences("My Preferences");

    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugMatrix;

    private Random randomMeteorPosition = new Random();

    private float timeForMeteor = 2;
    private float dialoguetime = 0;

    private Dialogue dialogue;
    private GlyphLayout glyph = new GlyphLayout();

    // blue orb
    private Sprite blueOrb;
    private boolean foundOrb = false;
    private float blueOrbXPosition = 1350;
    private float blueOrbYPosition = 200;
    private Level2.OrbMovement currentBlueOrbState = Level2.OrbMovement.GOING_DOWN;
    private final float DISTANCE_BLUE_ORB_SOPHIE = 10;

    // yellow orb
    private Sprite yellowOrb;
    private float yellowOrbXPosition = -500;
    private float yellowOrbYPosition = 150;
    private Level2.OrbMovement currentYellowOrbState = Level2.OrbMovement.GOING_UP;
    private final float DISTANCE_YELLOW_ORB_SOPHIE = -140;

    // photo
    private Sprite photoSpr;
    private int photoXPosition = 3150;
    private int photoYPosition = 200;

    // JAGUAR
    private Sprite jaguarSpr;
    private Sprite jaguarArmSpr;
    private Sprite jaguarLegSpr;
    private float jaguarYPosition = 850; // 850
    private float jaguarXPosition = 3200;
    private float jaguarAcceleration = 5;
    private boolean rotateJaguarArm = true;
    private boolean bossMode = false;


    public Level2(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);

    }

    @Override
    public void show(){
        super.show();
        textureInit();
        worldInit();
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(level2Input);
        dialogue = new Dialogue(aManager);

        debugRenderer=new Box2DDebugRenderer();




    }

    private void worldInit() {
        world = new World(Vector2.Zero, true);
        meteorsOutOfBounds = new HashSet<Body>();
        deadMeteors = new HashSet<Body>();
        world.setContactListener(new ContactListener() {
             @Override
             public void beginContact(Contact contact) {
                 Object ob1 = contact.getFixtureA().getBody().getUserData();
                 Object ob2 = contact.getFixtureB().getBody().getUserData();
                 if(ob1 instanceof ArcadeSophie || ob2 instanceof ArcadeSophie){
                     if(ob1 instanceof ArcadeSophie){
                         deadMeteors.add(contact.getFixtureB().getBody());
                     }
                     if(ob2 instanceof ArcadeSophie){
                         deadMeteors.add(contact.getFixtureA().getBody());
                     }
                 }
             }

             @Override
             public void endContact(Contact contact) {

             }

             @Override
             public void preSolve(Contact contact, Manifold oldManifold) {

             }

             @Override
             public void postSolve(Contact contact, ContactImpulse impulse) {

             }
            }

        );

        sophie = new ArcadeSophie(world,sophieTexture);



    }

    private void textureInit() {
        // background
        background = aManager.get("MOUNTAINS/GoBackMOUNTAINSPanoramic.png");

        // meteor
        meteor = aManager.get("MINIONS/METEOR/MINIONMeteor00.png");

        // sophie
        sophieTexture = aManager.get("Squirts/Sophie/SOPHIEComplete.png");

        // orb
        blueOrb = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png"));
        blueOrb.setPosition(blueOrbXPosition,blueOrbYPosition);
        blueOrb.setColor(1.0f,1.0f,1.0f,0.4f);
        yellowOrb = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png"));
        yellowOrb.setPosition(yellowOrbXPosition,yellowOrbYPosition);
        yellowOrb.setColor(1.0f,1.0f,1.0f,0.4f);

        // photo
        photoSpr = new Sprite((Texture)aManager.get("CLUES/Photo/CLUESPhoto.png"));
        photoSpr.setPosition(photoXPosition,photoYPosition);

        // JAGUAR
        jaguarLegSpr = new Sprite((Texture)aManager.get("BOSS/JAGUAR/BOSSJaguarBackLeg.png"));
        jaguarSpr = new Sprite((Texture)aManager.get("BOSS/JAGUAR/BOSSJaguarBody.png"));
        jaguarArmSpr = new Sprite((Texture)aManager.get("BOSS/JAGUAR/BOSSJaguarFrontLeg.png"));


    }

    @Override
    public void render(float delta) {
        debugMatrix = new Matrix4(super.camera.combined);
        debugMatrix.scale(100, 100, 1f);
        cls();




        batch.begin();
        batch.setProjectionMatrix(super.camera.combined);

        if(soundPreferences.getBoolean("soundOn"))
            bgMusic.play();
        else
            bgMusic.stop();

        if(state == GameState.CLUE){
            batch.end();
            clueStage.draw();
            Gdx.input.setInputProcessor(clueStage);
        }else if(state == GameState.STATS){
            batch.end();
            statsStage.sophieCoins.setText(Integer.toString(statsStage.statsPrefs.getInteger("Coins")));
            statsStage.yellowXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.blueXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.redXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.draw();
            Gdx.input.setInputProcessor(inputMultiplexer);
        }else if(state == GameState.PLAYING){

            batch.draw(background,0,0);
            batch.draw(pauseButton,camera.position.x+HALFW-pauseButton.getWidth(),camera.position.y-HALFH);

            if(sophieInitFlag) {
                sophieInitialMove();
            }else{
                drawMeteors(delta);

            }

            sophie.update();
            sophie.draw(batch);
            blueOrb.draw(batch);
            yellowOrb.draw(batch);
            photoSpr.draw(batch);
            moveBlueOrb(delta);
            moveYellowOrb(delta);
            checkBlueOrbCollision();
            if(sophie.getX() >= 3050){
                bossMode = true;
                RIGHT_LIMIT = 3050;
            }
            if(bossMode){
                drawJaguar(delta);
            }


            updateCamera();
            Gdx.input.setInputProcessor(level2Input);
            //Gdx.app.log("sophie life",sophie.life+"");

            stepper(delta);
            batch.end();
            if(sophie.life <= 0){
                state = GameState.LOST;
            }

        }else if(state == GameState.PAUSED){
            batch.end();
            pauseStage.draw();
            Gdx.input.setInputProcessor(pauseStage);
        }else if(state == GameState.LOST){
            batch.draw(background,0,0);
            batch.setColor(1f, 1f, 1f, 1f);

            sophie.setMovementState(ArcadeSophie.MovementState.DYING);
            sophie.draw(batch);


            loose(delta);
        }




        batch.begin();
        batch.setProjectionMatrix(super.camera.combined);
        //debugRenderer.render(world, debugMatrix);
        batch.end();
    }

    private void drawJaguar(float delta) {
        if(jaguarYPosition > 300){
            jaguarYPosition -= delta * jaguarAcceleration;
            jaguarAcceleration += 9.5;
        }else{
            jaguarAcceleration += .5;
            if(jaguarAcceleration > 870){
                jaguarXPosition += delta * 100;

                if(jaguarXPosition > 3790){


                    pref.putBoolean("boss",true);
                    pref.flush();
                    ArcadeValues.bossFightFlag = true;
                    app.setScreen(new Fade(app, LoaderState.ARCADE));
                    this.dispose();

                }
            }
        }

        jaguarSpr.setPosition(jaguarXPosition,jaguarYPosition);
        jaguarSpr.draw(batch);
        jaguarLegSpr.setPosition(jaguarXPosition+515,jaguarYPosition-150);
        jaguarLegSpr.draw(batch);
        jaguarArmSpr.setPosition(jaguarXPosition + 250,jaguarYPosition-150);
        jaguarArmSpr.draw(batch);
        jaguarArmSpr.setOrigin(104,273);
        if (rotateJaguarArm) {
            jaguarArmSpr.rotate((float) 0.5);
        } else {
            jaguarArmSpr.rotate((float) -0.5);
        }
        if (jaguarArmSpr.getRotation() > 5) {
            rotateJaguarArm = false;
        } else if (jaguarArmSpr.getRotation() < -20) {
            rotateJaguarArm = true;
        }

    }

    private void loose(float delta) {
        dialoguetime += delta;

        if(dialoguetime < 2.5f) {
            dialogue.makeText(glyph, batch, "This dream overwhelmed you", camera.position.x);
            batch.end();
        }else{
            batch.end();
            app.setScreen(new Fade(app, LoaderState.LEVEL2));
            dispose();
        }
    }

    private void drawMeteors(float delta) {
        // must appear at 1420
        timeForMeteor += delta;

        if(timeForMeteor >= 0.2){
            if(camera.position.x <= 3100){
                new ArcadeMeteor(world, (float)(randomMeteorPosition.nextInt(1280)+(camera.position.x-640) )   , meteor);
            }
            timeForMeteor = 0;
        }

        world.getBodies(squirts);

        for(Body b : squirts){
            meteorObj = b.getUserData();
            if( meteorObj instanceof ArcadeMeteor){
                ((ArcadeMeteor)meteorObj).draw(batch);

                if(((ArcadeMeteor) meteorObj).sprite.getY() <= -200){
                    meteorsOutOfBounds.add(b);
                }
            }


        }
        squirts.clear();
    }

    private void moveBlueOrb(float delta){
        if(blueOrbYPosition >= 205){ // 155
            currentBlueOrbState = OrbMovement.GOING_DOWN;
        }else if(blueOrbYPosition <= 175){ //145
            currentBlueOrbState = OrbMovement.GOING_UP;
        }

        if(foundOrb){
            blueOrbXPosition = blueOrb.getX();
        }

        if(currentBlueOrbState == OrbMovement.GOING_UP){
            blueOrbYPosition += delta*30;
        }else if(currentBlueOrbState == OrbMovement.GOING_DOWN){
            blueOrbYPosition -= delta*30;
        }

        blueOrb.setPosition(blueOrbXPosition,blueOrbYPosition);
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
        if(yellowOrb.getX()<(sophie.getX()-yellowOrb.getWidth()-DISTANCE_YELLOW_ORB_SOPHIE)){
            yellowOrb.setPosition(sophie.getX()-yellowOrb.getWidth()-DISTANCE_YELLOW_ORB_SOPHIE+1,yellowOrb.getY());
        }else if((sophie.getX()+sophie.getSprite().getWidth()+DISTANCE_YELLOW_ORB_SOPHIE)<yellowOrb.getX()){
            yellowOrb.setPosition(sophie.getX()+sophie.getSprite().getWidth()+DISTANCE_YELLOW_ORB_SOPHIE-1, yellowOrb.getY());
        }
    }


    private void checkBlueOrbCollision() {

        if(!foundOrb){
            if(blueOrb.getBoundingRectangle().contains(sophie.getX()+blueOrb.getBoundingRectangle().getWidth()/2,sophie.getY())){
                foundOrb = true;
            }
        }else{
            if(blueOrb.getX()<(sophie.getX()-blueOrb.getWidth()-DISTANCE_BLUE_ORB_SOPHIE)){
                blueOrb.setPosition(sophie.getX()-blueOrb.getWidth()-DISTANCE_BLUE_ORB_SOPHIE+1,blueOrb.getY());
            }else if((sophie.getX()+sophie.getSprite().getWidth()+DISTANCE_BLUE_ORB_SOPHIE)<blueOrb.getX()){
                blueOrb.setPosition(sophie.getX()+sophie.getSprite().getWidth()+DISTANCE_BLUE_ORB_SOPHIE-1, blueOrb.getY());
            }

        }
    }

    private void stepper(float delta){
        world.step(1/60f, 6, 2);
        for(Body b: meteorsOutOfBounds){
            while(b.getFixtureList().size > 0){
                b.destroyFixture(b.getFixtureList().get(0));
            }
            world.destroyBody(b);
        }
        meteorsOutOfBounds.clear();

        for(Body b: deadMeteors){
            while(b.getFixtureList().size > 0){
                b.destroyFixture(b.getFixtureList().get(0));
            }

            world.destroyBody(b);
            sophie.life -= 10;
        }
        deadMeteors.clear();

    }

    private void sophieInitialMove() {
        if(sophie.getX() < 250){
            sophie.setMovementState(ArcadeSophie.MovementState.MOVE_RIGHT);
        }else{
            sophieInitFlag = false;
            sophie.setMovementState(ArcadeSophie.MovementState.STILL_RIGHT);
        }

    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        aManager.unload("MINIONS/METEOR/MINIONMeteor00.png");
        aManager.unload("MOUNTAINS/GoBackMOUNTAINSPanoramic.png");
        aManager.unload("Squirts/Sophie/SOPHIEComplete.png");
        aManager.unload("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png");
        aManager.unload("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png");
        aManager.unload("CLUES/Photo/CLUESPhoto.png");
        aManager.unload("BOSS/JAGUAR/BOSSJaguarBackLeg.png");
        aManager.unload("BOSS/JAGUAR/BOSSJaguarBody.png");
        aManager.unload("BOSS/JAGUAR/BOSSJaguarFrontLeg.png");
        meteorsOutOfBounds.clear();
        deadMeteors.clear();

        if(bgMusic != null){
            if(bgMusic.isPlaying()){
                bgMusic.pause();
            }
        }
    }

    public void updateCamera(){
        float posX = sophie.getX()+sophie.getSprite().getWidth()/2;
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

    private class Input implements InputProcessor{

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

            if(sophie.getMovementState()==ArcadeSophie.MovementState.STILL_LEFT||sophie.getMovementState()==ArcadeSophie.MovementState.STILL_RIGHT) {
                if(!(camera.position.x - v.x < -522 && v.y < 135)){
                    if(!bossMode){
                        if (v.x >= camera.position.x) {
                            sophie.setMovementState(ArcadeSophie.MovementState.MOVE_RIGHT);

                        } else {
                            sophie.setMovementState(ArcadeSophie.MovementState.MOVE_LEFT);
                        }
                    }

                }

            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if(sophie.getMovementState()==ArcadeSophie.MovementState.MOVE_LEFT)
                sophie.setMovementState(ArcadeSophie.MovementState.STILL_LEFT);
            else if(sophie.getMovementState() == ArcadeSophie.MovementState.MOVE_RIGHT)
                sophie.setMovementState(ArcadeSophie.MovementState.STILL_RIGHT);
            sophie.update();


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
