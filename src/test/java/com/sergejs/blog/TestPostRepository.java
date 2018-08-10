package com.sergejs.blog;

import com.sergejs.blog.database.PostRepository;
import com.sergejs.blog.database.PostRepositoryImpl;
import com.sergejs.blog.entity.Post;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPostRepository {

    private static EmbeddedDatabase embeddedDatabase;
    private static PostRepository postRepository;

    private static Post testingPost;
    private static String testPostID = "";

    @BeforeClass
    public static void  setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        postRepository = new PostRepositoryImpl(jdbcTemplate);


        testingPost = new Post("12:00",
                "admin", "TestPostTitle",
                "this is Post created for db testing",
                "Lorem ipsum bla bla bla just whatever");

    }

    /*
    should not return null
    and should return all elements of table
     */
    @Test
    public void test01FindAll() {
        Assert.assertNotNull(postRepository.findAll());
        Assert.assertEquals(5, postRepository.findAll().size());
    }

    /*
    return null when can`t find entry
    if id is existent should not return null
    //TODO test if found entry equels inserted entry
     */
    @Test
    public void test02FindOne() {
        Assert.assertNotNull(postRepository.findOne("1"));
        Assert.assertNull(postRepository.findOne("non_existing_ID"));
    }

    /*
    save(Person person) должен сохранять объект и возвращать его сохранённую версию, либо выбрасывать исключение
    DataIntegrityViolationException в случае попытке сохранения объекта с невалидными данными.
     */
    @Test
    public void test03Save(){
        Post post = postRepository.save(testingPost);
        Assert.assertNotNull(post);
        Assert.assertNotNull(post.getId());
        testPostID = post.getId();
        Assert.assertEquals("TestPostTitle", post.getTitle());
    }


    @Test(expected = DataIntegrityViolationException.class)
    public void test04SaveInvalid() {
        postRepository.save(new Post());
    }

    @Test
    public void test05Update(){

        Post post = postRepository.findOne(testPostID);
        String postToDelete = "TestPostToBeDeleted";
        post.setTitle(postToDelete);

        post = postRepository.save(post);

        Assert.assertNotNull(post);
        Assert.assertNotNull(post.getId());
        Assert.assertEquals(postToDelete, post.getTitle());
    }

    @Test
    public void test06Delete() {
        Assert.assertEquals(1, postRepository.delete(testPostID));
        Assert.assertEquals(0, postRepository.delete("nonexistent-id"));
    }

    @AfterClass
    public static void tearDown() {
        embeddedDatabase.shutdown();
    }
}