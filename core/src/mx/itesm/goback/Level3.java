package mx.itesm.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
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
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by kevin on 18/03/17.
 */
class Level3 extends Frame {
    //box2d shit
    private World world;
    private HashSet<Body> deadThings;
    private HashSet<Body> outofBountThings;
    private HashMap<Sprite,Integer> sprts;
    private HashSet<Sprite> dsprts;
    private HashSet<Sprite> ddsprts;
    // Sophie
    //private Sophie sophie;
    private Texture sophieTexture;
    boolean sophieInitFlag = true;
    boolean orbClueInitFlag = true;
    private Dialogue dialogue;

    private GlyphLayout glyph = new GlyphLayout();

    private float dialoguetime = 0;
    private float vx=1.2f;
    private float vy=0.5f;

    private ArcadeSophie sophie;
    private OrbMovement currentOrbState = OrbMovement.GOING_DOWN;
    private OrbMovement currentRedState = OrbMovement.GOING_DOWN;
    private OrbMovement currentBlueOrbState = OrbMovement.GOING_UP;

    // background
    Texture background;
    Texture arrow1;
    Texture arrow2;
    Texture arrow3;
    Texture airs;
    Sprite clue;
    Sprite orb;
    Sprite air;
    Sprite sign;

    //BlueOrb
    private Sprite blueOrb;
    private float blueOrbXPosition = -500;
    private float blueOrbYPosition = 200;
    private final float DISTANCE_BLUE_ORB_SOPHIE = 20;

    //YellowOrb
    private Sprite yellowOrb;
    private float yellowOrbXPosition = -200;
    private float yellowOrbYPosition = 160;
    private final float DISTANCE_YELLOW_ORB_SOPHIE = -130;



    //map
    public static final float WIDTH_MAP = 3840;
    public static final float HEIGHT_MAP = 720;

    protected static final float LEFT_LIMIT = 20;
    protected static float RIGHT_LIMIT = 3650;

    private Input level3Input = new Input();
    private boolean cond1=true;
    private float jumpC=0f;

    // preferences
    Preferences pref = Gdx.app.getPreferences("getLevel");
    Preferences soundPreferences = Gdx.app.getPreferences("My Preferences");

    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugMatrix;

    private Random randomArrowPosition = new Random();
    private Array<Body> bodies = new Array<Body>();
    private Object obj;

    private float timeForArrow = 0;
    private float timeForAir=0;

    public Level3(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);

    }

    @Override
    public void show(){
        super.show();
        textureInit();
        worldInit();
        dialogue = new Dialogue(aManager);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(level3Input);

        debugRenderer=new Box2DDebugRenderer();

    }

    private void worldInit() {
        world = new World(Vector2.Zero, true);
        deadThings = new HashSet<Body>();
        outofBountThings= new HashSet<Body>();
        sprts=new HashMap<Sprite, Integer>();
        sprts.put(air,1);
        world.setContactListener(new ContactListener() {
                                     @Override
                                     public void beginContact(Contact contact) {
                                         Object ob1 = contact.getFixtureA().getBody().getUserData();
                                         Object ob2 = contact.getFixtureB().getBody().getUserData();
                                         if(ob1 instanceof ArcadeSophie || ob2 instanceof ArcadeSophie){
                                             if(ob1 instanceof ArcadeSophie){
                                                 deadThings.add(contact.getFixtureB().getBody());
                                             }
                                             if(ob2 instanceof ArcadeSophie){
                                                 deadThings.add(contact.getFixtureA().getBody());
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
        clue.setPosition(0,200);
        orb.setPosition(-100,180);
        sign.setPosition(600,250);
        air.setPosition(-10,200);
    }

    private void textureInit() {
        // background
        background = aManager.get("WOODS/WOODSPanoramic2of2.png");

        arrow1= aManager.get("MINIONS/ARROW/MINIONBlueArrow00.png");
        arrow2= aManager.get("MINIONS/ARROW/MINIONRedArrow00.png");
        arrow3= aManager.get("MINIONS/ARROW/MINIONYellowArrow00.png");

        // sophie
        sophieTexture = new Texture("Squirts/Sophie/SOPHIEComplete.png");

        clue=new Sprite( new Texture("CLUES/Note/CLUESNote.png"));
        orb= new Sprite(new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADERedOrb.png")));
        airs=new Texture("WOODS/WOODSAir.png");
        air= new Sprite(airs);
        sign= new Sprite(new Texture("WOODS/WOODSSign.png"));

        blueOrb = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png"));
        blueOrb.setPosition(blueOrbXPosition,blueOrbYPosition);
        blueOrb.setColor(1.0f,1.0f,1.0f,0.4f);
        yellowOrb = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png"));
        yellowOrb.setPosition(yellowOrbXPosition,yellowOrbYPosition);
        yellowOrb.setColor(1.0f,1.0f,1.0f,0.4f);
    }

    @Override
    public void render(float delta) {
        debugMatrix = new Matrix4(super.camera.combined);
        debugMatrix.scale(100, 100, 1f);
        cls();

        if(soundPreferences.getBoolean("soundOn"))
            bgMusic.play();
        else
            bgMusic.stop();

        batch.begin();
        batch.setProjectionMatrix(super.camera.combined);

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
            drawAirs(batch,delta);
            if(orbClueInitFlag){
                orbClueInit(batch,delta);
                sign.draw(batch);
            }else{
                setOrbClueFin(batch);
            }
            if(sophieInitFlag) {
                sophieInitialMove();
            }
            if(sophie.getX()>2900&&cond1&&sophie.getMovementState()!= ArcadeSophie.MovementState.JUMP&&sophie.getMovementState()!= ArcadeSophie.MovementState.JUMP2){
                sophie.setMovementState(ArcadeSophie.MovementState.STILL_RIGHT);
                cond1=false;
            }
//            if(sophie.getX()<3000) drawArrow(delta);
//            if(sophie.getX()<ArcadeValues.pxToMeters(3000)) drawArrow(delta);
            drawArrow(delta);
            sophie.update();
            sophie.draw(batch);
            blueOrb.draw(batch);
            yellowOrb.draw(batch);
            moveBlueOrb(delta);
            moveYellowOrb(delta);
            Gdx.app.log("sophie", (sophie.getX()+" "+sophie.getY()));
            updateCamera();
            Gdx.input.setInputProcessor(level3Input);
            stepper(delta);
            batch.end();

            if(sophie.life <= 0&&sophie.getMovementState()!= ArcadeSophie.MovementState.JUMP&&sophie.getMovementState()!= ArcadeSophie.MovementState.JUMP2){
                state = GameState.LOST;
            }

            if(sophie.getX()>3200) sophie.setMovementState(ArcadeSophie.MovementState.JUMPFIN);
            killAirs();
            if(blueOrbYPosition<-40){
                pref.putBoolean("boss",true);
                pref.flush();
                ArcadeValues.bossFightFlag = true;
                app.setScreen(new Fade(app, LoaderState.ARCADE));
                this.dispose();
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




//        batch.begin();
//        batch.setProjectionMatrix(super.camera.combined);
//        debugRenderer.render(world, debugMatrix);
//        batch.end();
    }

    private void drawAirs(Batch batch,float delta){
        timeForAir += delta;
        if(timeForAir >= 15f){
            float height= MathUtils.random(200f,500f);
            Sprite sh=new Sprite(airs);
            sh.setPosition(-10,height);
            sprts.put(sh,1);
            timeForAir = 0;
        }
        dsprts=new HashSet<Sprite>(sprts.keySet());
        sprts.clear();
        for(Sprite s:dsprts){
            s.setPosition(s.getX()+2,s.getY());
            sprts.put(s,1);
            s.draw(batch);
        }
        timeForAir++;
    }
    private void killAirs(){
        ddsprts=new HashSet<Sprite>(sprts.keySet());
        sprts.clear();
        for(Sprite s:ddsprts){
            if(s.getX()<2700) sprts.put(s,1);
        }
    }

    private void drawArrow(float delta) {
        // must appear at 1420
        timeForArrow += delta;
        if(timeForArrow >= .9&&sophie.getX()<2640){
            int rl= MathUtils.random(1);
            int typeshit= MathUtils.random(2);
            switch (typeshit){
                case 0:
                    new ArcadeArrow(world, typeshit,1,rl, arrow1);
                    break;
                case 1:
                    new ArcadeArrow(world, typeshit,1,rl, arrow2);
                    break;
                case 2:
                    new ArcadeArrow(world, typeshit,1,rl, arrow3);
                    break;
            }
            timeForArrow = 0;
        }

        world.getBodies(bodies);
        for(Body b : bodies){
            obj = b.getUserData();
            if( obj instanceof ArcadeArrow){
                ((ArcadeArrow)obj).draw(batch);
//                Gdx.app.log("arrows", ((ArcadeArrow) obj).sprite.getY()+"");
                if(((ArcadeArrow) obj).sprite.getX() <= 0){
                    outofBountThings.add(b);
                }
            }else if(obj instanceof ArcadeSophie){
                ((ArcadeSophie)obj).draw(batch);
            }


        }

        bodies.clear();
    }

    private void stepper(float delta){
        world.step(1/60f, 6, 2);
        for(Body b: outofBountThings){
            while(b.getFixtureList().size > 0){
                b.destroyFixture(b.getFixtureList().get(0));
            }
            world.destroyBody(b);
        }
        outofBountThings.clear();

        for(Body b: deadThings){
            while(b.getFixtureList().size > 0){
                b.destroyFixture(b.getFixtureList().get(0));
            }
            world.destroyBody(b);
            sophie.life -= 10;
        }
        deadThings.clear();
    }

    private void sophieInitialMove() {

        if(sophie.getX() < 250){
            sophie.setMovementState(ArcadeSophie.MovementState.MOVE_RIGHT);
        }else{
            sophieInitFlag = false;
            sophie.setMovementState(ArcadeSophie.MovementState.MOVE_RIGHT);
        }
    }

    private void orbClueInit(Batch batch,float delta){
        if(orb.getX()<3100) {
            orb.setX(orb.getX() + delta * 100);
            if (orb.getY() >= 200) { // 155
                currentOrbState = OrbMovement.GOING_DOWN;
            } else if (orb.getY() <= 160) { //145
                currentOrbState = OrbMovement.GOING_UP;
            }
            if (currentOrbState == OrbMovement.GOING_UP) {
                orb.setY(orb.getY() + delta * 30);
            } else if (currentOrbState == OrbMovement.GOING_DOWN) {
                orb.setY(orb.getY() - delta * 30);
            }
            if (clue.getX() < 3230) clue.setX(clue.getX() + delta * 100);
            clue.draw(batch);
            orb.draw(batch);
        }else orbClueInitFlag=false;
    }

    private void setOrbClueFin(Batch batch){
        if(sophie.getX()>2900){
            vy-=0.008f*jumpC;
            orb.setX(orb.getX()+vx*jumpC);
            orb.setY(orb.getY()+vy*jumpC);
            clue.setX(clue.getX()+vx*jumpC);
            clue.setY(clue.getY()+vy*jumpC);
        }
        clue.draw(batch);
        orb.draw(batch);
        jumpC+=0.005;
    }
    private void moveBlueOrb(float delta){
        if(blueOrbXPosition<3300) {
            if (blueOrbYPosition >= 230) { // 155
                currentBlueOrbState = OrbMovement.GOING_DOWN;
            } else if (blueOrbYPosition <= 170) { //145
                currentBlueOrbState = OrbMovement.GOING_UP;
            }
            blueOrbXPosition = blueOrb.getX();
            if (currentBlueOrbState == OrbMovement.GOING_UP) {
                blueOrbYPosition += delta * 30;
            } else if (currentBlueOrbState == OrbMovement.GOING_DOWN) {
                blueOrbYPosition -= delta * 30;
            }
            blueOrb.setPosition(blueOrbXPosition, blueOrbYPosition);
            if (blueOrb.getX() < (sophie.getX() - blueOrb.getWidth() - DISTANCE_BLUE_ORB_SOPHIE)) {
                blueOrb.setPosition(sophie.getX() - blueOrb.getWidth() - DISTANCE_BLUE_ORB_SOPHIE + 1, blueOrb.getY());
            } else if ((sophie.getX() + sophie.getSprite().getWidth() + DISTANCE_BLUE_ORB_SOPHIE) < blueOrb.getX()) {
                blueOrb.setPosition(sophie.getX() + sophie.getSprite().getWidth() + DISTANCE_BLUE_ORB_SOPHIE - 1, blueOrb.getY());
            }
        }else{
            blueOrbYPosition-=3;
            blueOrb.setY(blueOrbYPosition);
        }
    }

    private void moveYellowOrb(float delta){
        if(yellowOrbXPosition<3300) {
            if (yellowOrbYPosition >= 155) { // 155
                currentRedState = OrbMovement.GOING_DOWN;
            } else if (yellowOrbYPosition <= 125) { //145
                currentRedState = OrbMovement.GOING_UP;
            }
            yellowOrbXPosition = yellowOrb.getX();

            if (currentBlueOrbState == OrbMovement.GOING_UP) {
                yellowOrbYPosition += delta * 30;
            } else if (currentBlueOrbState == OrbMovement.GOING_DOWN) {
                yellowOrbYPosition -= delta * 30;
            }

            yellowOrb.setPosition(yellowOrbXPosition, yellowOrbYPosition);
            if (yellowOrb.getX() < (sophie.getX() - yellowOrb.getWidth() - DISTANCE_YELLOW_ORB_SOPHIE)) {
                yellowOrb.setPosition(sophie.getX() - yellowOrb.getWidth() - DISTANCE_YELLOW_ORB_SOPHIE + 1, yellowOrb.getY());
            } else if ((sophie.getX() + sophie.getSprite().getWidth() + DISTANCE_YELLOW_ORB_SOPHIE) < yellowOrb.getX()) {
                yellowOrb.setPosition(sophie.getX() + sophie.getSprite().getWidth() + DISTANCE_YELLOW_ORB_SOPHIE - 1, yellowOrb.getY());
            }
        }else{
            yellowOrbYPosition-=3;
            yellowOrb.setY(yellowOrbYPosition);
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
        aManager.unload("MINIONS/ARROW/MINIONBlueArrow00.png");
        aManager.unload("MINIONS/ARROW/MINIONRedArrow00.png");
        aManager.unload("MINIONS/ARROW/MINIONYellowArrow00.png");
        aManager.unload("WOODS/WOODSPanoramic2of2.png");
        aManager.unload("Squirts/Sophie/SOPHIEWalk.png");
        outofBountThings.clear();
        deadThings.clear();

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

    private void loose(float delta) {
        dialoguetime += delta;

        if(dialoguetime < 3.5f) {
            dialogue.makeText(glyph, batch, "This dream overwhelmed you", camera.position.x);
            batch.end();
        }else{
            batch.end();
            app.setScreen(new Fade(app, LoaderState.LEVEL3));
            dispose();
        }
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
            if(camera.position.x - v.x < -522 && v.y < 135){
                state = GameState.PAUSED;
            }
            if(sophie.getMovementState()==ArcadeSophie.MovementState.STILL_LEFT) {
                if (v.x >= camera.position.x) {
                    sophie.setMovementState(ArcadeSophie.MovementState.JUMP);
                }else if(v.x<camera.position.x){
                    sophie.setMovementState(ArcadeSophie.MovementState.MOVE_LEFT);
                }
            } else if(sophie.getMovementState()==ArcadeSophie.MovementState.JUMP){
                if (v.x >= camera.position.x) {
                    sophie.setMovementState(ArcadeSophie.MovementState.JUMP2);
                }
            }else if(sophie.getMovementState()==ArcadeSophie.MovementState.STILL_RIGHT){
                if (v.x >= camera.position.x) {
                    sophie.setMovementState(ArcadeSophie.MovementState.MOVE_RIGHT);
                }
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if(sophie.getMovementState()==ArcadeSophie.MovementState.MOVE_LEFT)
                sophie.setMovementState(ArcadeSophie.MovementState.STILL_LEFT);
            else if(sophie.getMovementState() == ArcadeSophie.MovementState.MOVE_RIGHT){
                sophie.setMovementState(ArcadeSophie.MovementState.STILL_RIGHT);
            }
//                sophie.setMovementState(ArcadeSophie.MovementState.STILL_RIGHT);
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
