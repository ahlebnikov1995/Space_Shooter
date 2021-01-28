package com.mygdx.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpaceShooter extends Game {

    SpriteBatch batch;
    BitmapFont font;
    static Preferences prefs;
    static int highScore;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
        prefs = Gdx.app.getPreferences("My preferences");
        highScore = prefs.getInteger("highscore", 1);
    }

    @Override
    public void render() {
        super.render();

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();

    }
}
