package com.test;

public class Test {
	public static void testnum(){
		TestDAO dao=new TestDAO();
		TestDTO dto=new TestDTO();

		dto.setNum1(33334);
		dto.setNum2(33334);
		
		dao.insertNum(dto);
	}
	
	public static void main(String[] args) {
		testnum();
	}
}

