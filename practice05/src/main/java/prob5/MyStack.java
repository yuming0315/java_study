package prob5;

public class MyStack {
	private int top;
	private String[] buffer;

	public MyStack(int capacity) {
		top = -1;
		buffer = new String[capacity];
	}

	public void push(String s) {
		if (top == buffer.length - 1) {
			resize();
		}

		buffer[++top] = s;		
	}

	public String pop() throws MyStackException {
		if (isEmpty()) {
			throw new MyStackException("stack is empty");
		}

		String result = buffer[top];		
		buffer[top--] = null;

		return result;
	}

	public boolean isEmpty() {
		return top == -1;
	}

	private void resize() {
		String[] temp = new String[buffer.length * 2];
		for (int i = 0; i <= top; i++) {
			temp[i] = buffer[i];
		}

		buffer = temp;
	}
}