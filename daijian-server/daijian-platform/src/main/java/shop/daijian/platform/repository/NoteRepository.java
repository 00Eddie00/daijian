package shop.daijian.platform.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import shop.daijian.platform.entity.Note;

/**
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-17-45
 */
public interface NoteRepository extends ElasticsearchRepository<Note, String> {

}
