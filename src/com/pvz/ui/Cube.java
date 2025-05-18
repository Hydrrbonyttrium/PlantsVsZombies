package com.pvz.ui;

import java.awt.Graphics;

import com.pvz.plants.Plants;

public class Cube {
    /*292 177
366 184
450 184
534 177
614 181
693 174
774 183
855 189
934 189
292 302
446 303
530 306
609 309
690 311
773 313
940 311
289 442
371 431
452 447
540 444
610 449
693 439
768 445
849 441
948 442
287 567
368 573
452 573
532 576
610 574
690 578
853 571
941 569
286 699
529 698
606 693
687 692
774 692
853 686
940 692 */

    int[][] postion = {
            {292, 177}, {366, 184}, {450, 184}, {534, 177}, {614, 181}, {693, 174}, {774, 183}, {855, 189}, {934, 189},
            {292, 302}, {365, 303}, {446, 303}, {530, 306}, {609, 309}, {690, 311}, {773, 313}, {850, 310}, {940, 311},
            {289, 442}, {371, 431}, {452, 447}, {540, 444}, {610, 449}, {693, 439}, {768, 445}, {849, 441}, {948, 442},
            {287, 567}, {368, 573}, {452, 573}, {532, 576}, {610, 574}, {690, 578}, {770, 575}, {853, 571}, {941, 569},
            {286, 699}, {365, 695}, {450, 695}, {529, 698}, {606, 693}, {687, 692}, {774, 692}, {853, 686}, {940, 692}
    };
    int[] point;
    int x;
    int y;
    int width=73;
    int height=116;
    int row;
    int column;
    Plants plant;

    public Cube(int row, int column) {
        this.row = row;
        this.column = column;
        point = postion[row * 9 + column];
        this.x = point[0];
        this.y = point[1];

        
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }



    public void setPlant(Plants plant) {
        this.plant = plant;
    }

    public Plants getPlant() {
        return plant;
    }

    public void grow(Graphics g) {
        if(plant != null) {
            plant.grow(g);
        }
    }

    //输入坐标，得到距离

    public int getDistance(int x, int y) {
        return (int) Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }

    
}
