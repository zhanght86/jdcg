package ses.listener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ses.model.ems.Expert;
import ses.service.ems.ExpertService;

/**
 * 版权：(C) 版权所有 2011-2016
 * <简述>
 * 定时任务:删除无效的专家
 * <详细描述>
 * @author   WangHuijie
 * @version  
 * @since
 * @see
 */
public class DelExpertListener implements ServletContextListener{
 
    public static final long PERIOD_DAY = 86400000L;
 
    public static final long NO_DELAY = 0;
 
    private Timer timer;

 
    public void contextInitialized (ServletContextEvent event) {
        //WebApplicationContext ctx= WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        //final ExpertService service = (ExpertService) ctx.getBean("ExpertService");
        // 定义定时器
        timer = new Timer("删除无效专家", true);
        // 启动删除任务,每天执行一次
        timer.schedule(new DelExpertTask(), NO_DELAY, PERIOD_DAY);
    }

 
    public void contextDestroyed (ServletContextEvent event) {
        timer.cancel();// 定时器销毁
    }
}

class DelExpertTask extends TimerTask {
    private static boolean isRunning = false;
    private ExpertService expertService;
    
    public void run () {
        if (!isRunning) {
            isRunning = true;
            delExpert();
            // 开始删除专家
            isRunning = false;
        }
    }
    
    /**
     *〈简述〉
     * 执行删除Expert功能
     *〈详细描述〉
     * @author WangHuijie
     * @throws Exception 
     */
    void delExpert () {
        List<Expert> allExpert = expertService.getAllExpert();
        int daysBetween;
        try {
            for (Expert expert : allExpert) {
                // 判断多长时间没有进行操作
                daysBetween = expertService.daysBetween(expert.getUpdatedAt());
                if (("0".equals(expert.getIsSubmit()) && daysBetween > 90) || ("1".equals(expert.getIsSubmit()) && "3".equals(expert.getStatus()) && daysBetween > 60)) {
                    expertService.deleteByPrimaryKey(expert.getId());
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}