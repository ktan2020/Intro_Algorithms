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


class Chapter12_Test {

    class MyTree {
        def key
        def left, right
        def p
        def root
    }
    
    def create_tree(T, k) {
        if (T==null) {
            return new MyTree(key: k)
        } else{
            if (k<T.key) {
                def n = create_tree(T.left, k)
                T.left = n
                n.p = T
            } else {
                def n = create_tree(T.right, k)
                T.right = n
                n.p = T
            }
        }
        T
    }
    

    /**
     * Example from page 287
     */
    @Test
    void test_inorder_walk_a() {    
        def r = new MyTree(key:6,left:new MyTree(key:5,left:new MyTree(key:2),right:new MyTree(key:5)),right:new MyTree(key:7,right:new MyTree(key:8)))

        //Chapter12.Inorder_Tree_Walk(r)
        assert Chapter12.Inorder_Tree_Walk_Str(r,"") == "2 5 5 6 7 8"
    }
    
    @Test
    void test_inorder_walk_b() {
        def r = new MyTree(key:2,right:new MyTree(key:5,right:new MyTree(key:7,left:new MyTree(key:6,left:new MyTree(key:5)),right:new MyTree(key:8))))
        
        //Chapter12.Inorder_Tree_Walk(r)
        assert Chapter12.Inorder_Tree_Walk_Str(r,"") == "2 5 5 6 7 8"
    }
    
    @Test
    void test_tree_search() {
        def r = new MyTree(key:2,right:new MyTree(key:5,right:new MyTree(key:7,left:new MyTree(key:6,left:new MyTree(key:5)),right:new MyTree(key:8))))
        
        assert Chapter12.Tree_Search(r, 2)
        assert Chapter12.Tree_Search(r,999) == null
    }
    
    @Test
    void test_tree_maximum() {
        def r = new MyTree(key:2,right:new MyTree(key:5,right:new MyTree(key:7,left:new MyTree(key:6,left:new MyTree(key:5)),right:new MyTree(key:8))))
        
        assert Chapter12.Tree_Maximum(r).key == 8
    }
    
    @Test
    void test_tree_minimum() {
        def r = new MyTree(key:2,right:new MyTree(key:5,right:new MyTree(key:7,left:new MyTree(key:6,left:new MyTree(key:5)),right:new MyTree(key:8))))
        
        assert Chapter12.Tree_Minimum(r).key == 2
    }
    
    @Test
    void test_tree_successor() {
        def r = new MyTree(key:2,right:new MyTree(key:5,right:new MyTree(key:7,left:new MyTree(key:6,left:new MyTree(key:5)),right:new MyTree(key:8))))
        
        Chapter12.Tree_Successor(r).key == 5
    }
    
    /** 
     * Example from page 295
     */
    @Test
    void test_tree_insert() {
        def A = [12,5,18,2,9,15,19,17]
        
        def root = null
        A.each {
            root = create_tree(root, it)
        }
        root.root = root
        
        Chapter12.Tree_Insert(root, new MyTree(key:13))
        assert Chapter12.Inorder_Tree_Walk_Str(root,"") == "2 5 9 12 13 15 17 18 19"
        //Chapter12.Inorder_Tree_walk(root)
    }
    
    @Test
    void test_tree_delete() {
        def A = [12,5,18,2,9,15,19,17]
        
        def root = null
        A.each {
            root = create_tree(root, it)
        }
        root.root = root
        
        Chapter12.Tree_Delete(root, Chapter12.Tree_Minimum(root))
        assert Chapter12.Inorder_Tree_Walk_Str(root,"") == "5 9 12 15 17 18 19"
        
        Chapter12.Tree_Delete(root, Chapter12.Tree_Maximum(root))
        assert Chapter12.Inorder_Tree_Walk_Str(root,"") == "5 9 12 15 17 18"
    }
    
}

