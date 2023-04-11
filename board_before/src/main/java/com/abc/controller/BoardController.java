package com.abc.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.abc.service.BoardService;
import com.abc.service.ReplyService;
import com.abc.util.FileService;
import com.abc.vo.Board;
import com.abc.vo.Reply;
import com.fasterxml.jackson.databind.ser.std.FileSerializer;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private BoardService bService;
	
	@Autowired
	private ReplyService rService;
	
	private final String REDIRECT_LIST = "redirect:/list";
	
	// application.properites에 적어놓은 속성이름을
	// @Value 옆에 적으면 해당하는 값을 가져올 수 있음
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath; // 위의 속성 값을 가져와서 좌측 멤버 변수에 저장
		
	// 목록보기
//	@GetMapping("/list") 
	public String list(Model model) {
		List<Board> list = bService.getBoardList();
		
		log.debug("list의 사이즈 : {}", list.size());
		
		model.addAttribute("bList", list);
		return "board/list";
	}
	
	// 글쓰기 화면표시
	@GetMapping("/write")
	public String write() {
		// 세션에 정보가 있는지 확인하고 있으면 넘어감
		// 없으면 목록으로 가기
		return "board/write";
	}
	
	// 글쓰기 실행
	@PostMapping("/write")
	public String write(Board board, HttpSession session, @RequestParam MultipartFile file) {
		log.debug("write(Board)");
		log.debug("Board : {}", board);
		log.debug("filePath : {}", file.getOriginalFilename());
		
		// 사용자 id를 세션에서 가져와서 board 객체에 넣기
		board.setUserId((String)session.getAttribute("login"));
		
		// file이 있으면 저장하고 아니면 건너뛰자
		if(!file.isEmpty()) { // 파일이 비어있지 않다면
			// FileService 사용해서 파일 저장 & 저장된 이름 가져오기
			String savedFileName = FileService.saveFile(file, uploadPath);
			log.debug("savedFileName : {}", savedFileName);
			
			// Board 객체에 원래 파일 이름과 서버에 저장된 파일 이름
			board.setOriginalFile(file.getOriginalFilename());
			board.setSavedFile(savedFileName);
		}
		
		// Service 호출
		bService.write(board);

		return REDIRECT_LIST; // 글 저장 후에는 목록으로 돌아간다
	}
	
	// 글 보기
	@GetMapping("/read")
	public String read(Model model, int boardNum) {
		log.debug("read()");
		log.debug("boardNum : {}", boardNum);
		
		// Service 호출
		Board b = bService.read(boardNum);
		
		// Model 객체에 글 정보 담기
		model.addAttribute("board", b);
		
		return "board/read";
	}
	
	// 수정하기 화면표시
	@GetMapping("/update")
	public String update(Model model, int boardNum) {
		log.debug("update()");
		log.debug("boardNum : {}", boardNum);
		
		// Service 호출
		Board b = bService.read(boardNum);
		
		// Model 객체에 글 정보 담기
		model.addAttribute("board", b);

		return "board/update";
	}
	
	// 수정하기 실행
	@PostMapping("/update")
	public String update(Board board) {
		log.debug("update(Board)");
		log.debug("Board : {}", board);
		
		// Service 호출
		bService.update(board);
		
		// 나중에 시간이 된다면 해당하는 글로 다시 가고 싶음
		return REDIRECT_LIST;
	}
	
	// 글 삭제
	@GetMapping("/delete")
	public String delete(int boardNum) {
		log.debug("delete()");
		log.debug("boardNum : {}", boardNum);
		
		// Service 호출
		bService.delete(boardNum);
		
		return REDIRECT_LIST;
	}
	
	// 글 검색
	@GetMapping("/search")
	public String search(Model model, String keyword, String category){
		log.debug("serch()");
		log.debug("keyword : {}", keyword);
		
		List<Board> bList = bService.selectBoardByKeyword(keyword, category);
		
		log.debug("검색 결과의 수 : {}", bList.size());
		
		// 검색 결과를 model 객체에 담는다
		model.addAttribute("bList", bList);
		model.addAttribute("keyword", keyword); // 검색창에 검색어 표시하기 위한것
		model.addAttribute("category", category); // 검색창에 분류 표식하기 위한것
		
		return "board/list";
	}
	
	// 글 검색(2)
	@GetMapping("/list")
	public String list1(String category, String keyword, Model model) {
		
		log.debug("category : {}, keyword : {}", category, keyword);
		
		List<Board> bList = bService.selectAllBoard_1(category, keyword);
		
		log.debug("bList size : {}", bList.size());
		
		// 반환된 리스트를 모델에 포함
		model.addAttribute("bList", bList);
		model.addAttribute("keyword", keyword); // 검색창에 검색어 표시하기 위한것
		model.addAttribute("category", category); // 검색창에 분류 표식하기 위한것
		
		return "board/list";
	}
	
	// 글 추천하기
	@PostMapping("/recommend")
	@ResponseBody // AJAX등으로 데이터만 보내야 할때 (화면 새로고침X 일부분)
	public String recommend(int boardNum) { //boardNum은 read.html의 AJAX에서 지정해 놓은 Key값
		
		log.debug("boardNum : {}", boardNum);
		
		int currentRec = bService.updateRecommend(boardNum);
		
		log.debug("현재 추천수 : {}", currentRec);
		
		return currentRec + ""; // 숫자를 String형으로 형변환 + ""
	}
	
	// 댓글 쓰기
	@PostMapping("/insertReply")
	@ResponseBody
	public String insertReply(String replyText, int boardNum, HttpSession session) {
		log.debug("replyText : {}", replyText);
		log.debug("boardNum : {}", boardNum);
		
		Reply r = new Reply();
		
		r.setReplyText(replyText);
		r.setBoardNum(boardNum);
		r.setUserId((String)session.getAttribute("login")); //login은 Object라 String으로 형변환
		
		rService.insertReply(r);
		
		return "OK";
	}
	
	// 댓글 가져오기
	@PostMapping("/loadReply")
	@ResponseBody
	public List<Reply> loadReply(int boardNum) {
		log.debug("loadReply()");
		log.debug("boardNum : {}" ,boardNum);
		
		// 하나의 글에 달려있는 모든 댓글 가져오기
		List<Reply> replyList = rService.getAllReply(boardNum);
		
		log.debug("replyList size : {}", replyList.size());
		
		return replyList; // -> read.html -> success: function(data)
	}
	
	// 하나의 댓글 정보를 가지고 오는 메서드
	@PostMapping("/getOneReply")
	@ResponseBody
	public Reply getOneReply(int replyNum) {  // -> read.html -> data: {"replyNum" :}
		log.debug("replyNum : {}", replyNum);
		
		// 클라이언트에서 온 댓글번호로 해당하는 댓글 정보를 찾는다
		Reply r = rService.getOneReply(replyNum);
		
		log.debug("Reply : {}", r);
		
		return r;
	}
	
	// 댓글 수정할떄
	@PostMapping("/updateReply")
	@ResponseBody
	public String updateReply(String replyText, int replyNum) {
		
		log.debug("replyText : {}", replyText);
		log.debug("replyNum : {}", replyNum);
		
		Reply r = new Reply();
		
		r.setReplyText(replyText);
		r.setReplyNum(replyNum);
		
		rService.updateReply(r);
		
		return "OK";
	}
	
	@GetMapping("/deleteReply")
	@ResponseBody
	public String deleteReply(int replyNum) {
		log.debug("replyNum : {}", replyNum);
		
		rService.deleteReply(replyNum);
		
		return "OK";
	}
	
	// 글 추천하기
	@PostMapping("/recommend2")
	@ResponseBody
	public String recommend(HttpSession session, int boardNum) {
		
		String userId = (String) session.getAttribute("login");
		
		boolean result = bService.recommend(userId, boardNum);
		
		if (result) {
			return "OK";
		} else {
			return "NG";
		}
	}
	
	// 파일 다운로드 받기
	@GetMapping("/download")
	public String downloadFile(int boardNum, HttpServletResponse response) {
		log.debug("boardNum : {}", boardNum);
		
		// 글 정보 조회
		Board board = bService.read(boardNum);
		
		// 원래 파일명
		String originalFile = board.getOriginalFile();
		
		// 응답 객체 준비
		try {
			response.setHeader("Content-Disposition", 
								"attachment;filename=" + URLEncoder.encode(originalFile, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		// 서버에 저장된 파일 가져오기
		String fullPath = uploadPath + "/" + board.getSavedFile();
		
		// 파일을 읽을 파일 스트림하고 클라이언트에 전달할 출력 스트림 준비
		FileInputStream fis = null;
		ServletOutputStream sos = null;
		
		try {
			// 서버에 저장된 파일 주소에 있는 파일을 입력스트림에 읽어 옴
			fis = new FileInputStream(fullPath);
			// 응답 객체의 출력스트림 설정
			sos = response.getOutputStream();
			
			// Spring이 제공하는 파일관련 유틸 사용해서 입력->출력 스트림으로 파일 이동
			FileCopyUtils.copy(fis, sos);
			
			// 사용이 끝난 스트림은 닫는다
			fis.close();
			sos.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/list"; // 얘는 동작 안함
	}
	
	@GetMapping("/display")
	public ResponseEntity<Resource> display(int boardNum) {
		
		log.debug("boardNum : {}", boardNum);
		
		Board board = bService.read(boardNum);
		
		log.debug("board : {}", board);
		
		// 서버에 저장된 파일 가져오기
		String fullPath = uploadPath + "/" + board.getSavedFile();
				
		Resource resource = new FileSystemResource(fullPath);
		
		// 리소스가 없으면 NOT_FOUND error 발생
		if(!resource.exists()) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND); // 리소스와 404에러 리턴
		}
		
		HttpHeaders header = new HttpHeaders();
		
		Path filePath = null;
		
		try {
			filePath = Paths.get(fullPath);
			header.add("Content-type", Files.probeContentType(filePath));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource> (resource, header, HttpStatus.OK);
	}
}
