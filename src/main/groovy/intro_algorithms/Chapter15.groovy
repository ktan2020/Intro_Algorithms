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

final class Chapter15 {


    static def Cut_Rod(p,n) {
        if (n==0) { return 0 }
        def q = Integer.MIN_VALUE
        
        for (i in 1..n) {
            q = [q, p[i] + Cut_Rod(p,n-i)].max()
        }
        q
    }
    
    static def Memoized_Cut_Rod(p,n) {
        // Let r[0..n] be new array
        def r = (0..n).collect { Integer.MIN_VALUE }
        
        Memoized_Cut_Rod_Aux(p,n,r)
    }
    
    static def Memoized_Cut_Rod_Aux(p,n,r) {
        def q
        if (r[n]>=0) { return r[n] }
        if (n==0) {
            q = 0
        } else {
            q = Integer.MIN_VALUE
            for (i in 1..n) {
                q = [q, p[i] + Memoized_Cut_Rod_Aux(p,n-i,r)].max()
            }
        }
        r[n] = q
        q
    }
    
    static def Bottom_Up_Cut_Rod(p,n) {
        // Let r[0..n] be new array
        def r = (0..n).collect { 0 }
        for (j in 1..n) {
            def q = Integer.MIN_VALUE
            for (i in 1..j) {
                q = [q, p[i] + r[j-i]].max()
            }
            r[j] = q
        }
        r[n]
    }
    
    static def Extended_Bottom_Up_Cut_Rod(p,n) {
        // Let r[0..n] and s[0..n] be new arrays
        def (r,s) = [(0..n).collect { 0 }, (0..n).collect {0}]
        r[0] = 0        
        for (j in 1..n) {
            def q = Integer.MIN_VALUE
            for (i in 1..j) {
                if (q < p[i]+r[j-i]) {
                    q = p[i] + r[j-i]
                    s[j] = i
                }
            }
            r[j] = q
        }
        [r,s]
    }
    
    static def Print_Cut_Rod_Solution(p,n) {
        def L = []
        def (r,s) = Extended_Bottom_Up_Cut_Rod(p,n)
        while (n>0) {
            L << s[n]
            n -= s[n]
        }
        L
    }
    
    static def Matrix_Multiply(A,B) {
        if (A[0].size() != B.size()) { new RuntimeException("Incompatible Dimensions!") }
        else {
            // Let C be new A.rows x B.columns matrix
            def C = (1..A.size()).collect { (1..B[0].size()).collect {0} } 
            for (i in 0..<A.size()) {
                for (j in 0..<B[0].size()) {
                    C[i][j] = 0
                    for (k in 0..<A[0].size()) {
                        C[i][j] = C[i][j] + A[i][k]*B[k][j]
                    }
                }
            }
            return C
        }
    }    
        
    static def Matrix_Chain_Order(p) {
        def n = p.size()-1
        // Let m[1..n,1..n] and s[1..n-1,2..n] be new tables
        def m = (0..n).collect { (0..n).collect {0} }
        def s = (0..n-1).collect { (1..n).collect {0} }
        
        for (i in 1..n) {
            m[i][i] = 0
        }
        for (l in 2..n) {
            for (i in 1..(n-l+1)) {
                def j = i+l-1
                m[i][j] = Integer.MAX_VALUE
                for (k in i..(j-1)) {
                    def q = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j]
                    if (q < m[i][j]) {
                        m[i][j] = q
                        s[i][j] = k
                    }
                }
            }
        }
        [m,s]
    }
        
    static def Print_Optimal_Parens(s,i,j) {
        if (i==j) {
            print "A${i}"
        }
        else {
            print "("
            Print_Optimal_Parens(s,i,s[i][j])
            Print_Optimal_Parens(s,s[i][j]+1,j)
            print ")"
        }
    }
    
    static def Print_Optimal_Parens_Str(s,i,j,S="") {
        if (i==j) {
            S += "A${i}"
        }
        else {
            S += "("
            S = Print_Optimal_Parens_Str(s,i,s[i][j],S)
            S = Print_Optimal_Parens_Str(s,s[i][j]+1,j,S)
            S += ")"
        }
        S
    }
        
    static def Matrix_Chain_Multiply(A,s,i,j) {
        if (j<i) {
            def X = Matrix_Chain_Multiply(A,s,i,s[i][j])
            def Y = Matrix_Chain_Multiply(A,s,s[i][j]+1,j)
            return Matrix_Multiply(X,Y)
        } else {
            return A[i]
        }
    }
    
    static def Recursive_Matrix_Chain(m,p,i,j) {
        if (i==j) {
            return 0
        } 
        m[i][j] = Integer.MAX_VALUE
        for (k in i..(j-1)) {
            def q = Recursive_Matrix_Chain(p,i,k) + 
                    Recursive_Matrix_Chain(p,k+1,j) +
                    p[i-1]*p[k]*p[j]
            if (q < m[i][j]) {
                m[i][j] = q
            }
        }
        m[i][j]
    }
    
    static def Memoized_Matrix_Chain(p) {
        def n=p.size()-1
        // Let m[1..n,1..n] be new table
        def m = (0..n).collect { (0..n).collect {0} }
        for (i in 1..n) {
            for (j in i..n) {
                m[i][j] = Integer.MAX_VALUE    
            }
        }
        return Lookup_Chain(m,p,1,n)
    }

    static def Lookup_Chain(m,p,i,j) {
        if (m[i][j] < Integer.MAX_VALUE) { return m[i][j] }
        if (i==j) {
            m[i][j] = 0
        } else {
            for (k in i..(j-1)) {
                def q = Lookup_Chain(m,p,i,k) +
                        Lookup_Chain(m,p,k+1,j) +
                        p[i-1]*p[k]*p[j]
                if (q < m[i][j]) {
                    m[i][j] = q
                }
            }
        }
        m[i][j]
    }
    
    enum DIR {
        UP,
        LEFT,
        DIAG,
    }
    
    static def LCS_Length(X,Y) {
        def m = X.size()
        def n = Y.size()
        X = ['-'] + X.split("").flatten() // put dummy element in 0th position since
        Y = ['-'] + Y.split("").flatten() // str indexed from 1
        println "X:${X}, Y:${Y}"
        
        // Let b[1..m,1..n] and c[0..m,0..n] be new tables
        def b = (0..m).collect { (0..n).collect {0} }
        def c = (0..m).collect { (0..n).collect {0} }
        
        for (i in 1..m) { c[i][0] = 0 }
        for (j in 0..n) { c[0][j] = 0 }
        
        for (i in 1..m) {
            for (j in 1..n) {
                if (X[i] == Y[j]) {
                    c[i][j] = c[i-1][j-1] + 1
                    b[i][j] = DIR.DIAG
                } else if (c[i-1][j] >= c[i][j-1]) {
                    c[i][j] = c[i-1][j]
                    b[i][j] = DIR.UP
                } else {
                    c[i][j] = c[i][j-1]
                    b[i][j] = DIR.LEFT
                }
            }
        }
        
        [c,b]
    }
  
    static def Print_LCS(b,X,i,j) {
        if (i==0 || j==0) return
        
        if (b[i][j] == DIR.DIAG) {
            Print_LCS(b,X,i-1,j-1)
            print X[i]
        } else if (b[i][j] == DIR.UP) {
            Print_LCS(b,X,i-1,j)
        } else {
            Print_LCS(b,X,i,j-1)
        }
    }
    
    static def Print_LCS_Str(b,X,i,j,S="") {
        if (i==0 || j==0) return S
        
        if (b[i][j] == DIR.DIAG) {
            S = Print_LCS_Str(b,X,i-1,j-1,S)
            S += X[i]
        } else if (b[i][j] == DIR.UP) {
            S = Print_LCS_Str(b,X,i-1,j,S)
        } else {
            S = Print_LCS_Str(b,X,i,j-1,S)
        }
    }

}
