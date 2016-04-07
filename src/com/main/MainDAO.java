package com.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class MainDAO {
	private Connection conn =  DBConn.getConnection();
	
	public List<MainDTO> mainShare(){
		List<MainDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append(" SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM ( ");
			sb.append(" SELECT shareNum ,shareSubject FROM share ORDER BY shareNum DESC)tb, ");  
			sb.append("  (SELECT @rownum:=0) T)tb1 WHERE rnum >= 1 and rnum <= 5");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				MainDTO dto = new MainDTO();
				
				dto.setShareNum(rs.getInt("shareNum"));
				dto.setShareSubject(rs.getString("shareSubject"));
				
				list.add(dto);				
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			
			if(pstmt!=null){
				try{
					pstmt.close();
				} catch (SQLException e){
				}
			}
		}
		return list;
	}
}
