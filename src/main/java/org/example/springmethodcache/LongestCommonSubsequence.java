package org.example.springmethodcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LongestCommonSubsequence {
    public Map<String, Integer> cache = new HashMap<>();

    @Autowired
    @Lazy
    LongestCommonSubsequence longestCommonSubsequence;

    public int longestCommonSubsequence(String text1, String text2) {
        return dp(0, 0, text1, text2);
    }

    public int dp(int i, int j, String text1, String text2) {
        // 如果其中一个字符串已经遍历完，返回 0
        if (i == text1.length() || j == text2.length()) {
            return 0;
        }
        // 检查缓存
        String key = i + "," + j;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        // 如果当前字符相等，则递归计算下一个字符
        if (text1.charAt(i) == text2.charAt(j)) {
            int result = 1 + dp(i + 1, j + 1, text1, text2);
            cache.put(key, result);
            return result;
        }
        // 如果当前字符不相等，则分别尝试跳过 text1 或 text2 的当前字符
        int result = Math.max(dp(i + 1, j, text1, text2), dp(i, j + 1, text1, text2));
        cache.put(key, result);
        return result;
    }

    public int longestCommonSubsequence2(String text1, String text2) {
        return longestCommonSubsequence.dp2(0, 0, text1, text2);
    }

    @MethodCache
    public int dp2(int i, int j, String text1, String text2) {
        // 如果其中一个字符串已经遍历完，返回 0
        if (i == text1.length() || j == text2.length()) {
            return 0;
        }
        // 如果当前字符相等，则递归计算下一个字符
        if (text1.charAt(i) == text2.charAt(j)) {
            int result = 1 + longestCommonSubsequence.dp2(i + 1, j + 1, text1, text2);
            return result;
        }
        // 如果当前字符不相等，则分别尝试跳过 text1 或 text2 的当前字符
        int result = Math.max(longestCommonSubsequence.dp2(i + 1, j, text1, text2), longestCommonSubsequence.dp2(i, j + 1, text1, text2));
        return result;
    }

    public int longestCommonSubsequence3(String text1, String text2) {
        return dp3(0, 0, text1, text2);
    }

    public int dp3(int i, int j, String text1, String text2) {
        // 如果其中一个字符串已经遍历完，返回 0
        if (i == text1.length() || j == text2.length()) {
            return 0;
        }
        // 如果当前字符相等，则递归计算下一个字符
        if (text1.charAt(i) == text2.charAt(j)) {
            int result = 1 + dp3(i + 1, j + 1, text1, text2);
            return result;
        }
        // 如果当前字符不相等，则分别尝试跳过 text1 或 text2 的当前字符
        int result = Math.max(dp3(i + 1, j, text1, text2), dp3(i, j + 1, text1, text2));
        return result;
    }

    public int longestCommonSubsequence4(String text1, String text2) {
        return longestCommonSubsequence.dp4(0, 0, text1, text2);
    }

    @MethodCache(fastCache = true)
    public int dp4(int i, int j, String text1, String text2) {
        // 如果其中一个字符串已经遍历完，返回 0
        if (i == text1.length() || j == text2.length()) {
            return 0;
        }
        // 如果当前字符相等，则递归计算下一个字符
        if (text1.charAt(i) == text2.charAt(j)) {
            int result = 1 + longestCommonSubsequence.dp4(i + 1, j + 1, text1, text2);
            return result;
        }
        // 如果当前字符不相等，则分别尝试跳过 text1 或 text2 的当前字符
        int result = Math.max(longestCommonSubsequence.dp4(i + 1, j, text1, text2), longestCommonSubsequence.dp4(i, j + 1, text1, text2));
        return result;
    }
}