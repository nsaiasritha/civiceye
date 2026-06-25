package civic;
import java.util.ArrayList;

public class MaxHeap {
	 ArrayList<PriorityIncident> heap =
	            new ArrayList<>();

	    int parent(int i) {
	        return (i - 1) / 2;
	    }

	    int left(int i) {
	        return 2 * i + 1;
	    }

	    int right(int i) {
	        return 2 * i + 2;
	    }

	    void swap(int i, int j) {

	        PriorityIncident temp = heap.get(i);

	        heap.set(i, heap.get(j));
	        heap.set(j, temp);
	    }

	    public void insert(PriorityIncident item) {

	        heap.add(item);

	        int i = heap.size() - 1;

	        while (i > 0 &&
	                heap.get(parent(i)).priority <
	                        heap.get(i).priority) {

	            swap(i, parent(i));

	            i = parent(i);
	        }
	    }

	    public PriorityIncident extractMax() {

	        if (heap.isEmpty())
	            return null;

	        PriorityIncident root = heap.get(0);

	        PriorityIncident last =
	                heap.remove(heap.size() - 1);

	        if (!heap.isEmpty()) {

	            heap.set(0, last);

	            heapify(0);
	        }

	        return root;
	    }

	    void heapify(int i) {

	        int largest = i;

	        int l = left(i);
	        int r = right(i);

	        if (l < heap.size() &&
	                heap.get(l).priority >
	                        heap.get(largest).priority)
	            largest = l;

	        if (r < heap.size() &&
	                heap.get(r).priority >
	                        heap.get(largest).priority)
	            largest = r;

	        if (largest != i) {

	            swap(i, largest);

	            heapify(largest);
	        }
	    }

	    public void display() {

	        for (PriorityIncident p : heap) {

	            System.out.println(
	                    p.incident +
	                            " Priority = " +
	                            p.priority);
	        }
	    }
}
