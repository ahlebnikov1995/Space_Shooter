package com.mygdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
	SpaceShooter game;

	public static final int SCR_WIDTH = 540, SCR_HEIGHT = 960;
	long lastEnemySpawnTime;
	long enemySpawnInterval = 1000;
	long lastShootTime;
	long shootInterval = 500;


	SpriteBatch batch;
	BitmapFont font;
	OrthographicCamera camera;
	Vector3 touch;

	Texture imgShip;
	Texture imgBadShip;
	Texture imgSpace;
	Texture imgLaser;

	Array<Stars> stars = new Array<>();
	Ship ship;
	Array<ShipEnemy> shipEnemies = new Array<>();
	Array<Shoot> shoots = new Array<>();
	int score;
	boolean isgame = true;


	public GameScreen (SpaceShooter gam) {
		this.game = gam;

		batch = new SpriteBatch();
		font = new BitmapFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
		touch = new Vector3();

		loadResources();
		stars.add(new Stars(0));
		stars.add(new Stars(SCR_HEIGHT));
		ship = new Ship();


	}

	private void actions(){
		for (int i = 0; i < stars.size; i++) stars.get(i).move();

		if (Gdx.input.isTouched()){
			touch.set(Gdx.input.getX(), Gdx.input.getY(),0);
			camera.unproject(touch);
			ship.x += (touch.x - (ship.x + ship.width / 2)) / 5;
		}


		if(ship.isAlive && TimeUtils.millis() - lastShootTime > shootInterval) spawnShoot();
		for (int i = 0; i < shoots.size; i++) {
			shoots.get(i).move();

			for (int j = 0; j < shipEnemies.size ; j++) {
				if(shoots.get(i).overlaps(shipEnemies.get(j))){
					shoots.get(i).isAlive = false;
					shipEnemies.get(j).isAlive = false;
					score = (int) (score - shipEnemies.get(j).getVy());
					if(score > SpaceShooter.highScore){
						SpaceShooter.highScore = score;
						SpaceShooter.prefs.putInteger("highscore", SpaceShooter.highScore);
						SpaceShooter.prefs.flush();
					}
				}

			}
			if (!shoots.get(i).isAlive) shoots.removeIndex(i);
		}


		if (TimeUtils.millis() - lastEnemySpawnTime > enemySpawnInterval) spawnEnemy();

		for (int i = 0; i < shipEnemies.size ; i++) {
			shipEnemies.get(i).move();
			if(shipEnemies.get(i).y < 0 && ship.isAlive) gameOver();

			if (!shipEnemies.get(i).isAlive) shipEnemies.removeIndex(i);
		}

	}

	void spawnShoot(){
		shoots.add(new Shoot(ship));
		lastShootTime = TimeUtils.millis();
	}

	void spawnEnemy(){
		shipEnemies.add(new ShipEnemy());
		lastEnemySpawnTime = TimeUtils.millis();
	}

	private void gameOver() {
		ship.isAlive = false;
		isgame = false;

	}

	private void loadResources(){
	imgShip = new Texture("my_spaceship.png");
	imgBadShip = new Texture("spaceship_bad.png");
	imgLaser = new Texture("laser_final.png");
	imgSpace = new Texture("sky.jpg");
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		actions();

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int i = 0; i < stars.size; i++)
			batch.draw(imgSpace, stars.get(i).x, stars.get(i).y, stars.get(i).width, stars.get(i).height);
		font.draw(batch, "SCORE: " + score, SCR_WIDTH-150, SCR_HEIGHT-30);

		font.draw(batch, "HIGH SCORE: " + SpaceShooter.highScore, SCR_WIDTH-150, SCR_HEIGHT-10);

		if (ship.isAlive) batch.draw(imgShip, ship.x, ship.y, ship.width, ship.height);

		for (int i = 0; i < shoots.size; i++) {
			batch.draw(imgLaser, shoots.get(i).x, shoots.get(i).y, shoots.get(i).width, shoots.get(i).height);
		}

		for (int i = 0; i < shipEnemies.size; i++) {
			batch.draw(imgBadShip, shipEnemies.get(i).x, shipEnemies.get(i).y, shipEnemies.get(i).width, shipEnemies.get(i).height);
		}

		if(isgame == false){
			font.draw(batch, "DEFEAT! TAP TO RESTART", SCR_WIDTH/2-85, SCR_HEIGHT/2-5);
		}

		if(isgame == false && Gdx.input.isTouched()){
			for (int i = 0; i < shipEnemies.size; i++) {
				shipEnemies.get(i).isAlive = false;
			}
			isgame = true;
			ship.isAlive = true;
			score = 0;
		}


		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {
		batch.dispose();
		imgShip.dispose();
		imgBadShip.dispose();
		imgSpace.dispose();
		imgLaser.dispose();
		
	}
}
