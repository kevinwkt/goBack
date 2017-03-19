package com.tec.goback;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by sergiohernandezjr on 18/03/17.
 */
public class Level1 extends Frame {
    // Mario
    private Sophie sophie;
    private Texture sophieTexture;

    // Música
    private Music bgMusic;
    private Sound fx;

    //BG
    Texture background;


    public Level1(App app) {
        super(app);
    }




    //@Override
    private void backgroundCharactersInit() {
        sophieTexture = new Texture("Squirts/Sophie/SophieWalk.png");
        sophie = new Sophie(sophieTexture, 200,200);

        batch = new SpriteBatch();
        frameStage = new Stage(view, batch);

        //Background
        /*background = new Texture("");
        Image bgImg = new Image(background);
        bgImg.setPosition(HALFW-bgImg.getWidth()/2, HALFH-bgImg.getHeight()/2);
        aboutScreenStage.addActor(bgImg);

        //Cast  overlay image
        Image castImg = new Image(castOverlay);
        castImg.setPosition(HALFW-castImg.getWidth()/2, HALFH-castImg.getHeight()/2);
        aboutScreenStage.addActor(castImg);*/
    }
}
