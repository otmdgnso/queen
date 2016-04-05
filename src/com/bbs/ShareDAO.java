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
		sb.append("insert into share (shareNum, shareSubject, ");
		sb.append(" shareContent,shareCreated, shareHitCount,");
		sb.append(" shareModified, memId)");
		sb.append(" values (?,?,?,?,?,?,?,?);");
		pstmt=conn.prepareStatement(sb.toString());
		pstmt.setInt(1, dto.getShareNum());
		pstmt.setString(2, dto.getShareContent());
		pstmt.setString(3, dto.getShareCreated());
		
	
	} catch (Exception e) {
        System.out.println(e.toString());
	}
	  
	  return result;
  }
  
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
