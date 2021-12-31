package main.ui;

import main.Main;
import main.object.Occurrence;
import main.util.ColorsManager;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

// This is used to show a timetable of the registered occurrences for students and staff.
public class TimetablePanel extends JPanel {

    final int START_HOUR = 9;
    final int END_HOUR = 21;

    final static int STUDENT_MODE = 0;
    final static int STAFF_MODE = 1;

    TimetablePanel(int mode){

        // Make a list of all the registered occurrence for the current user.
        List<Occurrence> registered = new ArrayList<>();
        for (String codeAndOcc: Main.currentUser.getOccurrences()) {
            if (!codeAndOcc.isBlank()){
                registered.addAll(Occurrence.getOccurrencesFromCodeAndOcc(codeAndOcc));
            }
        }

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.gridx = 0;

        // Create the header of the table.
        String[] days = new String[]{"MON", "TUE", "WED", "THU", "FRI"};
        for (String day: days) {
            c.gridx += 1;
            add(getBorderPanel(100, day), c);
        }

        c.gridx = 0;

        // Create the hours column of the table. (The blue column on the left side)
        for (int i = START_HOUR; i <= END_HOUR; i++){
            c.gridy += 1;
            add(getBorderPanel(50, String.format("%d:00", i)), c);
        }

        // This empty array will be used to fill the empty cells later.
        boolean[][] isFullCell = new boolean[6][END_HOUR - (START_HOUR - 2)];

        // Calculate the size and position of each occurrence on the table and store them in this map.
        Map<String, String> data = new HashMap<>();
        for (Occurrence occ: registered) {
            SimpleDateFormat format = new SimpleDateFormat("E h:m a");

            try {
                String[] time = occ.getTime().split(" ");
                Date start = format.parse(String.format("%s %s %s", time[0], time[1], time[2]));
                Date end = format.parse(String.format("%s %s %s", time[0], time[4], time[5]));

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(start);

                int hours = (int)((end.getTime() - start.getTime()) / 3600000);
                int startHour = startCal.get(Calendar.HOUR_OF_DAY);
                int dayOfWeek = startCal.get(Calendar.DAY_OF_WEEK) - 1;

                String dataKey = String.format("%d %d %d", hours, startHour, dayOfWeek);

                if(data.containsKey(dataKey)){
                    data.put(dataKey, String.format("%s, %d", data.get(dataKey), occ.getOccurrenceNumber()));
                } else {
                    data.put(dataKey, String.format("%s %s\n%d",occ.getCode(), occ.getActivityString().charAt(0), occ.getOccurrenceNumber()));
                }

            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        // Add the occurrences from the map calculated earlier into the table.
        for(String keyString: data.keySet()){
            String[] key = keyString.split(" ");
            int hours = Integer.parseInt(key[0]);
            int startHour = Integer.parseInt(key[1]);
            int dayOfWeek = Integer.parseInt(key[2]);

            String text = data.get(keyString);

            System.out.println(text);

            JLabel label = new JLabel(text.split("\n")[0]);
            label.setToolTipText(String.format("%s", Main.modules.get(text.split(" ")[0]).getTitle()));

            JLabel label2 = new JLabel(String.format("OCC: %s", text.split("\n")[1]));

            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());

            GridBagConstraints c2 = new GridBagConstraints();
            c2.insets = new Insets(5, 0, 5, 0);
            panel.add(label, c2);
            panel.setBorder(new BevelBorder(BevelBorder.RAISED));

            // If the current user is a staff, add the occurrences numbers of the current entry in the table,
            // because sometimes a lecture will happen for multiple different occurrences at the same time.
            if(mode == STAFF_MODE){
                c2.gridy = 1;
                panel.add(label2, c2);
            }

            int y = startHour - (START_HOUR - 1);

            c.gridx = dayOfWeek;
            c.gridy = y;
            c.gridheight = hours;
            add(panel, c);

            // Mark which cells the current occurrence has filled in the table.
            for (int i = 0; i < hours; i++){
                isFullCell[dayOfWeek][y+i] = true;
            }
        }

        // Put empty rectangles in the empty cells.
        for(int x = 1; x < isFullCell.length; x++){
            for(int y = 1; y < isFullCell[0].length; y++){
                c.gridx = x;
                c.gridy = y;
                c.gridheight = 1;

                JPanel panel = new JPanel();
                panel.setBorder(new BevelBorder(BevelBorder.LOWERED));

                add(panel, c);
            }
        }
    }

    // This is used to generate the blue cells at the borders of the table.
    JPanel getBorderPanel(int width, String text){
        JLabel label = new JLabel(text);
        label.setForeground(ColorsManager.whiteFont);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, 30));
        panel.setLayout(new GridBagLayout());
        panel.add(label);

        Border border1 = BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.RAISED), new BevelBorder(BevelBorder.LOWERED));
        Border border = BorderFactory.createCompoundBorder(border1, new BevelBorder(BevelBorder.RAISED));
        panel.setBorder(border);

        panel.setBackground(ColorsManager.signatureDarkBlue);

        return panel;
    }
}
