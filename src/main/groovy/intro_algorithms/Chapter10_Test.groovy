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


class Chapter10_Test {

    class Node {
        def key
        def next, prev
        def head
    }
    
    def List_Traverse_Str(L,S="") {
        def x = L.head
        S += ((x) ? "L.head -> " : "")
        while (x!=null) {
            S += ((x.next) ? "${x.key} <-> " : "${x.key}")
            x=x.next
        }
        S
    }


    /**
     * Example from page 236
     */
    @Test
    void test_list_insert_and_delete() {
        // manually hand-create linked list
        def L1 = new Node(prev:null, key:9)
        def L2 = new Node(prev:L1, key:16)
        def L3 = new Node(prev:L2, key:4)
        def L4 = new Node(prev:L3, key:1)
        L1.next = L2
        L2.next = L3
        L3.next = L4
        L4.next = null   
        
        def L = L1; L.head = L1     
        
        Chapter10.List_Insert(L, new Node(key:25))
        assert List_Traverse_Str(L) == "L.head -> 25 <-> 9 <-> 16 <-> 4 <-> 1"
        
        Chapter10.List_Delete(L, Chapter10.List_Search(L, 1))
        assert List_Traverse_Str(L) == "L.head -> 25 <-> 9 <-> 16 <-> 4"
        
        Chapter10.List_Delete(L, Chapter10.List_Search(L, 25))
        assert List_Traverse_Str(L) == "L.head -> 9 <-> 16 <-> 4"
    }
    
}
