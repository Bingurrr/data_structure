import java.util.Arrays;

interface MyStack<E> {
    public void clear();
    public void push(E item);
    public E pop();
    public E top();
    public int length();
}

/* A stack implementation using Array */
class AStack<E> implements MyStack<E> {
    private static final int DEFAULT_CAPACITY = 10;
    E[] data;
    int top;
    
    public AStack(){
        data = (E[]) new Object[DEFAULT_CAPACITY];
        top = -1;
    }
    public void clear(){
        top = -1;
    }
    public void push(E item){
        if(top+1 >= data.length){
            data = Arrays.copyOf(data,data.length*2);
        }
        data[++top] = item;
    }
    public E pop(){
        if(top == -1)
            return null;
        else 
            return data[top--];
    }
    public E top(){
        if(top == -1){
            return null;
        }
        else{
            return data[top];
        }
    }
    public int length(){
        return top+1;
    }
}

/* A stack implementation using Link */
class LStack<E> implements MyStack<E> {
    
    Link<E> head;
    int size;
    
    public LStack(){
        head = new Link<E>(null, null);
        size = 0;
    }
    
    public void clear(){
        size = 0;
    }
    public void push(E item){
        if(size==0){
            Link<E> ret = new Link(item, null);
            head = ret;
            size++;
        }
        else{
            Link<E> ret = new Link(item, head);
            head = ret;
            size++;
        }
    }
    public E pop(){
        if(size ==0){
            return null;
        }
        else {
            E ret = head.item;
            head = head.next;
            size--;
            return ret;
        }
    }
    
    public E top(){
        if(size == 0){
            return null;
        }
        else{
            return head.item;
        }
    }
    public int length(){
        return size;   
    }
}

class Link<E>{
    public E item;
    public Link<E> next;
    public Link(E item, Link<E> next){
        this.item = item;
        this.next = next;
    }
}

class Solution {
    public int solution(int[] nums) {
        
        MyStack<Integer> stack_a = new AStack<>();
        MyStack<Integer> stack_b = new LStack<>();
        
        int checksum = 0;
        
        for(int n : nums){
            stack_a.push(n);
            checksum += stack_a.length();
        }
        
        while(stack_a.top() != null){
            checksum += stack_a.top();
            stack_b.push(stack_a.pop());
            checksum += stack_b.length();
            checksum %= 7919;
        }
        
        int len = stack_b.length();
        for(int i=0; i<len; i++){
            checksum += stack_b.pop();
           checksum %= 7919;
        }
        
        for(int n : nums){
            stack_a.push(n);
            stack_b.push(n);
        }
        stack_a.clear();
        stack_b.clear();
        
        if(stack_a.pop() == null && stack_a.length() == 0) checksum+=1;
        if(stack_b.pop() == null && stack_a.length() == 0) checksum+=7;
        checksum %= 7919;
        
        return checksum;
    }
}
