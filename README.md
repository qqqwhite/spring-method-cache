# Spring Method Cache

[English](#english) | [中文](#chinese)

<a name="english"></a>
## English

A Spring-based method caching solution similar to Python's `@cache` decorator, providing easy-to-use method result caching capabilities with support for both normal and fast caching modes.

### Features

- Simple annotation-based caching (`@MethodCache`)
- Support for both safe and fast caching modes
- Automatic cache key generation based on method parameters
- Spring AOP integration
- Thread-safe implementation using `ConcurrentHashMap`
- Support for internal method calls (self-invocation) through Spring AOP

### Usage

#### Basic Usage

1. Enable AOP in your Spring Boot application:

```java
@SpringBootTest
@EnableAspectJAutoProxy
public class YourApplication {
    // ...
}
```

2. Add the `@MethodCache` annotation to methods you want to cache:

```java
@Component
public class Calculator {
    @MethodCache
    public int expensiveCalculation(int n) {
        // Some time-consuming calculation
        return result;
    }
}
```

#### Fast Cache Mode

The `@MethodCache` annotation supports two caching modes:

1. **Safe Mode** (default): Uses deep comparison of method parameters to ensure cache correctness
2. **Fast Mode**: Uses string representation of parameters for faster cache lookup

To enable fast cache mode:

```java
@MethodCache(fastCache = true)
public int quickCalculation(int n) {
    // Method implementation
}
```

#### Internal Method Calls

To support caching for internal method calls (self-invocation), you need to:

1. Autowire the component into itself using `@Lazy`:

```java
@Component
public class Calculator {
    @Autowired
    @Lazy
    private Calculator self;

    @MethodCache
    public int calculate(int n) {
        // Method implementation
        return self.calculate(n - 1) + self.calculate(n - 2);
    }
}
```

### Performance Comparison

Based on the test cases:

1. **Normal Method Call**: No caching, full computation every time
2. **@MethodCache (Safe Mode)**: Caches results with parameter deep comparison
3. **@MethodCache (Fast Mode)**: Fastest caching with string-based parameter comparison

The fast cache mode can provide significant performance improvements when dealing with complex parameters or frequent cache lookups.

### Implementation Details

The caching mechanism is implemented using:

- Spring AOP for method interception
- Concurrent hash maps for thread-safe caching
- Custom annotation processing
- Lazy proxy injection for self-invocation support

### Important Notes

1. The cache is stored in memory and will be cleared when the application restarts
2. Fast cache mode trades some safety for performance - use it when you're sure the string representation of parameters is sufficient for cache key generation
3. For internal method calls, always use the autowired reference (self) instead of this to ensure caching works properly

### Example

Here's a complete example showing different caching modes:

```java
@Component
public class Example {
    @Autowired
    @Lazy
    private Example self;

    // Regular caching with safe parameter comparison
    @MethodCache
    public int normalCache(String param1, int param2) {
        // Expensive operation
        return result;
    }

    // Fast caching for better performance
    @MethodCache(fastCache = true)
    public int fastCache(String param1, int param2) {
        // Expensive operation
        return result;
    }

    // Internal method call with caching
    @MethodCache
    public int recursiveCalculation(int n) {
        if (n <= 1) return n;
        return self.recursiveCalculation(n - 1) + self.recursiveCalculation(n - 2);
    }
}
```

<a name="chinese"></a>
## 中文版

一个基于Spring的方法缓存解决方案，类似于Python的`@cache`装饰器，提供易用的方法结果缓存功能，支持普通和快速缓存模式。

### 特性

- 基于注解的简单缓存实现（`@MethodCache`）
- 支持安全和快速两种缓存模式
- 基于方法参数的自动缓存键生成
- Spring AOP集成
- 使用`ConcurrentHashMap`实现线程安全
- 通过Spring AOP支持内部方法调用（自调用）

### 使用方法

#### 基本用法

1. 在Spring Boot应用中启用AOP：

```java
@SpringBootTest
@EnableAspectJAutoProxy
public class YourApplication {
    // ...
}
```

2. 在需要缓存的方法上添加`@MethodCache`注解：

```java
@Component
public class Calculator {
    @MethodCache
    public int expensiveCalculation(int n) {
        // 一些耗时的计算
        return result;
    }
}
```

#### 快速缓存模式

`@MethodCache`注解支持两种缓存模式：

1. **安全模式**（默认）：使用方法参数的深度比较确保缓存正确性
2. **快速模式**：使用参数的字符串表示进行更快的缓存查找

启用快速缓存模式：

```java
@MethodCache(fastCache = true)
public int quickCalculation(int n) {
    // 方法实现
}
```

#### 内部方法调用

要支持内部方法调用（自调用）的缓存，你需要：

1. 使用`@Lazy`注解将组件自动注入到自身：

```java
@Component
public class Calculator {
    @Autowired
    @Lazy
    private Calculator self;

    @MethodCache
    public int calculate(int n) {
        // 方法实现
        return self.calculate(n - 1) + self.calculate(n - 2);
    }
}
```

### 性能比较

基于测试用例：

1. **普通方法调用**：无缓存，每次都进行完整计算
2. **@MethodCache（安全模式）**：使用参数深度比较进行缓存
3. **@MethodCache（快速模式）**：使用基于字符串的参数比较进行最快速的缓存

在处理复杂参数或频繁缓存查找时，快速缓存模式可以提供显著的性能改进。

### 实现细节

缓存机制使用以下技术实现：

- Spring AOP用于方法拦截
- 并发哈希映射实现线程安全
- 自定义注解处理
- 延迟代理注入支持自调用

### 重要说明

1. 缓存存储在内存中，应用重启时会被清除
2. 快速缓存模式以牺牲一定安全性为代价换取性能 - 仅在确定参数的字符串表示足够作为缓存键时使用
3. 对于内部方法调用，始终使用自动注入的引用（self）而不是this，以确保缓存正常工作

### 示例

这里是一个展示不同缓存模式的完整示例：

```java
@Component
public class Example {
    @Autowired
    @Lazy
    private Example self;

    // 使用安全参数比较的常规缓存
    @MethodCache
    public int normalCache(String param1, int param2) {
        // 耗时操作
        return result;
    }

    // 使用快速缓存提升性能
    @MethodCache(fastCache = true)
    public int fastCache(String param1, int param2) {
        // 耗时操作
        return result;
    }

    // 带缓存的内部方法调用
    @MethodCache
    public int recursiveCalculation(int n) {
        if (n <= 1) return n;
        return self.recursiveCalculation(n - 1) + self.recursiveCalculation(n - 2);
    }
}
