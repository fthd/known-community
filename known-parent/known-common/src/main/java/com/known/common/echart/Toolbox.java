package com.known.common.echart;
public class Toolbox
{
    private boolean show = true;

    private Feature feature;
    
    public Toolbox() {
		feature = new Feature();
	}

    public void setShow(boolean show){
        this.show = show;
    }
    public boolean getShow(){
        return this.show;
    }
    public void setFeature(Feature feature){
        this.feature = feature;
    }
    public Feature getFeature(){
        return this.feature;
    }
}


