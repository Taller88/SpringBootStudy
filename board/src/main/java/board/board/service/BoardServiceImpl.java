package board.board.service;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import board.board.common.FileUtils;
import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.mapper.BoardMapper;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		
		return boardMapper.selectBoardList();
	}

	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardMapper.insertBoard(board);
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false) {
			boardMapper.insertBoardFileList(list);
		}
	}

	@Override
	public BoardDto selectBoardDetail(int boardIdx)  throws Exception{
		boardMapper.updateHitCount(boardIdx);
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		
		return board;
	}

	@Override
	public void updateBoard(BoardDto board) throws Exception {
		boardMapper.updateBoard(board);
	}

	@Override
	public void deleteBoard(int board) throws Exception {
		boardMapper.deleteBoard(board);
	}

	
	
}
