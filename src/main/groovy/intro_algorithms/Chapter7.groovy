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

final class Chapter7 {

    static def QuickSort(A,p,r) {
        if (p<r) {
            def q = Partition(A,p,r)
            QuickSort(A,p,q-1)
            QuickSort(A,q+1,r)
        }
        A
    }
    
    static int Partition(A,p,r) {
        def x = A[r]
        def i = p-1
        for (j in p..r-1) {
            if (A[j]<=x) {
                i += 1
                A.swap(i,j)
            }
        }
        A.swap(i+1, r)
        //println "A:${A}"
        i+1
    }
    
    static int Random(p,r) {
        p + (new Random().nextInt(r-p))
    }
    
    static int Randomized_Partition(A,p,r) {
        def i = Random(p,r)
        A.swap(r,i)
        Partition(A,p,r)
    }
    
    static def Randomized_QuickSort(A,p,r) {
        if (p<r) {
            def q = Randomized_Partition(A,p,r)
            Randomized_QuickSort(A,p,q-1)
            Randomized_QuickSort(A,q+1,r)
        }
        A
    }
    
    
}