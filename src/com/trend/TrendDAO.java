package com.trend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class TrendDAO {
	private Connection conn = DBConn.getConnection();

	// 데이터 추가
	public int insertTrend(TrendDTO dto) {
	      int result = 0;
	      PreparedStatement pstmt = null;
	      StringBuffer sb = new StringBuffer();

	      try {
	         sb.append("INSERT INTO trend(memId, trendSubject, trendContent, trendHead) ");
	         sb.append(" VALUES (?,?,?,?)");

	         pstmt = conn.prepareStatement(sb.toString());
	         pstmt.setString(1, dto.getMemId());
	         pstmt.setString(2, dto.getTrendSubject());
	         pstmt.setString(3, dto.getTrendContent());
	         pstmt.setString(4, dto.getTrendHead());

	         result = pstmt.executeUpdate();

	      } catch (Exception e) {
	         System.out.println(e.toString());
	      } finally {
	         if (pstmt != null)
	            try {
	               pstmt.close();
	            } catch (SQLException e) {

	            }
	      }
	      return result;
	   }


	// 데이터 개수
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = " SELECT IFNULL(COUNT(*), 0) FROM trend";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);

			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}

	// 검색에서의 데이터 개수 (쿼리문이 아직 확실하지 않음.....)
	public int dataCount(String searchKey, String searchValue) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT IFNULL(COUNT(*), 0) FROM trend");
			if (searchKey.equals("memId"))
				sb.append(" WHERE memId LIKE CONCAT('%', ?, '%')");
			else if (searchKey.equals("trendCreated"))
				sb.append(" WHERE trendCreated=? ");
			else
				sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%' )");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, searchValue);

			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);

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

		return result;
	}

	// 게시판 리스트
	public List<TrendDTO> listTrend(int start, int end) {
		List<TrendDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT trendNum, trendSubject,memId, trendHead,");
			sb.append(" DATE_FORMAT(trendCreated , '%Y-%m-%d') trendCreated FROM trend");
			sb.append(" ORDER BY trendNum DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				TrendDTO dto = new TrendDTO();

				dto.setTrendNum(rs.getInt("TrendNum"));
				dto.setTrendSubject(rs.getString("TrendSubject"));
				dto.setMemId(rs.getString("memId"));
				dto.setTrendCreated(rs.getString("TrendCreated"));
				dto.setTrendHead(rs.getString("TrendHead"));

				list.add(dto);
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
		return list;
	}
	//검색될 때 리스트
	public List<TrendDTO> listTrend (int start, int end, String searchKey, String searchValue) {
	      List<TrendDTO> list=new ArrayList<>();
	      PreparedStatement pstmt=null;
	      StringBuffer sb= new StringBuffer();
	      ResultSet rs=null;
	      
	      try {
	    	  	sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT trendNum, trendSubject,memId, trendHead,");
				sb.append(" DATE_FORMAT(trendCreated , '%Y-%m-%d') trendCreated FROM trend");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" ORDER BY trendNum DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
				
				pstmt=conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()) {
					TrendDTO dto = new TrendDTO();
					
					dto.setTrendNum(rs.getInt("TrendNum"));
					dto.setTrendSubject(rs.getString("TrendSubject"));
					dto.setMemId(rs.getString("memId"));
					dto.setTrendCreated(rs.getString("TrendCreated"));
					dto.setTrendHead(rs.getString("TrendHead"));
					dto.setTrendHead(rs.getString("TrendHead"));
					
					list.add(dto);
				}
				rs.close();
				pstmt.close();
	      } catch (Exception e) {
	         System.out.println(e.toString());
	      }
	      
	      return list;
	 }
	

	// 게시판 글보기
	public TrendDTO readTrend(int trendNum) {
		TrendDTO dto = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;

		try {
			sb.append("SELECT trendNum, memId, trendSubject, trendContent, trendHead,");
			sb.append(" DATE_FORMAT(trendCreated , '%Y-%m-%d %h:%i:%s') trendCreated");
			sb.append(" FROM trend");
			sb.append(" WHERE trendNum=?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, trendNum);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new TrendDTO();
				dto.setTrendNum(trendNum);
				dto.setMemId(rs.getString("memId"));
				dto.setTrendSubject(rs.getString("trendSubject"));
				dto.setTrendContent(rs.getString("trendContent"));
				dto.setTrendCreated(rs.getString("trendCreated"));
				dto.setTrendHead(rs.getString("TrendHead"));
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

	// 게시글 수정
	public int TrendUpdate(TrendDTO dto) {
		PreparedStatement pstmt = null;
		int result = 0;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("UPDATE trend set trendSubject=?, trendContent=?,");
			sb.append(" trendModified=NOW()");
			sb.append(" WHERE trendNum=?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getTrendSubject());
			pstmt.setString(2, dto.getTrendContent());
			pstmt.setInt(3, dto.getTrendNum());

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
    
	public int deleteTrend(int trendNum){
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql="DELETE FROM trend WHERE trendNum=?";
		try {
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setInt(1, trendNum);
		   result = pstmt.executeUpdate();
		   
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(pstmt !=null){
				try {
					pstmt.close();
				} catch (Exception e2) {					
				}
			}
		}
		return result;
	}
	
	 // 이전글
	public TrendDTO preReadTrend(int trendNum, String searchKey, String searchValue) {
		TrendDTO dto =null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			if(searchValue!=null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT trendNum, trendSubject,memId,");
				sb.append(" DATE_FORMAT(trendCreated , '%Y-%m-%d') trendCreated FROM trend");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND trendNum>?");
				sb.append(" ORDER BY trendNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, trendNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT trendNum, trendSubject,memId,");
				sb.append(" DATE_FORMAT(trendCreated , '%Y-%m-%d') trendCreated FROM trend");
				sb.append(" WHERE trendNum > ?");
				sb.append(" ORDER BY trendNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, trendNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new TrendDTO();
				dto.setTrendNum(rs.getInt("trendNum"));
				dto.setTrendSubject(rs.getString("trendSubject"));
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
	// 다음글
	public TrendDTO nextReadTrend(int trendNum, String searchKey, String searchValue) {
		TrendDTO dto =null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			if(searchValue!=null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT trendNum, trendSubject,memId,");
				sb.append(" DATE_FORMAT(trendCreated , '%Y-%m-%d') trendCreated FROM trend");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND trendNum<?");
				sb.append(" ORDER BY trendNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, trendNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT trendNum, trendSubject,memId,");
				sb.append(" DATE_FORMAT(trendCreated , '%Y-%m-%d') trendCreated FROM trend");
				sb.append(" WHERE trendNum < ?");
				sb.append(" ORDER BY trendNum DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, trendNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new TrendDTO();
				dto.setTrendNum(rs.getInt("trendNum"));
				dto.setTrendSubject(rs.getString("trendSubject"));
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
	// 리플 ==========================
	//댓글 등록
	public int insertTrendReply(TrendReplyDTO dto){
	      int result=0;
	      PreparedStatement pstmt=null;
	      StringBuffer sb=new StringBuffer();
	      
	      try {
	         sb.append("INSERT INTO trendReply(trendR_content, memId, trendNum)");
	         sb.append("VALUES (?, ?, ?)");
	         
	         pstmt=conn.prepareStatement(sb.toString());
	         pstmt.setString(1, dto.getTrendR_content());
	         pstmt.setString(2, dto.getMemId());
	         pstmt.setInt(3, dto.getTrendNum());
	         
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
	   
	// 댓글 갯수
	public int dataCountTrendReply(int trendNum) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT IFNULL(COUNT(*), 0) FROM trendReply where trendNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, trendNum);
			
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
	
	// 댓글 리스트
	public List<TrendReplyDTO> listTrendReply(int trendNum, int start, int end) {
		List<TrendReplyDTO> list= new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT trendR_Num, trendNum, memId, trendR_Content,");
			sb.append(" DATE_FORMAT(trendR_Created , '%Y-%m-%d') trendR_Created FROM trendReply");
			sb.append(" WHERE trendNum=?");
			sb.append(" ORDER BY trendR_Num DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, trendNum);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				TrendReplyDTO dto= new TrendReplyDTO();
				
				dto.setTrendR_num(rs.getInt("trendR_num"));
				dto.setTrendNum(rs.getInt("trendNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setTrendR_content(rs.getString("trendR_content"));
				dto.setTrendR_created(rs.getString("trendR_created"));
				
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
	
	// 댓글 삭제
	   public int deleteTrendReply(int trendR_num){
		      int result = 0;
		      PreparedStatement pstmt=null;
		      String sql;
		      
		      sql="DELETE FROM trendReply WHERE trendR_num=?";
		      try {
		         pstmt =conn.prepareStatement(sql);
		         pstmt.setInt(1, trendR_num);
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
