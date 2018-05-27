package pkpm.desktop.cloud.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.searchbox.client.JestResult;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import pkpm.desktop.cloud.search.service.JestService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

	private String indexName = "hwd";
	private String typeName = "user";

	@Autowired
	private JestService jestService;

	@Test
	public void createIndex() throws Exception {

		boolean result = jestService.createIndex(indexName);
		System.out.println(result);
	}

	@Test
	public void createIndexMapping() throws Exception {

		//es5.x版本增加keyword、text类型
		//text：可以指定分词器，不指定使用默认分词器，英文按空格分词，中文每个字一个分词，keyword：不进行分词
		//本例使用了IK中文分词器
		String source = "{\"" + typeName + "\":{\"properties\":{" + "\"id\":{\"type\":\"integer\"}"
				+ ",\"name\":{\"type\":\"keyword\",\"index\":\"false\"}" 
				+ ",\"addr\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\",\"search_analyzer\":\"ik_max_word\"}"
				+ "}}}";
		System.out.println(source);
		boolean result = jestService.createIndexMapping(indexName, typeName, source);
		System.out.println(result);
	}
//
//	@Test
//	public void getIndexMapping() throws Exception {
//
//		String result = jestService.getIndexMapping(indexName, typeName);
//		System.out.println(result);
//	}

	@Test
	public void index() throws Exception {

		List<Object> objs = new ArrayList<Object>();
		objs.add(new User(1, "T:o\"m-", "北京市朝阳区北三环30号",new Date()));
		objs.add(new User(2, "J,e{r}r;y:", "吉林省白山市靖宇县", new Date()));
		objs.add(new User(3, "test123", "河北省廊坊市香河县", new Date()));
		boolean result = jestService.index(indexName, typeName, objs);
		System.out.println(result);
	}

	
	@Test
	public void queryAll() throws Exception {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(10);
		searchSourceBuilder.from(0);
		String query = searchSourceBuilder.toString();
		System.out.println(query);
		SearchResult result = jestService.search(indexName, typeName, query);
		List<Hit<User, Void>> hits = result.getHits(User.class);
		System.out.println("Size:" + hits.size());
		for (Hit<User, Void> hit : hits) {
			User user = hit.source;
			System.out.println(user.toString());
		}
	}
	
	
	@Test
	public void termQuery() throws Exception {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.termQuery("name", "T:o\"m-");// 单值完全匹配查询
		
		searchSourceBuilder.query(queryBuilder);
		searchSourceBuilder.size(10);
		searchSourceBuilder.from(0);
		String query = searchSourceBuilder.toString();
		System.out.println(query);
		SearchResult result = jestService.search(indexName, typeName, query);
		List<Hit<User, Void>> hits = result.getHits(User.class);
		System.out.println("Size:" + hits.size());
		for (Hit<User, Void> hit : hits) {
			User user = hit.source;
			System.out.println(user.toString());
		}
	}

	@Test
	public void termsQuery() throws Exception {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.termsQuery("name", new String[] { "T:o\"m-", "J,e{r}r;y:", "test123" });// 多值完全匹配查询
		searchSourceBuilder.query(queryBuilder);
		searchSourceBuilder.size(10);
		searchSourceBuilder.from(0);
		String query = searchSourceBuilder.toString();
		System.out.println(query);
		SearchResult result = jestService.search(indexName, typeName, query);
		List<Hit<User, Void>> hits = result.getHits(User.class);
		System.out.println("Size:" + hits.size());
		for (Hit<User, Void> hit : hits) {
			User user = hit.source;
			System.out.println(user.toString());
		}
	}

	@Test
	public void wildcardQuery() throws Exception {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("name", "*:*");// 通配符和正则表达式查询
		searchSourceBuilder.query(queryBuilder);
		searchSourceBuilder.size(10);
		searchSourceBuilder.from(0);
		String query = searchSourceBuilder.toString();
		System.out.println(query);
		SearchResult result = jestService.search(indexName, typeName, query);
		List<Hit<User, Void>> hits = result.getHits(User.class);
		System.out.println("Size:" + hits.size());
		for (Hit<User, Void> hit : hits) {
			User user = hit.source;
			System.out.println(user.toString());
		}
	}

	@Test
	public void prefixQuery() throws Exception {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.prefixQuery("name", "T:o");// 前缀查询
		searchSourceBuilder.query(queryBuilder);
		searchSourceBuilder.size(10);
		searchSourceBuilder.from(0);
		String query = searchSourceBuilder.toString();
		System.out.println(query);
		SearchResult result = jestService.search(indexName, typeName, query);
		List<Hit<User, Void>> hits = result.getHits(User.class);
		System.out.println("Size:" + hits.size());
		for (Hit<User, Void> hit : hits) {
			User user = hit.source;
			System.out.println(user.toString());
		}
	}

	@Test
	public void rangeQuery() throws Exception {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.rangeQuery("birth").gte("2016-09-01T00:00:00")
				.lte("2018-10-01T00:00:00").includeLower(true).includeUpper(true);// 区间查询
		searchSourceBuilder.query(queryBuilder);
		searchSourceBuilder.size(10);
		searchSourceBuilder.from(0);
		String query = searchSourceBuilder.toString();
		System.out.println(query);
		SearchResult result = jestService.search(indexName, typeName, query);
		List<Hit<User, Void>> hits = result.getHits(User.class);
		System.out.println("Size:" + hits.size());
		for (Hit<User, Void> hit : hits) {
			User user = hit.source;
			System.out.println(user.toString());
		}
	}

	@Test
	public void queryString() throws Exception {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(QueryParser.escape("北京市朝阳区"));// 文本检索，应该是将查询的词先分成词库中存在的词，然后分别去检索，存在任一存在的词即返回，查询词分词后是OR的关系。需要转义特殊字符
		searchSourceBuilder.query(queryBuilder);
		searchSourceBuilder.size(10);
		searchSourceBuilder.from(0);
		String query = searchSourceBuilder.toString();
		System.out.println(query);
		SearchResult result = jestService.search(indexName, typeName, query);
		List<Hit<User, Void>> hits = result.getHits(User.class);
		System.out.println("Size:" + hits.size());
		for (Hit<User, Void> hit : hits) {
			User user = hit.source;
			System.out.println(user.toString());
		}
	}

	@Test
	public void count() throws Exception {

		String[] name = new String[] { "T:o\"m-", "Jerry" };
		String from = "2016-09-01T00:00:00";
		String to = "2018-10-01T00:00:00";
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.termsQuery("name", name))
				.must(QueryBuilders.rangeQuery("birth").gte(from).lte(to));
		searchSourceBuilder.query(queryBuilder);
		String query = searchSourceBuilder.toString();
		System.out.println(query);
		Double count = jestService.count(indexName, typeName, query);
		System.out.println("Count:" + count);
	}

	@Test
	public void get() throws Exception {

		String id = "2";
		JestResult result = jestService.get(indexName, typeName, id);
		if (result.isSucceeded()) {
			User user = result.getSourceAsObject(User.class);
			System.out.println(user.toString());
		}
	}

	@Test
	public void deleteIndexDocument() throws Exception {

		String id = "2";
		boolean result = jestService.delete(indexName, typeName, id);
		System.out.println(result);
	}

	@Test
	public void deleteIndex() throws Exception {

		boolean result = jestService.delete(indexName);
		System.out.println(result);
	}
}