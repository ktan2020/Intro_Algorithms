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

final class Chapter21 {
    
    static def Make_Set(x) {
        [x]
    }
    
    static def Union(S,x,y) {
        S -= [x]
        S -= [y]
        S << x+y
    }
    
    static def Find_Set(S,x) {
        for (s in S) {
            if (x in s) return s
        }
        null
    }
    
    static def Connected_Components(G) {
        G.S = G.V.collect { Make_Set(it) }
        for (e in G.E) {
            def (u,v) = [e[0],e[1]]
            def su = Find_Set(G.S,u)
            def sv = Find_Set(G.S,v)
            println "u:${u}, v:${v}, su:${su}, sv:${sv}"
            if (su != sv) {
                G.S = Union(G.S,su,sv)
            }
        }
        G
    }
    
    static def Same_Component(S,u,v) {
        if (Find_Set(S,u)==Find_Set(S,v)) return true
        false
    }
        
}