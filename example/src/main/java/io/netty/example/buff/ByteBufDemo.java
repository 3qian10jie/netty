package io.netty.example.buff;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * @author Administrator
 * @date 2024-02-08 16:29
 */
public class ByteBufDemo {
    public static void main(String[] args) {
//        testRelease();
        testCache();
    }

    public static void testRelease() {
        int page = 1024 * 8;
        // 获取PooledByteBufAllocator实例。
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;

        // 创建一个ByteBuf，申请16KB内存
        ByteBuf byteBuf1 = allocator.directBuffer(2 * page);

        // 回收ByteBuf、缓存内存
        byteBuf1.release();
    }

    public static void testCache() {
        int page = 1024 * 8;
        // 获取PooledByteBufAllocator实例。
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;

        // 先创建一个ByteBuf，申请16kb内存，即两个页
        ByteBuf byteBuf1 = allocator.directBuffer(2 * page);
        // 回收ByteBuf，这一步会缓存内存、将ByteBuf对象扔进对象池，详细等后边博客更新。
        byteBuf1.release();

        // 再次获取ByteBuf，申请同样大小的内存，就可以触发缓存了
        // 我们要追的就是这行代码。
        ByteBuf byteBuf2 = allocator.directBuffer(2 * page);
        byteBuf2.release();
    }
}
