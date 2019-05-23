package com.known.service_impl;

import com.known.common.echart.Echart;
import com.known.common.echart.Series;
import com.known.common.echart.XAxis;
import com.known.common.enums.ArticleType;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.model.*;
import com.known.common.utils.DateUtil;
import com.known.exception.BussinessException;
import com.known.manager.mapper.*;
import com.known.manager.query.*;
import com.known.service.IStatisticalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class StatisticalDataServiceImpl implements IStatisticalDataService {
	
	@Autowired
	private StatisticsMapper<Statistics, StatisticsQuery> statisticsMapper;
	
	@Autowired
	private SignInMapper<SignIn, SignInQuery> signInMapper;
	
	@Autowired
	private ShuoShuoMapper<ShuoShuo, ShuoShuoQuery> shuoShuoMapper;
	
	@Autowired
	private ShuoShuoCommentMapper<ShuoShuoComment, ShuoShuoQuery> shuoShuoCommentMapper;
	
	@Autowired
	private TopicMapper<Topic, TopicQuery> topicMapper;
	
	@Autowired
	private CommentMapper<Comment, CommentQuery> commentMapper;
	
	@Autowired
	private KnowledgeMapper<Knowledge, KnowledgeQuery> knowledgeMapper;
	
	@Autowired
	private AskMapper<Ask, AskQuery> askMapper;
	@Autowired
	private UserMapper<User, UserQuery> userMapper;

	@Override
	public void caculateData() {
		System.out.println("统计中。。。。。。");
		Statistics statistics = new Statistics();
		
		Date date = new Date();
		
		statistics.setStatisticsDate(date);
		
		SignInQuery signInQuery = new SignInQuery();
		signInQuery.setCurDate(date);
		int signInCount = signInMapper.selectCount(signInQuery);
		statistics.setSigninCount(signInCount);
		
		ShuoShuoQuery shuoShuoQuery = new ShuoShuoQuery();
		shuoShuoQuery.setStartDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		shuoShuoQuery.setEndDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		int shuoshuoCount = shuoShuoMapper.selectCount(shuoShuoQuery);
		statistics.setShuoshuoCount(shuoshuoCount);
		
		int shuoshuoCommentCount = shuoShuoCommentMapper.selectCount(shuoShuoQuery);
		statistics.setShuoshuoCommentCount(shuoshuoCommentCount);
		
		TopicQuery topicQuery = new TopicQuery();
		topicQuery.setStartDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		topicQuery.setEndDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		int topicCount = topicMapper.selectCount(topicQuery);
		statistics.setTopicCount(topicCount);
		
		CommentQuery commentQuery = new CommentQuery();
		commentQuery.setStartDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		commentQuery.setEndDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		commentQuery.setArticleType(ArticleType.TOPIC);
		int topicCommentCount = commentMapper.selectCount(commentQuery);
		statistics.setTopicCommentCount(topicCommentCount);
		
		commentQuery.setArticleType(ArticleType.KNOWLEDGE);
		int knowledgeCommentCount = commentMapper.selectCount(commentQuery);
		statistics.setKnowledgeCommentCount(knowledgeCommentCount);
		
		commentQuery.setArticleType(ArticleType.Ask);
		int askCommentCount = commentMapper.selectCount(commentQuery);
		statistics.setAskCommentCount(askCommentCount);;
		
		KnowledgeQuery knowledgeQuery = new KnowledgeQuery();
		knowledgeQuery.setStartDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		knowledgeQuery.setEndDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		int knowledgeCount = knowledgeMapper.selectCount(knowledgeQuery);
		statistics.setKnowledgeCount(knowledgeCount);
		
		AskQuery askQuery = new AskQuery();
		askQuery.setStartDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		askQuery.setEndDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));	
		int askcount = askMapper.selectCount(askQuery);
		statistics.setAskCount(askcount);

		UserQuery userQuery = new UserQuery();
		userQuery.setStartDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		userQuery.setEndDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));		
		int userCount = userMapper.selectCount(userQuery);
		statistics.setUserCount(userCount);
		
		userQuery = new UserQuery();
		userQuery.setLoginStartDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		userQuery.setLoginEndDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		int activeUserCount = userMapper.selectCount(userQuery);
		statistics.setActiveUserCount(activeUserCount);
		
		statisticsMapper.insert(statistics);
	}

	@Override
	public List<Echart> findEcharts() throws BussinessException {
		List<Statistics> list = findStatistics();
		List<Echart> echarts = new ArrayList<>();
		
		XAxis xAxis = new XAxis();
		List<String> dates = new ArrayList<>();
		List<Integer> signInCounts	= new ArrayList<>();
		List<Integer> shuoshuoCounts	= new ArrayList<>();
		List<Integer> shuoshuoCommentCounts	= new ArrayList<>();
		List<Integer> topicCounts	= new ArrayList<>();
		List<Integer> topicCommentCounts	= new ArrayList<>();
		List<Integer> knowledgeCounts	= new ArrayList<>();
		List<Integer> knowledgeCommentCounts	= new ArrayList<>();
		List<Integer> askCounts	= new ArrayList<>();
		List<Integer> askCommentCounts	= new ArrayList<>();
		List<Integer> blogCounts	= new ArrayList<>();
		List<Integer> blogCommentCounts	= new ArrayList<>();
		List<Integer> examCounts	= new ArrayList<>();
		List<Integer> userCounts	= new ArrayList<>();
		List<Integer> activeUserCounts	= new ArrayList<>();
		for(Statistics statistics : list){
			dates.add(DateUtil.format(statistics.getStatisticsDate(), DateTimePatternEnum.YYYY_MM_DD.getPattern()));
			signInCounts.add(statistics.getSigninCount());
			shuoshuoCounts.add(statistics.getShuoshuoCount());
			shuoshuoCommentCounts.add(statistics.getShuoshuoCommentCount());
			topicCounts.add(statistics.getTopicCount());
			topicCommentCounts.add(statistics.getTopicCommentCount());
			knowledgeCounts.add(statistics.getKnowledgeCount());
			knowledgeCommentCounts.add(statistics.getKnowledgeCommentCount());
			askCounts.add(statistics.getAskCount());
			askCommentCounts.add(statistics.getAskCommentCount());
			blogCounts.add(statistics.getBlogCount());
			blogCommentCounts.add(statistics.getBlogCommentCount());
			examCounts.add(statistics.getExamCount());
			userCounts.add(statistics.getUserCount());
			activeUserCounts.add(statistics.getActiveUserCount());
		}
		xAxis.setData(dates);
		
		List<Series> series = new ArrayList<Series>();
		series.add(new Series("签到", signInCounts));
		Echart signInEchart = new Echart("一周之内的签到", new String[]{"签到"}, xAxis, series);
		echarts.add(signInEchart);
		
		
		series = new ArrayList<Series>();
		series.add(new Series("说说", shuoshuoCounts));
		series.add(new Series("说说评论", shuoshuoCommentCounts));
		Echart shuoshuoEchart = new Echart("一周之内的说说", new String[]{"说说","说说评论"}, xAxis, series);
		echarts.add(shuoshuoEchart);
		
		series = new ArrayList<Series>();
		series.add(new Series("话题", topicCounts));
		series.add(new Series("话题评论", topicCommentCounts));
		Echart topicEchart = new Echart("一周之内的话题", new String[]{"话题","话题评论"}, xAxis, series);
		echarts.add(topicEchart);
		
		series = new ArrayList<Series>();
		series.add(new Series("知识库", knowledgeCounts));
		series.add(new Series("知识库评论", knowledgeCommentCounts));
		Echart knowledgeEchart = new Echart("一周之内的知识库", new String[]{"知识库","知识库评论"}, xAxis, series);
		echarts.add(knowledgeEchart);
		
		series = new ArrayList<Series>();
		series.add(new Series("提问", askCounts));
		series.add(new Series("回答", askCommentCounts));
		Echart askEchart = new Echart("一周之内的问答", new String[]{"提问","回答"}, xAxis, series);
		echarts.add(askEchart);
		
		series = new ArrayList<Series>();
		series.add(new Series("话题", blogCounts));
		series.add(new Series("话题评论", blogCommentCounts));
		Echart blogEchart = new Echart("一周之内的话题", new String[]{"话题","话题评论"}, xAxis, series);
		echarts.add(blogEchart);
		
		series = new ArrayList<Series>();
		series.add(new Series("考题", examCounts));
		Echart examEchart = new Echart("一周之内的考题", new String[]{"考题"}, xAxis, series);
		echarts.add(examEchart);
		
		series = new ArrayList<Series>();
		series.add(new Series("新增用户", userCounts));
		series.add(new Series("活跃用户", activeUserCounts));
		Echart userEchart = new Echart("一周之内的用户", new String[]{"新增用户","活跃用户"}, xAxis, series);
		echarts.add(userEchart);
		
		return echarts;
	}

	@Override
	public List<Statistics> findStatistics() throws BussinessException {
		StatisticsQuery statisticsQuery = new StatisticsQuery();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -6);
		statisticsQuery.setStartDate(calendar.getTime());
		statisticsQuery.setEndDate(new Date());
		return this.statisticsMapper.selectList(statisticsQuery);
	}
}
