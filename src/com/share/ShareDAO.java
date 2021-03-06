package com.share;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ShareDAO {
	private Connection conn = DBConn.getConnection();

	// 데이터 추가
	public int insertShare(ShareDTO dto) {
	      int result = 0;
	      PreparedStatement pstmt = null;
	      StringBuffer sb = new StringBuffer();

	      try {
	         sb.append("INSERT INTO share(memId, shareSubject, shareContent) ");
	         sb.append(" VALUES (?,?,?)");

	         pstmt = conn.prepareStatement(sb.toString());
	         pstmt.setString(1, dto.getMemId());
	         pstmt.setString(2, dto.getShareSubject());
	         pstmt.setString(3, dto.getShareContent());

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
			sql = " SELECT IFNULL(COUNT(*), 0) FROM share";
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
			sb.append("SELECT IFNULL(COUNT(*), 0) FROM share");
			if (searchKey.equals("memId"))
				sb.append(" WHERE memId LIKE CONCAT('%', ?, '%')");
			else if (searchKey.equals("shareCreated"))
				sb.append(" WHERE shareCreated=? ");
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
	public List<ShareDTO> listShare(int start, int end) {
		List<ShareDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT shareNum, shareSubject,memId,");
			sb.append(" DATE_FORMAT(shareCreated , '%Y-%m-%d') shareCreated, shareHitCount FROM share");
			sb.append(" ORDER BY shareNum DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShareDTO dto = new ShareDTO();

				dto.setShareNum(rs.getInt("ShareNum"));
				dto.setShareSubject(rs.getString("ShareSubject"));
				dto.setMemId(rs.getString("memId"));
				dto.setShareCreated(rs.getString("ShareCreated"));
				dto.setShareHitCount(rs.getInt("ShareHitCount"));

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
	public List<ShareDTO> listShare (int start, int end, String searchKey, String searchValue) {
	      List<ShareDTO> list=new ArrayList<>();
	      PreparedStatement pstmt=null;
	      StringBuffer sb= new StringBuffer();
	      ResultSet rs=null;
	      
	      try {
	    	  	sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT shareNum, shareSubject,memId,");
				sb.append(" DATE_FORMAT(shareCreated , '%Y-%m-%d') shareCreated, shareHitCount FROM share");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" ORDER BY shareNum DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
				
				pstmt=conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()) {
					ShareDTO dto = new ShareDTO();
					
					dto.setShareNum(rs.getInt("ShareNum"));
					dto.setShareSubject(rs.getString("ShareSubject"));
					dto.setMemId(rs.getString("memId"));
					dto.setShareCreated(rs.getString("ShareCreated"));
					dto.setShareHitCount(rs.getInt("ShareHitCount"));
					
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
	public ShareDTO readShare(int shareNum) {
		ShareDTO dto = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;

		try {
			sb.append("SELECT shareNum, memId, shareSubject, shareContent,");
			sb.append(" shareHitCount, DATE_FORMAT(shareCreated , '%Y-%m-%d %h:%i:%s') shareCreated");
			sb.append(" FROM share");
			sb.append(" WHERE shareNum=?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, shareNum);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ShareDTO();
				dto.setShareNum(shareNum);
				dto.setMemId(rs.getString("memId"));
				dto.setShareSubject(rs.getString("shareSubject"));
				dto.setShareContent(rs.getString("shareContent"));
				dto.setShareHitCount(rs.getInt("shareHitCount"));
				dto.setShareCreated(rs.getString("shareCreated"));
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
	public int ShareUpdate(ShareDTO dto) {
		PreparedStatement pstmt = null;
		int result = 0;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("UPDATE share set shareSubject=?, shareContent=?,");
			sb.append(" shareModified=NOW()");
			sb.append(" WHERE shareNum=?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getShareSubject());
			pstmt.setString(2, dto.getShareContent());
			pstmt.setInt(3, dto.getShareNum());

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
    
	public int deleteShare(int shareNum){
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql="DELETE FROM share WHERE shareNum=?";
		try {
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setInt(1, shareNum);
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
	public int ShareHitCount(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE share SET shareHitCount= shareHitCount+1 WHERE shareNum=?";

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
	public ShareDTO preReadShare(int shareNum, String searchKey, String searchValue) {
		ShareDTO dto =null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			if(searchValue!=null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT shareNum, shareSubject,memId,");
				sb.append(" DATE_FORMAT(shareCreated , '%Y-%m-%d') shareCreated, shareHitCount FROM share");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND shareNum>?");
				sb.append(" ORDER BY shareNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, shareNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT shareNum, shareSubject,memId,");
				sb.append(" DATE_FORMAT(shareCreated , '%Y-%m-%d') shareCreated, shareHitCount FROM share");
				sb.append(" WHERE shareNum > ?");
				sb.append(" ORDER BY shareNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, shareNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new ShareDTO();
				dto.setShareNum(rs.getInt("shareNum"));
				dto.setShareSubject(rs.getString("shareSubject"));
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
	// 다음글
	public ShareDTO nextReadShare(int shareNum, String searchKey, String searchValue) {
		ShareDTO dto =null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			if(searchValue!=null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT shareNum, shareSubject,memId,");
				sb.append(" DATE_FORMAT(shareCreated , '%Y-%m-%d') shareCreated, shareHitCount FROM share");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND shareNum<?");
				sb.append(" ORDER BY shareNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, shareNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT shareNum, shareSubject,memId,");
				sb.append(" DATE_FORMAT(shareCreated , '%Y-%m-%d') shareCreated, shareHitCount FROM share");
				sb.append(" WHERE shareNum < ?");
				sb.append(" ORDER BY shareNum DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, shareNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new ShareDTO();
				dto.setShareNum(rs.getInt("shareNum"));
				dto.setShareSubject(rs.getString("shareSubject"));
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
	// 리플 ==========================
	//댓글 등록
	public int insertShareReply(ShareReplyDTO dto){
	      int result=0;
	      PreparedStatement pstmt=null;
	      StringBuffer sb=new StringBuffer();
	      
	      try {
	         sb.append("INSERT INTO shareReply(shareR_content, memId, shareNum)");
	         sb.append("VALUES (?, ?, ?)");
	         
	         pstmt=conn.prepareStatement(sb.toString());
	         pstmt.setString(1, dto.getShareR_content());
	         pstmt.setString(2, dto.getMemId());
	         pstmt.setInt(3, dto.getShareNum());
	         
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
	public int dataCountShareReply(int shareNum) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT IFNULL(COUNT(*), 0) FROM shareReply where shareNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, shareNum);
			
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
	public List<ShareReplyDTO> listShareReply(int shareNum, int start, int end) {
		List<ShareReplyDTO> list= new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT shareR_Num, shareNum, memId, shareR_Content,");
			sb.append(" DATE_FORMAT(shareR_Created , '%Y-%m-%d') shareR_Created FROM shareReply");
			sb.append(" WHERE shareNum=?");
			sb.append(" ORDER BY shareR_Num DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, shareNum);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				ShareReplyDTO dto= new ShareReplyDTO();
				
				dto.setShareR_num(rs.getInt("shareR_num"));
				dto.setShareNum(rs.getInt("shareNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setShareR_content(rs.getString("shareR_content"));
				dto.setShareR_created(rs.getString("shareR_created"));
				
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
	   public int deleteShareReply(int shareR_num){
		      int result = 0;
		      PreparedStatement pstmt=null;
		      String sql;
		      
		      sql="DELETE FROM shareReply WHERE shareR_num=?";
		      try {
		         pstmt =conn.prepareStatement(sql);
		         pstmt.setInt(1, shareR_num);
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
