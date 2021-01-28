package com.mygdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class MainMenuScreen implements Screen {

    final SpaceShooter game;
    OrthographicCamera camera;
    Texture imgSpace;
    Array<Stars> stars = new Array<>();

    public MainMenuScreen(SpaceShooter game) {

        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameScreen.SCR_WIDTH, GameScreen.SCR_HEIGHT);
        imgSpace = new Texture("sky.jpg");
        stars.add(new Stars(0));
        stars.add(new Stars(GameScreen.SCR_HEIGHT));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        for (int i = 0; i < stars.size; i++) stars.get(i).move();
        for (int i = 0; i < stars.size; i++)
            game.batch.draw(imgSpace, stars.get(i).x, stars.get(i).y, stars.get(i).width, stars.get(i).height);
        game.font.draw(game.batch, "TAP TO START!!!", GameScreen.SCR_WIDTH/2-45, GameScreen.SCR_HEIGHT/2-5);


        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
        }
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
    public void dispose() {
        imgSpace.dispose();
    }
}
