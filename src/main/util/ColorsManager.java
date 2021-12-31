package main.util;

import java.awt.*;

// This class contains all the colors used in this program.
// Storing all the colors in the same place makes trying different combination of color easier.
public class ColorsManager {
    public static Color signatureDarkBlue = new Color(5, 49, 121);
    public static Color whiteFont = Color.WHITE;

    public static Color loginAndSignupBackground = Color.WHITE;
    public static Color loginBackground = new Color(180, 230, 230);
    public static Color studentSignupBackground = loginBackground;
    public static Color staffSignupBackground = studentSignupBackground;

    public static Color studentModuleBackground = studentSignupBackground;
    public static Color staffModuleBackground = staffSignupBackground;

    public static Color editorBackground = new Color(189, 228, 248);//new Color(150, 230, 150);
    public static Color editorCheckBoxes = Color.WHITE;

    public static Color whiteComboBox = Color.WHITE;

    public static Color missingOccurrence = new Color(230, 150, 150);
    public static Color studentNotAllowedOccurrence = Color.lightGray;
    public static Color studentRegisteredOccurrence = new Color(189, 228, 248);
    public static Color staffCoordinatorOccurrence = new Color(80, 200, 220);
    public static Color staffRegisteredOccurrence = new Color(189, 228, 248);
}
