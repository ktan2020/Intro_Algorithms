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

final class Chapter2 {
    
    static def Insertion_Sort(A) {
        for (j in 2..<A.size()) {
            def key = A[j]
            // Insert A[j] into the sorted sequence A[1..j-1]
            def i = j-1
            while (i>0 && A[i]>key) {
                A[i+1] = A[i]
                i = i-1                
            }
            A[i+1] = key
        }
        A
    }

    static def Merge(A,p,q,r) { 
        def n1 = q-p+1
        def n2 = r-q
        // Let L[1..n1+1] and R[1..n2+1] be new arrays
        def L = (0..n1+1).collect { null }
        def R = (0..n2+1).collect { null }
        for (i in 1..n1) {
            L[i] = A[p+i-1]
        }
        for (j in 1..n2) {
            R[j] = A[q+j]
        }
        
        L[n1+1] = Integer.MAX_VALUE
        R[n2+1] = Integer.MAX_VALUE        
        def i = 1
        def j = 1
        
        for (k in p..r) {
            if (L[i] <= R[j]) {
                A[k] = L[i]
                i = i+1
            } else {
                A[k] = R[j]
                j = j+1
            }
        }
    }
    
    static def Merge_Sort(A,p,r) { 
        if (p < r) {
            def q = (p+r).intdiv(2)
            Merge_Sort(A,p,q)
            Merge_Sort(A,q+1,r)
            Merge(A,p,q,r)
        }
        A
    }
    
}
