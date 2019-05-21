package com.known.common.echart;
public class YAxis
{
    private String type = "value";

    private AxisLabel axisLabel;
    
    public YAxis() {
    	axisLabel = new AxisLabel(); 	
    }
    


    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setAxisLabel(AxisLabel axisLabel){
        this.axisLabel = axisLabel;
    }
    public AxisLabel getAxisLabel(){
        return this.axisLabel;
    }
}


