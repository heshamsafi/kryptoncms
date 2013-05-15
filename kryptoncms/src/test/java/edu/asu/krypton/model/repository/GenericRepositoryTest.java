package edu.asu.krypton.model.repository;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.persist.db.Album;
import edu.asu.krypton.model.persist.db.Article;
import edu.asu.krypton.model.persist.db.Photo;
import edu.asu.krypton.model.persist.db.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class GenericRepositoryTest {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private AlbumRepository albumRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PhotoRepository photoRepository;
	
//	@Autowired
//	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
//
//	@Autowired
//	private DataAccessObject<?> dao;
	
	@Test
	public void saveOrUpdate() throws CustomRuntimeException{
//		dao.openSession();
//		Role role = new Role();
//		role.setRole(2);

		User user = new User();
		user.setUsername("test username");
		user.setPassword("test password");
		user.setRole(BigInteger.valueOf(2));
//		role.setUser(user);
//		roleRepository.saveOrUpdate(role);
		userRepository.saveOrUpdate(user);
		
		Article article = new Article();
		article.setTitle("test");
		article.setDescription("test desc");
		article.setContent("test cont");
		articleRepository.saveOrUpdate(article);
		
		Photo photo = new Photo();
		photo.setPath("test path");
		
		Album album = new Album();
		album.setTitle("test title");
		albumRepository.saveOrUpdate(album);
		photo.setParent(album);
		photoRepository.saveOrUpdate(photo);

		
//		ArticleComment articleComment = new ArticleComment();
//		articleComment.setParent(article);
//		articleComment.setContent("test content");
//		articleComment.setAuthor(user);
//		commentRepository.saveOrUpdate(articleComment);
		
//		dao.killSession(true);
	}

}
