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

class Dialogue {
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

    public void makeText(SpriteBatch batch, String msj, float offset){

        GlyphLayout glyph = new GlyphLayout();

        batch.draw(sheetTexture, HALFW-sheetTexture.getWidth()/2 + offset, 0);

        glyph.setText(font, msj, Color.BLACK, 710.0F, 30, true);
        font.draw(batch ,glyph,HALFW-sheetTexture.getWidth()/2+185 + offset, sheetTexture.getHeight()-115);
    }

    public void makeText(SpriteBatch batch, String msj, Sprite left, float offset){

        GlyphLayout glyph = new GlyphLayout();



        left.setPosition(0,0); //corregir offset
        left.draw(batch);
        batch.draw(sheetTexture, HALFW-sheetTexture.getWidth()/2 + offset, 0);

        glyph.setText(font, msj, Color.BLACK, 710.0F, 30, true);
        font.draw(batch ,glyph,HALFW-sheetTexture.getWidth()/2+185 + offset, sheetTexture.getHeight()-115);
    }

    public void makeText(SpriteBatch batch, String msj, Sprite left, Sprite right, boolean rightTalks, float offset){

        GlyphLayout glyph = new GlyphLayout();



        left.setPosition(0 ,0); // corregir offset
        right.setPosition(0 , WIDTH-right.getWidth()); // corregir offset

        if(rightTalks){
            left.setColor(1F, 1F, 1F, 0.6F);
        }else{
            right.setColor(1F, 1F, 1F, 0.6F);
        }
        right.draw(batch);
        left.draw(batch);

        batch.draw(sheetTexture, HALFW-sheetTexture.getWidth()/2 + offset, 0);

        glyph.setText(font, msj, Color.BLACK, 710.0F, 30, true);
        font.draw(batch ,glyph,HALFW-sheetTexture.getWidth()/2+185 + offset, sheetTexture.getHeight()-115);
    }

}
