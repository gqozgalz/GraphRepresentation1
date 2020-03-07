package com.algo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Welcome extends JFrame {
    private JPanel panel1;
    private JButton ADDButton;
    private JButton removeButton;
    private JButton generateButton;
    private JTable edgeTable;
    private JButton ADDVetrexButton;
    private JButton REMOVEVertexButton;
    private JTable vertexTable;
    private DefaultTableModel edgeTableModel;
    private DefaultTableModel vertexTableModel;

    Welcome() {
        edgeTableModel = new DefaultTableModel(new Object[]{}, 0) {
            @Override
            public Class getColumnClass(int columnIndex) {
                if (columnIndex == 2)
                    return Boolean.class;
                return String.class;
            }
        };
        vertexTableModel = new DefaultTableModel();
        setTitle("DataForm");
        setSize(800, 600);
        add(panel1);
        edgeTable.setModel(edgeTableModel);
        edgeTableModel.addColumn("From");
        edgeTableModel.addColumn("To");
        edgeTableModel.addColumn("Directed");


        vertexTable.setModel(vertexTableModel);
        vertexTableModel.addColumn("Vertex Name");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        btnActions();


    }

    private void btnActions() {
        ADDVetrexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                vertexTableModel.insertRow(vertexTableModel.getRowCount(), new Object[]{""});
            }
        });
        REMOVEVertexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int[] rows = vertexTable.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    vertexTableModel.removeRow(rows[i] - i);
                }
            }
        });
        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                edgeTableModel.insertRow(edgeTableModel.getRowCount(), new Object[]{ "", "", false});
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int[] rows = edgeTable.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    edgeTableModel.removeRow(rows[i] - i);
                }
            }
        });
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<String> vertices = new ArrayList<>();
                for (int i = 0; i < vertexTable.getRowCount(); i++) {
                    vertices.add(vertexTableModel.getValueAt(i, 0).toString());
                }
                ArrayList<Edge> edges = new ArrayList<>();
                for (int i = 0; i < edgeTable.getRowCount(); i++) {
                    edges.add(new Edge(
                            edgeTableModel.getValueAt(i, 0).toString(),
                            edgeTableModel.getValueAt(i, 1).toString(),
                            (Boolean) edgeTableModel.getValueAt(i, 2)
                    ));
                }
                Matrices matrices=new Matrices(vertices,edges);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            Result result = new Result(matrices);
                            result.setVisible(true);
                        }
                    });
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Welcome welcome = new Welcome();
                welcome.setVisible(true);
            }
        });
    }
}
