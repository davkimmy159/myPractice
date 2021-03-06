package kr.co.ps119.event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ps119.repository.AuthorityRepository;
import kr.co.ps119.repository.BoardHistoryRepository;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.repository.MemberAuthorityRepository;
import kr.co.ps119.repository.MemberRepository;
import kr.co.ps119.repository.MemoRepository;

@Service
@Transactional
public class TestDataCreationEventListener implements ApplicationListener<ApplicationReadyEvent> {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MemberRepository memberRepo;

	@Autowired
	private MemberAuthorityRepository memAuthRepo;
	
	@Autowired
	private AuthorityRepository authRepo;

	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private MemoRepository memoRepo;
	
	@Autowired
	private BoardHistoryRepository boardHisRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		
		/*
		Member member1= new Member("mail1@mail.com", "testUser1", passwordEncoder.encode("password"));
		MemberAuthority memAuth1A = new MemberAuthority();
		memAuth1A.setAuthority(authRepo.findOne(1));
		memAuth1A.setMember(member1);
		MemberAuthority memAuth1B = new MemberAuthority();
		memAuth1B.setAuthority(authRepo.findOne(2));
		memAuth1B.setMember(member1);
		
		Member member2 = new Member("mail2@mail.com", "testUser2", passwordEncoder.encode("password"));
		MemberAuthority memAuth2 = new MemberAuthority();
		memAuth2.setAuthority(authRepo.findOne(1));
		memAuth2.setMember(member2);
		
		Member member3 = new Member("mail3@mail.com", "testUser3", passwordEncoder.encode("password"));
		MemberAuthority memAuth3 = new MemberAuthority();
		memAuth3.setAuthority(authRepo.findOne(1));
		memAuth3.setMember(member3);
		
		Member member4 = new Member("mail4@mail.com", "testUser4", passwordEncoder.encode("password"));
		MemberAuthority memAuth4 = new MemberAuthority();
		memAuth4.setAuthority(authRepo.findOne(1));
		memAuth4.setMember(member4);
		
		Member member5 = new Member("mail5@mail.com", "testUser5", passwordEncoder.encode("password"));
		MemberAuthority memAuth5 = new MemberAuthority();
		memAuth5.setAuthority(authRepo.findOne(1));
		memAuth5.setMember(member5);

		memberRepo.save(member1);
		memberRepo.save(member2);
		memberRepo.save(member3);
		memberRepo.save(member4);
		memberRepo.save(member5);
		
		Board board;
		Board board249 = null;
		
		for(int i = 1; i <= 250; i++) {
			board = new Board();
			board.setTitle("title " + i);
			
			switch(i % 4) {
				case 1:
					board.setMember(member1);
					break;
				case 2:
					board.setMember(member2);
					break;
				case 3:
					board.setMember(member3);
					break;
				case 0:
					board.setMember(member4);
					break;
			}
			boardRepo.save(board);
			if(i == 249) {
				board249 = board;
			}
		}
		
		Memo memo;
		for(int i = 1; i <= 101; i++) {
			memo = new Memo();
			memo.setBoard(board249);
			memo.setTitle("memo title " + i);
			memo.setContent("memo content " + i);
			
			switch(i % 4) {
				case 1:
					memo.setMember(member1);
					break;
				case 2:
					memo.setMember(member2);
					break;
				case 3:
					memo.setMember(member3);
					break;
				case 0:
					memo.setMember(member4);
					break;
			}
			memoRepo.save(memo);
		}

		BoardHistory history;
		for(int i = 1; i <= 101; i++) {
			history = new BoardHistory();
			history.setBoard(board249);
			
			switch(i % 4) {
				case 1:
					history.setMember(member1);
					history.setContent("editor update");
					break;
				case 2:
					history.setMember(member2);
					history.setContent("editor update save");
					break;
				case 3:
					history.setMember(member3);
					history.setContent("memo create");					
					break;
				case 0:
					history.setMember(member4);
					history.setContent("memo update");
					break;
			}
			boardHisRepo.save(history);
		}
		
		System.out.println("\n-------------------------- test data is ready --------------------------\n");
		*/
		
		/*
		INSERT INTO board(title, content, create_date, member_id) VALUES('title_1', 'content', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 1);
		INSERT INTO board(title, content, create_date, member_id) VALUES('title_2', 'content', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 1);
		INSERT INTO board(title, content, create_date, member_id) VALUES('title_3', 'content', {ts '2000-12-12 11:22:33'}, 2);
		INSERT INTO board(title, content, create_date, member_id) VALUES('title_4', 'content', {ts '2000-12-12 11:22:33'}, 2);
		
		INSERT INTO comment(content, create_date, board_id) VALUES('content_1', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 1);
		INSERT INTO comment(content, create_date, board_id) VALUES('content_2', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 1);
		INSERT INTO comment(content, create_date, board_id) VALUES('content_3', {ts '2000-12-12 11:22:33'}, 2);
		INSERT INTO comment(content, create_date, board_id) VALUES('content_4', {ts '2000-12-12 11:22:33'}, 2);
		 */
	}
}