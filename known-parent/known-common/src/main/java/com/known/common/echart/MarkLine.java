package com.known.common.echart;
import java.util.ArrayList;
import java.util.List;

public class MarkLine
{
    private List<Data> data;

    public MarkLine() {
		data = new ArrayList<Data>();
		Data d = new Data();
		d.setType("average");
		d.setName("平均值");
		data.add(d);
	}
    
    public void setData(List<Data> data){
        this.data = data;
    }
    public List<Data> getData(){
        return this.data;
    }
}


