package com.wanted;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class WantedDAO {
	private Connection conn = DBConn.getConnection();

	// 데이터 추가
	public int insertWanted(WantedDTO dto) {
	      int result = 0;
	      PreparedStatement pstmt = null;
	      StringBuffer sb = new StringBuffer();

	      try {
	         sb.append("INSERT INTO wanted(memId, wantedSubject, wantedContent, wantedHead) ");
	         sb.append(" VALUES (?,?,?,?)");

	         pstmt = conn.prepareStatement(sb.toString());
	         pstmt.setString(1, dto.getMemId());
	         pstmt.setString(2, dto.getWantedSubject());
	         pstmt.setString(3, dto.getWantedContent());
	         pstmt.setString(4, dto.getWantedHead());

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
			sql = " SELECT IFNULL(COUNT(*), 0) FROM wanted";
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
			sb.append("SELECT IFNULL(COUNT(*), 0) FROM wanted");
			if (searchKey.equals("memId"))
				sb.append(" WHERE memId LIKE CONCAT('%', ?, '%')");
			else if (searchKey.equals("wantedCreated"))
				sb.append(" WHERE wantedCreated=? ");
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
	public List<WantedDTO> listWanted(int start, int end) {
		List<WantedDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT wantedNum, wantedSubject,memId, wantedHead,");
			sb.append(" DATE_FORMAT(wantedCreated , '%Y-%m-%d') wantedCreated, wantedHitCount FROM wanted");
			sb.append(" ORDER BY wantedNum DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				WantedDTO dto = new WantedDTO();

				dto.setWantedNum(rs.getInt("WantedNum"));
				dto.setWantedSubject(rs.getString("WantedSubject"));
				dto.setMemId(rs.getString("memId"));
				dto.setWantedCreated(rs.getString("WantedCreated"));
				dto.setWantedHitCount(rs.getInt("WantedHitCount"));

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
	public List<WantedDTO> listWanted (int start, int end, String searchKey, String searchValue) {
	      List<WantedDTO> list=new ArrayList<>();
	      PreparedStatement pstmt=null;
	      StringBuffer sb= new StringBuffer();
	      ResultSet rs=null;
	      
	      try {
	    	  	sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT wantedNum, wantedSubject,memId, wantedHead,");
				sb.append(" DATE_FORMAT(wantedCreated , '%Y-%m-%d') wantedCreated, wantedHitCount FROM wanted");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" ORDER BY wantedNum DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
				
				pstmt=conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()) {
					WantedDTO dto = new WantedDTO();
					
					dto.setWantedNum(rs.getInt("WantedNum"));
					dto.setWantedSubject(rs.getString("WantedSubject"));
					dto.setMemId(rs.getString("memId"));
					dto.setWantedCreated(rs.getString("WantedCreated"));
					dto.setWantedHitCount(rs.getInt("WantedHitCount"));
					
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
	public WantedDTO readWanted(int wantedNum) {
		WantedDTO dto = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;

		try {
			sb.append("SELECT wantedNum, memId, wantedSubject, wantedContent, wantedHead,");
			sb.append(" wantedHitCount, DATE_FORMAT(wantedCreated , '%Y-%m-%d %h:%i:%s') wantedCreated");
			sb.append(" FROM wanted");
			sb.append(" WHERE wantedNum=?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, wantedNum);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new WantedDTO();
				dto.setWantedNum(wantedNum);
				dto.setMemId(rs.getString("memId"));
				dto.setWantedSubject(rs.getString("wantedSubject"));
				dto.setWantedContent(rs.getString("wantedContent"));
				dto.setWantedHitCount(rs.getInt("wantedHitCount"));
				dto.setWantedCreated(rs.getString("wantedCreated"));
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
	public int WantedUpdate(WantedDTO dto) {
		PreparedStatement pstmt = null;
		int result = 0;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("UPDATE wanted set wantedSubject=?, wantedContent=?,");
			sb.append(" wantedModified=NOW()");
			sb.append(" WHERE wantedNum=?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getWantedSubject());
			pstmt.setString(2, dto.getWantedContent());
			pstmt.setInt(3, dto.getWantedNum());

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
    
	public int deleteWanted(int wantedNum){
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql="DELETE FROM wanted WHERE wantedNum=?";
		try {
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setInt(1, wantedNum);
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
	// 조회수 증가
	public int WantedHitCount(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE wanted SET wantedHitCount= wantedHitCount+1 WHERE wantedNum=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, num);

			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return result;
	}
	
	 // 이전글
	public WantedDTO preReadWanted(int wantedNum, String searchKey, String searchValue) {
		WantedDTO dto =null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			if(searchValue!=null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT wantedNum, wantedSubject,memId,");
				sb.append(" DATE_FORMAT(wantedCreated , '%Y-%m-%d') wantedCreated, wantedHitCount FROM wanted");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND wantedNum>?");
				sb.append(" ORDER BY wantedNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, wantedNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT wantedNum, wantedSubject,memId,");
				sb.append(" DATE_FORMAT(wantedCreated , '%Y-%m-%d') wantedCreated, wantedHitCount FROM wanted");
				sb.append(" WHERE wantedNum > ?");
				sb.append(" ORDER BY wantedNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, wantedNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new WantedDTO();
				dto.setWantedNum(rs.getInt("wantedNum"));
				dto.setWantedSubject(rs.getString("wantedSubject"));
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
	// 다음글
	public WantedDTO nextReadWanted(int wantedNum, String searchKey, String searchValue) {
		WantedDTO dto =null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			if(searchValue!=null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT wantedNum, wantedSubject,memId,");
				sb.append(" DATE_FORMAT(wantedCreated , '%Y-%m-%d') wantedCreated, wantedHitCount FROM wanted");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND wantedNum<?");
				sb.append(" ORDER BY wantedNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, wantedNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT wantedNum, wantedSubject,memId,");
				sb.append(" DATE_FORMAT(wantedCreated , '%Y-%m-%d') wantedCreated, wantedHitCount FROM wanted");
				sb.append(" WHERE wantedNum < ?");
				sb.append(" ORDER BY wantedNum DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, wantedNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new WantedDTO();
				dto.setWantedNum(rs.getInt("wantedNum"));
				dto.setWantedSubject(rs.getString("wantedSubject"));
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
	// 리플 ==========================
	//댓글 등록
	public int insertWantedReply(WantedReplyDTO dto){
	      int result=0;
	      PreparedStatement pstmt=null;
	      StringBuffer sb=new StringBuffer();
	      
	      try {
	         sb.append("INSERT INTO wantedReply(wantedR_content, memId, wantedNum)");
	         sb.append("VALUES (?, ?, ?)");
	         
	         pstmt=conn.prepareStatement(sb.toString());
	         pstmt.setString(1, dto.getWantedR_content());
	         pstmt.setString(2, dto.getMemId());
	         pstmt.setInt(3, dto.getWantedNum());
	         
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
	public int dataCountWantedReply(int wantedNum) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT IFNULL(COUNT(*), 0) FROM wantedReply where wantedNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, wantedNum);
			
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
	public List<WantedReplyDTO> listWantedReply(int wantedNum, int start, int end) {
		List<WantedReplyDTO> list= new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT wantedR_Num, wantedNum, memId, wantedR_Content,");
			sb.append(" DATE_FORMAT(wantedR_Created , '%Y-%m-%d') wantedR_Created FROM wantedReply");
			sb.append(" WHERE wantedNum=?");
			sb.append(" ORDER BY wantedR_Num DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, wantedNum);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				WantedReplyDTO dto= new WantedReplyDTO();
				
				dto.setWantedR_num(rs.getInt("wantedR_num"));
				dto.setWantedNum(rs.getInt("wantedNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setWantedR_content(rs.getString("wantedR_content"));
				dto.setWantedR_created(rs.getString("wantedR_created"));
				
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
	   public int deleteWantedReply(int wantedR_num){
		      int result = 0;
		      PreparedStatement pstmt=null;
		      String sql;
		      
		      sql="DELETE FROM wantedReply WHERE wantedR_num=?";
		      try {
		         pstmt =conn.prepareStatement(sql);
		         pstmt.setInt(1, wantedR_num);
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
