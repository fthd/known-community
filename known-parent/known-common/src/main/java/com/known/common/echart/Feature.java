package com.known.common.echart;
public class Feature
{
    private Mark mark;

    private DataView dataView;

    private MagicType magicType;

    private Restore restore;

    private SaveAsImage saveAsImage;
    
    public Feature() {
    	 mark = new Mark();
    	 restore = new Restore();
    	 dataView = new DataView();
    	 magicType = new MagicType();
    	 saveAsImage = new SaveAsImage();
	}

    public void setMark(Mark mark){
        this.mark = mark;
    }
    public Mark getMark(){
        return this.mark;
    }
    public void setDataView(DataView dataView){
        this.dataView = dataView;
    }
    public DataView getDataView(){
        return this.dataView;
    }
    public void setMagicType(MagicType magicType){
        this.magicType = magicType;
    }
    public MagicType getMagicType(){
        return this.magicType;
    }
    public void setRestore(Restore restore){
        this.restore = restore;
    }
    public Restore getRestore(){
        return this.restore;
    }
    public void setSaveAsImage(SaveAsImage saveAsImage){
        this.saveAsImage = saveAsImage;
    }
    public SaveAsImage getSaveAsImage(){
        return this.saveAsImage;
    }
}


