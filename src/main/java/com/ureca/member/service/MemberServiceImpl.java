package com.ureca.member.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ureca.member.dao.MemberDAO;
import com.ureca.member.dto.Member;

@Service
public class MemberServiceImpl implements MemberService {

	MemberDAO memberDAO;

	public MemberServiceImpl(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	@Override
	public Member login(Member member) throws SQLException {
		return memberDAO.selectLogin(member);
	}

	@Override
	public Member userInfo(String id) throws SQLException {
		return memberDAO.selectUserInfo(id);
	}

	@Override
	public void saveRefreshToken(String id, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", id);
		map.put("token", refreshToken);
		memberDAO.saveRefreshToken(map);

		
	}

	@Override
	public Object getRefreshToken(String id) throws Exception {
		return memberDAO.getRefreshToken(id);
	}

	@Override
	public void deleRefreshToken(String id) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", id);
		map.put("token", null);
		memberDAO.deleteRefreshToken(map);
		
	}

//	@Override
//	public Member findMember(Member member) throws SQLException {
//		return memberDAO.selectLogin(member);
//	}

}
