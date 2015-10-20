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


final class Chapter22 {

    static {
        List.metaClass.myEnqueue = { n -> delegate.add(0, n) } 
        List.metaClass.myDequeue = { -> 
            def _ = delegate.getAt(-1)
            delegate.pop()
            _
        }
    }
    
    enum COLOR { WHITE, GRAY, BLACK }
    
    
    static def _find_vertex_by_id_(G,id) {
        assert G.V
        for (v in G.V) {
            if (v.id==id) return v
        }
        null
    }
    
    static def Adj(G,u) {
        assert G.E
        return G.E.get(u.id).inject([]) { l,id -> 
            l << _find_vertex_by_id_(G,id) 
        }
    }
    
    static def BFS(G,s) {
        (G.V - s).each { u ->
            u.c = COLOR.WHITE
            u.d = Integer.MAX_VALUE
            u.p = null 
        }        
        s.c = COLOR.GRAY
        s.d = 0
        s.p = null
        
        def Q = []
        Q.myEnqueue(s)
        
        while (Q.size() != 0) {
            def u = Q.myDequeue()
            
            for (v in Adj(G,u)) {        
                if (v.c == COLOR.WHITE) {
                    v.c = COLOR.GRAY
                    v.d = u.d + 1
                    v.p = u
                    Q.myEnqueue(v)
                }
            }            
            u.c = COLOR.BLACK
        }
    }
    
    static def Print_Path(G,s,v) {
        if (v == s) {
            println s.id
        } else if (v.p == null) {
            println "No path from '${s.id}' to '${v.id}' exists!"
        } else {
            Print_Path(G,s,v.p)
            println v.id
        }
    }
    
    static def Print_Path_List(G,s,v,L=[]) {
        if (v == s) {
            L << s.id
        } else if (v.p == null) {
            return null
        } else {
            L=Print_Path_List(G,s,v.p,L)
            L << v.id
        }
        L
    }
    
    static def DFS(G) {
        for (u in G.V) {
            u.c = COLOR.WHITE
            u.p = null
        }
        G.TIME = 0
        for (u in G.V) {
            if (u.c == COLOR.WHITE) {
                DFS_Visit(G,u)
            }
        }    
    }
    
    static def DFS_Visit(G,u) {
        G.TIME += 1
        u.d = G.TIME
        u.c = COLOR.GRAY        
        for (v in Adj(G,u)) {
            if (v.c == COLOR.WHITE) {
                v.p = u
                DFS_Visit(G,v)
            }
        }
        u.c = COLOR.BLACK
        G.TIME += 1
        u.f = G.TIME
        assert u.d < u.f
    }
    
    static def Topological_Sort(G) {
        DFS(G)

        def V = G.V
        def _find_vertex_by_largest_f_ = { it ->
            def h = it.head()
            for (v in it.tail()) {
                if (v.f > h.f) { h = v } 
            }
            h
        }
        def L = []
                       
        while (V) {
            def v = _find_vertex_by_largest_f_(V)
            L << v.id   
            V -= v         
        }
        L
    }
        
}