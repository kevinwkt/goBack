package com.tec.goback;

import com.badlogic.gdx.Gdx;
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
 * Created by gerry on 2/18/17.
 */
public class MainMenu implements Screen {

    //Main app class
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

    private Texture aboutBtn; //Button
    private Texture arcadeBtn; //Button
    private Texture sonidoBtn; //Button
    private Texture storyBtn; //Button
    private Texture title; //Button




    //SpriteBatch
    private SpriteBatch batch;

    //Stage
    private Stage mainMenuStage;


    //Constructor recieves main App class (implements Game)
    public MainMenu(App app) {
        this.app = app;
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

        background = new Texture("HARBOR/GoBackHARBOR0.png");
        aboutBtn = new Texture("Interfaces/MENU/ABOUT.png"); 
        arcadeBtn = new Texture("Interfaces/MENU/ARCADE.png");
        sonidoBtn = new Texture("Interfaces/MENU/SONIDO.png");
        storyBtn = new Texture("Interfaces/MENU/STORY.png");
        title = new Texture("Interfaces/MENU/TITLE.png");
    }

    private void objectInit() { //MAYBE PROBABLY (NOT) WORKING
        
        batch = new SpriteBatch();
        mainMenuStage = new Stage(view, batch);
        
        //background hace Image
        Image backgroundImg = new Image(background);
        backgroundImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
        mainMenuStage.addActor(backgroundImg);
        

        //aboutBtn hace ImageButton
        TextureRegionDrawable aboutBtnTrd = new TextureRegionDrawable(new TextureRegion(aboutBtn));
        ImageButton aboutBtnImg = new ImageButton(aboutBtnTrd);

        aboutBtnImg.setPosition(160, 10);
        mainMenuStage.addActor(aboutBtnImg);

        aboutBtnImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new About(app));
            }
        });


        //arcadeBtn
        TextureRegionDrawable arcadeBtnTrd = new TextureRegionDrawable(new TextureRegion(arcadeBtn));
        ImageButton arcadeBtnImg = new ImageButton(arcadeBtnTrd);

        arcadeBtnImg.setPosition((HALFW-arcadeBtnImg.getWidth()/2)+190, HALFH-arcadeBtnImg.getHeight()/2-100);
        mainMenuStage.addActor(arcadeBtnImg);

        arcadeBtnImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //app.setScreen(new About(app));
            }
        });


        //sonidoBtn
        TextureRegionDrawable sonidoBtnTrd = new TextureRegionDrawable(new TextureRegion(sonidoBtn));
        ImageButton sonidoBtnImg = new ImageButton(sonidoBtnTrd);

        sonidoBtnImg.setPosition(10, 10);
        mainMenuStage.addActor(sonidoBtnImg);

        sonidoBtnImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new SoundSettings(app));
            }
        });


        // //storyBtn
        // TextureRegionDrawable storyBtnTrd = new TextureRegionDrawable(new TextureRegion(storyBtn));
        // ImageButton storyBtnImg = new ImageButton(storyBtnTrd);

        // storyBtnImg.setPosition(HALFW-storyBtnImg.getWidth()/2, 3*HEIGHT/4-storyBtnImg.getHeight()/2);
        // mainMenuStage.addActor(storyBtnImg);

        // storyBtnImg.addListener(new ClickListener(){
        //     @Override
        //     public void clicked(InputEvent event, float x, float y) {
        //         //app.setScreen(new About(app));
        //     }
        // });


        //title
        Image titleImg = new Image(title);
        titleImg.setPosition(2, (HALFH-titleImg.getHeight()/2)+60);
        mainMenuStage.addActor(titleImg);

        //pass the Stage
        Gdx.input.setInputProcessor(mainMenuStage);



    }



    @Override
    public void render(float delta) {
        cls();
        mainMenuStage.draw();
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
