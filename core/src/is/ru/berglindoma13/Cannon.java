package is.ru.berglindoma13;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by Berglind on 9/15/2016.
 */
public class Cannon {
    Point position;
    Vector direction;
    private float angle;

    public Cannon(){
        position = new Point();
        position.x = 512;
        position.y = 50;
        position.z = 0;
        direction = new Vector();
        angle = 0.0f;
        direction.x = 0.0f;
        direction.y = 0.0f;
    }

    public Vector get_direction(){
        return direction;
    }

    public void display(int colorLoc){
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.setModelMatrixTranslation(position.x,position.y,position.z);
        ModelMatrix.main.setModelMatrixRotation(angle);
        ModelMatrix.main.setShaderMatrix();
        Gdx.gl.glUniform4f(colorLoc, 0.3f, 0.2f, 0, 1);
        RectangleGraphic.drawSolidSquare();
        ModelMatrix.main.popMatrix();
    }

    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (angle <= 45.0f) {
                angle += 5.0f;
                direction.x = (float)-Math.sin(angle * Math.PI / 180.0f) * Gdx.graphics.getDeltaTime() * 5.0f;
                direction.y = (float)Math.cos(angle * Math.PI / 180.0f) * Gdx.graphics.getDeltaTime() * 5.0f;
                System.out.println("direction.x" + direction.x + "direction.y" + direction.y);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if(angle >= -45.0f){
                angle -= 5.0f;
                direction.x = (float)-Math.sin(angle * Math.PI / 180.0f) * Gdx.graphics.getDeltaTime() * 1.0f;
                direction.y = (float)Math.cos(angle * Math.PI / 180.0f) * Gdx.graphics.getDeltaTime() * 1.0f;
            }
        }
    }
}
