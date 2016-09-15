package is.ru.berglindoma13;

import com.badlogic.gdx.Gdx;
import com.sun.javafx.sg.prism.NGShape;

/**
 * Created by Berglind on 9/15/2016.
 */
public class CannonBall {
    Point position;
    Vector direction;

    public CannonBall(Cannon cannon){
        direction = new Vector();
        position = new Point();
        position.x = 0;
        position.y = 0;
        position.z = 0;
        direction = cannon.get_direction();
    }

    public void display(int colorLoc){
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.setModelMatrixTranslation(position.x,position.y,position.z);
        ModelMatrix.main.setModelMatrixScale(5,5,0);
        ModelMatrix.main.setShaderMatrix();
        Gdx.gl.glUniform4f(colorLoc, 0.5f, 0.5f, 0, 1);
        CircleGraphic.drawSolidCircle();
        ModelMatrix.main.popMatrix();
}

    public void update(){
        position.x += direction.x;
        position.y += direction.y;
    }
}
