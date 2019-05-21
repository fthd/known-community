package com.known.common.echart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Echart
{
    private Title title;

    private Tooltip tooltip = new Tooltip();

    private Legend legend;

    private Toolbox toolbox = new Toolbox();

    private boolean calculable = true;
    
    @JsonProperty
    private List<XAxis> xAxis = new ArrayList<XAxis>();

    @JsonProperty
    private List<YAxis> yAxis = new ArrayList<YAxis>();

    private List<Series> series;
    
    
    public Echart() {
		
	}
    
    public Echart(String title, String[] legend, XAxis xAxis, List<Series> series){
    	Title t = new Title();
    	t.setText(title);
    	this.title = t;
    	YAxis yAxis = new YAxis();
    	this.yAxis.add(yAxis);
    	Legend l = new Legend();
    	l.setData(Arrays.asList(legend));
    	this.legend = l;
    	this.xAxis.add(xAxis);
    	this.series = series;
    }

    public void setTitle(Title title){
        this.title = title;
    }
    public Title getTitle(){
        return this.title;
    }
    public void setTooltip(Tooltip tooltip){
        this.tooltip = tooltip;
    }
    public Tooltip getTooltip(){
        return this.tooltip;
    }
    public void setLegend(Legend legend){
        this.legend = legend;
    }
    public Legend getLegend(){
        return this.legend;
    }
    public void setToolbox(Toolbox toolbox){
        this.toolbox = toolbox;
    }
    public Toolbox getToolbox(){
        return this.toolbox;
    }
    public void setCalculable(boolean calculable){
        this.calculable = calculable;
    }
    public boolean getCalculable(){
        return this.calculable;
    }
    
    @JsonIgnore
    public void setXAxis(List<XAxis> xAxis){
        this.xAxis = xAxis;
    }
    
    @JsonIgnore
    public List<XAxis> getXAxis(){
        return this.xAxis;
    }
    
    @JsonIgnore
    public void setYAxis(List<YAxis> yAxis){
        this.yAxis = yAxis;
    }
    
    @JsonIgnore
    public List<YAxis> getYAxis(){
        return this.yAxis;
    }
    public void setSeries(List<Series> series){
        this.series = series;
    }
    public List<Series> getSeries(){
        return this.series;
    }
}
