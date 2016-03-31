package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.util.DBConn;

public class TestDAO {
	private Connection conn = DBConn.getConnection();

	public int insertNum(TestDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("INSERT INTO teat values(?,?)");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, dto.getNum1());
			pstmt.setInt(2, dto.getNum2());
			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
}
