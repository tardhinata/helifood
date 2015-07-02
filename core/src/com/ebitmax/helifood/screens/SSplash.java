package com.ebitmax.helifood.screens;
 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen; 
import com.badlogic.gdx.graphics.GL20; 
import com.badlogic.gdx.graphics.g2d.SpriteBatch; 
import com.ebitmax.helifood.HeliFood; 
import com.ebitmax.helifood.helper.GameManager;
import com.ebitmax.helifood.helper.Assets;;

public class SSplash implements Screen {
	
	final HeliFood myGame;
	SpriteBatch batch; 
	 
	public SSplash(final HeliFood game) {
		myGame = game; 
		batch = new SpriteBatch();  
    }
	
	@Override
	public void render(float delta) { 
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(Assets.splash, GameManager.uiCamera.position.x - Assets.splash.getWidth() / 2, GameManager.uiCamera.position.y - Assets.splash.getHeight() / 2);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
