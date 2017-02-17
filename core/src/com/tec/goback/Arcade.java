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
public class Arcade implements Screen {

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
    private Texture plasticTree;
    private Texture plasticEarth;

    //SpriteBatch
    private SpriteBatch batch;

    //Stage
    private Stage arcadeStage;

    //Constructor recieves main App class (implements Game)
    public Arcade(App app) {
        this.app = app;
    }

    //Call other methods because FIS
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
        plasticTree = new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEDISPLAY.png");
        plasticEarth = new Texture("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTPause.png");
    }

    private void objectInit() { //MAYBE PROBABLY WORKING
        batch = new SpriteBatch();
        arcadeStage = new Stage(view, batch);

        //plasticTree hace Image
        Image plasticTreeImg = new Image(plasticTree);
        plasticTreeImg.setPosition(HALFW-plasticTreeImg.getWidth()/2, HALFH-plasticTreeImg.getHeight()/2);
        arcadeStage.addActor(plasticTreeImg);

        //plasticEarth
        TextureRegionDrawable plasticEarthTrd = new TextureRegionDrawable(new TextureRegion(plasticEarth));
        ImageButton plasticEarthImg = new ImageButton(plasticEarthTrd);

        plasticEarthImg.setPosition((WIDTH-plasticEarthImg.getWidth()/2)-92, 18);
        arcadeStage.addActor(plasticEarthImg);

        final Screen toRet = this;
        plasticEarthImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new Pause(app, toRet));
            }
        });

        //pass the Stage
        Gdx.input.setInputProcessor(arcadeStage);

        //let go of android device back key
        Gdx.input.setCatchBackKey(false);
    }



    @Override
    public void render(float delta) {
        cls();
        arcadeStage.draw();
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
