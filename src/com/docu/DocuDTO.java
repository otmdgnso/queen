package com.docu;

public class DocuDTO {
 private int docuNum,docuHitCount, listDocuNum, docuRecomm;
 private String  docuSubject,docuContent;
 private String docuCreated;
 private String memId;
 private String docuFile;
 private String originalFilename;
 private long fileSize;
public int getDocuNum() {
	return docuNum;
}
public void setDocuNum(int docuNum) {
	this.docuNum = docuNum;
}
public int getDocuHitCount() {
	return docuHitCount;
}
public void setDocuHitCount(int docuHitCount) {
	this.docuHitCount = docuHitCount;
}
public int getListDocuNum() {
	return listDocuNum;
}
public void setListDocuNum(int listDocuNum) {
	this.listDocuNum = listDocuNum;
}
public String getDocuSubject() {
	return docuSubject;
}
public void setDocuSubject(String docuSubject) {
	this.docuSubject = docuSubject;
}
public String getDocuContent() {
	return docuContent;
}
public void setDocuContent(String docuContent) {
	this.docuContent = docuContent;
}
public String getDocuCreated() {
	return docuCreated;
}
public void setDocuCreated(String docuCreated) {
	this.docuCreated = docuCreated;
}
public String getMemId() {
	return memId;
}
public void setMemId(String memId) {
	this.memId = memId;
}
public String getDocuFile() {
	return docuFile;
}
public void setDocuFile(String docuFile) {
	this.docuFile = docuFile;
}
public int getDocuRecomm() {
	return docuRecomm;
}
public void setDocuRecomm(int docuRecomm) {
	this.docuRecomm = docuRecomm;
}
public String getOriginalFilename() {
	return originalFilename;
}
public void setOriginalFilename(String originalFilename) {
	this.originalFilename = originalFilename;
}
public long getFileSize() {
	return fileSize;
}
public void setFileSize(long fileSize) {
	this.fileSize = fileSize;
}

 
 
}
