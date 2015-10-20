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


class Chapter15_Test {

    /**
     * Example from page 360
     */
    @Test
    void test_cut_rod_naive() {
        def P = [1,5,8,9,10,17,17,20,24,30]
    
        assert Chapter15.Cut_Rod([null]+P, 4) == 10
    }
    
    @Test
    void test_memoized_cut_rod() {
        def P = [1,5,8,9,10,17,17,20,24,30]
        
        assert Chapter15.Memoized_Cut_Rod([null]+P, 4) == Chapter15.Cut_Rod([null]+P, 4)
    }
    
    @Test
    void test_bottomup_cut_rod() {
        def P = [1,5,8,9,10,17,17,20,24,30]
        
        assert Chapter15.Bottom_Up_Cut_Rod([null]+P,4) == Chapter15.Memoized_Cut_Rod([null]+P, 4) 
    }
    
    @Test
    void test_extended_bottomup_cut_rod() {
        def P = [1,5,8,9,10,17,17,20,24,30]
    
        def lengths = Chapter15.Print_Cut_Rod_Solution([null]+P,4)
        assert lengths == [2,2]
        
        assert lengths.inject(0) { s,it -> s+=P[it-1] } == Chapter15.Cut_Rod([null]+P, 4)
    }
    
    /**
     * Example from page 376
     */
    @Test
    void test_matrix_chain_order() {
        // A1=30x35, A2=35x15, A3=15x5, A4=5x10, A5=10x20, A6=20x25
        def P = [30,35,15,5,10,20,25]
        
        def (m,s) = Chapter15.Matrix_Chain_Order(P)    
        
        assert m == [[0, 0, 0, 0, 0, 0, 0], [0, 0, 15750, 7875, 9375, 11875, 15125], [0, 0, 0, 2625, 4375, 7125, 10500], [0, 0, 0, 0, 750, 2500, 5375], [0, 0, 0, 0, 0, 1000, 3500], [0, 0, 0, 0, 0, 0, 5000], [0, 0, 0, 0, 0, 0, 0]]
        assert s == [[0, 0, 0, 0, 0, 0], [0, 0, 1, 1, 3, 3, 3], [0, 0, 0, 2, 3, 3, 3], [0, 0, 0, 0, 3, 3, 3], [0, 0, 0, 0, 0, 4, 5], [0, 0, 0, 0, 0, 0, 5]]
    }
    
    /**
     * Example from page 377
     */
    @Test
    void test_optimal_parens() {
        def P = [30,35,15,5,10,20,25]
        
        def (m,s) = Chapter15.Matrix_Chain_Order(P)    
        
        //Chapter15.Print_Optimal_Parens(s,1,P.size()-1)
        assert Chapter15.Print_Optimal_Parens_Str(s,1,P.size()-1) == "((A1(A2A3))((A4A5)A6))"
    }
    
    /**
     * Example from page 395
     */
    @Test
    void test_lcs() {
        def (X,Y) = ["ABCBDAB", "BDCABA"]
        def (c,b) = Chapter15.LCS_Length(X,Y)
        
        assert c == [[0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 1, 1, 1], [0, 1, 1, 1, 1, 2, 2], [0, 1, 1, 2, 2, 2, 2], [0, 1, 1, 2, 2, 3, 3], [0, 1, 2, 2, 2, 3, 3], [0, 1, 2, 2, 3, 3, 4], [0, 1, 2, 2, 3, 4, 4]]
        println b
        
        //Chapter15.Print_LCS(b, '-'+X, X.size(), Y.size())
        assert Chapter15.Print_LCS_Str(b, '-'+X, X.size(), Y.size()) == "BCBA"
    }
     
     
}

