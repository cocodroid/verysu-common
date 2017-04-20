package com.verysu.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CircleQueue<T> {

	/**
	 * 默认容量大小
	 */
	private final int DEFAULT_CAPACITY = 100;
	/**
	 * 容量
	 */
	private int capacity;
	/**
	 * 队列存放的元素
	 */
	private Object[] elementData;
	/**
	 * 队头
	 */
	private int head = 0;
	/**
	 * 队尾
	 */
	private int tail = 0;
	/**
	 * 队列存放元素个数
	 */
	private int length = 0;
	
	public CircleQueue() {
		this.capacity = DEFAULT_CAPACITY;
		this.elementData = new Object[capacity];
	}
	
	public CircleQueue(int capacity) {
		this.capacity = capacity;
		this.elementData = new Object[capacity];
	}
	
	public int size(){
		if(isEmpty()){
			return 0;
		}else if (isFull()) {
			return capacity;
		}else {
			return length;
		}
	}
	
	public boolean isFull(){
		if(length == capacity){
			return true;
		}
		return false;
	}
	
	public boolean isEmpty(){
		if(length == 0){
			return true;
		}
		return false;
	}
	
	public void addToQueue(T element) throws Exception{
		if(isFull()){
			throw new Exception("队列已满，添加失败！");
		}
		elementData[tail] = element;
		tail++;
		tail = tail % capacity;
		length++;
	}
	
	public Object delFromQueue() throws Exception{
		if(isEmpty()){
			throw new Exception("队列已空，删除失败！");
		}
		Object obj = elementData[head];
		head++;
		head = head % capacity;
		length--;
		return obj;
	}
	
	public Object[] traverseQueue(){
		List queue = new ArrayList(length);
		for (int i = head; i < head + length; i++) {
			queue.add(elementData[i%capacity]);
		}
		return queue.toArray();
	}
	
	public void clearQueue(){
		Arrays.fill(elementData, null);
		head = tail = 0;
	}
	
	public static void main(String[] args) throws Exception {
		String s1 = "s1";
		String s2 = "s2";
		String s3 = "s3";
		String s4 = "s4";
		String s5 = "s5";
		CircleQueue circleQueue = new CircleQueue<String>(5);
		circleQueue.addToQueue(s1);
		circleQueue.addToQueue(s2);
//		circleQueue.addToQueue(s2);
		System.out.println("head:"+circleQueue.head);
		System.out.println("tail:"+circleQueue.tail);
		System.out.println(circleQueue.size());
		circleQueue.delFromQueue();
		System.out.println("head:"+circleQueue.head);
		System.out.println("tail:"+circleQueue.tail);
		System.out.println(circleQueue.size());
		circleQueue.addToQueue(s3);
 		circleQueue.addToQueue(s4);
 		System.out.println(circleQueue.size());
		circleQueue.addToQueue(s5);
		circleQueue.delFromQueue();
		circleQueue.delFromQueue();
		System.out.println("head:"+circleQueue.head);
		System.out.println("tail:"+circleQueue.tail);
		System.out.println(circleQueue.size());
		circleQueue.delFromQueue();
		circleQueue.delFromQueue();
		System.out.println("head:"+circleQueue.head);
		System.out.println("tail:"+circleQueue.tail);

		System.out.println(circleQueue.isEmpty());
		System.out.println(circleQueue.isFull());
		System.out.println(Arrays.toString(circleQueue.traverseQueue()));
		System.out.println(circleQueue.size());
		
		
		
		
	}
}
