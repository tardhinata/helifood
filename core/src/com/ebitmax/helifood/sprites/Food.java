package com.ebitmax.helifood.sprites;
 
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ebitmax.helifood.helper.GameManager;
 
public class Food {
	SpriteBatch batch;  
	public Vector2 position = new Vector2();
	public TextureRegion image;
	public boolean counted;
	public boolean collide;
	
	public Food(float x, float y, TextureRegion image) {
		batch = new SpriteBatch(); 
		this.position.x = x;
		this.position.y = y;
		this.image = image; 
	}
	
	public void render()
	{
		if(!collide){
			batch.setProjectionMatrix(GameManager.camera.combined);
			batch.begin();    
			batch.draw(this.image, this.position.x, this.position.y);  
			batch.end(); 
		}
	}
}