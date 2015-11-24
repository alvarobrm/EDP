
public interface Queue<E> {
	
	public int size();
	/**
	* Returns whether the queue is empty.
	* @return true if the queue is empty, false otherwise. */
	public boolean isEmpty();
	/**
	* Inspects the element at the front of the queue. * @return element at the front of the queue.
	*/
	public E front();
	/**
	* Inserts an element at the rear of the queue. * @param element new element to be inserted. */
	public void enqueue (E element);
	/**
	* Removes the element at the front of the queue. * @return element removed.
	*/
	public E dequeue();
	
}
