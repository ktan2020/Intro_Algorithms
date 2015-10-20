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


class Chapter7_Test {

    /**
     * Example from page 172
     */
    @Test
    void test_partition() {
        def A = [2,8,7,1,3,5,6,4]
        
        assert Chapter7.Partition([null]+A, 1, A.size()) == 4
    }
    
    @Test
    void test_quicksort() {
        def A = [2,8,7,1,3,5,6,4]
        
        assert Chapter7.QuickSort([null]+A, 1, A.size()).tail() == A.sort()
    }
    
    @Test
    void test_randomized_quicksort() {
        def A = [2,8,7,1,3,5,6,4]
        
        assert Chapter7.Randomized_QuickSort([null]+A, 1, A.size()).tail() == Chapter7.QuickSort([null]+A, 1, A.size()).tail()
    }
    
}

