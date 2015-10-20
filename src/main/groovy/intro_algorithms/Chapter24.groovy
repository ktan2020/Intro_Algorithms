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


final class Chapter24 {

    static def _find_node_by_id_(G, id) {
        for (n in G.V) { if (n.id==id) { return n } }
        null
    }

    static def _weight_by_edge_(w, u, v) {
        if (u==null || v==null) { return null }
        for (e in w) {
            if (u.id in e[0] && v.id in e[0]) { return e[1] }
        }
        null
    }
    
    static def Adj(w, u) {
        def L = []
        for (e in w.collect{ it[0] }) {
            if (u.id == e[0]) {
                L << e[1]
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
        if (l < Q.size() && Q[l].d < Q[i].d) {
            smallest = l
        } else {
            smallest = i
        }
        if (r < Q.size() && Q[r].d < Q[smallest].d) {
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


    static def Initialize_Single_Source(G,s) {
        for (v in G.V) {
            v.d = Integer.MAX_VALUE
            v.p = null
        }
        s.d = 0
    }
        
    static def Relax(u,v,w) {
        def wt = _weight_by_edge_(w,u,v)
        if (v.d > u.d + wt) {
            v.d = u.d + wt
            v.p = u
        }
    }
    
    static def Dijkstra(G,w,s) {
        Initialize_Single_Source(G,s)
        
        def S=[]        
        def Q=_min_pq_(G.V)
        
        while (Q.size()!=0) {
            println "*** Q => ${Q.collect { [it.id,it.d] }}"
            def u = Extract_Min(Q)
            def adj_nodes = Adj(w,u)
            
            println "  <<< id:${u.id}, d:${u.d}, adj:${adj_nodes}, p:${u.p?.id}, Qsize:${Q.size()} >>>"    
            S << [u,u.p]
            
            for (v in adj_nodes.collect{ id -> _find_node_by_id_(G,id) }) {
                if (v) {
                    Relax(u,v,w)
                }
            }
            
            Q = _min_pq_(Q)
        }
        
        S
    }

}