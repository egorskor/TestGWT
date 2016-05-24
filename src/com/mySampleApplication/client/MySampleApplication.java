package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.datepicker.client.DatePicker;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import com.google.gwt.event.logical.shared.*;
import com.mySampleApplication.server.MySampleApplicationServiceImpl;
import  java.util.ArrayList;
/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        RootPanel.get("main").add(modalWindow);
        RootPanel.get("main").add(table);
        RootPanel.get("main").add(addButton);


        modalWindow.add(new Label("Employee Full Name"));
        modalWindow.add(horizontalPanel);
        horizontalPanel.add(firstNameBox);
        horizontalPanel.add(secondNameBox);
        horizontalPanel.add(surnameBox);
        horizontalPanel.setSpacing(10);
        modalWindow.add(new Label("Employee gender"));
        modalWindow.add(genderListBox);
        genderListBox.addItem("Male");
        genderListBox.addItem("Female");
        modalWindow.add(new Label("Employee date of employment"));
        modalWindow.add(datePicker);
        modalWindow.add(OKButton);

        table.setText(0,0, "First Name");
        table.setText(0,1, "Second Name");
        Button sortButton=new Button("Surname Name");
        sortButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Collections.sort(employeeArrayList,new Comparator<Employee>() {
                    @Override
                    public int compare(Employee o1, Employee o2) {
                        //Window.alert(String.valueOf(o1.surname.compareTo(o1.surname)));
                        return o1.surname.compareTo(o2.surname);
                    }
                });
                for (int i=0;i<employeeArrayList.size();i++) {
                    table.setText(i+1,0,employeeArrayList.get(i).firstname);
                    table.setText(i+1,1,employeeArrayList.get(i).secondName);
                    table.setText(i+1,2,employeeArrayList.get(i).surname);
                    table.setText(i+1,3,employeeArrayList.get(i).gender);
                    table.setText(i+1,4, DateTimeFormat.getFormat("dd.MM.yy").format(employeeArrayList.get(i).dateOfEmployment));
                }
            }
        });
        table.setWidget(0,2, sortButton);
        table.setText(0,3,"Gender");
        table.setText(0,4,"Date of employment");
        table.setText(0,5,"Experience");
        modalWindow.setStyleName("modal_form");
        modalWindow.setVisible(false);
        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                modalWindow.setVisible(true);
            }
        });

        OKButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addEmployee();
            }
        });
        datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
            public void onValueChange(ValueChangeEvent<Date> event) {
                datePickerValue = event.getValue();
            }
        });
    }
    /*public String calkExperience() {
        long curTime = System.currentTimeMillis();
        String toReturn=String.valueOf((curTime-datePickerValue.getTime())/(1000*60*60*24*30*12));
        return toReturn;
    }*/

    public void addEmployee(){
        if (firstNameBox.getText().equals("")||secondNameBox.getText().equals("")||surnameBox.getText().equals("")){
            Window.alert("Please, fill in  all name fields");
        }
        else {
            String template="[a-z]+||[A-Z][a-z]+";
            if (!(firstNameBox.getText().matches(template)&&secondNameBox.getText().matches(template)&&surnameBox.getText().matches(template))) {
                Window.alert("Please, use only English letters");
            } else {

                int row = table.getRowCount();
                table.setText(row, 0, firstNameBox.getText());
                table.setText(row, 1, secondNameBox.getText());
                table.setText(row, 2, surnameBox.getText());
                table.setText(row, 3, genderListBox.getSelectedItemText());
                table.setText(row, 4, DateTimeFormat.getFormat("dd.MM.yy").format(datePickerValue));
                server.getMessage(datePickerValue.getTime(),callback);
                /*table.setText(row, 5, calkExperience());*/
                //table.setText(row, 5, employee.experience);
                final Employee employee= new Employee(firstNameBox.getText(),
                        secondNameBox.getText(),
                        surnameBox.getText(),
                        genderListBox.getSelectedItemText(),
                        datePickerValue,
                        lastServerAns);
                employeeArrayList.add(employee);
                Button removeStockButton = new Button("x");
                removeStockButton.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        int removeIndex=employeeArrayList.indexOf(employee);
                        employeeArrayList.remove(employee);
                        table.removeRow(removeIndex + 1);
                    }
                });
                table.setWidget(row, 6, removeStockButton);
                modalWindow.setVisible(false);
            }
        }
    }

    class Employee {
        String firstname;
        String secondName;
        String surname;
        String gender;
        Date dateOfEmployment;
        String experience;
        Employee(String _firstname,
                 String _secondName,
                 String _surname,
                 String _gender,
                 Date _dateOfEmployment,
                 String _experience){
            firstname=_firstname;
            secondName=_secondName;
            surname=_surname;
            gender=_gender;
            dateOfEmployment=_dateOfEmployment;
            experience=_experience;
        }

        /*public int compareTo(Object obj)
        {
            Employee tmp = (Employee) obj;
            *//*if(this.surname < tmp.surname)
            {
      *//**//* текущее меньше полученного *//**//*
                return -1;
            }
            else if(this.surname > tmp.surname)
            {
      *//**//* текущее больше полученного *//**//*
                return 1;
            }
    *//**//* текущее равно полученному *//**//*
            return 0;*//*
            return this.surname.compareTo(((Employee) obj).surname);
        }*/
    }

    private String lastServerAns="ERROR";
    private ArrayList<Employee> employeeArrayList = new ArrayList<Employee>();
    private Date datePickerValue=new Date();
    private TextArea d=new TextArea();
    private ListBox genderListBox=new ListBox();
    private TextBox firstNameBox = new TextBox();
    private TextBox secondNameBox = new TextBox();
    private TextBox surnameBox = new TextBox();
    private Button addButton = new Button("Add");
    private Button OKButton = new Button("OK");
    private FlexTable table=new FlexTable();
    private HorizontalPanel horizontalPanel=new HorizontalPanel();
    private DatePicker datePicker=new DatePicker();
    private VerticalPanel modalWindow =new VerticalPanel();
    private MySampleApplicationServiceAsync server= MySampleApplicationServiceImpl.App.getInstance();
    AsyncCallback<String> callback = new AsyncCallback<String>() {
        public void onFailure(Throwable caught) {
            // TODO: Do something with errors.
            table.setText(table.getRowCount(), 5, "ERROR");
        }
        public void onSuccess(String result) {
            table.setText(table.getRowCount()-1, 5, result);
            lastServerAns=result;
        }
    };
   /* private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }*/
}
