package com.known.web.controller;

import com.known.common.model.SignInfo;
import com.known.manager.query.AskQuery;
import com.known.manager.query.KnowledgeQuery;
import com.known.manager.query.TopicQuery;
import com.known.service.AskService;
import com.known.service.KnowledgeService;
import com.known.service.SignInService;
import com.known.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * 首页及其他页
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-21 23:33
 */
@RestController
public class IndexController extends  BaseController {

    @Autowired
    private SignInService signInService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private AskService askService;

    /**
     * 首页
     *
     * @param session
     * @return
     */
    @RequestMapping("/")
    public ModelAndView index(HttpSession session) {

        String userid = getUserid(session);
        ModelAndView view = new ModelAndView("/page/index");
        if (userid != null) {
            SignInfo signInfo = signInService.findSignInfoByUserid(userid);
            view.addObject("signInfo", signInfo);
        }
        view.addObject("topics", topicService.findTopicByPage(new TopicQuery()));
        view.addObject("knowledges", knowledgeService.findKnowledgeByPage(new KnowledgeQuery()));
        view.addObject("asks", askService.findAskByPage(new AskQuery()));;
        return view;
    }

    /**
     * 赞助作者
     *
     * @param session
     * @return
     */
    @RequestMapping("/donate")
    public ModelAndView donate(HttpSession session) {
        String userid = getUserid(session);
        ModelAndView view = new ModelAndView("/page/donate");
        if (userid != null) {
            SignInfo signInfo = signInService.findSignInfoByUserid(userid);
            view.addObject("signInfo", signInfo);
        }
        return view;
    }

    /**
     * 关于站长
     * @author tangjunxiang
     * @version 1.0
     * @date 2019-05-22 21:26
     */
    @RequestMapping("/aboutWebmaster")
    public ModelAndView aboutWebmaster(HttpSession session) {
        String userid = getUserid(session);
        ModelAndView view = new ModelAndView("/page/aboutWebmaster");
        if (userid != null) {
            SignInfo signInfo = signInService.findSignInfoByUserid(userid);
            view.addObject("signInfo", signInfo);
        }
        return view;
    }

    /**
     * 常见问题
     * @author tangjunxiang
     * @version 1.0
     * @date 2019-05-22 21:26
     */
    @RequestMapping("/faq")
    public ModelAndView faq(HttpSession session) {
        String userid = getUserid(session);
        ModelAndView view = new ModelAndView("/page/FAQ");
        if (userid != null) {
            SignInfo signInfo = signInService.findSignInfoByUserid(userid);
            view.addObject("signInfo", signInfo);
        }
        return view;
    }

    /**
     *
     */
    @RequestMapping("/error404")
    public ModelAndView error404() {
        return new ModelAndView("/page/error/404");
    }

}
