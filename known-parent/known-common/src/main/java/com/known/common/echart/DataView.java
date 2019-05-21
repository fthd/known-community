package com.known.common.echart;
public class DataView
{
    private boolean show = true;

    private boolean readOnly = false;

    public void setShow(boolean show){
        this.show = show;
    }
    public boolean getShow(){
        return this.show;
    }
    public void setReadOnly(boolean readOnly){
        this.readOnly = readOnly;
    }
    public boolean getReadOnly(){
        return this.readOnly;
    }
}


