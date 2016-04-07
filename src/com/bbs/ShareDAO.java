package com.bbs;

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
	public int insertDocu(ShareDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("INSERT INTO share(shareNum, memId, shareSubject, shareContent) ");
			sb.append(" VALUES (share_seq.NEXTVAL,?,?,?)");

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
				sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%' ");

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
			sb.append(" shareCreated, shareHitCount FROM share");
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
	
	public List<ShareDTO> listShare(int start, int end, String searchKey, String searchValue) {
		
	}

	// 게시판 글보기
	public ShareDTO readShare(int shareNum) {
		ShareDTO dto = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;

		try {
			sb.append("SELECT shareNum, memId, shareSubject, shareContent,");
			sb.append(" shareHitCount, shareCreated");
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
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
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
	
	
	
	
	
	
	
	
	
}
