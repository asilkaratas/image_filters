/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagefilters.view;

import com.imagefilters.filters.convolutionfilters.ConvolutionFilter;
import com.imagefilters.filters.functionfilters.FunctionFilter;
import com.imagefilters.filters.gammafilters.GammaFilter;
import com.imagefilters.filters.FilterFactory;
import com.imagefilters.filters.ImageFilter;
import com.imagefilters.filters.ImageFilterGroup;
import com.imagefilters.filters.colorquantizationfilter.ColorQuantizationFilter;
import com.imagefilters.filters.ditheringfilters.OrderedDitheringFilter;
import com.imagefilters.filters.thresholdfilters.ThresholdFilter;
import com.imagefilters.model.AppModel;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author asilkaratas
 */
public class FiltersView extends StackPane
{
    private ComboBox<Object> chooseFilterComboBox;
    private VBox filterVBox;
    private VBox filterGroupVBox;
    
    public FiltersView()
    {
        //ChooseFilterType
        VBox filterFactoryVBox = new VBox();
        filterFactoryVBox.setPrefWidth(250.0);
        filterFactoryVBox.setSpacing(5.0);
        
        ToggleGroup filterFactoryGroup = new ToggleGroup();
        filterFactoryGroup.selectedToggleProperty().addListener(new FilterTypeChangeListener());
        
        ArrayList<FilterFactory> filterFactories = FilterFactory.getFactories();
        for(FilterFactory filterFactory : filterFactories)
        {
            RadioButton filterFactoryButton = new RadioButton(filterFactory.toString());
            filterFactoryButton.setUserData(filterFactory);
            filterFactoryButton.setToggleGroup(filterFactoryGroup);
            
            filterFactoryVBox.getChildren().add(filterFactoryButton);
        }
        
        BorderedTitledPane filterTypePane = new BorderedTitledPane("choose filter type", filterFactoryVBox);
        
        //ChooseFilter
        chooseFilterComboBox = new ComboBox();
        chooseFilterComboBox.valueProperty().addListener(new FilterChangeListener());
        
        filterGroupVBox = new VBox();
        filterGroupVBox.setPadding(new Insets(5.0, 0.0, 0.0, 0.0));
        filterGroupVBox.setSpacing(5.0);
        
        VBox chooseFilterVBox = new VBox();
        chooseFilterVBox.setPrefWidth(250.0);
        chooseFilterVBox.getChildren().addAll(chooseFilterComboBox, filterGroupVBox);
        
        BorderedTitledPane chooseFilterPane = new BorderedTitledPane("choose filter", chooseFilterVBox);
        
        
        filterVBox = new VBox();
        
        BorderedTitledPane filterPane = new BorderedTitledPane("filter", filterVBox);
        
        HBox hBox = new HBox();
        hBox.getChildren().addAll(filterTypePane, chooseFilterPane);
        
        //Filters
        VBox filtersVBox = new VBox();
        filtersVBox.getChildren().addAll(hBox, filterPane);
        
        BorderedTitledPane filtersPane = new BorderedTitledPane("filters", filtersVBox);
        getChildren().add(filtersPane);
       
        //InitialSettings
        //convolutionFiltersButton.setSelected(true);
        
        int lastIndex = filterFactoryVBox.getChildren().size() - 1;
        RadioButton selectedButton = (RadioButton)filterFactoryVBox.getChildren().get(lastIndex);
        selectedButton.setSelected(true);
        
        AppModel.getInstance().getOriginalImage().addListener(new OriginalImageChangeHandler());
        
        updateUI();
    }
    
    private void setFilterFactory(FilterFactory filterFactory)
    {
        ArrayList<Object> filters = filterFactory.getFilters();
        
        chooseFilterComboBox.getItems().clear();
        chooseFilterComboBox.getItems().addAll(filters);
        chooseFilterComboBox.getSelectionModel().select(0);
    }
    
    private void setImageFilterGroup(ImageFilterGroup imageFilterGroup)
    {
        filterGroupVBox.getChildren().clear();
        
        if(imageFilterGroup != null && imageFilterGroup.getFilters().size() > 0)
        {
            filterGroupVBox.setVisible(true);
            ToggleGroup toggleGroup = new ToggleGroup();
            toggleGroup.selectedToggleProperty().addListener(new RadioFilterChangeListener());
            for(ImageFilter filter:imageFilterGroup.getFilters())
            {
               RadioButton radioButton = new RadioButton(filter.getName());
               radioButton.setUserData(filter);
               radioButton.setToggleGroup(toggleGroup);
               filterGroupVBox.getChildren().add(radioButton);
            } 
            
            RadioButton radioButton = (RadioButton)filterGroupVBox.getChildren().get(0);
            radioButton.setSelected(true);
        }
        else
        {
            filterGroupVBox.setVisible(false);
        }
        
    }
    
    private void setFilter(Object filter)
    {
        filterVBox.getChildren().clear();
        
        if(filter instanceof FunctionFilter)
        {
            FunctionFilterView filterView = new FunctionFilterView((FunctionFilter)filter);
            filterVBox.getChildren().add(filterView);
        }
        else if(filter instanceof ConvolutionFilter)
        {
            ConvolutionFilterView filterView = new ConvolutionFilterView((ConvolutionFilter)filter);
            filterVBox.getChildren().add(filterView);
        }
        else if(filter instanceof GammaFilter)
        {
            GammaFilterView filterView = new GammaFilterView((GammaFilter)filter);
            filterVBox.getChildren().add(filterView);
        }
        else if(filter instanceof ThresholdFilter)
        {
            ThresholdFilterView filterView = new ThresholdFilterView((ThresholdFilter)filter);
            filterVBox.getChildren().add(filterView);
        }
        else if(filter instanceof OrderedDitheringFilter)
        {
            OrderedDitheringFilterView filterView = new OrderedDitheringFilterView((ImageFilter)filter);
            filterVBox.getChildren().add(filterView);
        }
        else if(filter instanceof ColorQuantizationFilter)
        {
            UniformColorQuantizationFilterView filterView = new UniformColorQuantizationFilterView((ImageFilter)filter);
            filterVBox.getChildren().add(filterView);
        }
        else 
        {
            ImageFilterView filterView = new ImageFilterView((ImageFilter)filter);
            filterVBox.getChildren().add(filterView);
        }
        
    }
    
    private void updateUI()
    {
        Boolean disable = !AppModel.getInstance().hasImage();
            
        this.setDisable(disable);
    }
    
    private class FilterTypeChangeListener implements ChangeListener<Toggle>
    {
        @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue)
        {
            FilterFactory filterFactory = (FilterFactory)newValue.getUserData();
            setFilterFactory(filterFactory);
        }
    }
    
    private class FilterChangeListener implements ChangeListener<Object>
    {
        @Override
        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue)
        {
            AppModel.getInstance().getFilteredImage().setValue(null);
            
            if(newValue != null)
            {
                if(newValue instanceof ImageFilterGroup)
                {
                    ImageFilterGroup group = (ImageFilterGroup)newValue;
                    setImageFilterGroup(group);
                }
                else
                {
                    setImageFilterGroup(null);
                    setFilter(newValue);
                }
            }
            
        }
    }
    
    private class RadioFilterChangeListener implements ChangeListener<Toggle>
    {
        @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue)
        {
            ImageFilter filter = (ImageFilter)newValue.getUserData();
            setFilter(filter);
        }
    }
    
    private class OriginalImageChangeHandler implements ChangeListener<BufferedImage>
    {
        @Override
        public void changed(ObservableValue<? extends BufferedImage> observable, BufferedImage oldValue, BufferedImage newValue)
        {
            updateUI();
        }
    }
}
