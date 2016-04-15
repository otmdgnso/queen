package com.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class QnaDAO {
	private Connection conn = DBConn.getConnection();

	// 데이터 추가
	public int insertQna(QnaDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(
					"INSERT INTO question(memId, questSubject, questHead, questContent, questSource) VALUES (?, ?, ?, ?, ?)");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getMemId());
			pstmt.setString(2, dto.getQnaSubject());
			pstmt.setString(3, dto.getQnaHead());
			pstmt.setString(4, dto.getQnaContent());
			pstmt.setString(5, dto.getQnaSource());

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
			sql = " SELECT IFNULL(COUNT(*), 0) FROM question";
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

	public int dataCount(String searchKey, String searchValue) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT IFNULL(COUNT(*), 0) FROM question");
			if (searchKey.equals("memId"))
				sb.append(" WHERE memId LIKE CONCAT('%', ?, '%')");
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

	public int QnaHitCount(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE question SET questhitCount=questhitCount+1  WHERE questNum=?";
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

	// 게시판 리스트
	public List<QnaDTO> listQna(int start, int end) {
		List<QnaDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append(
					"SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (SELECT questNum, questRecomm, questHead, questSubject, memId,");
			sb.append(
					"	DATE_FORMAT(questCreated , '%Y-%m-%d') questCreated, questHitCount FROM question ORDER BY questNum DESC) tb, (SELECT @rownum:=0) T)tb1 ");
			sb.append("	WHERE rnum >= ? and rnum <=?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				QnaDTO dto = new QnaDTO();

				dto.setQnaNum(rs.getInt("questNum"));
				dto.setQnaRecomm(rs.getInt("questRecomm"));
				dto.setQnaHead(rs.getString("questHead"));
				dto.setQnaSubject(rs.getString("questSubject"));
				dto.setMemId(rs.getString("memId"));
				dto.setQnaCreated(rs.getString("questCreated"));
				dto.setQnaHitCount(rs.getInt("questHitCount"));

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

	// 검색될 때 리스트
	public List<QnaDTO> listQna(int start, int end, String searchKey, String searchValue) {
		List<QnaDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;

		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT questNum, questRecomm, questHead, questSubject, memId,");
			sb.append(" DATE_FORMAT(questCreated , '%Y-%m-%d') questCreated, questHitCount FROM question");
			if (searchKey.equals("memId"))
				sb.append(" WHERE memId =?");
			else
				sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
			sb.append(" ORDER BY questNum DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, searchValue);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				QnaDTO dto = new QnaDTO();

				dto.setQnaNum(rs.getInt("questNum"));
				dto.setQnaRecomm(rs.getInt("questRecomm"));
				dto.setQnaHead(rs.getString("questHead"));
				dto.setQnaSubject(rs.getString("questSubject"));
				dto.setMemId(rs.getString("memId"));
				dto.setQnaCreated(rs.getString("questCreated"));
				dto.setQnaHitCount(rs.getInt("questHitCount"));

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
	public QnaDTO readQna(int qnaNum) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;

		try {
			sb.append("SELECT questNum, memId, questHead, questSubject, questContent, questSource,");
			sb.append(" questRecomm, questHitCount,  ");
			sb.append(" DATE_FORMAT(questCreated , '%Y-%m-%d %h:%i:%s') questCreated");
			sb.append(" FROM question");
			sb.append(" WHERE questNum=?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, qnaNum);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();
				dto.setQnaNum(qnaNum);
				dto.setMemId(rs.getString("memId"));
				dto.setQnaSubject(rs.getString("questSubject"));
				dto.setQnaSource(rs.getString("questSource"));
				dto.setQnaContent(rs.getString("questContent"));
				dto.setQnaRecomm(rs.getInt("questRecomm"));
				dto.setQnaHitCount(rs.getInt("questHitCount"));
				dto.setQnaCreated(rs.getString("questCreated"));
				dto.setQnaHead(rs.getString("questHead"));
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
	public int qnaUpdate(QnaDTO dto) {
		PreparedStatement pstmt = null;
		int result = 0;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("UPDATE question set questSubject=?, questHead=?, questContent=?, questSource=? ");
			sb.append(" WHERE questNum=?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getQnaSubject());
			pstmt.setString(2, dto.getQnaHead());
			pstmt.setString(3, dto.getQnaContent());
			pstmt.setString(4, dto.getQnaSource());
			pstmt.setInt(5, dto.getQnaNum());

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

	public int deleteQna(int qnaNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM answer WHERE questNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaNum);
			result = pstmt.executeUpdate();
			
			pstmt.close();
			sql = "DELETE FROM question WHERE questNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaNum);
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

	// 조회수 증가
	public int qnaHitCount(int qnaNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE question SET questHitCount= questHitCount+1 WHERE questNum=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, qnaNum);

			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return result;
	}

	// 이전글
	public QnaDTO preReadQna(int qnaNum, String searchKey, String searchValue) {
		QnaDTO dto = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			if (searchValue != null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT questNum, questSubject,memId,");
				sb.append(" DATE_FORMAT(questCreated , '%Y-%m-%d') questCreated, questHitCount FROM question");
				if (searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND questNum>?");
				sb.append(" ORDER BY questNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, qnaNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT questNum, questSubject,memId,");
				sb.append(" DATE_FORMAT(questCreated , '%Y-%m-%d') questCreated, questHitCount FROM question");
				sb.append(" WHERE questNum > ?");
				sb.append(" ORDER BY questNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, qnaNum);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();
				dto.setQnaNum(rs.getInt("questNum"));
				dto.setQnaSubject(rs.getString("questSubject"));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return dto;
	}

	// 다음글
	public QnaDTO nextReadQna(int qnaNum, String searchKey, String searchValue) {
		QnaDTO dto = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			if (searchValue != null && searchValue.length() != 0) {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT questNum, questSubject,memId,");
				sb.append(" DATE_FORMAT(questCreated , '%Y-%m-%d') questCreated, questHitCount FROM question");
				if (searchKey.equals("memId"))
					sb.append(" WHERE memId =?");
				else
					sb.append(" WHERE " + searchKey + " LIKE CONCAT('%', ? ,'%') ");
				sb.append(" AND questNum<?");
				sb.append(" ORDER BY questNum ASC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, searchValue);
				pstmt.setInt(2, qnaNum);
			} else {
				sb.append("SELECT * FROM (");
				sb.append(" SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
				sb.append(" SELECT questNum, questSubject,memId,");
				sb.append(" DATE_FORMAT(questCreated , '%Y-%m-%d') questCreated, questHitCount FROM question");
				sb.append(" WHERE questNum < ?");
				sb.append(" ORDER BY questNum DESC) tb,");
				sb.append(" (SELECT @rownum:=0) T)tb1");
				sb.append(" WHERE rnum =1");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, qnaNum);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();
				dto.setQnaNum(rs.getInt("questNum"));
				dto.setQnaSubject(rs.getString("questSubject"));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return dto;
	}

	public int insertQuestionReply(QnaReplyDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("INSERT INTO question_Reply(questR_content, memId, questNum)");
			sb.append("VALUES (?, ?, ?)");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getQnaR_content());
			pstmt.setString(2, dto.getMemId());
			pstmt.setInt(3, dto.getQnaNum());

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

	// 댓글 갯수
	public int dataCountQnaReply(int qnaNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT IFNULL(COUNT(*), 0) FROM question_Reply where questNum=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaNum);

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

	// 댓글 리스트
	public List<QnaReplyDTO> listQnaReply(int qnaNum, int start, int end) {
		List<QnaReplyDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(" SELECT questR_Num, questNum, memId, questR_Content,");
			sb.append(" DATE_FORMAT(questR_Created , '%Y-%m-%d') questR_Created FROM question_reply");
			sb.append(" WHERE questNum=?");
			sb.append(" ORDER BY questR_Num DESC) tb,");
			sb.append(" (SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <= ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, qnaNum);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				QnaReplyDTO dto = new QnaReplyDTO();

				dto.setQnaR_num(rs.getInt("questR_Num"));
				dto.setQnaNum(rs.getInt("questNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setQnaR_content(rs.getString("questR_Content"));
				dto.setQnaR_created(rs.getString("questR_Created"));

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

	// 댓글 삭제
	public int deleteQuestionReply(int questR_Num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		sql = "DELETE FROM question_Reply WHERE questR_Num=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, questR_Num);
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

	public int insertAnswer(AnswerDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("INSERT INTO answer(answerContent, memId, questNum)");
			sb.append("VALUES (?, ?, ?)");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getA_content());
			pstmt.setString(2, dto.getMemId());
			pstmt.setInt(3, dto.getQnaNum());

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

	// 댓글 갯수
	public int dataCountAnswer(int qnaNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT IFNULL(COUNT(*), 0) FROM answer where questNum=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaNum);

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

	public List<AnswerDTO> listAnswer(int qnaNum, int start, int end) {
		List<AnswerDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM ( SELECT tb.*,  @rownum:=@rownum+1 AS rnum FROM (");
			sb.append(
					"    SELECT answerNum, a.questNum, a.memId, answerContent, DATE_FORMAT(answerCreated , '%Y-%m-%d') answerCreated, b.memId createId, answerchoose ");
			sb.append("     FROM answer a JOIN question b ON a.questNum=b.questNum    ");
			sb.append(
					"   	WHERE a.questNum=? ORDER BY answerNum DESC) tb, ( SELECT @rownum:=0) T)tb1 WHERE rnum >= ? and rnum <=?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, qnaNum);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				AnswerDTO dto = new AnswerDTO();

				dto.setA_num(rs.getInt("answerNum"));
				dto.setQnaNum(rs.getInt("questNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setA_content(rs.getString("answerContent"));
				dto.setA_created(rs.getString("answerCreated"));
				dto.setCreateId(rs.getString("createId"));
				dto.setAnswerchoose(rs.getInt("answerchoose"));
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

	public int deleteAnswer(int a_Num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		sql = "DELETE FROM answer WHERE answerNum=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, a_Num);
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

	public int selectAnswer(int a_Num) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT answerchoose, questnum FROM answer WHERE answerNum=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, a_Num);

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
		if (result == 1)
			sql = "UPDATE answer SET answerchoose=0  WHERE answerNum=?";
		else
			sql = "UPDATE answer SET answerchoose=1  WHERE answerNum=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, a_Num);
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
	
	// 추천수 증가
				public int QnaRecomm(int qnaNum, String memId) {
					int result = 0;
					PreparedStatement pstmt = null;
					String sql;
					String sql2;
		
					try {
						// 추천수 +1
						sql = "UPDATE question SET questRecomm= questRecomm+1 WHERE questNum=?";
		
						pstmt = conn.prepareStatement(sql);
		
						pstmt.setInt(1, qnaNum);
						
						pstmt.executeUpdate();
						pstmt.close();
						pstmt=null;
						
						//추천 테이블에 추천체크하는데 필요한 정보 추가
						sql2=" INSERT INTO questRecomm VALUES(?,?)";
						
						pstmt= conn.prepareStatement(sql2);
						
						pstmt.setString(1, memId);
						pstmt.setInt(2, qnaNum);
		
						result = pstmt.executeUpdate();
						pstmt.close();
						pstmt = null;
					} catch (Exception e) {
						System.out.println(e.toString());
					}
		
					return result;
				}
				
				// 추천 체크
				public int dataCount(int qnaNum, String memId) {
					int result = 0;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					StringBuffer sb= new StringBuffer();
		
					try {
						// 게시물 번호에 아이디가 없을 경우 0 있을경우 1
						sb.append("SELECT IFNULL(COUNT(*), 0) FROM questRecomm");
						sb.append(" WHERE questNum=? AND memId=?");
						pstmt = conn.prepareStatement(sb.toString());
						pstmt.setInt(1, qnaNum);
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
}
