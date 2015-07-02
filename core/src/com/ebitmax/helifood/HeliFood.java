package com.ebitmax.helifood;

import com.badlogic.gdx.Game; 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.ebitmax.helifood.helper.Assets;
import com.ebitmax.helifood.helper.GameManager;
import com.ebitmax.helifood.helper.GameState;
import com.ebitmax.helifood.screens.SGame; 
import com.ebitmax.helifood.screens.SSplash;


public class HeliFood extends Game { 
	public SGame screenGame;
	
    @Override
    public void create() { 
    	GameManager.camera = new OrthographicCamera();
    	GameManager.camera.setToOrtho(false, GameManager.SCREEN_W, GameManager.SCREEN_H);
    	GameManager.uiCamera = new OrthographicCamera();
    	GameManager.uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    	GameManager.uiCamera.update(); 
    	GameManager.enableSound = true;
		GameManager.state = GameState.Loading;
		
		Assets.loadAll("en");
		
        this.setScreen(new SSplash(this));
    }

    @Override
    public void render() {
        super.render(); //important!
        
		if(Gdx.input.justTouched()) {
			if(GameManager.state == GameState.Loading || GameManager.state == GameState.GameOver) {
				GameManager.state = GameState.Start; 
				
				if(screenGame!=null)
					screenGame.dispose(); 
				screenGame = new SGame(this); 
				
				this.setScreen(screenGame);
			}
		}  
    }

    @Override
    public void dispose() { 
    	
    }

}