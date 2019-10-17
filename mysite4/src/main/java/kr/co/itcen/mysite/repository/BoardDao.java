package kr.co.itcen.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.exception.BoardDaoException;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.Pagination;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;

	/////select 게시판 첫 조회하기User랑 Board랑 조인한 부분 (성공) 검색 기능 페이징 처리 필요/////
	public List<BoardVo> getList(String keyword, Pagination pagination) throws BoardDaoException{
		
		Map<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("page", (pagination.getCurrentPage() - 1) * pagination.getListSize());
		map.put("listSize", pagination.getListSize());
		
		List<BoardVo> result = sqlSession.selectList("board.getList", map);
		return result;
		
	}
	
	/////insert 글쓰기(성공) /////
	public Boolean insert(BoardVo boardVo) {

		int count = sqlSession.insert("board.insert", boardVo);
		return count == 1;
		
	}
	
	
	/////select 게시글 제목 클릭해서 View할 내용 조회하기(성공)/////
	public BoardVo get(Long no, Long userNo) {
		return sqlSession.selectOne("board.getByNo", no);
	}
	
	
	/////select 게시글 제목 클릭해서 View할 내용 조회하면 조회수 증가하기(성공)/////
	public Boolean hitInsert(Long no, Long userNo) {
		int count = sqlSession.insert("board.hit",no);
		return count == 1;
	}
	

	/////update 수정하기(성공)/////

	public Boolean update(BoardVo vo){
		
		int result = sqlSession.update("board.update",vo);
		return result == 1;
	}

	
	/////delete 삭제하기(성공)/////
	
	public Boolean delete(BoardVo vo) {
	
		//board.delete는 sql쿼리문연결입니다. board는 username이고 delete는 sql의 id입니다.
		int result = sqlSession.delete("board.delete",vo);
		return result == 1;
	}
	
	
	
	//// replyInsert 답글쓰기 (진행중)//////
	//// 여기서 g_no랑 o_no도 함께 update하는 구문 필요 -> 이거 controller에서 구현하기
	public void replyInsert(BoardVo boardVo) {
		
		sqlSession.insert("board.replyInsert", boardVo);
	}

	public void replyUpdate(BoardVo boardVo) {

		sqlSession.update("board.replyUpdate", boardVo);
	}

	public int countSelect(String keyword) {
		
		return sqlSession.selectOne("board.countSelect", keyword);
	}
}
	

	

	