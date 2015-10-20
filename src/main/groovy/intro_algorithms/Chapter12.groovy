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




final class Chapter12 {

	
    static def Inorder_Tree_Walk_Str(x,S) {
        if (x!=null) {
            S=Inorder_Tree_Walk_Str(x.left,S)
            S = "${S} ${x.key}"
            S=Inorder_Tree_Walk_Str(x.right,S)
        }
        S.trim()
    }

    static def Inorder_Tree_Walk(x) {
        if (x!=null) {
            Inorder_Tree_Walk(x.left)
            println "key: ${x.key}"
            Inorder_Tree_Walk(x.right)
        }
    }
    
    static def Tree_Search(x,k) {
        if (x==null || k==x.key) { return x }
        if (k<x.key) {
            return Tree_Search(x.left, k)
        } else {
            return Tree_Search(x.right, k)
        }
    }
    
    static def Iterative_Tree_Search(x,k) {
        while (x!=null && k!=x.key) {
            if (k<x.key) {
                x=x.left
            } else {
                x=x.right
            }
        }
        x
    }
    
    static def Tree_Minimum(x) {
        while (x.left!=null) {
            x=x.left
        }
        x
    }
    
    static def Tree_Maximum(x) {
        while (x.right!=null) {
            x=x.right
        }
        x
    }
    
    static def Tree_Successor(x) {
        if (x.right!=null) { return Tree_Minimum(x.right) }
        def y=x.p
        while (y!=null && x==y.right) {
            x=y
            y=y.p
        }
        y
    }
    
    static def Tree_Insert(T,z) {
        def y=null
        def x=T.root
        while (x!=null) {
            y=x
            if (z.key < x.key) {
                x=x.left
            } else {
                x=x.right
            }
        }
        z.p=y
        if (y==null) {
            T.root=z
        } else if(z.key < y.key) {
            y.left=z
        } else {
            y.right=z
        }
    }
    
    static def Transplant(T,u,v) {
        if (u.p==null) { T.root=v }
        else if (u==u.p.left) {
            u.p.left = v
        } else {
            u.p.right = v
        }
        if (v!=null) { 
            v.p = u.p 
        }
    }
    
    static def Tree_Delete(T,z) {
        if (z.left==null) {
            Transplant(T,z,z.right)
        } else if (z.right==null) {
            Transplant(T,z,z.left)
        } else {
            def y = Tree_Minimum(z.right)
            if (y.p!=z) {
                Transplant(T,y,y.right)
                y.right = z.right
                y.right.p = y
            }
            Transplant(T,z,y)
            y.left = z.left
            y.left.p = y
        }
        T
    }
    

}


