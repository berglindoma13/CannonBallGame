package is.ru.berglindoma13;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

import java.nio.FloatBuffer;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.BufferUtils;

public class CannonGame extends ApplicationAdapter {

	private FloatBuffer projectionMatrix;

	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private int positionLoc;

	private int modelMatrixLoc;
	private int projectionMatrixLoc;

	private int colorLoc;

    //Goal
    private static Goal goal;

    //Cannon
    private static Cannon cannon;

    //position of CannonBall
    private static boolean ball_moving;
    private static CannonBall cannonball;

    //Obstacles
    private static float[] ObstacleX;
    private static float[] ObstacleY;
    private static int Obstacles;
    private boolean leftButtonPressed;

    //Lines
    private float[] linesCoord;
    private int linecounter;
    private int lineCoordCounter;
    private boolean rightButtonPressed;
    private LineGraphic[] lines;

	@Override
	public void create () {

        //Goal initialization
        goal = new Goal();

        //Cannon initialization
        cannon = new Cannon();

        //CannonnBall initalization
        ball_moving = false;
        cannonball = new CannonBall(cannon);

        //Obstacles Array
        ObstacleX = new float[100];
        ObstacleY = new float[100];
        Obstacles = 0;
        leftButtonPressed = false;

        //Line array
        lines = new LineGraphic[100];
        rightButtonPressed = false;
        linecounter = 0;
        linesCoord = new float[4];
        lineCoordCounter = 0;


        ModelMatrix.main = new ModelMatrix();

		String vertexShaderString;
		String fragmentShaderString;

		vertexShaderString = Gdx.files.internal("core/assets/shaders/simple2D.vert").readString();
		fragmentShaderString =  Gdx.files.internal("core/assets/shaders/simple2D.frag").readString();

		vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

		Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
		Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

		Gdx.gl.glCompileShader(vertexShaderID);
		Gdx.gl.glCompileShader(fragmentShaderID);

		renderingProgramID = Gdx.gl.glCreateProgram();

		Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
		Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);

		Gdx.gl.glLinkProgram(renderingProgramID);

		positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
		Gdx.gl.glEnableVertexAttribArray(positionLoc);

		modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
		projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

		colorLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");

		Gdx.gl.glUseProgram(renderingProgramID);

		float[] pm = new float[16];

		pm[0] = 2.0f / Gdx.graphics.getWidth(); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -1.0f;
		pm[1] = 0.0f; pm[5] = 2.0f / Gdx.graphics.getHeight(); pm[9] = 0.0f; pm[13] = -1.0f;
		pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 1.0f; pm[14] = 0.0f;
		pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

		projectionMatrix = BufferUtils.newFloatBuffer(16);
		projectionMatrix.put(pm);
		projectionMatrix.rewind();
		Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, projectionMatrix);

		//COLOR IS SET HERE
		Gdx.gl.glUniform4f(colorLoc, 0.7f, 0.2f, 0, 1);
		Gdx.gl.glClearColor(0.4f, 0.6f, 1.0f, 1.0f);

		ModelMatrix.main.loadIdentityMatrix();
		ModelMatrix.main.setShaderMatrix(modelMatrixLoc);
		RectangleGraphic.create(positionLoc);
        CircleGraphic.create(positionLoc);

	}

	public void update(){

        cannon.update();
        goal.update();
        Point hit = goal.goalcords();

        if(!leftButtonPressed){
            leftButtonPressed = true;
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
            {
                ObstacleX[Obstacles] = Gdx.input.getX();
                ObstacleY[Obstacles] = Gdx.graphics.getHeight() - Gdx.input.getY();
                Obstacles++;
            }
        }
        else if(leftButtonPressed && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            leftButtonPressed = false;
        }


        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){

            cannonball.setDirection(cannon.direction.x, cannon.direction.y);
            ball_moving = true;
        }
        if (ball_moving){
            cannonball.update(hit);
        }

        if(!rightButtonPressed){
            if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
                rightButtonPressed = true;
                linesCoord[lineCoordCounter] = Gdx.input.getX();
                linesCoord[lineCoordCounter+1] = Gdx.graphics.getHeight() - Gdx.input.getY();

                lineCoordCounter += 2;
                if (lineCoordCounter == 4){
                    lineCoordCounter = 0;
                    Point a = new Point(linesCoord[0], linesCoord[1]);
                    Point b = new Point(linesCoord[2], linesCoord[3]);
                    lines[linecounter++] = new LineGraphic(positionLoc, a, b);

                }
            }
        }
        else if(rightButtonPressed && !Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            rightButtonPressed = false;
        }

    }

    public void display(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for(int i = 0; i < linecounter; i++){
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.loadIdentityMatrix();
            ModelMatrix.main.setShaderMatrix();
            lines[i].drawline();
            ModelMatrix.main.popMatrix();
        }

        //drawing the goal
        goal.display(colorLoc);

        //drawing the cannon
        cannon.display(colorLoc);

        drawObstacles();

        if(ball_moving){

            cannonball.display(colorLoc);
        }
    }

	@Override
	public void render () {
		update();
        display();
	}

    private void drawObstacles(){
        for(int i = 0; i < Obstacles; i++){
            ModelMatrix.main.pushMatrix();
            if(ObstacleX[i] > Gdx.graphics.getWidth() - 50){
                ObstacleX[i] = Gdx.graphics.getWidth() - 50;
            }
            if(ObstacleY[i] > Gdx.graphics.getHeight() - 20){
                ObstacleY[i] = Gdx.graphics.getHeight() - 20;
            }
            if(ObstacleX[i] < 50){
                ObstacleX[i] = 50;
            }
            if(ObstacleY[i] < 20){
                ObstacleY[i] = 20;
            }
            ModelMatrix.main.setModelMatrixTranslation(ObstacleX[i],ObstacleY[i], 0);
            ModelMatrix.main.setModelMatrixRotation(90);
            ModelMatrix.main.setShaderMatrix();
            Gdx.gl.glUniform4f(colorLoc, 1.0f, 0.0f, 0, 1);
            RectangleGraphic.drawSolidSquare();
            ModelMatrix.main.popMatrix();
        }
    }

    public static float[] getObstaclesX(){
        return ObstacleX;
    }
    public static float[] getObstacleY(){
        return ObstacleY;
    }
    public static float getNumberOfObstacles(){
        return Obstacles;
    }

    public static void stopball(){
        ball_moving = false;
    }

}