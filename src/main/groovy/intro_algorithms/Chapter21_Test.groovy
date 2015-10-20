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


class Chapter21_Test {
    
    class Graph {
        def V
        def E
        def S
    }
    
    /** 
     * Example from page 563
     */
    @Test
    void test_connected_components() {
        def g = new Graph()
        g.V = ['a','b','c','d','e','f','g','h','i','j']
        g.E = [['b','d'],['e','g'],['a','c'],['h','i'],['a','b'],['e','f'],['b','c']]
        
        Chapter21.Connected_Components(g)
        assert g.S == [['j'], ['h', 'i'], ['a', 'c', 'b', 'd'], ['e', 'g', 'f']]
    }
    
    @Test
    void test_same_component() {
        def g = new Graph()
        g.V = ['a','b','c','d','e','f','g','h','i','j']
        g.E = [['b','d'],['e','g'],['a','c'],['h','i'],['a','b'],['e','f'],['b','c']]
        
        Chapter21.Connected_Components(g)        
        assert Chapter21.Same_Component(g.S, 'a', 'b')
        assert ! Chapter21.Same_Component(g.S, 'e', 'b')
        assert ! Chapter21.Same_Component(g.S, 'z', 'b')
    }
    
}