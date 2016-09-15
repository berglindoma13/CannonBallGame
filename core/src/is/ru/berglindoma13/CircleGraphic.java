package is.ru.berglindoma13;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Created by Berglind on 9/14/2016.
 */
public class CircleGraphic {
    private static FloatBuffer vertexBuffer;
    private static int vertexPointer;
    private static int verticesPerCircle = 50;

    public static void create (int vertexPointer){
        CircleGraphic.vertexPointer = vertexPointer;

        double f = 0.0f;

        vertexBuffer = BufferUtils.newFloatBuffer(2*verticesPerCircle);

        for(int i = 0; i < verticesPerCircle; i++){
            vertexBuffer.put(2*i, (float)Math.cos(f));
            vertexBuffer.put(2*i + 1, (float)Math.sin(f));

            f += 2.0*Math.PI / (double) verticesPerCircle;
        }

        vertexBuffer.rewind();
    }

    public static void drawSolidCircle(){
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, verticesPerCircle);
    }

    public static void drawSquareLines(){
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 0, verticesPerCircle);
    }
}
