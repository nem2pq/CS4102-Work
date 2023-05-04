/**
 * CS4102 Spring 2022 -- Unit C Programming
 *********************************
 * Collaboration Policy: You are encouraged to collaborate with up to 3 other
 * students, but all work submitted must be your own independently written
 * solution. List the computing ids of all of your collaborators in the comment
 * at the top of your java or python file. Do not seek published or online
 * solutions for any assignments. If you use any published or online resources
 * (which may not include solutions) when completing this assignment, be sure to
 * cite them. Do not submit a solution that you are unable to explain orally to a
 * member of the course staff.
 *********************************
 * Your Computing ID: nem2pq
 * Collaborators: 
 * Sources: Introduction to Algorithms, Cormen
 **************************************/

public class SeamCarving {
    // x coordinates of every pixel in the seam
    int[] seam;
    double weight;
    int[][][] image;
    double[][][] eOP;
    int height;
    int width;


    
    public SeamCarving() {
        this.height = 0;
        this.width = 0;
        this.weight = 0;
    }
    
    /**
     * This method is the one you should implement.  It will be called to perform
     * the seam carving.  You may create any additional data structures as fields
     * in this class or write any additional methods you need.
     * 
     * @return the seam's weight
     */
    public double run(int[][][] image) {
        // do the seam carving
        this.height = image.length;
        //System.out.println(this.height);

        this.width = image[0].length;
        //System.out.println(this.width);
        // 0 will be e of P, 1 will be smallest energy required to get pixel
        this.eOP = new double[this.height][this.width][2];
        this.seam = new int[this.height];
        this.image = image;
        // edge cases
        // only one pixel
        if(this.height == 1 && this.width ==  1){
            this.seam[0] = 0;
            this.weight = 0;
            return this.weight;
        // one col
        }else if(this.width ==  1){
            for(int i = 0; i < this.height; i++){
                this.seam[i] = 0;
                // first pixel
                if(i == 0){
                    this.eOP[0][0][0] = e3D(0, 0, 0, 1);
                    this.eOP[0][0][1] = e3D(0, 0, 0, 1);
                    continue;
                // last pixel
                }else if(i == this.height -1){
                    this.eOP[i][0][0] = e3D(0, i, 0, i-1);
                    this.eOP[i][0][1] = this.eOP[i][0][0] + this.eOP[i-1][0][1];
                    continue;
                }else{
                    this.eOP[i][0][0] = (e3D(0, i, 0, i+1) + e3D(0, i, 0, i-1))/2;
                    this.eOP[i][0][1] = this.eOP[i][0][0] + this.eOP[i-1][0][1];
                }
            }
        this.weight = this.eOP[this.height-1][0][1];
        return this.weight;
        // one row
        }else if(this.height ==1){
            this.weight = Double.MAX_VALUE;
            for(int i = 0; i < this.width; i++){
                if(i ==0){
                    this.eOP[0][i][0] = e3D(i, 0, i+1, 0);
                    if(this.eOP[0][i][0] < this.weight){
                        this.weight = this.eOP[0][i][0];
                        this.seam[0] = i;
                    }
                    continue;
                }
                if(i == this.width -1){
                    this.eOP[0][i][0] = e3D(i, 0, i-1, 0);
                    if(this.eOP[0][i][0] < this.weight){
                        this.weight = this.eOP[0][i][0];
                        this.seam[0] = i;
                    }
                    continue;
                }else{
                    this.eOP[0][i][0] = (e3D(i, 0, i+1, 0) + e3D(i, 0, i-1, 0))/2;
                    if(this.eOP[0][i][0] < this.weight){
                        this.weight = this.eOP[0][i][0];
                        this.seam[0] = i;
                    }
                }
            }
            return this.weight;
        }
        // normal cases
        // edge cases(hard code)- maybe two rows we'll see
        // grab first row (don't need to check min())
        for(int i = 0; i < this.width; i++){
            this.eOP[0][i][0] = eofP(i, 0);
            this.eOP[0][i][1] = this.eOP[0][i][0];
        }
        this.weight = Double.MAX_VALUE;
        for(int i = 1; i < this.height; i++){
            for(int j = 0; j < this.width; j++){
                // calculate energy of individual pixel
                this.eOP[i][j][0] = eofP(j, i);
                // calculate minimum path to pixel by taking min of three pixels behind it (edge cases would be two)
                // modification for last row so we don't have to loop again
                if(i == this.height-1){
                    if(j == 0){
                        double p1 = Math.min(this.eOP[i-1][j][1],this.eOP[i-1][j+1][1]);
                        this.eOP[i][j][1] = this.eOP[i][j][0] + p1;
                        if(this.eOP[i][j][1] < this.weight){
                            this.weight = this.eOP[i][j][1];
                            this.seam[i] = j;
                        }
                        continue;
                    }else if(j == this.width-1){
                        double p1 = Math.min(this.eOP[i-1][j][1],this.eOP[i-1][j-1][1]);
                        this.eOP[i][j][1] = this.eOP[i][j][0] + p1;
                        if(this.eOP[i][j][1] < this.weight){
                            this.weight = this.eOP[i][j][1];
                            this.seam[i] = j;
                        }
                        continue;
                    }
                    double p1 = Math.min(this.eOP[i-1][j][1],this.eOP[i-1][j-1][1]);
                    double p2 = Math.min(p1,this.eOP[i-1][j+1][1]);
                    this.eOP[i][j][1] = this.eOP[i][j][0] + p2;
                    if(this.eOP[i][j][1] < this.weight){
                        this.weight = this.eOP[i][j][1];
                        this.seam[i] = j;
                    }
                // normal rows
                }else if(j == 0){
                    double p1 = Math.min(this.eOP[i-1][j][1],this.eOP[i-1][j+1][1]);
                    this.eOP[i][j][1] = this.eOP[i][j][0] + p1;
                    continue;
                }else if(j == this.width-1){
                    double p1 = Math.min(this.eOP[i-1][j][1],this.eOP[i-1][j-1][1]);
                    this.eOP[i][j][1] = this.eOP[i][j][0] + p1;
                    continue;
                }
                double p1 = Math.min(this.eOP[i-1][j][1],this.eOP[i-1][j-1][1]);
                double p2 = Math.min(p1,this.eOP[i-1][j+1][1]);
                this.eOP[i][j][1] = this.eOP[i][j][0] + p2;
            }

        }
        // now backtrack starting from seam[this.height-1]
        for(int i = this.height-2; i >= 0; i--){
            int loc = this.seam[i+1];
            //System.out.println(loc);
            if(loc != 0 && loc != this.width-1){
                double p1 = Math.min(this.eOP[i][loc][1],this.eOP[i][loc-1][1]);
                double p2 = Math.min(p1,this.eOP[i][loc+1][1]);
                if(p2 == this.eOP[i][loc][1]){
                    this.seam[i] = loc;
                    continue;
                }
                if(p2 == this.eOP[i][loc-1][1]){
                    this.seam[i] = loc -1;
                    continue;
                }
                else{
                    this.seam[i] = loc+1;
                    continue;
                }
            }
            else if(loc == 0){
                double p1 = Math.min(this.eOP[i][loc][1],this.eOP[i][loc+1][1]);
                if(p1 == this.eOP[i][loc][1]){
                    this.seam[i] = loc;
                    continue;
                } else{
                    this.seam[i] = loc+1;
                    continue;
                }

            } else{
                double p1 = Math.min(this.eOP[i][loc][1],this.eOP[i][loc-1][1]);
                if(p1 == this.eOP[i][loc][1]){
                    this.seam[i] = loc;
                    continue;
                }else {
                    this.seam[i] = loc-1;
                    continue;
                }
            }

        }

        // find e of P of all pixels in first two rows
        // calculate minimum path to 2nd row
        // continue calculating eOP of each row,
        // then calculating min of point 
        // checking min of three points behind it
        // when checking the last row (when y== height-1), after calculating mins
        // find minimum value in row, save as weight of seam (this.weight), 
        // backtrack to create seam
        // fill from this.height (last row) to 0 (first row) with X-coord
        // make sure it is save to this.seam for seam get
        //
        return this.weight;
    }
    
    // should check to see if a point has had it's e3D calculated
    // edge cases
    public double eofP(int x, int y){
        if(x == 0 && y == 0){
            double r = e3D(x, y, x+1, y);
            double br = e3D(x, y, x+1, y+1);
            double b = e3D(x, y, x, y+1);
            return ((r + br + b)/3);
        }else if(x == 0 && y ==  this.height-1){
            double t = e3D(x, y, x, y-1);
            double tr = e3D(x, y, x+1, y-1);
            double r = e3D(x, y, x+1, y);
            return ((t + tr + r)/3);
        }else if(x == this.width-1 && y == 0){
            double bl = e3D(x, y, x-1, y+1);
            double l = e3D(x, y, x-1, y);
            double b = e3D(x, y, x, y+1);
            return ((bl + l + b)/3);
        }else if(x == this.width-1 && y == this.height-1){
            double l = e3D(x, y, x-1, y);
            double tl = e3D(x, y, x-1, y-1);
            double t = e3D(x, y, x, y-1);
            return ((l + tl + t)/3);
        }else if(x == 0){
            double t = e3D(x, y, x, y-1);
            double tr = e3D(x, y, x+1, y-1);
            double r = e3D(x, y, x+1, y);
            double br = e3D(x, y, x+1, y+1);
            double b = e3D(x, y, x, y+1);
            return ((t + tr+ r + br + b)/5);
        }else if(x == this.width-1){
            double t = e3D(x, y, x, y-1);
            double b = e3D(x, y, x, y+1);
            double bl = e3D(x, y, x-1, y+1);
            double l = e3D(x, y, x-1, y);
            double tl = e3D(x, y, x-1, y-1);
            return ((t + tl+ l + bl + b)/5);
        }else if(y == 0){
            double r = e3D(x, y, x+1, y);
            double br = e3D(x, y, x+1, y+1);
            double b = e3D(x, y, x, y+1);
            double bl = e3D(x, y, x-1, y+1);
            double l = e3D(x, y, x-1, y);
            return ((r + br+ b + bl + l)/5);
        }else if(y == this.height-1){
            double tl = e3D(x, y, x-1, y-1);
            double t = e3D(x, y, x, y-1);
            double tr = e3D(x, y, x+1, y-1);
            double r = e3D(x, y, x+1, y);
            double l = e3D(x, y, x-1, y);
            return ((tl + t+ tr + r + l)/ 5);
        }else{
            double tl = e3D(x, y, x-1, y-1);
            double t = e3D(x, y, x, y-1);
            double tr = e3D(x, y, x+1, y-1);
            double r = e3D(x, y, x+1, y);
            double br = e3D(x, y, x+1, y+1);
            double b = e3D(x, y, x, y+1);
            double bl = e3D(x, y, x-1, y+1);
            double l = e3D(x, y, x-1, y);
            return ((tl + t+ tr + r + l + br + b + bl)/8);
        }
    }

    // this should only be done on a pixel once (smallest sub problem)
    public double e3D(int x1, int y1, int x2, int y2){
        int r1 = image[y1][x1][0];
        int g1 = image[y1][x1][1];
        int b1 = image[y1][x1][2];
        int r2 = image[y2][x2][0];
        int g2 = image[y2][x2][1];
        int b2 = image[y2][x2][2];
        double r = (r2-r1)*(r2-r1);
        double g = (g2-g1)*(g2-g1);
        double b = (b2-b1)*(b2-b1);
        double d = Math.sqrt((r + g + b));
        return d;
    }


    /**
     * Get the seam, in order from top to bottom, where the top-left corner of the
     * image is denoted (0,0).
     * 
     * Since the y-coordinate (row) is determined by the order, only return the x-coordinate
     * 
     * @return the ordered list of x-coordinates (column number) of each pixel in the seam
     */
    public int[] getSeam() {
        return this.seam;
    }
}
