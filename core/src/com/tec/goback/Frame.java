package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    protected Texture background;

    protected SpriteBatch batch;

    //Stage
    protected Stage frameStage;


    //Auxiliary screens
    protected Pause pauseStage;
    protected Stats statsStage;
    protected ClueDetail clueStage;

    protected InputProcessor statsInput;
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
            case 3:
                background = aManager.get("WOODS/WOODSBeginning.png");
                break;
            case 4:
                background = aManager.get("WOODS/WOODSEnding.png");
                break;
        }

        batch = new SpriteBatch();
        frameStage = new Stage(view, batch);
        pauseStage = new Pause(view, batch, app);

        pauseButton = aManager.get("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTPause.png");

        statsStage = new Stats(view, batch, app);
        statsInput = new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
            @Override
            public void onRight() {
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

            @Override
            public void onLeft() {
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
            }

            @Override
            public void onUp() {

            }

            @Override
            public void onDown() {

            }
        });

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(statsStage);
        inputMultiplexer.addProcessor(statsInput);

        //pass the Stage
        Gdx.input.setInputProcessor(frameStage);

        //let go of android device back key
        Gdx.input.setCatchBackKey(false);
    }


    public class Stats extends Stage{
        private final int[] LIFE_COST_ARR = {0,150,300,600,900,1800};
        private final int[] LIFE_COST_SOPHIE_ARR = {0,1,2,3,4,5};
        private final float[] LIFE_ARR = {100f,125f,150f,175f,200f,250f};

        private final int[] ATK_COST_ARR = {0,300,600,1200,2400,4800};
        private final float[] ATK_ARR = {20f,25f,35f,50f,70f,100f};

        private AssetManager aManager;
        private final App app;

        //Screen sizes
        public static final float WIDTH = 1280;
        public static final float HEIGHT = 720;
        public static final float HALFW = WIDTH/2;
        public static final float HALFH = HEIGHT/2;

        //Textures

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

        float xSophie = 0;
        float ySophie = 0;
        float xYellow = 0;
        float yYellow = 0;
        float xBlue = 0;
        float yBlue = 0;
        float xRed = 0;
        float yRed = 0;

        private Texture backBton;

        //LABELS
        Preferences statsPrefs = Gdx.app.getPreferences("STATS");
        Label yellowLifeLbl;
        Label yellowAtkLbl;
        Label yellowXPLbl;
        Label yellowSpAtkLbl;

        Label blueLifeLbl;
        Label blueAtkLbl;
        Label blueXPLbl;
        Label blueSpAtkLbl;

        Label redLifeLbl;
        Label redAtkLbl;
        Label redXPLbl;
        Label redSpAtkLbl;

        Label sophieCoins;
        Label nextUp;


        public Stats(Viewport view, SpriteBatch batch, App app){
            super(view, batch);
            this.app = app;
            this.aManager = app.getAssetManager();

            textureInit();
            objectInit();
        }

        public void textureInit(){


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
            yellowOrbPanelImg = new Image(yellowOrbPanel);
            blueOrbPanelImg = new Image(blueOrbPanel);
            redOrbPanelImg = new Image(redOrbPanel);

            

            //Declare LabelStyle and fonts for XP and cost texts
            BitmapFont smallFont = new BitmapFont(Gdx.files.internal("fira.fnt"));
            BitmapFont largeFont = new BitmapFont(Gdx.files.internal("fira.fnt"));
            largeFont.getData().setScale(2f);
            smallFont.getData().setScale(1f);

            Label.LabelStyle lblStySophie = new Label.LabelStyle(smallFont, new Color (1f, 1f, 1f, 1f));

            Label.LabelStyle lblStyYellowXP = new Label.LabelStyle(largeFont, new Color (1f, 0.8666666667f, 0.007843137255f, 1f));
            Label.LabelStyle lblStyYellow = new Label.LabelStyle(smallFont, new Color (1f, 0.8666666667f, 0.007843137255f, 1f));

            Label.LabelStyle lblStyBlueXP = new Label.LabelStyle(largeFont, new Color (0.2039215686f, 0.8352941176f, 0.9529411765f, 1f));            
            Label.LabelStyle lblStyBlue = new Label.LabelStyle(smallFont, new Color (0.2039215686f, 0.8352941176f, 0.9529411765f, 1f));

            Label.LabelStyle lblStyRedXP = new Label.LabelStyle(largeFont, new Color (1f, 0.1803921569f, 0.09803921569f, 1f));
            Label.LabelStyle lblStyRed = new Label.LabelStyle(smallFont, new Color (1f, 0.1803921569f, 0.09803921569f, 1f));

            //STATS BUTTONS
            //SOPHIE
            sophiePanelImg = new Image(sophiePanel);

            TextureRegionDrawable sophieArrowTrd = new TextureRegionDrawable(new TextureRegion(sophieArrow));
            ImageButton sophieLife = new ImageButton(sophieArrowTrd);
            if (statsPrefs.getInteger("SophieLifeStg")>LIFE_COST_SOPHIE_ARR.length-1) {//No XP to buy or already full
                sophieLife.setColor(1f, 1f, 1f, 0.5f);
            }else{
                if(statsPrefs.getInteger("Coins")<LIFE_COST_SOPHIE_ARR[statsPrefs.getInteger("SophieLifeStg")+1])
                    sophieLife.setColor(1f, 1f, 1f, 0.5f);
            }

            sophieCoins = new Label(Integer.toString(statsPrefs.getInteger("Coins")),lblStySophie);
            Label obsidian = new Label(Integer.toString(statsPrefs.getInteger("Obsidian")),lblStySophie);
            Label quartz = new Label(Integer.toString(statsPrefs.getInteger("Quartz")),lblStySophie);

            if(statsPrefs.getInteger("SophieLifeStg")<LIFE_COST_SOPHIE_ARR.length-1)
                nextUp = new Label(Integer.toString(LIFE_COST_SOPHIE_ARR[statsPrefs.getInteger("SophieLifeStg")+1]),lblStySophie);
            else{
                nextUp = new Label("MAX",lblStySophie);
            }

            sophieLife.addListener(new UpgradeButtons(StatsState.SOPHIE, 1, sophieLife));

            sophieGroup = new Group();
            sophieGroup.addActor(sophiePanelImg);
            sophieGroup.addActor(sophieLife);
            sophieGroup.addActor(sophieCoins);
            sophieGroup.addActor(obsidian);
            sophieGroup.addActor(quartz);
            sophieGroup.addActor(nextUp);

            sophiePanelImg.setZIndex(0);
            sophieLife.setZIndex(1);
            sophieCoins.setZIndex(2);
            obsidian.setZIndex(3);
            quartz.setZIndex(4);
            nextUp.setZIndex(5);

            //Yellow Orb
            TextureRegionDrawable yellowOrbArrowTrd = new TextureRegionDrawable(new TextureRegion(yellowOrbArrow));
            ImageButton yellowOrbLife = new ImageButton(yellowOrbArrowTrd);
            if (statsPrefs.getInteger("YellowLifeStg")>=LIFE_COST_ARR.length-1) {//No XP to buy or already full
                yellowOrbLife.setColor(1f, 1f, 1f, 0.5f);
            }else{
                if(statsPrefs.getInteger("XP")<LIFE_COST_ARR[statsPrefs.getInteger("YellowLifeStg")+1])
                    yellowOrbLife.setColor(1f, 1f, 1f, 0.5f);
            }

            ImageButton yellowOrbAtk = new ImageButton(yellowOrbArrowTrd);
            if (statsPrefs.getInteger("YellowAtkStg")>=ATK_COST_ARR.length-1) {//No XP to buy or already full
                yellowOrbAtk.setColor(1f, 1f, 1f, 0.5f);
            }else{
                if(statsPrefs.getInteger("XP")<ATK_COST_ARR[statsPrefs.getInteger("YellowAtkStg")+1])
                    yellowOrbAtk.setColor(1f, 1f, 1f, 0.5f);
            }
            //ImageButton yellowOrbSpAtk = new ImageButton(yellowOrbArrowTrd);

            yellowXPLbl = new Label(Integer.toString(statsPrefs.getInteger("XP")),lblStyYellowXP);
            yellowXPLbl.setOrigin(yellowXPLbl.getWidth()/2, yellowXPLbl.getHeight()/2);

            if(statsPrefs.getInteger("YellowLifeStg")<LIFE_COST_ARR.length-1){
                yellowLifeLbl = new Label(Integer.toString(LIFE_COST_ARR[statsPrefs.getInteger("YellowLifeStg")+1]),lblStyYellow);
            }else{
                yellowLifeLbl = new Label("MAX",lblStyYellow);
            }
            yellowLifeLbl.setOrigin(yellowLifeLbl.getWidth()/2, yellowLifeLbl.getHeight()/2);

            if(statsPrefs.getInteger("YellowAtkStg")<ATK_COST_ARR.length-1){
                yellowAtkLbl = new Label(Integer.toString(ATK_COST_ARR[statsPrefs.getInteger("YellowAtkStg")+1]),lblStyYellow);
            }else{
                yellowAtkLbl = new Label("MAX",lblStyYellow);
            }
            yellowAtkLbl.setOrigin(yellowAtkLbl.getWidth()/2, yellowAtkLbl.getHeight()/2);

            yellowOrbLife.addListener(new UpgradeButtons(StatsState.YELLOW, 1, yellowOrbLife));
            yellowOrbAtk.addListener(new UpgradeButtons(StatsState.YELLOW, 2, yellowOrbAtk));
            //yellowOrbSpAtk.addListener(new UpgradeButtons(StatsState.YELLOW, 3));

            yellowOrbGroup = new Group();
            yellowOrbGroup.addActor(yellowOrbPanelImg);
            yellowOrbGroup.addActor(yellowOrbLife);
            yellowOrbGroup.addActor(yellowOrbAtk);
            //yellowOrbGroup.addActor(yellowOrbSpAtk);
            yellowOrbGroup.addActor(yellowLifeLbl);
            yellowOrbGroup.addActor(yellowAtkLbl);
            yellowOrbGroup.addActor(yellowXPLbl);

            yellowOrbPanelImg.setZIndex(0);
            yellowOrbLife.setZIndex(1);
            yellowOrbAtk.setZIndex(2);
            //yellowOrbSpAtk.setZIndex(3);
            yellowLifeLbl.setZIndex(4);
            yellowAtkLbl.setZIndex(5);
            yellowXPLbl.setZIndex(6);

            //Blue Orb
            TextureRegionDrawable blueOrbArrowTrd = new TextureRegionDrawable(new TextureRegion(blueOrbArrow));
            ImageButton blueOrbLife = new ImageButton(blueOrbArrowTrd);
            if (statsPrefs.getInteger("BlueLifeStg")>=LIFE_COST_ARR.length-1) {//No XP to buy or already full
                blueOrbLife.setColor(1f, 1f, 1f, 0.5f);
            }else{
                if(statsPrefs.getInteger("XP")<LIFE_COST_ARR[statsPrefs.getInteger("BlueLifeStg")+1])
                    blueOrbLife.setColor(1f, 1f, 1f, 0.5f);
            }

            ImageButton blueOrbAtk = new ImageButton(blueOrbArrowTrd);
            if (statsPrefs.getInteger("BlueAtkStg")>=ATK_COST_ARR.length-1) {//No XP to buy or already full
                blueOrbAtk.setColor(1f, 1f, 1f, 0.5f);
            }else{
                if(statsPrefs.getInteger("XP")<ATK_COST_ARR[statsPrefs.getInteger("BlueAtkStg")+1])
                    blueOrbAtk.setColor(1f, 1f, 1f, 0.5f);
            }
            //ImageButton blueOrbSpAtk = new ImageButton(blueOrbArrowTrd);

            blueXPLbl = new Label(Integer.toString(statsPrefs.getInteger("XP")),lblStyBlueXP);
            blueXPLbl.setOrigin(blueXPLbl.getWidth()/2, blueXPLbl.getHeight()/2);

            if(statsPrefs.getInteger("BlueLifeStg")<LIFE_COST_ARR.length-1){
                blueLifeLbl = new Label(Integer.toString(LIFE_COST_ARR[statsPrefs.getInteger("BlueLifeStg")+1]),lblStyBlue);
            }else{
                blueLifeLbl = new Label("MAX",lblStyBlue);
            }
            blueLifeLbl.setOrigin(blueLifeLbl.getWidth()/2, blueLifeLbl.getHeight()/2);

            if(statsPrefs.getInteger("BlueAtkStg")<ATK_COST_ARR.length-1){
                blueAtkLbl = new Label(Integer.toString(ATK_COST_ARR[statsPrefs.getInteger("BlueAtkStg")+1]),lblStyBlue);
            }else{
                blueAtkLbl = new Label("MAX",lblStyBlue);
            }
            blueAtkLbl.setOrigin(blueAtkLbl.getWidth()/2, blueAtkLbl.getHeight()/2);

            blueOrbLife.addListener(new UpgradeButtons(StatsState.BLUE, 1, blueOrbLife));
            blueOrbAtk.addListener(new UpgradeButtons(StatsState.BLUE, 2, blueOrbAtk));
            //blueOrbSpAtk.addListener(new UpgradeButtons(StatsState.BLUE, 3));

            blueOrbGroup = new Group();
            blueOrbGroup.addActor(blueOrbPanelImg);
            blueOrbGroup.addActor(blueOrbLife);
            blueOrbGroup.addActor(blueOrbAtk);
            //blueOrbGroup.addActor(blueOrbSpAtk);
            blueOrbGroup.addActor(blueLifeLbl);
            blueOrbGroup.addActor(blueAtkLbl);
            blueOrbGroup.addActor(blueXPLbl);

            blueOrbPanelImg.setZIndex(0);
            blueOrbLife.setZIndex(1);
            blueOrbAtk.setZIndex(2);
            //blueOrbSpAtk.setZIndex(3);
            blueLifeLbl.setZIndex(4);
            blueAtkLbl.setZIndex(5);
            blueXPLbl.setZIndex(6);

            //Red Orb
            TextureRegionDrawable redOrbArrowTrd = new TextureRegionDrawable(new TextureRegion(redOrbArrow));
            ImageButton redOrbLife = new ImageButton(redOrbArrowTrd);
            if (statsPrefs.getInteger("RedLifeStg")>=LIFE_COST_ARR.length-1) {//No XP to buy or already full
                redOrbLife.setColor(1f, 1f, 1f, 0.5f);
            }else{
                if(statsPrefs.getInteger("XP")<LIFE_COST_ARR[statsPrefs.getInteger("RedLifeStg")+1])
                    redOrbLife.setColor(1f, 1f, 1f, 0.5f);
            }

            ImageButton redOrbAtk = new ImageButton(redOrbArrowTrd);
            if (statsPrefs.getInteger("RedAtkStg")>=ATK_COST_ARR.length-1) {//No XP to buy or already full
                redOrbAtk.setColor(1f, 1f, 1f, 0.5f);
            }else{
                if(statsPrefs.getInteger("XP")<ATK_COST_ARR[statsPrefs.getInteger("RedAtkStg")+1])
                    redOrbAtk.setColor(1f, 1f, 1f, 0.5f);
            }
            /*ImageButton redOrbSpAtk = new ImageButton(redOrbArrowTrd);
            if (statsPrefs.getInteger("RedLifeStg")>LIFE_COST_ARR.length-1) {//No XP to buy or already full
                redOrbLife.setColor(1f, 1f, 1f, 0.5f);
            }else{
                if(statsPrefs.getInteger("XP")<LIFE_COST_ARR[statsPrefs.getInteger("RedLifeStg")+1])
                    redOrbLife.setColor(1f, 1f, 1f, 0.5f);
            }*/

            redXPLbl = new Label(Integer.toString(statsPrefs.getInteger("XP")),lblStyRedXP);
            redXPLbl.setOrigin(redXPLbl.getWidth()/2, redXPLbl.getHeight()/2);

            if(statsPrefs.getInteger("RedLifeStg")<LIFE_COST_ARR.length-1){
                redLifeLbl = new Label(Integer.toString(LIFE_COST_ARR[statsPrefs.getInteger("RedLifeStg")+1]),lblStyRed);
            }else{
                redLifeLbl = new Label("MAX",lblStyRed);
            }
            redLifeLbl.setOrigin(redLifeLbl.getWidth()/2, redLifeLbl.getHeight()/2);

            if(statsPrefs.getInteger("RedAtkStg")<ATK_COST_ARR.length-1){
                redAtkLbl = new Label(Integer.toString(ATK_COST_ARR[statsPrefs.getInteger("RedAtkStg")+1]),lblStyRed);
            }else{
                redAtkLbl = new Label("MAX",lblStyRed);
            }
            redAtkLbl.setOrigin(redAtkLbl.getWidth()/2, redAtkLbl.getHeight()/2);

            redOrbLife.addListener(new UpgradeButtons(StatsState.RED, 1, redOrbLife));
            redOrbAtk.addListener(new UpgradeButtons(StatsState.RED, 2, redOrbAtk));
            //redOrbSpAtk.addListener(new UpgradeButtons(StatsState.RED, 3));

            redOrbGroup = new Group();
            redOrbGroup.addActor(redOrbPanelImg);
            redOrbGroup.addActor(redOrbLife);
            redOrbGroup.addActor(redOrbAtk);
            //redOrbGroup.addActor(redOrbSpAtk);
            redOrbGroup.addActor(redLifeLbl);
            redOrbGroup.addActor(redAtkLbl);
            redOrbGroup.addActor(redXPLbl);

            redOrbPanelImg.setZIndex(0);
            redOrbLife.setZIndex(1);
            redOrbAtk.setZIndex(2);
            //redOrbSpAtk.setZIndex(3);
            redLifeLbl.setZIndex(4);
            redAtkLbl.setZIndex(5);
            redXPLbl.setZIndex(6);

            //////////

            Group groupPanels = new Group();

            //pref.putInteger("level", 3);

            switch(pref.getInteger("level")){
                case 0:
                    //SOPHIE
                    xSophie = HALFW-(sophiePanelImg.getWidth()/2);
                    groupPanels.addActor(sophieGroup);

                    stateStats=StatsState.SOPHIE;
                    break;
                case 1:
                    //YELLOW ORB
                    xYellow = HALFW-(yellowOrbPanelImg.getWidth()/2)-H_PANEL_GAP;
                    groupPanels.addActor(yellowOrbGroup);

                    //SOPHIE
                    xSophie = HALFW-(sophiePanelImg.getWidth()/2)+H_PANEL_GAP;
                    groupPanels.addActor(sophieGroup);

                    stateStats=StatsState.YELLOW;
                    break;
                case 2:
                    //BLUE ORB
                    xBlue = HALFW-(blueOrbPanelImg.getWidth()/2)-PANEL_GAP;
                    groupPanels.addActor(blueOrbGroup);

                    //YELLOW ORB
                    xYellow = HALFW-(yellowOrbPanelImg.getWidth()/2);
                    groupPanels.addActor(yellowOrbGroup);

                    //SOPHIE
                    xSophie = HALFW-(sophiePanelImg.getWidth()/2)+PANEL_GAP;
                    groupPanels.addActor(sophieGroup);

                    stateStats=StatsState.BLUE;
                    break;
                case 3:
                default:
                    //RED ORB
                    xRed = HALFW-(redOrbPanelImg.getWidth()/2)-(PANEL_GAP+H_PANEL_GAP);
                    groupPanels.addActor(redOrbGroup);

                    //BLUE ORB
                    xBlue = HALFW-(blueOrbPanelImg.getWidth()/2)-H_PANEL_GAP;
                    groupPanels.addActor(blueOrbGroup);

                    //YELLOW ORB
                    xYellow = HALFW-(yellowOrbPanelImg.getWidth()/2)+H_PANEL_GAP;
                    groupPanels.addActor(yellowOrbGroup);

                    //SOPHIE
                    xSophie = HALFW-(sophiePanelImg.getWidth()/2)+(PANEL_GAP+H_PANEL_GAP);
                    groupPanels.addActor(sophieGroup);

                    stateStats=StatsState.RED;
                    break;
            }

            //////DEFINE DRAW ORDER
            sophieGroup.setZIndex(0);
            yellowOrbGroup.setZIndex(1);
            blueOrbGroup.setZIndex(2);
            redOrbGroup.setZIndex(3);


            //DEFINE POSITIONS
            ySophie = HALFH-sophiePanelImg.getHeight()/2;
            yYellow = HALFH-yellowOrbPanelImg.getHeight()/2;
            yBlue = HALFH-blueOrbPanelImg.getHeight()/2;
            yRed = HALFH-redOrbPanelImg.getHeight()/2;

            sophieLife.setPosition(xSophie+W_BUTTON_SOPHIE,ySophie+H_BUTTON_LIFE);
            sophiePanelImg.setPosition(xSophie, ySophie);
            nextUp.setPosition(xSophie+460,ySophie+460);
            quartz.setPosition(xSophie+142,ySophie+172);
            obsidian.setPosition(xSophie+142,ySophie+310);
            sophieCoins.setPosition(xSophie+456,ySophie+172);

            yellowOrbPanelImg.setPosition(xYellow, yYellow);
            yellowOrbLife.setPosition(xYellow+W_BUTTONS,yYellow+H_BUTTON_LIFE);
            yellowOrbAtk.setPosition(xYellow+W_BUTTONS,yYellow+H_BUTTON_ATK);
            //yellowOrbSpAtk.setPosition(xYellow+W_BUTTONS,yYellow+H_BUTTON_SPATK);
            yellowLifeLbl.setPosition(yellowOrbLife.getX()+yellowOrbLife.getWidth()+30, yellowOrbLife.getY()+25);
            yellowAtkLbl.setPosition(yellowOrbAtk.getX()+yellowOrbAtk.getWidth()+30, yellowOrbAtk.getY()+19);
            yellowXPLbl.setPosition(xYellow+135,yYellow+395);

            blueOrbPanelImg.setPosition(xBlue, yBlue);
            blueOrbLife.setPosition(xBlue+W_BUTTONS,yBlue+H_BUTTON_LIFE);
            blueOrbAtk.setPosition(xBlue+W_BUTTONS,yBlue+H_BUTTON_ATK);
            //blueOrbSpAtk.setPosition(xBlue+W_BUTTONS,yBlue+H_BUTTON_SPATK);
            blueLifeLbl.setPosition(blueOrbLife.getX()+blueOrbLife.getWidth()+30, blueOrbLife.getY()+25);
            blueAtkLbl.setPosition(blueOrbAtk.getX()+blueOrbAtk.getWidth()+30, blueOrbAtk.getY()+19);
            blueXPLbl.setPosition(xBlue+95,yBlue+440);

            redOrbPanelImg.setPosition(xRed, yRed);
            redOrbLife.setPosition(xRed+W_BUTTONS,yRed+H_BUTTON_LIFE);
            redOrbAtk.setPosition(xRed+W_BUTTONS,yRed+H_BUTTON_ATK);
            //redOrbSpAtk.setPosition(xRed+W_BUTTONS,yRed+H_BUTTON_SPATK);
            redLifeLbl.setPosition(redOrbLife.getX()+redOrbLife.getWidth()+30, redOrbLife.getY()+25);
            redAtkLbl.setPosition(redOrbAtk.getX()+redOrbAtk.getWidth()+30, redOrbAtk.getY()+19);
            redXPLbl.setPosition(xRed+120,yRed+420);

            this.addActor(groupPanels);

            statsPrefs.flush();
        }

        class UpgradeButtons extends ClickListener{
            int[] cost;
            float[] upgradeArr;
            String prefStr;
            String stageStr;
            String upgradeStr;

            Label label;

            StatsState color;

            ImageButton button;

            public UpgradeButtons(StatsState color, int upgrade, ImageButton button){
                super();

                if (color==StatsState.YELLOW) {
                    upgradeStr = "Yellow";
                    if (upgrade==1) {
                        upgradeStr += "Life";
                        label = yellowLifeLbl;
                    }else if(upgrade==2){
                        upgradeStr += "Atk";
                        label = yellowAtkLbl;
                    }else{
                        upgradeStr += "SpAtk";
                        label = yellowSpAtkLbl;
                    }
                }else if (color==StatsState.BLUE) {
                    upgradeStr = "Blue";
                    if (upgrade==1) {
                        upgradeStr += "Life";
                        label = blueLifeLbl;
                    }else if(upgrade==2){
                        upgradeStr += "Atk";
                        label = blueAtkLbl;
                    }else{
                        upgradeStr += "SpAtk";
                        label = blueSpAtkLbl;
                    }
                }else if (color==StatsState.RED){
                    upgradeStr = "Red";
                    if (upgrade==1) {
                        upgradeStr += "Life";
                        label = redLifeLbl;
                    }else if(upgrade==2){
                        upgradeStr += "Atk";
                        label = redAtkLbl;
                    }else{
                        upgradeStr += "SpAtk";
                        label = redSpAtkLbl;
                    }
                }else{
                    upgradeStr = "SophieLife";
                    label = nextUp;
                }

                stageStr = upgradeStr + "Stg";

                if(color==StatsState.SOPHIE){
                    cost = LIFE_COST_SOPHIE_ARR;
                    upgradeArr = LIFE_ARR;
                }else if(upgrade==1){
                    cost = LIFE_COST_ARR;
                    upgradeArr = LIFE_ARR;
                }else if(upgrade==2){
                    cost = ATK_COST_ARR;
                    upgradeArr = ATK_ARR;
                }else{ //Checking
                    cost = ATK_COST_ARR; //Checking
                    upgradeArr = ATK_ARR;
                }

                if(color==StatsState.SOPHIE){
                    prefStr = "Coins";
                }else{
                    prefStr = "XP";
                }

                this.color = color;
                this.button = button;

            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                //HAY UPDATES DISPONIBLES
                if(statsPrefs.getInteger(stageStr)<cost.length-1){
                    if(statsPrefs.getInteger(prefStr)>=cost[statsPrefs.getInteger(stageStr)+1]){ //TIENES XP
                        //Actualizar preferencias
                        statsPrefs.putInteger(stageStr, statsPrefs.getInteger(stageStr)+1);
                        statsPrefs.putFloat(upgradeStr, upgradeArr[statsPrefs.getInteger(stageStr)]);
                        statsPrefs.flush();

                        //Actualizar Banners
                        if(statsPrefs.getInteger(stageStr)<cost.length-1){
                            label.setText(Integer.toString(cost[statsPrefs.getInteger(stageStr)+1]));
                        }else{
                            label.setText("MAX");
                        }

                        statsPrefs.putInteger(prefStr, statsPrefs.getInteger(prefStr)-cost[statsPrefs.getInteger(stageStr)]);
                        statsPrefs.flush();

                        if (color==StatsState.SOPHIE) {
                            sophieCoins.setText(Integer.toString(statsPrefs.getInteger(prefStr)));
                        }else{
                            yellowXPLbl.setText(Integer.toString(statsPrefs.getInteger(prefStr)));
                            blueXPLbl.setText(Integer.toString(statsPrefs.getInteger(prefStr)));
                            redXPLbl.setText(Integer.toString(statsPrefs.getInteger(prefStr)));
                        }

                        if(statsPrefs.getInteger(stageStr)>=cost.length-1){
                            button.setColor(1f, 1f, 1f, 0.5f);
                        }else{
                            if(statsPrefs.getInteger(prefStr)<cost[statsPrefs.getInteger(stageStr)+1])
                                button.setColor(1f, 1f, 1f, 0.5f);
                        }
                    }
                }

                statsPrefs.flush();
            }
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

        //Textures
        private Texture bottom; //Image
        private Texture map; //Image
        private Texture quitBton; //Button
        private Texture statsBton; //Button
        private Texture top; //Image
        private Texture backBton; //Button
        private Texture musicBton;
        private Texture fxBton;
        //CLUES
        private Texture newspaper; //Image
        private Texture newspaperDetail; //Image
        private Texture photo; //Image
        private Texture photoDetail; //Image
        private Texture note; //Image
        private Texture noteDetail; //Image
        private Texture bone; //Image
        private Texture boneDetail; //Image

        Preferences prefes = Gdx.app.getPreferences("My Preferences");

        Pause pauseStg;


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
            bottom = aManager.get("Interfaces/PAUSE/PAUSEBottomDisplay.png");
            map = aManager.get("Interfaces/PAUSE/PAUSEMapList.png");
            quitBton = aManager.get("Interfaces/PAUSE/PAUSEQuit.png");
            statsBton = aManager.get("Interfaces/PAUSE/PAUSEStats.png");
            top = aManager.get("Interfaces/PAUSE/PAUSETopDisplay.png");
            backBton = aManager.get("Interfaces/PAUSE/PAUSEback.png");

            newspaper = aManager.get("CLUES/Newspaper/CLUESNewspaper.png");
            newspaperDetail = aManager.get("CLUES/Newspaper/CLUESNewspaperDetail.png");
            photo = aManager.get("CLUES/Photo/CLUESPhoto.png");
            photoDetail = aManager.get("CLUES/Photo/CLUESPhotoDetail.png");
            note = aManager.get("CLUES/Note/CLUESNote.png");
            noteDetail = aManager.get("CLUES/Note/CLUESNoteDetail.png");
            bone = aManager.get("CLUES/Bone/CLUESBone.png");
            boneDetail = aManager.get("CLUES/Bone/CLUESBoneDetail.png");

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
            backgroundImg.setPosition(0, 0);
            this.addActor(backgroundImg);

            //top
            Image topImg = new Image(top);
            topImg.setPosition(0, 0);
            this.addActor(topImg);


            //bottom
            //1280x720
            Image bottomImg=new Image(bottom);
            bottomImg.setPosition(0, 0);
            this.addActor(bottomImg);


            //map
            Image mapImg = new Image(map);
            mapImg.setPosition(0, 220);
            this.addActor(mapImg);

            ////////////ITEMS

            //Newspaper
            

            TextureRegionDrawable newsBtonTrd = new TextureRegionDrawable(new TextureRegion(newspaper));
            ImageButton newspaperBtonImg = new ImageButton(newsBtonTrd);

            newspaperBtonImg.setPosition(145-newspaperBtonImg.getWidth()/2, 440-newspaperBtonImg.getHeight()/2);

            newspaperBtonImg.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clueStage = new ClueDetail(view, batch, app, pauseStg, newspaperDetail);
                    state = GameState.CLUE;
                }
            });

            //Phote
            TextureRegionDrawable photoBtonTrd = new TextureRegionDrawable(new TextureRegion(photo));
            ImageButton photoBtonImg = new ImageButton(photoBtonTrd);

            photoBtonImg.setPosition(410-photoBtonImg.getWidth()/2, 440-photoBtonImg.getHeight()/2);

            photoBtonImg.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clueStage = new ClueDetail(view, batch, app, pauseStg, photoDetail);
                    state = GameState.CLUE;
                }
            });

            //Note
            TextureRegionDrawable noteBtonTrd = new TextureRegionDrawable(new TextureRegion(note));
            ImageButton noteBtonImg = new ImageButton(noteBtonTrd);

            noteBtonImg.setPosition(145-noteBtonImg.getWidth()/2, 290-noteBtonImg.getHeight()/2);

            noteBtonImg.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clueStage = new ClueDetail(view, batch, app, pauseStg, noteDetail);
                    state = GameState.CLUE;
                }
            });

            //Bone
            TextureRegionDrawable boneBtonTrd = new TextureRegionDrawable(new TextureRegion(bone));
            ImageButton boneBtonImg = new ImageButton(boneBtonTrd);

            boneBtonImg.setPosition(410-boneBtonImg.getWidth()/2, 290-boneBtonImg.getHeight()/2);

            boneBtonImg.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clueStage = new ClueDetail(view, batch, app, pauseStg, boneDetail);
                    state = GameState.CLUE;
                }
            });


            switch(pref.getInteger("level")){
                case 5:
                    this.addActor(boneBtonImg);
                case 4:
                    this.addActor(noteBtonImg);
                case 3:
                    this.addActor(photoBtonImg);
                case 2:
                    this.addActor(newspaperBtonImg);
                default:
                    break;
            }


            //////////////////////

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
            pauseStg = this;
            textureInit();
            objectInit();
        }
    }


    public class ClueDetail extends Stage {
        private AssetManager aManager;
        private final App app;

        //Screen sizes
        public static final float WIDTH = 1280;
        public static final float HEIGHT = 720;
        public static final float HALFW = WIDTH/2;
        public static final float HALFH = HEIGHT/2;

        Pause pauseStg;
        ClueDetail me;

        // buttons
        private Texture backBton; //Button
        private Texture detTex;

        private void textureInit(){
            backBton = aManager.get("Interfaces/PAUSE/PAUSEback.png");
        }

        private void objectInit(){
            //background hace Image
            Image backgroundImg = new Image(background);
            backgroundImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
            this.addActor(backgroundImg);


            //detail
            Image detImg = new Image(detTex);
            detImg.setPosition(HALFW-detImg.getWidth()/2, HALFH-detImg.getHeight()/2);
            this.addActor(detImg);

            //back
            TextureRegionDrawable backBtonTrd = new TextureRegionDrawable(new TextureRegion(backBton));
            ImageButton backBtonImg = new ImageButton(backBtonTrd);

            backBtonImg.setPosition(HALFW+470, 0);
            this.addActor(backBtonImg);

            backBtonImg.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    state = GameState.PAUSED;
                    Gdx.input.setInputProcessor(pauseStage);
                    me.dispose();
                }
            });
        }

        public ClueDetail(Viewport view, SpriteBatch batch, App app, Pause pauseStg, Texture img) {
            super(view, batch);
            this.app = app;
            this.aManager = app.getAssetManager();
            this.pauseStg = pauseStg;
            this.detTex = img;
            this.me = this;
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

