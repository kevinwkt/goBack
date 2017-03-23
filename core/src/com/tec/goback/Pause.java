/*package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by kevin on 2/16/2017.


public class Pause implements Screen{

    //Main app class
    private final App app;
    private AssetManager aManager;


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



    //SpriteBatch
    private SpriteBatch batch;

    //Stage
    private Stage pauseMenu;

    // buttons
    private ImageButton musicImg;
    private TextureRegionDrawable musicPlay;
    private ImageButton fxImg;
    private TextureRegionDrawable fxPlay;


    //Constructor recieves main App class (implements Game)
    public Pause(App app, LoaderState prev) {
        this.app = app;
        this.aManager = app.getAssetManager();
    }

    //Call other methods because readability
    @Override
    public void show() {
        cameraInit();
        textureInit();
        objectInit();
    }

    private void cameraInit() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(HALFW, HALFH, 0);
        camera.update();
        view = new StretchViewport(WIDTH, HEIGHT);
    }

    private void textureInit() {
        background = aManager.get("HARBOR/GoBackHARBOR0.png");
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
        pauseMenu.addActor(musicImg);

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
        pauseMenu.addActor(fxImg);

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

    private void objectInit() {
        batch = new SpriteBatch();
        pauseMenu = new Stage(view, batch);

        //background hace Image
        Image backgroundImg = new Image(background);
        backgroundImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
        pauseMenu.addActor(backgroundImg);

        //top
        Image topImg = new Image(top);
        topImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
        pauseMenu.addActor(topImg);


        //bottom
        //1280x720
        Image bottomImg=new Image(bottom);
        bottomImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
        pauseMenu.addActor(bottomImg);


        //map
        Image mapImg = new Image(map);
        mapImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2+220);
        pauseMenu.addActor(mapImg);


        //quitBton
        TextureRegionDrawable quitBtonTrd = new TextureRegionDrawable(new TextureRegion(quitBton));
        ImageButton quitBtonImg = new ImageButton(quitBtonTrd);

        quitBtonImg.setPosition(HALFW-quitBtonImg.getWidth()/2-470, HEIGHT-quitBtonImg.getHeight()/2-640);
        pauseMenu.addActor(quitBtonImg);

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
        pauseMenu.addActor(backBtonImg);

        backBtonImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new Fade(app, LoaderState.PAUSE));
            }
        });

        //music

        musicPlay = new TextureRegionDrawable(new TextureRegion(musicBton));
        musicImg = new ImageButton(musicPlay );
        musicImg .setPosition(4*WIDTH/7, 8*HEIGHT/15);
        pauseMenu.addActor(musicImg);

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
        pauseMenu.addActor(fxImg);

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

        //pass the Stage
        Gdx.input.setInputProcessor(pauseMenu);



    }



    @Override
    public void render(float delta) {
        cls();
        pauseMenu.draw();
    }
    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
*/