package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by sergiohernandezjr on 16/02/17.
 * Con ayuda de DON PABLO
 */

abstract class Frame implements Screen {
    //Main app class
    protected final App app;


    Preferences pref=Gdx.app.getPreferences("getLevel");

    //Screen sizes
    protected static  float WIDTH_MAP;
    protected static  float HEIGHT_MAP;

    protected static final float WIDTH = 1280;
    protected static final float HEIGHT = 720;
    protected static final float HALFW = WIDTH/2;
    protected static final float HALFH = HEIGHT/2;

    //Camera
    protected OrthographicCamera camera;

    //Main app class
    protected Viewport view;

    //Textures
    protected Texture pauseButton;//Image that holds creators photos and back button

    protected SpriteBatch batch;

    //Stage
    protected Stage frameStage;

    //Auxiliary screens
    protected Pause pauseStage;
    protected Stats statsStage;
    protected StatsInput statsInput;
    protected InputMultiplexer inputMultiplexer;

    //Gamestate
    protected GameState state = GameState.PLAYING;

    //AssetMng
    protected AssetManager aManager;

    // pause button
    protected Sprite pauseSprite;

    //Music
    protected Music bgMusic;

    protected StatsState stateStats;

    public Frame(App app, float WIDTH_MAP, float HEIGHT_MAP) {
        this.app = app;
        aManager= app.getAssetManager();
        this.WIDTH_MAP = WIDTH_MAP;
        this.HEIGHT_MAP = HEIGHT_MAP;
    }

    @Override
    public void show() {
        cameraInit();
        pauseInit();

    }

    private void cameraInit() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(HALFW, HALFH, 0);
        camera.update();

        view = new StretchViewport(WIDTH, HEIGHT);
    }

    private void pauseInit() {
        batch = new SpriteBatch();
        frameStage = new Stage(view, batch);
        pauseStage = new Pause(view, batch, app);

        pauseButton = aManager.get("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTPause.png");

        statsStage = new Stats(view, batch, app);
        statsInput = new StatsInput();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(statsStage);
        inputMultiplexer.addProcessor(statsInput);

        //pass the Stage
        Gdx.input.setInputProcessor(frameStage);

        //let go of android device back key
        Gdx.input.setCatchBackKey(false);
    }


    public class Stats extends Stage{
        private AssetManager aManager;
        private final App app;

        //Screen sizes
        public static final float WIDTH = 1280;
        public static final float HEIGHT = 720;
        public static final float HALFW = WIDTH/2;
        public static final float HALFH = HEIGHT/2;

        //Textures
        private Texture background; //Background

        Texture blueOrbPanel;
        Texture blueOrbArrow;
        Texture yellowOrbPanel;
        Texture yellowOrbArrow;
        Texture redOrbPanel;
        Texture redOrbArrow;
        Texture sophiePanel;
        Texture sophieArrow;

        Image sophiePanelImg;
        Image yellowOrbPanelImg;
        Image blueOrbPanelImg;
        Image redOrbPanelImg;

        Group sophieGroup;
        Group yellowOrbGroup;
        Group blueOrbGroup;
        Group redOrbGroup;

        final int PANEL_GAP = 80;
        final int H_PANEL_GAP = PANEL_GAP/2;
        final int W_BUTTONS = 290;
        final int W_BUTTON_SOPHIE = 350;
        final int H_BUTTON_LIFE = 420;
        final int H_BUTTON_ATK = 285;
        final int H_BUTTON_SPATK = 150;

        float xSophie;
        float ySophie;
        float xYellow;
        float yYellow;
        float xBlue;
        float yBlue;
        float xRed;
        float yRed;

        private Texture backBton;


        public Stats(Viewport view, SpriteBatch batch, App app){
            super(view, batch);
            this.app = app;
            this.aManager = app.getAssetManager();

            textureInit();
            objectInit();
        }

        public void textureInit(){
            switch(pref.getInteger("level")){
                default:
                case 0:
                    background = aManager.get("INTRO/INTROBackground.png");
                    break;
                case 1:
                    background = aManager.get("HARBOR/GoBackHARBOR0.png");
                    break;
                case 2:
                    background = aManager.get("MOUNTAINS/GoBackMOUNTAINS0.png");
                    break;

            }

            blueOrbPanel = aManager.get("Interfaces/STATS/STATSBlueOrb.png");
            blueOrbArrow = aManager.get("Interfaces/STATS/STATSBlueOrbArrow.png");
            yellowOrbPanel = aManager.get("Interfaces/STATS/STATSYellowOrb.png");
            yellowOrbArrow = aManager.get("Interfaces/STATS/STATSYellowOrbArrow.png");
            redOrbPanel = aManager.get("Interfaces/STATS/STATSRedOrb.png");
            redOrbArrow = aManager.get("Interfaces/STATS/STATSRedOrbArrow.png");
            sophiePanel = aManager.get("Interfaces/STATS/STATSSophie.png");
            sophieArrow = aManager.get("Interfaces/STATS/STATSSophieArrow.png");



            backBton = aManager.get("Interfaces/PAUSE/PAUSEback.png");
        }

        public void objectInit(){
            //background hace Image
            Image backgroundImg = new Image(background);
            backgroundImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
            this.addActor(backgroundImg);

            //back Btn
            TextureRegionDrawable backBtonTrd = new TextureRegionDrawable(new TextureRegion(backBton));
            ImageButton backBtonImg = new ImageButton(backBtonTrd);
            backBtonImg.setPosition(HALFW+470, 0);
            this.addActor(backBtonImg);

            backBtonImg.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    state = GameState.PAUSED;
                }
            });

            ///INITIALIZE PANELS
            //Draw stats panels
            sophiePanelImg = new Image(sophiePanel);
            yellowOrbPanelImg = new Image(yellowOrbPanel);
            blueOrbPanelImg = new Image(blueOrbPanel);
            redOrbPanelImg = new Image(redOrbPanel);

            //STATS BUTTONS
            //SOPHIE
            TextureRegionDrawable sophieArrowTrd = new TextureRegionDrawable(new TextureRegion(sophieArrow));
            ImageButton sophieLife = new ImageButton(sophieArrowTrd);
            sophieLife.addListener(new ClickListener(){
                //Upgrade Life Sophie
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("SOPHIE UPGRADE", "LIFE");
                }
            });

            sophieGroup = new Group();
            sophieGroup.addActor(sophiePanelImg);
            sophieGroup.addActor(sophieLife);

            sophiePanelImg.setZIndex(0);
            sophieLife.setZIndex(1);

            //Yellow Orb
            TextureRegionDrawable yellowOrbArrowTrd = new TextureRegionDrawable(new TextureRegion(yellowOrbArrow));
            ImageButton yellowOrbLife = new ImageButton(yellowOrbArrowTrd);
            ImageButton yellowOrbAtk = new ImageButton(yellowOrbArrowTrd);
            ImageButton yellowOrbSpAtk = new ImageButton(yellowOrbArrowTrd);
            yellowOrbLife.addListener(new ClickListener(){
                //Upgrade Life Yellow Orb
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("YELLOW ORB UPGRADE", "LIFE");
                }
            });

            yellowOrbAtk.addListener(new ClickListener(){
                //Upgrade Atk Yellow Orb
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("YELLOW ORB UPGRADE", "ATK");
                }
            });

            yellowOrbSpAtk.addListener(new ClickListener(){
                //Upgrade Sp Atk Yellow Orb
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("YELLOW ORB UPGRADE", "SP ATK");
                }
            });

            yellowOrbGroup = new Group();
            yellowOrbGroup.addActor(yellowOrbPanelImg);
            yellowOrbGroup.addActor(yellowOrbLife);
            yellowOrbGroup.addActor(yellowOrbAtk);
            yellowOrbGroup.addActor(yellowOrbSpAtk);

            yellowOrbPanelImg.setZIndex(0);
            yellowOrbLife.setZIndex(1);
            yellowOrbAtk.setZIndex(2);
            yellowOrbSpAtk.setZIndex(3);

            //Blue Orb
            TextureRegionDrawable blueOrbArrowTrd = new TextureRegionDrawable(new TextureRegion(blueOrbArrow));
            ImageButton blueOrbLife = new ImageButton(blueOrbArrowTrd);
            ImageButton blueOrbAtk = new ImageButton(blueOrbArrowTrd);
            ImageButton blueOrbSpAtk = new ImageButton(blueOrbArrowTrd);

            blueOrbLife.addListener(new ClickListener(){
                //Upgrade Life Blue Orb
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("BLUE ORB UPGRADE", "LIFE");
                }
            });

            blueOrbAtk.addListener(new ClickListener(){
                //Upgrade Atk Blue Orb
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("BLUE ORB UPGRADE", "ATK");
                }
            });

            blueOrbSpAtk.addListener(new ClickListener(){
                //Upgrade Sp Atk Blue Orb
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("BLUE ORB UPGRADE", "SP ATK");
                }
            });

            blueOrbGroup = new Group();
            blueOrbGroup.addActor(blueOrbPanelImg);
            blueOrbGroup.addActor(blueOrbLife);
            blueOrbGroup.addActor(blueOrbAtk);
            blueOrbGroup.addActor(blueOrbSpAtk);

            blueOrbPanelImg.setZIndex(0);
            blueOrbLife.setZIndex(1);
            blueOrbAtk.setZIndex(2);
            blueOrbSpAtk.setZIndex(3);

            //Red Orb
            TextureRegionDrawable redOrbArrowTrd = new TextureRegionDrawable(new TextureRegion(redOrbArrow));
            ImageButton redOrbLife = new ImageButton(redOrbArrowTrd);
            ImageButton redOrbAtk = new ImageButton(redOrbArrowTrd);
            ImageButton redOrbSpAtk = new ImageButton(redOrbArrowTrd);

            redOrbLife.addListener(new ClickListener(){
                //Upgrade Life Red Orb
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("RED ORB UPGRADE", "LIFE");
                }
            });

            redOrbAtk.addListener(new ClickListener(){
                //Upgrade Atk Red Orb
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("RED ORB UPGRADE", "ATK");
                }
            });

            redOrbSpAtk.addListener(new ClickListener(){
                //Upgrade Sp Atk Red Orb
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("RED ORB UPGRADE", "SP ATK");
                }
            });

            redOrbGroup = new Group();
            redOrbGroup.addActor(redOrbPanelImg);
            redOrbGroup.addActor(redOrbLife);
            redOrbGroup.addActor(redOrbAtk);
            redOrbGroup.addActor(redOrbSpAtk);

            redOrbPanelImg.setZIndex(0);
            redOrbLife.setZIndex(1);
            redOrbAtk.setZIndex(2);
            redOrbSpAtk.setZIndex(3);



            Group groupPanels = new Group();

            pref.putInteger("level", 3);

            switch(pref.getInteger("level")){
                case 0:
                    //SOPHIE
                    xSophie = HALFW-(sophiePanelImg.getWidth()/2);
                    ySophie = HALFH-sophiePanelImg.getHeight()/2;
                    sophiePanelImg.setPosition(xSophie, ySophie);
                    sophieLife.setPosition(xSophie+W_BUTTON_SOPHIE, ySophie+H_BUTTON_LIFE);

                    groupPanels.addActor(sophiePanelImg);
                    stateStats=StatsState.SOPHIE;
                    break;
                case 1:
                    //YELLOW ORB
                    xYellow = HALFW-(yellowOrbPanelImg.getWidth()/2)-H_PANEL_GAP;
                    yYellow = HALFH-yellowOrbPanelImg.getHeight()/2;

                    yellowOrbPanelImg.setPosition(xYellow, yYellow);
                    yellowOrbLife.setPosition(xYellow+W_BUTTONS,yYellow+H_BUTTON_LIFE);
                    yellowOrbAtk.setPosition(xYellow+W_BUTTONS,yYellow+H_BUTTON_ATK);
                    yellowOrbSpAtk.setPosition(xYellow+W_BUTTONS,yYellow+H_BUTTON_SPATK);

                    groupPanels.addActor(yellowOrbGroup);

                    //SOPHIE
                    xSophie = HALFW-(sophiePanelImg.getWidth()/2)+H_PANEL_GAP;
                    ySophie = HALFH-sophiePanelImg.getHeight()/2;

                    sophiePanelImg.setPosition(xSophie, ySophie);
                    sophieLife.setPosition(xSophie+W_BUTTON_SOPHIE,ySophie+H_BUTTON_LIFE);

                    groupPanels.addActor(sophieGroup);

                    //////
                    sophieGroup.setZIndex(0);
                    yellowOrbGroup.setZIndex(1);

                    stateStats=StatsState.YELLOW;
                    break;
                case 2:
                    //BLUE ORB
                    xBlue = HALFW-(blueOrbPanelImg.getWidth()/2)-PANEL_GAP;
                    yBlue = HALFH-blueOrbPanelImg.getHeight()/2;

                    blueOrbPanelImg.setPosition(xBlue, yBlue);
                    blueOrbLife.setPosition(xBlue+W_BUTTONS,yBlue+H_BUTTON_LIFE);
                    blueOrbAtk.setPosition(xBlue+W_BUTTONS,yBlue+H_BUTTON_ATK);
                    blueOrbSpAtk.setPosition(xBlue+W_BUTTONS,yBlue+H_BUTTON_SPATK);

                    groupPanels.addActor(blueOrbGroup);

                    //YELLOW ORB
                    xYellow = HALFW-(yellowOrbPanelImg.getWidth()/2);
                    yYellow = HALFH-yellowOrbPanelImg.getHeight()/2;

                    yellowOrbPanelImg.setPosition(xYellow, yYellow);
                    yellowOrbLife.setPosition(xYellow+W_BUTTONS,yYellow+H_BUTTON_LIFE);
                    yellowOrbAtk.setPosition(xYellow+W_BUTTONS,yYellow+H_BUTTON_ATK);
                    yellowOrbSpAtk.setPosition(xYellow+W_BUTTONS,yYellow+H_BUTTON_SPATK);

                    groupPanels.addActor(yellowOrbGroup);

                    //SOPHIE
                    xSophie = HALFW-(sophiePanelImg.getWidth()/2)+PANEL_GAP;
                    ySophie = HALFH-sophiePanelImg.getHeight()/2;

                    sophieLife.setPosition(xSophie+W_BUTTON_SOPHIE,ySophie+H_BUTTON_LIFE);
                    sophiePanelImg.setPosition(xSophie, ySophie);

                    groupPanels.addActor(sophieGroup);

                    //////
                    sophieGroup.setZIndex(0);
                    yellowOrbGroup.setZIndex(1);
                    blueOrbGroup.setZIndex(2);

                    stateStats=StatsState.BLUE;
                    break;
                case 3:
                default:
                    //RED ORB
                    xRed = HALFW-(redOrbPanelImg.getWidth()/2)-(PANEL_GAP+H_PANEL_GAP);
                    yRed = HALFH-redOrbPanelImg.getHeight()/2;

                    redOrbPanelImg.setPosition(xRed, yRed);
                    redOrbLife.setPosition(xRed+W_BUTTONS,yRed+H_BUTTON_LIFE);
                    redOrbAtk.setPosition(xRed+W_BUTTONS,yRed+H_BUTTON_ATK);
                    redOrbSpAtk.setPosition(xRed+W_BUTTONS,yRed+H_BUTTON_SPATK);

                    groupPanels.addActor(redOrbGroup);

                    //BLUE ORB
                    xBlue = HALFW-(blueOrbPanelImg.getWidth()/2)-H_PANEL_GAP;
                    yBlue = HALFH-blueOrbPanelImg.getHeight()/2;

                    blueOrbPanelImg.setPosition(xBlue, yBlue);
                    blueOrbLife.setPosition(xBlue+W_BUTTONS,yBlue+H_BUTTON_LIFE);
                    blueOrbAtk.setPosition(xBlue+W_BUTTONS,yBlue+H_BUTTON_ATK);
                    blueOrbSpAtk.setPosition(xBlue+W_BUTTONS,yBlue+H_BUTTON_SPATK);

                    groupPanels.addActor(blueOrbGroup);

                    //YELLOW ORB
                    xYellow = HALFW-(yellowOrbPanelImg.getWidth()/2)+H_PANEL_GAP;
                    yYellow = HALFH-yellowOrbPanelImg.getHeight()/2;

                    yellowOrbPanelImg.setPosition(xYellow, yYellow);
                    yellowOrbLife.setPosition(xYellow+W_BUTTONS,yYellow+H_BUTTON_LIFE);
                    yellowOrbAtk.setPosition(xYellow+W_BUTTONS,yYellow+H_BUTTON_ATK);
                    yellowOrbSpAtk.setPosition(xYellow+W_BUTTONS,yYellow+H_BUTTON_SPATK);

                    groupPanels.addActor(yellowOrbGroup);

                    //SOPHIE
                    xSophie = HALFW-(sophiePanelImg.getWidth()/2)+(PANEL_GAP+H_PANEL_GAP);
                    ySophie = HALFH-sophiePanelImg.getHeight()/2;

                    sophieLife.setPosition(xSophie+W_BUTTON_SOPHIE,ySophie+H_BUTTON_LIFE);
                    sophiePanelImg.setPosition(xSophie, ySophie);

                    groupPanels.addActor(sophieGroup);

                    //////
                    sophieGroup.setZIndex(0);
                    yellowOrbGroup.setZIndex(1);
                    blueOrbGroup.setZIndex(2);
                    redOrbGroup.setZIndex(3);

                    stateStats=StatsState.RED;
                    break;
            }

            //pref.putInteger("level", 1);

            this.addActor(groupPanels);
        }

    }

    private class StatsInput implements InputProcessor {
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
            v.set(v.x-camera.position.x+HALFW, v.y, 0);

            if(v.x>HALFW){
                switch (pref.getInteger("level")){
                    case 0:
                        break;
                    case 1:
                        switch (stateStats){
                            case SOPHIE:
                                //Do SHIT
                                break;
                            default:
                                //YELLOW ORB, Go to SOPHIE
                                statsStage.sophieGroup.setZIndex(1);
                                stateStats=StatsState.SOPHIE;
                                break;
                        }
                        break;
                    case 2:
                        switch (stateStats){
                            case SOPHIE:
                                //Do SHIT
                                break;
                            case YELLOW:
                                //YELLOW ORB, Go to SOPHIE
                                statsStage.sophieGroup.setZIndex(2);
                                stateStats=StatsState.SOPHIE;
                                break;
                            default:
                                //BLUE ORB, Go to YELLOW ORB
                                statsStage.yellowOrbGroup.setZIndex(2);
                                stateStats=StatsState.YELLOW;
                                break;
                        }
                        break;
                    case 3:
                    default:
                        switch (stateStats){
                            case SOPHIE:
                                //Do SHIT
                                break;
                            case YELLOW:
                                //YELLOW ORB, Go to SOPHIE
                                statsStage.sophieGroup.setZIndex(3);
                                stateStats=StatsState.SOPHIE;
                                break;
                            case BLUE:
                                //BLUE ORB, Go to YELLOW ORB
                                statsStage.yellowOrbGroup.setZIndex(3);
                                stateStats=StatsState.YELLOW;
                                break;
                            default:
                                //RED ORB, Go to BLUE ORB
                                statsStage.blueOrbGroup.setZIndex(3);
                                stateStats=StatsState.BLUE;
                                break;
                        }
                        break;
                }
            }else{
                switch (pref.getInteger("level")){
                    case 0:
                        break;
                    case 1:
                        switch (stateStats){
                            case YELLOW:
                                //Do nothing
                                break;
                            default:
                                //SOPHIE, Go to YELLOW
                                statsStage.yellowOrbGroup.setZIndex(1);
                                stateStats=StatsState.YELLOW;
                                break;
                        }
                        break;
                    case 2:
                        switch (stateStats){
                            case BLUE:
                                //Do SHIT
                                break;
                            case YELLOW:
                                //YELLOW ORB, Go to BLUE
                                statsStage.blueOrbGroup.setZIndex(2);
                                stateStats=StatsState.BLUE;
                                break;
                            default:
                                //SOPHIE, Go to YELLOW ORB
                                statsStage.yellowOrbGroup.setZIndex(2);
                                stateStats=StatsState.YELLOW;
                                break;
                        }
                        break;
                    case 3:
                    default:
                        switch (stateStats){
                            case RED:
                                //Do SHIT
                                break;
                            case BLUE:
                                //BLUE ORB, Go to RED
                                statsStage.redOrbGroup.setZIndex(3);
                                stateStats=StatsState.RED;
                                break;
                            case YELLOW:
                                //YELLOW ORB, Go to BLUE
                                statsStage.blueOrbGroup.setZIndex(3);
                                stateStats=StatsState.BLUE;
                                break;
                            default:
                                //SOPHIE, Go to YELLOW ORB
                                statsStage.yellowOrbGroup.setZIndex(3);
                                stateStats=StatsState.YELLOW;
                                break;
                        }
                        break;
                }
            }
            /*Gdx.app.log("current state", stateStats+"");

            for (int i = 0; i<statsStage.getActors().size;i++)
                Gdx.app.log("current actors", statsStage.getActors().get(i).toString() + " " + statsStage.getActors().get(i).getZIndex());*/
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}

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

    public class Pause extends Stage {
        private AssetManager aManager;
        private final App app;

        //Screen sizes
        public static final float WIDTH = 1280;
        public static final float HEIGHT = 720;
        public static final float HALFW = WIDTH/2;
        public static final float HALFH = HEIGHT/2;

        //Camera
        private OrthographicCamera camera;


        //Viewport
        private Viewport view;

        //Textures
        private Texture background; //Background
        private Texture bottom; //Image
        private Texture map; //Image
        private Texture quitBton; //Button
        private Texture statsBton; //Button
        private Texture top; //Image
        private Texture backBton; //Button
        private Texture musicBton;
        private Texture fxBton;

        Preferences prefes = Gdx.app.getPreferences("My Preferences");


        // buttons
        private ImageButton musicImg;
        private TextureRegionDrawable musicPlay;
        private ImageButton fxImg;
        private TextureRegionDrawable fxPlay;

        public void changeSoundTexture(){
            musicImg.remove();
            if(prefes.getBoolean("soundOn")){
                musicBton = aManager.get("Interfaces/SOUND/SOUNDMusicON.png");
            }else{
                musicBton = aManager.get("Interfaces/SOUND/SOUNDMusic.png");
            }
            musicPlay = new TextureRegionDrawable(new TextureRegion(musicBton));
            musicImg = new ImageButton(musicPlay );
            musicImg.setPosition(4*WIDTH/7, 8*HEIGHT/15);
            this.addActor(musicImg);

            musicImg.addListener(new ClickListener(){

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(prefes.getBoolean("soundOn")){
                        prefes.putBoolean("soundOn",false);
                    }else{
                        prefes.putBoolean("soundOn",true);
                    }
                    prefes.flush();
                    changeSoundTexture();
                }
            });
        }

        private void changeFxTexture() {
            fxImg.remove();
            if(prefes.getBoolean("fxOn")){
                fxBton = aManager.get("Interfaces/SOUND/SOUNDSoundON.png");
            }else{
                fxBton = aManager.get("Interfaces/SOUND/SOUNDSound.png");
            }

            fxPlay = new TextureRegionDrawable(new TextureRegion(fxBton));
            fxImg = new ImageButton(fxPlay);
            fxImg.setPosition(4*WIDTH/7, 4*HEIGHT/15);
            this.addActor(fxImg);

            fxImg.addListener(new ClickListener(){

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(prefes.getBoolean("fxOn")){
                        prefes.putBoolean("fxOn",false);

                    }else{
                        prefes.putBoolean("fxOn",true);

                    }
                    prefes.flush();
                    changeFxTexture();
                }
            });

        }

        private void textureInit(){
            switch(pref.getInteger("level")){
                default:
                case 0:
                    background = aManager.get("INTRO/INTROBackground.png");
                    break;
                case 1:
                    background = aManager.get("HARBOR/GoBackHARBOR0.png");
                    break;
                case 2:
                    background = aManager.get("MOUNTAINS/GoBackMOUNTAINS0.png");
                    break;
            }
            bottom = aManager.get("Interfaces/PAUSE/PAUSEBottomDisplay.png");
            map = aManager.get("Interfaces/PAUSE/PAUSEMapList.png");
            quitBton = aManager.get("Interfaces/PAUSE/PAUSEQuit.png");
            statsBton = aManager.get("Interfaces/PAUSE/PAUSEStats.png");
            top = aManager.get("Interfaces/PAUSE/PAUSETopDisplay.png");
            backBton = aManager.get("Interfaces/PAUSE/PAUSEback.png");

            if(prefes.getBoolean("soundOn")){
                musicBton = aManager.get("Interfaces/SOUND/SOUNDMusicON.png");
            }else{
                musicBton = aManager.get("Interfaces/SOUND/SOUNDMusic.png");
            }
            if(prefes.getBoolean("fxOn")){
                fxBton = aManager.get("Interfaces/SOUND/SOUNDSoundON.png");
            }else{
                fxBton = aManager.get("Interfaces/SOUND/SOUNDSound.png");
            }
        }

        private void objectInit(){
            //background hace Image
            Image backgroundImg = new Image(background);
            backgroundImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
            this.addActor(backgroundImg);

            //top
            Image topImg = new Image(top);
            topImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
            this.addActor(topImg);


            //bottom
            //1280x720
            Image bottomImg=new Image(bottom);
            bottomImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
            this.addActor(bottomImg);


            //map
            Image mapImg = new Image(map);
            mapImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2+220);
            this.addActor(mapImg);

            //statsBtn
            TextureRegionDrawable statsBtonTrd = new TextureRegionDrawable(new TextureRegion(statsBton));
            ImageButton statsBtonImg = new ImageButton(statsBtonTrd);

            statsBtonImg.setPosition(HALFW-statsBtonImg.getWidth()/2, 0);
            this.addActor(statsBtonImg);

            statsBtonImg.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    state = GameState.STATS;
                    Gdx.input.setInputProcessor(inputMultiplexer);
                }
            });

            //quitBton
            TextureRegionDrawable quitBtonTrd = new TextureRegionDrawable(new TextureRegion(quitBton));
            ImageButton quitBtonImg = new ImageButton(quitBtonTrd);

            quitBtonImg.setPosition(HALFW-470-quitBtonImg.getWidth(), 0);
            this.addActor(quitBtonImg);

            quitBtonImg.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    app.setScreen(new Fade(app, LoaderState.MAINMENU));
                    if(bgMusic != null){
                        if(bgMusic.isPlaying()){
                            bgMusic.pause();
                        }
                    }

                }
            });


            //back
            TextureRegionDrawable backBtonTrd = new TextureRegionDrawable(new TextureRegion(backBton));
            ImageButton backBtonImg = new ImageButton(backBtonTrd);

            backBtonImg.setPosition(HALFW+470, 0);
            this.addActor(backBtonImg);

            backBtonImg.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    state = GameState.PLAYING;
                }
            });

            //music

            musicPlay = new TextureRegionDrawable(new TextureRegion(musicBton));
            musicImg = new ImageButton(musicPlay );
            musicImg .setPosition(4*WIDTH/7, 8*HEIGHT/15);
            this.addActor(musicImg);

            musicImg.addListener(new ClickListener(){

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(prefes.getBoolean("soundOn")){
                        prefes.putBoolean("soundOn",false);
                    }else{
                        prefes.putBoolean("soundOn",true);

                    }
                    prefes.flush();
                    changeSoundTexture();
                }
            });

            //fx
            fxPlay = new TextureRegionDrawable(new TextureRegion(fxBton));
            fxImg = new ImageButton(fxPlay);
            fxImg.setPosition(4*WIDTH/7, 4*HEIGHT/15);
            this.addActor(fxImg);

            fxImg.addListener(new ClickListener(){

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(prefes.getBoolean("fxOn")){
                        prefes.putBoolean("fxOn",false);
                    }else{
                        prefes.putBoolean("fxOn",true);
                    }
                    prefes.flush();
                    changeFxTexture();
                }
            });
        }

        public Pause(Viewport view, SpriteBatch batch, App app) {
            super(view, batch);
            this.app = app;
            this.aManager = app.getAssetManager();

            textureInit();
            objectInit();
        }

    }

    enum StatsState{
        SOPHIE,
        YELLOW,
        BLUE,
        RED
    }
}

