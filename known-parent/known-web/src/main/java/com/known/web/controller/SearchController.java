package com.known.web.controller;

import com.known.common.enums.ArticleTypeEnum;
import com.known.common.enums.CodeEnum;
import com.known.common.model.AskEsModel;
import com.known.common.model.KnowledgeEsModel;
import com.known.common.model.SignInfo;
import com.known.common.model.TopicEsModel;
import com.known.common.vo.OutResponse;
import com.known.common.vo.PageResult;
import com.known.service.SearchService;
import com.known.service.SignInService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * ES搜索模块实现
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-22 00:45
 */
@RestController
@RequestMapping("/search")
public class SearchController extends BaseController {

    @Autowired
    private SignInService signInService;


    @Autowired
    private SearchService searchService;

    private Logger logger = LoggerFactory.getLogger(SearchController.class);

    @RequestMapping("/search")
    public ModelAndView search(HttpSession session, String keyword, String articleType) {
        String userid = getUserid(session);
        ModelAndView view = new ModelAndView("/page/search");
        if (userid != null) {
            SignInfo signInfo = signInService.findSignInfoByUserid(userid);
            view.addObject("signInfo", signInfo);
        }
        view.addObject("keyword", keyword);
        view.addObject("articleType", articleType);
        return view;
    }


    @RequestMapping("/searchArticle")
    public OutResponse<Object> searchArticle(String keyword, String articleType,
                                             Integer pageNum, Integer countTotal) {
        OutResponse<Object> outResponse = new OutResponse<>();
        try {
            if (ArticleTypeEnum.TOPIC.getType().equals(articleType)) {
                PageResult<TopicEsModel> pageResult = searchService.findTopicEsByPage(keyword, pageNum, countTotal);
                outResponse.setData(pageResult);
            }
            if (ArticleTypeEnum.KNOWLEDGE.getType().equals(articleType)) {
                PageResult<KnowledgeEsModel>pageResult = searchService.findKnowledgeEsByPage(keyword, pageNum, countTotal);
                outResponse.setData(pageResult);
            }
            if (ArticleTypeEnum.Ask.getType().equals(articleType)) {
                PageResult<AskEsModel>pageResult = searchService.findAskEsByPage(keyword, pageNum, countTotal);
                outResponse.setData(pageResult);
            }
            outResponse.setCode(CodeEnum.SUCCESS);
        } catch (Exception e) {
            logger.error("搜索异常", e);
            outResponse.setMsg(e.getLocalizedMessage());
            outResponse.setCode(CodeEnum.SERVERERROR);
        }
        return outResponse;
    }

}
