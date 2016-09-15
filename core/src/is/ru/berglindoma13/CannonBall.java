package is.ru.berglindoma13;

import com.badlogic.gdx.Gdx;

/**
 * Created by Berglind on 9/15/2016.
 */
public class CannonBall {
    Point position;
    //CircleGraphic c;

    public CannonBall(){
        position = new Point();
        position.x = 0;
        position.y = 0;
    }

    public void display(int colorLoc){
        Gdx.gl.glUniform4f(colorLoc, 0.5f, 0.5f, 0, 1);
        ModelMatrix.main.setModelMatrixTranslation(position.x, position.y,0);
        //ModelMatrix.main.setModelMatrixScale(0.5f,0.5f,1.0f);
        ModelMatrix.main.setShaderMatrix();
        CircleGraphic.drawSolidCircle();
}

    public void update(){
        position.x += 500;
        position.y += 500;
    }
}
