package com.known.common.echart;
import java.util.ArrayList;
import java.util.List;

public class MarkPoint
{
    private List<Data> data;
    
    public MarkPoint() {
    	data = new ArrayList<Data>();
		Data d = new Data();
		d.setType("max");
		d.setName("最大值");
		data.add(d);
		d = new Data();
		d.setType("min");
		d.setName("最小值");
		data.add(d);
	}

    public void setData(List<Data> data){
        this.data = data;
    }
    public List<Data> getData(){
        return this.data;
    }
}


