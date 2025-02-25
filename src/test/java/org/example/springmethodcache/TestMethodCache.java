package org.example.springmethodcache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootTest
@EnableAspectJAutoProxy
public class TestMethodCache {

    @Autowired
    TestMethod testMethod;

    @Autowired
    LongestCommonSubsequence longestCommonSubsequence;

    @Test
    public void testMethod() throws InterruptedException {
        for (int i = 10;i<100;i++){
            long start = System.currentTimeMillis();
            int res = testMethod.getSum(i%10);
            System.out.println("result: "+res);
            System.out.println("cost time: "+(System.currentTimeMillis()-start));
        }
    }

    @Test
    public void testMethod2() throws InterruptedException {
        //aklndlakflkjdsaf
        String s1 = "adsfdasfasf12ddafafdavssdvasdfasd2e2ed1dafasdvadsva43f4rgef13defdas";
        String s2 = "asdfsdafd1q34tgqrgvbvsgnyemshq35gh231232e2ed1d32e2ed1d";
        long start = System.currentTimeMillis();
        int res = longestCommonSubsequence.longestCommonSubsequence(s1,s2);
        System.out.println("result: "+res);
        System.out.println("normal DP cost: "+(System.currentTimeMillis()-start));
        start = System.currentTimeMillis();
        res = longestCommonSubsequence.longestCommonSubsequence2(s1,s2);
        System.out.println("result: "+res);
        System.out.println("method cache cost: "+(System.currentTimeMillis()-start));
//        start = System.currentTimeMillis();
//        res = longestCommonSubsequence.longestCommonSubsequence3(s1,s2);
//        System.out.println("result: "+res);
//        System.out.println("No DP cost: "+(System.currentTimeMillis()-start));
        start = System.currentTimeMillis();
        res = longestCommonSubsequence.longestCommonSubsequence4(s1,s2);
        System.out.println("result: "+res);
        System.out.println("method fast cache cost: "+(System.currentTimeMillis()-start));
    }

}
