package com.example.demo;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final MyTaskExecutor myTaskExecutor;


    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @GetMapping("/test")
    public void test(@RequestParam Long totalTasks) {


        for (int i = 0; i < totalTasks; i++) {
            int taskId = i;
            log.info("Created task " + taskId);

            myTaskExecutor.executeTask(() -> {
                log.info("Executing task {}", taskId);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    log.error("Interrupted");
                }
                log.info("QUEUE SIZE: {}", threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().size());
            });
        }

    }

}
