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
        direction.y = 0.0f;
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
        position.x += direction.x * deltaTime;
        //* 150.0f;
        position.y += direction.y * deltaTime ;
                //* 150.0f;

        collision(hit);

    }

    public void setDirection(float x, float y){
        direction.x = x;
        direction.y = y;
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

            Vector v = new Vector();

            if(direction.x >= 0 && direction.y >= 0){

                //bottom
                float thit2 = thit(p1,p3);
                if (thit2 < deltaTime && thit2 > 0){
                    v.x = p1.x - p3.x;
                    v.y = p1.y - p3.x;
                    getReflection(v);
                }

                //left
                float thit3 = thit(p1,p2);
                if(thit3 < deltaTime && thit3 > 0){
                    v.x = p1.x - p2.x;
                    v.y = p1.y - p2.x;
                    getReflection(v);
                }
            }

            else if(direction.x <= 0 && direction.y >= 0){
                //bottom
                float thit2 = thit(p1,p3);
                if (thit2 < deltaTime && thit2 > 0){
                    v.x = p1.x - p3.x;
                    v.y = p1.y - p3.x;
                    getReflection(v);
                }

                //right
                float thit4 = thit(p3,p4);
                if(thit4 < deltaTime && thit4 > 0){
                    v.x = p3.x - p4.x;
                    v.y = p3.y - p4.x;
                    getReflection(v);
                }
            }

            else if(direction.x >= 0 && direction.y <= 0){
                //left
                float thit3 = thit(p1,p2);
                if(thit3 < deltaTime && thit3 > 0){
                    v.x = p1.x - p2.x;
                    v.y = p1.y - p2.x;
                    getReflection(v);
                }

                //top
                float thit1 = thit(p2,p4);
                if(thit1 < deltaTime && thit1 > 0){
                    v.x = p2.x - p4.x;
                    v.y = p2.y - p4.x;
                    getReflection(v);
                }
            }

            else if(direction.x <= 0 && direction.y <= 0){
                //top
                float thit1 = thit(p2,p4);
                if(thit1 < deltaTime && thit1 > 0){
                    v.x = p2.x - p4.x;
                    v.y = p2.y - p4.x;
                    getReflection(v);
                }

                //right
                float thit4 = thit(p3,p4);
                if(thit4 < deltaTime && thit4 > 0){
                    System.out.println(thit4);
                    v.x = p3.x - p4.x;
                    v.y = p3.y - p4.x;
                    getReflection(v);
                }
            }
        }

        //Checking wall hits
        Point lefttop = new Point();
        lefttop.x = 0;
        lefttop.y = 768;

        Point righttop = new Point();
        righttop.x = 1024;
        righttop.y = 768;

        Point leftbottom = new Point();
        leftbottom.x = 0;
        leftbottom.y = 0;

        Point rightbottom = new Point();
        rightbottom.x = 1024;
        rightbottom.y = 0;


        Vector v = new Vector();
        //right side wall
        if(direction.x >= 0) {
            float thitright = thit(righttop, rightbottom);
            if (thitright < deltaTime) {

                v.x = rightbottom.x - righttop.x;
                v.y = rightbottom.y - righttop.y;
                getReflection(v);
            }
        }

        else if(direction.x < 0) {
            float thitleft = thit(lefttop, leftbottom);

            if (thitleft < deltaTime && thitleft > 0) {
                v.x = leftbottom.x - lefttop.x;
                v.y = leftbottom.y - lefttop.y;

                getReflection(v);
            }
        }
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
    }

    private float thit(Point a, Point b){
        Vector v = new Vector();
        v.x = a.x - b.x;
        v.y = a.y - b.y;

        Vector normal;
        normal = v.getnormal();

        Vector b_a = new Vector();
        b_a.x = (a.x - position.x);
        b_a.y = (a.y - position.y);

        float thit = ((normal.x * b_a.x) + (normal.y * b_a.y)) / ((normal.x * direction.x) + (normal.y * direction.y));

        return thit;
    }



}
