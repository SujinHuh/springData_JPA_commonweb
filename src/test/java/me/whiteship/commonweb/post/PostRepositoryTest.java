package me.whiteship.commonweb.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {


    @Autowired
    private PostRepository postRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Test
    public void save() {

        Post post = new Post();
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post);//Persist - id가 없기때


        assertThat(entityManager.contains(post)).isTrue();
        assertThat(entityManager.contains(savedPost)).isTrue();
        assertThat(savedPost == post);

        Post postUpdate = new Post();
        postUpdate.setId(post.getId());
        postUpdate.setTitle("Hibernate");
        Post updatePost = postRepository.save(postUpdate);//merge id가 있어서 merge로 세팅이됨 /update quary가 발생하고 하이버네이트로 값을 업데이트한다.

        assertThat(entityManager.contains(updatePost)).isTrue();
        assertThat(entityManager.contains(postUpdate)).isFalse();
        assertThat(updatePost == postUpdate);

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }
}