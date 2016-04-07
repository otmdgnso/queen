	package com.portfolio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class PortfolioDAO {
private Connection conn=DBConn.getConnection();
	
	public int insertPortfolio(PortfolioDTO dto) {
		int result=0;
		
		PreparedStatement pstmt=null;
		String sql;

		String fields = "subject, content, imageFilename, memId";
		sql="INSERT INTO portfolio (" + fields + ") VALUES (?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getImageFilename());
			pstmt.setString(4, dto.getMemId());
			
			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT IFNULL(COUNT(*), 0) FROM portfolio";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);
			rs.close();
			pstmt.close();
			
			rs=null;
			pstmt=null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public List<PortfolioDTO> listPortfolio(int start, int end) {
		List<PortfolioDTO> list=new ArrayList<PortfolioDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append("    SELECT Num , memId, subject, content, imageFilename FROM portfolio ORDER BY num DESC)tb,  ");
			sb.append("       (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				PortfolioDTO dto=new PortfolioDTO();
				dto.setNum(rs.getInt("Num"));
				dto.setMemId(rs.getString("memId"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setImageFilename(rs.getString("imageFilename"));

				list.add(dto);
			}
			rs.close();
			pstmt.close();
			

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return list;
	}
	
	public PortfolioDTO readPortfolio(int num) {
		PortfolioDTO dto=null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();

		try {
			sb.append("SELECT Num, memId, Subject, Content, ");
			sb.append("    DATE_FORMAT(Created , '%Y-%m-%d') Created , ");
			sb.append("    imageFilename   FROM portfolio ");
			sb.append("    WHERE num=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new PortfolioDTO();
				dto.setNum(rs.getInt("num"));
				dto.setMemId(rs.getString("memId"));
				dto.setSubject(rs.getString("Subject"));
				dto.setContent(rs.getString("Content"));
				dto.setCreated(rs.getString("Created"));
				dto.setImageFilename(rs.getString("imageFilename"));
			}
			rs.close();
			pstmt.close();
					
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
	public int updatePortfolio(PortfolioDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("UPDATE portfolio SET subject=?");
			sb.append("   ,content=?, imageFilename=? ");
			sb.append("   WHERE Num=?");
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getImageFilename());
			pstmt.setInt(5, dto.getNum());
			
			result=pstmt.executeUpdate();
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public int deletePortfolio(int num) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="DELETE FROM portfolio WHERE num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result=pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
}
