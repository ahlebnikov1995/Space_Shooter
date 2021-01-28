package com.mygdx.spaceshooter;

import com.badlogic.gdx.math.MathUtils;

public class SpaceObject {
    float x, y;
    int width, height;
    boolean isAlive = true;
    float vx,vy;

    void move(){
        x += vx;
        y += vy;

        if (x < 0 - width || x > GameScreen.SCR_WIDTH || y < 0 - width || y > GameScreen.SCR_HEIGHT)
            isAlive = false;
    }

    boolean overlaps (SpaceObject o){
        return (x > o.x && x < o.x + o.width || o.x > x && o.x < x + width) &&
                (y > o.y && y < o.y + o.width || o.y > y && o.y < y + width);
    }

}


class Shoot extends SpaceObject{
    Shoot(SpaceObject ship){
        width = 20;
        height = 80;
        x = ship.x + ship.width/2.5f;
        y = ship.y + ship.height;
        vy = 10;

    }
}


class ShipEnemy extends SpaceObject{
    ShipEnemy(){
        width = 100;
        height = 100;
        x = MathUtils.random(0, GameScreen.SCR_WIDTH - width);
        y = GameScreen.SCR_HEIGHT;
        vy = MathUtils.random(-5,-2);
    }
    public float getVy() {
        return vy;
    }
}



class Ship extends SpaceObject{
    Ship(){
        width = 100;
        height = 100;
        x = GameScreen.SCR_WIDTH / 2f - width / 2;
        y = 30;
    }
}


class Stars extends SpaceObject{
    Stars (float y){
        width = GameScreen.SCR_WIDTH;
        height = GameScreen.SCR_HEIGHT;
        vy = -1;
        this.y = y;
    }

    @Override
    void move() {
        super.move();
        if (y <= -height) y = height;
    }
}
