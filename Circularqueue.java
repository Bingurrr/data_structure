import java.util.Arrays;
import java.util.Random;

interface MyQueue<E> {
	public void clear();
	public void enqueue(E item);
	public E dequeue();
	public E front();
	public int length();
}

class AQueue<E> implements MyQueue<E> {
	private static int DEFAULT_CAPACITY = 10; 
	E[] data;
	int front, rear, size;
	
	public AQueue() {
		data = (E[]) new Object[DEFAULT_CAPACITY];
		front = 0;
		rear = 0;
	}

    public void clear(){
        size = 0;
        front = 0;
        rear = 0;
    } 
	public void enqueue(E item){
        //더블링
        if(size == data.length-1){
            data = Arrays.copyOf(data, data.length*2);
            //front앞에 있는거 뒤에 붙이기
            for(int i = 0; i <= rear; i ++){
                data[size +1 + i] = data[i];
            }
            //front를 배열 맨 앞으로 차례대로 댕기기
            for(int i = 0; i < size; i ++){
                data[i] = data[front+i];
            }
            //rear뒷 부분은 다 null로 처리
            for(int i = size; i < rear+1; i ++){
                data[i] = null;
            }
            rear = size;
            front = 0;
        }
        data[rear] = item;
        rear++;
        size++;
        rear = rear%(data.length);
    }
	public E dequeue(){
        if(size ==0) {return null;}
        E ret = data[front];
        data[front] = null;
        front++;
        front = front%(data.length);
        size--;
        return ret;
    }
	public E front(){
        if(size ==0) {return null;}
        return data[front];
    }
	public int length(){
        return size;
    }
    
    
}

class Solution {
    public int solution(int[] nums) {
        AQueue<Integer> queue = new AQueue<>();

		int checksum = 0;

		for (int n : nums) {
			if(n >= 0) {
				queue.enqueue(n);
				checksum += n;
				checksum %= 7919;
			}
			else {
				int x = queue.dequeue();
				checksum += x;
				checksum %= 7919;
			}
			checksum += queue.length();
			checksum %= 7919;
		}
		
		checksum += queue.length();
		checksum %= 7919;
		
		queue.clear();
		checksum += queue.length();
		checksum %= 7919;

		return checksum;
    }
}
