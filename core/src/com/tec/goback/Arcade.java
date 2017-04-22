package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;
import java.util.HashSet;

import com.badlogic.gdx.graphics.g2d.Animation;

/*
 * Created by gerry on 2/18/17.
 */


@SuppressWarnings("ConstantConditions")
class Arcade extends Frame{

    private World world;
    private HashSet<Body> deadThings;
    private ArrayList<Body> wall = new ArrayList<Body>();
    private float cooldown;

    private float betweenSpawns = ArcadeValues.initalFrequency;
    private float factor = ArcadeValues.initialFactor;

    private float elapsed = 0;
    private float elapsed2 = 0;

    private float shot = 0;
    private float hit = 0;
    private float match = 0;

    //CURRENT COLOR ORB
    private orbColor currentColor = orbColor.YELLOW;

    //MOTHER FUCKER
    private int d;

    // Music
    private Music bgMusic;
    private Sound fx;

    //Textures
    private Texture background; //Background

    private Texture sophieTx;
    private Sprite sophie;

    //orbes
    private boolean died = false;
    private ArcadeOrb[] orbs = new ArcadeOrb[3];
    private Texture orbRed;
    private Texture orbBlue;
    private Texture orbYellow;
    private Texture eyesred;
    private Texture eyesblue;
    private Texture eyesyellow;

    private Texture pelletYellow;
    private Texture pelletBlue;
    private Texture pelletRed;

    private Texture lizard;
    private Texture goo;
    private Texture skull;
    private Texture spike;

    private Animation<TextureRegion> lizardAnimation;
    private Animation<TextureRegion> gooAnimation;
    private Animation<TextureRegion> skullRedAnimation;
    private Animation<TextureRegion> skullBlueAnimation;
    private Animation<TextureRegion> skullYellowAnimation;

    /*
    private float timerchangeframelizard;
    private float timerchangeframegoo;
    private float timerchangeframeskullred;
    private float timerchangeframeskullblue;
    private float timerchangeframeskullyellow;
    */


    private static final float WIDTH_MAP = 1280;
    private static final float HEIGHT_MAP = 720;

    private Dialogue dialogue;
    private GlyphLayout glyph = new GlyphLayout();
    private float dialoguetime = 0.0f;

    private Input input;

    private Preferences soundPreferences = Gdx.app.getPreferences("My Preferences");

    Arcade(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);
    }

    @Override
    public void show() {
        //d = pref.getInteger("level");
        d = 3;
        super.show();
        textureInit();
        worldInit();
        allyInit();
        wallsInit();
        input = new Input();
        dialogue = new Dialogue(aManager);
        Gdx.input.setInputProcessor(input);
        Gdx.input.setCatchBackKey(true); //Not important


        pelletBlue = aManager.get("PELLET/ATAQUEBluePellet.png");
        musicInit();
    }

    private void musicInit() {
        bgMusic = aManager.get("MUSIC/GoBackMusicArcade.mp3");
        if(soundPreferences.getBoolean("soundOn")) {
            bgMusic.setLooping(true);
            bgMusic.play();
        }
    }

    private void textureInit() {
        switch (d){
            case 1:
                orbYellow = new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png");
                eyesyellow= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrbEyes.png");

                pelletYellow = aManager.get("PELLET/ATAQUEYellowPellet.png");

                background = new Texture("HARBOR/GoBackHARBORPanoramic.png"); //switch
                break;
            case 2:
                orbYellow = new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png");
                eyesyellow= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrbEyes.png");
                orbBlue = new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png");
                eyesblue= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrbEyes.png");

                pelletYellow = aManager.get("PELLET/ATAQUEYellowPellet.png");
                pelletBlue = aManager.get("PELLET/ATAQUEBluePellet.png");

                background = new Texture("HARBOR/GoBackHARBORPanoramic.png"); //switch
                break;
            case 3:
                orbYellow = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png");
                eyesyellow = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrbEyes.png");
                orbBlue = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png");
                eyesblue = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrbEyes.png");
                orbRed = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADERedOrb.png");
                eyesred = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADERedOrbEyes.png");

                pelletYellow = aManager.get("PELLET/ATAQUEYellowPellet.png");
                pelletBlue = aManager.get("PELLET/ATAQUEBluePellet.png");
                pelletRed = aManager.get("PELLET/ATAQUERedPellet.png");

                background = new Texture("HARBOR/GoBackHARBORPanoramic.png"); //switch
                break;
        }

        lizard=new Texture("MINIONS/LIZARD/MINIONYellowLizard.png");
        goo=new Texture("MINIONS/GOO/MINIONYellowGoo.png");
        skull=new Texture("SKULL/MINIONSkulls.png");
        spike=new Texture("MINIONS/SPIKE/MINIONYellowSpike00.png");

        TextureRegion texturaCompleta = new TextureRegion(lizard);
        TextureRegion[][] texturaPersonaje = texturaCompleta.split(227,65);
        lizardAnimation = new Animation(0.18f, texturaPersonaje[0][0], texturaPersonaje[0][1]);
        lizardAnimation.setPlayMode(Animation.PlayMode.LOOP);

        texturaCompleta=new TextureRegion(goo);
        texturaPersonaje=texturaCompleta.split(118,125);
        gooAnimation = new Animation(0.18f, texturaPersonaje[0][0], texturaPersonaje[0][1]);
        gooAnimation.setPlayMode(Animation.PlayMode.LOOP);

        texturaCompleta=new TextureRegion(skull);
        texturaPersonaje=texturaCompleta.split(128,242);
        skullBlueAnimation = new Animation(0.18f, texturaPersonaje[0][0], texturaPersonaje[0][1]);
        skullRedAnimation = new Animation(0.18f, texturaPersonaje[0][2], texturaPersonaje[0][3]);
        skullYellowAnimation = new Animation(0.18f, texturaPersonaje[0][4], texturaPersonaje[0][5]);
        skullBlueAnimation.setPlayMode(Animation.PlayMode.LOOP);
        skullRedAnimation.setPlayMode(Animation.PlayMode.LOOP);
        skullYellowAnimation.setPlayMode(Animation.PlayMode.LOOP);

    }

    private void allyInit(){
        orbs[0] = new ArcadeYellowOrb(world, orbYellow, 0, true, 100);
        switch(d){
            case 2:
                orbs[1] = new ArcadeBlueOrb(world, orbBlue, 1, false, 100);
                orbs[2] = null;
                break;
            case 3:
                orbs[1] = new ArcadeBlueOrb(world, orbBlue, 1, false, 100);
                orbs[2] = new ArcadeRedOrb(world, orbRed, 2, false, 100);
        }
        sophieTx = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADESophie.png");
        sophie = new Sprite(sophieTx);
    }

    private void wallsInit(){
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(ArcadeValues.pxToMeters(-120),0);

        wall.add(world.createBody(bd)); //append to body array 
        makeWallFixture(wall.get(0),100,HEIGHT_MAP);

        bd.position.set(ArcadeValues.pxToMeters(WIDTH_MAP+120),0);

        wall.add(world.createBody(bd)); //append to body array 
        makeWallFixture(wall.get(1),100,HEIGHT_MAP);

        bd.position.set(ArcadeValues.pxToMeters(-120),ArcadeValues.pxToMeters(-120));

        wall.add(world.createBody(bd)); //append to body array 
        makeWallFixture(wall.get(2),WIDTH_MAP+200,100);

        bd.position.set(ArcadeValues.pxToMeters(-120), ArcadeValues.pxToMeters(HEIGHT_MAP+120));

        wall.add(world.createBody(bd)); //append to body array 
        makeWallFixture(wall.get(3),WIDTH_MAP+200,100);
    }

    private void makeWallFixture(Body b, float x, float y){
        //neumann preventive shit
        /*
        for (Fixture f : b.getFixtureList()){
            b.destroyFixture(f);
        }
        */

        /*
        Iterator<Fixture> it = b.getFixtureList().iterator();
        while(it.hasNext()){
            b.destroyFixture(it.next());
        }
        */


        FixtureDef f = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(ArcadeValues.pxToMeters(x),ArcadeValues.pxToMeters(y));
        f.shape = shape;

        f.filter.categoryBits = ArcadeValues.wallCat;
        f.filter.maskBits = ArcadeValues.wallMask;
        b.createFixture(f);
    }

    private void worldInit(){
        world = new World(Vector2.Zero, true);
        //fstep = 0f;
        deadThings = new HashSet<Body>();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                //check who the fuck is colliding and update deadThings for deletion
                Object ob1 = contact.getFixtureA().getBody().getUserData();
                Object ob2 = contact.getFixtureB().getBody().getUserData();

                //pellets die no matter who they collide with
                if(ob1 instanceof OrbAttack){
                    deadThings.add(contact.getFixtureA().getBody());
                    //Gdx.app.log("DESTRUC", deadThings.size+"");
                }
                if(ob2 instanceof OrbAttack){
                    deadThings.add(contact.getFixtureB().getBody());
                    //Gdx.app.log("DESTRUC", deadThings.size+"");
                }



                if(ob1 instanceof ArcadeOrb || ob2 instanceof ArcadeOrb) {//If we got hit
                    if (ob1 instanceof ArcadeOrb) {
                        if (((ArcadeOrb)ob1).getHurtDie(((Enemy)ob2).getColor(), ((Enemy)ob2).getDamage())){
                            deadThings.add(contact.getFixtureA().getBody());
                            orbs[ ((ArcadeOrb)ob1).getColor()-1 ] = null;
                            died = true;
                        }
                        deadThings.add(contact.getFixtureB().getBody());
                    }
                    if (ob2 instanceof ArcadeOrb) {
                        if (((ArcadeOrb)ob2).getHurtDie(((Enemy)ob1).getColor(), ((Enemy)ob1).getDamage())){
                            deadThings.add(contact.getFixtureB().getBody());
                            orbs[((ArcadeOrb)ob2).getColor()-1] = null;
                            died = true;
                        }
                        deadThings.add(contact.getFixtureA().getBody());
                    }

                }else{//If some bad guy got hit
                    if (ob1 instanceof Enemy) {
                        if ( ((Enemy)ob1).getHurtDie( ((OrbAttack)ob2).getColor(), ((OrbAttack)ob2).getDamage()) ) {
                            //Gdx.app.log("LACONCHA", ""+((OrbAttack) ob2).getColor());
                            hit++;
                            if (((Enemy) ob1).getColor() == ((OrbAttack) ob2).getColor()) match++;
                            deadThings.add(contact.getFixtureA().getBody());
                        }
                    }
                    if (ob2 instanceof Enemy) {
                        if (((Enemy)ob2).getHurtDie(((OrbAttack)ob1).getColor(), ((OrbAttack)ob1).getDamage()) ) {
                            //Gdx.app.log("LACONCHA", ""+((OrbAttack) ob1).getColor());
                            hit++;
                            if (((OrbAttack) ob1).getColor() == ((Enemy) ob2).getColor()) match++;
                            deadThings.add(contact.getFixtureB().getBody());
                        }
                    }

                    //Gdx.app.log("enemy", "was hit");
                }

            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });

    }//         <------COLLISION EVENT HERE

    @Override
    public void render(float delta) {
        Gdx.app.log(""+d,"");
        batch.setProjectionMatrix(super.camera.combined);
        cls();

        batch.begin();

        drawShit();
        batch.draw(pauseButton,camera.position.x+HALFW-pauseButton.getWidth(),camera.position.y-HALFH);

        boolean flag = false;
        for(ArcadeOrb orb : orbs) {
            if (orb != null) flag = true;
        }

        if(!flag) state = GameState.LOST;

        if (state==GameState.PAUSED) {
            Gdx.input.setInputProcessor(pauseStage);
            batch.end();
            pauseStage.draw();
        }else if(state != GameState.LOST){
            Gdx.input.setInputProcessor(input);
            stepper(delta);
            spawnMonsters(delta);
            if(died){
                swapOrbes();
                died = false;
            }
            batch.end();
        }else{
            loose(delta);
        }
    }

    private void loose(float delta){
        //Calc score
        /*
        preference.write(

        hit + 10*(hit/shot) + 10*(hit*(match/hit))

        );
        */

        float score = hit + 10*(hit/shot) + 10*(hit*(match/hit));
        Gdx.app.log("Score was:", ""+score);
        dialoguetime += delta;
        if(dialoguetime < 2.5f) {
            dialogue.makeText(glyph, batch, "This dream overwhelmed you", camera.position.x);
            batch.end();
        }else{
            app.setScreen(new Fade(app, LoaderState.ARCADE));
        }
    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void spawnMonsters(float delta){
        elapsed += delta;
        elapsed2 += delta;
        if(elapsed > betweenSpawns){
            spawnSomething();
            elapsed = 0;
        }
        if(elapsed2 > 1) {
            betweenSpawns -= factor;
            elapsed2 = 0;
        }
    }

    private void spawnSomething(){
        float r = MathUtils.random();
        float lr =MathUtils.random();
        int color  = calcColor();
        if(r >= 0.5f && r < 1f){//lizard (0.25)
            //Gdx.app.log("Enemy", " spawned");
            switch (color){
                case(1):
                    if(lr>=0.5) new ArcadeLizard(world, color, 1, lizardAnimation);
                    else        new ArcadeLizard(world, color, 0, lizardAnimation);
                    break;
                case(2):
                    if(lr>=0.5) new ArcadeLizard(world, color, 1, lizardAnimation);
                    else        new ArcadeLizard(world, color, 0, lizardAnimation);
                    break;
                case(3):
                    if(lr>=0.5) new ArcadeLizard(world, color, 1, lizardAnimation);
                    else        new ArcadeLizard(world, color, 0, lizardAnimation);
            }
        }
        /*
        if(r >= 0.25f && r < 0.5f){//spike
            switch (color){
                case(1):
                    if(lr>=0.5) new ArcadeLizard(world, color, 1, lizardAnimation);
                    else new ArcadeLizard(world, color, 0, lizardAnimation);
                    break;
                case(2):
                    if(lr>=0.5) new ArcadeLizard(world, color, 1, lizardAnimation);
                    else new ArcadeLizard(world, color, 0, lizardAnimation);
                    break;
                case(3):
                    if(lr>=0.5) new ArcadeLizard(world, color, 1, lizardAnimation);
                    else new ArcadeLizard(world, color, 0, lizardAnimation);
                    break;
            }
        }
        */
        if(r >= 0.0f && r <= 0.5f){//goo
            double a = lr * Math.PI;
            double x = ArcadeValues.pelletOriginX + ArcadeValues.highOnPot * Math.cos(a);
            double y = ArcadeValues.pelletOriginY + ArcadeValues.highOnPot * Math.sin(a);
            Gdx.app.log("new goo at "+x, "spawned at angle"+ (float)a*MathUtils.radiansToDegrees);
            new ArcadeGoo(world, 1, (float)a, (float)x, (float)y, gooAnimation);

        }

        /*
        if(r >= 0.75f && r <= 1.0f){//skull
            switch (color){
                case(1):
                    if(lr>=0.5) new ArcadeLizard(world, color, 1, lizardAnimation);
                    else new ArcadeLizard(world, color, 0, lizardAnimation);
                    break;
                case(2):
                    if(lr>=0.5) new ArcadeLizard(world, color, 1, lizardAnimation);
                    else new ArcadeLizard(world, color, 0, lizardAnimation);
                    break;
                case(3):
                    if(lr>=0.5) new ArcadeLizard(world, color, 1, lizardAnimation);
                    else new ArcadeLizard(world, color, 0, lizardAnimation);
                    break;
            }
        }
        */
    }

    private int calcColor(){
        float r = MathUtils.random();
        switch (d){
            case(1):
                if(r >= 0.0f && r < 0.8f) return 1;
                if(r >= 0.8f && r < 0.9f) return 2;
                if(r >= 0.9f && r <= 1f) return 3;
                break;
            case(2):
                if(r >= 0.0f && r < 0.4f) return 1;
                if(r >= 0.4f && r < 0.8f) return 2;
                if(r >= 0.8f && r <= 1f) return 3;
                break;
            case(3):
                if(r >= 0.0f && r < 0.4f) return 1;
                if(r >= 0.4f && r < 0.8f) return 2;
                if(r >= 0.8f && r <= 1f) return 3;
                break;
            default:
                return 1;
        }
        return 1;
    }

    private void stepper(float delta){
        // /*

        //Gdx.app.log("Bodies: "+world.getBodyCount(), ", Fixtures: "+world.getFixtureCount());

        // */


        //much steps
        world.step(1/60f, 6, 2);

        //clean dead things
        for(Body b: deadThings){
            while(b.getFixtureList().size > 0){
                b.destroyFixture(b.getFixtureList().get(0));
            }
            world.destroyBody(b);
        }
        deadThings.clear();

        cooldown += delta;
        //        fstep += delta;
//        while(fstep > 1/120f){
//            world.step(1/120F, 8, 3);
//
//            //clean dead things
//            for(Body b: deadThings){
//                while(b.getFixtureList().size > 0){
//                    b.destroyFixture(b.getFixtureList().get(0));
//                }
//                world.destroyBody(b);
//            }
//            deadThings.clear();
//
//
//            fstep -= 1/120f;
//        }

    }

    private void drawShit(){
        batch.draw(background,-2560,0);
        sophie.setColor(1.0f,1.0f,1.0f,0.8f);
        sophie.setPosition(ArcadeValues.pelletOriginX-100
                , ArcadeValues.pelletOriginY-27);
        sophie.draw(batch);

        //B2D bodies
        Array<Body> squirts = new Array<Body>();
        world.getBodies(squirts);
        Object obj;
        for(Body b: squirts){
            obj = b.getUserData();
            if(obj instanceof OrbAttack) {
                ((OrbAttack) obj).draw(batch);
            }else if(obj instanceof ArcadeOrb){
                ((ArcadeOrb)obj).draw(batch);
            }else if(obj instanceof ArcadeLizard){
                ((ArcadeLizard)obj).draw(batch);
            }else if(obj instanceof ArcadeGoo){
            ((ArcadeGoo)obj).draw(batch);
        }
        }
    }

    private void swapOrbes(){
        if(d > 1) {
            Integer[] places = new Integer[3];
            Float[] lives = new Float[3];
            int n = 0;
            for(int i = 0; i < orbs.length; i++){
                if(orbs[i] != null){
                    places[i] = orbs[i].getPlace();
                    lives[i] = orbs[i].getLife();
                    deadThings.add(orbs[i].getBody());
                    orbs[i] = null;
                    n++;

                }else{
                    places[i] = null;
                    lives[i] = null;
                }

            }

            for(int i = 0; i < places.length; i++){
                if(places[i] != null){
                    switch(i){
                        case 0:
                            orbs[i] = new ArcadeYellowOrb(  world, orbYellow, (places[i]+1)%n,  ((places[i]+1)%n)==0,  lives[0]);
                            break;
                        case 1:
                            orbs[i] = new ArcadeBlueOrb(    world, orbBlue, (places[i]+1)%n,  ((places[i]+1)%n)==0,    lives[1]);
                            break;
                        case 2:
                            orbs[i] = new ArcadeRedOrb(     world, orbRed, (places[i]+1)%n,  ((places[i]+1)%n)==0,    lives[2]);
                    }
                }
            }
            for(int i = 0; i < 3; i++){
                if(orbs[i] != null){
                    if(orbs[i].getPlace() == 0) {
                        switch (orbs[i].getColor() - 1) {
                            case 0:
                                currentColor = orbColor.YELLOW;
                                break;
                            case 1:
                                currentColor = orbColor.BLUE;
                                break;
                            case 2:
                                currentColor = orbColor.RED;
                        }
                    }
                }
            }
        }

    }


    @Override
    public void resize(int width, int height) {
        view.update(width, height);
    }

//WTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTF
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
//WTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTF
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
        public boolean touchDown(int screenX, int screenY, int pointer, int button)
        {
            v.set(screenX,screenY,0);
            camera.unproject(v);

            if(//if hit orb
                        (v.x > ArcadeValues.pelletOriginX-60 && v.x < ArcadeValues.pelletOriginX+60)
                    &&  (v.y > ArcadeValues.pelletOriginY-60 && v.y < ArcadeValues.pelletOriginY+60)
                    &&  state == GameState.PLAYING
            )
            {
                swapOrbes();
            }else if(v.x > 1172 && v.y < 135){//if hit pause
                state = GameState.PAUSED;
            }else if(state == GameState.PLAYING){//if we're not switching orbs we shooting
                if(cooldown > 0.2f){
                    float angle = MathUtils.atan2(
                            v.y - ArcadeValues.pelletOriginY,
                            v.x - ArcadeValues.pelletOriginX
                    );
                    switch (currentColor) {
                        case YELLOW:
                            new OrbAttack(world, 1, angle, pelletYellow);
                            //Gdx.app.log("1", "");
                            break;
                        case BLUE:
                            new OrbAttack(world, 2, angle, pelletBlue);
                            //Gdx.app.log("2", "");
                            break;
                        case RED:
                            new OrbAttack(world, 3, angle, pelletRed);
                            //Gdx.app.log("3", "");
                            break;
                    }
                    cooldown = 0.0f;
                }

            }
            shot ++;
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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

    private enum orbColor {
        YELLOW,
        BLUE,
        RED
    }
}