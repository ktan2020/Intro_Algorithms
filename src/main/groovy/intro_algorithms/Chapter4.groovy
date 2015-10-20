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

final class Chapter4 {

    static def Find_Max_Crossing_SubArray(A,low,mid,high) {
        def left_sum = Integer.MIN_VALUE
        def sum = 0        
        def (max_left,max_right) = [-1,-1]
        
        for (i in mid..low) {
            sum += A[i]
            if (sum > left_sum) {
                left_sum = sum
                max_left = i
            }
        }
        
        def right_sum = Integer.MIN_VALUE
        sum = 0
        
        for (j in mid+1 .. high) {
            sum += A[j]
            if (sum > right_sum) {
                right_sum = sum
                max_right = j
            }
        }
        [max_left, max_right, left_sum + right_sum]
    }
        
    static def Find_Maximum_SubArray(A,low,high) {
        if (high == low) {
            return [low, high, A[low]]
        } else {
            def mid = (low+high).intdiv(2)            
            def (left_low,left_high,left_sum) = Find_Maximum_SubArray(A,low,mid)
            def (right_low,right_high,right_sum) = Find_Maximum_SubArray(A,mid+1,high)
            def (cross_low,cross_high,cross_sum) = Find_Max_Crossing_SubArray(A,low,mid,high)
            if (left_sum >= right_sum && left_sum >= cross_sum) {
                return [left_low,left_high,left_sum]
            } else if (right_sum >= left_sum && right_sum >= cross_sum) {
                return [right_low,right_high,right_sum]
            } else {
                return [cross_low,cross_high,cross_sum]
            }
        }
    }
    
    static def Square_Matrix_Multiply(A,B) {
        def n = A.size()
        // Let C be new nxn matrix
        def C = (0..n).collect { (0..n).collect {0} }
        
        for (i in 1..n) {
            for (j in 1..n) {
                C[i][j] = 0
                for (k in 1..n) {
                    C[i][j] += A[i][k]*B[k][j]
                }
            }
        }
        C
    }
    
    
}
