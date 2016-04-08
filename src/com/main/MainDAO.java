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
		List<MainDTO> listShare = new ArrayList<>();
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
				
				listShare.add(dto);				
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
		return listShare;
	}

	public List<MainDTO> mainPortfolio(){
		List<MainDTO> listPfo = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append(" SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM ( ");
			sb.append(" SELECT Num ,Subject FROM portfolio ORDER BY Num DESC)tb, ");  
			sb.append("  (SELECT @rownum:=0) T)tb1 WHERE rnum >= 1 and rnum <= 5");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				MainDTO dto = new MainDTO();
				
				dto.setPfoNum(rs.getInt("Num"));
				dto.setPfoSubject(rs.getString("Subject"));
				
				listPfo.add(dto);				
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
		return listPfo;
	}
	
	public List<MainDTO> mainWanted(){
		List<MainDTO> listWanted = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append(" SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM ( ");
			sb.append(" SELECT wantedNum ,wantedSubject, wantedHead FROM wanted ORDER BY wantedNum DESC)tb, ");  
			sb.append("  (SELECT @rownum:=0) T)tb1 WHERE rnum >= 1 and rnum <= 5");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				MainDTO dto = new MainDTO();
				
				dto.setWantedNum(rs.getInt("wantedNum"));
				dto.setWantedSubject(rs.getString("wantedSubject"));
				dto.setWantedHead(rs.getString("wantedHead"));
				
				listWanted.add(dto);				
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
		return listWanted;
	}
	
	public List<MainDTO> mainResume(){
		List<MainDTO> listResume = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append(" SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM ( ");
			sb.append(" SELECT resumeNum ,resumeSubject FROM resume ORDER BY resumeNum DESC)tb, ");  
			sb.append("  (SELECT @rownum:=0) T)tb1 WHERE rnum >= 1 and rnum <= 5");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				MainDTO dto = new MainDTO();
				
				dto.setResumeNum(rs.getInt("resumeNum"));
				dto.setResumeSubject(rs.getString("resumeSubject"));
				
				listResume.add(dto);				
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
		return listResume;
	}
	
	public List<MainDTO> mainQuest(){
		List<MainDTO> listQuest = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append(" SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM ( ");
			sb.append(" SELECT questNum ,questSubject, questHead FROM question ORDER BY questNum DESC)tb, ");  
			sb.append("  (SELECT @rownum:=0) T)tb1 WHERE rnum >= 1 and rnum <= 5");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				MainDTO dto = new MainDTO();
				
				dto.setQuestNum(rs.getInt("questNum"));
				dto.setQuestHead(rs.getString("questHead"));
				dto.setQuestSubject(rs.getString("questSubject"));
				
				listQuest.add(dto);				
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
		return listQuest;
	}
	
	public List<MainDTO> mainTip(){
		List<MainDTO> listTip = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append(" SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM ( ");
			sb.append(" SELECT tipNum ,tipSubject, tipHead FROM tip ORDER BY tipNum DESC)tb, ");  
			sb.append("  (SELECT @rownum:=0) T)tb1 WHERE rnum >= 1 and rnum <= 5");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				MainDTO dto = new MainDTO();
				
				dto.setTipNum(rs.getInt("tipNum"));
				dto.setTipHead(rs.getString("tipHead"));
				dto.setTipSubject(rs.getString("tipSubject"));
				
				listTip.add(dto);				
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
		return listTip;
	}
	
	public List<MainDTO> mainCompany(){
		List<MainDTO> listCompany = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append(" SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM ( ");
			sb.append(" SELECT companyNum , companySubject FROM company ORDER BY companyNum DESC)tb, ");  
			sb.append("  (SELECT @rownum:=0) T)tb1 WHERE rnum >= 1 and rnum <= 5");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				MainDTO dto = new MainDTO();
				
				dto.setCompanyNum(rs.getInt("companyNum"));
				dto.setCompanySubject(rs.getString("companySubject"));
				
				listCompany.add(dto);				
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
		return listCompany;
	}
	
	public List<MainDTO> mainDocu(){
		List<MainDTO> listDocu = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append(" SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM ( ");
			sb.append(" SELECT docuNum , docuSubject FROM docu ORDER BY docuNum DESC)tb, ");  
			sb.append("  (SELECT @rownum:=0) T)tb1 WHERE rnum >= 1 and rnum <= 5");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				MainDTO dto = new MainDTO();
				
				dto.setDocuNum(rs.getInt("docuNum"));
				dto.setDocuSubject(rs.getString("docuSubject"));
				
				listDocu.add(dto);				
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
		return listDocu;
	}
	
	public List<MainDTO> mainTrend(){
		List<MainDTO> listTrend = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append(" SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM ( ");
			sb.append(" SELECT trendNum ,trendSubject, trendHead FROM trend ORDER BY trendNum DESC)tb, ");  
			sb.append("  (SELECT @rownum:=0) T)tb1 WHERE rnum >= 1 and rnum <= 5");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				MainDTO dto = new MainDTO();
				
				dto.setTrendNum(rs.getInt("trendNum"));
				dto.setTrendHead(rs.getString("trendHead"));
				dto.setTrendSubject(rs.getString("trendSubject"));
				
				listTrend.add(dto);				
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
		return listTrend;
	}
}

