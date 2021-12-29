package maya.util;

import maya.Main;
import maya.object.Occurrence;
import maya.object.Module;
import maya.util.DataManager;

import java.util.ArrayList;

public class ModulesAdder {
    public static void addAll(){
        Main.modules.clear();

        WIX1001();
        WIX1002();
        WIX1003();
        WIX2001();
        WIX2002();
        WIA2001();
        WIC3002();
        WID3007();
        WIE3007();
        WIF3005();
        WIG3007();
        WIH3003();
        GLT1018();
        GLT1021();
        GLT1024();
        GLT1027();

        DataManager.storeModules();
    }

    private static void WIX1001(){
        String code = "WIX1001";
        int credits = 3;

        Occurrence o1t = new Occurrence(code, "AZNUL QALID BIN MD SABRI", "Wednesday 12:00 PM - 1:00 PM", 45, 1, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o1l = new Occurrence(code, "LIM CHEE KAU", "Monday 4:00 PM - 6:00 PM", 45, 1, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o2t = new Occurrence(code, "ERMA RAHAYU BINTI MOHD FAIZAL ABDULLAH", "Wednesday 1:00 PM - 2:00 PM", 45,2, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o2l = new Occurrence(code, "ERMA RAHAYU BINTI MOHD FAIZAL ABDULLAH", "Monday 4:00 PM - 6:00 PM", 45,2, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o3t = new Occurrence(code, "AZNUL QALID BIN MD SABRI", "Wednesday 2:00 PM - 3:00 PM", 45,3, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o3l = new Occurrence(code, "LIM CHEE KAU", "Monday 4:00 PM - 6:00 PM", 45,3, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1t);
        occurrences.add(o1l);
        occurrences.add(o2t);
        occurrences.add(o2l);
        occurrences.add(o3t);
        occurrences.add(o3l);

        Module m = new Module(code, "COMPUTING MATHEMATICS I", "LIM CHEE KAU", "", credits, Module.MOD_TYPE_LECTURE_TUTORIAL, occurrences);

        Main.modules.put(code, m);
    }

    private static void WIX1002(){
        String code = "WIX1002";
        int credits = 5;

        String atf = "ANG TAN FONG";

        Occurrence o1t = new Occurrence(code, atf, "Thursday 9:00 AM - 12:00 PM", 50,1, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o1l = new Occurrence(code, atf, "Monday 9:00 AM - 11:00 AM", 50,1, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o2t = new Occurrence(code, "SITI SORAYA BINTI ABDUL RAHMA", "Thursday 9:00 AM - 12:00 PM", 50,2, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o2l = new Occurrence(code, atf, "Monday 9:00 AM - 11:00 AM", 50,2, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o3t = new Occurrence(code, "HEMA A/P SUBRAMANIAM", "Friday 9:00 AM - 12:00 PM", 50,3, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o3l = new Occurrence(code, atf, "Monday 9:00 AM - 11:00 AM", 50,3, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1t);
        occurrences.add(o1l);
        occurrences.add(o2t);
        occurrences.add(o2l);
        occurrences.add(o3t);
        occurrences.add(o3l);

        Module m = new Module(code, "FUNDAMENTALS OF PROGRAMMING", atf, "", credits, Module.MOD_TYPE_LECTURE_TUTORIAL, occurrences);

        Main.modules.put(code, m);
    }

    private static void WIX1003(){
        String code = "WIX1003";
        String title = "COMPUTER SYSTEMS AND ORGANIZATION";
        int credits = 3;

        Occurrence o1t = new Occurrence(code, "ROZIANA BINTI RAMLI", "Thursday 1:00 PM - 2:00 PM", 36, 1, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o1l = new Occurrence(code, "TEY KOK SOON", "Wednesday 9:00 AM - 11:00 AM", 36, 1, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o2t = new Occurrence(code, "EMRAN BIN MOHD TAMIL", "Thursday 2:00 PM - 3:00 PM", 36,2, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o2l = new Occurrence(code, "TEY KOK SOON", "Wednesday 9:00 AM - 11:00 AM", 36,2, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o3t = new Occurrence(code, "EMRAN BIN MOHD TAMIL", "Friday 11:00 AM - 12:00 PM", 36,3, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o3l = new Occurrence(code, "TEY KOK SOON", "Wednesday 9:00 AM - 11:00 AM", 36,3, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1t);
        occurrences.add(o1l);
        occurrences.add(o2t);
        occurrences.add(o2l);
        occurrences.add(o3t);
        occurrences.add(o3l);

        Module m = new Module(code, title, "TEY KOK SOON", "", credits, Module.MOD_TYPE_LECTURE_TUTORIAL, occurrences);

        Main.modules.put(code, m);
    }

    private static void WIX2001(){
        String code = "WIX2001";
        String title = "THINKING AND COMMUNICATION SKILLS";
        int credits = 3;

        Occurrence o1l = new Occurrence(code, "HANNYZZURA BINTI PAL @ AFFAL", "Tuesday 10:00 AM - 1:00 PM", 55, 1, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o2l = new Occurrence(code, "MAS IDAYU BINTI MD SABRI", "Tuesday 10:00 AM - 1:00 PM", 55,2, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o3l = new Occurrence(code, "NORNAZLITA BINTI HUSSIN", "Tuesday 10:00 AM - 1:00 PM", 55,3, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1l);
        occurrences.add(o2l);
        occurrences.add(o3l);

        Module m = new Module(code, title, "HANNYZZURA BINTI PAL @ AFFAL", "", credits, Module.MOD_TYPE_LECTURE, occurrences);

        Main.modules.put(code, m);
    }

    private static void WIX2002(){
        String code = "WIX2002";
        String title = "PROJECT MANAGEMENT";
        int credits = 3;

        Occurrence o1t = new Occurrence(code, "CHIAM YIN KIA", "Wednesday 4:00 PM - 5:00 PM", 60, 1, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o1l = new Occurrence(code, "CHIAM YIN KIA", "Monday 9:00 AM - 11:00 AM", 60, 1, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o2t = new Occurrence(code, "HEMA A/P SUBRAMANIAM", "Wednesday 4:00 PM - 5:00 PM", 60,2, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o2l = new Occurrence(code, "HEMA A/P SUBRAMANIAM", "Monday 4:00 PM - 6:00 PM", 60,2, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o3t = new Occurrence(code, "MOHD HAIRUL NIZAM BIN MD NASIR", "Wednesday 5:00 PM - 6:00 PM", 60,3, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o3l = new Occurrence(code, "MOHD HAIRUL NIZAM BIN MD NASIR", "Monday 9:00 AM - 11:00 AM", 60,3, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1t);
        occurrences.add(o1l);
        occurrences.add(o2t);
        occurrences.add(o2l);
        occurrences.add(o3t);
        occurrences.add(o3l);

        Module m = new Module(code, title, "CHIAM YIN KIA", "", credits, Module.MOD_TYPE_LECTURE_TUTORIAL, occurrences);

        Main.modules.put(code, m);
    }

    private static void WIA2001(){
        String code = "WIA2001";
        String title = "DATABASE";
        int credits = 3;

        Occurrence o1t = new Occurrence(code, "FARIZA HANUM BINTI MD NASARUDDIN", "Wednesday 12:00 PM - 1:00 PM", 52, 1, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o1l = new Occurrence(code, "FARIZA HANUM BINTI MD NASARUDDIN", "Monday 2:00 PM - 4:00 PM", 52, 1, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o2t = new Occurrence(code, "MAIZATUL AKMAR BINTI ISMAIL", "Wednesday 12:00 PM - 1:00 PM", 52,2, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o2l = new Occurrence(code, "MAIZATUL AKMAR BINTI ISMAIL", "Monday 2:00 PM - 4:00 PM", 52,2, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o3t = new Occurrence(code, "NORJIHAN BINTI ABDUL GHANI", "Wednesday 12:00 PM - 1:00 PM", 52,3, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o3l = new Occurrence(code, "NORJIHAN BINTI ABDUL GHANI", "Monday 2:00 PM - 4:00 PM", 52,3, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1t);
        occurrences.add(o1l);
        occurrences.add(o2t);
        occurrences.add(o2l);
        occurrences.add(o3t);
        occurrences.add(o3l);

        Module m = new Module(code, title, "FARIZA HANUM BINTI MD NASARUDDIN", "", credits, Module.MOD_TYPE_LECTURE_TUTORIAL, occurrences);

        Main.modules.put(code, m);
    }

    private static void WIC3002(){
        String code = "WIC3002";
        String title = "CRYPTOGRAPHY";
        int credits = 3;

        Occurrence o1t = new Occurrence(code, "POR LIP YEE @ POR KHOON SUN", "Friday 2:00 PM - 3:00 PM", 100, 1, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o1l = new Occurrence(code, "POR LIP YEE @ POR KHOON SUN", "Wednesday 9:00 AM - 11:00 AM", 100, 1, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1t);
        occurrences.add(o1l);

        Module m = new Module(code, title, "POR LIP YEE @ POR KHOON SUN", "0 : -1", credits, Module.MOD_TYPE_LECTURE_TUTORIAL, occurrences);

        Main.modules.put(code, m);
    }

    private static void WID3007(){
        String code = "WID3007";
        String title = "FUZZY LOGIC";
        int credits = 3;

        Occurrence o1t = new Occurrence(code, "LIM CHEE KAU", "Thursday 11:00 AM - 12:00 PM", 80, 1, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o1l = new Occurrence(code, "LIM CHEE KAU", "Tuesday 4:00 PM - 6:00 PM", 80, 1, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1t);
        occurrences.add(o1l);

        Module m = new Module(code, title, "LIM CHEE KAU", "1 : -1", credits, Module.MOD_TYPE_LECTURE_TUTORIAL, occurrences);

        Main.modules.put(code, m);
    }

    private static void WIE3007(){
        String code = "WIE3007";
        String title = "DATA MINING AND WAREHOUSING";
        int credits = 3;

        Occurrence o1t = new Occurrence(code, "TEH YING WAH", "Thursday 4:00 PM - 5:00 PM", 80, 1, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o1l = new Occurrence(code, "TEH YING WAH", "Wednesday 4:00 PM - 6:00 PM", 80, 1, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1t);
        occurrences.add(o1l);

        Module m = new Module(code, title, "TEH YING WAH", "2, 5 : -1", credits, Module.MOD_TYPE_LECTURE_TUTORIAL, occurrences);

        Main.modules.put(code, m);
    }

    private static void WIF3005(){
        String code = "WIF3005";
        String title = "SOFTWARE MAINTENANCE AND EVOLUTION";
        int credits = 3;

        Occurrence o1t = new Occurrence(code, "HASAN KAHTAN KHALAF", "Thursday 4:00 PM - 5:00 PM", 40, 1, Occurrence.OCC_TYPE_TUTORIAL);
        Occurrence o1l = new Occurrence(code, "HASAN KAHTAN KHALAF", "Tuesday 4:00 PM - 6:00 PM", 40, 1, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1t);
        occurrences.add(o1l);

        Module m = new Module(code, title, "HASAN KAHTAN KHALAF", "3 : -1", credits, Module.MOD_TYPE_LECTURE_TUTORIAL, occurrences);

        Main.modules.put(code, m);
    }

    private static void WIG3007(){
        String code = "WIG3007";
        String title = "SPECIAL TOPICS IN MULTIMEDIA";
        int credits = 3;

        Occurrence o1l = new Occurrence(code, "AMIRRUDIN BIN KAMSIN", "Tuesday 3:00 PM - 6:00 PM", 50, 1, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1l);

        Module m = new Module(code, title, "AMIRRUDIN BIN KAMSIN", "4 : -1", credits, Module.MOD_TYPE_LECTURE, occurrences);

        Main.modules.put(code, m);
    }

    private static void WIH3003(){
        String code = "WIH3003";
        String title = "BIG DATA APPLICATIONS AND ANALYTICS";
        int credits = 3;

        Occurrence o1l = new Occurrence(code, "HOO WAI LAM", "Tuesday 9:00 AM - 12:00 PM", 35, 1, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1l);

        Module m = new Module(code, title, "HOO WAI LAM", "5 : -1", credits, Module.MOD_TYPE_LECTURE_TUTORIAL, occurrences);

        Main.modules.put(code, m);
    }

    private static void GLT1018(){
        String code = "GLT1018";
        String title = "PROFICIENCY IN ENGLISH I";
        int credits = 2;

        Occurrence o1l = new Occurrence(code, "KAMARAZAMAN BIN MD SAID", "Monday 10:00 AM - 12:00 PM", 18, 1, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o2l = new Occurrence(code, "AINA AZLIN BINTI MOHAMAT ARIF", "Tuesday 5:00 PM - 7:00 PM", 20,2, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o3l = new Occurrence(code, "NOR SYAKIRIN BINTI MOHAMED NASRUDIN", "Wednesday 10:00 AM - 12:00 PM", 20,3, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1l);
        occurrences.add(o2l);
        occurrences.add(o3l);

        Module m = new Module(code, title, "KAMARAZAMAN BIN MD SAID", "-1 : 2", credits, Module.MOD_TYPE_LECTURE, occurrences);

        Main.modules.put(code, m);
    }

    private static void GLT1021(){
        String code = "GLT1021";
        String title = "PROFICIENCY IN ENGLISH II";
        int credits = 2;

        Occurrence o1l = new Occurrence(code, "NOR AAFINA BINTI MOHD ZAMIL", "Monday 9:00 AM - 11:00 AM", 20, 1, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o2l = new Occurrence(code, "NOR AZLINA BINTI MUHAMAD", "Wednesday 4:00 PM - 6:00 PM", 22,2, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o3l = new Occurrence(code, "FARRIL DANIEL BIN ZAINAL", "Wednesday 6:00 PM - 8:00 PM", 22,3, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1l);
        occurrences.add(o2l);
        occurrences.add(o3l);

        Module m = new Module(code, title, "NOR AAFINA BINTI MOHD ZAMIL", "-1 : 3", credits, Module.MOD_TYPE_LECTURE, occurrences);

        Main.modules.put(code, m);
    }

    private static void GLT1024(){
        String code = "GLT1024";
        String title = "PROFICIENCY IN ENGLISH III";
        int credits = 2;

        Occurrence o1l = new Occurrence(code, "KIRANJEET KAUR A/P MUKHTIAR SINGH", "Monday 8:00 AM - 10:00 AM", 22, 1, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o2l = new Occurrence(code, "MOHD NAZRIQ BIN NOOR AHMAD", "Monday 11:00 AM - 1:00 PM", 22,2, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o3l = new Occurrence(code, "TAN KEN SIANG", "Monday 2:00 PM - 4:00 PM", 22,3, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1l);
        occurrences.add(o2l);
        occurrences.add(o3l);

        Module m = new Module(code, title, "KIRANJEET KAUR A/P MUKHTIAR SINGH", "-1 : 4", credits, Module.MOD_TYPE_LECTURE, occurrences);

        Main.modules.put(code, m);
    }

    private static void GLT1027(){
        String code = "GLT1027";
        String title = "ADVANCED ORAL COMMUNICATION";
        int credits = 2;

        Occurrence o1l = new Occurrence(code, "NG GOON HOONG PETER", "Monday 2:00 PM - 4:00 PM", 27, 1, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o2l = new Occurrence(code, "LIM CHOY WAN", "Tuesday 2:00 PM - 4:00 PM", 26,2, Occurrence.OCC_TYPE_LECTURE);

        Occurrence o3l = new Occurrence(code, "MOHD NAZRIQ BIN NOOR AHMAD", "Thursday 2:00 PM - 4:00 PM", 26,3, Occurrence.OCC_TYPE_LECTURE);

        ArrayList<Occurrence> occurrences = new ArrayList<>();

        occurrences.add(o1l);
        occurrences.add(o2l);
        occurrences.add(o3l);

        Module m = new Module(code, title, "NG GOON HOONG PETER", "-1 : 5, 6", credits, Module.MOD_TYPE_LECTURE, occurrences);

        Main.modules.put(code, m);
    }
}
