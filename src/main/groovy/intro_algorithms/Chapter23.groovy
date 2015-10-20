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


final class Chapter23 {

            
    static boolean _forms_spanning_tree_(G,A) {
        G.S = G.V.collect { Chapter21.Make_Set(it.id) }        

        for (e in A) {
            def (u,v) = [e[0],e[1]]
            def su = Chapter21.Find_Set(G.S,u)
            def sv = Chapter21.Find_Set(G.S,v)
            if (su != sv) {
                G.S = Chapter21.Union(G.S,su,sv)
            }
        }

        println "G.S: ${G.S}"
        if (G.S.size() != 1) { return false }      
        G.S[0].sort() == G.V.collect { it.id }.sort()
    }
    
    static def _find_a_safe_edge_(w,A) {
        // Remove known spanning edges in A from w
        def E = w - A
        E[((new Random()).nextInt()) % E.size()]
    }
    
    static def _find_node_by_id_(G, id) {
        for (n in G.V) { if (n.id==id) { return n } }
        null
    }
    
    static def _find_weight_by_edge_(w, u, v) {
        if (u==null || v==null) { return null }
        for (e in w) {
            if (u.id in e[0] && v.id in e[0]) { return e[1] }
        }
        null
    }
             
    static def _q_contains_(Q,n) {
        //(n in Q) ? true : false
        Q.find { it.id==n.id } != null
    }
    
    static def Adj(w, u) {
        def L = []
        for (e in w.collect{ it[0] }) {
            if (u.id in e) {
                L << (e-u.id).head()
            }
        }
        L
    }
    
    static def _min_pq_(V) {
        if (V.size()<=1) { return V } // !!! Beware slightly different from book. Added termination condition !!!
        V.add(0,null)
        for (i in (V.size()-1).intdiv(2)..1) {
            _min_heapify_(V,i)
        }
        V.removeAt(0)
        V
    }
    
    static def _min_heapify_(Q,i) {
        def (l,r) = [2*i,2*i+1] // left and right respectively
        def smallest
        if (l < Q.size() && Q[l].key < Q[i].key) {
            smallest = l
        } else {
            smallest = i
        }
        if (r < Q.size() && Q[r].key < Q[smallest].key) {
            smallest = r
        }
        if (smallest != i) {
            Q.swap(i,smallest)
            _min_heapify_(Q,smallest)
        }
    }
        
    static def Extract_Min(Q) {
        Q.add(0,null)
        def min = Q[1]
        Q[1] = Q[Q.size()-1]
        Q.removeAt(Q.size()-1)
        _min_heapify_(Q,1)
        Q.removeAt(0)
        min
    }
       
        
    static def Generic_MST(G,w) {
        def A = []
        while (! _forms_spanning_tree_(G,A)) {
            // find an edge (u,v) from [w] that is safe for A
            def edge = _find_a_safe_edge_(w,A)
            A << edge 
        }
        A
    }
    
    static def MST_Kruskal(G,w) {
        def A = []
        G.S = G.V.collect { Chapter21.Make_Set(it.id) }
        
        // sort w into non-decreasing order by weight
        w.sort { a,b -> a[1] <=> b[1] }
        
        for (e in w) {
            def (u,v) = [e[0][0],e[0][1]]
            def su = Chapter21.Find_Set(G.S,u)
            def sv = Chapter21.Find_Set(G.S,v)
            println "@ [u,v] => ${u},${v}, [su,sv] => ${su}${sv} @"
            if (su != sv) {
                A << [u,v]
                G.S = Chapter21.Union(G.S,su,sv) 
            }
        }
        
        A.sort()
    }
    
    static def MST_Prim(G,w,r) {
        def E = []
        
        for (u in G.V) {
            u.key = Integer.MAX_VALUE-1
            u.p = null
        }
        r.key = 0
        
        def Q = _min_pq_(G.V)     
        
        while (Q.size() != 0) { 
            println "*** Q => ${Q.collect { [it.id,it.key] }}"
            def u = Extract_Min(Q)            
            def adj_nodes = Adj(w,u)
            
            println "  <<< id:${u.id}, key:${u.key}, adj:${adj_nodes}, p:${u.p?.id}, Qsize:${Q.size()} >>>"            
            E << [u,u.p]
            
            for (v in adj_nodes.collect{ id -> _find_node_by_id_(G,id) }) {
                if (v) {
                    def weight = _find_weight_by_edge_(w,u,v)
                    if (_q_contains_(Q,v) && weight < v.key) {
                        v.p = u
                        v.key = weight
                    }
                }
            }
             
            Q = _min_pq_(Q) // heapify right after setting nodes' key
        }        
        E
    }
    
     
}