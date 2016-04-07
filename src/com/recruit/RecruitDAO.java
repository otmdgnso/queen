package com.recruit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class RecruitDAO {
	
private Connection conn=DBConn.getConnection();
	
	public List<RecruitDTO> listStartRecruit(String start, String end){
		
		List<RecruitDTO> list=new ArrayList<RecruitDTO>();
		RecruitDTO dto=null;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		ResultSet rs=null;
		
		try {
			sb.append("SELECT recruitSubject, recruitNum, DATE_FORMAT(recruitStart,'%Y-%m-%d') recruitStart FROM recruit");
			sb.append(" WHERE DATE_FORMAT(recruitStart,'%Y-%m-%d')>=?"); 
			sb.append(" AND DATE_FORMAT(recruitStart,'%Y-%m-%d')<=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				dto=new RecruitDTO();
				
				dto.setRecruitSubject(rs.getString("recruitSubject"));
				dto.setRecruitNum(rs.getInt("recruitNum"));
				dto.setRecruitStart(rs.getString("recruitStart"));
				
				list.add(dto);
			}
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return list;
	}
	
public List<RecruitDTO> listEndRecruit(String start, String end){
		
		List<RecruitDTO> list=new ArrayList<RecruitDTO>();
		RecruitDTO dto=null;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		ResultSet rs=null;
		
		try {
			sb.append("SELECT recruitSubject, recruitNum, DATE_FORMAT(recruitEnd,'%Y-%m-%d') recruitEnd FROM recruit");
			sb.append(" WHERE DATE_FORMAT(recruitEnd,'%Y-%m-%d')>=?"); 
			sb.append(" AND DATE_FORMAT(recruitEnd,'%Y-%m-%d')<=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				dto=new RecruitDTO();
				
				dto.setRecruitSubject(rs.getString("recruitSubject"));
				dto.setRecruitNum(rs.getInt("recruitNum"));
				dto.setRecruitEnd(rs.getString("recruitEnd"));
				
				list.add(dto);
			}
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return list;
	}
	
	public RecruitDTO readRecruit(int recruitNum){
		RecruitDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			
			sb.append("SELECT r.memId, recruitSubject, recruitCompany, recruitHead,");
			sb.append(" DATE_FORMAT(recruitStart,'%Y-%m-%d') recruitStart,");
			sb.append(" DATE_FORMAT(recruitEnd,'%Y-%m-%d') recruitEnd, recruitQual,");
			sb.append(" recruitStep, recruitImg, recruitCreated, recruitNum");
			sb.append(" FROM recruit r JOIN member1 m ON m.memId=r.memId WHERE recruitNum=?;");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, recruitNum);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				dto=new RecruitDTO();
				dto.setRecruitNum(rs.getInt("recruitNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setRecruitSubject(rs.getString("recruitSubject"));
				dto.setRecruitCompany(rs.getString("recruitCompany"));
				dto.setRecruitHead(rs.getString("recruitHead"));
				dto.setRecruitStart(rs.getString("recruitStart"));
				dto.setRecruitEnd(rs.getString("recruitEnd"));
				dto.setRecruitQual(rs.getString("recruitQual"));
				dto.setRecruitStep(rs.getString("recruitStep"));
				dto.setRecruitImg(rs.getString("recruitImg"));
				dto.setRecruitCreated(rs.getString("recruitCreated"));
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
		
		return dto;
	}
	
	public int insertRecruit(RecruitDTO dto){
		int result=0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("INSERT INTO recruit(recruitSubject, recruitCompany, recruitHead,");
			sb.append(" recruitStart, recruitEnd, recruitQual, recruitStep, recruitImg, memId)");
			sb.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getRecruitSubject());
			pstmt.setString(2, dto.getRecruitCompany());
			pstmt.setString(3, dto.getRecruitHead());
			pstmt.setString(4, dto.getRecruitStart());
			pstmt.setString(5, dto.getRecruitEnd());
			pstmt.setString(6, dto.getRecruitQual());
			pstmt.setString(7, dto.getRecruitStep());
			pstmt.setString(8, dto.getRecruitImg());
			pstmt.setString(9, dto.getMemId());
			
			result=pstmt.executeUpdate();
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public int updateRecruit(RecruitDTO dto){
		int result=0;
		
		return result;
	}
	
	public int deleteRecruit(int recruitNum){
		int result=0;
		
		return result;
	}

}
