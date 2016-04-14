package com.tip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.company.CompanyDTO;
import com.util.DBConn;

public class TipDAO {
	private Connection conn = DBConn.getConnection();

	// 데이터 추가
	public int insertTip(TipDTO dto) {
	      int result = 0;
	      PreparedStatement pstmt = null;
	      StringBuffer sb = new StringBuffer();

	      try {
	         sb.append("INSERT INTO tip(memId, tipSubject, tipName, tipDate, tipSales");
	         sb.append(" ,tipForm, tipIndustry, tipPlanet, tipWeb, tipContent, tipSalary");
	         sb.append(" ,tipScore)");
	         sb.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

	         pstmt = conn.prepareStatement(sb.toString());
	         pstmt.setString(1, dto.getMemId());
	         pstmt.setString(2, dto.getTipSubject());
	         pstmt.setString(10, dto.getTipContent());

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
			sql = " SELECT IFNULL(COUNT(*), 0) FROM tip";
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
			sb.append("SELECT IFNULL(COUNT(*), 0) FROM tip");
			if (searchKey.equals("memId"))
				sb.append(" WHERE memId LIKE CONCAT('%', ?, '%')");
			else if (searchKey.equals("tipCreated"))
				sb.append(" WHERE tipCreated=? ");
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
	public List<TipDTO> listTip(int start, int end) {
		List<TipDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT tipNum, tipRecomm, tipHead, tipSubject, memId,");
			sb.append(" DATE_FORMAT(tipCreated , '%Y-%m-%d') tipCreated, tipHitCount FROM tip");
			sb.append(" ORDER BY tipNum DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				TipDTO dto = new TipDTO();

				dto.setTipNum(rs.getInt("tipNum"));
				dto.setTipRecomm(rs.getInt("tipRecomm"));
				dto.setTipHead(rs.getString("tipHead"));
				dto.setTipSubject(rs.getString("tipSubject"));
				dto.setMemId(rs.getString("memId"));
				dto.setTipCreated(rs.getString("tipCreated"));
				dto.setTipHitCount(rs.getInt("tipHitCount"));

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
	public List<TipDTO> listTip (int start, int end, String searchKey, String searchValue) {
	      List<TipDTO> list=new ArrayList<>();
	      PreparedStatement pstmt=null;
	      StringBuffer sb= new StringBuffer();
	      ResultSet rs=null;
	      
	      try {
	    	  	sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
	    	  	sb.append(" SELECT tipNum, tipRecomm, tipHead, tipSubject, memId,");
				sb.append(" DATE_FORMAT(tipCreated , '%Y-%m-%d') tipCreated, tipHitCount FROM tip");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" ORDER BY tipNum DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
				
				pstmt=conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()) {
					TipDTO dto = new TipDTO();
					
					dto.setTipNum(rs.getInt("tipNum"));
					dto.setTipRecomm(rs.getInt("tipRecomm"));
					dto.setTipHead(rs.getString("tipHead"));
					dto.setTipSubject(rs.getString("tipSubject"));
					dto.setMemId(rs.getString("memId"));
					dto.setTipCreated(rs.getString("tipCreated"));
					dto.setTipHitCount(rs.getInt("tipHitCount"));
					
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
	public TipDTO readTip(int tipNum) {
		TipDTO dto = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;

		try {
			sb.append("SELECT tipNum, memId, tipHead, tipSubject, tipContent,");
			sb.append(" tipRecomm, tipHitCount, tipSource, ");
			sb.append(" DATE_FORMAT(tipCreated , '%Y-%m-%d %h:%i:%s') tipCreated");
			sb.append(" FROM tip");
			sb.append(" WHERE tipNum=?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, tipNum);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new TipDTO();
				dto.setTipNum(tipNum);
				dto.setMemId(rs.getString("memId"));
				dto.setTipSubject(rs.getString("tipSubject"));
				dto.setTipContent(rs.getString("tipContent"));
				dto.setTipRecomm(rs.getInt("tipRecomm"));
				dto.setTipHitCount(rs.getInt("tipHitCount"));
				dto.setTipCreated(rs.getString("tipCreated"));
				dto.setTipHead(rs.getString("tipHead"));
				dto.setTipSource(rs.getString("tipSource"));
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
	public int tipUpdate(TipDTO dto) {
		PreparedStatement pstmt = null;
		int result = 0;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("UPDATE tip set tipSubject=?, tipHead=?, tipContent=?, tipSource=?,");
			sb.append(" tipModified=NOW()");
			sb.append(" WHERE tipNum=?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getTipSubject());
			pstmt.setString(2, dto.getTipHead());
			pstmt.setString(3, dto.getTipContent());
			pstmt.setString(4, dto.getTipSource());
			pstmt.setInt(5, dto.getTipNum());

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
    
	public int deleteTip(int tipNum){
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql="DELETE FROM tip WHERE tipNum=?";
		try {
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setInt(1, tipNum);
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
	public int tipHitCount(int tipNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE tip SET tipHitCount= tipHitCount+1 WHERE tipNum=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, tipNum);

			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return result;
	}
	
	 // 이전글
	public TipDTO preReadTip(int tipNum, String searchKey, String searchValue) {
		TipDTO dto =null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			if(searchValue!=null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT tipNum, tipSubject,memId,");
				sb.append(" DATE_FORMAT(tipCreated , '%Y-%m-%d') tipCreated, tipHitCount FROM tip");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND tipNum>?");
				sb.append(" ORDER BY tipNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, tipNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT tipNum, tipSubject,memId,");
				sb.append(" DATE_FORMAT(tipCreated , '%Y-%m-%d') tipCreated, tipHitCount FROM tip");
				sb.append(" WHERE tipNum > ?");
				sb.append(" ORDER BY tipNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, tipNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new TipDTO();
				dto.setTipNum(rs.getInt("tipNum"));
				dto.setTipSubject(rs.getString("tipSubject"));
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
	// 다음글
	public TipDTO nextReadTip(int tipNum, String searchKey, String searchValue) {
		TipDTO dto =null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			if(searchValue!=null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT tipNum, tipSubject,memId,");
				sb.append(" DATE_FORMAT(tipCreated , '%Y-%m-%d') tipCreated, tipHitCount FROM tip");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND tipNum<?");
				sb.append(" ORDER BY tipNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, tipNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT tipNum, tipSubject,memId,");
				sb.append(" DATE_FORMAT(tipCreated , '%Y-%m-%d') tipCreated, tipHitCount FROM tip");
				sb.append(" WHERE tipNum < ?");
				sb.append(" ORDER BY tipNum DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, tipNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new TipDTO();
				dto.setTipNum(rs.getInt("tipNum"));
				dto.setTipSubject(rs.getString("tipSubject"));
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
	// 리플 ==========================
	//댓글 등록
	public int insertTipReply(TipReplyDTO dto){
	      int result=0;
	      PreparedStatement pstmt=null;
	      StringBuffer sb=new StringBuffer();
	      
	      try {
	         sb.append("INSERT INTO tip_Reply(tipR_content, memId, tipNum)");
	         sb.append("VALUES (?, ?, ?)");
	         
	         pstmt=conn.prepareStatement(sb.toString());
	         pstmt.setString(1, dto.getTipR_content());
	         pstmt.setString(2, dto.getMemId());
	         pstmt.setInt(3, dto.getTipNum());
	         
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
	public int dataCountTipReply(int tipNum) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT IFNULL(COUNT(*), 0) FROM tip_Reply where tipNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, tipNum);
			
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
	public List<TipReplyDTO> listTipReply(int tipNum, int start, int end) {
		List<TipReplyDTO> list= new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT tipR_Num, tipNum, memId, tipR_Content,");
			sb.append(" DATE_FORMAT(tipR_Created , '%Y-%m-%d') tipR_Created FROM tip_Reply");
			sb.append(" WHERE tipNum=?");
			sb.append(" ORDER BY tipR_Num DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, tipNum);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				TipReplyDTO dto= new TipReplyDTO();
				
				dto.setTipR_num(rs.getInt("tipR_num"));
				dto.setTipNum(rs.getInt("tipNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setTipR_content(rs.getString("tipR_content"));
				dto.setTipR_created(rs.getString("tipR_created"));
				
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
	   public int deleteTipReply(int tipR_num){
		      int result = 0;
		      PreparedStatement pstmt=null;
		      String sql;
		      
		      sql="DELETE FROM tip_Reply WHERE tipR_num=?";
		      try {
		         pstmt =conn.prepareStatement(sql);
		         pstmt.setInt(1, tipR_num);
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
	   
	   public int recommCount(int tipNum, String memId) {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuffer sb= new StringBuffer();

			try {
				// 게시물 번호에 아이디가 없을 경우 0 있을경우 1
				sb.append("SELECT IFNULL(COUNT(*), 0) FROM tipRecomm");
				sb.append(" WHERE tipNum=? AND memId=?");
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, tipNum);
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
		public int TipRecomm(int tipNum, String memId) {
			int result = 0;
			PreparedStatement pstmt = null;
			String sql;
			String sql2;

			try {
				// 추천수 +1
				sql = "UPDATE tip SET tipRecomm= tipRecomm+1 WHERE tipNum=?";

				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, tipNum);
				
				pstmt.executeUpdate();
				pstmt.close();
				pstmt=null;
				
				//추천 테이블에 추천체크하는데 필요한 정보 추가
				sql2=" INSERT INTO tipRecomm VALUES(?,?)";
				
				pstmt= conn.prepareStatement(sql2);
				
				pstmt.setString(1, memId);
				pstmt.setInt(2, tipNum);

				result = pstmt.executeUpdate();
				pstmt.close();
				pstmt = null;
			} catch (Exception e) {
				System.out.println(e.toString());
			}

			return result;
		}				
		
		
		
		//베스트글 리스트
				public List<TipDTO> listBestTip(){
					
					
					List<TipDTO> list = new ArrayList<>();
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					StringBuffer sb = new StringBuffer();

					try {
						sb.append(" SELECT tipNum, tipRecomm, tipHead, tipSubject, memId,");
						sb.append(" DATE_FORMAT(tipCreated , '%Y-%m-%d') tipCreated, tipHitCount");
						sb.append(" FROM tip WHERE tipRecomm>5 ORDER BY tipRecomm DESC LIMIT 3");

						pstmt = conn.prepareStatement(sb.toString());

						rs = pstmt.executeQuery();

						while (rs.next()) {
							TipDTO dto = new TipDTO();

							dto.setTipNum(rs.getInt("tipNum"));
							dto.setTipRecomm(rs.getInt("tipRecomm"));
							dto.setTipHead(rs.getString("tipHead"));
							dto.setTipSubject(rs.getString("tipSubject"));
							dto.setMemId(rs.getString("memId"));
							dto.setTipCreated(rs.getString("tipCreated"));
							dto.setTipHitCount(rs.getInt("tipHitCount"));

							list.add(dto);
						}
						rs.close();
						pstmt.close();
						
					} catch (Exception e) {
						System.out.println(e.toString());
					}
					
					return list;
					
					
				}

	
	
	
}
