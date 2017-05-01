package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created b1y pablo on 16/02/17.
 */

class SoundSettings implements Screen{

    private  App app;
    private AssetManager aManager;

    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;
    public static final float HALFW = WIDTH/2;
    public static final float HALFH = HEIGHT/2;

    // preferencias

    Preferences soundPreferences = Gdx.app.getPreferences("My Preferences");
    Preferences levelPreferences = Gdx.app.getPreferences("getLevel");

    private Viewport view;

    // textures
    private Texture background;
    private Texture backButton;
    private Texture musicButton ;
    private Texture fxButton;
    private Texture soundDecoration;
    private Texture resetButton;


    // camera
    private OrthographicCamera camera;

    private SpriteBatch batch;

    private Stage soundSettingsStage;

    // buttons
    private ImageButton musicImg;
    private TextureRegionDrawable musicPlay;
    private ImageButton fxImg;
    private TextureRegionDrawable fxPlay;

    //Music
    private Music bgMusic;  // Sonidos largos

    //Menu
    private MainMenu menu;
    private Skin skin;

    public SoundSettings(App app, MainMenu menu) {
        this.app = app;
        this.aManager = app.getAssetManager();
        this.menu = menu;
    }


    @Override
    public void show() {
        cameraInit();
        generalTextureInit();
        objectInit();
        musicInit();
    }

    private void musicInit() {
        bgMusic = aManager.get("MUSIC/GoBackMusicMainMenu.mp3");
        bgMusic.setLooping(true);
    }


    private void cameraInit() {
        camera = new OrthographicCamera(WIDTH,HEIGHT);
        camera.position.set(HALFW,HALFH,0);
        camera.update();
        view = new StretchViewport(WIDTH,HEIGHT);
    }

    private void generalTextureInit() {
        switch(levelPreferences.getInteger("level")){
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
        backButton = aManager.get("Interfaces/SOUND/SOUNDBack.png");
        soundDecoration = aManager.get("Interfaces/SOUND/SOUNDDecoration.png");
        resetButton = aManager.get("Interfaces/SOUND/SOUNDResetButton.png");
        if(soundPreferences.getBoolean("soundOn")){
            musicButton = aManager.get("Interfaces/SOUND/SOUNDMusicON.png");
        }else{
            musicButton = aManager.get("Interfaces/SOUND/SOUNDMusic.png");
        }
        if(soundPreferences.getBoolean("fxOn")){
            fxButton = aManager.get("Interfaces/SOUND/SOUNDSoundON.png");
        }else{
            fxButton = aManager.get("Interfaces/SOUND/SOUNDSound.png");
        }
    }

    private void changeSoundTexture() {
        musicImg.remove();
        if(soundPreferences.getBoolean("soundOn")){
            musicButton = aManager.get("Interfaces/SOUND/SOUNDMusicON.png");
        }else{
            musicButton = aManager.get("Interfaces/SOUND/SOUNDMusic.png");
        }
        musicPlay = new TextureRegionDrawable(new TextureRegion(musicButton));
        musicImg = new ImageButton(musicPlay);
        musicImg.setPosition(4*WIDTH/7, 8*HEIGHT/15);
        soundSettingsStage.addActor(musicImg);

        musicImg.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(soundPreferences.getBoolean("soundOn")){
                    soundPreferences.putBoolean("soundOn",false);

                }else{
                    soundPreferences.putBoolean("soundOn",true);
                }
                soundPreferences.flush();
                changeSoundTexture();
            }
        });
    }

    private void changeFxTexture() {
        fxImg.remove();
        if(soundPreferences.getBoolean("fxOn")){
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
                if(soundPreferences.getBoolean("fxOn")){
                    soundPreferences.putBoolean("fxOn",false);

                }else{
                    soundPreferences.putBoolean("fxOn",true);

                }
                soundPreferences.flush();
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
        soundDecorationImg.setPosition(HALFW, HEIGHT/2-soundDecorationImg.getHeight()/2);
        soundSettingsStage.addActor(soundDecorationImg);

        //reset
        TextureRegionDrawable resetBtnPlay = new TextureRegionDrawable(new TextureRegion(resetButton));
        ImageButton resetBtnImg = new ImageButton(resetBtnPlay);
        resetBtnImg.setPosition(HALFW/4, HALFH-resetBtnImg.getHeight()/2);
        soundSettingsStage.addActor(resetBtnImg);

        resetBtnImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Skin skin = new Skin(Gdx.files.internal("neutralizer/skin/neutralizer-ui.json"));
                Dialog resetDialog = new Dialog("Confirm Reset", skin) {
                    {
                        text("Do you really want to reset the game?\nYour whole progress will be lost.\nThe game will need to be restarted manually.");
                        button("Yes", "Yes");
                        button("Cancel", "No");
                    }

                    @Override
                    protected void result(Object object) {
                        if (object.equals("Yes")) {
                            resetPreferences();
                        }
                        this.remove();
                    }

                    private void resetPreferences() {
                        Preferences pref = Gdx.app.getPreferences("getLevel");
                        pref.putInteger("level", 0);
                        pref.flush();

                        Preferences stats=Gdx.app.getPreferences("STATS");
                        stats.putInteger("XP",0);
                        stats.putFloat("SophieLife",0);
                        stats.putFloat("YellowLife",0);
                        stats.putFloat("YellowAtk",0);
                        stats.putFloat("BlueLife",0);
                        stats.putFloat("BlueAtk",0);
                        stats.putFloat("RedLife",0);
                        stats.putFloat("RedAtk",0);
                        stats.flush();


                        Gdx.app.exit();
                    }
                };
                resetDialog.show(soundSettingsStage);
            }
        });


        //back

        TextureRegionDrawable backBtnPlay = new TextureRegionDrawable(new TextureRegion(backButton));
        ImageButton backBtnImg = new ImageButton(backBtnPlay);
        backBtnImg.setPosition(HALFW+470, 0);
        soundSettingsStage.addActor(backBtnImg);

        backBtnImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new Fade(app, LoaderState.MAINMENU, menu));
            }
        });


        musicPlay = new TextureRegionDrawable(new TextureRegion(musicButton));
        musicImg = new ImageButton(musicPlay );
        musicImg .setPosition(4*WIDTH/7, 8*HEIGHT/15);
        soundSettingsStage.addActor(musicImg);

        musicImg.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(soundPreferences.getBoolean("soundOn")){
                    soundPreferences.putBoolean("soundOn",false);

                }else{
                    soundPreferences.putBoolean("soundOn",true);
                }
                soundPreferences.flush();
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
                if(soundPreferences.getBoolean("fxOn")){
                    soundPreferences.putBoolean("fxOn",false);
                }else{
                    soundPreferences.putBoolean("fxOn",true);
                }
                soundPreferences.flush();
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
        if(soundPreferences.getBoolean("soundOn"))
            bgMusic.play();
        else
            bgMusic.stop();
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
        aManager.unload("INTRO/INTROBackground.png");
        aManager.unload("HARBOR/GoBackHARBOR0.png");
        aManager.unload("MOUNTAINS/GoBackMOUNTAINS0.png"); //Level2
        //aManager.unload(".png"); //Level3
        aManager.unload("Interfaces/SOUND/SOUNDMusicON.png");
        aManager.unload("Interfaces/SOUND/SOUNDMusic.png");
        aManager.unload("Interfaces/SOUND/SOUNDSoundON.png");
        aManager.unload("Interfaces/SOUND/SOUNDSound.png");
        aManager.unload("Interfaces/SOUND/SOUNDBack.png");
        aManager.unload("Interfaces/SOUND/SOUNDDecoration.png");
        aManager.unload("Interfaces/SOUND/SOUNDSoundResetButton.png");
    }
}
