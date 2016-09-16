package is.ru.berglindoma13;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import javax.sound.sampled.Line;
import java.nio.FloatBuffer;

/**
 * Created by Berglind on 9/16/2016.
 */
public class LineGraphic {
    private static FloatBuffer vertexBuffer;
    private static int vertexPointerLocal;

    public LineGraphic(){};

    public static void drawline(int vertexPointer, Point a, Point b) {
        LineGraphic.vertexPointerLocal = vertexPointer;

        float[] array = {a.x,a.y,b.x,b.y};

        vertexBuffer = BufferUtils.newFloatBuffer(4);
        vertexBuffer.put(array);
        vertexBuffer.rewind();

        Gdx.gl.glVertexAttribPointer(vertexPointerLocal, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_LINE_STRIP, 0, 2);
    }
}
