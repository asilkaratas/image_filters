/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.view;

import com.imagefilters.controller.ResizeController;
import com.imagefilters.controller.RowColumnResizeController;
import com.imagefilters.filters.convolutionfilters.ConvolutionFilter;
import com.imagefilters.model.AppModel;
import com.imagefilters.model.enums.EdgeMode;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author asilkaratas
 */
public class ConvolutionFilterView extends VBox
{
    private ConvolutionFilter originalFilter;
    private ConvolutionFilter filter;
    private VBox kernelVBox;
    private VBox pivotVBox;
    private ToggleGroup edgeModeToggleGroup;
    private TextField offsetTextField;
    private TextField dTextField;
    
    private ResizeController resizeController;
    
    public ConvolutionFilterView(final ConvolutionFilter filter)
    {
        this.originalFilter = filter;
        this.filter = filter.clone();
        
        BorderedTitledPane kernelPane = createKernelPane();
        BorderedTitledPane pivotPane = createPivotPane();
        BorderedTitledPane settingsPane = createSettingsPane();
        //BorderedTitledPane resizePane = createResizePane();
        BorderedTitledPane edgeModePane = createEdgeModePane();
        BorderedTitledPane buttonsPane = createButtonsPane();
        VBox singleResizePane = createSingleResizePane();
        
        HBox rightVBox = new HBox();
        rightVBox.getChildren().addAll(settingsPane, singleResizePane, edgeModePane, buttonsPane);
        
        getChildren().addAll(rightVBox, kernelPane, pivotPane);
        
        updateUI();
    }
    
    private VBox createSingleResizePane()
    {
        Button addColumnButton = new Button("+");
        Button removeColumnButton = new Button("-");
        
        Button addRowButton = new Button("+");
        Button removeRowButton = new Button("-");
       
        HBox rowBox = new HBox();
        rowBox.setSpacing(5.0);
        rowBox.getChildren().addAll(addRowButton, removeRowButton);
        
        HBox columnBox = new HBox();
        columnBox.setSpacing(5.0);
        columnBox.getChildren().addAll(addColumnButton, removeColumnButton);
        
        BorderedTitledPane rowPane = new BorderedTitledPane("row", rowBox);
        BorderedTitledPane columnPane = new BorderedTitledPane("column", columnBox);
        
        VBox box = new VBox();
        //box.setSpacing(5.0);
        box.getChildren().addAll(rowPane, columnPane);
        
        RowColumnResizeController controller = new RowColumnResizeController(this, filter, addRowButton, removeRowButton, addColumnButton, removeColumnButton);
        
        return box;
    }
    
    private BorderedTitledPane createKernelPane()
    {
        kernelVBox = new VBox();
        kernelVBox.setSpacing(1.0);
        
        HBox kernelHBox = new HBox();
        kernelHBox.setAlignment(Pos.CENTER);
        kernelHBox.getChildren().add(kernelVBox);
        
        BorderedTitledPane kernelPane = new BorderedTitledPane("kernel", kernelHBox);
        return kernelPane;
    }
    
    private void updateKernelUI()
    {
        kernelVBox.getChildren().clear();
        
        ArrayList<ArrayList<Integer>> kernelList = filter.getKernelList();
        int kernelWidth = filter.getKernelWidth();
        int kernelHeight = filter.getKernelHeight();
        
        for(int i = 0; i < kernelHeight; ++i)
        {
            ArrayList<Integer> kernelRow = kernelList.get(i);
            
            HBox hBox = new HBox();
            hBox.setSpacing(1.0);
            kernelVBox.getChildren().add(hBox);
            
           for(int j = 0; j < kernelWidth; ++j)
           {
               int index = i * kernelWidth + j;
               int value = kernelRow.get(j);
               
               TextField textField = new TextField(String.valueOf(value));
               textField.setUserData(index);
               textField.setOnKeyReleased(new TextChangeHandler());
               textField.setAlignment(Pos.CENTER);
               textField.setMaxWidth(50.0);
               hBox.getChildren().add(textField);
           }
        }
    }
    
    private BorderedTitledPane createPivotPane()
    {   
        pivotVBox = new VBox();
        pivotVBox.setSpacing(1.0);
        
        HBox pivotHBox = new HBox();
        pivotHBox.setAlignment(Pos.CENTER);
        pivotHBox.getChildren().add(pivotVBox);
        
        BorderedTitledPane pivotPane = new BorderedTitledPane("pivot", pivotHBox);
        return pivotPane;
    }
    
    private void updatePivotUI()
    {
        pivotVBox.getChildren().clear();
        
        int kernelWidth = filter.getKernelWidth();
        int kernelHeight = filter.getKernelHeight();
        int pivotIndex = filter.getPivotIndex();
        
        ToggleGroup pivotGroup = new ToggleGroup();
        pivotGroup.selectedToggleProperty().addListener(new PivotChangeHandler());
        for(int i = 0; i < kernelHeight; ++i)
        {
            HBox hBox = new HBox();
            hBox.setSpacing(1.0);
            pivotVBox.getChildren().add(hBox);
           for(int j = 0; j < kernelWidth; ++j)
           {
               int index = i * kernelWidth + j;
               
               RadioButton radioButton = new RadioButton();
               radioButton.setToggleGroup(pivotGroup);
               radioButton.setUserData(index);
               hBox.getChildren().add(radioButton);
               
               if(index == pivotIndex)
               {
                   radioButton.setSelected(true);
               }
           }
        }
    }
    
    private BorderedTitledPane createButtonsPane()
    {
        Button applyButton = new Button("apply");
        applyButton.setOnAction(new ApplyButtonHandler());
        
        Button resetButton = new Button("reset");
        resetButton.setOnAction(new ResetButtonHandler());
        
        VBox buttonsHBox = new VBox();
        buttonsHBox.setSpacing(5.0);
        buttonsHBox.getChildren().addAll(applyButton, resetButton);
        
        
        BorderedTitledPane buttonsPane = new BorderedTitledPane("apply", buttonsHBox);
        return buttonsPane;
    }
    
    private BorderedTitledPane createResizePane()
    {
        Button minusButton = new Button("-");
        Button plusButton = new Button("+");
        HBox resizeHBox = new HBox();
        resizeHBox.setSpacing(5.0);
        resizeHBox.setAlignment(Pos.CENTER);
        resizeHBox.getChildren().addAll(minusButton, plusButton);
        
        BorderedTitledPane resizePane = new BorderedTitledPane("resize", resizeHBox);
        
        resizeController = new ResizeController(this, minusButton, plusButton, filter, 3, 9);
        return resizePane;
    }
    
    private BorderedTitledPane createSettingsPane()
    {
        Label offsetLabel = new Label("offset:");
        offsetTextField = new TextField();
        offsetTextField.setMaxWidth(50.0);
        
        Label dLabel = new Label("D:");
        dTextField = new TextField();
        dTextField.setMaxWidth(50.0);
        
        
        GridPane.setConstraints(offsetLabel, 0, 0);
        GridPane.setConstraints(offsetTextField, 1, 0);
        GridPane.setConstraints(dLabel, 0, 1);
        GridPane.setConstraints(dTextField, 1, 1);
        
        GridPane.setHalignment(offsetLabel, HPos.RIGHT);
        GridPane.setHalignment(dLabel, HPos.RIGHT);
                
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5.0);
        gridPane.setVgap(5.0);
        gridPane.getChildren().addAll(offsetLabel, offsetTextField, dLabel, dTextField);
        
        BorderedTitledPane settingsPane = new BorderedTitledPane("settings", gridPane);
        return settingsPane;
    }
    
    private BorderedTitledPane createEdgeModePane()
    {
        edgeModeToggleGroup = new ToggleGroup();
        
        RadioButton blackButton = new RadioButton("black");
        RadioButton repeatButton = new RadioButton("repeat");
        RadioButton wrapButton = new RadioButton("wrap");
        
        blackButton.setUserData(EdgeMode.BLACK);
        repeatButton.setUserData(EdgeMode.REPEAT);
        wrapButton.setUserData(EdgeMode.WRAP);
        
        blackButton.setToggleGroup(edgeModeToggleGroup);
        repeatButton.setToggleGroup(edgeModeToggleGroup);
        wrapButton.setToggleGroup(edgeModeToggleGroup);
        
        VBox vBox = new VBox();
        vBox.setSpacing(5.0);
        vBox.getChildren().addAll(blackButton, repeatButton, wrapButton);
        
        for(int i = 0; i < vBox.getChildren().size(); ++i)
        {
            RadioButton radioButton = (RadioButton)vBox.getChildren().get(i);
            if(radioButton.getUserData() == filter.getEdgeMode())
            {
                radioButton.setSelected(true);
            }
        }
        
        BorderedTitledPane edgeModePane = new BorderedTitledPane("edge mode", vBox);
        edgeModePane.setPrefWidth(120.0);
        return edgeModePane;
    }
    
    private void updateSettingsUI()
    {
        offsetTextField.setText(String.valueOf(filter.getOffset()));
        dTextField.setText(String.valueOf(filter.getD()));
    }
    
    public void updateUI()
    {
        updateKernelUI();
        updatePivotUI();
        updateSettingsUI();
    }
    
    private void readFromUI()
    {
        EdgeMode edgeMode = (EdgeMode)edgeModeToggleGroup.getSelectedToggle().getUserData();
        int d = Integer.parseInt(dTextField.getText());
        int offset = Integer.parseInt(offsetTextField.getText());
                
        filter.setEdgeMode(edgeMode);
        filter.setD(d);
        filter.setOffset(offset);
    }
    
    private void apply()
    {
        readFromUI();
        
        BufferedImage originalImage = AppModel.getInstance().getOriginalImage().getValue();
        BufferedImage filteredImage = filter.apply(originalImage);
        
        AppModel.getInstance().getFilteredImage().setValue(filteredImage);
    }
    
    private void reset()
    {
        filter = originalFilter.clone();
        resizeController.setFilter(filter);
        updateUI();
    }
    
    
    private class TextChangeHandler implements EventHandler<KeyEvent>
    {
        @Override
        public void handle(KeyEvent event)
        {
            TextField textField = (TextField)event.getTarget();
            try
            {
                int value = Integer.parseInt(textField.getText());
                int index = (int)textField.getUserData();
            
                filter.setValueWithIndex(value, index);
            }
            catch(NumberFormatException e)
            {
                if(!textField.getText().equals("-") && textField.getText().length() != 0)
                {
                    textField.setText("0");
                }
            }
        }
    }
    
    private class PivotChangeHandler implements ChangeListener<Toggle>
    {
        @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue)
        {
            int pivotIndex = (int)newValue.getUserData();
            filter.setPivotIndex(pivotIndex);
        }
    }
    
    private class ApplyButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            apply();
        }
    }
    
    private class ResetButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            reset();
        }
    }
}
