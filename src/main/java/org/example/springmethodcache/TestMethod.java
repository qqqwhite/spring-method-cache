package org.example.springmethodcache;

import org.springframework.stereotype.Component;

@Component
public class TestMethod {

    @MethodCache
    public int getSum(int num) throws InterruptedException {
        int sum = 0;
        for (int i = 0; i < num; i++) {
            sum += i*10;
            Thread.sleep(100);
        }
        return sum;
    }

}
