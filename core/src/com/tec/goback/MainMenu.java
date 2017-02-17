package com.tec.goback;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by gerry on 2/16/17.
 */
public class MainMenu implements Screen {

    //Main app class
    private final App app;

    //Screen sizes
    public static final float WIDTH = 1200;
    public static final float HEIGHT = 800;
    public static final float HALFW = 600;
    public static final float HALFH = 400;

    //Camera
    private OrthographicCamera camera;
    
    
    //Viewport
    private Viewport view;

    //Textures
    private Texture background; //Background

    private Texture aboutBtn; //Buttin
    private Texture arcadeBtn; //Buttin
    private Texture sonidoBtn; //Buttin
    private Texture storyBtn; //Buttin
    private Texture title; //Buttin




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

        background = new Texture("Interfaces/HARBOR/GoBackHARBOR0.png");
        aboutBtn = new Texture("Interfaces/MENU/ABOUT.png"); 
        arcadeBtn = new Texture("Interfaces/MENU/ARCADE.png");
        sonidoBtn = new Texture("Interfaces/MENU/SONIDO");
        storyBtn = new Texture("Interfaces/MENU/STORY");
        title = new Texture("Interfaces/MENU/TITLE");
    }

    private void objectInit() { //NOT WORKING
        batch = new SpriteBatch();
        mainMenuStage = new Stage(view, batch);
        
        //background hace Image
        Image backgroundImg = new Image(background);
        menuScene.addActor(backgroundImg);
        

        //aboutBtn hace ImageButton
        TextureRegionDrawable aboutBtnTrd = new TextureRegionDrawable(new TextureRegion(aboutBtn));
        ImageButton imgPlayBtn = new ImageButton(aboutBtnTrd);

        imgPlayBtn.setPosition(WIDTH/2-imgPlayBtn.getWidth()/2, 3*HEIGHT/4-imgPlayBtn.getHeight()/2);
        menuScene.addActor(imgPlayBtn);

        imgPlayBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Gdx.app.log("clicked", "LOL"); imprime pura mamada
                menuLOL.setScreen(new About(menuLOL));
            }
        });

       


        //arcadeBtn
        TextureRegionDrawable trdPlayBtn = new TextureRegionDrawable(new TextureRegion(arcadeBtn));
        ImageButton imgPlayBtn = new ImageButton(trdPlayBtn);
        imgPlayBtn.setPosition(WIDTH/2-imgPlayBtn.getWidth()/2, 3*HEIGHT/4-imgPlayBtn.getHeight()/2);
        menuScene.addActor(imgPlayBtn);

        imgPlayBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Gdx.app.log("clicked", "LOL"); imprime pura mamada
                menuLOL.setScreen(new About(menuLOL));
            }
        });

       


        //sonidoBtn
        TextureRegionDrawable trdPlayBtn = new TextureRegionDrawable(new TextureRegion(sonidoBtn));
        ImageButton imgPlayBtn = new ImageButton(trdPlayBtn);
        imgPlayBtn.setPosition(WIDTH/2-imgPlayBtn.getWidth()/2, 3*HEIGHT/4-imgPlayBtn.getHeight()/2);
        menuScene.addActor(imgPlayBtn);

        imgPlayBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Gdx.app.log("clicked", "LOL"); imprime pura mamada
                menuLOL.setScreen(new About(menuLOL));
            }
        });

    


        //storyBtn
        TextureRegionDrawable trdPlayBtn = new TextureRegionDrawable(new TextureRegion(storyBtn));
        ImageButton imgPlayBtn = new ImageButton(trdPlayBtn);
        imgPlayBtn.setPosition(WIDTH/2-imgPlayBtn.getWidth()/2, 3*HEIGHT/4-imgPlayBtn.getHeight()/2);
        menuScene.addActor(imgPlayBtn);

        imgPlayBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Gdx.app.log("clicked", "LOL"); imprime pura mamada
                menuLOL.setScreen(new About(menuLOL));
            }
        });

       


        //title
        Image titleImg = new Image(title);
        titleImg.setPosition();
        menuScene.addActor(titleImg);

        //Recibe el Stage
        Gdx.input.setInputProcessor(mainMenuStage);



    }



    @Override
    public void render(float delta) {

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
