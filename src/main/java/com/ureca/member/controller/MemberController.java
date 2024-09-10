package com.ureca.member.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.ureca.member.dto.Member;
import com.ureca.member.service.MemberService;
import com.ureca.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/member")
public class MemberController {

	MemberService memberService;
	JWTUtil jwtUtil;

	public MemberController(MemberService memberService, JWTUtil jwtUtil) {
		this.memberService = memberService;//생성자 주입
		this.jwtUtil = jwtUtil;            //생성자 주입
	}

	//멤버 존재유무
//	@PostMapping("/findMember")
//	public Member findMember(@RequestBody Member member) throws SQLException {
//	    return memberService.findMember(member);
//	}

//	@PostMapping("/findMember")  //==> /login으로 변경

	//리턴값 ==> 클라이언트(자바스크립트)에게 전달하는 데이터 ({}, [])
	//리턴값 ResponseEntity  ==>  전달데이터 + 서버의 상태
	//ResponseEntity<리턴값의 자료형>

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Member member)  {
		System.out.println("loginMember>>>"+ member);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		//요청 처리후 서버의 상태!!
		HttpStatus status= HttpStatus.ACCEPTED;
		try {


			//		Member loginMember = memberService.findMember(member);
			Member loginMember = memberService.login(member);
			System.out.println("loginMember>>>"+ loginMember);

			if(loginMember != null) {
				String accessToken = jwtUtil.createAccessToken(loginMember.getId());
				String refreshToken = jwtUtil.createRefreshToken(loginMember.getId());
				System.out.println("access token : "+ accessToken);
				System.out.println("refresh token : "+ refreshToken);

//			발급받은 refresh token 을 DB에 저장.
				memberService.saveRefreshToken(loginMember.getId(), refreshToken);

//			JSON 으로 token 전달.
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);

				status = HttpStatus.CREATED; //201
			}else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해 주세요.");
				status = HttpStatus.UNAUTHORIZED; //401
			}

		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("로그인 에러 발생: "+ e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;//500
		}
//	    return loginMember;
//	    return resultMap;
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}



	//	@Operation(summary = "회원인증", description = "회원 정보를 담은 Token 을 반환한다.")
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") String userId,
//			@PathVariable("userId") @Parameter(description = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			System.out.println ("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				Member memberDto = memberService.userInfo(userId);
				resultMap.put("userInfo", memberDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				System.out.println("정보조회 실패 : {}"+ e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			System.out.println("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	//	@Operation(summary = "로그아웃", description = "회원 정보를 담은 Token 을 제거한다.")
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable ("userId") String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			System.out.println("로그아웃 실패 : {}"+ e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	//	@Operation(summary = "Access Token 재발급", description = "만료된 access token 을 재발급 받는다.")
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody Member member, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		System.out.println("token : {"+token+"}, memberDto : {"+member+"}");
		if (jwtUtil.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(member.getId()))) {
				String accessToken = jwtUtil.createAccessToken(member.getId());
				System.out.println("token : {}"+ accessToken);
				System.out.println("정상적으로 access token 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			System.out.println("refresh token 도 사용 불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}

