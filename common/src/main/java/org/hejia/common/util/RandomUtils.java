package org.hejia.common.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 生成四位和六位的随机数字
 */
public class RandomUtils {

	private static final Random random = new Random();

	private static final DecimalFormat fourdf = new DecimalFormat("0000");

	private static final DecimalFormat sixdf = new DecimalFormat("000000");

	public static String getFourBitRandom() {
		return fourdf.format(random.nextInt(10000));
	}

	public static String getSixBitRandom() {
		return sixdf.format(random.nextInt(1000000));
	}

	/**
	 * 给定数组，抽取n个数据
	 * @param list 待抽取的数组
	 * @param n 抽取的个数
	 * @return 抽取结果
	 */
	public static ArrayList<Object> getRandom(List<Object> list, int n) {

		Random random = new Random();

		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

		// 生成随机数字并存入HashMap
		for (int i = 0; i < list.size(); i++) {

			int number = random.nextInt(100) + 1;

			hashMap.put(number, i);
		}

		// 从HashMap导入数组
		Object[] objects = hashMap.values().toArray();

		ArrayList<Object> r = new ArrayList<>();

		// 遍历数组并打印数据
		for (int i = 0; i < n; i++) {
			r.add(list.get((int) objects[i]));
			System.out.print(list.get((int) objects[i]) + "\t");
		}
		System.out.print("\n");
		return r;
	}
}
