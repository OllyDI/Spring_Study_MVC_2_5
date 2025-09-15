package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

        String requestURI = req.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        req.setAttribute(LOG_ID, uuid);    // 이걸 통해 애프터컴플리션에 uuid를 넘김

        /**
         * @RequestMapping: HandlerMethod 사용됨
         * 정적 리소스: ResourceHttpRequestHandler 사용됨
         */
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;// 호출할 컨트롤러 메서드의 모든 정보가 포함
        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("POST HANDLE [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) throws Exception {
        String requestURI = req.getRequestURI();
        Object uuid = req.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}][{}]", uuid, requestURI, handler);

        if (ex != null) {
            log.error("afterCompletion error", ex);
        }
    }
}
