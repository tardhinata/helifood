package com.ebitmax.helifood.screens;
 
import com.badlogic.gdx.Screen; 
import com.badlogic.gdx.Gdx;  
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion; 
import com.badlogic.gdx.math.Rectangle; 
import com.badlogic.gdx.utils.Array;  
import com.ebitmax.helifood.HeliFood;
import com.ebitmax.helifood.helper.Assets;
import com.ebitmax.helifood.helper.GameState;
import com.ebitmax.helifood.helper.GameManager; 
import com.ebitmax.helifood.sprites.Food;
import com.ebitmax.helifood.sprites.Plane;
import com.ebitmax.helifood.sprites.Rock;
import com.ebitmax.helifood.sprites.Pawn;
import com.ebitmax.helifood.sprites.Weapon;

public class SGame implements Screen {

	final HeliFood myGame;
	 
	SpriteBatch batch; 
	float groundOffsetX = 0;  
	
	Plane plane; 
	int score = 0;
	
	Rectangle rectPlane = new Rectangle();
	Rectangle rectRock = new Rectangle();
	Rectangle rectFood = new Rectangle();
	Rectangle rectPawn = new Rectangle();
	Rectangle rectWeapon = new Rectangle();
	
	TextureRegion rock;
	Array<Rock> rocks = new Array<Rock>();
	
	TextureRegion food;
	Array<Food> foods = new Array<Food>();
	
	Array<Pawn> pawns = new Array<Pawn>();
	 
	public SGame(final HeliFood game) {
		myGame = game;
 
		batch = new SpriteBatch();  
		plane = new Plane(); 
		Assets.playMusic(Assets.music); 
		resetWorld();
    }
	
	private void resetWorld() { 
		score = 0;
		groundOffsetX = 0; 
		GameManager.camera.position.x = 400;
		
		//Reset Plane 
		plane.reset();
		
		//Reset Rocks
		rocks.clear();
		for(int i = 0; i < 3; i++) {  
			float x = 700 + i * 300;
			float y= 0;
			rock = Assets.rock;
			rocks.add(new Rock(x, y, rock));
		}
		
		//Reset Foods
		foods.clear();
		for(int i = 0; i < 10; i++) { 
			float x = 600 + i * 200;
			float y= GameManager.rand.nextInt(440-150) + 150;
			food = Assets.getFruit(GameManager.rand.nextInt(10)); 
			foods.add(new Food(x, y, food));
		}
		
		//Reset Pawns
		pawns.clear();
		for(int i = 0; i < 5; i++) { 
			float x = 900 + i * 400;
			float y= GameManager.rand.nextInt(440-200) + 200; 
			pawns.add(new Pawn(x,y));
		}
	}
	
	private void updateWorld() { 
		
		if(Gdx.input.justTouched()) { 
			if(GameManager.state == GameState.Start) {
				GameManager.state = GameState.Running;
			}
			if(GameManager.state == GameState.Running) {
				if(Gdx.input.getX() < 200)
					plane.fire(); 
				plane.jump();
			}
		}
		
		if(GameManager.state == GameState.Running) {
			
			plane.update();
			
			GameManager.camera.position.x = plane.position.x + 350;		
			if(GameManager.camera.position.x - groundOffsetX > Assets.ground.getRegionWidth() + 400) {
				groundOffsetX += Assets.ground.getRegionWidth();
			} 
			
			rectPlane.set(plane.position.x + 20, plane.position.y, plane.animation.getKeyFrames()[0].getRegionWidth() - 20, plane.animation.getKeyFrames()[0].getRegionHeight());
			
			//Colission pawns and weapons
			for(Pawn p: pawns) {
				p.update();
				if(GameManager.camera.position.x - p.position.x > 400) { 
					p.reset();
				}
				rectPawn = p.getBounds(); 
				for(Weapon w: plane.getWeapons()) 
				{ 
					rectWeapon = w.getBounds(); 
					if(rectPawn.overlaps(rectWeapon)) {
						Assets.playSound(Assets.killed);
						w.visible=false;
						p.visible = false;
						p.state=Pawn.DESTROYING; 
					}
				}
			}
			
			//Colission plane and rock 
			for(Rock r: rocks) {
				if(GameManager.camera.position.x - r.position.x > 400 + r.image.getRegionWidth()) { 
					r.position.x += 5 * 200;
					r.position.y = 0;
					r.image = rock;
					r.counted = false;
				}
				rectRock.set(r.position.x + (r.image.getRegionWidth() - 30) / 2 + 20, r.position.y, 20, r.image.getRegionHeight() - 10);
				if(rectPlane.overlaps(rectRock)) {
					if(GameManager.state != GameState.GameOver) Assets.playSound(Assets.explode); 
					GameManager.state = GameState.GameOver;
					plane.velocity.x = 0;				
				}
				if(r.position.x < plane.position.x && !r.counted) {
					//score++;
					r.counted = true;
				}
			}
			
			//Colission plane and food
			for(Food f: foods) {
				if(GameManager.camera.position.x - f.position.x > 400 + f.image.getRegionWidth()) { 
					f.position.x += 5 * 300;
					f.position.y = GameManager.rand.nextInt(440-150) + 150; 
					f.image = Assets.getFruit(GameManager.rand.nextInt(10)); 
					f.collide = false;
					f.counted = false;
				}
				rectFood.set(f.position.x + (f.image.getRegionWidth() - 30) / 2 + 20, f.position.y, 20, f.image.getRegionHeight() - 10);
				if(rectPlane.overlaps(rectFood) && !f.counted) {
					score++;
					f.collide = true;
					f.counted = true;		
					Assets.playSound(Assets.catchFood);
				} 
			}
			
			//Colission with ground
			if(plane.position.y < Assets.ground.getRegionHeight() - 20) {
				if(GameManager.state != GameState.GameOver) Assets.playSound(Assets.explode);
				GameManager.state = GameState.GameOver;
				plane.velocity.x = 0;
			}
		}
	}
	
	private void drawWorld() { 
		
		//Render Background
		GameManager.camera.update();
		batch.setProjectionMatrix(GameManager.camera.combined);
		batch.begin();  
		batch.draw(Assets.background, GameManager.camera.position.x - Assets.background.getWidth() / 2, 0); 
		batch.draw(Assets.ground, groundOffsetX, 0);
		batch.draw(Assets.ground, groundOffsetX + Assets.ground.getRegionWidth(), 0);  
		batch.end(); 
		 
		//Render Game Components
		for(Rock rock: rocks) {
			rock.render();
		}
		for(Food food: foods) {
			food.render();
		}
		for(Pawn pawn: pawns) {
			pawn.render();
		}
		plane.render();
		
		//Render Game Info
		batch.setProjectionMatrix(GameManager.uiCamera.combined);
		batch.begin();		 
		if(GameManager.state == GameState.Start) {
			batch.draw(Assets.ready, Gdx.graphics.getWidth() / 2 - Assets.ready.getRegionWidth() / 2, Gdx.graphics.getHeight() / 2 - Assets.ready.getRegionHeight() / 2);
		}
		if(GameManager.state == GameState.GameOver) {
			batch.draw(Assets.gameOver, Gdx.graphics.getWidth() / 2 - Assets.gameOver.getRegionWidth() / 2, Gdx.graphics.getHeight() / 2 - Assets.gameOver.getRegionHeight() / 2);
		}
		if(GameManager.state == GameState.GameOver || GameManager.state == GameState.Running) {
			Assets.font.draw(batch, "" + score, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 60);
		} 
		batch.end(); 
	} 

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub  
		updateWorld();
		drawWorld();	
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
		Assets.stopMusic(Assets.music); 
	} 
}
