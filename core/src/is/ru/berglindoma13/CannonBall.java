package is.ru.berglindoma13;

import com.badlogic.gdx.Gdx;
import com.sun.javafx.sg.prism.NGShape;

/**
 * Created by Berglind on 9/15/2016.
 */
public class CannonBall {
    Point position;
    Vector direction;
    int i = 0;

    public CannonBall(Cannon cannon){
        direction = new Vector();
        position = new Point();
        position.x = cannon.get_cannonPoint().x;
        position.y = cannon.get_cannonPoint().y;
        position.z = cannon.get_cannonPoint().z;
        direction.x = 0.0f;
        direction.y = 1.0f;
    }

    public void display(int colorLoc){
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.setModelMatrixTranslation(position.x,position.y,position.z);
        ModelMatrix.main.setModelMatrixScale(5,5,0);
        ModelMatrix.main.setShaderMatrix();
        Gdx.gl.glUniform4f(colorLoc, 0.1f, 0.5f, 0, 1);
        CircleGraphic.drawSolidCircle();
        ModelMatrix.main.popMatrix();
}

    public void update(float hit){
        float deltaTime = Gdx.graphics.getDeltaTime();
        position.x += direction.x * deltaTime * 150.0f;
        position.y += direction.y * deltaTime * 150.0f;

        collision(hit);

    }

    public void setDirection(float x){
        direction.x = x;
        direction.y = 1;
    }

    public void collision(float hit){
        float deltaTime = Gdx.graphics.getDeltaTime();

        for(int i = 0 ; i < CannonGame.getNumberOfObstacles(); i++){
            Point middle = new Point();
            middle.x = CannonGame.getObstaclesX()[i];
            middle.y = CannonGame.getObstacleY()[i];

            //bottom left corner
            Point p1 = new Point();
            p1.x = middle.x - 50;
            p1.y = middle.y - 20;

            //top left corner
            Point p2 = new Point();
            p2.x = middle.x - 50;
            p2.y = middle.y + 20;

            //bottom right corner
            Point p3 = new Point();
            p3.x = middle.x + 50;
            p3.y = middle.y - 20;

            //top right corner
            Point p4 = new Point();
            p4.x = middle.x + 50;
            p4.y = middle.y + 20;

            System.out.println("middle.x: " + middle.x + " middle.y: " + middle.y);
            System.out.println("p1.x: " + p1.x + " p1.y: " + p1.y);
            System.out.println("p2.x: " + p2.x + " p2.y: " + p2.y);
            System.out.println("p3.x: " + p3.x + " p3.y: " + p3.y);
            System.out.println("p4.x: " + p4.x + " p4.y: " + p4.y);
        }

        Point leftside = new Point();
        leftside.x = 0;
        leftside.y = 768;

        Vector v = new Vector();
        v.x = 0;
        v.y = -768;

        //float thit1 = thit(v,leftside);

    }

    public void getReflection(Vector v){
        Vector reflection = new Vector();

        Vector normal = new Vector();
        normal.x = -v.y;
        normal.y = v.x;

        float calc = 2*(((direction.x * normal.x) + (direction.y * normal.y))/((normal.x*normal.x)+(normal.y*normal.y)));

        Vector temp = new Vector();
        temp.x = normal.x*calc;
        temp.y = normal.y*calc;

        reflection.x = direction.x - temp.x;
        reflection.y = direction.y - temp.y;

        direction.x = reflection.x;
        direction.y = reflection.y;
        //i = 0;
    }

    private float thit(Point a, Point b){
        Vector v = new Vector();
        v.x = a.x - b.x;
        v.y = a.y - b.y;

        Vector normal;
        normal = v.getnormal();

        Vector b_a = new Vector();
        b_a.x = (b.x - position.x);
        b_a.y = (b.y - position.y);

        float thit = ((normal.x * b_a.x) + (normal.y * b_a.y)) / ((normal.x * direction.x) + (normal.y * direction.y));

        return thit;
    }



}
