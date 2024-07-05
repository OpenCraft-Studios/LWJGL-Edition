package net.opencraft.util;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.lwjgl.opengl.Display;

import net.opencraft.annotation.Required;

public class Utils {

	public static final String LWJGL_ZIP = "https://kumisystems.dl.sourceforge.net/project/java-game-lib/Official%20Releases/LWJGL%202.9.3/lwjgl-2.9.3.zip?viasf=1";

	private Utils() {
	}

	public static class GUI {

		private GUI() {
		}

		/**
		 * Sets the style of the dialog boxes to system's default.
		 * 
		 * @return true if the look and feel was established successfully, otherwise
		 *         false
		 */
		public static boolean setOSLookAndFeel() {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception ignored) {
				System.err.println("Failed to set Look and feel!");
				return false;
			}

			return true;
		}

		public static boolean alert(String title, String message) {
			try {
				Class<?> sysClass = Class.forName("org.lwjgl.Sys");
				Field[] fields = sysClass.getDeclaredFields();

				Object implementation = null;

				for (Field field : fields) {
					if (!field.getName().equals("implementation"))
						continue;

					field.setAccessible(true);
					implementation = field.get(null);
				}

				Objects.requireNonNull(implementation, "LWJGL Implementation must not be null!");
				Class<?> sysImplementationClass = Class.forName("org.lwjgl.SysImplementation");

				Method[] methods = sysImplementationClass.getDeclaredMethods();
				for (Method method : methods) {
					if (!method.getName().equals("alert"))
						continue;

					method.setAccessible(true);
					method.invoke(implementation, title, message);
				}
			} catch (Exception ignored) {
				return false;
			}

			return true;
		}

	}

	public static class Natives {

		private Natives() {
		}

		/**
		 * Downloads the natives into the selected directory.
		 * 
		 * @return true if everything goes successful, otherwise false
		 */
		public static boolean downloadNatives(File nativesDir) {
			try {
				// Execute part 1
				downloadNatives0(nativesDir);
				// Execute part 2
				downloadNatives1(nativesDir);
			} catch (Exception ignored) {
				return false;
			}

			return true;
		}

		private static void downloadNatives0(File nativesDir) throws Exception {
			// If the specified item is a file, delete it
			if (nativesDir.isFile())
				nativesDir.delete();

			// If the specified item doesn't exists, create it
			if (!nativesDir.exists())
				nativesDir.mkdirs();

			/* Create the LWJGL.zip file */

			// The file
			File lwjglFile = new File(nativesDir, "lwjgl.zip");

			// If a directory takes the name "lwjgl.zip", delete it
			if (lwjglFile.isDirectory())
				lwjglFile.delete();

			// Create the URI of the file
			URI uri = URI.create(LWJGL_ZIP);

			// Download the file
			try (InputStream in = uri.toURL().openStream()) {
				Files.copy(in, lwjglFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}

		private static void downloadNatives1(@Required File nativesDir) throws Exception {
			@Required
			File lwjglFile = new File(nativesDir, "lwjgl.zip");

			// If the 'lwjgl.zip' file doesn't exists, throw an exception
			if (!lwjglFile.exists())
				throw new Exception();

			if (nativesDir.isFile())
				nativesDir.delete();

			if (!nativesDir.exists())
				nativesDir.mkdirs();

			final boolean windows, linux, macOS;
			{
				final String strOS = System.getProperty("os.name").toLowerCase();
				windows = strOS.contains("windows");
				linux = strOS.contains("linux");
				macOS = strOS.contains("macos");
			}

			ZipFile zip = new ZipFile(lwjglFile);
			final String strNativesEntry;

			if (windows)
				strNativesEntry = "native/windows";
			else if (linux)
				strNativesEntry = "native/linux";
			else if (macOS)
				strNativesEntry = "native/macosx";
			else
				strNativesEntry = "";

			Enumeration<? extends ZipEntry> entries_enum = zip.entries();
			while (entries_enum.hasMoreElements()) {
				ZipEntry entry = entries_enum.nextElement();
				if (!entry.getName().contains(strNativesEntry))
					continue;
				if (entry.isDirectory())
					continue;

				System.out.println("Extracting: ".concat(entry.getName()));

				Path path;
				{
					String strPath = entry.getName();
					String[] folders = strPath.split("/");
					strPath = folders[folders.length - 1];

					path = new File(nativesDir, strPath).toPath();
				}

				try (InputStream in = zip.getInputStream(entry)) {
					Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
				}
			}

			zip.close();
			lwjglFile.delete();
		}

		public static boolean nativesLinked() {
			return !System.getProperty("java.library.path").contains(";");
		}
	}

	public static boolean isFullscreen(int width, int height) {
		final int dWidth = Display.getDisplayMode().getWidth();
		final int dHeight = Display.getDisplayMode().getHeight();

		if (width >= dWidth && height >= dHeight)
			return true;

		return false;
	}

	public static File requestDirectory() {
		boolean invalidDir = true;
		File directory;

		do {
			String strDirectory;
			strDirectory = (String) JOptionPane.showInputDialog(null, "Insert the directory of the game:", "opcraft");

			// If the user has pressed the cancel button, exit JVM
			if (strDirectory == null)
				System.exit(0);

			// Try to parse directory
			directory = parseDirectory(strDirectory);
			if (invalidDir = directory == null)
				JOptionPane.showMessageDialog(null, "Invalid directory! Try again.", "", JOptionPane.ERROR_MESSAGE);

		} while (invalidDir);

		return directory;
	}

	public static File parseDirectory(Object directory) {
		File dir;

		try {
			dir = new File((String) directory);
		} catch (Exception ignored) {
			return null;
		}

		if (dir.isFile())
			dir.delete();
		if (!dir.exists())
			dir.mkdirs();

		return dir;
	}

}
