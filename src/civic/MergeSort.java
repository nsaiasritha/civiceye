package civic;

public class MergeSort {
	   public static void sort(
	            Incident[] arr,
	            int left,
	            int right) {

	        if (left < right) {

	            int mid =
	                    (left + right) / 2;

	            sort(arr,
	                    left,
	                    mid);

	            sort(arr,
	                    mid + 1,
	                    right);

	            merge(arr,
	                    left,
	                    mid,
	                    right);
	        }
	    }

	    private static void merge(
	            Incident[] arr,
	            int left,
	            int mid,
	            int right) {

	        int n1 =
	                mid - left + 1;

	        int n2 =
	                right - mid;

	        Incident[] L =
	                new Incident[n1];

	        Incident[] R =
	                new Incident[n2];

	        for (int i = 0;
	             i < n1;
	             i++) {

	            L[i] =
	                    arr[left + i];
	        }

	        for (int j = 0;
	             j < n2;
	             j++) {

	            R[j] =
	                    arr[mid + 1 + j];
	        }

	        int i = 0;
	        int j = 0;
	        int k = left;

	        while (i < n1 &&
	                j < n2) {

	            if (L[i].incidentId
	                    <=
	                    R[j].incidentId) {

	                arr[k++] =
	                        L[i++];
	            }
	            else {

	                arr[k++] =
	                        R[j++];
	            }
	        }

	        while (i < n1) {

	            arr[k++] =
	                    L[i++];
	        }

	        while (j < n2) {

	            arr[k++] =
	                    R[j++];
	        }
	    }
}
