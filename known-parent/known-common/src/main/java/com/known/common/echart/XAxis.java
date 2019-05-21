package com.known.common.echart;
import java.util.List;

public class XAxis
{
    private String type = "category";

    private boolean boundaryGap = false;

    private List<String> data;

    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setBoundaryGap(boolean boundaryGap){
        this.boundaryGap = boundaryGap;
    }
    public boolean getBoundaryGap(){
        return this.boundaryGap;
    }
    public void setData(List<String> data){
        this.data = data;
    }
    public List<String> getData(){
        return this.data;
    }
}

