package com.example.javaeats;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController {
    public Stage stage2 = new Stage();
    public static AnchorPane root2 = new AnchorPane();
    public static Scene scene2 = new Scene(root2);
    public TextField name;
    public Button cartButton;
    private FoodItem foodItem = FoodItem.values()[0];
    private String pw;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public TextField loginUsername;
    public TextField loginPassword;
    public Label message;
    public Label total;
    public TextField email;
    public TextField signupPassword;
    public TextField confirmPassword;
    public Button logout;
    private static final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public Pattern pattern = Pattern.compile(regex);
    public Matcher matcher;
    private String userData;
    private String[] userDataList;

    static HashMap<String,String[]> map = new HashMap<>();
//    static Map<>
    File fname = new File("C:\\Users\\123\\Desktop\\JavaEats\\src\\main\\java\\com\\example\\javaeats\\users.txt");
    Path path = Paths.get(fname.toURI());
    FileWriter fwriter = new FileWriter(fname,true);
    public static TableView<MenuData> table = new TableView<>();
    private static final ObservableList<MenuData> data = FXCollections.observableArrayList();




    public HelloController() throws IOException {

    }

    public void cart(ActionEvent event) throws IOException {
        if(root2.getChildren().contains(table)){

        }else{
            TableColumn firstNameCol = new TableColumn("Item Names");
            firstNameCol.setMinWidth(200);
            firstNameCol.setCellValueFactory(
                    new PropertyValueFactory<MenuData, String>("itemName"));

            TableColumn lastNameCol = new TableColumn("Quantity");
            lastNameCol.setMinWidth(20);
            lastNameCol.setCellValueFactory(
                    new PropertyValueFactory<MenuData, String>("Quantity"));

            TableColumn emailCol = new TableColumn("Price");
            emailCol.setMinWidth(100);
            emailCol.setCellValueFactory(
                    new PropertyValueFactory<MenuData, String>("Price"));

            TableColumn close = new TableColumn("close");
            close.setMinWidth(50);


            table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
            table.setItems(data);
            root2.getChildren().add(table);

        }

       // Button checkout =new Button("Checkout");

        stage2.setScene(scene2);
        stage2.show();
    }

    public void checkLogin(ActionEvent event) throws IOException {
        userData = Files.readAllLines(path).toString();
        userData = userData.replaceAll("\\[","");
        userData = userData.replaceAll("]","");
        if(userData.isEmpty()){
        }else{
            userData = userData.replaceAll(", ","");
            userDataList = userData.split("/");
            for (int i =0; i < userDataList.length;i+=3) {
                map.put(userDataList[i], new String[]{userDataList[i + 1], userDataList[i + 2]});
            }
        }
        if((map.containsKey(loginUsername.getText())) && (loginPassword.getText().equals(map.get(loginUsername.getText())[0]))){
            root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(loginUsername.getText().isEmpty() || loginPassword.getText().isEmpty()){
            message.setText("Please fill in required fields");
        }
        else{
            message.setText("Incorrect Username or Password!");
            loginPassword.clear();
        }
    }

    public void signUp(ActionEvent event) throws IOException {
        userData = Files.readAllLines(path).toString();
        userData = userData.replaceAll("\\[","");
        userData = userData.replaceAll("]","");
        if(userData.isEmpty()){
        }else{
            userData = userData.replaceAll(", ","");
            userDataList = userData.split("/");
            for (int i =0; i < userDataList.length;i+=3) {
                map.put(userDataList[i], new String[]{userDataList[i + 1], userDataList[i + 2]});
            }
        }
        matcher = pattern.matcher(email.getText());

        if(email.getText().isEmpty() || signupPassword.getText().isEmpty() || name.getText().isEmpty() || confirmPassword.getText().isEmpty()){
            message.setText("Please fill in required fields");
        }
        else if (map.containsKey(email.getText())) {
            message.setText("User already exists! try another email address");
        }
        else if(!(matcher.matches())){
            message.setText("Invalid Email address");
        }
//        else if(!(email.getText().endsWith("@gmail.com"))){
//            message.setText("Invalid Email Address");
//        }
        else if(signupPassword.getText().length() < 8){
            message.setText("Password cannot be less than 8 characters");
        }
        else if (((!Objects.equals(signupPassword.getText(), confirmPassword.getText())))) {
            message.setText("Password and confirm Password are not same");
        }
        else{
            map.put(email.getText(),new String[]{signupPassword.getText(), name.getText()});
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            fwriter.write(email.getText()+"/");
            fwriter.write(signupPassword.getText()+"/");
            fwriter.write(name.getText()+"/");
            fwriter.write("\n");
            fwriter.close();
        }
    }

    public void chickenBurger(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.CHICKENBURGER.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.CHICKENBURGER.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.CHICKENBURGER.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.CHICKENBURGER.name, foodItem.quantity, foodItem.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.CHICKENBURGER.name, foodItem.quantity, foodItem.getPrice()));
        }
    }
    public void zingerBurger(ActionEvent event){
            boolean match = false;
            for (int i = 0; i < table.getItems().size(); i++) {
                Object cellValue = table.getColumns().get(0).getCellData(i);
                if (cellValue.equals(FoodItem.ZINGERBURGER.name)) {
                    match = true;
                    break;
                }
            }
            if(match){
                FoodItem.ZINGERBURGER.increaseQuantity();
                int index;
                for(index = 0; index < data.size();index++){
                    if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.ZINGERBURGER.name)){
                        data.remove(index);
                        data.add(index, new MenuData(FoodItem.ZINGERBURGER.name, FoodItem.ZINGERBURGER.quantity, FoodItem.ZINGERBURGER.getPrice()));
                        break;
                    }
                }
            }if(match == false){
                data.add(new MenuData(FoodItem.ZINGERBURGER.name, FoodItem.ZINGERBURGER.quantity, FoodItem.ZINGERBURGER.getPrice()));
            }
        }
    public void beefBurger(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.BEEFBURGER.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.BEEFBURGER.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.BEEFBURGER.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.BEEFBURGER.name, FoodItem.BEEFBURGER.quantity, FoodItem.BEEFBURGER.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.BEEFBURGER.name, FoodItem.BEEFBURGER.quantity, FoodItem.BEEFBURGER.getPrice()));
        }
    }
    public void doubleBeefBurger(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.DOUBLEBEEFBURGER.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.DOUBLEBEEFBURGER.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.DOUBLEBEEFBURGER.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.DOUBLEBEEFBURGER.name, FoodItem.DOUBLEBEEFBURGER.quantity, FoodItem.DOUBLEBEEFBURGER.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.DOUBLEBEEFBURGER.name, FoodItem.DOUBLEBEEFBURGER.quantity, FoodItem.DOUBLEBEEFBURGER.getPrice()));
        }
    }
    public void andaShami(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.ANDASHAMI.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.ANDASHAMI.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.ANDASHAMI.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.ANDASHAMI.name, FoodItem.ANDASHAMI.quantity, FoodItem.ANDASHAMI.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.ANDASHAMI.name, FoodItem.ANDASHAMI.quantity, FoodItem.ANDASHAMI.getPrice()));
        }
    }
    public void zingerTower(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.ZINGERTOWER.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.ZINGERTOWER.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.ZINGERTOWER.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.ZINGERTOWER.name, FoodItem.ZINGERTOWER.quantity, FoodItem.ZINGERTOWER.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.ZINGERTOWER.name, FoodItem.ZINGERTOWER.quantity, FoodItem.ZINGERTOWER.getPrice()));
        }
    }
    public void chickenShwarma(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.CHICKENSHWARMA.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.CHICKENSHWARMA.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.CHICKENSHWARMA.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.CHICKENSHWARMA.name, FoodItem.CHICKENSHWARMA.quantity, FoodItem.CHICKENSHWARMA.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.CHICKENSHWARMA.name, FoodItem.CHICKENSHWARMA.quantity, FoodItem.CHICKENSHWARMA.getPrice()));
        }
    }
    public void zingeratha(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.ZINGERATHA.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.ZINGERATHA.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.ZINGERATHA.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.ZINGERATHA.name, FoodItem.ZINGERATHA.quantity, FoodItem.ZINGERATHA.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.ZINGERATHA.name, FoodItem.ZINGERATHA.quantity, FoodItem.ZINGERATHA.getPrice()));
        }
    }
    public void bbqShwarma(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.BBQSHWARMA.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.BBQSHWARMA.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.BBQSHWARMA.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.BBQSHWARMA.name, FoodItem.BBQSHWARMA.quantity, FoodItem.BBQSHWARMA.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.BBQSHWARMA.name, FoodItem.BBQSHWARMA.quantity, FoodItem.BBQSHWARMA.getPrice()));
        }
    }
    public void shwarmaPlatter(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.SHWARMAPLATTER.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.SHWARMAPLATTER.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.SHWARMAPLATTER.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.SHWARMAPLATTER.name, FoodItem.SHWARMAPLATTER.quantity, FoodItem.SHWARMAPLATTER.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.SHWARMAPLATTER.name, FoodItem.SHWARMAPLATTER.quantity, FoodItem.SHWARMAPLATTER.getPrice()));
        }
    }
    public void cheesyShwarma(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.CHEESYSHWARMA.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.CHEESYSHWARMA.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.CHEESYSHWARMA.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.CHEESYSHWARMA.name, FoodItem.CHEESYSHWARMA.quantity, FoodItem.CHEESYSHWARMA.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.CHEESYSHWARMA.name, FoodItem.CHEESYSHWARMA.quantity, FoodItem.CHEESYSHWARMA.getPrice()));
        }
    }
    public void bonelessHandi(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.BONELESSHANDI.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.BONELESSHANDI.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.BONELESSHANDI.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.BONELESSHANDI.name, FoodItem.BONELESSHANDI.quantity, FoodItem.BONELESSHANDI.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.BONELESSHANDI.name, FoodItem.BONELESSHANDI.quantity, FoodItem.BONELESSHANDI.getPrice()));
        }
    }
    public void chickenKarahi(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.CHICKENKARAHI.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.CHICKENKARAHI.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.CHICKENKARAHI.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.CHICKENKARAHI.name, FoodItem.CHICKENKARAHI.quantity, FoodItem.CHICKENKARAHI.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.CHICKENKARAHI.name, FoodItem.CHICKENKARAHI.quantity, FoodItem.CHICKENKARAHI.getPrice()));
        }
    }
    public void seekhKabab(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.SEEKHKABAB.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.SEEKHKABAB.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.SEEKHKABAB.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.SEEKHKABAB.name, FoodItem.SEEKHKABAB.quantity, FoodItem.SEEKHKABAB.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.SEEKHKABAB.name, FoodItem.SEEKHKABAB.quantity, FoodItem.SEEKHKABAB.getPrice()));
        }
    }
    public void malaiBoti(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.MALAIBOTI.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.MALAIBOTI.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.MALAIBOTI.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.MALAIBOTI.name, FoodItem.MALAIBOTI.quantity, FoodItem.MALAIBOTI.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.MALAIBOTI.name, FoodItem.MALAIBOTI.quantity, FoodItem.MALAIBOTI.getPrice()));
        }
    }
    public void naan(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.NAAN.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.NAAN.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.NAAN.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.NAAN.name, FoodItem.NAAN.quantity, FoodItem.NAAN.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.NAAN.name, FoodItem.NAAN.quantity, FoodItem.NAAN.getPrice()));
        }
    }
    public void roti(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.ROTI.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.ROTI.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.ROTI.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.ROTI.name, FoodItem.ROTI.quantity, FoodItem.ROTI.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.ROTI.name, FoodItem.ROTI.quantity, FoodItem.ROTI.getPrice()));
        }
    }
    public void masalaFries(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.MASALAFRIES.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.MASALAFRIES.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.MASALAFRIES.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.MASALAFRIES.name, FoodItem.MASALAFRIES.quantity, FoodItem.MASALAFRIES.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.MASALAFRIES.name, FoodItem.MASALAFRIES.quantity, FoodItem.MASALAFRIES.getPrice()));
        }
    }
    public void loadedFries(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.LOADEDFRIES.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.LOADEDFRIES.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.LOADEDFRIES.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.LOADEDFRIES.name, FoodItem.LOADEDFRIES.quantity, FoodItem.LOADEDFRIES.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.LOADEDFRIES.name, FoodItem.LOADEDFRIES.quantity, FoodItem.LOADEDFRIES.getPrice()));
        }
    }
    public void extraCheese(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.EXTRACHEESE.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.EXTRACHEESE.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.EXTRACHEESE.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.EXTRACHEESE.name, FoodItem.EXTRACHEESE.quantity, FoodItem.EXTRACHEESE.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.EXTRACHEESE.name, FoodItem.EXTRACHEESE.quantity, FoodItem.EXTRACHEESE.getPrice()));
        }
    }
    public void mayoColeslaw(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.MAYOCOLESLAW.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.MAYOCOLESLAW.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.MAYOCOLESLAW.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.MAYOCOLESLAW.name, FoodItem.MAYOCOLESLAW.quantity, FoodItem.MAYOCOLESLAW.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.MAYOCOLESLAW.name, FoodItem.MAYOCOLESLAW.quantity, FoodItem.MAYOCOLESLAW.getPrice()));
        }
    }
    public void garlicMayoDip(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.GARLICMAYODIP.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.GARLICMAYODIP.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.GARLICMAYODIP.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.GARLICMAYODIP.name, FoodItem.GARLICMAYODIP.quantity, FoodItem.GARLICMAYODIP.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.GARLICMAYODIP.name, FoodItem.GARLICMAYODIP.quantity, FoodItem.GARLICMAYODIP.getPrice()));
        }
    }
    public void cocaCola(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.COCACOLA.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.COCACOLA.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.COCACOLA.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.COCACOLA.name, FoodItem.COCACOLA.quantity, FoodItem.COCACOLA.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.COCACOLA.name, FoodItem.COCACOLA.quantity, FoodItem.COCACOLA.getPrice()));
        }
    }
    public void sprite(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.SPRITE.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.SPRITE.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.SPRITE.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.SPRITE.name, FoodItem.SPRITE.quantity, FoodItem.SPRITE.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.SPRITE.name, FoodItem.SPRITE.quantity, FoodItem.SPRITE.getPrice()));
        }
    }
    public void mirinda(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.MIRINDA.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.MIRINDA.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.MIRINDA.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.MIRINDA.name, FoodItem.MIRINDA.quantity, FoodItem.MIRINDA.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.MIRINDA.name, FoodItem.MIRINDA.quantity, FoodItem.MIRINDA.getPrice()));
        }
    }
    public void mintMargarita(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.MINTMARGARITA.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.MINTMARGARITA.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.MINTMARGARITA.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.MINTMARGARITA.name, FoodItem.MINTMARGARITA.quantity, FoodItem.MINTMARGARITA.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.MINTMARGARITA.name, FoodItem.MINTMARGARITA.quantity, FoodItem.MINTMARGARITA.getPrice()));
        }
    }
    public void chai(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.CHAI.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.CHAI.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.CHAI.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.CHAI.name, FoodItem.CHAI.quantity, FoodItem.CHAI.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.CHAI.name, FoodItem.CHAI.quantity, FoodItem.CHAI.getPrice()));
        }
    }
    public void beefCombo(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.BEEFCOMBO.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.BEEFCOMBO.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.BEEFCOMBO.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.BEEFCOMBO.name, FoodItem.BEEFCOMBO.quantity, FoodItem.BEEFCOMBO.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.BEEFCOMBO.name, FoodItem.BEEFCOMBO.quantity, FoodItem.BEEFCOMBO.getPrice()));
        }
    }
    public void pattyCombo(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.PATTYCOMBO.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.PATTYCOMBO.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.PATTYCOMBO.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.PATTYCOMBO.name, FoodItem.PATTYCOMBO.quantity, FoodItem.PATTYCOMBO.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.PATTYCOMBO.name, FoodItem.PATTYCOMBO.quantity, FoodItem.PATTYCOMBO.getPrice()));
        }
    }
    public void mightyCombo(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.MIGHTYCOMBO.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.MIGHTYCOMBO.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.MIGHTYCOMBO.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.MIGHTYCOMBO.name, FoodItem.MIGHTYCOMBO.quantity, FoodItem.MIGHTYCOMBO.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.MIGHTYCOMBO.name, FoodItem.MIGHTYCOMBO.quantity, FoodItem.MIGHTYCOMBO.getPrice()));
        }
    }
    public void shwarmaCombo(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.SHWARMACOMBO.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.SHWARMACOMBO.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.SHWARMACOMBO.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.SHWARMACOMBO.name, FoodItem.SHWARMACOMBO.quantity, FoodItem.SHWARMACOMBO.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.SHWARMACOMBO.name, FoodItem.SHWARMACOMBO.quantity, FoodItem.SHWARMACOMBO.getPrice()));
        }
    }
    public void bbqPlatter(ActionEvent event){
        boolean match = false;
        for (int i = 0; i < table.getItems().size(); i++) {
            Object cellValue = table.getColumns().get(0).getCellData(i);
            if (cellValue.equals(FoodItem.BBQPLATTER.name)) {
                match = true;
                break;
            }
        }
        if(match){
            FoodItem.BBQPLATTER.increaseQuantity();
            int index;
            for(index = 0; index < data.size();index++){
                if(data.get(index).itemName.get().equalsIgnoreCase(FoodItem.BBQPLATTER.name)){
                    data.remove(index);
                    data.add(index, new MenuData(FoodItem.BBQPLATTER.name, FoodItem.BBQPLATTER.quantity, FoodItem.BBQPLATTER.getPrice()));
                    break;
                }
            }
        }if(match == false){
            data.add(new MenuData(FoodItem.BBQPLATTER.name, FoodItem.BBQPLATTER.quantity, FoodItem.BBQPLATTER.getPrice()));
        }
    }
//    public void addToCart(ActionEvent event){
//        cartButton.setUserData(foodItem.name());
//        Node sourceComponent = (Node) event.getSource();
//        String productName = (String) sourceComponent.getUserData();
//        System.out.println(productName);
//        data.add(new MenuData(foodItem.name(), foodItem.quantity, foodItem.price));
//    }
    public void makeLabelDisappear(MouseEvent event){
        message.setText("");
    }

    public void changeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logOut(ActionEvent event) throws IOException {
        data.clear();
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void burgerButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void wrapsButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scene3.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void extrasButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scene5.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void desiButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scene4.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void drinksButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scene6.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void backToMenu(ActionEvent event){
        stage2.close();
    }

}