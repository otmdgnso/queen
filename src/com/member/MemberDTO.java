package com.member;

public class MemberDTO {
	private String memId,memNick, memName, memPwd, memCreated, memModify,memCourse;
	private String email,email1, email2;
	private String tel, tel1, tel2, tel3;
	private String birth, job;
	private String zip, addr1, addr2;
	private int enabled;
	//member1:  memId,memNick, memName, memPwd, memCreated, memModify,memCourse, enabled; 
	//member2:	memId, birth DATE,	email VARCHAR(50),tel VARCHAR(20) ,	job	VARCHAR(50),zip	VARACHAR(7),
	//			addr1 	VARCHAR(50), add2  	VARCHAR(50)

	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemNick() {
		return memNick;
	}
	public void setMemNick(String memNick) {
		this.memNick = memNick;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemPwd() {
		return memPwd;
	}
	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
	}
	public String getMemCreated() {
		return memCreated;
	}
	public void setMemCreated(String memCreated) {
		this.memCreated = memCreated;
	}
	public String getMemModify() {
		return memModify;
	}
	public void setMemModify(String memModify) {
		this.memModify = memModify;
	}
	public String getMemCourse() {
		return memCourse;
	}
	public void setMemCourse(String memCourse) {
		this.memCourse = memCourse;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getTel3() {
		return tel3;
	}
	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
}
