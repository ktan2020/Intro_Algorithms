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

final class Chapter13 {

    enum COLOR {
        RED,
        BLACK,
    }
    
    static def Left_Rotate(T,x) {    
        def y = x.right       // set y        
        x.right = y.left      // turn y's left subtree into x's right subtree
        if (y.left != null) { 
            y.left.p = x 
        }        
        y.p = x.p             // link x's parent to y 
        if (x.p == null) { 
            T.root = y 
        } else if (x == x.p.left) { 
            x.p.left = y 
        } else { 
            x.p.right = y 
        }         
        y.left = x            // put x on y's left
        x.p = y
        T
    }
    
    static def Right_Rotate(T,y) {        
        def x = y.left        // set x        
        y.left = x.right      // turn x's right subtree into y's left subtree
        if (x.left != null) { 
            x.right.p = y 
        }        
        x.p = y.p             // link y's parent to x
        if (y.p == null) { 
            T.root = x 
        } else if (y == y.p.right) { 
            y.p.right = x 
        } else { 
            y.p.left = x 
        }        
        x.right = y           // put y on x's right
        y.p = x
        T
    }
    
    static def RB_Insert(T,z) {
        def y = null
        def x = T.root
        while (x != null) {
            y = x
            if (z.key < x.key) { 
                x = x.left 
            } else { 
                x = x.right 
            }
        }
        //println "### Inserting ${z.key} @ ${y.key} ###"
        z.p = y
        if (y == null) { 
            T.root = z 
        } else if (z.key < y.key) { 
            y.left = z 
        } else { 
            y.right = z 
        }
        z.left = null
        z.right = null
        z.c = COLOR.RED
        RB_Insert_Fixup(T,z)
    }
    
    static def RB_Insert_Fixup(T,z) {
        while (z.p && z.p.c==COLOR.RED) { // !!! Beware slightly different from book !!!
            if (z.p == z.p.p.left) {
                def y = z.p.p.right
                if (y.c == COLOR.RED) {
                    z.p.c = COLOR.BLACK
                    y.c = COLOR.BLACK
                    z.p.p.c = COLOR.RED
                    z = z.p.p
                } else {
                    if (z == z.p.right) {
                        z = z.p
                        Left_Rotate(T,z) 
                    }
                    z.p.c = COLOR.BLACK
                    z.p.p.c = COLOR.RED
                    Right_Rotate(T,z.p.p)
                }                
            } else { // same as then clause with right/left exchanged
                def y = z.p.p.left
                if (y.c == COLOR.RED) {
                    z.p.c = COLOR.BLACK
                    y.c = COLOR.BLACK
                    z.p.p.c = COLOR.RED
                    z = z.p.p
                } else {
                    if (z == z.p.left) {
                        z = z.p
                        Right_Rotate(T,z) 
                    }
                    z.p.c = COLOR.BLACK
                    z.p.p.c = COLOR.RED
                    Left_Rotate(T,z.p.p)
                }                
            }
        }
        T.root.c = COLOR.BLACK
        T
    }
    
    static def RB_Transplant(T,u,v) {
        if (u.p == null) {
            T.root = v
        } else if (u == u.p.left) {
            u.p.left = v
        } else {
            u.p.right = v
        }
        if (v) { v.p = u.p } // !!! Beware slightly different from book !!!
        T
    }
    
    static def RB_Delete(T,z) {
        def x
        def y = z
        def y_original_color = y.c
        if (z.left == null) {
            x = z.right
            RB_Transplant(T,z,z.right)
        } else if (z.right == null) {
            x = z.left
            RB_Transplant(T,z,z.left)
        } else {
            y = Chapter12.Tree_Minimum(z.right)
            y_original_color = y.c
            x = y.right
            if (y.p == z) {
                if (x) x.p = y // !!! Beware slightly different from book !!!
            } else {
                RB_Transplant(T,y,y.right)
                y.right = z.right
                y.right.p = y
            }
            RB_Transplant(T,z,y)
            y.left = z.left
            y.left.p = y
            y.c = z.c
        }
        if (y_original_color == COLOR.BLACK) {
            RB_Delete_FixUp(T,x)
        }
        T
    }
    
    static def RB_Delete_FixUp(T,x) {
        while (x && x!=T.root && x.c==COLOR.BLACK) { // !!! Beware slightly different from book !!!
            if (x == x.p.left) {
                def w = x.p.right
                if (w.c == COLOR.RED) {
                    w.c = COLOR.BLACK
                    x.p.c = COLOR.RED
                    Left_Rotate(T,x.p)
                    w = x.p.right
                }
                if (w.left.c==COLOR.BLACK && w.right.c==COLOR.BLACK) {
                    w.c = COLOR.RED
                    x = x.p
                } else {
                    if (w.right.c == COLOR.BLACK) {
                        w.left.c = COLOR.BLACK
                        w.c = COLOR.RED
                        Right_Rotate(T,w)
                        w = x.p.right
                    }
                    w.c = x.p.c
                    x.p.c = COLOR.BLACK
                    w.right.c = COLOR.BLACK
                    Left_Rotate(T,x.p)
                    x = T.root
                }            
            } else { // same as then clause with right/left exchanged
                def w = x.p.left
                if (w.c == COLOR.RED) {
                    w.c = COLOR.BLACK
                    x.p.c = COLOR.RED
                    Right_Rotate(T,x.p)
                    w = x.p.left
                }
                if (w.right.c==COLOR.BLACK && w.left.c==COLOR.BLACK) {
                    w.c = COLOR.RED
                    x = x.p
                } else {
                    if (w.left.c == COLOR.BLACK) {
                        w.right.c = COLOR.BLACK
                        w.c = COLOR.RED
                        Left_Rotate(T,w)
                        w = x.p.left
                    }
                    w.c = x.p.c
                    x.p.c = COLOR.BLACK
                    w.right.c = COLOR.BLACK
                    Right_Rotate(T,x.p)
                    x = T.root
                }          
            }
        }
        if (x) x.c = COLOR.BLACK // !!! Beware slightly different from book !!!
    }
    
    
}