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


class Chapter23_Test {


    class G {
        def V
        def E
        def S
    }
    
    class Vertex {
        def id
        def p
        def key
    }
    
    
    /** 
     * Examples of spanning trees
     */
    //
    // Nodes:      a,b,c,d 
    //
    // A:
    //             a ---- b
    //
    // B:
    //             a ---- b
    //
    //             d ---- c
    //            
    // C:              
    //             a ---- b
    //                    |
    //             d ---- c 
    //
    @Test
    void test_is_spanning_tree() {
        def N = ['a','b','c','d']  // all nodes in graph
        
        // examples of valid and invalid spanning tree. (Sets of edges)
        def A = [['a','b']] 
        def B = [['a','b'],['d','c']]
        def C = [['a','b'],['b','c'],['d','c']]
        def D = []
        
        def graph = new G()
        graph.V = N.collect { new Vertex(id:it) }
        
        assert ! Chapter23._forms_spanning_tree_(graph,A)
        assert ! Chapter23._forms_spanning_tree_(graph,B)
        assert Chapter23._forms_spanning_tree_(graph,C)
        assert ! Chapter23._forms_spanning_tree_(graph,D)
    }
    
    
    // Is spanning tree test cases below are based on this graph from page 627
    //
    //            b -------- c -------- d
    //          / |         /   \       | \
    //         a  |       i       \     |  e
    //          \ |    /   \        \   |  /
    //            |  /      \         \ | /
    //            h --------  g -------- f     
    //
    
    /**
     * Example from page 627
     */
    @Test
    void test_is_spanning_tree1() {
        def vertices = ['a','b','c','d','e','f','g','h','i']
        def graph = new G()
        graph.V = vertices.collect { new Vertex(id:it) }

        def E = [ ['a','b'],
                  ['i','c'],
                  ['c','f'],
                  ['g','h'],
                  ['g','f'] ]
        assert ! Chapter23._forms_spanning_tree_(graph, E)
        
        def E1 = [ ['a','b'],
                   ['i','c'],
                   ['c','f'],
                   ['g','h'],
                   ['g','f'],
                   ['h','a'], ]
        assert ! Chapter23._forms_spanning_tree_(graph, E1)     
        
        def E2 = [ ['a','b'],
                   ['i','c'],
                   ['c','f'],
                   ['g','h'],
                   ['g','f'],
                   ['h','a'],
                   ['c','d'] ]
        assert ! Chapter23._forms_spanning_tree_(graph, E2) 
        
        def E3 = [ ['a','b'],
                   ['i','c'],
                   ['c','f'],
                   ['g','h'],
                   ['g','f'],
                   ['h','a'],
                   ['c','d'],
                   ['d','f'] ] // create a loop here
        assert ! Chapter23._forms_spanning_tree_(graph, E3)   
        
        def E4 = [ ['a','b'],
                   ['i','c'],
                   ['c','f'],
                   ['g','h'],
                   ['g','f'],
                   ['h','a'],
                   ['c','d'],
                   ['e','f'] ]
        assert Chapter23._forms_spanning_tree_(graph, E4)         
    }    
    
    /**
     * Example from page 625
     */
    @Test
    void test_generic_mst() {
        def vertices = ['a','b','c','d','e','f','g','h','i']
        def graph = new G()        
        graph.V = vertices.collect { new Vertex(id:it) }
        
        // List of weights of undirected edges
        def W = [ [['a','b'],4],
                  [['a','h'],8],
                  [['b','h'],11],
                  [['b','c'],8],
                  [['i','c'],2],
                  [['i','h'],7],
                  [['i','g'],6],
                  [['h','g'],1],
                  [['c','d'],7],
                  [['g','f'],2],
                  [['c','f'],4],
                  [['d','f'],14],
                  [['d','e'],9],
                  [['e','f'],10] ]     
        
    }
    
    
    @Test
    void test_mst_kruskal() {
        def vertices = ['a','b','c','d','e','f','g','h','i']
        def graph = new G()        
        graph.V = vertices.collect { new Vertex(id:it) }
        
        // List of weights of undirected edges
        def W = [ [['a','b'],4],
                  [['a','h'],8],
                  [['b','h'],11],
                  [['b','c'],8],
                  [['i','c'],2],
                  [['i','h'],7],
                  [['i','g'],6],
                  [['h','g'],1],
                  [['c','d'],7],
                  [['g','f'],2],
                  [['c','f'],4],
                  [['d','f'],14],
                  [['d','e'],9],
                  [['e','f'],10] ]
       
        assert Chapter23.MST_Kruskal(graph, W) == [['a', 'b'], ['a', 'h'], ['c', 'd'], ['c', 'f'], ['d', 'e'], ['g', 'f'], ['h', 'g'], ['i', 'c']]                 
    
        def weights = [['a', 'b'], ['a', 'h'], ['c', 'd'], ['c', 'f'], ['d', 'e'], ['g', 'f'], ['h', 'g'], ['i', 'c']].collect { e ->
             W.find { it[0]==e }[1]       
        }
        println "weights:${weights}"
        println "Kruskal MST Total:${weights.sum()}"
    }
    
    
    @Test
    void test_find_weight() {
        // List of weights of undirected edges
        def W = [ [['a','b'],4],
                  [['a','h'],8],
                  [['b','h'],11],
                  [['b','c'],8],
                  [['i','c'],2],
                  [['i','h'],7],
                  [['i','g'],6],
                  [['h','g'],1],
                  [['c','d'],7],
                  [['g','f'],2],
                  [['c','f'],4],
                  [['d','f'],14],
                  [['d','e'],9],
                  [['e','f'],10] ]
    
        assert Chapter23.Adj(W, new Vertex(id:'a')) == ['b','h']   
        assert Chapter23.Adj(W, new Vertex(id:'e')) == ['d','f']    
        assert ! Chapter23.Adj(W, new Vertex(id:'z'))       
    }
    
    @Test
    void test_extract_min(){
        def Q = Chapter23._min_pq_((5..1).collect { new Vertex(id:it,key:it) })
        assert Chapter23.Extract_Min(Q).id == 1
    }
    
    @Test
    void test_heapify() {
        def Q = (5..1).collect { new Vertex(id:it,key:it) }
        for (i in Q.size()-1..0) {
            Chapter23._min_heapify_(Q,i)
            println "i:${i}, Q: ${Q.collect{it.key}}"
        }
        assert Q[0].key == 1 // min should be at 1st position
    }
    
    @Test
    void test_mst_prim() {
        def vertices = ['a','b','c','d','e','f','g','h','i']
        def graph = new G()        
        graph.V = vertices.collect { new Vertex(id:it) }
        
        // List of weights of undirected edges
        def W = [ [['a','b'],4],
                  [['a','h'],8],
                  [['b','h'],11],
                  [['b','c'],8],
                  [['i','c'],2],
                  [['i','h'],7],
                  [['i','g'],6],
                  [['h','g'],1],
                  [['c','d'],7],
                  [['g','f'],2],
                  [['c','f'],4],
                  [['d','f'],14],
                  [['d','e'],9],
                  [['e','f'],10] ]
        
        def r = Chapter23._find_node_by_id_(graph, 'a')
        def edges = Chapter23.MST_Prim(graph,W,r)
        
        println edges.collect { [it[0].id,it[1]?.id] }
        def total = 0
        for (edge in edges) {
            total += Chapter23._find_weight_by_edge_(W,edge[0],edge[1])?:0
        }        
        println "Prim MST Total: ${total}"
    }
    
}