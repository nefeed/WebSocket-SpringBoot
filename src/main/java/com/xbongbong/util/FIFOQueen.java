package com.xbongbong.util;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc: 先进先出队列
 * Date: 2017-03-07
 * Time: 09:57
 */
public class FIFOQueen<T> {
    protected static final String TAG = "FIFOQueen";
    /**
     * 队列第一个元素
     */
    private Node first;
    /**
     * 队列最后元素
     */
    private Node last;
    /**
     * 队列大小
     */
    private int size = 0;

    /**
     * 队列结构
     */
    private class Node {
        T item;
        Node next;
    }

    /**
     * 队列是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 将元素压入队列尾部
     *
     * @param item
     */
    public void push(T item) {
        if (size == 0) {
            first = new Node();
            first.item = item;
            last = first;
            size++;
        } else if (size > 0) {
            Node newLast = new Node();
            newLast.item = item;
            last.next = newLast;
            last = newLast;
            size++;
        }
    }

    /**
     * 取出队列的第一个元素
     *
     * @return
     */
    public T pop() {
        if (size == 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Node oldFirst = first;
        first = first.next;
        size--;
        return oldFirst.item;
    }

    /**
     * 取队列深度
     *
     * @return
     */
    public int size() {
        return size;
    }
}
