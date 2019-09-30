package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	public List<BoardVo> getList() {
		return boardDao.getList();
	}

	public void insert(BoardVo vo) {
		boardDao.insert(vo);
	}
	
	public BoardVo get(Long no, Long userNo){
		return boardDao.get(no,userNo);
	}

	public void hit(Long no, Long userNo){
		boardDao.hitInsert(no,userNo);
	}

	
	public void update(BoardVo vo) {
		boardDao.update(vo);
	}

	public void delete(BoardVo vo) {
		boardDao.delete(vo);
		
	}

	public void replyInsert(BoardVo vo) {
		//select -> update -> insert 답글
		
		BoardVo parentVo = boardDao.get(vo.getNo(), 0L);
		
		vo.setGroupNumber(parentVo.getGroupNumber());
		vo.setOrderNumber(parentVo.getOrderNumber() + 1);
		vo.setDepth(parentVo.getDepth() + 1);
		
		boardDao.replyUpdate(vo);
		
		boardDao.replyInsert(vo);
	}
	
}
