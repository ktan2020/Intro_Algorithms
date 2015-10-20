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


class Chapter24_Test {
    
    
    class G {
        def V
        def E
    }
    
    class Vertex {
        def id
        def p 
        def d
    }
    
    /**
     * Example from page 659
     */
    @Test
    void test_dijkstra() {
        def vertices = ['s','t','x','y','z']
        def graph = new G()
        graph.V = vertices.collect { new Vertex(id:it) }
        
        // List of weights of directed edges
        def W = [ [['s','t'],10],
                  [['s','y'],5],
                  [['t','y'],2],
                  [['y','t'],3],
                  [['t','x'],1],
                  [['y','x'],9],
                  [['y','z'],2],
                  [['x','z'],4],
                  [['z','x'],6],
                  [['z','s'],7] ]
        
        def edges = Chapter24.Dijkstra(graph,W,Chapter24._find_node_by_id_(graph,'s'))        
        println edges.collect { [it[0].id,it[1]?.id] }
        
        def nodes_distances = edges.flatten().unique().findAll { it!=null }.inject([]) { x,y -> x << [y.id,y.d] }
        println nodes_distances
    }
    
}