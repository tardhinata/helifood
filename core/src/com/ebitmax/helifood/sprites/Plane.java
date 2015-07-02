package com.ebitmax.helifood.sprites;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;   
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;       
import com.ebitmax.helifood.helper.Assets;
import com.ebitmax.helifood.helper.GameManager;

public class Plane {
	SpriteBatch batch;   
	private static final float JUMP_IMPULSE = 350;
	private static final float GRAVITY = -20;
	private static final float VELOCITY_X = 200;
	private static final float START_Y = 240;
	private static final float START_X = 50;
	public Animation animation;
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2(); 
	public Vector2 gravity = new Vector2();
	public float stateTime = 0;
	
	private ArrayList<Weapon> weapons;
	public static int currentWeapon;
	public int numSpecialPower;
	public boolean hasSpecialPower;
	public float health;
	
	public Plane()
	{
		batch = new SpriteBatch();     
		animation = new Animation(0.05f, new TextureRegion(Assets.plane1), new TextureRegion(Assets.plane2), new TextureRegion(Assets.plane3), new TextureRegion(Assets.plane2));
		animation.setPlayMode(PlayMode.LOOP);
		
		weapons = new ArrayList<Weapon>();
	    hasSpecialPower = false;
	    numSpecialPower = 0;
	    currentWeapon = Weapon.NORMAL;
	}

	public void reset(){
		position.set(START_X, START_Y);
		velocity.set(0, 0);
		gravity.set(0, GRAVITY);
	} 
	
	public void jump(){
		velocity.set(VELOCITY_X, JUMP_IMPULSE);
	}
	
	public static void setWeapon(int type){
		currentWeapon = type;
	}
	
	private int getCurrentWeapon(){
		return currentWeapon;
	}
	
	public ArrayList<Weapon> getWeapons(){
		return this.weapons;
	}
	
	public void fire(){ 
		Assets.playSound(Assets.shoot);
		int width = animation.getKeyFrames()[0].getRegionWidth();
		int height = animation.getKeyFrames()[0].getRegionHeight();
		Weapon w = new Weapon(position.x + (width), position.y + (height), getCurrentWeapon());
		weapons.add(w);
	}
	
	public void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime(); 
		stateTime += deltaTime;
		velocity.add(gravity);
		position.mulAdd(velocity, deltaTime); 
		
		for (Iterator<Weapon> iterator = weapons.iterator(); iterator.hasNext(); ) {
			Weapon w = iterator.next();
			if(w.position.x > (GameManager.camera.position.x + (GameManager.SCREEN_W/2)))
				iterator.remove();
			if (w != null) 
					w.update(deltaTime);  
		} 
	} 
	
	public void render()
	{
		float deltaTime = Gdx.graphics.getDeltaTime(); 
		batch.setProjectionMatrix(GameManager.camera.combined);
		batch.begin();
		batch.draw(animation.getKeyFrame(stateTime), position.x, position.y);
		batch.end(); 
		
		for(Weapon w: weapons) 
		{ 
			if (w != null) 
				w.dronaWeaponMedium(batch, deltaTime, w.position.x, w.position.y); 
		} 
	}
}
