package com.ebitmax.helifood.helper;

import java.util.Random; 
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameManager {
	public static float SCREEN_W = 800;
	public static float SCREEN_H = 480;
	public static GameState state; 
	public static OrthographicCamera camera;
	public static OrthographicCamera uiCamera;
	public static boolean enableSound = true;
	public static String dataDir = "data/";
	public static Random rand = new Random(); 
	
}
