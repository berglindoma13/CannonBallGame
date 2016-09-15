package is.ru.berglindoma13.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import is.ru.berglindoma13.CannonGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "CannonGame"; // or whatever you like
		config.width = 1024; //experiment with
		config.height = 768; //the window size
		new LwjglApplication(new CannonGame(), config);
	}
}
