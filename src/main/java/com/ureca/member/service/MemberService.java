package com.ureca.member.service;

import java.sql.SQLException;

import com.ureca.member.dto.Member;


public interface MemberService {

	//멤버 존재유무(로그인에 사용) => login으로 바꾸면 좋겠다
//  public Member findMember(Member member)throws SQLException;
    public Member login(Member member)throws SQLException;
	Member userInfo(String id) throws SQLException;
	
	void saveRefreshToken(String id, String refreshToken) throws Exception;
	Object getRefreshToken(String id) throws Exception;
	void deleRefreshToken(String id) throws Exception;


}
