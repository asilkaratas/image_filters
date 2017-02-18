/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.view;

import com.imagefilters.filters.functionfilters.FunctionFilterOperation;
import com.imagefilters.filters.functionfilters.FilterPoint;
import com.imagefilters.filters.functionfilters.FunctionFilter;
import com.imagefilters.model.AppModel;
import com.imagefilters.model.enums.DragMode;
import java.awt.image.BufferedImage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author asilkaratas
 */
public class FunctionFilterView extends VBox
{
    private FunctionFilter originalFilter;
    private FunctionFilter filter;
    private double orgSceneX;
    private double orgSceneY;
    private double orgCenterX;
    private double orgCenterY;
    
    private Pane pointsPane;
    private Pane linesPane;
    
    public FunctionFilterView(FunctionFilter filter)
    {
        originalFilter = filter;
        this.filter = filter.clone();
        
        Pane graphPane = createGraphPane();
        getChildren().add(graphPane);
        
        HBox graphBox = new HBox();
        graphBox.setAlignment(Pos.CENTER);
        graphBox.getChildren().add(graphPane);
        
        BorderedTitledPane graphTitledPane = new BorderedTitledPane("graph", graphBox);
        
        Button applyButton = new Button("apply");
        applyButton.setOnAction(new ApplyButtonHandler());
        
        Button resetButton = new Button("reset");
        resetButton.setOnAction(new ResetButtonHandler());
        
        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(5.0);
        buttonsBox.getChildren().addAll(applyButton, resetButton);
        
        BorderedTitledPane applyPane = new BorderedTitledPane("apply", buttonsBox);
        getChildren().addAll(graphTitledPane, applyPane);
        
        createPointsAndLines();
    }
    
    private Pane createGraphPane()
    {
        Line horizontalLine = new Line(0, 0, 255, 0);
        
        Group horizontalGroup = new Group();
        horizontalGroup.setTranslateY(255);
        horizontalGroup.getChildren().add(horizontalLine);
        
        for(int i = 5; i <= 255; i+= 5)
        {
            int lineHeight = (i%10) == 0 ? 5 : 3;
            Line line = new Line(i, 0, i, lineHeight);
            horizontalGroup.getChildren().add(line);
        }
        
        
        Line verticalLine = new Line(0, 0, 0, 255);
        
        Group verticalGroup = new Group();
        verticalGroup.getChildren().add(verticalLine);
        
        for(int i = 5; i <= 255; i+= 5)
        {
            int lineWidth = (i%10) == 0 ? 5 : 3;
            int yPos = 255-i;
            Line line = new Line(0, yPos, -lineWidth, yPos);
            verticalGroup.getChildren().add(line);
        }
        
        Group group = new Group();
        group.getChildren().addAll(verticalGroup, horizontalGroup);
        
        linesPane = new Pane();
        pointsPane = new Pane();
        
        Pane graphPane = new Pane();
        graphPane.setPrefSize(300.0, 300.0);
        graphPane.getChildren().addAll(group, linesPane, pointsPane);
        
        graphPane.setOnMouseClicked(new DoubleClickHandler());
        
        return graphPane;
    }
    
    private void createPointsAndLines()
    {
        pointsPane.getChildren().clear();
        linesPane.getChildren().clear();
        
        Circle prevCircle = null;
        for(FilterPoint filterPoint : filter.getFilterPoints())
        {
            Circle circle = new Circle(filterPoint.getX(), 255-filterPoint.getY(), 4, Color.RED);
            circle.setCursor(Cursor.HAND);
            circle.setOnMousePressed(new MousePressedHandler());
            circle.setOnMouseDragged(new MouseDraggedHandler());
            circle.setUserData(filterPoint);
            pointsPane.getChildren().add(circle);
            
            if(prevCircle != null)
            {
                Line line = new Line(prevCircle.getCenterX(), prevCircle.getCenterY(), 
                                     circle.getCenterX(), circle.getCenterY());
                linesPane.getChildren().add(line);
            }
            
            prevCircle = circle;
        }
    }
    
    public void updateLines()
    {
        
        for(int i = 0; i < linesPane.getChildren().size(); ++i)
        {
            Circle startCircle = (Circle)pointsPane.getChildren().get(i);
            Circle endCircle = (Circle)pointsPane.getChildren().get(i + 1);
            Line line = (Line)linesPane.getChildren().get(i);
            
            line.setStartX(startCircle.getCenterX());
            line.setStartY(startCircle.getCenterY());
            line.setEndX(endCircle.getCenterX());
            line.setEndY(endCircle.getCenterY());
        }
    }
    
    private void apply()
    {
        BufferedImage originalImage = AppModel.getInstance().getOriginalImage().getValue();
        BufferedImage filteredImage = filter.apply(originalImage);
        
        AppModel.getInstance().getFilteredImage().setValue(filteredImage);
    }
    
    private void reset()
    {
        filter = originalFilter.clone();
        createPointsAndLines();
    }
    
    private class MousePressedHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            
            Circle circle = (Circle)event.getTarget();
            orgCenterX = circle.getCenterX();
            orgCenterY = circle.getCenterY();
        }
    }
    
    
    private class MouseDraggedHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            
            Circle circle = (Circle)event.getTarget();
            
            FilterPoint filterPoint = (FilterPoint)circle.getUserData();
            
            System.out.println("filterPoint:" + filterPoint);
            
            double offsetX = event.getSceneX() - orgSceneX;
            double offsetY = event.getSceneY() - orgSceneY;
            
            double newCenterX = orgCenterX;
            double newCenterY = orgCenterY;
            
            if(filterPoint.canMoveOnX())
            {
                newCenterX = orgCenterX + offsetX;
            }
            
            if(filterPoint.canMoveOnY())
            {
                newCenterY = orgCenterY + offsetY;
            }
            
            newCenterX = newCenterX < 0 ? 0 : newCenterX;
            newCenterX = newCenterX > 255 ? 255 : newCenterX;
            
            newCenterY = newCenterY < 0 ? 0 : newCenterY;
            newCenterY = newCenterY > 255 ? 255 : newCenterY;
            
            
            int circleIndex = pointsPane.getChildren().indexOf(circle);
            if(circleIndex > 0)
            {
                int prevCircleIndex = circleIndex - 1;
                Circle prevCircle = (Circle)pointsPane.getChildren().get(prevCircleIndex);
                if(newCenterX <= prevCircle.getCenterX())
                {
                    newCenterX = prevCircle.getCenterX() + 1;
                }
            }
            
            if(circleIndex < pointsPane.getChildren().size() -1)
            {
                int nextCircleIndex = circleIndex + 1;
                Circle nextCircle = (Circle)pointsPane.getChildren().get(nextCircleIndex);
                if(newCenterX >= nextCircle.getCenterX())
                {
                    newCenterX = nextCircle.getCenterX() - 1;
                }
            }
            
            circle.setCenterX(newCenterX);
            circle.setCenterY(newCenterY);
            
            filterPoint.move(newCenterX, 255-newCenterY);
            
            updateLines();
            
            //System.out.println("newCenterX:" + newCenterX + " newCenterY:" + newCenterY);
                    
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
   
    
    private class DoubleClickHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            if(event.getClickCount() == 2)
            {
                if(event.getTarget() instanceof Circle)
                {
                    Circle circle = (Circle)event.getTarget();
                    FilterPoint filterPoint = (FilterPoint)circle.getUserData();
                
                    if(filterPoint.getDragMode() == DragMode.X_Y)
                    {
                        filter.removePoint(filterPoint);
                        createPointsAndLines();
                    }
                }
                else
                {
                    int centerX = (int)event.getX();
                    int centerY = (int)event.getY();

                    if(centerX > 0 && centerX < 255 && centerY > 0 && centerY < 255)
                    {
                        FilterPoint filterPoint = new FilterPoint(centerX, 255-centerY, DragMode.X_Y);
                        filter.addPoint(filterPoint);

                        createPointsAndLines();

                        System.out.println("DoubleClickHandler:" + event.getClickCount() + " target:" + event.getTarget());  
                    }
                }
                
            }
        }
        
    }
    
}
