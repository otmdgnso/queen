package com.bbs;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class DocuDAO {
  private Connection conn=DBConn.getConnection();
  
  
 //데이터 추가
  public int insertDocu(DocuDTO dto){
	  int result=0;
	  PreparedStatement pstmt=null;
	  StringBuffer sb=new StringBuffer();
	  
	  try {
		sb.append("insert into docu (docuNum, docuSubject, ");
		sb.append(" docuContent,docuCreated, docuHitCount,");
		sb.append(" docuRecomm,docuFile, memId)");
		sb.append(" values (?,?,?,?,?,?,?,?);");
		pstmt=conn.prepareStatement(sb.toString());
		pstmt.setInt(1, dto.getDocuNum());
		pstmt.setString(2, dto.getDocuSubject());
		pstmt.setString(3, dto.getDocuContent());
		
	
	} catch (Exception e) {
        System.out.println(e.toString());
	}
	  
	  return result;
  }
  
  public List<DocuDTO> listDocu(){
	  List<DocuDTO> list=new ArrayList<>();
	  PreparedStatement pstmt=null;
	  ResultSet rs=null;
	  StringBuffer sb=new StringBuffer();
	  
	  try {
		sb.append("SELECT docuNum, docuSubject,memId,");
		sb.append(" docuCreated, docuHitCount FROM docu");
		
		pstmt =conn.prepareStatement(sb.toString());
		
		rs=pstmt.executeQuery();
		
		while(rs.next()){
			DocuDTO dto=new DocuDTO();
			
			dto.setDocuNum(rs.getInt("docuNum"));
			dto.setDocuSubject(rs.getString("docuSubject"));
			dto.setMemId(rs.getString("memId"));
			dto.setDocuCreated(rs.getString("docuCreated"));
			dto.setDocuHitCount(rs.getInt("docuHitCount"));
			
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
