package net.opencraft;

import static javax.swing.JOptionPane.*;
import static net.opencraft.OpenCraft.*;

import java.io.File;

import joptsimple.*;
import net.opencraft.util.Utils;

/**
 * <h1>Launcher</h1> This is the class that prepares the workspace for OpenCraft.
 */
public class Launcher {

    public static void main(String[] args) {
        File gameDir = (File) parseArguments(args)[0];

        // Call main thread LauncherBootstrapThread
        Thread.currentThread().setName("LauncherBootstrapThread");

        OpenCraft.oc = new OpenCraft(gameDir, 854, 480);
        System.out.println("Allocating game on: " + oc.thread.getName());

        // Start the game
        oc.thread.start();

        // Wait until the game finishes
        try {
            oc.thread.join();
        } catch (Exception ignored) {
        }

        // Exit
        System.exit(0);
    }

    private static Object[] parseArguments(String[] args) {
        OptionParser parser = new OptionParser();

        Utils.GUI.setOSLookAndFeel();
        checkNatives();

        OptionSpec<?> gameDirArgument = parser.accepts("gameDir").withRequiredArg();
        OptionSpec<?> userNameArgument = parser.accepts("username").withRequiredArg();
        OptionSet optionSet = parser.parse(args);

        // User name
        if (optionSet.has(userNameArgument)) {
            System.out.println("Setting user: " + optionSet.valueOf(userNameArgument));
        }

        File gameDir = checkGameDir(optionSet, gameDirArgument);
        return new Object[]{gameDir};
    }

    private static File checkGameDir(OptionSet optionSet, OptionSpec<?> gameDirArgument) {
        File gameDir;
        if (optionSet.has(gameDirArgument)) {
            gameDir = Utils.parseDirectory(optionSet.valueOf(gameDirArgument));
        } else {
            gameDir = Utils.requestDirectory();
        }

        return gameDir;
    }

    private static void checkNatives() {
        if (!Utils.Natives.nativesLinked()) {
            final int option = showConfirmDialog(null,
                    "The game detected there are no natives linked.\nDownload natives?", "No natives linked",
                    YES_NO_OPTION, QUESTION_MESSAGE);

            if (option == YES_OPTION) {
                File nativesDir = new File(".");

                System.out.println("No natives linked: Downloading...");
                boolean successful = Utils.Natives.downloadNatives(nativesDir);
                showMessageDialog(null, "The game has finished downloading the natives!", "", INFORMATION_MESSAGE);
                if (!successful) {
                    System.err.println("Failed to download natives!");
                }

            } else {
                System.out.println("No natives linked: Ignored");
            }
        }
    }

}
