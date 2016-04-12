package com.free;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class FreeDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertFree(FreeDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("INSERT INTO free(memId, freeContent) ");
			sb.append("VALUES(?, ?)");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getMemId());
			pstmt.setString(2, dto.getFreeContent());
			
			result=pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
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
			sql="SELECT IFNULL(COUNT(*), 0) FROM free";
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
	
	public List<FreeDTO> listFree(int start, int end) {
		List<FreeDTO> list=new ArrayList<FreeDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM ( SELECT @rownum:=@rownum+1 AS rnum, tb.* ");
			sb.append("FROM ( SELECT freeNum, f.memId,memName, freeContent,DATE_FORMAT(freeCreated,'%Y-%m-%d')as freeCreated ");
			sb.append("  FROM free f LEFT OUTER JOIN member1 m ON f.memId=m.memId  ");
			sb.append(" ORDER BY freeNum DESC )as tb, (SELECT @rownum:=0)as T )as tb1");
			sb.append(" WHERE rnum >= ? and rnum <=?");
	
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				FreeDTO dto=new FreeDTO();
				dto.setFreeNum(rs.getInt("freeNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setMemName(rs.getString("memName"));
				dto.setFreeContent(rs.getString("freeContent"));
				dto.setFreeCreated(rs.getString("freeCreated"));
				
				list.add(dto);
			}
			
			rs.close();
			pstmt.close();
			rs=null;
			pstmt=null;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return list;
	}
	
	public int deleteFree(int num) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="DELETE FROM free WHERE freeNum=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result=pstmt.executeUpdate();
			
			pstmt.close();
			pstmt=null;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	///////////////////////////////////////////////////////////////////////////////// ´ñ±ÛÀÛ¾÷
	
	
	/////////////////////////////// ´ñ±Ûµî·Ï //////////////////////////////////////////
	public int insertFreeReply(FreeReplyDTO dto){
	      int result=0;
	      PreparedStatement pstmt=null;
	      StringBuffer sb=new StringBuffer();
	      
	      try {
	         sb.append("INSERT INTO freeReply(freeR_content, memId, freeNum)");
	         sb.append("VALUES (?, ?, ?)");
	         
	         pstmt=conn.prepareStatement(sb.toString());
	         pstmt.setString(1, dto.getFreeR_content());
	         pstmt.setString(2, dto.getMemId());
	         pstmt.setInt(3, dto.getFreeNum());
	         
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
	public int dataCountFreeReply(int freeNum) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT IFNULL(COUNT(*), 0) FROM freeReply where freeNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,freeNum);
			
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
	public List<FreeReplyDTO> listFreeReply(int freeNum, int start, int end) {
		List<FreeReplyDTO> list= new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT freeR_Num, freeNum, memId, freeR_Content,");
			sb.append(" DATE_FORMAT(freeR_Created , '%Y-%m-%d') freeR_Created FROM freeReply");
			sb.append(" WHERE freeNum=?");
			sb.append(" ORDER BY freeR_Num DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, freeNum);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				FreeReplyDTO dto= new FreeReplyDTO();
				
				dto.setFreeR_num(rs.getInt("freeR_num"));
				dto.setFreeNum(rs.getInt("freeNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setFreeR_content(rs.getString("freeR_content"));
				dto.setFreeR_created(rs.getString("freeR_created"));
				
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
	   public int deleteFreeReply(int freeR_num){
		      int result = 0;
		      PreparedStatement pstmt=null;
		      String sql;
		      
		      sql="DELETE FROM freeReply WHERE freeR_num=?";
		      try {
		         pstmt =conn.prepareStatement(sql);
		         pstmt.setInt(1, freeR_num);
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
