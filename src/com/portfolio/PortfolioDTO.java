package com.portfolio;

public class PortfolioDTO {
	private int num, recomm, hitCount, imgCnt;
	
	private String subject, content, created, memId;
	private String imageFilename, imageFilename2,imageFilename3,imageFilename4;
	
	public int getImgCnt() {
		return imgCnt;
	}
	public void setImgCnt(int imgCnt) {
		this.imgCnt = imgCnt;
	}
	public String getImageFilename2() {
		return imageFilename2;
	}
	public void setImageFilename2(String imageFilename2) {
		this.imageFilename2 = imageFilename2;
	}
	public String getImageFilename3() {
		return imageFilename3;
	}
	public void setImageFilename3(String imageFilename3) {
		this.imageFilename3 = imageFilename3;
	}
	public String getImageFilename4() {
		return imageFilename4;
	}
	public void setImageFilename4(String imageFilename4) {
		this.imageFilename4 = imageFilename4;
	}
	public String getImageFilename() {
		return imageFilename;
	}
	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getRecomm() {
		return recomm;
	}
	public void setRecomm(int recomm) {
		this.recomm = recomm;
	}
	public int getHitCount() {
		return hitCount;
	}
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}

	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	
	
}
