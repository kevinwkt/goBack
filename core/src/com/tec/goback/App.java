package com.tec.goback;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;


public class App extends Game {

	private final AssetManager aManager;

	public App(){
		this.aManager = new AssetManager();
	}

	@Override
	public void create () {
		setScreen(new SplashScreen(this));
		aManager.load("HARBOR/GoBackHARBOR0.png", Texture.class);
		aManager.load("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTPause.png", Texture.class);
		aManager.load("Interfaces/PAUSE/PAUSEBottomDisplay.png", Texture.class);
		aManager.load("Interfaces/PAUSE/PAUSEMapList.png", Texture.class);
		aManager.load("Interfaces/PAUSE/PAUSEStats.png", Texture.class);
		//Stats
		aManager.load("Interfaces/STATS/STATSback.png", Texture.class);
		aManager.load("Interfaces/STATS/STATSBlueOrb.png", Texture.class);
		aManager.load("Interfaces/STATS/STATSBlueOrbArrow.png", Texture.class);
		aManager.load("Interfaces/STATS/STATSRedOrb.png", Texture.class);
		aManager.load("Interfaces/STATS/STATSRedOrbArrow.png", Texture.class);
		aManager.load("Interfaces/STATS/STATSYellowOrb.png", Texture.class);
		aManager.load("Interfaces/STATS/STATSYellowOrbArrow.png", Texture.class);
		aManager.load("Interfaces/STATS/STATSSophie.png", Texture.class);
		aManager.load("Interfaces/STATS/STATSSophieArrow.png", Texture.class);
		///
		aManager.load("Interfaces/PAUSE/PAUSEQuit.png", Texture.class);
		aManager.load("Interfaces/PAUSE/PAUSETopDisplay.png", Texture.class);
		aManager.load("Interfaces/PAUSE/PAUSEback.png", Texture.class);
		aManager.load("Interfaces/SOUND/SOUNDMusicON.png", Texture.class);
		aManager.load("Interfaces/SOUND/SOUNDMusic.png", Texture.class);
		aManager.load("Interfaces/SOUND/SOUNDSoundON.png", Texture.class);
		aManager.load("Interfaces/SOUND/SOUNDSound.png", Texture.class);
		aManager.load("CONSTANT/CONSTDialogueBox.png", Texture.class);
		aManager.load("Interfaces/GAMEPLAY/ARCADE/ARCADESophie.png", Texture.class);

		checkStatsPref();
	}

	private void checkStatsPref() {
		Preferences stats=Gdx.app.getPreferences("STATS");

		stats.putInteger("XP",0);
		if(stats.getFloat("SophieLife")==0){
			stats.putFloat("SophieLife", 100f);
			stats.putInteger("SophieLifeStg", 0);
		}
		if(stats.getFloat("YellowLife")==0){
			stats.putFloat("YellowLife", 100f);
			stats.putInteger("YellowLifeStg", 0);
		}
		if(stats.getFloat("YellowAtk")==0){
			stats.putFloat("YellowAtk", 25f);
			stats.putInteger("YellowLifeStg", 0);
		}
		if(stats.getFloat("BlueLife")==0){
			stats.putFloat("BlueLife", 100f);
			stats.putInteger("BlueLifeStg", 0);
		}
		if(stats.getFloat("BlueAtk")==0){
			stats.putFloat("BlueAtk", 25f);
			stats.putInteger("BlueAtkStg", 0);
		}
		if(stats.getFloat("RedLife")==0){
			stats.putFloat("RedLife", 100f);
			stats.putInteger("RedLifeStg", 0);
		}
		if(stats.getFloat("RedAtk")==0){
			stats.putFloat("RedAtk", 25f);
			stats.putInteger("RedAtkStg", 0);
		}

		stats.flush();
	}

	public AssetManager getAssetManager() {
        return aManager;
    }

	@Override
    public void dispose() {
        super.dispose();
        aManager.clear();
    }
}
