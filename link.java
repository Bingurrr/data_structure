class Link<E>{
    public E item;
    public Link<E> next;
    
    public Link(E item, Link<E> next){
        this.item = item;
        this.next = next;
    }
    E item() {return item;}
    Link<E> next() {return next;}
    
    
}
class Solution {
    public int solution(int not_used) {
        
 
        Link<Integer> link9 = new Link<>(9, null);
        Link<Integer> link7 = new Link<>(7, link9);
        Link<Integer> link5 = new Link<>(5, link7);
        Link<Integer> link3 = new Link<>(3, link5);
        
      
        int sum = link3.item() + link3.next().item() + link3.next().next().item() + link3.next().next().next().item();
        return sum;
    }
}
