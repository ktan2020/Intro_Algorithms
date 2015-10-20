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


class Chapter13_Test {

    class RBTree {
        def key
        def left, right
        def p
        def root
        def c      // color
    }
    
    def create_tree(T, k) {
        if (T==null) {
            return new RBTree(key: k)
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
    
    def _display_node_content_(T,k) {        
        assert T != null
        println "    * Searching for ## ${k} ## ... "
        def x = Chapter12.Tree_Search(T,k)
        if (x) {
            x.with {
                println "l:[${left?.key},${left?.c}] [k:${key},c:${c},p:${p?.key}] r:[${right?.key},${right?.c}]"
            }
        }
    }
    
    boolean _verify_tree_structure_(T,H) {
        H.each { k,v ->
            def z = Chapter12.Tree_Search(T, k)
            if (v.l) { assert z.left.key == v.l }
            assert z.key == k
            if (v.r) { assert z.right.key == v.r }
            if (v.c) { assert z.c == v.c }
        }
        true
    }
    
    
    /**
     * Example from page 314
     */
    @Test
    void test_left_rotate() {
        // Test left rotate with new root
        //
        // Before:
        //     11
        //    /  \
        //   9   18
        //
        // After:
        //      18
        //     /
        //   11
        //   /
        //  9
        def T = [11,9,18]
        
        def root = null
        T.each {
            root = create_tree(root, it)
        }
        root.root = root
        
        Chapter13.Left_Rotate(root, Chapter12.Tree_Search(root, 11))
        root = root.root; root.root = root 

        //T.each { _display_node_content_(root,it) }
        
        def tree_structure = [ 18:[l:11],
                               11:[l:9],
                               9:[] ]
        assert _verify_tree_structure_(root, tree_structure)        
    }
    
    /**
     * Example from page 314
     */
    @Test
    void test_right_rotate() {
        // Test right rotate with new root
        //
        // Before:
        //     18
        //    /  \
        //  11   19
        //
        // After:
        //   11
        //     \
        //     18
        //       \ 
        //       19
        
        def T = [18,11,19]
        
        def root = null
        T.each {
            root = create_tree(root, it)
        }
        root.root = root
        
        Chapter13.Right_Rotate(root, Chapter12.Tree_Search(root, 18))
        root = root.root; root.root = root 

        //T.each { _display_node_content_(root,it) }
        
        def tree_structure = [ 11:[r:18],
                               18:[r:19],
                               19:[] ]
        assert _verify_tree_structure_(root, tree_structure) 
    }
    
    /**
     * Example from page 314
     */
    @Test
    void test_left_rotate_then_right_rotate() {
        def T = [7,4,11,3,6,9,18,2,14,19,12,17,22,20]
        
        def root = null
        T.each {
            root = create_tree(root, it)
        }
        root.root = root
        
        // 1. rotate left 
        Chapter13.Left_Rotate(root, Chapter12.Tree_Search(root, 11))
        root = root.root; root.root = root
        
        def y = Chapter12.Tree_Search(root, 18)
        //println "y: ${y.left.key}, ${y.key}, ${y.right.key}"
        assert y.left.key==11 && y.key==18 && y.right.key==19
        
        // 2. then rotate right
        Chapter13.Right_Rotate(root, Chapter12.Tree_Search(root, 18))
        root = root.root; root.root = root
        
        def x = Chapter12.Tree_Search(root, 11)
        //println "x: ${x.left.key}, ${x.key}, ${x.right.key}"
        assert x.left.key==9 && x.key==11 && x.right.key==18
        
        // 3. we should get back original tree at this point
        def tree_structure = [ 7:[l:4,r:11],
                               4:[l:3,r:6],
                               3:[l:2],
                               11:[l:9,r:18],
                               18:[l:14,r:19],
                               14:[l:12,r:17],
                               19:[r:22],
                               22:[l:20] ]
        tree_structure.each { k,v ->
            def z = Chapter12.Tree_Search(root, k)
            if (v.l) { assert z.left.key == v.l }
            assert z.key == k
            if (v.r) { assert z.right.key == v.r }
        }  
                         
    }
    
    /**
     * Example from page 317
     */         
    @Test
    void test_rb_insert() {
        def T = [11,2,14,1,7,15,5,8]
        
        // Seed the 1st element in RB Tree
        def root = new RBTree(key:T.head(), c:Chapter13.COLOR.BLACK)
        root.root = root
        
        // Add the rest 
        T.tail().each {
            Chapter13.RB_Insert(root, new RBTree(key:it))
            root = root.root; root.root = root
        }
        
        // Display / verify 
        //T.each { _display_node_content_(root,it) }
        
        def tree_structure = [ 11:[l:2,r:14,c:Chapter13.COLOR.BLACK],
                               2:[l:1,r:7,c:Chapter13.COLOR.RED],
                               14:[r:15,c:Chapter13.COLOR.BLACK],
                               1:[c:Chapter13.COLOR.BLACK],
                               7:[l:5,r:8,c:Chapter13.COLOR.BLACK],
                               15:[c:Chapter13.COLOR.RED],
                               5:[c:Chapter13.COLOR.RED],
                               8:[c:Chapter13.COLOR.RED] ]
        assert _verify_tree_structure_(root, tree_structure)
    }     
    
    /**
     * Example from page 317
     * Add new node with key:4
     */
    @Test
    void test_rb_insert1() {
        // Same dataset as testcase above but now we insert a new node with key:4
        def T = [11,2,14,1,7,15,5,8,4]
                
        // Seed the 1st element in RB Tree
        def root = new RBTree(key:T.head(), c:Chapter13.COLOR.BLACK)
        root.root = root
        
        // Add the rest 
        T.tail().each {
            Chapter13.RB_Insert(root, new RBTree(key:it))
            root = root.root; root.root = root
        }
                
        T.each { 
            assert Chapter12.Tree_Search(root, it) != null 
            //_display_node_content_(root,it)
        }
        
        def tree_structure = [ 7:[l:2,r:11,c:Chapter13.COLOR.BLACK],
                               2:[l:1,r:5,c:Chapter13.COLOR.RED],
                               11:[l:8,r:14,c:Chapter13.COLOR.RED],
                               1:[c:Chapter13.COLOR.BLACK],
                               5:[l:4,c:Chapter13.COLOR.BLACK],
                               8:[c:Chapter13.COLOR.BLACK],
                               14:[r:15,c:Chapter13.COLOR.BLACK],
                               4:[c:Chapter13.COLOR.RED],
                               15:[c:Chapter13.COLOR.RED] ]
        assert _verify_tree_structure_(root, tree_structure)
    }
    
    
    /**
     * All RB Deletions from below are based on RB Tree on page 317
     * Note: [] => BLACK, () => RED
     *
     *                    [11]
     *                   /    \
     *                 (2)    [14]
     *                /  \      \
     *              [1]  [7]    (15)
     *                  /  \
     *                (5)  (8)
     */
    
    
    /**
     * Example from page 317
     * Delete node with key:5. No FixUp's needed.
     *
     *                    [11]
     *                   /    \
     *                 (2)    [14]
     *                /  \      \
     *              [1]  [7]    (15)
     *                     \
     *                     (8)
     */
    @Test
    void test_rb_delete1() {
        def T = [11,2,14,1,7,15,5,8]
        
        // Seed the 1st element in RB Tree
        def root = new RBTree(key:T.head(), c:Chapter13.COLOR.BLACK)
        root.root = root
        
        // Add the rest 
        T.tail().each {
            Chapter13.RB_Insert(root, new RBTree(key:it))
            root = root.root; root.root = root
        }
        
        Chapter13.RB_Delete(root, Chapter12.Tree_Search(root, 5))
        root = root.root; root.root = root
        
        assert Chapter12.Tree_Search(root, 5) == null
        
        def tree_structure = [ 11:[l:2,r:14,c:Chapter13.COLOR.BLACK],
                               2:[l:1,r:7,c:Chapter13.COLOR.RED],
                               14:[r:15,c:Chapter13.COLOR.BLACK],
                               1:[c:Chapter13.COLOR.BLACK],
                               7:[r:8,c:Chapter13.COLOR.BLACK],
                               15:[c:Chapter13.COLOR.RED],
                               8:[c:Chapter13.COLOR.RED] ]
        assert _verify_tree_structure_(root, tree_structure)
    }
    
    /**
     * Example from page 317
     * Delete node with key:7. FixUp's needed.
     *
     *                    [11]
     *                   /    \
     *                 (2)    [14]
     *                /  \      \
     *              [1]  [8]    (15)
     *                  /  
     *                (5)    
     */
    @Test
    void test_rb_delete2() {
        def T = [11,2,14,1,7,15,5,8]
        def key_to_delete = 7
        
        // Seed the 1st element in RB Tree
        def root = new RBTree(key:T.head(), c:Chapter13.COLOR.BLACK)
        root.root = root
        
        // Add the rest 
        T.tail().each {
            Chapter13.RB_Insert(root, new RBTree(key:it))
            root = root.root; root.root = root
        }
        
        Chapter13.RB_Delete(root, Chapter12.Tree_Search(root, key_to_delete))
        root = root.root; root.root = root
        
        assert Chapter12.Tree_Search(root, key_to_delete) == null
        
        (T-[key_to_delete]).each { 
            assert Chapter12.Tree_Search(root, it) != null 
            //_display_node_content_(root,it)
        }
                
        def tree_structure = [ 11:[l:2,r:14,c:Chapter13.COLOR.BLACK],
                               2:[l:1,r:8,c:Chapter13.COLOR.RED],
                               14:[r:15,c:Chapter13.COLOR.BLACK],
                               1:[c:Chapter13.COLOR.BLACK],
                               8:[l:5,c:Chapter13.COLOR.BLACK],
                               15:[c:Chapter13.COLOR.RED],
                               5:[c:Chapter13.COLOR.RED] ]
        assert _verify_tree_structure_(root, tree_structure)
    }
    
    /**
     * Example from page 317
     * Delete node with key:2. FixUp's needed.
     * 
     *                    [11]
     *                   /    \
     *                 (5)    [14]
     *                /  \      \
     *              [1]  [7]    (15)
     *                     \
     *                     (8)
     */
    @Test
    void test_rb_delete3() {
        def T = [11,2,14,1,7,15,5,8]
        def key_to_delete = 2
        
        // Seed the 1st element in RB Tree
        def root = new RBTree(key:T.head(), c:Chapter13.COLOR.BLACK)
        root.root = root
        
        // Add the rest 
        T.tail().each {
            Chapter13.RB_Insert(root, new RBTree(key:it))
            root = root.root; root.root = root
        }
        
        Chapter13.RB_Delete(root, Chapter12.Tree_Search(root, key_to_delete))
        root = root.root; root.root = root
        
        assert Chapter12.Tree_Search(root, key_to_delete) == null
        
        (T-[key_to_delete]).each { 
            assert Chapter12.Tree_Search(root, it) != null 
            //_display_node_content_(root,it)
        }
                
        def tree_structure = [ 11:[l:5,r:14,c:Chapter13.COLOR.BLACK],
                               5:[l:1,r:7,c:Chapter13.COLOR.RED],
                               14:[r:15,c:Chapter13.COLOR.BLACK],
                               1:[c:Chapter13.COLOR.BLACK],
                               7:[r:8,c:Chapter13.COLOR.BLACK],
                               15:[c:Chapter13.COLOR.RED],
                               8:[c:Chapter13.COLOR.RED] ]
        assert _verify_tree_structure_(root, tree_structure)
    }
    
    /**
     * Example from page 317
     * Delete node with key:1. No FixUp's needed because 1 is minimum element.
     *
     *                    [11]
     *                   /    \
     *                 (2)    [14]
     *                   \      \
     *                   [7]    (15)
     *                  /  \
     *                (5)  (8)
     */
    @Test
    void test_rb_delete4() {
        def T = [11,2,14,1,7,15,5,8]
        def key_to_delete = 1
        
        // Seed the 1st element in RB Tree
        def root = new RBTree(key:T.head(), c:Chapter13.COLOR.BLACK)
        root.root = root
        
        // Add the rest 
        T.tail().each {
            Chapter13.RB_Insert(root, new RBTree(key:it))
            root = root.root; root.root = root
        }
        
        Chapter13.RB_Delete(root, Chapter12.Tree_Search(root, key_to_delete))
        root = root.root; root.root = root
        
        assert Chapter12.Tree_Search(root, key_to_delete) == null
        
        (T-[key_to_delete]).each { 
            assert Chapter12.Tree_Search(root, it) != null 
            //_display_node_content_(root,it)
        }
                
        def tree_structure = [ 11:[l:2,r:14,c:Chapter13.COLOR.BLACK],
                               2:[r:7,c:Chapter13.COLOR.RED],
                               14:[r:15,c:Chapter13.COLOR.BLACK],
                               7:[l:5,r:8,c:Chapter13.COLOR.BLACK],
                               8:[c:Chapter13.COLOR.RED],
                               15:[c:Chapter13.COLOR.RED],
                               5:[c:Chapter13.COLOR.RED] ]
        assert _verify_tree_structure_(root, tree_structure)
    }
    
    /**
     * Example from page 317
     * Delete node with key:11. FixUp's needed.
     *
     *                    [14]
     *                   /    \
     *                 (2)    [15]
     *                /  \       
     *              [1]  [7]     
     *                  /  \
     *                (5)  (8)
     */
    @Test
    void test_rb_delete5() {
        def T = [11,2,14,1,7,15,5,8]
        def key_to_delete = 11
        
        // Seed the 1st element in RB Tree
        def root = new RBTree(key:T.head(), c:Chapter13.COLOR.BLACK)
        root.root = root
        
        // Add the rest 
        T.tail().each {
            Chapter13.RB_Insert(root, new RBTree(key:it))
            root = root.root; root.root = root
        }
        
        Chapter13.RB_Delete(root, Chapter12.Tree_Search(root, key_to_delete))
        root = root.root; root.root = root
        
        assert Chapter12.Tree_Search(root, key_to_delete) == null
        
        (T-[key_to_delete]).each { 
            assert Chapter12.Tree_Search(root, it) != null 
            //_display_node_content_(root,it)
        }
                
        def tree_structure = [ 14:[l:2,r:15,c:Chapter13.COLOR.BLACK],
                               2:[l:1,r:7,c:Chapter13.COLOR.RED],
                               7:[l:5,r:8,c:Chapter13.COLOR.BLACK],
                               1:[c:Chapter13.COLOR.BLACK],
                               8:[c:Chapter13.COLOR.RED],
                               15:[c:Chapter13.COLOR.BLACK],
                               5:[c:Chapter13.COLOR.RED] ]
        assert _verify_tree_structure_(root, tree_structure)
    }    
    
    /**
     * Example from page 317
     * Delete node with key:14. FixUp's needed.
     *
     *                    [11]
     *                   /    \
     *                 (2)    [15]
     *                /  \       
     *              [1]  [7]     
     *                  /  \
     *                (5)  (8)
     */
    @Test
    void test_rb_delete6() {
        def T = [11,2,14,1,7,15,5,8]
        def key_to_delete = 14
        
        // Seed the 1st element in RB Tree
        def root = new RBTree(key:T.head(), c:Chapter13.COLOR.BLACK)
        root.root = root
        
        // Add the rest 
        T.tail().each {
            Chapter13.RB_Insert(root, new RBTree(key:it))
            root = root.root; root.root = root
        }
        
        Chapter13.RB_Delete(root, Chapter12.Tree_Search(root, key_to_delete))
        root = root.root; root.root = root
        
        assert Chapter12.Tree_Search(root, key_to_delete) == null
        
        (T-[key_to_delete]).each { 
            assert Chapter12.Tree_Search(root, it) != null 
            //_display_node_content_(root,it)
        }
                
        def tree_structure = [ 11:[l:2,r:15,c:Chapter13.COLOR.BLACK],
                               2:[l:1,r:7,c:Chapter13.COLOR.RED],
                               15:[c:Chapter13.COLOR.BLACK],
                               7:[l:5,r:8,c:Chapter13.COLOR.BLACK],
                               1:[c:Chapter13.COLOR.BLACK],
                               8:[c:Chapter13.COLOR.RED],                               
                               5:[c:Chapter13.COLOR.RED] ]
        assert _verify_tree_structure_(root, tree_structure)
    }        
        
}