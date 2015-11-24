
public class ArrayQueue<E> implements Queue<E> {
	
	private int size;
	private int head;
	private int tail;
	private final int MAX_SIZE;
	private  E[] q;
	
	public ArrayQueue(int capacity) {
		this.MAX_SIZE=capacity;
		this.tail=0;
		this.size=0;
		this.head=0;
		q= (E[])new Object [MAX_SIZE];
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return (size==0);
	}

	@Override
	public E front() {
		return  q[head];
	}

	@Override
	public E dequeue() {
		E o =null;
		if (!this.isEmpty())  {
			o=q[head];
			this.size--;
			this.head++;
			if(head==MAX_SIZE){
				head=0;
			}
		}else{
			System.out.println("The queue is empty");
		}
		return  o;
	}

	@Override
	public void enqueue(E element) {
		if (size<MAX_SIZE) {
			q[tail]=element;
			tail++;
			size++;
			if(tail==MAX_SIZE){
				tail=0;
			}
		}else{
			System.out.println("The queue is full");
		}
		
		
	}

}
