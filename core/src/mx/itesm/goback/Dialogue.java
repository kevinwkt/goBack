package mx.itesm.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Dialogue {
    private BitmapFont font;
    //private AssetManager aManager; TODO ASSET MANAGER

    private static final float WIDTH = 1280;
    private static final float HALFW = WIDTH/2;


    private Texture sheetTexture;
    GlyphLayout namel = new GlyphLayout();
    GlyphLayout namer = new GlyphLayout();


    Dialogue(AssetManager aManager) {
        font = new BitmapFont(Gdx.files.internal("fira.fnt"));
        sheetTexture = aManager.get("CONSTANT/CONSTDialogueBox.png");
    }

    void makeText(GlyphLayout glyph, SpriteBatch batch, String msj,float cameraCenter){

        batch.draw(sheetTexture, cameraCenter-sheetTexture.getWidth()/2, 0);

        glyph.setText(font, msj, Color.BLACK, 710.0F, 30, true);
        font.draw(batch ,glyph, cameraCenter - sheetTexture.getWidth()/2 + 185, sheetTexture.getHeight()-115);
    }


    void makeText(GlyphLayout glyph, SpriteBatch batch, String msj, Sprite left, String leftName, float cameraCenter){
        left.setOrigin(0,0);
        left.setPosition(cameraCenter-HALFW,0);
        left.draw(batch);

        batch.draw(sheetTexture, cameraCenter-sheetTexture.getWidth()/2, 0);

        namel.setText(font, leftName, Color.BLACK, 710F, 30, true);
        glyph.setText(font, msj, Color.BLACK, 710.0F, 30, true);
        font.draw(batch ,namel, cameraCenter - sheetTexture.getWidth()/2 + 185, sheetTexture.getHeight()-30);
        font.draw(batch ,glyph, cameraCenter - sheetTexture.getWidth()/2 + 185, sheetTexture.getHeight()-115);
    }


    void makeText(GlyphLayout glyph, SpriteBatch batch, String msj, Sprite left, String leftName, Sprite right, String rightName, boolean rightTalks, float cameraCenter){

        left.setPosition(cameraCenter-HALFW ,0);
        right.setPosition(cameraCenter+HALFW-right.getWidth() , 0);

        if(rightTalks){
            left.setColor(1F, 1F, 1F, 0.6F);
            right.setColor(1F, 1F, 1F, 1.0F);
        }else{
            right.setColor(1F, 1F, 1F, 0.6F);
            left.setColor(1F, 1F, 1F, 1.0F);
        }

        right.draw(batch);
        left.draw(batch);

        batch.draw(sheetTexture, cameraCenter-sheetTexture.getWidth()/2, 0);

        glyph.setText(font, msj, Color.BLACK, 710.0F, 30, true);
        namel.setText(font, leftName, Color.BLACK, 710F, 30, true);
        namer.setText(font, rightName, Color.BLACK, 710F, 30, true);

        if(rightTalks)
            font.draw(batch ,namer, cameraCenter + 320, sheetTexture.getHeight()-30);
        else
            font.draw(batch ,namel, cameraCenter - sheetTexture.getWidth()/2 + 185, sheetTexture.getHeight()-30);

        font.draw(batch ,glyph, cameraCenter - sheetTexture.getWidth()/2 + 185, sheetTexture.getHeight()-115);
    }

}
