package com.known.common.echart;
import java.util.ArrayList;
import java.util.List;

public class MagicType
{
    private boolean show = true;

    private List<String> type;
    
    public MagicType() {
		type = new ArrayList<String>();
		type.add("line");
		type.add("bar");
	}

    public void setShow(boolean show){
        this.show = show;
    }
    public boolean getShow(){
        return this.show;
    }
    public void setType(List<String> type){
        this.type = type;
    }
    public List<String> getType(){
        return this.type;
    }
}

