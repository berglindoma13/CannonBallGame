package is.ru.berglindoma13;

/**
 * Created by Berglind on 9/15/2016.
 */
public class Cannon {
    Point position;
    Vector direction;

    public Cannon(){
        position = new Point();
        direction = new Vector();
    }

    public Vector get_direction(){
        return direction;
    }
}
