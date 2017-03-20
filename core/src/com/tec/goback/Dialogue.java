package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
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

        batch.draw(sheetTexture, HALFW-sheetTexture.getWidth()/2, 0);

        glyph.setText(font, msj, Color.BLACK, 710.0F, 30, true);
        font.draw(batch ,glyph,HALFW-sheetTexture.getWidth()/2+185, sheetTexture.getHeight()-115);
    }

    public void makeText(SpriteBatch batch, String msj, Sprite left){

        GlyphLayout glyph = new GlyphLayout();



        left.setPosition(0,0);
        left.draw(batch);
        batch.draw(sheetTexture, HALFW-sheetTexture.getWidth()/2, 0);

        glyph.setText(font, msj, Color.BLACK, 710.0F, 30, true);
        font.draw(batch ,glyph,HALFW-sheetTexture.getWidth()/2+185, sheetTexture.getHeight()-115);
    }

    public void makeText(SpriteBatch batch, String msj, Sprite left, Sprite right, boolean rightTalks){

        GlyphLayout glyph = new GlyphLayout();



        left.setPosition(0,0);
        right.setPosition(0, WIDTH-right.getWidth());

        if(rightTalks){
            left.setColor(0.5F, 0.5F, 0.5F, 0.6F);
        }else{
            right.setColor(0.5F, 0.5F, 0.5F, 1.6F);
        }
        right.draw(batch);
        left.draw(batch);

        batch.draw(sheetTexture, HALFW-sheetTexture.getWidth()/2, 0);

        glyph.setText(font, msj, Color.BLACK, 710.0F, 30, true);
        font.draw(batch ,glyph,HALFW-sheetTexture.getWidth()/2+185, sheetTexture.getHeight()-115);
    }

}
