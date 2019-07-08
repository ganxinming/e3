package cn.e3mall.search.execption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Badribbit
 * @create 2019/6/26 9:15
 * @Define  全局异常处理器
 * @Tutorials
 * @Opinion
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    //logger包使用slf4j的包
    
    private static final Logger LOGGER= LoggerFactory.getLogger(GlobalExceptionResolver.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //首先將异常打印控制台
        ex.printStackTrace();
        //使用logger写日志
        LOGGER.debug("测试输出的日志");
        LOGGER.info("系统发生了异常");
        LOGGER.error("系统的异常是",ex);

        //发邮件、发短信
        //使用jmail工具包。发短信使用第三方的Webservice。
        //显示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
