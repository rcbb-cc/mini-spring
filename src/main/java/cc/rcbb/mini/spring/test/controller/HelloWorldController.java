package cc.rcbb.mini.spring.test.controller;

import cc.rcbb.mini.spring.beans.factory.annotation.Autowired;
import cc.rcbb.mini.spring.test.IAction;
import cc.rcbb.mini.spring.test.TestUser;
import cc.rcbb.mini.spring.test.req.TestReq;
import cc.rcbb.mini.spring.web.bind.annotation.RequestMapping;
import cc.rcbb.mini.spring.web.bind.annotation.ResponseBody;
import cc.rcbb.mini.spring.web.servlet.ModelAndView;

/**
 * <p>
 * HelloWorldController
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/26
 */
public class HelloWorldController {

    /*@Autowired
    private TestUserService testUserService;*/

    @ResponseBody
    @RequestMapping("/test1")
    public String doTest1() {
        return "test 1, hello world!";
    }

    @ResponseBody
    @RequestMapping("/test2")
    public String doTest2(TestReq req) {
        return "test 2, hello world! " + req.toString();
    }

    @ResponseBody
    @RequestMapping("/test3")
    public TestUser doTest3() {
        TestUser testUser = new TestUser();
        testUser.setId("1");
        testUser.setAge(18);
        testUser.setName("zhang san");
        return testUser;
    }

    @RequestMapping("/test4")
    public ModelAndView doTest4(TestUser user) {
        ModelAndView mav = new ModelAndView("test", "msg", user.getName());
        return mav;
    }

    /*@RequestMapping("/testUser")
    public String testUser() {
        return testUserService.getTestUser().toString();
    }*/

    @Autowired
    IAction action;

    @RequestMapping("/testDoAction")
    @ResponseBody
    public String testDoAction() {
        action.doAction();
        return "SUCCESS";
    }

    @RequestMapping("/testDoSomething")
    @ResponseBody
    public String testDoSomething() {
        action.doSomething();
        return "SUCCESS";
    }

}
