package com.bbs;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ShareDAO {
  private Connection conn=DBConn.getConnection();
  
  
 //데이터 추가
  public int insertDocu(ShareDTO dto){
	  int result=0;
	  PreparedStatement pstmt=null;
	  StringBuffer sb=new StringBuffer();
	  
	  try {
		sb.append("INSERT INTO share(shareNum, memId, shareSubject, shareContent) ");
		sb.append(" VALUES (share_seq.NEXTVAL,?,?,?)");
		
		
		pstmt=conn.prepareStatement(sb.toString());
		pstmt.setString(1, dto.getMemId());
		pstmt.setString(2, dto.getShareSubject());
		pstmt.setString(3, dto.getShareContent());
		
		result=pstmt.executeUpdate();
	
	} catch (Exception e) {
        System.out.println(e.toString());
	} finally{
		if(pstmt!=null)
			try {
			   pstmt.close();	
			} catch (SQLException e) {
			  
			}
	}
	  return result;
  }
  
  //데이터 개수
  public int dataCount(){
	  int result=0;
	  PreparedStatement pstmt=null;
	  ResultSet rs=null;
	  String sql;
	  
	  try {
		sql=" SELECT IFNULL(COUNT(*), 0) FROM share";
		pstmt=conn.prepareStatement(sql);
		
		rs=pstmt.executeQuery();
		if(rs.next()){
			result=rs.getInt(1);
			
		}
	} catch (Exception e) {
		System.out.println(e.toString());
	}finally{
		if(rs!=null){
			try {
				rs.close();
			} catch (Exception e2) {				
			}
		}
		if(pstmt!=null){
			try {
			    pstmt.close();
			} catch (Exception e2) {			
			}
		}
	}
	  
	  return result;
  }
  
  
  //게시판 리스트
  public List<ShareDTO> listShare(){
	  List<ShareDTO> list=new ArrayList<>();
	  PreparedStatement pstmt=null;
	  ResultSet rs=null;
	  StringBuffer sb=new StringBuffer();
	  
	  try {
		sb.append("SELECT shareNum, shareSubject,memId,");
		sb.append(" shareCreated, shareHitCount FROM share");
		
		pstmt =conn.prepareStatement(sb.toString());
		
		rs=pstmt.executeQuery();
		
		while(rs.next()){
			ShareDTO dto=new ShareDTO();
			
			dto.setShareNum(rs.getInt("ShareNum"));
			dto.setShareSubject(rs.getString("ShareSubject"));
			dto.setMemId(rs.getString("memId"));
			dto.setShareCreated(rs.getString("ShareCreated"));
			dto.setShareHitCount(rs.getInt("ShareHitCount"));
			
			list.add(dto);
		}
	} catch (Exception e) {
		System.out.println(e.toString());
	}finally {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
			
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}
		}
	}
	  return list;
  }
}
