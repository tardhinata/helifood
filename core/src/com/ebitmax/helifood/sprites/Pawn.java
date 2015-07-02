package com.ebitmax.helifood.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle; 
import com.badlogic.gdx.math.Vector2;
import com.ebitmax.helifood.helper.Assets; 
import com.ebitmax.helifood.helper.GameManager;

public class Pawn {
	SpriteBatch batch; 
	public boolean visible; 
	public static float width;
	public static float height;
	
	public static final int NORMAL = 0;
	public static final int DESTROYING = 1;
	public int state;
	
	public static final int BLUE = 0;
	public static final int ORANGE = 1;
	public static final int RED = 2;
	public int type;
    
    public float stateTime;  
    public float MAX_VELOCITY = 0.5f;
    
	public final Vector2 position;
    public final Vector2 velocity;
    public final Vector2 accel;
    
    public ParticleEffect pawnsDeadEffect;
    
	public Pawn(float x, float y) {
		batch = new SpriteBatch();  
        position = new Vector2(x,y);
        velocity = new Vector2();
        accel = new Vector2();
        
		visible = true;
		stateTime = 0;
		state = NORMAL;
		type = getRandomType();
		
		pawnsDeadEffect = new ParticleEffect();
		pawnsDeadEffect.load(Gdx.files.internal("effect/fireballblast.p"), Gdx.files.internal("effect"));
		
	    float pScale = 0.5f; 
	    float scaling = pawnsDeadEffect.getEmitters().get(0).getScale().getHighMax();
	    pawnsDeadEffect.getEmitters().get(0).getScale().setHigh(scaling * pScale);

	    scaling = pawnsDeadEffect.getEmitters().get(0).getScale().getLowMax();
	    pawnsDeadEffect.getEmitters().get(0).getScale().setLow(scaling * pScale);

	    scaling = pawnsDeadEffect.getEmitters().get(0).getVelocity().getHighMax();
	    pawnsDeadEffect.getEmitters().get(0).getVelocity().setHigh(scaling * pScale);

	    scaling = pawnsDeadEffect.getEmitters().get(0).getVelocity().getLowMax();
	    pawnsDeadEffect.getEmitters().get(0).getVelocity().setLow(scaling * pScale);
	     
	}
	  
	public void reset(){
		position.x += 10 * 200;
		position.y = GameManager.rand.nextInt(440-200) + 200; ; 
		visible = true;
		stateTime = 0;
		state = NORMAL;
		type = getRandomType(); 
	}
	
	public void update() {  
		float deltaTime = Gdx.graphics.getDeltaTime(); 
		stateTime+= deltaTime;
		position.x -= MAX_VELOCITY*stateTime; 
	    position.add(velocity); 
    }
	
	public void render()
	{
		float deltaTime = Gdx.graphics.getDeltaTime(); 
		TextureRegion frame = null;
		batch.setProjectionMatrix(GameManager.camera.combined);  
		batch.begin();  
	
		switch(state)
		{
			case NORMAL:
				if(type == Pawn.BLUE)
					frame = Assets.pawn1Flying.getKeyFrame(stateTime); 
				if(type == Pawn.ORANGE)
					frame = Assets.pawn2Flying.getKeyFrame(stateTime);
				if(type == Pawn.RED)
					frame = Assets.dragonAnimation.getKeyFrame(stateTime);
				width = frame.getRegionWidth();
				height = frame.getRegionHeight();
				break;
			
			case DESTROYING:  
				pawnsDeadEffect.setPosition(position.x, position.y); 
				pawnsDeadEffect.start();
				pawnsDeadEffect.draw(batch, deltaTime); 
				break;
		}
		
		if(visible){
			if(type!=Pawn.RED)
				batch.draw(frame, position.x, position.y, width, height);
			else
				batch.draw(frame, position.x, position.y, -Pawn.width, Pawn.height);
		}		 
			
		batch.end();
	}
	
	public Rectangle getBounds(){
    	return new Rectangle(position.x, position.y, width, height);
    }
	
	public static int getRandomType(){
		return GameManager.rand.nextInt(3-0) + 0; 
	}
}
