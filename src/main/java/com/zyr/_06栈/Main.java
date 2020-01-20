package com.zyr._06栈;

/**
 * @author zyr
 * @Description
 * @Date 2020/1/20
 */
public class Main
{
	public static void main(String[] args) {
		Stack<Integer> stack = new Stack();
		stack.push(11);
		stack.push(22);
		stack.push(33);
		stack.push(44);

		while(!stack.isEmpty()) {
			System.out.println(stack.pop());
		}

		System.out.println(6/-132);

	}


}
