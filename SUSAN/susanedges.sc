#include <stdio.h>
#include <stdlib.h>
#include "sim.sh"
#include <string.h>
import "c_queue"; 
include "constant.h"
import "c_double_handshake";

//void susan_edges(in,r,mid,bp,max_no,x_size,y_size)
//  uchar *in, *bp, *mid;
//  int   *r, max_no, x_size, y_size;

behavior susan_edges(i_receiver in_port1, i_receiver in_port2, i sender out_port)	 // max_no, x_size, y_size - > all constants  
															 // in,bp are inputs
															 // r,mid is output	
{
    void main(void)
    {	
        float z,max_no;
        // assign x_size and y_size values if required

        max_no = MAX_NO_EDGES;
        x_size = X_SIZE_CONST;
        y_size = Y_SIZE_CONST;
        uchar in[X_SIZE_CONST*Y_SIZE_CONST];
        uchar bp[BP_CONST];
        int r[(X_SIZE_CONST*Y_SIZE_CONST)];
        uchar mid[#size]; //TODO find mid's size
        in_port1.receive(in,(X_SIZE_CONST*Y_SIZE_CONST)); // receive the "in"
        in_port2.receive(bp,BP_CONST); // receive the "bp"
        //in_port.receive(r,(X_SIZE_CONST*Y_SIZE_CONST));  //receive "r"
        //TODO memset mid to 100


        int   do_symmetry, i, j, m, n, a, b, x, y, w;
        uchar c,*p,*cp;

        memset (r,0,x_size * y_size * sizeof(int));
        for (i=3;i<y_size-3;i++)
            for (j=3;j<x_size-3;j++)
            {
                n=100;
                p=in + (i-3)*x_size + j - 1;
                cp=bp + in[i*x_size+j];
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=x_size-3; 

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=x_size-5;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=x_size-6;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=2;
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=x_size-6;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=x_size-5;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=x_size-3;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);

                if (n<=max_no)
                    r[i*x_size+j] = max_no - n;
            }

        for (i=4;i<y_size-4;i++)
            for (j=4;j<x_size-4;j++)
            {
                if (r[i*x_size+j]>0)
                {
                    m=r[i*x_size+j];
                    n=max_no - m;
                    cp=bp + in[i*x_size+j];

                    if (n>600)
                    {
                        p=in + (i-3)*x_size + j - 1;
                        x=0;y=0;

                        c=*(cp-*p++);x-=c;y-=3*c;
                        c=*(cp-*p++);y-=3*c;
                        c=*(cp-*p);x+=c;y-=3*c;
                        p+=x_size-3; 

                        c=*(cp-*p++);x-=2*c;y-=2*c;
                        c=*(cp-*p++);x-=c;y-=2*c;
                        c=*(cp-*p++);y-=2*c;
                        c=*(cp-*p++);x+=c;y-=2*c;
                        c=*(cp-*p);x+=2*c;y-=2*c;
                        p+=x_size-5;

                        c=*(cp-*p++);x-=3*c;y-=c;
                        c=*(cp-*p++);x-=2*c;y-=c;
                        c=*(cp-*p++);x-=c;y-=c;
                        c=*(cp-*p++);y-=c;
                        c=*(cp-*p++);x+=c;y-=c;
                        c=*(cp-*p++);x+=2*c;y-=c;
                        c=*(cp-*p);x+=3*c;y-=c;
                        p+=x_size-6;

                        c=*(cp-*p++);x-=3*c;
                        c=*(cp-*p++);x-=2*c;
                        c=*(cp-*p);x-=c;
                        p+=2;
                        c=*(cp-*p++);x+=c;
                        c=*(cp-*p++);x+=2*c;
                        c=*(cp-*p);x+=3*c;
                        p+=x_size-6;

                        c=*(cp-*p++);x-=3*c;y+=c;
                        c=*(cp-*p++);x-=2*c;y+=c;
                        c=*(cp-*p++);x-=c;y+=c;
                        c=*(cp-*p++);y+=c;
                        c=*(cp-*p++);x+=c;y+=c;
                        c=*(cp-*p++);x+=2*c;y+=c;
                        c=*(cp-*p);x+=3*c;y+=c;
                        p+=x_size-5;

                        c=*(cp-*p++);x-=2*c;y+=2*c;
                        c=*(cp-*p++);x-=c;y+=2*c;
                        c=*(cp-*p++);y+=2*c;
                        c=*(cp-*p++);x+=c;y+=2*c;
                        c=*(cp-*p);x+=2*c;y+=2*c;
                        p+=x_size-3;

                        c=*(cp-*p++);x-=c;y+=3*c;
                        c=*(cp-*p++);y+=3*c;
                        c=*(cp-*p);x+=c;y+=3*c;

                        z = sqrt((float)((x*x) + (y*y)));
                        if (z > (0.9*(float)n)) /* 0.5 */
                        {
                            do_symmetry=0;
                            if (x==0)
                                z=1000000.0;
                            else
                                z=((float)y) / ((float)x);
                            if (z < 0) { z=-z; w=-1; }
                            else w=1;
                            if (z < 0.5) { /* vert_edge */ a=0; b=1; }
                            else { if (z > 2.0) { /* hor_edge */ a=1; b=0; }
                                else { /* diag_edge */ if (w>0) { a=1; b=1; }
                                    else { a=-1; b=1; }}}
                                    if ( (m > r[(i+a)*x_size+j+b]) && (m >= r[(i-a)*x_size+j-b]) &&
                                            (m > r[(i+(2*a))*x_size+j+(2*b)]) && (m >= r[(i-(2*a))*x_size+j-(2*b)]) )
                                        mid[i*x_size+j] = 1;
                        }
                        else
                            do_symmetry=1;
                    }
                    else 
                        do_symmetry=1;

                    if (do_symmetry==1)
                    { 
                        p=in + (i-3)*x_size + j - 1;
                        x=0; y=0; w=0;

                        /*   |      \
                             y  -x-  w
                             |        \   */

                        c=*(cp-*p++);x+=c;y+=9*c;w+=3*c;
                        c=*(cp-*p++);y+=9*c;
                        c=*(cp-*p);x+=c;y+=9*c;w-=3*c;
                        p+=x_size-3; 

                        c=*(cp-*p++);x+=4*c;y+=4*c;w+=4*c;
                        c=*(cp-*p++);x+=c;y+=4*c;w+=2*c;
                        c=*(cp-*p++);y+=4*c;
                        c=*(cp-*p++);x+=c;y+=4*c;w-=2*c;
                        c=*(cp-*p);x+=4*c;y+=4*c;w-=4*c;
                        p+=x_size-5;

                        c=*(cp-*p++);x+=9*c;y+=c;w+=3*c;
                        c=*(cp-*p++);x+=4*c;y+=c;w+=2*c;
                        c=*(cp-*p++);x+=c;y+=c;w+=c;
                        c=*(cp-*p++);y+=c;
                        c=*(cp-*p++);x+=c;y+=c;w-=c;
                        c=*(cp-*p++);x+=4*c;y+=c;w-=2*c;
                        c=*(cp-*p);x+=9*c;y+=c;w-=3*c;
                        p+=x_size-6;

                        c=*(cp-*p++);x+=9*c;
                        c=*(cp-*p++);x+=4*c;
                        c=*(cp-*p);x+=c;
                        p+=2;
                        c=*(cp-*p++);x+=c;
                        c=*(cp-*p++);x+=4*c;
                        c=*(cp-*p);x+=9*c;
                        p+=x_size-6;

                        c=*(cp-*p++);x+=9*c;y+=c;w-=3*c;
                        c=*(cp-*p++);x+=4*c;y+=c;w-=2*c;
                        c=*(cp-*p++);x+=c;y+=c;w-=c;
                        c=*(cp-*p++);y+=c;
                        c=*(cp-*p++);x+=c;y+=c;w+=c;
                        c=*(cp-*p++);x+=4*c;y+=c;w+=2*c;
                        c=*(cp-*p);x+=9*c;y+=c;w+=3*c;
                        p+=x_size-5;

                        c=*(cp-*p++);x+=4*c;y+=4*c;w-=4*c;
                        c=*(cp-*p++);x+=c;y+=4*c;w-=2*c;
                        c=*(cp-*p++);y+=4*c;
                        c=*(cp-*p++);x+=c;y+=4*c;w+=2*c;
                        c=*(cp-*p);x+=4*c;y+=4*c;w+=4*c;
                        p+=x_size-3;

                        c=*(cp-*p++);x+=c;y+=9*c;w-=3*c;
                        c=*(cp-*p++);y+=9*c;
                        c=*(cp-*p);x+=c;y+=9*c;w+=3*c;

                        if (y==0)
                            z = 1000000.0;
                        else
                            z = ((float)x) / ((float)y);
                        if (z < 0.5) { /* vertical */ a=0; b=1; }
                        else { if (z > 2.0) { /* horizontal */ a=1; b=0; }
                            else { /* diagonal */ if (w>0) { a=-1; b=1; }
                                else { a=1; b=1; }}}
                                if ( (m > r[(i+a)*x_size+j+b]) && (m >= r[(i-a)*x_size+j-b]) &&
                                        (m > r[(i+(2*a))*x_size+j+(2*b)]) && (m >= r[(i-(2*a))*x_size+j-(2*b)]) )
                                    mid[i*x_size+j] = 2;	
                    }
                }
            }
        out_port.send(r,IMAGE_SIZE); //TODO figure out the size
        out_port.send(mid,IMAGE_SIZE); //TODO figure out the size	
    }
};
