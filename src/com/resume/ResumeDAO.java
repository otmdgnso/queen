package com.resume;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ResumeDAO {
	private Connection conn = DBConn.getConnection();

	// 데이터 추가
	public int insertResume(ResumeDTO dto) {
	      int result = 0;
	      PreparedStatement pstmt = null;
	      StringBuffer sb = new StringBuffer();

	      try {
	         sb.append("INSERT INTO resume(memId, resumeSubject, resumeCompany, resumeDate, resumeJob");
	         sb.append(" , resumeSchool, resumeMajor, resumeScore, resumeLanguage, resumeEx");
	         sb.append(" ,resumeAbility, resumeContent)");
	         sb.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

	         pstmt = conn.prepareStatement(sb.toString());
	         pstmt.setString(1, dto.getMemId());
	         pstmt.setString(2, dto.getResumeSubject());
	         pstmt.setString(3, dto.getResumeCompany());
	         pstmt.setString(4, dto.getResumeDate());
	         pstmt.setString(5, dto.getResumeJob());
	         pstmt.setString(6, dto.getResumeSchool());
	         pstmt.setString(7, dto.getResumeMajor());
	         pstmt.setString(8, dto.getResumeScore());
	         pstmt.setString(9, dto.getResumeLanguage());
	         pstmt.setString(10, dto.getResumeEx());
	         pstmt.setString(11, dto.getResumeAbility());
	         pstmt.setString(12, dto.getResumeContent());

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
			sql = " SELECT IFNULL(COUNT(*), 0) FROM resume";
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
			sb.append("SELECT IFNULL(COUNT(*), 0) FROM resume");
			if (searchKey.equals("memId"))
				sb.append(" WHERE memId LIKE CONCAT('%', ?, '%')");
			else if (searchKey.equals("resumeCreated"))
				sb.append(" WHERE resumeCreated=? ");
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
	public List<ResumeDTO> listResume(int start, int end) {
		List<ResumeDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT resumeNum, resumeRecomm, resumeJob, resumeSubject, memId,");
			sb.append(" DATE_FORMAT(resumeCreated , '%Y-%m-%d') resumeCreated, resumeHitCount FROM resume");
			sb.append(" ORDER BY resumeNum DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ResumeDTO dto = new ResumeDTO();

				dto.setResumeNum(rs.getInt("resumeNum"));
				dto.setResumeRecomm(rs.getInt("resumeRecomm"));
				dto.setResumeJob(rs.getString("resumeJob"));
				dto.setResumeSubject(rs.getString("resumeSubject"));
				dto.setMemId(rs.getString("memId"));
				dto.setResumeCreated(rs.getString("resumeCreated"));
				dto.setResumeHitCount(rs.getInt("resumeHitCount"));

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
	public List<ResumeDTO> listResume (int start, int end, String searchKey, String searchValue) {
	      List<ResumeDTO> list=new ArrayList<>();
	      PreparedStatement pstmt=null;
	      StringBuffer sb= new StringBuffer();
	      ResultSet rs=null;
	      
	      try {
	    	  	sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
	    	  	sb.append(" SELECT resumeNum, resumeRecomm, resumeJob, resumeSubject, memId,");
				sb.append(" DATE_FORMAT(resumeCreated , '%Y-%m-%d') resumeCreated, resumeHitCount FROM resume");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" ORDER BY resumeNum DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
				
				pstmt=conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()) {
					ResumeDTO dto = new ResumeDTO();
					
					dto.setResumeNum(rs.getInt("resumeNum"));
					dto.setResumeRecomm(rs.getInt("resumeRecomm"));
					dto.setResumeJob(rs.getString("resumeJob"));
					dto.setResumeSubject(rs.getString("resumeSubject"));
					dto.setMemId(rs.getString("memId"));
					dto.setResumeCreated(rs.getString("resumeCreated"));
					dto.setResumeHitCount(rs.getInt("resumeHitCount"));
					
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
	public ResumeDTO readResume(int resumeNum) {
		ResumeDTO dto = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;
		
		try {
			sb.append("SELECT resumeNum, memId, resumeSubject, resumeCompany, resumeDate, resumeJob,");
			sb.append(" resumeSchool, resumeMajor, resumeScore, resumeLanguage, resumeEx, resumeAbility,");
			sb.append(" resumeContent, resumeRecomm, resumeHitCount,");
			sb.append(" DATE_FORMAT(resumeCreated , '%Y-%m-%d %h:%i:%s') resumeCreated");
			sb.append(" FROM resume");
			sb.append(" WHERE resumeNum=?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, resumeNum);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ResumeDTO();
				dto.setResumeNum(resumeNum);
				dto.setMemId(rs.getString("memId"));
				dto.setResumeSubject(rs.getString("resumeSubject"));
				dto.setResumeCompany(rs.getString("resumeCompany"));
				dto.setResumeDate(rs.getString("resumeDate"));
				dto.setResumeJob(rs.getString("resumeJob"));
				dto.setResumeSchool(rs.getString("resumeSchool"));
				dto.setResumeMajor(rs.getString("resumeMajor"));
				dto.setResumeScore(rs.getString("resumeScore"));
				dto.setResumeLanguage(rs.getString("resumeLanguage"));
				dto.setResumeEx(rs.getString("resumeEx"));
				dto.setResumeAbility(rs.getString("resumeAbility"));
				dto.setResumeCreated(rs.getString("resumeCreated"));
				dto.setResumeContent(rs.getString("resumeContent"));
				dto.setResumeRecomm(rs.getInt("resumeRecomm"));
				dto.setResumeHitCount(rs.getInt("resumeHitCount"));
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
	public int resumeUpdate(ResumeDTO dto) {
		PreparedStatement pstmt = null;
		int result = 0;
		StringBuffer sb = new StringBuffer();

		
		try {
			sb.append("UPDATE resume set resumeSubject=?, resumeCompany=?, resumeDate=?, resumeJob=?,");
			sb.append(" resumeSchool=?, resumeMajor=?, resumeScore=?, resumeLanguage=?, resumeEx=?, resumeAbility=?,");
			sb.append(" resumeContent=?,");
			sb.append(" resumeModified=NOW()");
			sb.append(" WHERE resumeNum=?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getResumeSubject());
			pstmt.setString(2, dto.getResumeCompany());
			pstmt.setString(3, dto.getResumeDate());
			pstmt.setString(4, dto.getResumeJob());
			pstmt.setString(5, dto.getResumeSchool());
			pstmt.setString(6, dto.getResumeMajor());
			pstmt.setString(7, dto.getResumeScore());
			pstmt.setString(8, dto.getResumeLanguage());
			pstmt.setString(9, dto.getResumeEx());
			pstmt.setString(10, dto.getResumeAbility());
			pstmt.setString(11, dto.getResumeContent());
			pstmt.setInt(12, dto.getResumeNum());

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
    
	public int deleteResume(int resumeNum){
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql="DELETE FROM resume WHERE resumeNum=?";
		try {
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setInt(1, resumeNum);
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
	public int resumeHitCount(int resumeNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE resume SET resumeHitCount= resumeHitCount+1 WHERE resumeNum=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, resumeNum);

			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return result;
	}
	
	 // 이전글
	public ResumeDTO preReadResume(int resumeNum, String searchKey, String searchValue) {
		ResumeDTO dto =null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			if(searchValue!=null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT resumeNum, resumeSubject,memId,");
				sb.append(" DATE_FORMAT(resumeCreated , '%Y-%m-%d') resumeCreated, resumeHitCount FROM resume");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND resumeNum>?");
				sb.append(" ORDER BY resumeNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, resumeNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT resumeNum, resumeSubject,memId,");
				sb.append(" DATE_FORMAT(resumeCreated , '%Y-%m-%d') resumeCreated, resumeHitCount FROM resume");
				sb.append(" WHERE resumeNum > ?");
				sb.append(" ORDER BY resumeNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, resumeNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new ResumeDTO();
				dto.setResumeNum(rs.getInt("resumeNum"));
				dto.setResumeSubject(rs.getString("resumeSubject"));
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
	// 다음글
	public ResumeDTO nextReadResume(int resumeNum, String searchKey, String searchValue) {
		ResumeDTO dto =null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			if(searchValue!=null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT resumeNum, resumeSubject,memId,");
				sb.append(" DATE_FORMAT(resumeCreated , '%Y-%m-%d') resumeCreated, resumeHitCount FROM resume");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND resumeNum<?");
				sb.append(" ORDER BY resumeNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, resumeNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT resumeNum, resumeSubject,memId,");
				sb.append(" DATE_FORMAT(resumeCreated , '%Y-%m-%d') resumeCreated, resumeHitCount FROM resume");
				sb.append(" WHERE resumeNum < ?");
				sb.append(" ORDER BY resumeNum DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, resumeNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new ResumeDTO();
				dto.setResumeNum(rs.getInt("resumeNum"));
				dto.setResumeSubject(rs.getString("resumeSubject"));
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
	// 리플 ==========================
	//댓글 등록
	public int insertResumeReply(ResumeReplyDTO dto){
	      int result=0;
	      PreparedStatement pstmt=null;
	      StringBuffer sb=new StringBuffer();
	      
	      try {
	         sb.append("INSERT INTO resumeReply(resumeR_content, memId, resumeNum)");
	         sb.append("VALUES (?, ?, ?)");
	         
	         pstmt=conn.prepareStatement(sb.toString());
	         pstmt.setString(1, dto.getResumeR_content());
	         pstmt.setString(2, dto.getMemId());
	         pstmt.setInt(3, dto.getResumeNum());
	         
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
	public int dataCountResumeReply(int resumeNum) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT IFNULL(COUNT(*), 0) FROM resumeReply where resumeNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, resumeNum);
			
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
	public List<ResumeReplyDTO> listResumeReply(int resumeNum, int start, int end) {
		List<ResumeReplyDTO> list= new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT resumeR_Num, resumeNum, memId, resumeR_Content,");
			sb.append(" DATE_FORMAT(resumeR_Created , '%Y-%m-%d') resumeR_Created FROM resumeReply");
			sb.append(" WHERE resumeNum=?");
			sb.append(" ORDER BY resumeR_Num DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, resumeNum);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				ResumeReplyDTO dto= new ResumeReplyDTO();
				
				dto.setResumeR_num(rs.getInt("resumeR_num"));
				dto.setResumeNum(rs.getInt("resumeNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setResumeR_content(rs.getString("resumeR_content"));
				dto.setResumeR_created(rs.getString("resumeR_created"));
				
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
	   public int deleteResumeReply(int resumeR_num){
		      int result = 0;
		      PreparedStatement pstmt=null;
		      String sql;
		      
		      sql="DELETE FROM resumeReply WHERE resumeR_num=?";
		      try {
		         pstmt =conn.prepareStatement(sql);
		         pstmt.setInt(1, resumeR_num);
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
	   
	// 추천 체크
				public int recommCount(int resumeNum, String memId) {
					int result = 0;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					StringBuffer sb= new StringBuffer();
		
					try {
						// 게시물 번호에 아이디가 없을 경우 0 있을경우 1
						sb.append("SELECT IFNULL(COUNT(*), 0) FROM resumeRecomm");
						sb.append(" WHERE resumeNum=? AND memId=?");
						pstmt = conn.prepareStatement(sb.toString());
						pstmt.setInt(1, resumeNum);
						pstmt.setString(2, memId);
		
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
				
				// 추천수 증가
				public int ResumeRecomm(int resumeNum, String memId) {
					int result = 0;
					PreparedStatement pstmt = null;
					String sql;
					String sql2;
		
					try {
						// 추천수 +1
						sql = "UPDATE resume SET resumeRecomm= resumeRecomm+1 WHERE resumeNum=?";
		
						pstmt = conn.prepareStatement(sql);
		
						pstmt.setInt(1, resumeNum);
						
						pstmt.executeUpdate();
						pstmt.close();
						pstmt=null;
						
						//추천 테이블에 추천체크하는데 필요한 정보 추가
						sql2=" INSERT INTO resumeRecomm VALUES(?,?)";
						
						pstmt= conn.prepareStatement(sql2);
						
						pstmt.setString(1, memId);
						pstmt.setInt(2, resumeNum);
		
						result = pstmt.executeUpdate();
						pstmt.close();
						pstmt = null;
					} catch (Exception e) {
						System.out.println(e.toString());
					}
		
					return result;
				}				
	
	
	
}
