package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
import com.badlogic.gdx.utils.TimeUtils;

/*
 * Created by gerry on 2/18/17.
 */


@SuppressWarnings("ConstantConditions")
class Arcade extends Frame{

    //B2D bodies
    private Array<Body> squirts = new Array<Body>();

    private World world;
    private HashSet<Body> deadThings;
    private ArrayList<Body> wall = new ArrayList<Body>();
    private float cooldown;

    private Squirt boss;
    private boolean bosssFight, bossActive = false;
    private float betweenSpawns = ArcadeValues.initalFrequency;

    private boolean putXp = false;
    private float acumulator = 0;
    private float elapsed = 0;
    private float arcadeMultiplier;

    private float shot = 0;
    private float hit = 0;
    private float match = 0;

    private Preferences stats = Gdx.app.getPreferences("STATS");

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

    private Texture pelletYellow;
    private Texture pelletBlue;
    private Texture pelletRed;

    private Texture lizard;
    private Texture goo;
    private Texture skull;
    private Texture spike;
    private Texture meteor;

    private Animation<TextureRegion> lizardAnimation;
    private Animation<TextureRegion> yellowGooAnimation;
    private Animation<TextureRegion> blueGooAnimation;
    private Animation<TextureRegion> redGooAnimation;
    private Animation<TextureRegion> skullRedAnimation;
    private Animation<TextureRegion> skullBlueAnimation;
    private Animation<TextureRegion> skullYellowAnimation;

    private Texture bossLizard;
    private Texture bossJaguar;
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

    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugMatrix;



    Arcade(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);
    }

    @Override
    public void show() {
        //d = pref.getInteger("level");
        debugRenderer=new Box2DDebugRenderer();
        d = 1;
        bosssFight = ArcadeValues.bossFightFlag;
        arcadeMultiplier = !bosssFight ? ArcadeValues.arcadeMultiplier : 1;
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


                pelletYellow = aManager.get("PELLET/ATAQUEYellowPellet.png");

                background = new Texture("HARBOR/GoBackHARBORPanoramic.png"); //switch
                break;
            case 2:
                orbYellow = new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png");

                orbBlue = new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png");


                pelletYellow = aManager.get("PELLET/ATAQUEYellowPellet.png");
                pelletBlue = aManager.get("PELLET/ATAQUEBluePellet.png");

                background = new Texture("HARBOR/GoBackHARBORPanoramic.png"); //switch
                break;
            case 3:
                orbYellow = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png");

                orbBlue = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png");

                orbRed = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADERedOrb.png");


                pelletYellow = aManager.get("PELLET/ATAQUEYellowPellet.png");
                pelletBlue = aManager.get("PELLET/ATAQUEBluePellet.png");
                pelletRed = aManager.get("PELLET/ATAQUERedPellet.png");

                background = new Texture("HARBOR/GoBackHARBORPanoramic.png"); //switch
                break;
        }


        //TODO USE ASSET MANAGER LAZY KOREAN
        lizard=new Texture("MINIONS/LIZARD/MINIONYellowLizard.png");
        goo=new Texture("MINIONS/GOO/MINIONAnimation.png");
        skull=new Texture("SKULL/MINIONSkulls.png");
        spike=new Texture("MINIONS/SPIKE/MINIONYellowSpike00.png");
        //LIKE SO
        meteor = aManager.get("MINIONS/METEOR/MINIONMeteor00.png");

        TextureRegion texturaCompleta = new TextureRegion(lizard);
        TextureRegion[][] texturaPersonaje = texturaCompleta.split(227,65);
        lizardAnimation = new Animation(0.18f, texturaPersonaje[0][0], texturaPersonaje[0][1]);
        lizardAnimation.setPlayMode(Animation.PlayMode.LOOP);

        texturaCompleta=new TextureRegion(goo);
        texturaPersonaje=texturaCompleta.split(75,150);
        yellowGooAnimation = new Animation(0.18f, texturaPersonaje[0][0], texturaPersonaje[0][1]);
        yellowGooAnimation.setPlayMode(Animation.PlayMode.LOOP);
        redGooAnimation = new Animation(0.18f, texturaPersonaje[0][2], texturaPersonaje[0][3]);
        redGooAnimation.setPlayMode(Animation.PlayMode.LOOP);
        blueGooAnimation = new Animation(0.18f, texturaPersonaje[0][4], texturaPersonaje[0][5]);
        blueGooAnimation.setPlayMode(Animation.PlayMode.LOOP);

        texturaCompleta=new TextureRegion(skull);
        texturaPersonaje=texturaCompleta.split(128,242);
        skullBlueAnimation = new Animation(0.18f, texturaPersonaje[0][0], texturaPersonaje[0][1]);
        skullRedAnimation = new Animation(0.18f, texturaPersonaje[0][2], texturaPersonaje[0][3]);
        skullYellowAnimation = new Animation(0.18f, texturaPersonaje[0][4], texturaPersonaje[0][5]);
        skullBlueAnimation.setPlayMode(Animation.PlayMode.LOOP);
        skullRedAnimation.setPlayMode(Animation.PlayMode.LOOP);
        skullYellowAnimation.setPlayMode(Animation.PlayMode.LOOP);

        sophieTx = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADESophie.png");
        bossLizard = aManager.get("BOSS/IGUANA/BOSSIguanaBody.png");
        bossJaguar= aManager.get("BOSS/JAGUAR/BOSSJaguarBody.png");
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
        sophie = new Sprite(sophieTx);

        if(bosssFight){
            switch(d){
                case 1:
                    boss = new ArcadeBoss(world, 1, bossLizard);
                    break;
                case 2:
                    boss = new ArcadeBoss(world, 2, bossLizard);
                    break;
                case 3:
                    boss = new ArcadeBoss(world, 1, bossLizard);
                    break;
            }
        }
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
                        hit++;
                        if ( ((Enemy)ob1).getHurtDie( ((OrbAttack)ob2).getColor(), ((OrbAttack)ob2).getDamage()) ) {
                            if (((Enemy) ob1).getColor() == ((OrbAttack) ob2).getColor()) match++;
                            deadThings.add(contact.getFixtureA().getBody());
                        }
                    }
                    if (ob2 instanceof Enemy) {
                        hit++;
                        if (((Enemy)ob2).getHurtDie(((OrbAttack)ob1).getColor(), ((OrbAttack)ob1).getDamage()) ) {
                            if (((OrbAttack) ob1).getColor() == ((Enemy) ob2).getColor()) match++;
                            deadThings.add(contact.getFixtureB().getBody());
                        }
                    }

                    if (ob1 instanceof ArcadeBoss) {
                        if ( ((ArcadeBoss)ob1).getHurtDie( ((OrbAttack)ob2).getColor(), ((OrbAttack)ob2).getDamage()) ) {
                            deadThings.add(contact.getFixtureA().getBody());
                        }
                    }
                    if (ob2 instanceof ArcadeBoss) {
                        if (((ArcadeBoss)ob2).getHurtDie(((OrbAttack)ob1).getColor(), ((OrbAttack)ob1).getDamage()) ) {
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

    @Override//TODO GET DEBUG SHIT ORGANIZED
    public void render(float delta) {

        debugMatrix = new Matrix4(super.camera.combined);
        debugMatrix.scale(100, 100, 1f);

        cls();
        batch.begin();
        drawShit();
        batch.draw(pauseButton,camera.position.x+HALFW-pauseButton.getWidth(),camera.position.y-HALFH);

        //If all orbes died
        boolean flag = false;
        for(ArcadeOrb orb : orbs) {
            if (orb != null) flag = true;
        }
        if(!flag) state = GameState.LOST;
        //

        if(state == GameState.STATS){
            statsStage.sophieCoins.setText(Integer.toString(statsStage.statsPrefs.getInteger("Coins")));
            statsStage.yellowXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.blueXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.redXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            batch.end();
            statsStage.draw();
            Gdx.input.setInputProcessor(inputMultiplexer);
        }else if (state == GameState.PAUSED) {//Draw pause menu
            Gdx.input.setInputProcessor(pauseStage);
            batch.end();
            pauseStage.draw();
        }else if(state != GameState.LOST){ //TODO RETURN TO SAVE OR MAIN MENU
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
        batch.begin();
        batch.setProjectionMatrix(super.camera.combined);
        debugRenderer.render(world, debugMatrix);
        batch.end();
    }

    private void drawShit(){
        batch.draw(background,-2560,0);
        sophie.setColor(1.0f,1.0f,1.0f,0.8f);
        sophie.setPosition(ArcadeValues.pelletOriginX-100
                , ArcadeValues.pelletOriginY-27);
        sophie.draw(batch);
        drawBodies();
    }

    private void drawBodies(){
        long time = TimeUtils.nanoTime();
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
            }else if(obj instanceof ArcadeSkull){
                ((ArcadeSkull)obj).draw(batch);
            }else if(obj instanceof ArcadeSpike){
                ((ArcadeSpike)obj).draw(batch);
            }else if(obj instanceof ArcadeMeteor){
                ((ArcadeMeteor)obj).draw(batch);
            }else if(obj instanceof ArcadeBoss){
                ((ArcadeBoss)obj).draw(batch);
            }
        }
        squirts.clear();
        /*
        before/after creating array in the method
        t0 = 3500000
        t1 = 2000000
         */
        //time = TimeUtils.nanoTime()-time;
        //Gdx.app.log("Time was:", ""+time);
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

    private void loose(float delta){
        if(!putXp) {
            stats.putInteger("XP", stats.getInteger("XP") + ((int)(hit + 10 * (hit / shot) + 10 * (hit * (match / hit))))/10);
            stats.flush();
            putXp = true;
            Gdx.app.log("Xp given", ":" + stats.getInteger("XP"));
        }
        dialoguetime += delta;
        if(dialoguetime < 2.5f) {
            dialogue.makeText(glyph, batch, "This dream overwhelmed you", camera.position.x);
            batch.end();
        }else{
            batch.end();
            app.setScreen(new Fade(app, LoaderState.ARCADE));
        }
    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void spawnMonsters(float delta){
        //TODO MAKE STEPS
        acumulator += delta;
        elapsed += delta;
        //betweenSpawns = 2f;
        betweenSpawns = freq(acumulator);
        if(elapsed > betweenSpawns){
            Gdx.app.log("time: "+acumulator, "freq: "+betweenSpawns);
            spawnSomething();
            elapsed = 0;
        }
    }

    private float freq(float time){
        if(time >= 0 && time < ArcadeValues.stepTimes[0]){
            return 2;
        }
        if(time >= ArcadeValues.stepTimes[0] && time < ArcadeValues.stepTimes[1]){// goes up and ends in 1.6
            return betweenSpawns - (0.006666666667f*arcadeMultiplier)/30;
        }
        if(time >= ArcadeValues.stepTimes[1] && time < ArcadeValues.stepTimes[2]){// stays in 1.6
            if(!bossActive && bosssFight) {
                ((ArcadeBoss) boss).move();

                bossActive = true;
            }
            return betweenSpawns;
        }
        if(time >= ArcadeValues.stepTimes[2] && time < ArcadeValues.stepTimes[3]){//goes up and ends in 1.2
            if(bossActive && bosssFight){
                ((ArcadeBoss) boss).move();
                bossActive = false;
            }
            return betweenSpawns - (0.006666666667f*arcadeMultiplier)/30;
        }
        if(time >= ArcadeValues.stepTimes[3] && time < ArcadeValues.stepTimes[4]){//stays in 1.2
            if(!bossActive && bosssFight) {
                ((ArcadeBoss) boss).move();
                bossActive = true;
            }
            return betweenSpawns;
        }
        if(time >= ArcadeValues.stepTimes[4] && time < ArcadeValues.stepTimes[5]){//goes up and ends in 0.8
            if(bossActive && bosssFight) {
                ((ArcadeBoss) boss).move();
                bossActive = false;
            }
            return betweenSpawns - (0.006666666667f*arcadeMultiplier)/30;
        }
        if(time >= ArcadeValues.stepTimes[5] && time < ArcadeValues.stepTimes[6]){//starts in 0.8 and goes slowly
            return betweenSpawns - (0.006666666667f*arcadeMultiplier)/50;
        }
        return betweenSpawns;

    }

    private void spawnSomething(){
        float hitCheck = hit <= 120 ? hit : 120;
        float e1 = (float)(1 - 0.5*(0.005* hitCheck + 0.2));
        float e0 = (float)(1 - (0.005* hitCheck + 0.2));

        float p = MathUtils.random();
        float lr = MathUtils.random();

        int c = calcColor();
        if(0 <= p && p < e0/2){ //skull
            float a = lr * MathUtils.PI;
            float x = ArcadeValues.pelletOriginX + ArcadeValues.highOnPot * MathUtils.cos(a);
            float y = ArcadeValues.pelletOriginY + ArcadeValues.highOnPot * MathUtils.sin(a);
            switch(c){
                case 1:
                    new ArcadeSkull(world, 1, a, x, y, skullYellowAnimation);
                    Gdx.app.log("Spawn", "Yellow Skull");
                    break;
                case 2:
                    new ArcadeSkull(world, 2, a, x, y, skullBlueAnimation);
                    Gdx.app.log("Spawn", "Blue Skull");
                    break;
                case 3:
                    new ArcadeSkull(world, 3, a, x, y, skullRedAnimation);
                    Gdx.app.log("Spawn", "Red Skull");
                    break;
            }
        }
        if(e0/2 <= p && p <e0){ //goo
            Gdx.app.log("Goo", "to Spawn");
            float a = lr * MathUtils.PI;
            float x = ArcadeValues.pelletOriginX + ArcadeValues.highOnPot * MathUtils.cos(a);
            float y = ArcadeValues.pelletOriginY + ArcadeValues.highOnPot * MathUtils.sin(a);
            switch(c){
                case 1:
                    new ArcadeGoo(world, 1, a, x, y, yellowGooAnimation);
                    Gdx.app.log("Spawn", "Yellow Goo");
                    break;
                case 2:
                    new ArcadeGoo(world, 2, a, x, y, blueGooAnimation);
                    Gdx.app.log("Spawn", "Blue Goo");
                    break;
                case 3:
                    new ArcadeGoo(world, 3, a, x, y, redGooAnimation);
                    Gdx.app.log("Spawn", "Red Goo");
                    break;
            }
        }
        if(e0 <= p && p < e1){ //lizard
            switch(c){
                case 1: //Lizard
                    new ArcadeLizard(world, 1, lr > 0.5 ? 0 : 1, lizardAnimation);
                    break;
                case 2: //Jaguar
                    new ArcadeLizard(world, 2, lr > 0.5 ? 0 : 1, lizardAnimation);
                    break;
                case 3: //Bat
                    new ArcadeLizard(world, 3, lr > 0.5 ? 0 : 1, lizardAnimation);
                    break;
            }
        }
        if(e1 <= p && p < e1+(1-e1)/3){ //spike
            float a = lr * MathUtils.PI;
            float x = ArcadeValues.pelletOriginX + ArcadeValues.highOnPot * MathUtils.cos(a);
            float y = ArcadeValues.pelletOriginY + ArcadeValues.highOnPot * MathUtils.sin(a);
            new ArcadeSpike(world, 1, a, x, y, spike);
        }
        if(e1+(1-e1)/3 <= p && p < e1+(2*(1-e1))/3){//meteor
            Gdx.app.log("Meteor", "Spawn");
            new ArcadeMeteor(world, (100+1080*lr), meteor);
        }
        if(e1+((2*(1-e1))/3) <= p && p <= 1){//arrow
            float a = lr * MathUtils.PI;
            float x = ArcadeValues.pelletOriginX + ArcadeValues.highOnPot * MathUtils.cos(a);
            float y = ArcadeValues.pelletOriginY + ArcadeValues.highOnPot * MathUtils.sin(a);
            new ArcadeSpike(world, 1, a, x, y, spike);
        }


    }

    private int calcColor() {
        float r = MathUtils.random();
        if (bosssFight) {
            switch (d) {
                case (1):
                    return d;
                case (2):
                    if (r >= 0.0f && r < 0.5f) return 1;
                    if (r >= 0.5f && r < 1f) return 2;
                    break;
                case (3):
                    if (r >= 0.0f && r < 0.33f) return 1;
                    if (r >= 0.33f && r < 0.66f) return 2;
                    if (r >= 0.66f && r <= 1f) return 3;
                    break;
                default:
                    return 1;
            }
        }else{
            switch (d) {
                case (1):
                    if (r >= 0.0f && r < 0.8f) return 1;
                    if (r >= 0.8f && r < 0.9f) return 2;
                    if (r >= 0.9f && r <= 1f) return 3;
                    break;
                case (2):
                    if (r >= 0.0f && r < 0.4f) return 1;
                    if (r >= 0.4f && r < 0.8f) return 2;
                    if (r >= 0.8f && r <= 1f) return 3;
                    break;
                case (3):
                    if (r >= 0.0f && r < 0.33f) return 1;
                    if (r >= 0.33f && r < 0.66f) return 2;
                    if (r >= 0.66f && r <= 1f) return 3;
                    break;
                default:
                    return 1;
            }
        }
        return 1;
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

    void kill(Body b){
        deadThings.add(b);
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