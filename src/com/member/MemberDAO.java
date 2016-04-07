package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;

public class MemberDAO {
	private Connection conn=DBConn.getConnection();
	
	public MemberDTO readMember(String memId) {
		
		MemberDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		System.out.println("dao 작업중..");
		try {
			sb.append("SELECT m1.memId, memName, memPwd,");
			sb.append("      enabled, memCreated, memModified,");
			sb.append("      DATE_FORMAT(birth, '%Y-%m-%d') birth, ");
			sb.append("      email, tel, job,");
			sb.append("      add2");
			sb.append("      FROM member1 m1");
			sb.append("      LEFT OUTER JOIN member2 m2 ");
			sb.append("      ON m1.memId=m2.memId");
			sb.append("      WHERE m1.memId=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, memId);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				
				dto=new MemberDTO();
				dto.setMemId(rs.getString("memId"));
				dto.setMemPwd(rs.getString("memPwd"));
				dto.setMemName(rs.getString("memName"));
				dto.setEnabled(rs.getInt("enabled"));
				dto.setMemCreated(rs.getString("memCreated"));
				dto.setMemModify(rs.getString("memModified"));
				dto.setBirth(rs.getString("birth"));
				
				dto.setTel(rs.getString("tel"));
				if(dto.getTel()!=null) {
					String[] ss=dto.getTel().split("-");
					if(ss.length==3) {
						dto.setTel1(ss[0]);
						dto.setTel2(ss[1]);
						dto.setTel3(ss[2]);
					}
				}
				
				dto.setEmail(rs.getString("email"));
				if(dto.getEmail()!=null) {
					String[] ss=dto.getEmail().split("@");
					if(ss.length==2) {
						dto.setEmail1(ss[0]);
						dto.setEmail2(ss[1]);
					}
				}
				dto.setJob(rs.getString("job"));
				dto.setAddr2(rs.getString("add2"));
			}
			rs.close();
			pstmt.close();
			pstmt=null;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("성공! :"+dto.getMemId());
		System.out.println("성공! :"+dto.getMemName());
		return dto;
	}
	
	public int insertMember(MemberDTO dto) { 
		int result=0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		//member1: memId,memName, memPwd, memCreated, memModify,memCourse, enabled, //닉네임 삭제함 ; 
		try {
			sb.append("INSERT INTO member1(memId, memPwd, memName,memCourse) VALUES (?, ?, ?,?)");
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getMemId());
			pstmt.setString(2, dto.getMemPwd());
			pstmt.setString(3, dto.getMemName());
			pstmt.setString(4, dto.getMemCourse());
			
			result=pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
			// sb=new StringBuffer();
			sb.delete(0, sb.length());
			
			// member2
			sb.append("INSERT INTO member2(memId, birth, email, tel, job, add2) VALUES (?, ?, ?, ?, ?, ?)");
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getMemId());
			pstmt.setString(2, dto.getBirth());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getTel());
			pstmt.setString(5, dto.getJob());
			pstmt.setString(6, dto.getAddr2());
			
			pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	/*
	public int updateMember(MemberDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("UPDATE member1 SET userPwd=?, modify_date=SYSDATE  WHERE userId=?");
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getUserPwd());
			pstmt.setString(2, dto.getUserId());
			
			result=pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
			sb.delete(0, sb.length());
			
			sb.append("UPDATE member2 SET birth=?, email=?, tel=?, job=?, zip=?, addr1=?, addr2=? WHERE userId=?");
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getBirth());
			pstmt.setString(2, dto.getEmail());
			pstmt.setString(3, dto.getTel());
			pstmt.setString(4, dto.getJob());
			pstmt.setString(5, dto.getZip());
			pstmt.setString(6, dto.getAddr1());
			pstmt.setString(7, dto.getAddr2());
			pstmt.setString(8, dto.getUserId());
			
			pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public int deleteMember(String userId) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="UPDATE member1 SET enabled=0 WHERE userId=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			result=pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
			sql="DELETE FROM  member2 WHERE userId=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			result=pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
		} catch (Exception e) {
			System.out.println();
		}
		
		return result;
	}*/

}
