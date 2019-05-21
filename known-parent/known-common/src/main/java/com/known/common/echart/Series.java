package com.known.common.echart;
import java.util.List;

public class Series
{
    private String name;

    private String type = "line";

    private List<Integer> data;

    private MarkPoint markPoint = new MarkPoint();

    private MarkLine markLine = new MarkLine();
    
    public Series() {
		
	}
    
    public Series(String name, List<Integer> data) {
		this.name = name;
		this.data = data;
   	}

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    
    
	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}

	public void setMarkPoint(MarkPoint markPoint){
        this.markPoint = markPoint;
    }
    public MarkPoint getMarkPoint(){
        return this.markPoint;
    }
    public void setMarkLine(MarkLine markLine){
        this.markLine = markLine;
    }
    public MarkLine getMarkLine(){
        return this.markLine;
    }
}


