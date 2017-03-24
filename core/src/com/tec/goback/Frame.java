package com.tec.goback;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by sergiohernandezjr on 16/02/17.
 * Con ayuda de DON PABLO
 */

public abstract class Frame implements Screen {
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

    //Gamestate
    protected GameState state = GameState.PLAYING;

    //AssetMng
    protected AssetManager aManager;

    // pause button
    protected Sprite pauseSprite;

    //Music
    protected Music bgMusic;

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

        pauseButton = aManager.get("Interfaces/GAMEPLAY/CONSTANT/GoBackCONSTPause.png");


        //pass the Stage
        Gdx.input.setInputProcessor(frameStage);

        //let go of android device back key
        Gdx.input.setCatchBackKey(false);
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


            //quitBton
            TextureRegionDrawable quitBtonTrd = new TextureRegionDrawable(new TextureRegion(quitBton));
            ImageButton quitBtonImg = new ImageButton(quitBtonTrd);

            quitBtonImg.setPosition(HALFW-quitBtonImg.getWidth()/2-470, HEIGHT-quitBtonImg.getHeight()/2-640);
            this.addActor(quitBtonImg);

            quitBtonImg.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    app.setScreen(new Fade(app, LoaderState.MAINMENU));
                }
            });


            //back
            TextureRegionDrawable backBtonTrd = new TextureRegionDrawable(new TextureRegion(backBton));
            ImageButton backBtonImg = new ImageButton(backBtonTrd);

            backBtonImg.setPosition(HALFW-backBtonImg.getWidth()/2+500, HEIGHT-backBtonImg.getHeight()/2-640);
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
                        bgMusic.stop();

                    }else{
                        prefes.putBoolean("soundOn",true);
                        bgMusic.play();
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


}

