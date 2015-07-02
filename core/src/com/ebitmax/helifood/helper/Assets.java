package com.ebitmax.helifood.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;  
import com.badlogic.gdx.audio.Music;  
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;  

public class Assets {
	
	public static Texture background;
	public static TextureRegion ground;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion rock;
	public static Music music;
	public static Sound explode;
	public static Sound catchFood;
	public static Sound shoot; 
	public static Sound killed,killed1;
	
	public static BitmapFont font;
	public static Texture splash; 
	public static Texture plane1;
	public static Texture plane2;
	public static Texture plane3;
	public static TextureAtlas foodAtlas; 
	public static TextureAtlas enemyAtlas; 
	public static TextureAtlas textAtlas; 
	public static String locale;
	
	public static TextureRegion pawns1,pawns2;
	public static Animation pawn1Flying,pawn2Flying;
	
	public static TextureRegion dragon;
	public static Animation dragonAnimation;
	
	public static void loadAll(String localSetting) {   
		locale=localSetting;
		
		loadFonts(); 
		loadAtlas();
		loadTextures();
		loadSounds();
		loadAnimations();
	}
	
	private static void loadFonts(){
		font = new BitmapFont(Gdx.files.internal(GameManager.dataDir + "arial.fnt"));
	}
	
	private static void loadAtlas(){
		foodAtlas = new TextureAtlas(Gdx.files.internal(GameManager.dataDir + "food.atlas"));
		enemyAtlas = new TextureAtlas(Gdx.files.internal(GameManager.dataDir + "enemy.atlas"));
		if (locale.equals("de")) {
			textAtlas  = new TextureAtlas(Gdx.files.internal(GameManager.dataDir + "text_images_de.atlas"));
		}
		else {
			textAtlas  = new TextureAtlas(Gdx.files.internal(GameManager.dataDir + "text_images.atlas"));
		}
	}
	
	private static void loadTextures(){
		splash = new Texture(GameManager.dataDir + "markas_splash.png");	
		background = new Texture(GameManager.dataDir + "background.png");	
		ground = new TextureRegion(new Texture(GameManager.dataDir + "ground.png"));   
		ready = new TextureRegion(new Texture(GameManager.dataDir + "ready.png"));
		gameOver = new TextureRegion(new Texture(GameManager.dataDir + "gameover.png"));
		plane1 = new Texture(GameManager.dataDir + "heli1.png");
		plane2 = new Texture(GameManager.dataDir + "heli2.png");
		plane3 = new Texture(GameManager.dataDir + "heli3.png");
		rock = new TextureRegion(new Texture(GameManager.dataDir + "rock.png"));
		pawns1 = enemyAtlas.findRegion("pawns1");
		pawns2 = enemyAtlas.findRegion("pawns2");
		dragon = enemyAtlas.findRegion("dragon");
	}
	
	private static void loadSounds() {  
		music = Gdx.audio.newMusic(Gdx.files.internal(GameManager.dataDir + "backsound.mp3"));
		explode = Gdx.audio.newSound(Gdx.files.internal(GameManager.dataDir + "explode.wav"));
		catchFood = Gdx.audio.newSound(Gdx.files.internal(GameManager.dataDir + "catch.mp3"));
		shoot = Gdx.audio.newSound(Gdx.files.internal(GameManager.dataDir + "shoot.ogg")); 
		killed = Gdx.audio.newSound(Gdx.files.internal(GameManager.dataDir + "killed.mp3"));
		killed1 = Gdx.audio.newSound(Gdx.files.internal(GameManager.dataDir + "killed1.mp3"));
	}  
	
	private static void loadAnimations(){
		//Pawn Animation
		TextureRegion[][] pawnsRegions = Assets.pawns1.split(288/3, 384/4);
		pawn1Flying  = new Animation(0.15f, pawnsRegions[0][0], pawnsRegions[0][1], pawnsRegions[0][2]);
		pawn1Flying.setPlayMode(Animation.PlayMode.LOOP);
		
		TextureRegion[][] pawnsRegions2 = Assets.pawns2.split(512/4, 112);
		pawn2Flying  = new Animation(0.15f, pawnsRegions2[0][0], pawnsRegions2[0][1], pawnsRegions2[0][2], pawnsRegions2[0][3]);
		pawn2Flying.setPlayMode(Animation.PlayMode.LOOP);
		
		//Dragon Animation
		TextureRegion[][] dragonRegions = Assets.dragon.split(100,100);
		dragonAnimation = new Animation(0.15f, dragonRegions[0][0], dragonRegions[0][1], dragonRegions[0][2], dragonRegions[0][3], dragonRegions[0][4]);
		dragonAnimation.setPlayMode(Animation.PlayMode.LOOP);
	}
	
	public static TextureRegion getFruit(int fruitType) {
		return foodAtlas.findRegion(FoodType.fruitNames[fruitType]);
	}
	
	public static TextureRegion getFruitBig(int fruitType) {
		String name = FoodType.fruitNames[fruitType] + "2";
		return foodAtlas.findRegion(name);
	}	
	
	public static void playSound (Sound sound) {
		if (GameManager.enableSound) 
			sound.play(1);
	}
	
	public static void playMusic (Music music) {
		if (GameManager.enableSound) {
			music.setLooping(true);
			music.setVolume(0.3f);
			music.play();
		} 
	}
	
	public static void stopMusic (Music music) {
		music.stop();
		music.dispose();
	}
}
