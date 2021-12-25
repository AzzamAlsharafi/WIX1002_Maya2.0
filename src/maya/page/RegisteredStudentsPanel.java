package maya.page;

import maya.Main;
import maya.object.Account;
import maya.object.StudentAccount;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RegisteredStudentsPanel extends JPanel {
    RegisteredStudentsPanel(String occString){
        occString = occString.substring(0, occString.lastIndexOf("_"));

        String[] header = new String[]{"Matric No.", "Full Name"};

        ArrayList<String[]> dataList = new ArrayList<>();

        for (Account acc: Main.accounts.values()) {
            if(acc instanceof StudentAccount){
                if(acc.getOccurrences().contains(occString)){
                    dataList.add(new String[]{acc.getUsername(), acc.getFullName()});
                }
            }
        }

        String[][] data = new String[dataList.size()][2];

        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }

        JTable studentsTable = new JTable(data, header);
        studentsTable.setDragEnabled(false);

        int width0 = 100;
        studentsTable.getColumnModel().getColumn(0).setPreferredWidth(width0);
        studentsTable.getColumnModel().getColumn(0).setMinWidth(width0);
        studentsTable.getColumnModel().getColumn(0).setMaxWidth(width0);

        JScrollPane scrollPane = new JScrollPane(studentsTable);
        scrollPane.setPreferredSize(new Dimension(400, 400));

        setLayout(new GridBagLayout());

        add(scrollPane);
    }
}
