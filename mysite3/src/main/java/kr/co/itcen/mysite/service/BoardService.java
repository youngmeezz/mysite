package kr.co.itcen.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.itcen.mysite.exception.FileuploadException;
import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.Pagination;


@Service
public class BoardService {
	
	private static final String SAVE_PATH = "/uploads";
	private static final String URL_PREFIX = "/images";

	@Autowired
	private BoardDao boardDao;

	public List<BoardVo> getList(String keyword, Pagination pagination) {
		return boardDao.getList(keyword, pagination);
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
	
	public int getBoardCount(String keyword) {
		return boardDao.countSelect(keyword);
	}


	
	//파일 업로드
	public String restore(MultipartFile multipartFile) {
			String url = "";
			
			try {
				
					if(multipartFile == null) {
						return url;
					}
					
					String originalFilename = multipartFile.getOriginalFilename();
					String saveFileName = generateSaveFilename(originalFilename.substring(originalFilename.lastIndexOf('.')+1));
					long fileSize = multipartFile.getSize();
					
				
					System.out.println("#############"+originalFilename);
					System.out.println("#############"+saveFileName);
					System.out.println("#############"+fileSize);
					
			
					byte[] fileData = multipartFile.getBytes();
					OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
					os.write(fileData);
					os.close();
					
					url = URL_PREFIX + "/" + saveFileName;
				} catch (IOException e) {
					throw new FileuploadException();
				
				}
				
			
				return url;
			}


		private String generateSaveFilename(String extName) {
			
			String filename = "";
			
			Calendar calendar = Calendar.getInstance();
			filename += calendar.get(Calendar.YEAR);
			filename += calendar.get(Calendar.MONTH);
			filename += calendar.get(Calendar.DATE);
			filename += calendar.get(Calendar.HOUR);
			filename += calendar.get(Calendar.MINUTE);
			filename += calendar.get(Calendar.SECOND);
			filename += calendar.get(Calendar.MILLISECOND);
			filename += ("."+extName);

			return filename;
		}
	}
