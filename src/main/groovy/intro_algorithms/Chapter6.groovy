/*************************************************************************
 * Copyright (c) 2014-2015 Kenji Tan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 *************************************************************************/


package intro_algorithms

final class Chapter6 {

    static def Parent(i) { i.intdiv(2) ?: 1 }
    
    static def Left(i) { 2*i }
    
    static def Right(i) { 2*i+1 }
    
    static boolean Is_MaxHeap_Property(A,i) {
        A[Parent(i)] >= A[i]
    }
    
    static boolean Is_MinHeap_Property(A,i) {
        A[Parent(i)] <= A[i]
    }

    static def Max_Heapify(A,i) {
        def l = Left(i)
        def r = Right(i)
        def largest 
        if (l<=A.size() && A[l]>A[i]) {
            largest = l
        } else {
            largest = i
        }
        if (r<=A.size() && A[r]>A[largest]) {
            largest = r
        }
        if (largest!=i) {
            // exchange A[i] with A[largest]
            A.swap(i,largest)
            Max_Heapify(A,largest)
        }
        A
    }
    
    static def Build_Max_Heap(A) {
        for (i in A.size().intdiv(2)..1) {
            Max_Heapify(A,i)
        }
        A
    }

    static def Heap_Sort(A) {
        def L=[]
        Build_Max_Heap(A)
        //println "maxheap: ${A}"
        for (i in A.size()-1..1) {
            // exchange A[1] with A[i]
            A.swap(1,i)
            // A.heap_size = A.heap_size - 1
            L.add(0,A[-1])
            A = A[0..<-1]
            
            Max_Heapify(A,1)
        }
        L
    }
    
    static def Heap_Maximum(A) {
        A[1]
    }
    
    static def Heap_Extract_Max(A) {
        def max = A[1]
        A[1] = A[A.size()]
        A = A[0..<-1]
        Max_Heapify(A,1)
        max
    }
    
    static def Heap_Increase_Key(A,i,key) {
        if (key < A[i]) throw new RuntimeException("New key is smaller than current key!")
        A[i] = key        
        while (i>1 && A[Parent(i)]<A[i]) {
            A.swap(i, Parent(i))
            i = Parent(i)
        }
    }
    
    static def Max_Heap_Insert(A,key) {
        A << Integer.MIN_VALUE
        Heap_Increase_Key(A,A.size(),key)
    }
    
}


