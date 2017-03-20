package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by gerry on 3/19/17.
 */

public class Dialogue {
    private BitmapFont font;
    private AssetManager aManager;

    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;
    public static final float HALFW = WIDTH/2;
    public static final float HALFH = HEIGHT/2;

    private Texture sheetTexture;


    public Dialogue(AssetManager aManager) {
        this.aManager = aManager;
        font = new BitmapFont(Gdx.files.internal("fira.fnt"));
        sheetTexture = aManager.get("CONSTANT/CONSTDialogueBox.png");
    }

    public void makeText(SpriteBatch batch, String msj){

        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, msj);

        batch.draw(sheetTexture, 150, 0);
        font.draw(batch, glyph, 100, 50);
    }

    public void makeText(SpriteBatch batch, String msj, Sprite left){

        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, msj);

        batch.draw(sheetTexture, 150, 0);
        font.draw(batch, glyph, 100, 50);
    }

    public void makeText(SpriteBatch batch, String msj, Sprite left, Sprite right){

        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, msj);

        batch.draw(sheetTexture, 150, 0);
        font.draw(batch, glyph, 100, 50);
    }

}
