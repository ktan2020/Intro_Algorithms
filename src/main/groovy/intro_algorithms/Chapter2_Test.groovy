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


class Chapter2_Test {

    boolean is_sorted(A) {
        for (i in 0..<(A.size()-1)) {
            if (A[i] > A[i+1]) { return false } 
        }
        true
    }
    
    @Test
    void test_insertion_sort() {
        def A = [5,2,4,6,1,3]
        
        //println Chapter2.Insertion_Sort([null] + A)
        assert is_sorted(Chapter2.Insertion_Sort([null] + A).tail())        
    }

    @Test
    void test_merge_sort() {
        def A = [2,4,5,7,1,2,3,6]
        
        //println Chapter2.Merge_Sort([null] + A, 1, A.size())
        assert is_sorted(Chapter2.Merge_Sort([null] + A, 1, A.size()).tail())
    }
    
}