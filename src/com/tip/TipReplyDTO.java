package com.tip;

public class TipReplyDTO {

	int tipR_num, tipNum;
	String tipR_content, tipR_created, memId;
	
	public int getTipR_num() {
		return tipR_num;
	}
	public void setTipR_num(int tipR_num) {
		this.tipR_num = tipR_num;
	}
	public int getTipNum() {
		return tipNum;
	}
	public void setTipNum(int tipNum) {
		this.tipNum = tipNum;
	}
	public String getTipR_content() {
		return tipR_content;
	}
	public void setTipR_content(String tipR_content) {
		this.tipR_content = tipR_content;
	}
	public String getTipR_created() {
		return tipR_created;
	}
	public void setTipR_created(String tipR_created) {
		this.tipR_created = tipR_created;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	
	
}
