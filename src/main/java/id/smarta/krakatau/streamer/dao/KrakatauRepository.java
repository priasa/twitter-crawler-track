package id.smarta.krakatau.streamer.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class KrakatauRepository extends JdbcDaoSupport {

	public String findTwitterStreamStatus (String streamId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT status FROM twitter_stream where id = ?");
		return getJdbcTemplate().queryForObject(sb.toString(), new Object[] { streamId }, String.class);
	}
	
	public int updateTwitterStreamStatus(String streamId) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE twitter_stream SET status='RUNNING' where id = ?");
		return getJdbcTemplate().update(sb.toString(), new Object[] { streamId });
	}
	
	public List<String> findTwitterKeywords(String name) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mdc.fieldValue FROM master_data_content mdc, master_data md ");
		query.append("WHERE mdc.parentId_id = md.id AND md.name = ?");
		List<String> result = new ArrayList<String>();
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query.toString(), new Object[] { name });
		for (Map<String, Object> row : rows) {
			String keyword = (String)row.get("fieldValue");
			result.add(keyword);
		}
		return result;
	}
}
