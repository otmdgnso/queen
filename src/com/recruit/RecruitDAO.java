package com.recruit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.company.CompanyDTO;
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
	
	public CompanyDTO readCompany(String recruitCompany) {
		CompanyDTO dto = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;

		try {
			sb.append("SELECT companyNum FROM company WHERE companyName=?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, recruitCompany);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new CompanyDTO();
				dto.setCompanyNum(rs.getInt("companyNum"));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
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
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("UPDATE recruit SET recruitSubject=?, recruitHead=?, recruitStart=?, recruitEnd=?,");
			sb.append(" recruitQual=?, recruitStep=?, recruitImg=? WHERE recruitNum=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getRecruitSubject());
			pstmt.setString(2, dto.getRecruitHead());
			pstmt.setString(3, dto.getRecruitStart());
			pstmt.setString(4, dto.getRecruitEnd());
			pstmt.setString(5, dto.getRecruitQual());
			pstmt.setString(6, dto.getRecruitStep());
			pstmt.setString(7, dto.getRecruitImg());
			pstmt.setInt(8, dto.getRecruitNum());
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}		
		return result;
		
	}
	
	public int deleteRecruit(int recruitNum){
		int result=0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("DELETE FROM recruit WHERE recruitNum=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, recruitNum);
			result=pstmt.executeUpdate();
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	
	// ¸®ÇÃ ==========================
		//´ñ±Û µî·Ï
		public int insertRecruitReply(RecruitReplyDTO dto){
		      int result=0;
		      PreparedStatement pstmt=null;
		      StringBuffer sb=new StringBuffer();
		      
		      try {
		         sb.append("INSERT INTO recruitReply(recruitR_content, memId, recruitNum)");
		         sb.append("VALUES (?, ?, ?)");
		         
		         pstmt=conn.prepareStatement(sb.toString());
		         pstmt.setString(1, dto.getRecruitR_content());
		         pstmt.setString(2, dto.getMemId());
		         pstmt.setInt(3, dto.getRecruitNum());
		         
		         result=pstmt.executeUpdate();
		      } catch (Exception e) {
		         System.out.println(e.toString());
		      } finally {
		         if(pstmt!=null)
		            try {
		               pstmt.close();
		            } catch (SQLException e) {   
		            }
		      }
		      return result;
		   }
		   
		// ´ñ±Û °¹¼ö
		public int dataCountRecruitReply(int recruitNum) {
			int result=0;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			String sql;
			
			try {
				sql="SELECT IFNULL(COUNT(*), 0) FROM recruitReply where recruitNum=?";
				
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, recruitNum);
				
				rs=pstmt.executeQuery();
				if(rs.next())
					result=rs.getInt(1);
			} catch (Exception e) {
				System.out.println(e.toString());
			} finally {
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
			
			return result;
		}
		
		// ´ñ±Û ¸®½ºÆ®
		public List<RecruitReplyDTO> listRecruitReply(int recruitNum, int start, int end) {
			List<RecruitReplyDTO> list= new ArrayList<>();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			StringBuffer sb=new StringBuffer();
			
			try {
				sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT recruitR_Num, recruitNum, memId, recruitR_Content,");
				sb.append(" DATE_FORMAT(recruitR_Created , '%Y-%m-%d') recruitR_Created FROM recruitReply");
				sb.append(" WHERE recruitNum=?");
				sb.append(" ORDER BY recruitR_Num DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
				
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, recruitNum);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()) {
					RecruitReplyDTO dto= new RecruitReplyDTO();
					
					dto.setRecruitR_num(rs.getInt("recruitR_num"));
					dto.setRecruitNum(rs.getInt("recruitNum"));
					dto.setMemId(rs.getString("memId"));
					dto.setRecruitR_content(rs.getString("recruitR_content"));
					dto.setRecruitR_created(rs.getString("recruitR_created"));
					
					list.add(dto);
				}
				
			} catch (Exception e) {
				System.out.println(e.toString());
			} finally {
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
		
		// ´ñ±Û »èÁ¦
		   public int deleteRecruitReply(int recruitR_num){
			      int result = 0;
			      PreparedStatement pstmt=null;
			      String sql;
			      
			      sql="DELETE FROM recruitReply WHERE recruitR_num=?";
			      try {
			         pstmt =conn.prepareStatement(sql);
			         pstmt.setInt(1, recruitR_num);
			         result = pstmt.executeUpdate();
			      } catch (Exception e) {
			         System.out.println(e.toString());
			      } finally {
			         if(pstmt!=null){
			            try {
			               pstmt.close();
			            } catch (Exception e2) {      
			            }
			         }
			      }
			      return result;
			   }
		
		

}
