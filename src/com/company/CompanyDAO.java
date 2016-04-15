package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class CompanyDAO {
	private Connection conn = DBConn.getConnection();

	// 데이터 추가
	public int insertCompany(CompanyDTO dto) {
	      int result = 0;
	      PreparedStatement pstmt = null;
	      StringBuffer sb = new StringBuffer();

	      try {
	         sb.append("INSERT INTO company(memId, companySubject, companyName, companyDate, companySales");
	         sb.append(" ,companyForm, companyIndustry, companyPlanet, companyWeb, companyContent, companySalary");
	         sb.append(" ,companyScore)");
	         sb.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

	         pstmt = conn.prepareStatement(sb.toString());
	         pstmt.setString(1, dto.getMemId());
	         pstmt.setString(2, dto.getCompanySubject());
	         pstmt.setString(3, dto.getCompanyName());
	         pstmt.setString(4, dto.getCompanyDate());
	         pstmt.setString(5, dto.getCompanySales());
	         pstmt.setString(6, dto.getCompanyForm());
	         pstmt.setString(7, dto.getCompanyIndustry());
	         pstmt.setString(8, dto.getCompanyPlanet());
	         pstmt.setString(9, dto.getCompanyWeb());
	         pstmt.setString(10, dto.getCompanyContent());
	         pstmt.setString(11, dto.getCompanySalary());
	         pstmt.setString(12, dto.getCompanyScore());

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
			sql = " SELECT IFNULL(COUNT(*), 0) FROM company";
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
			sb.append("SELECT IFNULL(COUNT(*), 0) FROM company");
			if (searchKey.equals("memId"))
				sb.append(" WHERE memId LIKE CONCAT('%', ?, '%')");
			else if (searchKey.equals("companyCreated"))
				sb.append(" WHERE companyCreated=? ");
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
	public List<CompanyDTO> listCompany(int start, int end) {
		List<CompanyDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT companyNum, companyRecomm, companyForm, companySubject, memId,");
			sb.append(" DATE_FORMAT(companyCreated , '%Y-%m-%d') companyCreated, companyHitCount FROM company");
			sb.append(" ORDER BY companyNum DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				CompanyDTO dto = new CompanyDTO();

				dto.setCompanyNum(rs.getInt("companyNum"));
				dto.setCompanyRecomm(rs.getInt("companyRecomm"));
				dto.setCompanyForm(rs.getString("companyForm"));
				dto.setCompanySubject(rs.getString("companySubject"));
				dto.setMemId(rs.getString("memId"));
				dto.setCompanyCreated(rs.getString("companyCreated"));
				dto.setCompanyHitCount(rs.getInt("companyHitCount"));

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
	public List<CompanyDTO> listCompany (int start, int end, String searchKey, String searchValue) {
	      List<CompanyDTO> list=new ArrayList<>();
	      PreparedStatement pstmt=null;
	      StringBuffer sb= new StringBuffer();
	      ResultSet rs=null;
	      
	      try {
	    	  	sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
	    	  	sb.append(" SELECT companyNum, companyRecomm, companyForm, companySubject, memId,");
				sb.append(" DATE_FORMAT(companyCreated , '%Y-%m-%d') companyCreated, companyHitCount FROM company");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" ORDER BY companyNum DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
				
				pstmt=conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()) {
					CompanyDTO dto = new CompanyDTO();
					
					dto.setCompanyNum(rs.getInt("companyNum"));
					dto.setCompanyRecomm(rs.getInt("companyRecomm"));
					dto.setCompanyForm(rs.getString("companyForm"));
					dto.setCompanySubject(rs.getString("companySubject"));
					dto.setMemId(rs.getString("memId"));
					dto.setCompanyCreated(rs.getString("companyCreated"));
					dto.setCompanyHitCount(rs.getInt("companyHitCount"));
					
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
	public CompanyDTO readCompany(int companyNum) {
		CompanyDTO dto = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;

		try {
			sb.append("SELECT companyNum, memId, companySubject, companyName, companyDate, companyContent,");
			sb.append(" companySales, companyForm, companyIndustry, companyPlanet, companyWeb, companySalary,");
			sb.append(" companyScore, companyRecomm, companyHitCount,");
			sb.append(" DATE_FORMAT(companyCreated , '%Y-%m-%d %h:%i:%s') companyCreated");
			sb.append(" FROM company");
			sb.append(" WHERE companyNum=?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, companyNum);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new CompanyDTO();
				dto.setCompanyNum(companyNum);
				dto.setMemId(rs.getString("memId"));
				dto.setCompanySubject(rs.getString("companySubject"));
				dto.setCompanyName(rs.getString("companyName"));
				dto.setCompanyDate(rs.getString("companyDate"));
				dto.setCompanyContent(rs.getString("companyContent"));
				dto.setCompanySales(rs.getString("companySales"));
				dto.setCompanyForm(rs.getString("companyForm"));
				dto.setCompanyIndustry(rs.getString("companyIndustry"));
				dto.setCompanyPlanet(rs.getString("companyPlanet"));
				dto.setCompanyWeb(rs.getString("companyWeb"));
				dto.setCompanySalary(rs.getString("companySalary"));
				dto.setCompanyScore(rs.getString("companyScore"));
				dto.setCompanyRecomm(rs.getInt("companyRecomm"));
				dto.setCompanyHitCount(rs.getInt("companyHitCount"));
				dto.setCompanyCreated(rs.getString("companyCreated"));
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
	public int companyUpdate(CompanyDTO dto) {
		PreparedStatement pstmt = null;
		int result = 0;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("UPDATE company set companySubject=?, companyDate=?, companyContent=?, companyName=?,");
			sb.append(" companySales=?, companyForm=?, companyIndustry=?, companyPlanet=?, companyWeb=?, companySalary=?,");
			sb.append(" companyScore=?,");
			sb.append(" companyModified=NOW()");
			sb.append(" WHERE companyNum=?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getCompanySubject());
			pstmt.setString(2, dto.getCompanyDate());
			pstmt.setString(3, dto.getCompanyContent());
			pstmt.setString(4, dto.getCompanyName());
			pstmt.setString(5, dto.getCompanySales());
			pstmt.setString(6, dto.getCompanyForm());
			pstmt.setString(7, dto.getCompanyIndustry());
			pstmt.setString(8, dto.getCompanyPlanet());
			pstmt.setString(9, dto.getCompanyWeb());
			pstmt.setString(10, dto.getCompanySalary());
			pstmt.setString(11, dto.getCompanyScore());
			pstmt.setInt(12, dto.getCompanyNum());

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
    
	public int deleteCompany(int companyNum){
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		
		try {
			
			sql="DELETE FROM companyRecomm WHERE companyNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, companyNum);
			result = pstmt.executeUpdate();
			pstmt.close();
			
			sql="DELETE FROM company WHERE companyNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, companyNum);
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
	public int companyHitCount(int companyNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE company SET companyHitCount= companyHitCount+1 WHERE companyNum=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, companyNum);

			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return result;
	}
	
	 // 이전글
	public CompanyDTO preReadCompany(int companyNum, String searchKey, String searchValue) {
		CompanyDTO dto =null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			if(searchValue!=null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT companyNum, companySubject,memId,");
				sb.append(" DATE_FORMAT(companyCreated , '%Y-%m-%d') companyCreated, companyHitCount FROM company");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND companyNum>?");
				sb.append(" ORDER BY companyNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, companyNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT companyNum, companySubject,memId,");
				sb.append(" DATE_FORMAT(companyCreated , '%Y-%m-%d') companyCreated, companyHitCount FROM company");
				sb.append(" WHERE companyNum > ?");
				sb.append(" ORDER BY companyNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, companyNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new CompanyDTO();
				dto.setCompanyNum(rs.getInt("companyNum"));
				dto.setCompanySubject(rs.getString("companySubject"));
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
	// 다음글
	public CompanyDTO nextReadCompany(int companyNum, String searchKey, String searchValue) {
		CompanyDTO dto =null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			if(searchValue!=null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT companyNum, companySubject,memId,");
				sb.append(" DATE_FORMAT(companyCreated , '%Y-%m-%d') companyCreated, companyHitCount FROM company");
				if(searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND companyNum<?");
				sb.append(" ORDER BY companyNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, companyNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT companyNum, companySubject,memId,");
				sb.append(" DATE_FORMAT(companyCreated , '%Y-%m-%d') companyCreated, companyHitCount FROM company");
				sb.append(" WHERE companyNum < ?");
				sb.append(" ORDER BY companyNum DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, companyNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new CompanyDTO();
				dto.setCompanyNum(rs.getInt("companyNum"));
				dto.setCompanySubject(rs.getString("companySubject"));
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
	// 리플 ==========================
	//댓글 등록
	public int insertCompanyReply(CompanyReplyDTO dto){
	      int result=0;
	      PreparedStatement pstmt=null;
	      StringBuffer sb=new StringBuffer();
	      
	      try {
	         sb.append("INSERT INTO companyReply(companyR_content, memId, companyNum)");
	         sb.append("VALUES (?, ?, ?)");
	         
	         pstmt=conn.prepareStatement(sb.toString());
	         pstmt.setString(1, dto.getCompanyR_content());
	         pstmt.setString(2, dto.getMemId());
	         pstmt.setInt(3, dto.getCompanyNum());
	         
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
	public int dataCountCompanyReply(int companyNum) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT IFNULL(COUNT(*), 0) FROM companyReply where companyNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, companyNum);
			
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
	public List<CompanyReplyDTO> listCompanyReply(int companyNum, int start, int end) {
		List<CompanyReplyDTO> list= new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT companyR_Num, companyNum, memId, companyR_Content,");
			sb.append(" DATE_FORMAT(companyR_Created , '%Y-%m-%d') companyR_Created FROM companyReply");
			sb.append(" WHERE companyNum=?");
			sb.append(" ORDER BY companyR_Num DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, companyNum);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				CompanyReplyDTO dto= new CompanyReplyDTO();
				
				dto.setCompanyR_num(rs.getInt("companyR_num"));
				dto.setCompanyNum(rs.getInt("companyNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setCompanyR_content(rs.getString("companyR_content"));
				dto.setCompanyR_created(rs.getString("companyR_created"));
				
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
	   public int deleteCompanyReply(int companyR_num){
		      int result = 0;
		      PreparedStatement pstmt=null;
		      String sql;
		      
		      sql="DELETE FROM companyReply WHERE companyR_num=?";
		      try {
		         pstmt =conn.prepareStatement(sql);
		         pstmt.setInt(1, companyR_num);
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
		public int recommCount(int companyNum, String memId) {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuffer sb= new StringBuffer();

			try {
				// 게시물 번호에 아이디가 없을 경우 0 있을경우 1
				sb.append("SELECT IFNULL(COUNT(*), 0) FROM companyRecomm");
				sb.append(" WHERE companyNum=? AND memId=?");
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, companyNum);
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
		public int CompanyRecomm(int companyNum, String memId) {
			int result = 0;
			PreparedStatement pstmt = null;
			String sql;
			String sql2;

			try {
				// 추천수 +1
				sql = "UPDATE company SET companyRecomm= companyRecomm+1 WHERE companyNum=?";

				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, companyNum);
				
				pstmt.executeUpdate();
				pstmt.close();
				pstmt=null;
				
				//추천 테이블에 추천체크하는데 필요한 정보 추가
				sql2=" INSERT INTO companyRecomm VALUES(?,?)";
				
				pstmt= conn.prepareStatement(sql2);
				
				pstmt.setString(1, memId);
				pstmt.setInt(2, companyNum);

				result = pstmt.executeUpdate();
				pstmt.close();
				pstmt = null;
			} catch (Exception e) {
				System.out.println(e.toString());
			}

			return result;
		}				
		
		
		//베스트글 리스트
		public List<CompanyDTO> listBestCompany(){
			
			
			List<CompanyDTO> list=new ArrayList<CompanyDTO>();
			StringBuffer sb=new StringBuffer();
			PreparedStatement pstmt=null;
			ResultSet rs=null;

			try {
				sb.append("SELECT companyNum, companyRecomm, companyForm, companySubject, memId,");
				sb.append(" DATE_FORMAT(companyCreated , '%Y-%m-%d') companyCreated, companyHitCount");
				sb.append(" FROM company WHERE companyRecomm>5 ORDER BY companyRecomm DESC LIMIT 3");
				
				pstmt=conn.prepareStatement(sb.toString());
				rs=pstmt.executeQuery();
				
				while(rs.next()){
					CompanyDTO dto=new CompanyDTO();
					
					dto.setCompanyNum(rs.getInt("companyNum"));
					dto.setCompanyRecomm(rs.getInt("companyRecomm"));
					dto.setCompanyForm(rs.getString("companyForm"));
					dto.setCompanySubject(rs.getString("companySubject"));
					dto.setCompanyCreated(rs.getString("companyCreated"));
					dto.setCompanyHitCount(rs.getInt("companyHitCount"));
					dto.setMemId(rs.getString("memId"));
					
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
