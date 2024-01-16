package org.example.Application;

import org.example.dao.EmployeeDao;
import org.example.model.Employee;
import org.example.model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class App  extends JFrame {
    private EmployeeDao employeeDao;
    private JTable taskTable,planTable;
    private int selectedEmpId=1;
    private int monthNumb=0;
    public App(EmployeeDao employeeDao){
        this.employeeDao=employeeDao;
    }
    public void createApplication() {
    JFrame frame = new JFrame("Application");
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.setSize(1000,600);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());

    JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel labelEmployee = new JLabel("Сотрудник: ");

    JComboBox comboBox = new JComboBox<>(employeeDao.getName().toArray(new String[0]));

    panel1.add(labelEmployee);
    panel1.add(comboBox);

    taskTable = new JTable(createTaskTable(employeeDao.getEmployeeById(selectedEmpId)));
    TableCellRenderer tableCellCheckBox = new DefaultTableCellRenderer() {
        private JCheckBox checkbox = new JCheckBox();

        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            checkbox.setSelected((Boolean) value);
            checkbox.setHorizontalAlignment(JCheckBox.CENTER);
            return checkbox;
        }
    };

    taskTable.getColumnModel().getColumn(0).setCellRenderer(tableCellCheckBox);
    taskTable.getColumnModel().getColumn( 0 ).setMaxWidth(78);

    planTable = new JTable(createPlanTable(monthNumb));
    planTable.getColumnModel().getColumn(0).setPreferredWidth(500);

    JPanel planPanel =createPlanPane();
    JTabbedPane tabbedPane = new JTabbedPane();
    JScrollPane scrollPane = new JScrollPane(taskTable);

    tabbedPane.addTab("Задачи",scrollPane);
    tabbedPane.addTab("План",planPanel);

    comboBox.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
            String currentEmpoloyee = (String) comboBox.getSelectedItem();
            selectedEmpId = comboBox.getSelectedIndex()+1;
            taskTable.setModel(createTaskTable(employeeDao.getEmployeeByName(currentEmpoloyee)));
            taskTable.getColumnModel().getColumn( 0 ).setMaxWidth(78);
            taskTable.getColumnModel().getColumn(0).setCellRenderer(tableCellCheckBox);
            planTable.setModel(createPlanTable(monthNumb));
            planTable.getColumnModel().getColumn(0).setPreferredWidth(500);
            paintingCells();
        }
    });

    frame.add(panel1,BorderLayout.NORTH);
    frame.add(tabbedPane,BorderLayout.CENTER);
    frame.setVisible(true);

    }

    public DefaultTableModel createTaskTable(Employee employee){
        String[] columnNames = {"Выполнена","Задача","Дата начала","Дата оканчания","Дата выполнения"};

        List<Task> tasksList = employeeDao.getTasksForEmployee(employee);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        List<Object[]> dataList = new ArrayList<>();

        for (Task task : tasksList) {
            List<Object> values = new ArrayList<>();
            values.add(task.isCompleted());
            values.add(task.getTaskName());
            values.add(formatter.format(task.getStartDate()));
            values.add(formatter.format(task.getEndDate()));
            values.add(formatter.format(task.getImplementationDate()));
            dataList.add(values.toArray());
        }

        Object[][] data = dataList.toArray(new Object[0][]);
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        return model;
    }

    private JPanel createPlanPane(){
    String[] months = {"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};

    JPanel planPanel = new JPanel(new BorderLayout());
    JPanel panel =new JPanel(new FlowLayout(FlowLayout.LEFT));

    JLabel month = new JLabel("Месяц:         ");

    JComboBox comboBox = new JComboBox<>(months);
    panel.add(month);
    panel.add(comboBox);

    DefaultTableModel model = createPlanTable(0);
    planTable=new JTable(model);
    planTable.getColumnModel().getColumn(0).setPreferredWidth(500);
    paintingCells();
    planPanel.add(panel,BorderLayout.NORTH);
    planPanel.add(new JScrollPane(planTable),BorderLayout.CENTER);

    comboBox.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {

            DefaultTableModel model = createPlanTable(comboBox.getSelectedIndex());
            monthNumb = comboBox.getSelectedIndex();
            paintingCells();
            planTable.setModel(model);
            planTable.getColumnModel().getColumn(0).setPreferredWidth(500);
        }
    });
        return planPanel;
    }
    private DefaultTableModel createPlanTable(int monthNumber){
        List<Object> columnName = new ArrayList<>();
        columnName.add("Задача");

        Calendar monthStart = new GregorianCalendar(2009,monthNumber,1);
        int countDaysInMonth = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i=1;i<=countDaysInMonth;i++){
            columnName.add(i);
        }

        List<Task> taskList = employeeDao.getEmployeeById(selectedEmpId).getTasks();
        List<Task> filteredTasks = taskList.stream()
                .filter(task -> task.getStartDate().getMonth() == monthNumber || task.getEndDate().getMonth() == monthNumber)
                .collect(Collectors.toList());

        Object[][] data = new Object[filteredTasks.size()][countDaysInMonth];

        for (int i = 0; i < filteredTasks.size(); i++) {
            data[i][0] = filteredTasks.get(i).getTaskName();
        }
        return new DefaultTableModel(data,columnName.toArray());
    }

    private void paintingCells() {
        CustomCellsRenderer cellRenderer = null;
        int row = 0;
        List<Task> tasks = employeeDao.getEmployeeById(selectedEmpId).getTasks();

        for (Task task : tasks) {
            Date dateStart = task.getStartDate();
            Date dateEnd = task.getEndDate();

            if (dateStart.getMonth() == monthNumb || dateEnd.getMonth() == monthNumb) {
                int start = (dateStart.getMonth() == monthNumb) ? dateStart.getDate() + 1 : 1;
                int end = (dateEnd.getMonth() == monthNumb) ? dateEnd.getDate() : planTable.getColumnCount() - 1;

                if (cellRenderer == null) {
                    cellRenderer = new CustomCellsRenderer(row, start, end);
                } else {
                    cellRenderer.addRange(row, start, end);
                }

                row++;
            }
        }

        planTable.setDefaultRenderer(Object.class, cellRenderer);
    }
}
