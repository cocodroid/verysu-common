package com.verysu.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CircleQueue2<T> {

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
	/**
	 *环形队列 指针
	 */
	private static AtomicInteger currentIndex = new AtomicInteger(0);
	
	public int nextCurrentIndex(){
		Set<T> t = (Set<T>) elementData[currentIndex.get()];
		if(t != null && t.size() > 0)
			System.out.println(Arrays.toString(t.toArray()));
		currentIndex.set((currentIndex.get()+1) % this.capacity);
		return currentIndex.get();
	}
	
	public CircleQueue2() {
		this.capacity = DEFAULT_CAPACITY;
		this.elementData = new Object[capacity];
	}
	
	public CircleQueue2(int capacity) {
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
	
	/**
	 * 存放用户在环上的哪个1slot	
	 */
	static Map<String,Integer> uidInMap = new HashMap<String,Integer>();
	
	public static Set<String> uuids = new HashSet<String>();
	
	{
		uuids.add("u-1");
		uuids.add("u-2");
		uuids.add("u-3");
		uuids.add("u-4");
		uuids.add("u-5");
		uuids.add("u-6");
		uuids.add("u-7");
		uuids.add("u-8");
		uuids.add("u-9");
		uuids.add("u-10");
		uuids.add("u-11");
		uuids.add("u-12");
		uuids.add("u-13");
		uuids.add("u-14");
		uuids.add("u-15");
		uuids.add("u-16");
		uuids.add("u-17");
		uuids.add("u-18");
		uuids.add("u-19");
		uuids.add("u-20");
	}
	/**
	 * 随机获得即将要登录的用户
	 */
	public String userRandom(){
		return (String) uuids.toArray()[new Random().nextInt(uuids.size())];
	}
	/**
	 * 模拟用户登录
	 * @param circleQueue
	 */
	public void userLogin(CircleQueue2 circleQueue, String uuid){
		//随机生成一个用户ID
		//模拟用户登录
		//剔除用户之前在环上的某个slot
		if(uidInMap.containsKey(uuid)){
			Integer slot = uidInMap.get(uuid);
			//找到用户ID在哪个slot，将其删除
			Set<String> slotSets = (Set<String>) circleQueue.elementData[slot];
			if(slotSets != null && slotSets.contains(uuid))
				slotSets.remove(uuid);
		}
		//重新在当前index之上设置
		int upIndex = currentIndex.get() == 0 ? capacity-1 : currentIndex.get()-1;
		Set<String> nowSets = (Set<String>) circleQueue.elementData[upIndex];
		if(nowSets == null){
			nowSets = new HashSet<String>();
		}
		nowSets.add(uuid);
		circleQueue.elementData[upIndex] = nowSets;
		uidInMap.put(uuid, upIndex);
	}
	/**
	 * 扫描超时
	 */
	public void timeOut(final CircleQueue2 circleQueue){
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		Runnable r = new Runnable(){
			@Override
			public void run() {
				try {
					if(circleQueue.currentIndex.get() % circleQueue.capacity == 0){
						System.out.println("------------【开始超时处理】-------------");
					}
					Set<String> nowSets = (Set<String>) circleQueue.elementData[currentIndex.get()];
					if(nowSets != null && nowSets.size() > 0)
						for (String uuid : nowSets) {
							System.out.println("超时用户："+uuid);
							//剔除用户之前在环上的某个slot
							if(uidInMap.containsKey(uuid)){
								Integer slot = uidInMap.get(uuid);
								//找到用户ID在哪个slot，将其删除
								Set<String> slotSets = (Set<String>) circleQueue.elementData[slot];
								if(slotSets != null && slotSets.contains(uuid))
									slotSets.remove(uuid);
								uidInMap.remove(uuid);
							}
						}
					circleQueue.elementData[currentIndex.get()] = null;
					System.out.println(circleQueue.currentIndex);
					circleQueue.nextCurrentIndex();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		};
		executorService.scheduleAtFixedRate(r, 1, 1, TimeUnit.SECONDS);
	}
	
	public static void main(String[] args) throws Exception {
		
		final CircleQueue2 circleQueue = new CircleQueue2<String>(30);

		//10秒某个用户登录
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		Runnable r = new Runnable(){
			@Override
			public void run() {
				try {
					String uuid = circleQueue.userRandom();
					System.out.println("用户登录："+uuid);
					circleQueue.userLogin(circleQueue, uuid);
					System.out.println("已登录用户："+Arrays.toString(uidInMap.keySet().toArray()));
					System.out.println("用户所在slot："+uidInMap);
					System.out.println("环上元素："+Arrays.toString(circleQueue.elementData));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		};
		executorService.scheduleAtFixedRate(r, 1000, 200, TimeUnit.MILLISECONDS);
		circleQueue.timeOut(circleQueue);
//		
//		String s1 = "s1";
//		String s2 = "s2";
//		String s3 = "s3";
//		String s4 = "s4";
//		String s5 = "s5";
//		String s6 = "s6";
//		String s7 = "s7";
//		String s8 = "s8";
//		String s9 = "s9";
//		String s10 = "s10";
//		final CircleQueue2 circleQueue = new CircleQueue2<String>(5);
//		Set<String> sets = new HashSet<String>();
//		sets.add(s1);
//		sets.add(s2);
//		circleQueue.addToQueue(sets);
//		
//		Set<String> sets2 = new HashSet<String>();
//		sets2.add(s3);
//		sets2.add(s4);
//		sets2.add(s5);
//		circleQueue.addToQueue(sets2);
//		
//		Set<String> sets3 = new HashSet<String>();
//		sets3.add(s10);
//		circleQueue.addToQueue(sets3);
//		
//		Set<String> sets4 = new HashSet<String>();
//		sets4.add(s6);
//		circleQueue.addToQueue(sets4);
//		
//		Set<String> sets5 = new HashSet<String>();
//		sets5.add(s7);
//		sets5.add(s8);
//		sets5.add(s9);
//		circleQueue.addToQueue(sets5);
//		
//		
//		System.out.println(circleQueue.size());
//		System.out.println(Arrays.toString(circleQueue.traverseQueue()));
		
		
	}
}
