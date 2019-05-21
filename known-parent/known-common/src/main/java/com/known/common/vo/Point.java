package com.known.common.vo;
/**
 * 
 * @author Administrator
 *
 */
public class Point {
	
	private Data data;

    private int error;

    private String msg;

    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }
    public void setError(int error){
        this.error = error;
    }
    public int getError(){
        return this.error;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
	@Override
	public String toString() {
		return "Point [data=" + data + ", error=" + error + ", msg=" + msg + "]";
	}
    
    
    
}


