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

import org.junit.Test


class Chapter6_Test {

    boolean is_sorted(A) {
        for (i in 0..<(A.size()-1)) {
            if (A[i] > A[i+1]) { return false } 
        }
        true
    }

    /**
     * Example from page 152
     */
    @Test
    void test_ismax_heap() {
        def H = [16,14,10,8,7,9,3,2,4,1]
        
        //println Chapter6.Is_MaxHeap_Property(H,1)
        for (i in 1..<H.size()) {
            assert Chapter6.Is_MaxHeap_Property([null]+H,i)
        }
    }
    
    /**
     * Example from page 155
     */
    @Test
    void test_max_heapify() {
        def H = [16,4,10,14,7,9,3,2,8,1]
        
        assert Chapter6.Max_Heapify([null]+H, 2).tail() == [16, 14, 10, 8, 7, 9, 3, 2, 4, 1]
    }

    /**
     * Example from page 158
     */
     @Test
     void test_build_maxheap() {
         def A = [4,1,3,2,16,9,10,14,8,7]
         
         assert Chapter6.Build_Max_Heap([null]+A).tail() == [16, 14, 10, 8, 7, 9, 3, 2, 4, 1]

     }
     
     @Test
     void test_heap_sort() {
         def A = [16,14,10,8,7,9,3,2,4,1]
         
         assert is_sorted(Chapter6.Heap_Sort([null]+A))
     }
     
          
}