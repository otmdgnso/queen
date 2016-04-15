package com.qna;

public class AnswerDTO {
	private int a_num, qnaNum, answerchoose;
	String a_content, a_created, memId, createId;

	public int getAnswerchoose() {
		return answerchoose;
	}

	public void setAnswerchoose(int answerchoose) {
		this.answerchoose = answerchoose;
	}

	
	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public int getA_num() {
		return a_num;
	}

	public void setA_num(int a_num) {
		this.a_num = a_num;
	}

	public int getQnaNum() {
		return qnaNum;
	}

	public void setQnaNum(int qnaNum) {
		this.qnaNum = qnaNum;
	}

	public String getA_content() {
		return a_content;
	}

	public void setA_content(String a_content) {
		this.a_content = a_content;
	}

	public String getA_created() {
		return a_created;
	}

	public void setA_created(String a_created) {
		this.a_created = a_created;
	}

	public String getMemId() {
		return memId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}
}
