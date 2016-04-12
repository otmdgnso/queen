package com.portfolio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class PortfolioDAO {
	private Connection conn = DBConn.getConnection();

	public int insertPortfolio(PortfolioDTO dto, int cnt) {
		int result = 0;

		PreparedStatement pstmt = null;
		String sql;

		if (cnt == 1) {
			String fields = "subject, content, imageFilename, memId, imgCnt";
			sql = "INSERT INTO portfolio (" + fields + ") VALUES (?, ?, ?, ?, ?)";
			try {
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, dto.getSubject());
				pstmt.setString(2, dto.getContent());
				pstmt.setString(3, dto.getImageFilename());
				pstmt.setString(4, dto.getMemId());
				pstmt.setInt(5, dto.getImgCnt());

				result = pstmt.executeUpdate();
				pstmt.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		} else if (cnt == 2) {
			String fields = "subject, content, memId, imageFilename,imageFilename2, imgCnt";
			sql = "INSERT INTO portfolio (" + fields + ") VALUES (?, ?, ?, ?, ?, ?)";
			try {
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, dto.getSubject());
				pstmt.setString(2, dto.getContent());
				pstmt.setString(3, dto.getMemId());
				pstmt.setString(4, dto.getImageFilename());
				pstmt.setString(5, dto.getImageFilename2());
				pstmt.setInt(6, dto.getImgCnt());

				result = pstmt.executeUpdate();
				pstmt.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		} else if (cnt == 3) {
			String fields = "subject, content, memId, imageFilename, imageFilename2, imageFilename3, imgCnt";
			sql = "INSERT INTO portfolio (" + fields + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
			try {
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, dto.getSubject());
				pstmt.setString(2, dto.getContent());
				pstmt.setString(3, dto.getMemId());
				pstmt.setString(4, dto.getImageFilename());
				pstmt.setString(5, dto.getImageFilename2());
				pstmt.setString(6, dto.getImageFilename3());
				pstmt.setInt(7, dto.getImgCnt());
				
				result = pstmt.executeUpdate();
				pstmt.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		} else if (cnt == 4) {
			String fields = "subject, content, memId, imageFilename, imageFilename2, imageFilename3, imageFilename4, imgCnt";
			sql = "INSERT INTO portfolio (" + fields + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, dto.getSubject());
				pstmt.setString(2, dto.getContent());
				pstmt.setString(3, dto.getMemId());
				pstmt.setString(4, dto.getImageFilename());
				pstmt.setString(5, dto.getImageFilename2());
				pstmt.setString(6, dto.getImageFilename3());
				pstmt.setString(7, dto.getImageFilename4());
				pstmt.setInt(8, dto.getImgCnt());
				
				result = pstmt.executeUpdate();
				pstmt.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}

		return result;
	}

	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT IFNULL(COUNT(*), 0) FROM portfolio";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);
			rs.close();
			pstmt.close();

			rs = null;
			pstmt = null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return result;
	}

	public List<PortfolioDTO> listPortfolio(int start, int end) {
		List<PortfolioDTO> list = new ArrayList<PortfolioDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append("    SELECT Num , memId, subject, content, imageFilename FROM portfolio ORDER BY num DESC)tb,  ");
			sb.append("       (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PortfolioDTO dto = new PortfolioDTO();
				dto.setNum(rs.getInt("Num"));
				dto.setMemId(rs.getString("memId"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setImageFilename(rs.getString("imageFilename"));

				list.add(dto);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return list;
	}

	public PortfolioDTO readPortfolio(int num) {
		PortfolioDTO dto = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT Num, memId, Subject, Content, ");
			sb.append("    DATE_FORMAT(Created , '%Y-%m-%d') Created , ");
			sb.append("    imageFilename, imageFilename2, imageFilename3, imageFilename4, imgCnt   FROM portfolio ");
			sb.append("    WHERE num=?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new PortfolioDTO();
				dto.setNum(rs.getInt("num"));
				dto.setMemId(rs.getString("memId"));
				dto.setSubject(rs.getString("Subject"));
				dto.setContent(rs.getString("Content"));
				dto.setCreated(rs.getString("Created"));
				dto.setImageFilename(rs.getString("imageFilename"));
				dto.setImageFilename2(rs.getString("imageFilename2"));
				dto.setImageFilename3(rs.getString("imageFilename3"));
				dto.setImageFilename4(rs.getString("imageFilename4"));
				dto.setImgCnt(rs.getInt("imgCnt"));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return dto;
	}

	public int updatePortfolio(PortfolioDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("UPDATE portfolio SET subject=?");
			sb.append("   ,content=?, imageFilename=? ");
			sb.append("   WHERE Num=?");
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getImageFilename());
			pstmt.setInt(4, dto.getNum());

			result = pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return result;
	}

	// 조회수 증가하기
	public int updateHitCount(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE portfolio SET hitCount=hitCount+1  WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}

	public int deletePortfolio(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM portfolio WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return result;
	}

	/////////////////////////////////////////////////////////////////////////// 리플

	public int insertReply(PortfolioReplyDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("INSERT INTO portfolioreply(num, memId, content) ");
			sb.append(" VALUES (?, ?, ?)");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, dto.getNum());
			pstmt.setString(2, dto.getMemId());
			pstmt.setString(3, dto.getContent());

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

	public int dataCountReply(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT IFNULL(COUNT(*), 0) FROM portfolioreply WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

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

	public List<PortfolioReplyDTO> listReply(int num, int start, int end) {
		List<PortfolioReplyDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM (SELECT @rownum:=@rownum+1 AS rnum, tb.* FROM (");
			sb.append("		  SELECT replyNum, portfolioReply.num, portfolioReply.memId, portfolioReply.content, ");
			sb.append("		DATE_FORMAT(portfolioReply.Created , '%Y-%m-%d') Created");
			sb.append("			 FROM portfolioReply  ");
			sb.append("	WHERE num=?  ORDER BY replyNum DESC) tb,(SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PortfolioReplyDTO dto = new PortfolioReplyDTO();

				dto.setReplyNum(rs.getInt("replyNum"));
				dto.setNum(rs.getInt("num"));
				dto.setMemId(rs.getString("memId"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));

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

	public int deleteReply(int replyNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		sql = "DELETE FROM portfolioreply WHERE replyNum=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyNum);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
}
