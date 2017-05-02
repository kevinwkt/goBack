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
		checkStatsPref();
	}

	private void checkStatsPref() {
		Preferences stats=Gdx.app.getPreferences("STATS");
		Preferences pref=Gdx.app.getPreferences("getLevel");

		if(pref.getInteger("level")==0){
			pref.putBoolean("boss",false);
			pref.flush();
		}

		/*
		stats.putInteger("XP",0);
		stats.putFloat("SophieLife",0);
		stats.putFloat("YellowLife",0);
		stats.putFloat("YellowAtk",0);
		stats.putFloat("BlueLife",0);
		stats.putFloat("BlueAtk",0);
		stats.putFloat("RedLife",0);
		stats.putFloat("RedAtk",0);
		stats.flush();
		*/

		if(stats.getFloat("SophieLife")==0){
			stats.putFloat("SophieLife", 100f);
			stats.putInteger("SophieLifeStg", 0);
		}
		if(stats.getFloat("YellowLife")==0){
			stats.putFloat("YellowLife", 100f);
			stats.putInteger("YellowLifeStg", 0);
		}
		if(stats.getFloat("YellowAtk")==0){
			stats.putFloat("YellowAtk", 20f);
			stats.putInteger("YellowAtkStg", 0);
		}
		if(stats.getFloat("BlueLife")==0){
			stats.putFloat("BlueLife", 100f);
			stats.putInteger("BlueLifeStg", 0);
		}
		if(stats.getFloat("BlueAtk")==0){
			stats.putFloat("BlueAtk", 20f);
			stats.putInteger("BlueAtkStg", 0);
		}
		if(stats.getFloat("RedLife")==0){
			stats.putFloat("RedLife", 100f);
			stats.putInteger("RedLifeStg", 0);
		}
		if(stats.getFloat("RedAtk")==0){
			stats.putFloat("RedAtk", 20f);
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