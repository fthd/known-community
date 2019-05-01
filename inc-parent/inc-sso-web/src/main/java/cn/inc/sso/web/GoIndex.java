package cn.inc.sso.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GoIndex {

    @RequestMapping("/")
    public String goIndex() {
        return "index";
    }

    @RequestMapping("/page/{page}")
    public String goPage(@PathVariable String page ) {
        return page;
    }

}
