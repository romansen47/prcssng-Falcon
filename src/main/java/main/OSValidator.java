package main;

public class OSValidator {

	private static String OS = System.getProperty("os.name").toLowerCase();

	public static boolean isMac() {
		return (OSValidator.OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OSValidator.OS.indexOf("nix") >= 0 || OSValidator.OS.indexOf("nux") >= 0
				|| OSValidator.OS.indexOf("aix") > 0);
	}

	public static boolean isWindows() {
		return (OSValidator.OS.indexOf("win") >= 0);
	}

}