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


class Chapter22_Test {


    class G {
        def V
        def E
        def TIME
    }
    
    class Vertex {
        def c    // COLOR
        def id
        def p
        def d
        def f
    }
    
    def _print_graph_(G) {
        for (v in G.V) {
            println "id:${v.id}, c:${v.c}, d:${v.d}/${v.f}, p:${v.p?.id}"
        }
    }
    
    boolean _verify_graph_structure_(G,H) {
        H.each { k,v ->
            def n = Chapter22._find_vertex_by_id_(G,k)
            if (n.c) { assert n.c == v.c }
            if (n.p) { assert n.p.id == v.p }
            if (n.d) { assert n.d == v.d }
            if (n.f) { assert n.f == v.f }
        }
    }
    
    def _find_latest_discovered_(V) {
        def s = V.head()
        for (v in V.tail()) {
            if (v.d > s.d) { s = v }
        }
        s
    }
    
    def _all_dfs_paths_(G) {
        def V = G.V
        def P = []
        while (V) {
            def l = _find_latest_discovered_(V)
            def L = []
            while (l) {
                V -= l
                L.addAll(0,l.id)
                l = l.p 
            }
            P << L
        }
        P
    }
       
    
    /**
     * Example from page 596
     */
    @Test
    void test_bfs() {
        def vertices = ['r','s','t','u','v','w','x','y']
        def start_vertex = 's'
        def graph = new G()
        graph.V = vertices.collect { new Vertex(id:it) }
        graph.E = [  r:['s','v'],
                     s:['r','w'],
                     t:['w','x','u'],
                     u:['t','x','y'],
                     v:['r'],
                     w:['s','t','x'],
                     x:['w','t','u','y'],
                     y:['x','u'] ]
        
        Chapter22.BFS(graph, Chapter22._find_vertex_by_id_(graph,start_vertex))
        
        _print_graph_(graph)
        //Chapter22.Print_Path(graph, Chapter22._find_vertex_by_id_(graph,'s'), Chapter22._find_vertex_by_id_(graph,'u'))
        
        def graph_structure = [ r:[d:1, p:'s', c:Chapter22.COLOR.BLACK],
                                s:[d:0, c:Chapter22.COLOR.BLACK],
                                t:[d:2, p:'w',c:Chapter22.COLOR.BLACK],
                                u:[d:3, p:'t', c:Chapter22.COLOR.BLACK], // 2 possibilities: t or x
                                v:[d:2, p:'r', c:Chapter22.COLOR.BLACK],
                                w:[d:1, p:'s', c:Chapter22.COLOR.BLACK],
                                x:[d:2, p:'w', c:Chapter22.COLOR.BLACK],
                                y:[d:3, p:'x', c:Chapter22.COLOR.BLACK],        
                              ]
        assert _verify_graph_structure_(graph, graph_structure)
        
        //
        // Enumerated path starting from 's'
        // [r:[s, r], t:[s, w, t], u:[s, w, t, u], v:[s, r, v], w:[s, w], x:[s, w, x], y:[s, w, x, y]]
        //
        def all_possible_paths = [:]
        for (v in vertices-start_vertex) {
            all_possible_paths[v] = Chapter22.Print_Path_List(graph, Chapter22._find_vertex_by_id_(graph,start_vertex), Chapter22._find_vertex_by_id_(graph,v))
        }        
        println all_possible_paths        
        
    }
    
    /** 
     * Example from page 605
     */
    @Test
    void test_dfs() {
        def vertices = ['u','v','w','x','y','z']
        def start_vertex = 'u'
        def graph = new G()
        graph.V = vertices.collect { new Vertex(id:it) }
        graph.E = [  u:['v','x'],
                     v:['y'],
                     w:['y','z'],
                     x:['v'],
                     y:['x'],
                     z:['z'] ]

        Chapter22.DFS(graph)         
        
        _print_graph_(graph)          
        
        def graph_structure = [ u:[d:1, f:8, c:Chapter22.COLOR.BLACK],
                                v:[d:2, f:7, p:'u', c:Chapter22.COLOR.BLACK],
                                w:[d:9, f:12, c:Chapter22.COLOR.BLACK],
                                x:[d:4, f:5, p:'y', c:Chapter22.COLOR.BLACK], 
                                y:[d:3, f:6, p:'v', c:Chapter22.COLOR.BLACK],
                                z:[d:10, f:11, p:'w', c:Chapter22.COLOR.BLACK] ]
        assert _verify_graph_structure_(graph, graph_structure)  
                
        //
        // Enumerate all paths
        //
        assert _all_dfs_paths_(graph) == [['w','z'], ['u','v','y','x']]        
    }
    
    /**
     * Example from page 613
     */
     @Test
     void test_topological_sort() {
         def vertices = ["shirt","tie","jacket","belt","watch","undershorts","pants","shoes","socks"]
         def graph = new G()
         graph.V = vertices.collect { new Vertex(id:it) }
         graph.E = [ shirt:["belt","tie"],
                     tie:["jacket"],
                     jacket:[],
                     belt:["jacket"],
                     watch:[],                     
                     undershorts:["pants","shoes"],
                     pants:["belt","shoes"],                                                            
                     shoes:[],
                     socks:["shoes"] ]
                     
         println Chapter22.Topological_Sort(graph)      
         _print_graph_(graph)       
     }
    
}