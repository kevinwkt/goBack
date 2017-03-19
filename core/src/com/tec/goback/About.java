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
 * Created by sergiohernandezjr on 16/02/17.
 */

public class About implements Screen {
    //Main app class
    private final App app;


    //Screen sizes
    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;
    public static final float HALFW = WIDTH/2;
    public static final float HALFH = HEIGHT/2;

    //Camera
    private OrthographicCamera camera;

    //Main app class
    private Viewport view;

    //Textures
    public Texture background;//Background that changes with progress
    private Texture castOverlay;
    private Texture backButton;//Image that holds creators photos and back button

    private SpriteBatch batch;

    //Stage
    private Stage aboutScreenStage;

    public About (App app) {
        this.app = app;
    }

    @Override
    public void show() {
        cameraInit();
        textureInit();
        objectInit();
    }

    private void textureInit() {
        background = new Texture("HARBOR/GoBackHARBOR0.png");
        castOverlay = new Texture("Interfaces/ABOUT/ABOUTCast.png");
        backButton = new Texture("Interfaces/ABOUT/ABOUTBack.png");
    }

    private void objectInit() {
        batch = new SpriteBatch();
        aboutScreenStage = new Stage(view, batch);

        //Background
        Image bgImg = new Image(background);
        bgImg.setPosition(HALFW-bgImg.getWidth()/2, HALFH-bgImg.getHeight()/2);
        aboutScreenStage.addActor(bgImg);

        //Cast  overlay image
        Image castImg = new Image(castOverlay);
        castImg.setPosition(HALFW-castImg.getWidth()/2, HALFH-castImg.getHeight()/2);
        aboutScreenStage.addActor(castImg);


        //Back button
        TextureRegionDrawable backBtnTrd = new TextureRegionDrawable(new TextureRegion(backButton));
        ImageButton backImgBtn = new ImageButton(backBtnTrd);

        backImgBtn.setPosition(10, 10);
        aboutScreenStage.addActor(backImgBtn);

        backImgBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new MainMenu(app));
            }
        });

        //pass the Stage
        Gdx.input.setInputProcessor(aboutScreenStage);

        //let go of android device back key
        Gdx.input.setCatchBackKey(false);

    }

    private void cameraInit() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(HALFW, HALFH, 0);
        camera.update();

        view = new StretchViewport(WIDTH, HEIGHT);
    }

    @Override
    public void render(float delta) {
        cls();
        aboutScreenStage.draw();
    }
    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        view.update(width,height);
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
