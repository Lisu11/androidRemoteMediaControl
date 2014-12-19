package engineering_thesis_project.android.statics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class Settings {

	private static double mouseSensitivity;
	private static boolean mouseEnableTouchClick;
	private static boolean mouseEnableScroll;

	private static int streamFrameRate;
	private static boolean streamEnableTouchpad;

	@SuppressWarnings("finally")
	public static void getSettings(Context context) {
		File[] files = context.getFilesDir().listFiles();
		String jsonString = "";
		for (File f : files) {
			if (f.isFile() && f.getName().equals(Constants.JSON_FILE)) {
				try {
					jsonString = read(f);
				} catch (IOException e) {
					Log.e("File with settings is probably damaged",
							"New file Will be created");
					f.delete();
					jsonString = "";
				} finally {
					break;
				}
			}
		}
		if (jsonString.equals("")) {
			// when we create default file or previous file was broken
			newSettings();
		} else {
			try {
				JSONObject json = new JSONObject(jsonString);
				mouseSensitivity = json
						.getDouble(Constants.JSON_MOUSE_SENSITIVITY);
				mouseEnableTouchClick = json
						.getBoolean(Constants.JSON_MOUSE_ENABLE_TOUCH);
				mouseEnableScroll = json
						.getBoolean(Constants.JSON_MOUSE_ENABLE_SCROLL);

				streamEnableTouchpad = json
						.getBoolean(Constants.JSON_STREAM_ENABLE_TOUCH);
				streamFrameRate = json.getInt(Constants.JSON_STREAM_FRAMERATE);
			} catch (JSONException e) {
				Log.e("Something goes wrong with json file",
						"now program will use default settings");
				newSettings();
			}
		}
	}

	public static void saveSettings(Context context) {
		JSONObject json = new JSONObject();
		try {
			json.put(Constants.JSON_MOUSE_SENSITIVITY, mouseSensitivity);
			json.put(Constants.JSON_MOUSE_ENABLE_TOUCH, mouseEnableTouchClick);
			json.put(Constants.JSON_MOUSE_ENABLE_SCROLL, mouseEnableScroll);

			json.put(Constants.JSON_STREAM_ENABLE_TOUCH, streamEnableTouchpad);
			json.put(Constants.JSON_STREAM_FRAMERATE, streamFrameRate);

		} catch (JSONException e) {
			Log.e("Something goes wrong with json object", "settings not saved");
			return;
		}

		try {
			File file = new File(context.getFilesDir().getAbsolutePath()
					+ Constants.JSON_FILE);
			write(file, json.toString());
		} catch (IOException e) {
			Log.e("Something goes wrong with json file", "settings not saved");
		}
	}

	private static void newSettings() {
		Log.i("Creating new settings", "by default or previous were damaged");
		mouseSensitivity = 2;
		mouseEnableTouchClick = true;
		mouseEnableScroll = false;

		streamEnableTouchpad = true;
		streamFrameRate = 350;
	}

	private static String read(File file) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader in = new BufferedReader(new FileReader(file));

		String line;
		while ((line = in.readLine()) != null) {
			stringBuilder.append(line);
		}
		in.close();

		return stringBuilder.toString();
	}

	private static void write(File file, String data) throws IOException {
		FileWriter out = new FileWriter(file);
		out.write(data);
		out.close();
	}

	public static double getMouseSensitivity() {
		return mouseSensitivity;
	}

	public static void setMouseSensitivity(double mouseSensitivity) {
		Settings.mouseSensitivity = mouseSensitivity;
	}

	public static boolean isMouseEnableTouchClick() {
		return mouseEnableTouchClick;
	}

	public static void setMouseEnableTouchClick(boolean mouseEnableTouchClick) {
		Settings.mouseEnableTouchClick = mouseEnableTouchClick;
	}

	public static boolean isMouseEnableScroll() {
		return mouseEnableScroll;
	}

	public static void setMouseEnableScroll(boolean mouseEnableScroll) {
		Settings.mouseEnableScroll = mouseEnableScroll;
	}

	public static int getStreamFrameRate() {
		return streamFrameRate;
	}

	public static void setStreamFrameRate(int streamFrameRate) {
		Settings.streamFrameRate = streamFrameRate;
	}

	public static boolean isStreamEnableTouchpad() {
		return streamEnableTouchpad;
	}

	public static void setStreamEnableTouchpad(boolean streamEnableTouchpad) {
		Settings.streamEnableTouchpad = streamEnableTouchpad;
	}
}
