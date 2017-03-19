package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
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
 * Created b1y pablo on 16/02/17.
 */

public class SoundSettings implements Screen{

    private final App app;

    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;
    public static final float HALFW = WIDTH/2;
    public static final float HALFH = HEIGHT/2;

    // preferencias

    Preferences prefes = Gdx.app.getPreferences("My Preferences");


    private Viewport view;

    // textures
    private Texture background;
    private Texture backButton;
    private Texture musicButton ;
    private Texture fxButton;
    private Texture soundDecoration;


    // camera
    private OrthographicCamera camera;

    private SpriteBatch batch;

    private Stage soundSettingsStage;

    // buttons
    private ImageButton musicImg;
    private TextureRegionDrawable musicPlay;
    private ImageButton fxImg;
    private TextureRegionDrawable fxPlay;


    public SoundSettings(App app) {
        this.app = app;
    }


    @Override
    public void show() {
        cameraInit();
        generalTextureInit();
        objectInit();
    }


    private void cameraInit() {
        camera = new OrthographicCamera(WIDTH,HEIGHT);
        camera.position.set(HALFW,HALFH,0);
        camera.update();
        view = new StretchViewport(WIDTH,HEIGHT);
    }

    private void generalTextureInit() {
        background = new Texture("HARBOR/GoBackHARBOR0.png");
        backButton = new Texture("Interfaces/SOUND/SOUNDBack.png");
        soundDecoration = new Texture("Interfaces/SOUND/SOUNDDecoration.png");
        if(prefes.getBoolean("soundOn")){
            musicButton = new Texture("Interfaces/SOUND/SOUNDMusicON.png");
        }else{
            musicButton = new Texture("Interfaces/SOUND/SOUNDMusic.png");
        }
        if(prefes.getBoolean("fxOn")){
            fxButton = new Texture("Interfaces/SOUND/SOUNDSoundON.png");
        }else{
            fxButton = new Texture("Interfaces/SOUND/SOUNDSound.png");
        }

    }

    private void changeSoundTexture() {
        musicImg.remove();
        if(prefes.getBoolean("soundOn")){
            musicButton = new Texture("Interfaces/SOUND/SOUNDMusicON.png");
        }else{
            musicButton = new Texture("Interfaces/SOUND/SOUNDMusic.png");
        }
        musicPlay = new TextureRegionDrawable(new TextureRegion(musicButton));
        musicImg = new ImageButton(musicPlay );
        musicImg.setPosition(4*WIDTH/7, 8*HEIGHT/15);
        soundSettingsStage.addActor(musicImg);

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
            fxButton = new Texture("Interfaces/SOUND/SOUNDSoundON.png");
        }else{
            fxButton = new Texture("Interfaces/SOUND/SOUNDSound.png");
        }

        fxPlay = new TextureRegionDrawable(new TextureRegion(fxButton));
        fxImg = new ImageButton(fxPlay);
        fxImg.setPosition(4*WIDTH/7, 4*HEIGHT/15);
        soundSettingsStage.addActor(fxImg);

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
        soundSettingsStage = new Stage(view,batch);

        // background
        Image backImg = new Image(background);
        backImg.setPosition(WIDTH/2-backImg.getWidth()/2, HEIGHT/2-backImg.getHeight()/2);
        soundSettingsStage.addActor(backImg);

        // buttons
        TextureRegionDrawable soundDecorationPlay = new TextureRegionDrawable(new TextureRegion(soundDecoration));
        ImageButton soundDecorationImg = new ImageButton(soundDecorationPlay);
        soundDecorationImg.setPosition(WIDTH/20, HEIGHT/2-soundDecorationImg.getHeight()/2);
        soundSettingsStage.addActor(soundDecorationImg);

        TextureRegionDrawable backBtnPlay = new TextureRegionDrawable(new TextureRegion(backButton));
        ImageButton backBtnImg = new ImageButton(backBtnPlay);
        backBtnImg.setPosition(10, 10);
        soundSettingsStage.addActor(backBtnImg);

        backBtnImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new MainMenu(app));
            }
        });


        musicPlay = new TextureRegionDrawable(new TextureRegion(musicButton));
        musicImg = new ImageButton(musicPlay );
        musicImg .setPosition(4*WIDTH/7, 8*HEIGHT/15);
        soundSettingsStage.addActor(musicImg);

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



        fxPlay = new TextureRegionDrawable(new TextureRegion(fxButton));
        fxImg = new ImageButton(fxPlay);
        fxImg.setPosition(4*WIDTH/7, 4*HEIGHT/15);
        soundSettingsStage.addActor(fxImg);

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

        Gdx.input.setInputProcessor(soundSettingsStage);
        Gdx.input.setCatchBackKey(true);

    }



    @Override
    public void render(float delta) {
        cls();
        soundSettingsStage.draw();
    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            app.setScreen(new MainMenu(app));
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

    }
}
