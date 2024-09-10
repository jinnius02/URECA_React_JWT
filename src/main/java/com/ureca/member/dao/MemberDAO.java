package com.ureca.member.dao;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ureca.member.dto.Member;

@Mapper
public interface MemberDAO {
    public Member selectLogin(Member member)throws SQLException;
	Member selectUserInfo(String id) throws SQLException;

	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String id) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;

}
