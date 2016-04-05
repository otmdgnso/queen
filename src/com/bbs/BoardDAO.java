package com.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class BoardDAO {
	private Connection conn=DBConn.getConnection();
	
	public List<BoardDTO> listBoard() {
		List<BoardDTO> list=new ArrayList<>();
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		ResultSet rs=null;
		
		try {
			sb.append(" SELECT num, subject, b.userid, username, hitcount,");
			sb.append(" TO_CHAR(created,'YYYY-MM-DD') created");
			sb.append(" FROM bbs b");
			sb.append(" JOIN member1 m1 ON m1.userid=b.userid");
			sb.append(" ORDER BY num DESC");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			
		}
		
		return list;
	}
}
